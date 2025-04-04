package com.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.Scanner;

public class Attendee extends User {

    private String userId;
    private ArrayList<TicketRecord> attendeeTickets = new ArrayList<>();

    private static int count = 0;
    
 // ExecutorService to manage threads
    private static final ExecutorService bookingExecutor = Executors.newFixedThreadPool(5);

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
                case 4 -> {
                    attendeeActive = false;
                    System.out.println("Logging out...");
                }
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
            groupTickets(ticketNumber, ticketType, eventId);
        }
    }

    private void singleTicket() {
        System.out.print("Enter Event ID to book a ticket: ");
        String eventId = TicketQueen.getInput();
        bookTicket(TicketType.GENERAL, eventId);  // Example for single booking
    }

    private void bookTicket(TicketType ticketType, String eventId) {
        Event selectedEvent = TicketQueen.getEventById(eventId);

        if (selectedEvent == null) {
            System.out.println("Event not found.");
            return;
        }
// Using Executor Service to book tickets concurrently.... usecase needs improvement
        TicketBookingTask bookingTask = new TicketBookingTask(selectedEvent, ticketType);
        Future<Boolean> bookingResult = bookingExecutor.submit(bookingTask);

        try {
            if (bookingResult.get()) {
                attendeeTickets.add(new TicketRecord(eventId, ticketType));
                System.out.println("Ticket booked successfully for event: " + selectedEvent.getEventDetails());
            } else {
                System.out.println("No tickets available.");
            }
        } catch (Exception e) {
            System.out.println("Error processing ticket booking: " + e.getMessage());
        }
    }

    private void groupTickets(int ticketNumber, TicketType ticketType, String eventId) {
        Event selectedEvent = TicketQueen.getEventById(eventId);

        if (selectedEvent == null) {
            System.out.println("Event not found.");
            return;
        }

        int successfulBookings = 0;
        List<Future<Boolean>> bookingResults = new ArrayList<>();

        System.out.println("Initiating group booking with " + ticketNumber + " tickets...");

        for (int i = 0; i < ticketNumber; i++) {
            TicketBookingTask bookingTask = new TicketBookingTask(selectedEvent, ticketType);
            bookingResults.add(bookingExecutor.submit(bookingTask));
        }

        for (Future<Boolean> result : bookingResults) {
            try {
                if (result.get()) {
                    successfulBookings++;
                    attendeeTickets.add(new TicketRecord(eventId, ticketType));
                }
            } catch (Exception e) {
                System.out.println("Booking error: " + e.getMessage());
            }
        }
        System.out.println(successfulBookings + " tickets successfully booked for event: " + selectedEvent.getEventDetails());
    }


    private void viewTickets() {
        if (attendeeTickets.isEmpty()) {
            System.out.println("You have no booked tickets.");
        } else {
            Consumer<? super TicketRecord> printTicket = ticket -> 
                System.out.println("  - Event ID: " + ticket.eventId() + " | Ticket Type: " + ticket.ticketType());
            System.out.println("\nYour Booked Tickets:");
            attendeeTickets.forEach(printTicket);
        }
    }
}
