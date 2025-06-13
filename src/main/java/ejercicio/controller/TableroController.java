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

}

