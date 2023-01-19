package edu.dal.eauction.bidingSystem.dao;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.bidingSystem.entities.Biding;
import edu.dal.eauction.bidingSystem.entities.BidingUser;
import edu.dal.eauction.bidingSystem.entities.EventBiding;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BidingDao {

    private static final String INSERT_BID = "INSERT INTO BIDING VALUES (?,?,?,?)";
    private static final String SELECT_BID = "SELECT USERNAME, EVENT_ID, BIDING_AMOUNT, OPTIONAL_COMMENTS FROM BIDING WHERE USERNAME = ? AND EVENT_ID = ?";
    private static final String SELECT_EVENT = "SELECT eventId, itemId, startTime, endTime, minAmount, createdBy, winner FROM EVENT WHERE eventId = ?";
    private static final String SELECT_ALL_EVENTS = "SELECT eventId, itemId, startTime, endTime, minAmount, createdBy, winner FROM EVENT";
    private static final String SELECT_BID_DESC_ORDER_AMOUNT = "SELECT username, event_id, biding_amount, optional_comments FROM BIDING where event_id = ? order by biding_amount desc";
    private static final String SELECT_USER = "SELECT first_name, email, status FROM users where email = ?";
    private static final String UPDATE_EVENT = "UPDATE EVENT SET WINNER = ? where eventId = ?";

    public void saveBid(Biding biding) {
        DBConnect con = DBConnect.getInstance();
        try (Connection connection = con.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BID)) {
            preparedStatement.setString(1, biding.getUsername());
            preparedStatement.setInt(2, biding.getEventId());
            preparedStatement.setInt(3, biding.getBidingAmount());
            preparedStatement.setString(4, biding.getOptionalComments());

            preparedStatement.executeUpdate();
            System.out.println("bid inserted in biding table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfBidExistByUsernameAndEventId(String username, Integer eventId) {
        DBConnect con = DBConnect.getInstance();
        int count = 0;
        try (Connection connection = con.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BID)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, eventId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (count > 0) return true;
        else return false;
    }


    public EventBiding readEvent(Integer id) {
        EventBiding event = null;
        DBConnect con = DBConnect.getInstance();
        try (Connection connection = con.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT)) {
            preparedStatement.setInt(1, id);
            System.out.print(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer eventId = rs.getInt("eventId");
                Integer itemId = rs.getInt("itemId");
                LocalDateTime startTime = LocalDateTime.parse(rs.getObject("startTime").toString());
                LocalDateTime endTime = LocalDateTime.parse(rs.getObject("endTime").toString());
                Integer minAmount = rs.getInt("minAmount");
                String createdBy = rs.getString("createdBy");
                String winner = rs.getString("winner");
                event = new EventBiding(eventId, itemId, startTime, endTime, minAmount, createdBy, winner);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    public List<EventBiding> read() {
        DBConnect con = DBConnect.getInstance();
        EventBiding event = null;
        List<EventBiding> events = new ArrayList<>();
        try (Connection connection = con.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS);) {
            System.out.print(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer eventId = rs.getInt("eventId");
                Integer itemId = rs.getInt("itemId");
                LocalDateTime startTime = LocalDateTime.parse(rs.getObject("startTime").toString());
                LocalDateTime endTime = LocalDateTime.parse(rs.getObject("endTime").toString());
                Integer minAmount = rs.getInt("minAmount");
                String createdBy = rs.getString("createdBy");
                String winner = rs.getString("winner");
                event = new EventBiding(eventId, itemId, startTime, endTime, minAmount, createdBy, winner);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<Biding> readBidingAmount(Integer id) {
        DBConnect con = DBConnect.getInstance();
        Biding biding = null;
        List<Biding> bidings = new ArrayList<>();
        try (Connection connection = con.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BID_DESC_ORDER_AMOUNT);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                Integer eventId = rs.getInt("event_id");
                Integer bidingAmount = rs.getInt("biding_amount");
                String optionalComments = rs.getString("optional_comments");
                biding = new Biding(username, eventId, bidingAmount, optionalComments);
                bidings.add(biding);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bidings;
    }


    public BidingUser readUserStatus(String id) {
        DBConnect con = DBConnect.getInstance();
        BidingUser bidingUser = null;
        try (Connection connection = con.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String status = rs.getString("status");
                String email = rs.getString("email");
                String firstName = rs.getString("first_name");
                bidingUser = new BidingUser(status, email, firstName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bidingUser;
    }

    public void updateEvent(int eventId, String username) {
        DBConnect con = DBConnect.getInstance();
        try (Connection connection = con.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EVENT);) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, eventId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
