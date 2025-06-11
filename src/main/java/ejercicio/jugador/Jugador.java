package ejercicio.jugador;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Jugador {
    protected int x;
    protected int y;
    private boolean superPower;
    private int superPowerDuration;
    private String icono;
    private int direction;

    public Jugador(int x, int y, String icono){
        this.x = x;
        this.y = y;
        this.icono = icono;
        this.superPower = false;
        this.superPowerDuration = 0;
        this.direction = 2;
    }

    public void countDown() {
        if (this.superPowerDuration==0){
            this.superPower= false;
        }else{
            this.superPowerDuration--;
        }
    }
}

