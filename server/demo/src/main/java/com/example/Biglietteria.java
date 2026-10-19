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
}
