package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class RaffleClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private static final String QUIT_MESSAGE = "I1F-A£3?&Et4F~*8HAzcAsl3C8P£2`*";

    public static void main(String[] args) throws IOException {
        RaffleClient raffleClient = new RaffleClient();
        raffleClient.startConnection(InetAddress.getLocalHost().getHostAddress(), 55555);
        raffleClient.communicate();
        raffleClient.stopConnection();
    }

    public void communicate() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String inputLine;
        String cap;

        System.out.println(this.sendMessage(""));

        while (scanner.hasNextLine()) {
            inputLine = scanner.nextLine();

            cap = this.sendMessage(inputLine);
            if (cap == null) {
                System.out.println("Received null response from server.");
            } else if (QUIT_MESSAGE.equals(cap)) {
                break;
            } else {
                System.out.println(cap);
            }
        }
    }

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error when initializing connection: " + e.getMessage());
        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (Exception e) {
            System.err.println("Error when sending message: " + e.getMessage());
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error when closing connection: " + e.getMessage());
        }
    }
}
