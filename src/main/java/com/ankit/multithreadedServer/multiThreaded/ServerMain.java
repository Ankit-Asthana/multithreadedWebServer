package com.ankit.multithreadedServer.multiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class ServerMain {

    //doesn't return anything, just consume and push to the socket created for every thread
    private Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Hello from Server");

                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String messageFromClient = fromClient.readLine();
                System.out.println(messageFromClient);
//                toClient.close();
//                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain();

        int port = 8012;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(100000);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(() -> serverMain.getConsumer().accept(acceptedSocket));  // "accept" -> Consumer interface function
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
