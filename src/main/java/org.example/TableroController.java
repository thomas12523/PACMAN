package org.example;

public class TableroController {
    private Tablero tablero;
    private static TableroController instance;

    private TableroController() {
        this.tablero = Tablero.getInstance();
    }
    public static TableroController getInstance(){
        if (instance == null){
            instance = new TableroController();
        }
        return instance;
    }
    public void moverPacman(int x, int y) {
        if (tablero.validPosition(x, y)) {
            tablero.getPacman().setX(x);
            tablero.getPacman().setY(y);
            tablero.checkPelletPacman(x, y);
            if (tablero.checkCollision()) {
                System.out.println("¡Pacman ha sido atrapado por el fantasma!");
            }
        }
    }

    public void moverFantasma(int x, int y) {
        if (tablero.validPosition(x, y)) {
            tablero.getFantasma().setX(x);
            tablero.getFantasma().setY(y);
            tablero.checkPelletFantasma(x, y);
            if (tablero.checkCollision()) {
                System.out.println("¡El fantasma ha atrapado a Pacman!");
            }
        }
    }

    public void mostrarTablero() {
        tablero.printTablero();
    }

    public boolean juegoTerminado() {
        return tablero.isEqualToZero();
    }

    public Tablero getTablero() {
        return tablero;
    }
}
