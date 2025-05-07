package org.example.jugador;
//import java.util.Timer;


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
        this.direction = 2; // 1 es arriba - 2 derecha - 3 abajo - 4 izquierda
    }

    public void countDown() {
        if (this.superPowerDuration==0){
            this.superPower= false;
        }else{
            this.superPowerDuration--;
        }
    }
    public int getX() {
        return x;
    }

    public boolean isSuperPower() {
        return superPower;
    }

    public String getIcono() {
        return icono;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSuperPower(boolean superPower) {
        this.superPower = superPower;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setSuperPowerDuration(int superPowerDuration) {
        this.superPowerDuration = superPowerDuration;
    }

    public int getY() {
        return y;
    }

}
