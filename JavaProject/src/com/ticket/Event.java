package com.ticket;

import java.util.EnumMap;
// Example of sealed class to control event types.
public abstract sealed class Event permits MusicEvent, TheatreEvent, ComedyEvent {
	
    private String eventId;
    private String eventName;
    private String venue;
    private String date;
    private String organiser;
    
    // Enums being used. Declare in Ticket Type class.
    protected EnumMap<TicketType, Integer> ticketAvailability = new EnumMap<>(TicketType.class);
    
    public Event(String eventId, String eventName, String venue, String date, String organiser) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.setOrganiser(organiser);
        
        //Limit the amount of tickets available to demonstrate Lambda in main class.
        
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
    
    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
	public String getOrganiser() {
		return organiser;
	}
	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}

    public int getTicketAvailability(TicketType ticketType) {
        return ticketAvailability.get(ticketType);
    }
    // Used to display events to all users.
	public String getEventDetails() {
        return "Event ID: " + eventId + ", Name: " + eventName + ", Venue: " + venue + ", Date: " + date;
    }
    
    public abstract String getEventType();

}
