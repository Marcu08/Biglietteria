package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket mioSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String username = null;

    

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
            PrintWriter out = new PrintWriter(mioSocket.getOutputStream(), true);
            out.println("Benvenuto nella biglietteria");

            boolean continua = true;
            while (continua) {
                String linea = in.readLine();
                if (linea == null) {
                    continua = false; 
                } else {
                    continua = gestisciComando(linea.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Errore con un client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // niente, il socket era gia' chiuso
            }
            System.out.println("Connessione chiusa");
        }
    }

    
    private boolean gestisciComando(String linea) {
        String[] parti = linea.split(" ", 2);
        String comando = parti[0].toUpperCase();

        switch (comando) {

            case "LOGIN":
                if (parti.length < 2 || parti[1].isEmpty()) {
                    out.println("ERR SYNTAX");
                } else {
                    username = parti[1];
                    System.out.println("Login: " + username);
                    out.println("OK");
                }
                break;

            case "N":
                if (username == null) {
                    out.println("ERR LOGINREQUIRED");
                } else {
                    out.println(BiglietteriaServer.getDisponibilita());
                }
                break;

            case "BUY":
                if (username == null) {
                    out.println("ERR LOGINREQUIRED");
                    break;
                }
                try {
                    String richiesta = in.readLine();
                    String[] dati = richiesta.trim().split(" ", 2);

                    if (dati.length < 2) {
                        out.println("ERR SYNTAX");
                        break;
                    }

                    String tipo = dati[0];
                    int quantita = Integer.parseInt(dati[1]);

                    if (!BiglietteriaServer.tipoValido(tipo)) {
                        out.println("ERR UNKNOWNTYPE");
                    } else if (BiglietteriaServer.acquista(tipo, quantita)) {
                        out.println("OK");
                    } else {
                        out.println("KO");
                    }
                } catch (NumberFormatException ex) {
                    out.println("ERR SYNTAX");
                }
            break;

            case "QUIT":
                out.println("BYE");
                return false;

            default:
                out.println("ERR UNKNOWNCMD");
        }

        return true;
    }
}
