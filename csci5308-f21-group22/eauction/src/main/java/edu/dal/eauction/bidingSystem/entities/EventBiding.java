package edu.dal.eauction.bidingSystem.entities;

import java.time.LocalDateTime;

public class EventBiding {

    private int eventId;
    private int itemId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int minAmount;
    private String createdBy;
    private String winner;
    private boolean canBid;

    public EventBiding(Integer eventId, Integer itemId, LocalDateTime startTime, LocalDateTime endTime
            , Integer minAmount, String createdBy, String winner) {
        super();
        this.eventId = eventId;
        this.itemId = itemId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.minAmount = minAmount;
        this.createdBy = createdBy;
        this.winner = winner;
    }

    public boolean isCanBid() {
        return canBid;
    }

    public void setCanBid(boolean canBid) {
        this.canBid = canBid;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartDate(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndDate(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", itemId=" + itemId + ", startTime=" + startTime + ", endTime="
                + endTime + ", minAmount=" + minAmount + ", createdBy=" + createdBy + ", winner=" + winner + "]";
    }


}