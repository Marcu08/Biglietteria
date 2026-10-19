package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import main.java.com.example.Biglietteria;
import main.java.com.example.ClientHandler;

public class Main {
    public static void main(String[] args) {
        Biglietteria Biglietteria = new Biglietteria();
        ServerSocket ss = new ServerSocket(3000);
        Socket s = ss.accept();

        while (true) {
            Socket socket = server.accept();

            ClientHandler handler =
                new ClientHandler(socket, biglietteria);

            handler.start();
        }

    }
}