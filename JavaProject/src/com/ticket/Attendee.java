package com.ticket;

import java.util.ArrayList;
import java.util.Scanner;

public class Attendee extends User{

	private String userId;
	private ArrayList <TicketRecord> attendeeTickets = new ArrayList <>();

	private static int count = 0;
	
	//Invoke super (user) constructor
	public Attendee(String username, String emailAddress, String password) {
		super(username, emailAddress, password);
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
	public ArrayList<TicketRecord> getAttendeeTickets() {
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
	                case 1 -> TicketQueen.displayEvents();
	                case 2 -> numberOfTickets();
	                case 3 -> viewTickets();
	                case 4 -> {attendeeActive = false;
	                    System.out.println("Logging out...");}
	                default -> System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	   
	   private void numberOfTickets() {
	        System.out.print("Are you making a group booking: y/n ");
	        String groupBook = TicketQueen.getInput().toLowerCase();
	        if (groupBook.equals("n")){
	        	singleTicket();
	        	
	        } else {
	            Scanner scanner = new Scanner(System.in);
	            System.out.print("How many tickets do you want to book: ");
	            int ticketNumber = scanner.nextInt();
	            groupTickets(ticketNumber);
	            
	        }
	   }
	   
	   private void singleTicket() {
	        System.out.print("Enter Event ID to book a ticket: ");
	        String eventId = TicketQueen.getInput();

	        Event selectedEvent = null;
	        for (Event event : TicketQueen.getAllEvents()) {
	            if (event.getEventId().equals(eventId)) {
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
	            	 attendeeTickets.add(new TicketRecord(selectedEvent.getEventId(), ticketType));
	                System.out.println("Ticket booked successfully for event: " + selectedEvent.getEventDetails());
	            } else {
	                System.out.println("Sorry, no tickets available for the selected type.");
	            }
	        } else {
	            System.out.println("Event not found. Please check the event ID and try again.");
	        }
	    }
	   
	   private void groupTickets(int ticketNumber) {
	        System.out.print("Enter Event ID to book a ticket: ");
	        String eventId = TicketQueen.getInput();

	        Event selectedEvent = null;
	        for (Event event : TicketQueen.getAllEvents()) {
	            if (event.getEventId().equals(eventId)) {
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
	            
	            int ticketsBooked =0;
	            for(int i = 0; i < ticketNumber; i++) { 
		            if (selectedEvent.bookTicket(ticketType)) {
		            	ticketsBooked++;
		            	attendeeTickets.add(new TicketRecord(selectedEvent.getEventId(), ticketType));
		                System.out.println("Ticket booked successfully for event: " + selectedEvent.getEventDetails());
		            } else {
		            	System.out.println("Only " + ticketsBooked + " tickets were booked. No more tickets available of type " + ticketType);
	            }
	        		System.out.println(ticketsBooked + " tickets booked successfully for event: " + selectedEvent.getEventDetails());
	        } 
	        }
	            else {
	            System.out.println("Event not found. Please check the event ID and try again.");
	        }
	   }
	   
	    private void viewTickets() {
	        if (attendeeTickets.isEmpty()) {
	            System.out.println("You have no booked tickets.");
	        } else {
	            System.out.println("\nYour Booked Tickets:");
	            for (TicketRecord ticket : attendeeTickets) {
	                System.out.println("Event Id: " + ticket.eventId() + " | Ticekt Type: " + ticket.ticketType());
	            }
	        }
	    }
}
