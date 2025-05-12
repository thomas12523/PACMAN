package org.example;

import org.example.controller.TableroController;

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

        // configuracion de hacer invisible el GUI y que funcione
        setUndecorated(true);
        setSize(0, 0);
        setLocationRelativeTo(null);
        setFocusable(true);
        addKeyListener(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void jugar() {
        while (this.isRunning) {

            // printear tablero
            tableroController.mostrarTablero();

            System.out.println("Puntaje de Pacman: " + tableroController.getTablero().getPacman().getScore());

            // Movimiento del Pacman (Jugador 1)
            if (player1Up) {
                tableroController.moverPlayer(tableroController.getTablero().getPacman(),
                        tableroController.getTablero().getPacman().getX() - 1,
                        tableroController.getTablero().getPacman().getY());
                tableroController.setearDirection(1, tableroController.getTablero().getPacman());
            } else if (player1Down) {
                tableroController.moverPlayer(tableroController.getTablero().getPacman(),
                        tableroController.getTablero().getPacman().getX() + 1,
                        tableroController.getTablero().getPacman().getY());
                tableroController.setearDirection(3, tableroController.getTablero().getPacman());
            } else if (player1Left) {
                tableroController.moverPlayer(tableroController.getTablero().getPacman(),
                        tableroController.getTablero().getPacman().getX(),
                        tableroController.getTablero().getPacman().getY() - 1);
                tableroController.setearDirection(4, tableroController.getTablero().getPacman());
            } else if (player1Right) {
                tableroController.moverPlayer(tableroController.getTablero().getPacman(),
                        tableroController.getTablero().getPacman().getX(),
                        tableroController.getTablero().getPacman().getY() + 1);
                tableroController.setearDirection(2, tableroController.getTablero().getPacman());
            }

            // Transportar Pacman por túnel
            tableroController.transportar(tableroController.getTablero().getPacman());

            // Movimiento del Fantasma (Jugador 2)
            if (player2Up) {
                tableroController.moverPlayer(tableroController.getTablero().getFantasma(),
                        tableroController.getTablero().getFantasma().getX() - 1,
                        tableroController.getTablero().getFantasma().getY());
                tableroController.setearDirection(1, tableroController.getTablero().getFantasma());
            } else if (player2Down) {
                tableroController.moverPlayer(tableroController.getTablero().getFantasma(),
                        tableroController.getTablero().getFantasma().getX() + 1,
                        tableroController.getTablero().getFantasma().getY());
                tableroController.setearDirection(1, tableroController.getTablero().getFantasma());
            } else if (player2Left) {
                tableroController.moverPlayer(tableroController.getTablero().getFantasma(),
                        tableroController.getTablero().getFantasma().getX(),
                        tableroController.getTablero().getFantasma().getY() - 1);
                tableroController.setearDirection(1, tableroController.getTablero().getFantasma());
            } else if (player2Right) {
                tableroController.moverPlayer(tableroController.getTablero().getFantasma(),
                        tableroController.getTablero().getFantasma().getX(),
                        tableroController.getTablero().getFantasma().getY() + 1);
                tableroController.setearDirection(1, tableroController.getTablero().getFantasma());
            }
            // chequear poder y timer
            this.tableroController.timer(this.tableroController.getTablero().getPacman());
            this.tableroController.timer(this.tableroController.getTablero().getFantasma());
            // Chequear colisión
            if (tableroController.checkCollision()) {
                if (tableroController.getTablero().getPacman().isSuperPower()) {
                    System.out.println("¡Pacman atrapó al fantasma!");
                    System.out.println("¡PACMAN WIN!");
                } else {
                    System.out.println("¡Pacman fue atrapado por el fantasma!");
                    System.out.println("¡FANTASMA WIN!");
                }
                isRunning = false;
            }

            // Transportar fantasma por túnel
            tableroController.transportar(tableroController.getTablero().getFantasma());

            // Fin del juego
            if (tableroController.juegoTerminado()) {
                isRunning = false;
                System.out.println("¡PACMAN WIN!");
            }

            try {
                Thread.sleep(80);
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
        // No se usa
    }

}
