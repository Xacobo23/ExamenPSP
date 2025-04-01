package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RaffleServerWorker implements Runnable{

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public RaffleServerWorker(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                out.println(inputLine.toUpperCase());
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
