package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class pruebas {
    // Lista compartida por todos los hilos
    private static List<String> listaCompartida = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        int puerto = 1234;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                Socket socketCliente = serverSocket.accept();
                // Cada cliente es manejado en un nuevo hilo
                new Thread(new ManejadorCliente(socketCliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clase interna que maneja a cada cliente
    static class ManejadorCliente implements Runnable {
        private Socket socket;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("Recibido: " + mensaje);
                    // Se agrega a la lista compartida
                    listaCompartida.add(mensaje);

                    // Puedes mostrar el contenido actual de la lista
                    System.out.println("Lista actual: " + listaCompartida);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
