package ejercicio.observer;

import ejercicio.enums.GameEventType;
import ejercicio.interfaz.GameEventListener;
import ejercicio.jugador.Fantasma;
import ejercicio.jugador.Pacman;

public class TimePowerListener implements GameEventListener {
    @Override
    public void onEvent(GameEvent event){
        if (event.getType() == GameEventType.TIME_POWER && event.getData() instanceof Pacman){
            if (!Pacman.getInstance().isSuperPower()){
                Fantasma.getInstance().setIcono("F");
                Fantasma.getInstance().setVulnerable(false);
            }
        }
    }
}
