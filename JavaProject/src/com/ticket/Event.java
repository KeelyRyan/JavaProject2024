package com.ticket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;

// Example of sealed class to control event types.
public abstract sealed class Event permits MusicEvent, TheatreEvent, ComedyEvent {
	
    private String eventId;
    private String eventName;
    private String venue;
    private LocalDate date;
    private String organiser;
    
    // Enums being used. Declare in Ticket Type class.
    protected EnumMap<TicketType, Integer> ticketAvailability = new EnumMap<>(TicketType.class);
    
    public Event(String eventId, String eventName, String venue, LocalDate date, String organiser) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;  // Store as LocalDate directly
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

    public LocalDate getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);  // Parsing date input
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return date.format(formatter);  
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
    public String getEventDetails() {
        return "Event ID: " + eventId + ", Name: " + eventName + ", Venue: " + venue + ", Date: " + getFormattedDate();
    }

    
    public abstract String getEventType();

}
