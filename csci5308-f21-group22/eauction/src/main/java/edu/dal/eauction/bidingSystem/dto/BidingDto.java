package edu.dal.eauction.bidingSystem.dto;

public class BidingDto {
    private Integer eventId;
    private Integer bidingAmount;
    private String optionalComments;

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

    @Override
    public String toString() {
        return "Biding{" +
                ", eventId=" + eventId +
                ", bidingAmount=" + bidingAmount +
                ", optionalComments='" + optionalComments + '\'' +
                '}';
    }
}
