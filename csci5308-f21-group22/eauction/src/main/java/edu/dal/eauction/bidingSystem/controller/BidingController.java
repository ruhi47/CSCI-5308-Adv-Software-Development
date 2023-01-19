package edu.dal.eauction.bidingSystem.controller;

import edu.dal.eauction.bidingSystem.dto.BidingDto;
import edu.dal.eauction.bidingSystem.entities.EventBiding;
import edu.dal.eauction.bidingSystem.service.BidingService;
import edu.dal.eauction.userManagement.service.UserManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class BidingController {

    private static final Logger LOG = LogManager.getLogger();

    private UserManagementService userRegistrationService;
    private BidingService bidingService;

    public BidingController(UserManagementService userRegistrationService, BidingService bidingService) {
        this.userRegistrationService = userRegistrationService;
        this.bidingService = bidingService;
    }

    @GetMapping(value = {"/bidingPlacer/{id}"})
    public String goToBidingPlacer(@PathVariable("id") int id, Model model) {
        LOG.info("Routing to Biding Placer");
        EventBiding event = bidingService.getEventById(id);
        model.addAttribute("biding", new BidingDto());
        model.addAttribute("event", event);
        return "bidingPlacer";
    }

    @PostMapping("/bid/place/{id}/{amount}")
    public String bidPlace(@ModelAttribute("biding") BidingDto bidingDto, @PathVariable("id") Integer id, @PathVariable("amount") Integer amount, Model model) {
        LOG.info("Biding details : " + bidingDto.toString());
        if (bidingDto.getBidingAmount() < amount) {
            EventBiding event = bidingService.getEventById(id);
            model.addAttribute("event", event);
            model.addAttribute("biding", new BidingDto());
            model.addAttribute("errorMinAmount", "Amount entered should be greater than Min Bid Amount");
            return "bidingPlacer";
        }
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            username = authentication.getPrincipal().toString();
        } else {
            return "login";
        }
        bidingDto.setEventId(id);
        String result = bidingService.verifyBid(bidingDto, username);
        if (Objects.nonNull(result)) {
            EventBiding event = bidingService.getEventById(id);
            model.addAttribute("event", event);
            model.addAttribute("biding", new BidingDto());
            model.addAttribute("message", result);
            return "bidingPlacer";
        }
        bidingService.addBid(bidingDto, username);
        model.addAttribute("updateMessage", "Bid placed successfully");
        return "userDashboard";
    }


    @GetMapping(value = {"/upcomingBidingEvents"})
    public String
    getUpcomingEvents(Model model) {
        LOG.info("Routing to upcoming events");
        List<EventBiding> upcomingEvents = bidingService.getUpcomingEvents();
        model.addAttribute("upcomingEvents", upcomingEvents);
        return "upcomingBidingEvents";
    }

    @GetMapping(value = {"/pastBidingEvents"})
    public String getPastEvents(Model model) {
        LOG.info("Routing to past events");
        List<EventBiding> upcomingEvents = bidingService.getPastEvents();
        model.addAttribute("pastEvents", upcomingEvents);
        return "pastBidingEvents";
    }


}
