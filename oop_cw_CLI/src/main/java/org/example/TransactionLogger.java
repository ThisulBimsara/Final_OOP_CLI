package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//mange and log transactions for a ticket system
class TransactionLogger {

    // Class to represent a log entry
    private static class LogEntry {
        private final String timestamp;
        private final String message;

        public LogEntry(String timestamp, String message) {
            this.timestamp = timestamp;
            this.message = message;
        }
    }

    private final List<LogEntry> transactions;

    public TransactionLogger() {
        this.transactions = Collections.synchronizedList(new ArrayList<>());
    }

    // Log the transaction with a timestamp
    public void log(String message) {
        synchronized (transactions) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            transactions.add(new LogEntry(timestamp, message)); // Add log entry with timestamp
        }
    }

    // Save the transaction log to a JSON file in a structured format
    public void saveToFile(String filename) {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(transactions, writer); // Serialize transactions list as JSON array of objects
            System.out.println("Transaction log saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving transaction log: " + e.getMessage());
        }
    }
}