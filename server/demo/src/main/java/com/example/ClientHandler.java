package main.java.com.example;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Socket s = socket // Inserito qui per assicurarne la chiusura automatica alla fine
        ) {
            String input = in.readLine();
            String input2 = in.readLine();
            String operazione = in.readLine();

            if (operazione == null || input == null || input2 == null ||
                operazione.equalsIgnoreCase("exit") || input.equalsIgnoreCase("exit") || input2.equalsIgnoreCase("exit")) {
                return; 
            }

            try {
                int ope = Integer.parseInt(operazione); 
                int num1 = Integer.parseInt(input);
                int num2 = Integer.parseInt(input2);
                int risultato = 0;

                if (ope == 4 && num2 == 0) { 
                    out.println("ERR-0:divided_by_zero");
                } else {
                    switch (ope) {
                        case 1: 
                            risultato = num1 + num2;
                            out.println("RISULTATO:" + risultato);
                            break;
                        case 2: 
                            risultato = num1 - num2;
                            out.println("RISULTATO:" + risultato);
                            break;
                        case 3: 
                            risultato = num1 * num2;
                            out.println("RISULTATO:" + risultato);
                            break;
                        case 4: 
                            risultato = num1 / num2;
                            out.println("RISULTATO:" + risultato);
                            break;
                        default: 
                            out.println("ERR-2:unknown_operation");
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                out.println("ERR-1:invalid_operation_format");
            }   

        } catch (IOException e) {
            System.err.println("Errore di comunicazione con il client: " + e.getMessage());
        }
    }
}
