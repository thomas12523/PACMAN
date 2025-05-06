package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Juego extends JFrame implements KeyListener {
    private boolean isRunning;
    private TableroController tableroController;
    private boolean player1Up, player1Down, player1Left, player1Right;
    private boolean player2Up, player2Down, player2Left, player2Right;

    public Juego() {
        this.tableroController = TableroController.getInstance();
        this.isRunning = true;
        addKeyListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void jugar() {
        while (this.isRunning) {
            // Imprimir el tablero actualizado
            this.tableroController.mostrarTablero();

            // Mostrar puntaje actual
            System.out.println("Puntaje de Pacman: " + tableroController.getTablero().getPacman().getScore());

            // Movimiento del Pacman (Jugador 1)
            if (player1Up) {
                this.tableroController.moverPacman(this.tableroController.getTablero().getPacman().getX() - 1, this.tableroController.getTablero().getPacman().getY());
            } else if (player1Down) {
                tableroController.moverPacman(this.tableroController.getTablero().getPacman().getX() + 1, this.tableroController.getTablero().getPacman().getY());
            } else if (player1Left) {
                this.tableroController.moverPacman(this.tableroController.getTablero().getPacman().getX(), this.tableroController.getTablero().getPacman().getY() - 1);
            } else if (player1Right) {
                this.tableroController.moverPacman(this.tableroController.getTablero().getPacman().getX(), this.tableroController.getTablero().getPacman().getY() + 1);
            }

            // Condicional para transportar Pacman
            if (this.tableroController.getTablero().getPacman().getX() == 9 && this.tableroController.getTablero().getPacman().getY() == 0) {
                this.tableroController.getTablero().getPacman().setX(9);
                this.tableroController.getTablero().getPacman().setY(17);
            } else if (this.tableroController.getTablero().getPacman().getX() == 9 && this.tableroController.getTablero().getPacman().getY() == 18) {
                this.tableroController.getTablero().getPacman().setX(9);
                this.tableroController.getTablero().getPacman().setY(1);
            }

            // Movimiento del Fantasma (Jugador 2)
            if (player2Up) {
                this.tableroController.moverFantasma(this.tableroController.getTablero().getFantasma().getX() - 1, this.tableroController.getTablero().getFantasma().getY());
            } else if (player2Down) {
                this.tableroController.moverFantasma(this.tableroController.getTablero().getFantasma().getX() + 1, this.tableroController.getTablero().getFantasma().getY());
            } else if (player2Left) {
                this.tableroController.moverFantasma(this.tableroController.getTablero().getFantasma().getX(), this.tableroController.getTablero().getFantasma().getY() - 1);
            } else if (player2Right) {
                this.tableroController.moverFantasma(this.tableroController.getTablero().getFantasma().getX(), this.tableroController.getTablero().getFantasma().getY() + 1);
            }

            // Condicional para transportar Fantasma
            if (this.tableroController.getTablero().getFantasma().getX() == 9 && this.tableroController.getTablero().getFantasma().getY() == 0) {
                this.tableroController.getTablero().getFantasma().setX(9);
                this.tableroController.getTablero().getFantasma().setY(17);
            } else if (this.tableroController.getTablero().getFantasma().getX() == 9 && this.tableroController.getTablero().getFantasma().getY() == 18) {
                this.tableroController.getTablero().getFantasma().setX(9);
                this.tableroController.getTablero().getFantasma().setY(1);
            }

            // Si el juego ha terminado
            if (tableroController.juegoTerminado()) {
                this.isRunning = false;
                System.out.println("¡PACMAN WIN!");
            }

            // Checkear colisión Pacman vs Fantasma
            if (this.tableroController.getTablero().checkCollision()) {
                if (this.tableroController.getTablero().getPacman().isSuperPower()) {
                    System.out.println("¡PACMAN WIN!");
                    System.out.println("Puntaje obtenido: " + this.tableroController.getTablero().getPacman().getScore());
                } else {
                    System.out.println("¡FANTASMA WIN!");
                }
                this.isRunning = false;
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
