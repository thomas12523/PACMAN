package ejercicio.interfaz;

import ejercicio.observer.GameEvent;

public interface GameEventListener {
    void onEvent(GameEvent evento);
}
