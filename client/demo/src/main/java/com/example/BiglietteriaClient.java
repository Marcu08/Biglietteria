package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BiglietteriaClient {

    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);

        try {
            Socket socket = new Socket("localhost", 3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(in.readLine());

            boolean loginOk = false;
            while (!loginOk) {
                System.out.print("Username: ");
                out.println("LOGIN " + tastiera.nextLine());
                String risposta = in.readLine();
                System.out.println(risposta);
                if (risposta.equals("OK")) {
                    loginOk = true;
                }
            }

            boolean connesso = true;
            while (connesso) {
                System.out.println();
                System.out.println("1) Vedi disponibilita' (N)");
                System.out.println("2) Compra biglietti (BUY)");
                System.out.println("3) Esci (QUIT)");
                System.out.print("Scelta: ");

                String scelta = tastiera.nextLine().trim();

                switch (scelta) {
                    case "1":
                        out.println("N");
                        System.out.println(in.readLine());
                        break;

                    case "2":
                        out.println("BUY");
                        System.out.print("Tipo (Gold/Pit/Parterre): ");
                        String tipo = tastiera.nextLine().trim();
                        System.out.print("Quantita': ");
                        String quantita = tastiera.nextLine().trim();
                        out.println(tipo + " " + quantita);
                        String risposta = in.readLine();
                        System.out.println(risposta);
                        break;

                    case "3":
                        out.println("QUIT");
                        System.out.println(in.readLine());
                        connesso = false;
                        break;

                    default:
                        System.out.println("Scelta non valida");
                }
            }
        } catch (IOException e) {
            System.out.println("Impossibile connettersi al server: " + e.getMessage());
        }
    }
}
