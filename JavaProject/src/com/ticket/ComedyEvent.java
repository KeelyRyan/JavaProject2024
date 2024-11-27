package com.ticket;

public final class ComedyEvent extends Event {
	
	private String performer;
	
	public ComedyEvent(String eventId, String eventName, String venue, String date, String performer, String organiser) {
		super(eventId, eventName, venue, date, organiser);
		this.performer = performer;
	}

	@Override
	public String getEventType() {
		return "Comedy Event";
	
	}
	
    public String getShowDetails() {
        return "Comedy by: " + performer + " in " + venue;
    }

}
