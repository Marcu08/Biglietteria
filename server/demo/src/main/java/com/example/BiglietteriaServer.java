package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class BiglietteriaServer {

    public static final int PORTA = 3000;


    private static Map<String, Integer> disponibilita = new HashMap<>();

    static {
        disponibilita.put("Gold", 10);
        disponibilita.put("Pit", 30);
        disponibilita.put("Parterre", 60);
    }

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORTA)) {
            System.out.println("Server avviato sulla porta " + PORTA);

            while (true) {
                Socket socket = server.accept();
                System.out.println("Nuova connessione da: " + socket.getRemoteSocketAddress());
                // un thread per ogni client
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Errore del server: " + e.getMessage());
        }
    }

   
    public static synchronized String getDisponibilita() {
        StringBuilder sb = new StringBuilder("AVAIL");
        for (Map.Entry<String, Integer> e : disponibilita.entrySet()) {
            sb.append(" ").append(e.getKey()).append(":").append(e.getValue());
        }
        return sb.toString();
    }

    
    public static synchronized boolean acquista(String tipo, int quantita) {
        Integer disponibile = disponibilita.get(tipo);
        if (disponibile == null || quantita <= 0 || disponibile < quantita) {
            return false;
        }
        disponibilita.put(tipo, disponibile - quantita);
        return true;
    }

   
    public static synchronized boolean tipoValido(String tipo) {
        return disponibilita.containsKey(tipo);
    }
}
