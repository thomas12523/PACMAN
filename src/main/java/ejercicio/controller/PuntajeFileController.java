package ejercicio.controller;

import ejercicio.handler.PuntajeFileHandler;

public final class PuntajeFileController {
    private static PuntajeFileController instance;
    private final PuntajeFileHandler puntajeFileHandler;

    private PuntajeFileController(){
        this.puntajeFileHandler = PuntajeFileHandler.getInstance();
    }
    public static PuntajeFileController getInstance(){
        if (instance ==null){
            instance = new PuntajeFileController();
        }
        return instance;
    }

    public void guardarPuntaje(String nombre, int puntaje){
        this.puntajeFileHandler.guardarPuntaje(nombre,puntaje);
    }

    public void mostrarTop(){
        this.puntajeFileHandler.mostrarTop();
    }
}
