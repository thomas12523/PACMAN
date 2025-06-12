package ejercicio.observer;

import ejercicio.interfaz.GameEventListener;

import java.util.ArrayList;
import java.util.List;

public class GameEventManager {
    private static final List<GameEventListener> listeners = new ArrayList<>();

    public static void subscribe(GameEventListener listener) {
        listeners.add(listener);
    }

    public static void notify(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
