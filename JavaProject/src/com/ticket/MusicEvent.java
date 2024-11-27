package com.ticket;

public final class MusicEvent extends Event {
	
	private String act;
	
	public MusicEvent(String eventId, String eventName, String venue, String date, String act, String organiser) {
		super(eventId, eventName, venue, date, organiser);
		this.act = act;
	}

	@Override
	public String getEventType() {
		return "Music Event";
	
	}
	
    public String getShowDetails() {
        return "Live perfomance by: " + act + " in " + getVenue();
    }

}
