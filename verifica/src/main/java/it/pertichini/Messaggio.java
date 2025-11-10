package it.pertichini;

public class Messaggio {
    private static int counter = 1;
    private int id;
    private String autore;
    private String testo;

    public Messaggio(String autore, String testo) {
        this.id = counter++;
        this.autore = autore;
        this.testo = testo;
    }

    public int getId() {
        return id;
    }

    public String getAutore() {
        return autore;
    }

    public String getTesto() {
        return testo;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + autore + ": " + testo;
    }
}