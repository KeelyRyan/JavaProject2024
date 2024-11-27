package com.ticket;

public final class TheatreEvent extends Event {

	private String director;
	
	public TheatreEvent(String eventId, String eventName, String venue, String date, String director, String organiser) {
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
