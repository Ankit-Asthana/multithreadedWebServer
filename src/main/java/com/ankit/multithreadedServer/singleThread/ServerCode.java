package com.ankit.multithreadedServer.singleThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCode {

    public void runSocket() throws IOException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(10000);
        while (true) {
            System.out.println("Server is listening on port "+port);
            Socket acceptedConnectionSocket = socket.accept();
            /*
                PrintWriter is used to write from Server to Client
                Use socket with getOutputStream() to push the data in client
             */
            PrintWriter toClient = new PrintWriter(acceptedConnectionSocket.getOutputStream(), true);
            /*
                BufferedReader is used to get the data from Client to server, in form of bytes,
                It will comes continuously, that's why we need to use BufferedStream\
                Use InputStreamReader and pass the socket with getInputStream() to get the data
            */
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(acceptedConnectionSocket.getInputStream()));
            toClient.println("Hello from HackServer, you are hacked bitch");

            String messagefromClient = fromServer.readLine();
            System.out.println("Message from Client "+messagefromClient);
            socket.close();
            toClient.close();
            fromServer.close();
            acceptedConnectionSocket.close();
        }
    }

    public static void main(String[] args) {
        ServerCode server = new ServerCode();
        try {
            server.runSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
