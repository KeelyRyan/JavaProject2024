package com.ticket;

import java.time.LocalDate;

public final class MusicEvent extends Event {
	
	private String act;

	public MusicEvent(String eventId, String eventName, String venue, LocalDate date, String act, String organiser) {
		super(eventId, eventName, venue, date, organiser);  // Pass LocalDate directly
		this.act = act;
	}

	@Override
	public String getEventType() {
		return "Music Event";
	}

	public String getShowDetails() {
		return "Live performance by: " + act + " at " + getVenue();
	}
}
