package com.ankit.multithreadedServer.threadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImplementation {

    int poolSize = 10;

    public Runnable createSocketAndRespond(Socket socket) {
        try {
            PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromCient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Message from Client "+fromCient.readLine());
            toClient.println("Message from Server "+socket.getInetAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        /*
        Server listeneing on 8020 port for 100000ms,
        If any connection request comes in that time, then it will create a new socket and start data transmission through that socket
         */

        ServerImplementation serverImplementation = new ServerImplementation();
        int port = 8020;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
        ExecutorService executor = Executors.newFixedThreadPool(serverImplementation.poolSize);
        while(true) {
            //here we have to accept the socket connection from opened server-socket in different socket using multithreading
            Socket acceptedSocket = serverSocket.accept();
            executor.submit(serverImplementation.createSocketAndRespond(acceptedSocket));
        }
    }
}
