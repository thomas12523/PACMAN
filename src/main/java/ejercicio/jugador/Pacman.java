package ejercicio.jugador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Pacman extends Jugador {
    private static Pacman instance;
    private int score;


    private Pacman(){
        super(15, 9, "P");
        this.score=0;
    }


    public static Pacman getInstance() {
        if (instance==null){
            instance = new Pacman();
        }
        return instance;
    }

}
