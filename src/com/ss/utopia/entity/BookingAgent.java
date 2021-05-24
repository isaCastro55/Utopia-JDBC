package com.ss.utopia.entity;

public class BookingAgent {
    private Booking bookingId;
    private User agentId;

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public User getAgentId() {
        return agentId;
    }

    public void setAgentId(User agentId) {
        this.agentId = agentId;
    }
}
