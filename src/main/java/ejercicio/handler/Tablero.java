package ejercicio.handler;


import ejercicio.enums.GameEventType;
import ejercicio.observer.GameEvent;
import ejercicio.observer.GameEventManager;
import lombok.Getter;
import ejercicio.jugador.Jugador;
import ejercicio.jugador.Pacman;
import ejercicio.jugador.Fantasma;

@Getter
public final class Tablero {
    private static Tablero instance;
    private final int[][] tablero;
    private int cantidadPellets;
    public Pacman pacman;
    private Fantasma fantasma;


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
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero[i].length; j++) {
                if (pacman.getX() == i && pacman.getY() == j) {
                    System.out.print(pacman.getIcono() + " ");
                } else if (fantasma.getX() == i && fantasma.getY() == j) {
                    System.out.print(fantasma.getIcono() + " ");

                }
                else {
                    // * pellet clasico - + superPoder pacman - ^ superPoder fantasma
                    switch (tablero[i][j]) {
                        case 0 -> System.out.print("  ");
                        case 1 -> System.out.print("▒ ");
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

    public void checkPelletPlayer(Jugador player, int x, int y){
        if (player instanceof Pacman){
            if (this.tablero[x][y]==2){
                GameEventManager.notify(new GameEvent(GameEventType.PUNTAJE,player));
                this.cantidadPellets--;
                this.tablero[x][y]=0;

            } else if (this.tablero[x][y]==3) {
                this.pacman.setSuperPower(true);
                GameEventManager.notify(new GameEvent(GameEventType.SUPERPODER_ACTIVADO,player));
                this.cantidadPellets--;
                this.tablero[x][y]=0;
                this.fantasma.setIcono("V");
                this.fantasma.setVulnerable(true);
            }

        }else if (player instanceof Fantasma){
            if (this.tablero[x][y]==4) {
                GameEventManager.notify(new GameEvent(GameEventType.SUPERPODER_ACTIVADO,player));
                this.tablero[x][y]=0;
            }
        }
    }
    private void random(){
        int i = 0;
        while (i <10){
            int x_min = this.pacman.getX()-1;
            int x_max = this.pacman.getX()+1;
            int random_x = (int)(Math.random() * (x_max - x_min + 1)) + x_min;
            int y_min = this.pacman.getY()-1;
            int y_max = this.pacman.getY()+1;
            int random_y = (int)(Math.random() * (y_max - y_min + 1)) + y_min;

            if (random_x>=0 && random_x<this.tablero.length && random_y>=0 && random_y<this.tablero[0].length && random_x != this.pacman.getX() && random_y!=this.pacman.getY()){
                if (validPosition(random_x,random_y)){
                    this.fantasma.setX(random_x);
                    this.fantasma.setY(random_y);
                    break;
                }
            }
            i++;

        }
    }
    public void timer(Jugador player){
        if (this.pacman == player){
            if (this.pacman.isSuperPower()){
                this.pacman.countDown();
            }else{
                GameEventManager.notify(new GameEvent(GameEventType.TIME_POWER,player));
            }
        }else{
            if (this.fantasma.isSuperPower()){
                this.fantasma.countDown();
                if (this.fantasma.getSuperPowerDuration()%25==0){
                    random();
                }
            }
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

}