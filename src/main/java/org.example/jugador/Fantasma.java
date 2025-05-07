package org.example.jugador;

public final class Fantasma extends Jugador {
    private boolean vulnerable;
    private static Fantasma instance;

    private Fantasma(){
        super(9,9,"F");
    }


    public static Fantasma getInstance() {
        if (instance==null){
            instance = new Fantasma();
        }
        return instance;
    }


    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

}
