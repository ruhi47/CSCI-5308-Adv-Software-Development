package edu.dal.eauction.bidingSystem.service;

import edu.dal.eauction.bidingSystem.dao.BidingDao;
import edu.dal.eauction.bidingSystem.dto.BidingDto;
import edu.dal.eauction.bidingSystem.entities.Biding;
import edu.dal.eauction.bidingSystem.entities.BidingUser;
import edu.dal.eauction.bidingSystem.entities.EventBiding;
import edu.dal.eauction.emailSender.SmtpEmailSenderImpl;
import edu.dal.eauction.emailSender.email.Email;
import edu.dal.eauction.emailSender.email.EmailType;
import edu.dal.eauction.emailSender.email.params.AuctionWonParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BidingService {

    private static final Logger LOG = LogManager.getLogger();
    private BidingDao bidingDao;
    private SmtpEmailSenderImpl emailSender;

    public BidingService(BidingDao bidingDao, SmtpEmailSenderImpl emailSender) {
        this.bidingDao = bidingDao;
        this.emailSender = emailSender;
    }

    public String verifyBid(BidingDto bidingDto, String username) {
        if(hasUserAlreadyPlacedBid(username, bidingDto.getEventId())) {
           return  "Bid already placed for this event";
        } else if(hasBidDoneInInvalidTimePeriod(bidingDto.getEventId())) {
            return "Biding time has elapsed";
        } else {
            return null;
        }

    }

    public Biding addBid(BidingDto bidingDto, String username) {
        Biding biding = new Biding(username, bidingDto.getEventId(), bidingDto.getBidingAmount(), bidingDto.getOptionalComments());
        bidingDao.saveBid(biding);
        return biding;
    }

    public boolean hasBidDoneInInvalidTimePeriod(Integer eventId) {
        EventBiding eventBiding = bidingDao.readEvent(eventId);
        if(eventBiding.getStartTime().isBefore(LocalDateTime.now()) && eventBiding.getEndTime().isAfter(LocalDateTime.now()))
            return false;
        else return true;
    }

    public boolean hasUserAlreadyPlacedBid(String username, Integer eventId) {
        return bidingDao.checkIfBidExistByUsernameAndEventId(username, eventId);
    }

    public void processBid() {
        // find all events where currentTime is greater than BidEndTime of Event and event winner is not declared.
        LOG.info("Running deamon thread to process all bids");
        List<EventBiding> read = bidingDao.read();
        List<EventBiding> processEvent = new ArrayList<>();
        for (EventBiding e: read) {
            if(e.getWinner() == null && LocalDateTime.now().isAfter(e.getEndTime())) {
                processEvent.add(e);
            }
        }
        for (EventBiding e: processEvent) {
            List<Biding> bidings = bidingDao.readBidingAmount(e.getEventId());
            String username;
            for (Biding bid: bidings) {
                BidingUser bidingUser = bidingDao.readUserStatus(bid.getUsername());
                if(bidingUser.getStatus().equals("ACTIVE")) {
                    //sendMailToBuyer(bidingUser, e, bid);
                    // send mail to seller
                   bidingDao.updateEvent(e.getEventId(), bid.getUsername());
                    break;
                }
            }
        }
    }

    public void sendMailToBuyer(BidingUser user, EventBiding eventBiding, Biding bid) {
        AuctionWonParams auctionWonParams = new AuctionWonParams(user.getEmail(), String.valueOf(eventBiding.getItemId()), eventBiding.getStartTime().toString(), bid.getBidingAmount().toString());
        Email email = new Email(EmailType.AUCTION_WON, user.getEmail());
        email.constructEmailContent(auctionWonParams);
        emailSender.sendEmail(email);
    }

    public List<EventBiding> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<EventBiding> events  = bidingDao.read();
        // add logic to fectch enrollment for a user

        //List<Integer> userEnrolledEvents = new ArrayList<>();
        // select eventId from enrollment_table where userId = email; -> List of eventId
        List<EventBiding> filteredEvents = new ArrayList<>();
        for (EventBiding e: events) {
            if(e.getEndTime().isAfter(now) || e.getStartTime().isAfter(now)) {  // && userEnrolledEvents.contains(e.getEventId())
                filteredEvents.add(e);
            }
        }
        for (EventBiding e : filteredEvents) {
            if(e.getStartTime().isBefore(now) && e.getEndTime().isAfter(now)) {
                e.setCanBid(true);
            } else {
                e.setCanBid(false);
            }
        }
        return filteredEvents;
    }

    public List<EventBiding> getPastEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<EventBiding> events  = bidingDao.read();
        List<EventBiding> filteredEvents = new ArrayList<>();
        for (EventBiding e: events) {
            if(e.getEndTime().isBefore(now)) {
                filteredEvents.add(e);
            }
        }
        return filteredEvents;
    }

    public EventBiding getEventById(int id) {
       return bidingDao.readEvent(id);
    }
}
