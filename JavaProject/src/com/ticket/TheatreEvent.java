package com.ticket;

import java.time.LocalDate;

public final class TheatreEvent extends Event {

	private String director;
	
	public TheatreEvent(String eventId, String eventName, String venue, LocalDate date, String performer, String organiser) {
        super(eventId, eventName, venue, date, organiser); 
		this.director = director;
	}

	@Override
	public String getEventType() {
		
		return "Theater Performance";
	}
	
    public String getPlayDetails() {
        return "Directed by: " + director;
    }
}
