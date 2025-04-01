package org.example;

import java.util.Random;

public class Ticket {
    String clientName = null;
    int nChars;
    String ticketValue;

    public static final String  CHARS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";


    public Ticket(String clientName, int nChars) {
        this.clientName = clientName;
        this.nChars = nChars;
        this.ticketValue = generateValue(nChars);
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getnChars() {
        return nChars;
    }

    public void setnChars(int nChars) {
        this.nChars = nChars;
    }

    public String getTicketValue() {
        return ticketValue;
    }

    public void setTicketValue(String ticketValue) {
        this.ticketValue = ticketValue;
    }

    @Override
    public String toString() {
        return "Ticket(" +ticketValue+", "+clientName+')';
    }

    public static String generateValue(int nChars){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nChars; i++) {
            sb.append(CHARS.charAt(new Random().nextInt(0, CHARS.length())));
        }
        return sb.toString();
    }
}
