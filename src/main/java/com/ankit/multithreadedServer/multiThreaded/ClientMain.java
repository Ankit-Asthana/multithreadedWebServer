package com.ankit.multithreadedServer.multiThreaded;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMain {
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8012;
                try {
                    InetAddress inetAddress = InetAddress.getByName("localhost");
                    Socket clientSocket = new Socket(inetAddress, port);

                    PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    toServer.println("Hi Server, Client Here");
                    String message = fromServer.readLine();
                    System.out.println(message);

//                    toServer.close();
//                    fromServer.close();
//                    clientSocket.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        for(int i = 0; i < 100; i++) {
            try {
               Thread thread = new Thread(clientMain.getRunnable());
               thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
