package org.example;

import org.example.ConfigData;
import org.example.TicketBox;
import org.example.TransactionLogger;

class TicketManager {
    private final ConfigData settings;
    private final TicketBox ticketBox;
    private final TransactionLogger logger;
    private volatile boolean isActive = false;

    //manage ticket system and coordinating ticket addition and custormer purchases in a multithreaded environment
    public TicketManager(ConfigData settings, TransactionLogger logger) {
        this.settings = settings;
        this.ticketBox = new TicketBox(settings.getTotalTicketsAvailable());
        this.logger = logger;// Initialize transaction logger.
    }


    public void runSystem() {
        isActive = true;
        new Thread(new TicketAdder()).start();
        new Thread(new CustomerBuyer()).start();
    }

    public void stopSystem() {
        isActive = false;
    }

    // Inner class to handle the addition of tickets to the system
    private class TicketAdder implements Runnable {
        @Override
        public void run() {
            while (isActive) {
                synchronized (ticketBox) {

                    // Add a ticket if there's space in the ticket box.
                    if (ticketBox.getAvailableTicketsCount() < settings.getMaxStorage()) {
                        ticketBox.addTickets(1); // Add one ticket at a time
                        logger.log("Added 1 ticket. Total available: " + ticketBox.getAvailableTicketsCount());
                    }
                }
                try {
                    // Calculate sleep time based on the ticket addition rate.
                    int sleepTime = settings.getTicketAddRate() > 0 ? (1000 / settings.getTicketAddRate()) : 1000;
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private class CustomerBuyer implements Runnable {
        @Override
        public void run() {
            while (isActive) {
                synchronized (ticketBox) {
                    if (ticketBox.buyTicket()) {
                        logger.log("Ticket purchased. Remaining tickets: " + ticketBox.getAvailableTicketsCount());
                    } else {
                        logger.log("Attempted to purchase ticket but none were available.");
                    }
                }
                try {
                    int sleepTime = settings.getCustomerPurchaseRate() > 0 ? (1000 / settings.getCustomerPurchaseRate()) : 1000;
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
