package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class RaffleServer {
    private static final int MAX_OF_TICKETS = 10;
    private static final int NUM_CHARS = 6;
    private static final int PORT = 55555;

    private Ticket winner;

    private List<Ticket> tickets = new ArrayList<>();

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
}