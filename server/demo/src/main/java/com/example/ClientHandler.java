package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket mioSocket;
    private BiglietteriaServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String username = null;

    public ClientHandler(Socket socket, BiglietteriaServer server) {
        this.mioSocket = socket;
        this.server = server;
    }

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
                mioSocket.close();
            } catch (IOException e) {
                
            }
            System.out.println("Connessione chiusa");
        }
    }

    private boolean gestisciComando(String comando) {
        
        comando = comando.toUpperCase();

        switch (comando) {

            case "LOGIN":
                try {
                    out.println("Inserisci il tuo username:");
                    username = in.readLine();
                    System.out.println("Login effettuato da: " + username);
                    out.println("OK");
                } catch (IOException e) {
                    out.println("ERR LOGIN");
                }
                break;

            case "N":
                if (username == null) {
                    out.println("ERR LOGINREQUIRED");
                } else {
                    System.out.println("Richiesta di disponibilita' da parte di: " + username);
                    out.println(server.getDisponibilita());
                }
                break;

            case "BUY":
                if (username == null) {
                    out.println("ERR LOGINREQUIRED");
                    break;
                }
                
                try {
                    
                    out.println("Inserisci il tipo di biglietto (Gold/Pit/Parterre):");
                    String tipo = in.readLine();
                    
                    out.println("Inserisci la quantita':");
                    int quantita = Integer.parseInt(in.readLine());

                    
                    if (!server.tipoValido(tipo)) {
                        out.println("ERR UNKNOWNTYPE");
                    } else if (server.acquista(tipo, quantita)) {
                        out.println("OK - Acquisto riuscito!");
                    } else {
                        out.println("KO - Biglietti insufficienti o input errato");
                    }
                } catch (IOException | NumberFormatException ex) {
                    out.println("ERR SYNTAX");
                }
                break;

            case "QUIT":
                out.println("BYE");
                return false; 

            default:
                out.println("ERR COMANDO NON VALIDO. Comandi validi: LOGIN, N, BUY, QUIT");
        }

        return true;     }
}
