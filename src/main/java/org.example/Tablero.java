package org.example;

import org.example.jugador.Fantasma;
import org.example.jugador.Jugador;
import org.example.jugador.Pacman;

public class Tablero {
    private int[][] tablero;
    private int cantidadPellets;
    public Pacman pacman;
    private Fantasma fantasma;
    private static Tablero instance;

    private Tablero(){
        int[][] tabla = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,3,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,3,1},
                {1,2,1,1,2,1,1,1,2,1,2,1,1,1,2,1,1,2,1},
                {1,2,2,2,4,2,2,2,2,2,2,2,2,2,4,2,2,2,1},
                {1,2,1,1,2,1,2,1,1,1,1,1,2,1,2,1,1,2,1},
                {1,2,2,2,2,1,2,2,2,2,2,2,2,1,2,2,2,2,1},
                {1,1,1,1,2,1,1,1,1,2,1,1,1,1,2,1,1,1,1},
                {1,1,1,1,2,1,2,2,2,2,2,2,2,1,2,1,1,1,1},
                {1,1,1,1,2,1,2,1,1,2,1,1,2,1,2,1,1,1,1},
                {0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0},
                {1,1,1,1,2,1,2,1,1,2,1,1,2,1,2,1,1,1,1},
                {1,1,1,1,2,1,2,2,2,2,2,2,2,1,2,1,1,1,1},
                {1,1,1,1,2,1,2,1,1,1,1,1,2,1,2,1,1,1,1},
                {1,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,1},
                {1,2,1,1,2,1,1,1,2,1,2,1,1,1,2,1,1,2,1},
                {1,3,2,1,4,2,2,2,2,0,2,2,2,2,4,1,2,3,1},
                {1,1,2,1,2,1,2,1,1,1,1,1,2,1,2,1,2,1,1},
                {1,2,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,2,1},
                {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        this.tablero = tabla;
        this.cantidadPellets = 170;
        this.pacman = Pacman.getInstance();
        this.fantasma = Fantasma.getInstance();
    }

    public static Tablero getInstance() {
        if (instance ==null){
            instance = new Tablero();
        }
        return instance;
    }

    public void printTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (pacman.getX() == i && pacman.getY() == j) {
                    System.out.print(pacman.getIcono() + " ");
                } else if (fantasma.getX() == i && fantasma.getY() == j) {
                    System.out.print(fantasma.getIcono() + " ");
                } else {
                    // * pellet clasico - + superPoder pacman - ^ superPoder fantasma
                    switch (tablero[i][j]) {
                        case 0 -> System.out.print("  ");
                        case 1 -> System.out.print("█ ");
                        case 2 -> System.out.print("* ");
                        case 3 -> System.out.print("+ ");
                        case 4 -> System.out.print("^ ");
                    }
                }
            }
            System.out.println();
        }
    }

    public boolean validPosition(int x, int y){
        return (this.tablero[x][y]!=1);
    }

    public boolean isEqualToZero(){
        return getCantidadPellets()==0;
    }
    public void checkPelletPacman(int x, int y){
        if (this.tablero[x][y]==2){
            this.pacman.setScore(this.pacman.getScore()+100); // el +100 podría ser otro numero como score.
            this.cantidadPellets--;
            this.tablero[x][y]=0;

        } else if (this.tablero[x][y]==3) {
            this.pacman.setSuperPower(true);
            this.pacman.setSuperPowerDuration(125); // son 10 segundos ( 10/0.08 milisegundos)
            this.cantidadPellets--;
            this.tablero[x][y]=0;
            this.fantasma.setIcono("V");
            this.fantasma.setVulnerable(true);
        }
        if (this.pacman.isSuperPower()){
            this.pacman.countDown();
        }else{
            this.fantasma.setIcono("F");
            this.fantasma.setVulnerable(false);
        }
    }
    public void checkPelletFantasma(int x, int y){
        if (this.tablero[x][y]==4) {
            this.fantasma.setSuperPower(true);
            this.fantasma.setSuperPowerDuration(10); // son 10 segundos, modificarlo adelante.
            this.tablero[x][y]=0;
        }
        if (this.fantasma.isSuperPower()){
            this.fantasma.countDown(); // recordar usar tiempo luego, esto te setea por turno...
        }
    }
    public boolean checkCollision(){
        return ((this.pacman.getX() == this.fantasma.getX()) && (this.pacman.getY() == this.fantasma.getY()));
    }
    public void transportar(Jugador player){
        if (player.getX() == 9 && player.getY() == 0) {
            player.setX(9);
            player.setY(17);
        } else if (player.getX() == 9 && player.getY() == 18) {
            player.setX(9);
            player.setY(1);
        }
    }
    private int getCantidadPellets() {
        return cantidadPellets;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Fantasma getFantasma() {
        return fantasma;
    }


}