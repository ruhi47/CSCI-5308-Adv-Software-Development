package edu.dal.eauction.bidingSystem.entities;

public class BidingUser {
    private String status;
    private String email;
    private String firstName;


    public BidingUser(String status, String email, String firstName) {
        this.status = status;
        this.email = email;
        this.firstName = firstName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
