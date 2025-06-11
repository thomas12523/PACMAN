package ejercicio.controller;


import ejercicio.handler.MoverJugadorHandler;
import ejercicio.jugador.Jugador;

public final class MoverJugadorController {
    private static MoverJugadorController instance;
    private MoverJugadorHandler moverJugadorHandler;

    private MoverJugadorController (){
        this.moverJugadorHandler = MoverJugadorHandler.getInstance();
    }

    public static MoverJugadorController getInstance(){
        if (instance == null){
            instance = new MoverJugadorController();
        }
        return instance;
    }

    public void movilizar(Jugador player){
        this.moverJugadorHandler.movilizar(player);
    }
}

