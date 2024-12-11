package org.example;

import java.io.*;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import com.google.gson.Gson;

// class to store and manage  configuration data
class ConfigData {

    private int totalTicketsAvailable;
    private int ticketAddRate;
    private int customerPurchaseRate;
    private int maxStorage;

    // use geters  and setters
    public int getTotalTicketsAvailable() {
        return totalTicketsAvailable;
    }

    public void setTotalTicketsAvailable(int totalTicketsAvailable) {
        this.totalTicketsAvailable = totalTicketsAvailable;
    }

    public int getTicketAddRate() {
        return ticketAddRate;
    }

    public void setTicketAddRate(int ticketAddRate) {
        this.ticketAddRate = ticketAddRate;
    }

    public int getCustomerPurchaseRate() {
        return customerPurchaseRate;
    }

    public void setCustomerPurchaseRate(int customerPurchaseRate) {
        this.customerPurchaseRate = customerPurchaseRate;
    }

    public int getMaxStorage() {
        return maxStorage;
    }

    public void setMaxStorage(int maxStorage) {
        this.maxStorage = maxStorage;
    }

    //showing all configuration  detaills.
    @Override
    public String toString() {
        return "ConfigData { " +
                "Total Tickets Available = " + totalTicketsAvailable +
                ", Ticket Add Rate = " + ticketAddRate +
                ", Customer Purchase Rate = " + customerPurchaseRate +
                ", Max Storage = " + maxStorage +
                " }";
    }
}