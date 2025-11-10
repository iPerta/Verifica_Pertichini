package it.pertichini;

import java.io.PrintWriter;

public class MessageThread implements Runnable {
    private String command;
    private String user;
    private PrintWriter out;

    public MessageThread(String command, String user, PrintWriter out) {
        this.command = command;
        this.user = user;
        this.out = out;
    }

    @Override
    public void run() {
        try {

            if (command.startsWith("ADD")) {
                String testo = command.substring(4).trim();
                if (testo.isEmpty()) {
                    out.println("ERR SYNTAX");
                } else {
                    Main.addMessage(user, testo, out);
                }
            }

            else if (command.startsWith("LIST")) {
                Main.listMessages(out);
            }

            else if (command.startsWith("DEL")) {
                try {
                    int id = Integer.parseInt(command.substring(4).trim());
                    Main.deleteMessage(id, user, out);
                } catch (NumberFormatException e) {
                    out.println("ERR SYNTAX");
                }
            }

            else {
                out.println("ERR UNKNOWNCMD");
            }
        } catch (Exception e) {
            out.println("ERR UNKNOWNERROR");
        }
    }
}