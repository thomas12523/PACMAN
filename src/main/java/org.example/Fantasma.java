package org.example;

public class Fantasma extends Jugador{
    private boolean vulnerable;
    private static Fantasma instance;

    private Fantasma(){
        super(9,9,0,"F");
    }


    public static Fantasma getInstance() {
        if (instance==null){
            instance = new Fantasma();
        }
        return instance;
    }


    public void up() {
        this.setX(getX() - 1);
        this.setDirection(1);
    }

    public void right() {
        this.setY(getY() + 1);
        this.setDirection(2);
    }

    public void down() {
        this.setX(getX() + 1);
        this.setDirection(3);
    }

    public void left() {
        this.setY(getY() - 1);
        this.setDirection(4);
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }
}
