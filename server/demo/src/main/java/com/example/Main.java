package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ServerSocket ss = new ServerSocket(3000);
        Socket s = ss.accept();


        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        String testo = in.readLine();
}