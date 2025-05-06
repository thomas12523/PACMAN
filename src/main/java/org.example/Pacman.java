package org.example;

public final class Pacman extends Jugador {
    private int score;
    private static Pacman instance;

    private Pacman(){
        super(15, 9, 0, "P");
        this.score=0;
    }


    public static Pacman getInstance() {
        if (instance==null){
            instance = new Pacman();
        }
        return instance;
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public void arriba() {
        this.setX(getX() - 1);
        this.setDirection(1);
    }

    public void derecha() {
        this.setY(getY() + 1);
        this.setDirection(2);
    }

    public void abajo() {
        this.setX(getX() + 1);
        this.setDirection(3);
    }

    public void izquierda() {
        this.setY(getY() - 1);
        this.setDirection(4);
    }
}
