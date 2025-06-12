package ejercicio.view;

import ejercicio.controller.PuntajeFileController;
import ejercicio.controller.TableroController;
import ejercicio.controller.MoverJugadorController;
import ejercicio.handler.MoverJugadorHandler;
import ejercicio.jugador.Fantasma;
import ejercicio.jugador.Jugador;
import ejercicio.jugador.Pacman;
import ejercicio.observer.GameEventManager;
import ejercicio.observer.PuntajeListener;
import ejercicio.observer.SuperPoderListener;
import ejercicio.observer.TimePowerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewSwing extends JFrame implements KeyListener {
    private final TableroController tableroController;
    private final MoverJugadorController moverJugadorController;
    private final PuntajeFileController puntajeFileController;
    private final JPanel panelTablero;
    private final JLabel labelPuntaje;
    private boolean isRunning = false;
    private final Image pacmanImg = new ImageIcon("src/main/java/ejercicio/images/pacman.png").getImage();
    private final Image fantasmaImg = new ImageIcon("src/main/java/ejercicio/images/fantasma.png").getImage();
    private final Image fantasmaVulnerableImg = new ImageIcon("src/main/java/ejercicio/images/fantasmaVulnerable.png").getImage();
    private final Image muroImg = new ImageIcon("src/main/java/ejercicio/images/muro.jpg").getImage();
    private final Image pelletImg = new ImageIcon("src/main/java/ejercicio/images/pellet.png").getImage();
    private final Image powerPacman = new ImageIcon("src/main/java/ejercicio/images/superPowerPacman.jpg").getImage();
    private final Image powerFantasma = new ImageIcon("src/main/java/ejercicio/images/superPowerFantasma.png").getImage();

    public ViewSwing() {
        this.tableroController = TableroController.getInstance();
        this.moverJugadorController = MoverJugadorController.getInstance();
        this.puntajeFileController = PuntajeFileController.getInstance();
        GameEventManager.subscribe(new SuperPoderListener());
        GameEventManager.subscribe(new PuntajeListener());
        GameEventManager.subscribe(new TimePowerListener());

        setTitle("Pacman - Swing Edition");
        setSize(800, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        addKeyListener(this);
        setFocusable(true);

        labelPuntaje = new JLabel("Puntaje: 0");
        labelPuntaje.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelPuntaje, BorderLayout.NORTH);

        panelTablero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTablero(g);
            }
        };
        panelTablero.setBackground(Color.BLACK);
        add(panelTablero, BorderLayout.CENTER);

        setVisible(true);

        startGame();  // Iniciar el juego automáticamente al abrir esta ventana
    }

    private void startGame() {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            while (isRunning) {
                moverJugadorController.movilizar(tableroController.getTablero().getPacman());
                moverJugadorController.movilizar(tableroController.getTablero().getFantasma());

                tableroController.transportar(tableroController.getTablero().getPacman());
                tableroController.transportar(tableroController.getTablero().getFantasma());

                tableroController.timer(tableroController.getTablero().getPacman());
                tableroController.timer(tableroController.getTablero().getFantasma());

                if (tableroController.checkCollision()) {
                    isRunning = false;
                    JOptionPane.showMessageDialog(this,
                            tableroController.getTablero().getPacman().isSuperPower() ?
                                    "¡Pacman atrapó al fantasma!" : "¡Pacman fue atrapado!",
                            "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                }

                if (tableroController.juegoTerminado()) {
                    isRunning = false;
                    JOptionPane.showMessageDialog(this, "¡PACMAN WIN!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                }

                labelPuntaje.setText("Puntaje: " + tableroController.getTablero().getPacman().getScore());
                panelTablero.repaint();

                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                puntajeFileController.guardarPuntaje(nombre.trim(), tableroController.getTablero().getPacman().getScore());
            }
            this.dispose();
            new MenuPrincipalSwing();
            // Aquí podés agregar guardar puntaje o reiniciar juego si querés.
        }).start();
    }

    private void drawTablero(Graphics g) {
        int[][] mapa = tableroController.getTablero().getTablero();
        int cellSize = panelTablero.getWidth() / mapa[0].length;

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                switch (mapa[i][j]) {
                    case 1 -> g.drawImage(muroImg, x, y, cellSize, cellSize, null);
                    case 2 -> g.drawImage(pelletImg, x, y, cellSize, cellSize, null);
                    case 3 -> g.drawImage(powerPacman, x, y, cellSize, cellSize, null);
                    case 4 -> g.drawImage(powerFantasma, x, y, cellSize, cellSize, null);
                    default -> {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }
            }
        }

        drawJugador(g, tableroController.getTablero().getPacman(), cellSize);
        drawJugador(g, tableroController.getTablero().getFantasma(), cellSize);
    }

    private void drawJugador(Graphics g, Jugador jugador, int cellSize) {
        int x = jugador.getY() * cellSize;
        int y = jugador.getX() * cellSize;

        if (jugador instanceof Pacman) {
            g.drawImage(pacmanImg, x, y, cellSize, cellSize, null);
        } else if (jugador instanceof Fantasma) {
            Image imgFantasma = Pacman.getInstance().isSuperPower() ? fantasmaVulnerableImg : fantasmaImg;
            g.drawImage(imgFantasma, x, y, cellSize, cellSize, null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
                 KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L -> {
                MoverJugadorHandler.getInstance().keyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        MoverJugadorHandler.getInstance().keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
