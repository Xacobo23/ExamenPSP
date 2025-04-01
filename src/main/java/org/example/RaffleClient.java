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

    public static void main(String[] args) throws IOException {
        RaffleClient raffleClient = new RaffleClient();
        raffleClient.startConnection(InetAddress.getLocalHost().toString(),55555);
        raffleClient.communicate();
        raffleClient.stopConnection();
    }
    public void communicate() throws IOException {
        System.out.println("Enter lines of text. Finish with .");
        Scanner scanner=new Scanner(System.in);
        String inputLine;
        String cap;
        while (scanner.hasNextLine()){
            inputLine=scanner.nextLine();
            cap=this.sendMessage(inputLine);
            System.out.println(cap);
            if (cap.equals("bye")){
                break;
            }
        }
    }
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            //LOG.debug("Error when initializing connection", e);
        }
    }
    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }
    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            //LOG.debug("error when closing", e);
        }
    }

}
