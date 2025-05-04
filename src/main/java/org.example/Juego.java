package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Juego extends JFrame implements KeyListener {
    private boolean isRunning;
    private Tablero tablero;
    private boolean player1Up, player1Down, player1Left, player1Right;
    private boolean player2Up, player2Down, player2Left, player2Right;

    public Juego() {
        this.tablero = new Tablero();
        this.isRunning = true;
        addKeyListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void jugar() {
        while (this.isRunning) {
            // Imprimir el tablero actualizado
            this.tablero.printTablero();

            // Mostrar puntaje actual
            System.out.println("Puntaje de Pacman: " + tablero.getPacman().getScore());

            // Movimiento del Pacman (Jugador 1)
            if (player1Up && tablero.validPosition(tablero.getPacman().getX() - 1, tablero.getPacman().getY())) {
                tablero.getPacman().arriba();
            } else if (player1Down && tablero.validPosition(tablero.getPacman().getX() + 1, tablero.getPacman().getY())) {
                tablero.getPacman().abajo();
            } else if (player1Left && tablero.validPosition(tablero.getPacman().getX(), tablero.getPacman().getY() - 1)) {
                tablero.getPacman().izquierda();
            } else if (player1Right && tablero.validPosition(tablero.getPacman().getX(), tablero.getPacman().getY() + 1)) {
                tablero.getPacman().derecha();
            }

            // Condicional para transportar Pacman
            if (tablero.getPacman().getX() == 9 && tablero.getPacman().getY() == 0) {
                tablero.getPacman().setX(9);
                tablero.getPacman().setY(17);
            } else if (tablero.getPacman().getX() == 9 && tablero.getPacman().getY() == 18) {
                tablero.getPacman().setX(9);
                tablero.getPacman().setY(1);
            }

            // Checkear si Pacman comió algo
            tablero.checkPelletPacman(tablero.getPacman().getX(), tablero.getPacman().getY());

            // Movimiento del Fantasma (Jugador 2)
            if (player2Up && tablero.validPosition(tablero.getFantasma().getX() - 1, tablero.getFantasma().getY())) {
                tablero.getFantasma().up();
            } else if (player2Down && tablero.validPosition(tablero.getFantasma().getX() + 1, tablero.getFantasma().getY())) {
                tablero.getFantasma().down();
            } else if (player2Left && tablero.validPosition(tablero.getFantasma().getX(), tablero.getFantasma().getY() - 1)) {
                tablero.getFantasma().left();
            } else if (player2Right && tablero.validPosition(tablero.getFantasma().getX(), tablero.getFantasma().getY() + 1)) {
                tablero.getFantasma().right();
            }

            // Condicional para transportar Fantasma
            if (tablero.getFantasma().getX() == 9 && tablero.getFantasma().getY() == 0) {
                tablero.getFantasma().setX(9);
                tablero.getFantasma().setY(17);
            } else if (tablero.getFantasma().getX() == 9 && tablero.getFantasma().getY() == 18) {
                tablero.getFantasma().setX(9);
                tablero.getFantasma().setY(1);
            }

            // Checkear si el Fantasma comió algo
            tablero.checkPelletFantasma(tablero.getFantasma().getX(), tablero.getFantasma().getY());


            // Si chocan, termina el juego
            if (tablero.isEqualToZero()){
                this.isRunning=false;
                System.out.println("PACMAN WIN");
            }
            // Checkear colisión Pacman vs Fantasma
            if (tablero.checkCollision()) {
                if (this.tablero.pacman.isSuperPower()){
                    System.out.println("PACMAN WIN");
                    System.out.println("Score obtained: " + this.tablero.pacman.getScore());
                }else{
                    System.out.println("FANTASMA WIN");
                }

            }

            // Pausa para que no se mueva muy rápido
            try {
                Thread.sleep(80); // 0.8 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("¡Juego terminado!");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> player1Up = true;
            case KeyEvent.VK_S -> player1Down = true;
            case KeyEvent.VK_A -> player1Left = true;
            case KeyEvent.VK_D -> player1Right = true;
            case KeyEvent.VK_I -> player2Up = true;
            case KeyEvent.VK_K -> player2Down = true;
            case KeyEvent.VK_J -> player2Left = true;
            case KeyEvent.VK_L -> player2Right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> player1Up = false;
            case KeyEvent.VK_S -> player1Down = false;
            case KeyEvent.VK_A -> player1Left = false;
            case KeyEvent.VK_D -> player1Right = false;
            case KeyEvent.VK_I -> player2Up = false;
            case KeyEvent.VK_K -> player2Down = false;
            case KeyEvent.VK_J -> player2Left = false;
            case KeyEvent.VK_L -> player2Right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se necesita implementar este método para este caso
    }

}
