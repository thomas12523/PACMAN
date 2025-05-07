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
                this.tableroController.moverPlayer(this.tableroController.getTablero().getPacman(), this.tableroController.getTablero().getPacman().getX() - 1, this.tableroController.getTablero().getPacman().getY());
                this.tableroController.setearDirection(1,this.tableroController.getTablero().getPacman());
            } else if (player1Down) {
                this.tableroController.moverPlayer(this.tableroController.getTablero().getPacman(),this.tableroController.getTablero().getPacman().getX() + 1, this.tableroController.getTablero().getPacman().getY());
                this.tableroController.setearDirection(3,this.tableroController.getTablero().getPacman());
            } else if (player1Left) {
                this.tableroController.moverPlayer(this.tableroController.getTablero().getPacman(),this.tableroController.getTablero().getPacman().getX(), this.tableroController.getTablero().getPacman().getY() - 1);
                this.tableroController.setearDirection(4,this.tableroController.getTablero().getPacman());
            } else if (player1Right) {
                this.tableroController.moverPlayer(this.tableroController.getTablero().getPacman(),this.tableroController.getTablero().getPacman().getX(), this.tableroController.getTablero().getPacman().getY() + 1);
                this.tableroController.setearDirection(2,this.tableroController.getTablero().getPacman());
            }

            //  transportar Pacman por tunel
            this.tableroController.transportar(this.tableroController.getTablero().getPacman());

            // Movimiento del Fantasma (Jugador 2)
            if (player2Up) {
                this.tableroController.moverPlayer(this.tableroController.getTablero().getFantasma(),this.tableroController.getTablero().getFantasma().getX() - 1, this.tableroController.getTablero().getFantasma().getY());
                this.tableroController.setearDirection(1,this.tableroController.getTablero().getFantasma());
            } else if (player2Down) {
                this.tableroController.moverPlayer(this.tableroController.getTablero().getFantasma(),this.tableroController.getTablero().getFantasma().getX() + 1, this.tableroController.getTablero().getFantasma().getY());
                this.tableroController.setearDirection(1,this.tableroController.getTablero().getFantasma());
            } else if (player2Left) {
                this.tableroController.moverPlayer(this.tableroController.getTablero().getFantasma(),this.tableroController.getTablero().getFantasma().getX(), this.tableroController.getTablero().getFantasma().getY() - 1);
                this.tableroController.setearDirection(1,this.tableroController.getTablero().getFantasma());
            } else if (player2Right) {
                this.tableroController.moverPlayer(this.tableroController.getTablero().getFantasma(),this.tableroController.getTablero().getFantasma().getX(), this.tableroController.getTablero().getFantasma().getY() + 1);
                this.tableroController.setearDirection(1,this.tableroController.getTablero().getFantasma());
            }

            // Chequear colisión Pacman vs Fantasma
            if (this.tableroController.checkCollision()){
                if (this.tableroController.getTablero().getPacman().isSuperPower()) {
                    System.out.println("¡Pacman atrapo al fantasma!");
                    System.out.println("¡PACMAN WIN!");

                }else{
                    System.out.println("¡Pacman fue atrapado por el fantasma!");
                    System.out.println("¡FANTASMA WIN!");

                }
                this.isRunning= false;
            }

            // transportar fantasma por tunel
            this.tableroController.transportar(this.tableroController.getTablero().getFantasma());


            // Si el juego ha terminado
            if (tableroController.juegoTerminado()) {
                this.isRunning = false;
                System.out.println("¡PACMAN WIN!");
            }


            // Pausa para que no se mueva muy rápido
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
        // No se necesita implementar este método para este caso
    }
}
