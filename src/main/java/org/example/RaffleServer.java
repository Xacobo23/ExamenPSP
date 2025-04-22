package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class RaffleServer {
    public static final int MAX_OF_TICKETS = 10;
    public static final int NUM_CHARS = 6;
    private static final int PORT = 55555;

    private Ticket winner;

    private static final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        RaffleServer raffleServer = new RaffleServer();
        raffleServer.begin(PORT);
    }
    public void begin(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening");
            while (true) {
                new Thread(new RaffleServerWorker(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add(Ticket ticket){
        synchronized (tickets){
            if(tickets.size() < MAX_OF_TICKETS) tickets.add(ticket);
        }
    }
    public static List<Ticket> getList(){
        synchronized (tickets){
            return tickets;
        }
    }
}