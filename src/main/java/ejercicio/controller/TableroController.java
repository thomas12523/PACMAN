package ejercicio.controller;

import lombok.Getter;
import ejercicio.handler.Tablero;
import ejercicio.jugador.*;

@Getter
public final class TableroController {
    private static TableroController instance;
    private final Tablero tablero;


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

    public void mostrarTablero() {
        this.tablero.printTablero();
    }

    public boolean checkCollision(){
        return this.tablero.checkCollision();

    }
    public boolean juegoTerminado() {

        return tablero.getCantidadPellets()==0;
    }

    public void transportar(Jugador player){
        this.getTablero().transportar(player);
    }

    public void setearDirection(int direction,Jugador player) {
        switch (direction){
            // 1 es arriba - 2 derecha - 3 abajo - 4 izquierda
            case 1 -> player.setDirection(1);
            case 2 -> player.setDirection(2);
            case 3 -> player.setDirection(3);
            case 4 -> player.setDirection(4);
        }
    }
}

