package com.ticket;

import java.time.LocalDate;

public final class ComedyEvent extends Event {

    private String performer;

    public ComedyEvent(String eventId, String eventName, String venue, LocalDate date, String performer, String organiser) {
        super(eventId, eventName, venue, date, organiser);  // Pass LocalDate directly
        this.performer = performer;
    }

    @Override
    public String getEventType() {
        return "Comedy Event";
    }

    public String getShowDetails() {
        return "Comedy by: " + performer + " at " + getVenue();
    }
}
