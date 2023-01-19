package edu.dal.eauction.bidingSystem.entities;

public class Biding {
    private String username;
    private Integer eventId;
    private Integer bidingAmount;
    private String optionalComments;

    public Biding(String username, Integer eventId, Integer bidingAmount, String optionalComments) {
        this.username = username;
        this.eventId = eventId;
        this.bidingAmount = bidingAmount;
        this.optionalComments = optionalComments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getBidingAmount() {
        return bidingAmount;
    }

    public void setBidingAmount(Integer bidingAmount) {
        this.bidingAmount = bidingAmount;
    }

    public String getOptionalComments() {
        return optionalComments;
    }

    public void setOptionalComments(String optionalComments) {
        this.optionalComments = optionalComments;
    }
}
