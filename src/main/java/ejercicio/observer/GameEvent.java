package ejercicio.observer;

import ejercicio.enums.GameEventType;
import lombok.Getter;

@Getter
public class GameEvent {
    private final GameEventType type;
    private final Object data;

    public GameEvent(GameEventType type, Object data){
        this.type = type;
        this.data = data;
    }


}
