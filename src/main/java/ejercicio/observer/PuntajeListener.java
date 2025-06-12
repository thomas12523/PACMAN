package ejercicio.observer;

import ejercicio.enums.GameEventType;
import ejercicio.interfaz.GameEventListener;
import ejercicio.jugador.Pacman;

public class PuntajeListener implements GameEventListener {
    @Override
    public void onEvent(GameEvent event){
        if (event.getType() == GameEventType.PUNTAJE && event.getData() instanceof Pacman pacman){
            pacman.setScore(pacman.getScore()+(int) (Math.random()*500)+100);

        }
    }
}
