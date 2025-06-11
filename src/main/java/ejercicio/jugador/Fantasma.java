package ejercicio.jugador;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class Fantasma extends Jugador {
    private static Fantasma instance;
    private boolean vulnerable;


    private Fantasma(){
        super(9,9,"F");
    }


    public static Fantasma getInstance() {
        if (instance==null){
            instance = new Fantasma();
        }
        return instance;
    }

}
