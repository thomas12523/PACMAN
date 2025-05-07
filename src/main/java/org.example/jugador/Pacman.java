package org.example.jugador;

public final class Pacman extends Jugador {
    private int score;
    private static Pacman instance;

    private Pacman(){
        super(15, 9, "P");
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


}
