package ejercicio.view;

import ejercicio.controller.*;
import ejercicio.enums.MenuItem;
import ejercicio.observer.*;


import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewConsole  {
    private final TableroController tableroController;
    private boolean isRunning;
    private final MoverJugadorController moverJugadorController;
    private final PuntajeFileController puntajeFileController;
    private MenuItem menuItem;
    private final Scanner scanner = new Scanner(System.in);

    public ViewConsole() {
        this.puntajeFileController = PuntajeFileController.getInstance();
        this.tableroController = TableroController.getInstance();
        this.isRunning = true;
        this.moverJugadorController = MoverJugadorController.getInstance();
        GameEventManager.subscribe(new PuntajeListener());
        GameEventManager.subscribe(new SuperPoderListener());
        GameEventManager.subscribe(new TimePowerListener());
    }

    public void jugar() {
        while (this.isRunning) {

            // printear tablero
            tableroController.mostrarTablero();
            System.out.println("Puntaje de Pacman: " + tableroController.getTablero().getPacman().getScore());

            // Movimiento del Pacman (Jugador 1)
            this.moverJugadorController.movilizar(tableroController.getTablero().getPacman());

            // Transportar Pacman por túnel
            tableroController.transportar(tableroController.getTablero().getPacman());

            // Movimiento del Fantasma (Jugador 2)
            this.moverJugadorController.movilizar(tableroController.getTablero().getFantasma());

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
        String name_Pacman = this.playerPacman();
        this.puntajeFileController.guardarPuntaje(name_Pacman,this.tableroController.getTablero().getPacman().getScore());
        this.Menu();
    }

    public void Menu(){
        System.out.println("\n" +
                " /$$$$$$$   /$$$$$$   /$$$$$$  /$$      /$$  /$$$$$$  /$$   /$$\n" +
                "| $$__  $$ /$$__  $$ /$$__  $$| $$$    /$$$ /$$__  $$| $$$ | $$\n" +
                "| $$  \\ $$| $$  \\ $$| $$  \\__/| $$$$  /$$$$| $$  \\ $$| $$$$| $$\n" +
                "| $$$$$$$/| $$$$$$$$| $$      | $$ $$/$$ $$| $$$$$$$$| $$ $$ $$\n" +
                "| $$____/ | $$__  $$| $$      | $$  $$$| $$| $$__  $$| $$  $$$$\n" +
                "| $$      | $$  | $$| $$    $$| $$\\  $ | $$| $$  | $$| $$\\  $$$\n" +
                "| $$      | $$  | $$|  $$$$$$/| $$ \\/  | $$| $$  | $$| $$ \\  $$\n" +
                "|__/      |__/  |__/ \\______/ |__/     |__/|__/  |__/|__/  \\__/\n" +
                "                                                               \n" +
                "                                                               \n" +
                "                                                               \n");

        this.menuItem = menuItems();

        if (MenuItem.RUN_GAME == this.menuItem){
            this.jugar();
        }else if(MenuItem.TOP_10_PACMAN == this.menuItem){
            this.puntajeFileController.mostrarTop();
            System.out.println("\n");
            this.Menu();
        }else if (MenuItem.Exit == this.menuItem){
            System.exit(0);
        }

    }

    private MenuItem menuItems() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Elija una opción:\n[0] Exit\n[1] Jugar\n[2] Top 10");

            try {
                int opcion = scanner.nextInt();

                if (opcion >= 0 && opcion <= 2) {
                    return MenuItem.getFromIndex(opcion);
                } else {
                    System.out.println("Opción inválida. Intente nuevamente.\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Debe ingresar un número (0, 1 o 2).\n");
                scanner.nextLine();
            }
        }
    }


    private String playerPacman(){

        System.out.println("Pacman introduzca un nombre: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()){
            System.out.println("No dejar el campo null.");
            return this.playerPacman();
        }
        return name;
    }

}

