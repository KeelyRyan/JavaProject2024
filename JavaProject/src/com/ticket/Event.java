package com.ticket;

import java.util.EnumMap;

public abstract sealed class Event permits MusicEvent, TheatreEvent, ComedyEvent {
	
    protected String eventId;
    protected String eventName;
    protected String venue;
    protected String date;
    protected String organiser;
    
    protected EnumMap<TicketType, Integer> ticketAvailability = new EnumMap<>(TicketType.class);
    
    public Event(String eventId, String eventName, String venue, String date, String organiser) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.organiser = organiser;
        
        ticketAvailability.put(TicketType.VIP, 100);
        ticketAvailability.put(TicketType.GENERAL, 700);
        ticketAvailability.put(TicketType.BALCONY, 200);
    }
    public boolean bookTicket(TicketType ticketType) {
        if (ticketAvailability.get(ticketType) > 0) {
            ticketAvailability.put(ticketType, ticketAvailability.get(ticketType) - 1);
            return true;
        } else {
            return false;
        }
    }

    public int getTicketAvailability(TicketType ticketType) {
        return ticketAvailability.get(ticketType);
    }
	public String getEventDetails() {
        return "Event ID: " + eventId + ", Name: " + eventName + ", Venue: " + venue + ", Date: " + date;
    }
    
    public abstract String getEventType();
}
