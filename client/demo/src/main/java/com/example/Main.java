package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s = new Socket("127.0.0.1", 3000);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        
        System.out.println("Server dice: " + in.readLine());
        
        out.println("LOGIN");
        System.out.println("Server dice: " + in.readLine()); 
        out.println("StudenteTest");
        System.out.println("Server dice: " + in.readLine()); 
        
        out.println("N");
        System.out.println("Biglietti disponibili: " + in.readLine());
        out.println("QUIT");
        System.out.println("Server dice: " + in.readLine());         
    }
}
