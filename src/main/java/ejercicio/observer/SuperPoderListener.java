package ejercicio.observer;

import ejercicio.enums.GameEventType;
import ejercicio.interfaz.GameEventListener;
import ejercicio.jugador.Fantasma;
import ejercicio.jugador.Pacman;

public final class SuperPoderListener  implements GameEventListener {
    @Override
    public void onEvent(GameEvent event){
        if (event.getType() == GameEventType.SUPERPODER_ACTIVADO && event.getData() instanceof Pacman){
            Pacman.getInstance().setSuperPower(true);
            Pacman.getInstance().setSuperPowerDuration(125);
        }else if(event.getType() == GameEventType.SUPERPODER_ACTIVADO && event.getData() instanceof Fantasma){
            Fantasma.getInstance().setSuperPower(true);
            Fantasma.getInstance().setSuperPowerDuration(150);
        }
    }
}
