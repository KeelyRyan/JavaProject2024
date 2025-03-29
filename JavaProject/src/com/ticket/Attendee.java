package com.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
		    if (groupBook.equals("n")) {
		        singleTicket();
		    } else {
		        Scanner scanner = new Scanner(System.in);
		        System.out.print("Enter Event ID: ");
		        String eventId = TicketQueen.getInput();
		        
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

		        System.out.print("How many tickets do you want to book: ");
		        int ticketNumber = scanner.nextInt();
		        
		        groupTickets(ticketNumber, ticketType, eventId);  // ✅ Pass the correct parameters
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
	   
	   private static final ExecutorService bookingExecutor = Executors.newFixedThreadPool(5);  // Create a Thread Pool

	   private void bookTicket(TicketType ticketType, String eventId) {
	       Event selectedEvent = TicketQueen.getEventById(eventId);

	       if (selectedEvent == null) {
	           System.out.println("Event not found.");
	           return;
	       }

	       Future<Boolean> bookingResult = bookingExecutor.submit(() -> {  // Run booking in a separate thread
	           synchronized (selectedEvent) {  // Ensure thread-safe event booking
	               return selectedEvent.bookTicket(ticketType);
	           }
	       });

	       try {
	           if (bookingResult.get()) {  // Wait for result (ensures thread completes)
	               attendeeTickets.add(new TicketRecord(eventId, ticketType));
	               System.out.println("Ticket booked successfully for event: " + selectedEvent.getEventDetails());
	           } else {
	               System.out.println("No tickets available.");
	           }
	       } catch (Exception e) {
	           System.out.println("Error processing ticket booking: " + e.getMessage());
	       }
	   }
	   
	   @SuppressWarnings("unused")
	private void groupTickets(int ticketNumber, TicketType ticketType, String eventId) {
		    Event selectedEvent = TicketQueen.getEventById(eventId);

		    if (selectedEvent == null) {
		        System.out.println("Event not found.");
		        return;
		    }

		    int[] successfulBookings = {0};  // Array to store the count of successful bookings

		    List<Future<Boolean>> bookingResults = new ArrayList<>();
		    
		    for (int i = 0; i < ticketNumber; i++) {
		        bookingResults.add(bookingExecutor.submit(() -> {  //  Submit multiple booking tasks
		            synchronized (selectedEvent) {  //Ensure thread-safe booking
		                return selectedEvent.bookTicket(ticketType);
		            }
		        }));
		    }

		    // Process booking results
		    for (Future<Boolean> result : bookingResults) {
		        try {
		            if (result.get()) {
		                successfulBookings[0]++;
		                attendeeTickets.add(new TicketRecord(eventId, ticketType));
		            }
		        } catch (Exception e) {
		            System.out.println("Booking error: " + e.getMessage());
		        }
		    }
		    if (selectedEvent != null) {
		        System.out.println(successfulBookings[0] + " tickets successfully booked for event: " + selectedEvent.getEventDetails());
		    } else {
		        System.out.println("Error: Event not found.");
		    }
	   }

	   
	   private void viewTickets() {
		    if (attendeeTickets.isEmpty()) {
		        System.out.println("You have no booked tickets.");
		    } else {
		        System.out.println("\n Your Booked Tickets:");
		        attendeeTickets.forEach(ticket -> 
		            System.out.println("  - Event ID: " + ticket.eventId() + " | Ticket Type: " + ticket.ticketType())
		        );
		    }
		}
}
