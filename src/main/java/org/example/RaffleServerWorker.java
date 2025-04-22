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

        private String name = null;


        public RaffleServerWorker(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Welcome to ClemenRaffle service.");
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {

        /*
        CASE de CLIENT
         */
                    if (inputLine.split(" ").length==2 && inputLine.split(" ")[0].equalsIgnoreCase("CLIENT")){
                        if (name != null) out.println("<"+name+">- Client name is not modifiable.");
                        else {
                            name = inputLine.split(" ")[1];
                            out.println("Hello <"+name+">.");
                        }
                    }

                    else switch (inputLine.toUpperCase()){
                        case "BUY" -> {
                            if (name==null) out.println("Client name required.");
                            else {

                                if (RaffleServer.getList().size() < RaffleServer.MAX_OF_TICKETS) {
                                    Ticket ticket = new Ticket(name, RaffleServer.NUM_CHARS);
                                    RaffleServer.add(ticket);
                                    out.println("<"+name+">- <"+name+"> has got the raffle "+ticket.ticketValue);
                                }
                                else out.println("<"+name+">- List has reached the maximum");
                            }
                        }
                        case "CALC" -> {
                            if (name==null) out.println("Client name required.");
                            if (RaffleServer.getList().size()==RaffleServer.MAX_OF_TICKETS) out.println("<"+name+">- The raffle has ended.");
                            else out.println("<"+name+">- The odds are 1/"+RaffleServer.getList().size()+".");
                        }
                        case "QUIT" -> {
                            out.println("<"+name+">- Bye.");
                            in.close();
                            out.close();
                            clientSocket.close();
                        }
                        default -> out.println("<"+name+">- Unknown command.");
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }
