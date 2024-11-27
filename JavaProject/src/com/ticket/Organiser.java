package com.ticket;

import java.util.ArrayList;

public final class Organiser extends User implements EventManager {

	private String userId;
	private static int count;
	private ArrayList <Event> orgEvents = new ArrayList <>();
	
		
	public Organiser(String username, String emailAddress, String password) {
		super(username, emailAddress, password);
		count++;
		this.userId = "ORG" + String.format("%03d", count);
	}
	
	public String getUserId() {
		return userId;
	}

	@Override
	public String displayUserRole() {
		return "Organiser";

	}
	 @Override
	    public void postLoginMenu() {
	        boolean organiserActive = true;
	        while (organiserActive) {
	            System.out.println("\nOrganizer Menu:");
	            System.out.println("Enter '1' to add event,");
	            System.out.println("Enter '2' to view all events,");
	            System.out.println("Enter '3' to edit event,");
	            System.out.println("Enter '4' to cancel event,");
	            System.out.println("Enter '5' to logout");

	            int choice = TicketQueen.getUserChoice();
	            switch (choice) {
	                case 1:
	                    addEvent();
	                    break;
	                case 2:
	                	TicketQueen.displayEvents();
	                    break;
	                case 3:
	                    editEvent();
	                    break;
	                case 4:
	                    cancelEvent();
	                    break;
	                case 5:
	                    organiserActive = false;
	                    System.out.println("Logging out...");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	 }
	 @Override
	public void addEvent() {
	        System.out.print("Enter Event ID: ");
	        String eventId = TicketQueen.getInput();
	        
	        System.out.print("Enter category (Music/Comedy/Theatre: ");
	        String eventType = TicketQueen.getInput().toLowerCase();
	        
	        System.out.print("Enter Director/act/comedian: ");
	        String eventPerformers = TicketQueen.getInput();

	        System.out.print("Enter Event Name: ");
	        String eventName = TicketQueen.getInput();

	        System.out.print("Enter Venue: ");
	        String venue = TicketQueen.getInput();

	        System.out.print("Enter Date (YYYY-MM-DD): ");
	        String date = TicketQueen.getInput();

	        Event newEvent = null;
	     
	        if(eventType.equals("music")) {
	        	newEvent = new MusicEvent(eventId, eventName, venue, date, eventPerformers, userId); 
	        }if(eventType.equals("comedy")) {
	        	newEvent = new ComedyEvent(eventId, eventName, venue, date, eventPerformers, userId);
	        }if(eventType.equals("theatre")) {
		        newEvent = new TheatreEvent(eventId, eventName, venue, date, eventPerformers, userId);
	        }
	        orgEvents.add(newEvent);
	        TicketQueen.addEvent(newEvent);
	
	        System.out.println("Event added successfully: " + newEvent.getEventDetails());
	    }
	 private void editEvent() {
		 System.out.println("Enter the Event ID of the event to edit: ");
		 String eventId = TicketQueen.getInput();
		 
		 Event eventToEdit = null;
		 for(Event event : orgEvents) {
			 if(event.getEventId().equals(eventId)) {
				 eventToEdit = event;
				 break;
			 }
		 }
		
		 if (eventToEdit != null) {
			 System.out.println("Editing Event: " + eventToEdit.getEventDetails());
		 
	         System.out.print("Update Event name: (hit enter to skip)");
	         String newName = TicketQueen.getInput();
	         if (!newName.isEmpty()) {
	             eventToEdit.setEventName(newName);
	         }
	         System.out.print("Update Event venue: (hit enter to skip)");
	         String newVenue = TicketQueen.getInput();
	         if (!newVenue.isEmpty()) {
	             eventToEdit.setVenue(newVenue);
	         }
	         System.out.print("Update Event Date: (hit enter to skip)");
	         String newDate = TicketQueen.getInput();
	         if (!newDate.isEmpty()) {
	             eventToEdit.setDate(newDate);
	         }
	         
	         System.out.println("Event update successfully: " + eventToEdit.getEventDetails());
	 } else {
		 System.out.println("Event not found in your event list.");
         
         
	 }
}
	 @Override 
	    public void cancelEvent() {
	        System.out.print("Enter Event ID to delete: ");
	        String eventId = TicketQueen.getInput();

	        Event eventToDelete = null;
	        for (Event event : orgEvents) {
	            if (event.getEventId().equals(eventId)) {
	                eventToDelete = event;
	                break;
	            }
	        }

	        if (eventToDelete != null) {
	            orgEvents.remove(eventToDelete);
	            TicketQueen.removeEvent(eventToDelete);
	            System.out.println("Event deleted successfully.");
	        } else {
	            System.out.println("Event not found in your event list.");
	        }
	    }
	}
