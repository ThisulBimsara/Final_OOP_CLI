package org.example;

import com.google.gson.Gson;
import org.example.ConfigData;
import org.example.TicketManager;
import org.example.TransactionLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

// Main application to manage the ticketing system.
public class MainApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConfigData settings = new ConfigData();
        TransactionLogger logger = new TransactionLogger();

        //  manual entry of settings
        setSettings(scanner, settings);

        System.out.println("Configured settings: " + settings);

        TicketManager manager = new TicketManager(settings, logger);

        String command;
        do {
            // user enter commands to control the system.
            System.out.print("Enter command (start/stop/exit): ");
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "start":
                    manager.runSystem();
                    System.out.println("Ticket manager started.");
                    break;
                case "stop":
                    manager.stopSystem();
                    System.out.println("Ticket manager stopped.");
                    logger.saveToFile("transaction_log.json");
                    break;
                case "exit":
                    manager.stopSystem();
                    logger.saveToFile("transaction_log.json");
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Unknown command. Please enter 'start', 'stop', or 'exit'.");
                    break;
            }
        } while (!command.equals("exit"));

        scanner.close();
    }

    private static void setSettings(Scanner scanner, ConfigData settings) {
        settings.setTotalTicketsAvailable(promptForPositiveInt(scanner, "Please enter total number of tickets(only positive numbers): "));
        settings.setTicketAddRate(promptForPositiveInt(scanner, "Please enter the ticket add rate (tickets per second, positive integer): "));
        settings.setCustomerPurchaseRate(promptForPositiveInt(scanner, "Please Enter Customer Purchase Rate (tickets per second, positive integer): "));
        settings.setMaxStorage(promptForPositiveInt(scanner, "Enter Max Storage (greater than enter Total Tickets): "));

        while (settings.getMaxStorage() < settings.getTotalTicketsAvailable()) {
            System.out.println("can't run the system!!!Max Storage must be greater than or equal to Total Tickets.");
            settings.setMaxStorage(promptForPositiveInt(scanner, "Enter Max Storage: "));
        }

        System.out.println("Settings configured: " + settings);
        saveSettings("settings.json", settings);
    }

    private static int promptForPositiveInt(Scanner scanner, String message) {
        int value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    scanner.nextLine(); // Consume newline
                    return value;
                }
            } else {
                scanner.next(); // Clear invalid input
            }
            System.out.println("Can't go to next step!!!Please enter a valid positive integer.");
        }
    }

    private static void saveSettings(String filename, ConfigData settings) {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            gson.toJson(settings, writer);
            System.out.println("Settings saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving settings: " + e.getMessage());
        }
    }
}
