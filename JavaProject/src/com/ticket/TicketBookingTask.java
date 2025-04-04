package com.ticket;

import java.time.Duration;
import java.util.concurrent.Callable;

public class TicketBookingTask implements Callable<Boolean> {
    private final Event event;
    private final TicketType ticketType;

    public TicketBookingTask(Event event, TicketType ticketType) {
        this.event = event;
        this.ticketType = ticketType;
    }

    @Override
    public Boolean call() {
        try {
            // Simulate a delay to visualize concurrency
        	Thread.sleep(Duration.ofMillis(500));

            synchronized (event) {
                System.out.println("Booking started by thread: " + Thread.currentThread().getName());
                boolean result = event.bookTicket(ticketType);
                System.out.println("Booking finished by thread: " + Thread.currentThread().getName() + " - Success: " + result);
                return result;
            }
        } catch (InterruptedException e) {
            System.out.println("Booking interrupted: " + e.getMessage());
            return false;
        }
    }
}
