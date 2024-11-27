package com.ticket;

import java.util.ArrayList;

public class Attendee extends User{

	private String userId;
	private ArrayList <String> attendeeTickets = new ArrayList <>();

	private static int count = 0;
		
	public Attendee(String emailAddress, String username, String password) {
		super(emailAddress, username, password);
		count++;
		this.userId = "ATT" + String.format("%03d", count);
	}
	
	public String getUserId() {
		return userId;
	}

	@Override
	public String displayUserRole() {
		return "Member";

	}
	public ArrayList<String> getAttendeeTickets() {
	    return attendeeTickets;
	}

	   @Override
	    public void postLoginMenu() {
	        boolean attendeeActive = true;
	        while (attendeeActive) {
	            System.out.println("\nAttendee Menu:");
	            System.out.println("Enter '1' to browse events,");
	            System.out.println("Enter '2' to book tickets,");
	            System.out.println("Enter '3' to view your tickets,");
	            System.out.println("Enter '4' to logout");

	            int choice = TicketQueen.getUserChoice();
	            switch (choice) {
	                case 1:
	                	TicketQueen.displayEvents();
	                    break;
	                case 2:
	                	bookTicket();
	                    break;
	                case 3:
	                	viewTickets();
	                    break;
	                case 4:
	                    attendeeActive = false;
	                    System.out.println("Logging out...");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	   
	   private void bookTicket() {
	        System.out.print("Enter Event ID to book a ticket: ");
	        String eventId = TicketQueen.getInput();

	        Event selectedEvent = null;
	        for (Event event : TicketQueen.getAllEvents()) {
	            if (event.eventId.equals(eventId)) {
	                selectedEvent = event;
	                break;
	            }
	        }

	        if (selectedEvent != null) {
	            System.out.println("Available Ticket Types: VIP, GENERAL, BALCONY");
	            System.out.print("Enter ticket type: ");
	            String ticketTypeStr = TicketQueen.getInput().toUpperCase();
	            TicketType ticketType;

	            try {
	                ticketType = TicketType.valueOf(ticketTypeStr);
	            } catch (IllegalArgumentException e) {
	                System.out.println("Invalid ticket type. Please try again.");
	                return;
	            }

	            if (selectedEvent.bookTicket(ticketType)) {
	            	 attendeeTickets.add("Event: " + selectedEvent.getEventDetails() + ", Ticket Type: " + ticketType);
	                System.out.println("Ticket booked successfully for event: " + selectedEvent.getEventDetails());
	            } else {
	                System.out.println("Sorry, no tickets available for the selected type.");
	            }
	        } else {
	            System.out.println("Event not found. Please check the event ID and try again.");
	        }
	    }
	    private void viewTickets() {
	        if (attendeeTickets.isEmpty()) {
	            System.out.println("You have no booked tickets.");
	        } else {
	            System.out.println("\nYour Booked Tickets:");
	            for (String ticket : attendeeTickets) {
	                System.out.println(ticket);
	            }
	        }
	    }
}
