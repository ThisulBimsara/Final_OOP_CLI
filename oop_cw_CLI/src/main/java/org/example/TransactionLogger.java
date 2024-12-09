package org.example;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//mange and log transactions for a ticket system
class TransactionLogger {

    private final List<String> transactions;

    public TransactionLogger() {
        this.transactions = Collections.synchronizedList(new ArrayList<>());
    }

    public void log(String message) {
        synchronized (transactions) {
            transactions.add(message);//add massage to transaction
        }
    }

    //save the trasaction log to json file and list in to json array
    public void saveToFile(String filename) {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            gson.toJson(transactions, writer);// Serialize the transactions list to json and write to file.

            System.out.println("Transaction log saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving transaction log: " + e.getMessage());
        }
    }
}