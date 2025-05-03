package org.example;

public class Tablero {
    int[][] tablero;
    int cantidadPellets;
    Pacman pacman;
    Fantasma fantasma;

    public Tablero() {
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
                {1,1,1,1,2,2,2,0,0,0,0,0,2,2,2,1,1,1,1},
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
        this.cantidadPellets = 167;
        this.pacman = new Pacman();
        this.fantasma = new Fantasma();
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

    // (Todo el resto de Tablero.java queda igual, no lo toco)


    public boolean validPosition(int x, int y){
        return (this.tablero[x][y]!=1);
    }
    public boolean isEqualToZero(){
        if (this.cantidadPellets==0){
            return true;
        }
        return false;
    }
    public void checkPelletPacman(int x, int y){
        if (this.tablero[x][y]==2){
            this.pacman.setScore(this.pacman.getScore()+100); // el +100 podría ser otro numero como score.
            this.cantidadPellets--;
            this.tablero[x][y]=0;



        } else if (this.tablero[x][y]==3) {
            this.pacman.setSuperPower(true);
            this.pacman.setSuperPowerDuration(100); // son 10 segundos, modificarlo adelante.
            this.pacman.setVelocity(2); // aumenta la velocidad, lo regulamos cuando veamos GUI.
            this.cantidadPellets--;
            this.tablero[x][y]=0;
            this.fantasma.setIcono("V");
        }
        if (this.pacman.isSuperPower()){
            this.pacman.countDown(); // recordar usar tiempo luego, esto te setea por turno...
        }else{
            this.fantasma.setIcono("F");
        }
    }
    public void checkPelletFantasma(int x, int y){
        if (this.tablero[x][y]==4) {
            this.fantasma.setSuperPower(true);
            this.fantasma.setSuperPowerDuration(10); // son 10 segundos, modificarlo adelante.
            this.fantasma.setVelocity(2); // aumenta la velocidad, lo regulamos cuando veamos GUI.
            this.cantidadPellets--;
            this.tablero[x][y]=0;
        }
        if (this.fantasma.isSuperPower()){
            this.fantasma.countDown(); // recordar usar tiempo luego, esto te setea por turno...
        }
    }
    public void checkCollision(){ // El output no deberia ser un void, pero como no lo vimos aun toca renegar con esto.
        if ((this.pacman.getX() == this.fantasma.getX()) && (this.pacman.getY() == this.fantasma.getY())){

            if (this.pacman.isSuperPower()){
                System.out.println("PACMAN WIN");
                System.out.println("Score obtained: " + this.pacman.getScore());
            }else{
                System.out.println("FANTASMA WIN");
            }
        }
    }
    public int getCantidadPellets() {
        return cantidadPellets;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Fantasma getFantasma() {
        return fantasma;
    }

}