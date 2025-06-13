package ejercicio.handler;


import lombok.Getter;
import ejercicio.jugador.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

@Getter
public final class MoverJugadorHandler extends JFrame implements KeyListener {
    private static MoverJugadorHandler instance;
    private boolean player1Up, player1Down, player1Left, player1Right;
    private boolean player2Up, player2Down, player2Left, player2Right;
    private final Tablero tablero;

    private MoverJugadorHandler(){
        // configuracion de hacer invisible el GUI y que funcione keyListener
        setUndecorated(true);
        setSize(0, 0);
        setLocationRelativeTo(null);
        setFocusable(true);
        addKeyListener(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tablero = Tablero.getInstance();
    }
    public static MoverJugadorHandler getInstance() {

        if (instance ==null){
            instance = new MoverJugadorHandler();
        }
        return instance;
    }

    public void movilizar(Jugador player){
        if (player instanceof Pacman){
            accionDelPlayer(this.tablero.getPacman(), player1Up, player1Down, player1Left, player1Right);
        }else if (player instanceof Fantasma){
            accionDelPlayer(this.tablero.getFantasma(), player2Up, player2Down, player2Left, player2Right);
        }

    }

    private void moverPlayer(Jugador player,int x,int y){
        if (player instanceof Pacman){
            if (this.tablero.validPosition(x,y)){
                this.tablero.getPacman().setX(x);
                this.tablero.getPacman().setY(y);
                this.tablero.checkPelletPlayer(this.tablero.getPacman(),x, y);
            }

        }else if(player instanceof Fantasma){
            if (this.tablero.validPosition(x,y)){
                this.tablero.getFantasma().setX(x);
                this.tablero.getFantasma().setY(y);
                this.tablero.checkPelletPlayer(this.tablero.getFantasma(),x, y);
            }

        }
    }

    private void accionDelPlayer(Jugador jugador,boolean up, boolean down, boolean left, boolean right){
        int x = jugador.getX();
        int y = jugador.getY();

        if (up) {
            moverPlayer(jugador, x - 1, y);
        } else if (down) {
            moverPlayer(jugador, x + 1, y);
        } else if (left) {
            moverPlayer(jugador, x, y - 1);
        } else if (right) {
            moverPlayer(jugador, x, y + 1);
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
