package org.example.controller;
import org.example.Tablero;
import org.example.jugador.Jugador;

public class TableroController {
    private Tablero tablero;
    private static TableroController instance;

    private TableroController() {
        this.tablero = Tablero.getInstance();
    }
    public static TableroController getInstance(){
        if (instance == null){
            instance = new TableroController();
        }
        return instance;
    }

    public void timer(Jugador player){
        this.tablero.timer(player);
    }
    public void moverPlayer(Jugador player,int x,int y){
        if (player == this.tablero.getPacman()){
            if (this.tablero.validPosition(x,y)){
                this.tablero.getPacman().setX(x);
                this.tablero.getPacman().setY(y);
                this.tablero.checkPelletPlayer(this.tablero.getPacman(),x, y);
            }

        }else{
            if (this.tablero.validPosition(x,y)){
                this.tablero.getFantasma().setX(x);
                this.tablero.getFantasma().setY(y);
                this.tablero.checkPelletPlayer(this.tablero.getFantasma(),x, y);
            }

        }
    }

    public void mostrarTablero() {
        this.tablero.printTablero();
    }
    public boolean checkCollision(){
        return this.tablero.checkCollision();

    }
    public boolean juegoTerminado() {

        return tablero.getCantidadPellets()==0;
    }

    public Tablero getTablero() {
        return tablero;
    }
    public void transportar(Jugador player){
        this.getTablero().transportar(player);
    }

    public void setearDirection(int direction,Jugador player) {
        switch (direction){
            case 1 -> player.setDirection(1);
            case 2 -> player.setDirection(2);
            case 3 -> player.setDirection(3);
            case 4 -> player.setDirection(4);
        }
    }
}
