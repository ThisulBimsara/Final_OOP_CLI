package org.example;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

//class  manages a synchronized collection of tickets for a ticketing system.

class TicketBox {
    //thread-safe list to store tickets.
    private final List<Integer> ticketsList;

    public TicketBox(int initialTicketCount) {
        ticketsList = Collections.synchronizedList(new Vector<>());
       //add the initial tickets to the list
        for (int i = 0; i < initialTicketCount; i++) {
            ticketsList.add(1); //every entry represents a ticket
        }
    }


    //tickets add the ticket box
    public synchronized void addTickets(int number) {
        for (int i = 0; i < number; i++) {
            ticketsList.add(1);
        }
        System.out.println(number + " tickets added. Total available tickets: " + ticketsList.size());
    }

    //allows but a ticket if available.
    public synchronized boolean buyTicket() {
        if (ticketsList.size() > 0) {
            ticketsList.remove(0);
            System.out.println("Ticket purchased. Total available tickets: " + ticketsList.size());
            return true;
        } else {
            System.out.println("No tickets available to buy.");
            return false;
        }
    }

    public synchronized int getAvailableTicketsCount() {
        return ticketsList.size();
    }
}