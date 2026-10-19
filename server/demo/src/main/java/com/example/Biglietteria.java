package main.java.com.example;

import java.util.HashMap;

public class Biglietteria {
    private HashMap<String, Integer> disponibilita;

    public Biglietteria() {
        disponibilita = new HashMap<String, Integer>();
    }

    public void inizializzaDisponibilita() {
        disponibilita.put("Gold", 10);
        disponibilita.put("Pit", 30);
        disponibilita.put("Parterre", 60);
    }

    public HashMap<String, Integer> getDisponibilita() {
        return disponibilita;
    }

    public synchronized void acquista(String tipo, int quantita) {
        if (disponibilita.containsKey(tipo)) {
            int disponibilitaAttuale = disponibilita.get(tipo);
            if (disponibilitaAttuale >= quantita) {
                disponibilita.put(tipo, disponibilitaAttuale - quantita);
            } else {
                throw new IllegalArgumentException("Quantità richiesta superiore alla disponibilità.");
            }
        } else {
            throw new IllegalArgumentException("Tipo di biglietto non valido.");
        }
    }
}
