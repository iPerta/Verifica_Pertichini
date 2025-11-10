package it.pertichini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Messaggio> messaggi = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(3000);

        while (true) {
            Socket cs = s.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter out = new PrintWriter(cs.getOutputStream(), true);

            out.println("WELCOME");

            String respond;
            String user = null;


            respond = in.readLine();
            if (respond.startsWith("LOGIN")) {
                user = respond.substring(6);
                out.println("OK");
            } else {
                out.println("ERR LOGINREQUIRED");
                cs.close();
                continue;
            }


            String command;
            while ((command = in.readLine()) != null) {
                command.toUpperCase();
                if (command.equals("QUIT")) {
                    out.println("BYE");
                    break;
                }


                Thread messThread = new Thread(new MessageThread(command, user, out));
                messThread.start();
            }

            System.out.println("Client disconnected");
            cs.close();
        }
    }

    public static synchronized void addMessage(String autore, String testo, PrintWriter out) {
        messaggi.add(new Messaggio(autore, testo));
        out.println("OK ADDED " + messaggi.get(messaggi.size() - 1).getId());
    }

    public static synchronized String listMessages(PrintWriter out) {
        if (messaggi.isEmpty()) {
            out.println("BOARD:");
            out.println("END");
            return "";
        }

        out.println("BOARD:");
        for (Messaggio m : messaggi) {
            out.println(m.toString());
        }
        out.println("END");
        return "";
    }

    public static synchronized void deleteMessage(int id, String autore, PrintWriter out) {
        boolean found = false;

        for (Messaggio m : messaggi) {
            if (m.getId() == id) {
                if (m.getAutore().equals(autore)) {
                    messaggi.remove(m);
                    out.println("OK DELETED");
                    found = true;
                    break;
                } else {
                    out.println("ERR PERMISSION");
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            out.println("ERR NOTFOUND");
        }
    }
}