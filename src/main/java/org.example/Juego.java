package org.example;

import org.example.controller.TableroController;
import org.example.jugador.Jugador;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Juego extends JFrame implements KeyListener {
    private final TableroController tableroController;
    private boolean isRunning;
    private boolean player1Up, player1Down, player1Left, player1Right;
    private boolean player2Up, player2Down, player2Left, player2Right;

    public Juego() {
        this.tableroController = TableroController.getInstance();
        this.isRunning = true;

        // configuracion de hacer invisible el GUI y que funcione keyListener
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
            accionDelPlayer(tableroController.getTablero().getPacman(),player1Up,player1Down,player1Left,player1Right);

            // Transportar Pacman por túnel
            tableroController.transportar(tableroController.getTablero().getPacman());

            // Movimiento del Fantasma (Jugador 2)
            accionDelPlayer(tableroController.getTablero().getFantasma(),player2Up,player2Down,player2Left,player2Right);

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
    private void accionDelPlayer(Jugador jugador,boolean up, boolean down, boolean left, boolean right){
        int x = jugador.getX();
        int y = jugador.getY();

        if (up) {
            tableroController.moverPlayer(jugador, x - 1, y);
            tableroController.setearDirection(1, jugador);
        } else if (down) {
            tableroController.moverPlayer(jugador, x + 1, y);
            tableroController.setearDirection(3, jugador);
        } else if (left) {
            tableroController.moverPlayer(jugador, x, y - 1);
            tableroController.setearDirection(4, jugador);
        } else if (right) {
            tableroController.moverPlayer(jugador, x, y + 1);
            tableroController.setearDirection(2, jugador);
        }

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
