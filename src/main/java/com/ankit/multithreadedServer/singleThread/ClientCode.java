package com.ankit.multithreadedServer.singleThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientCode {
    public void runClient() {
        int port = 8010;
        try {
            InetAddress address = InetAddress.getByName("localhost");
            Socket socket = new Socket(address, port);
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer.println("Kindly connect, Msz from client");

            String messaageFromServer = fromServer.readLine();
            System.out.println("Response from Server is "+messaageFromServer);
            toServer.close();
            fromServer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientCode clientCode = new ClientCode();
        try {
            clientCode.runClient();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
