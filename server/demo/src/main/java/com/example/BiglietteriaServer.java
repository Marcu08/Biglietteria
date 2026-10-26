package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class BiglietteriaServer {

    private Map<String, Integer> disponibilita = new HashMap<>();

    
    public BiglietteriaServer() {
        disponibilita.put("Gold", 100);
        disponibilita.put("Pit", 200);
        disponibilita.put("Parterre", 300);
    }

    public static void main(String[] args) {

        
        try (ServerSocket server = new ServerSocket(3000)) {
            System.out.println("Server avviato sulla porta " + 3000);

            while (true) {
                Socket socket = server.accept();
                System.out.println("Nuova connessione da: " + socket.getRemoteSocketAddress());
                // un thread per ogni client
                new ClientHandler().start();
            }
        } catch (IOException e) {
            System.out.println("Errore del server: " + e.getMessage());
        }
    }

   
    public synchronized String getDisponibilita() {
        String risultato = "AVAIL";
        for (String tipo : disponibilita.keySet()) {
        int quantita = disponibilita.get(tipo);
        
        risultato = risultato + " " + tipo + ":" + quantita;
        }
    
        return risultato;
    }

    
    public synchronized boolean acquista(String tipo, int quantita) {
        Integer disponibile = disponibilita.get(tipo);
        if (disponibile == null || quantita <= 0 || disponibile < quantita) {
            return false;
        }
        disponibilita.put(tipo, disponibile - quantita);
        return true;
    }

   
    public synchronized boolean tipoValido(String tipo) {
        return disponibilita.containsKey(tipo);
    }
}
