package org.example;
//import java.util.Timer;


public class Jugador {
    protected int x;
    protected int y;
    private int velocity;
    private boolean superPower;
    private int superPowerDuration;
    private String icono;
    private int direction;

    public Jugador(int x, int y, int velocity, String icono){
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.icono = icono;
        this.superPower = false;
        this.superPowerDuration = 0;
        this.direction = 0; // 1 es arriba - 2 derecha - 3 abajo - 4 izquierda
    }


    public int getX() {
        return x;
    }

    public int getVelocity() {
        return velocity;
    }

    public boolean isSuperPower() {
        return superPower;
    }

    public int getSuperPowerDuration() {
        return superPowerDuration;
    }

    public String getIcono() {
        return icono;
    }

    public int getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setSuperPower(boolean superPower) {
        this.superPower = superPower;
    }

    public void countDown() {
        if (this.superPowerDuration==0){
            this.superPower= false;
        }else{
            this.superPowerDuration--;
        }
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
