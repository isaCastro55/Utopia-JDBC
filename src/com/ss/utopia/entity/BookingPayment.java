package com.ss.utopia.entity;

public class BookingPayment {
    private Booking bookingId;
    private String stripeId;
    private Short refunded;

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }

    public Short getRefunded() {
        return refunded;
    }

    public void setRefunded(Short refunded) {
        this.refunded = refunded;
    }
}
