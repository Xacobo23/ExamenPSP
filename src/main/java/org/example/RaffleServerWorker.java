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
                out.println("Welcome to ClemenRaffle service.");
                while ((inputLine = in.readLine()) != null) {

        /*
        CASE de CLIENT
         */
                    if (inputLine.split(" ").length==2 && inputLine.split(" ")[0].equalsIgnoreCase("CLIENT")){
                        out.println(inputLine.split(" ")[1]);
                    }
                    switch (inputLine.toUpperCase()){
                        case "CLIENT" -> {
                            out.println("falta o nombre");
                        }
                        case "BUY" -> {
                            out.println("dixeches buy");
                        }
                        case "CALC" -> {
                            out.println("calculo");
                        }
                        case "QUIT" -> {
                            out.println("I1F-A£3?&Et4F~*8HAzcAsl3C8P£2`*");
                            in.close();
                            out.close();
                            clientSocket.close();
                        }
                        default -> out.println("Error");

                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }
