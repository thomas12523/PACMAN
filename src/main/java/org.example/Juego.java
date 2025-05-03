package org.example;

import java.util.Scanner;
import java.io.IOException;

public class Juego {
    private boolean isRunning;
    private Tablero tablero;

    public Juego() {
        this.tablero = new Tablero();
        this.isRunning = true;
    }

    public void jugar() {
        // Cambiar la página de códigos a UTF-8
        try {
            Runtime.getRuntime().exec("cmd /c chcp 65001");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        while (this.isRunning) {
            // Imprimir el tablero actualizado
            this.tablero.printTablero();

            // Mostrar puntaje actual
            System.out.println("Puntaje de Pacman: " + tablero.getPacman().getScore());

            // Movimiento del Pacman (Jugador 1)
            System.out.print("Mover Pacman (WASD): ");
            char movePacman = scanner.next().charAt(0);

            if (movePacman == 'w' && tablero.validPosition(tablero.getPacman().getX() - 1, tablero.getPacman().getY())) {
                tablero.getPacman().arriba();
            } else if (movePacman == 's' && tablero.validPosition(tablero.getPacman().getX() + 1, tablero.getPacman().getY())) {
                tablero.getPacman().abajo();
            } else if (movePacman == 'a' && tablero.validPosition(tablero.getPacman().getX(), tablero.getPacman().getY() - 1)) {
                tablero.getPacman().izquierda();
            } else if (movePacman == 'd' && tablero.validPosition(tablero.getPacman().getX(), tablero.getPacman().getY() + 1)) {
                tablero.getPacman().derecha();
            }

            // Checkear si Pacman comió algo
            tablero.checkPelletPacman(tablero.getPacman().getX(), tablero.getPacman().getY());

            // Movimiento del Fantasma (Jugador 2)
            System.out.print("Mover Fantasma (IJKL): ");
            char moveFantasma = scanner.next().charAt(0);

            if (moveFantasma == 'i' && tablero.validPosition(tablero.getFantasma().getX() - 1, tablero.getFantasma().getY())) {
                tablero.getFantasma().up();
            } else if (moveFantasma == 'k' && tablero.validPosition(tablero.getFantasma().getX() + 1, tablero.getFantasma().getY())) {
                tablero.getFantasma().down();
            } else if (moveFantasma == 'j' && tablero.validPosition(tablero.getFantasma().getX(), tablero.getFantasma().getY() - 1)) {
                tablero.getFantasma().left();
            } else if (moveFantasma == 'l' && tablero.validPosition(tablero.getFantasma().getX(), tablero.getFantasma().getY() + 1)) {
                tablero.getFantasma().right();
            }

            // Checkear si el Fantasma comió algo
            tablero.checkPelletFantasma(tablero.getFantasma().getX(), tablero.getFantasma().getY());

            // Checkear colisión Pacman vs Fantasma
            tablero.checkCollision();

            // Si chocan, termina el juego
            if (tablero.getPacman().getX() == tablero.getFantasma().getX() &&
                    tablero.getPacman().getY() == tablero.getFantasma().getY()) {
                this.isRunning = false;
            }

            // Pausa para que no se mueva muy rápido
            try {
                Thread.sleep(500); // 0.5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("¡Juego terminado!");
        scanner.close();
    }
}
