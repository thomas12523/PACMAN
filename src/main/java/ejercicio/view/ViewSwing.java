package ejercicio.view;

import ejercicio.controller.PuntajeFileController;
import ejercicio.controller.TableroController;
import ejercicio.controller.MoverJugadorController;
import ejercicio.jugador.Fantasma;
import ejercicio.jugador.Jugador;
import ejercicio.jugador.Pacman;
import ejercicio.observer.GameEventManager;
import ejercicio.observer.PuntajeListener;
import ejercicio.observer.SuperPoderListener;
import ejercicio.observer.TimePowerListener;

import javax.swing.*;
import java.awt.*;

public final class ViewSwing extends JFrame {
    private final TableroController tableroController;
    private final MoverJugadorController moverJugadorController;
    private final PuntajeFileController puntajeFileController;
    private final JPanel panelTablero;
    private final JLabel labelPuntaje;
    private boolean isRunning;
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
        this.isRunning = false;
        GameEventManager.subscribe(new SuperPoderListener());
        GameEventManager.subscribe(new PuntajeListener());
        GameEventManager.subscribe(new TimePowerListener());

        setTitle("Pacman - Swing Edition");
        setSize(800, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setFocusable(true);

        this.labelPuntaje = new JLabel("Puntaje: 0");
        this.labelPuntaje.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelPuntaje, BorderLayout.NORTH);

        this.panelTablero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTablero(g);
            }
        };
        this.panelTablero.setBackground(Color.BLACK);
        add(this.panelTablero, BorderLayout.CENTER);

        setVisible(true);

        this.startGame();  // Iniciar el juego automáticamente al abrir esta ventana
    }

    private void startGame() {
        if (isRunning) return;
        this.isRunning = true;

        new Thread(() -> {
            while (isRunning) {
                this.moverJugadorController.movilizar(this.tableroController.getTablero().getPacman());
                this.moverJugadorController.movilizar(this.tableroController.getTablero().getFantasma());

                this.tableroController.transportar(this.tableroController.getTablero().getPacman());
                this.tableroController.transportar(this.tableroController.getTablero().getFantasma());

                this.tableroController.timer(this.tableroController.getTablero().getPacman());
                this.tableroController.timer(this.tableroController.getTablero().getFantasma());

                if (this.tableroController.checkCollision()) {
                    isRunning = false;
                    JOptionPane.showMessageDialog(this,
                            tableroController.getTablero().getPacman().isSuperPower() ?
                                    "¡Pacman atrapó al fantasma!" : "¡Pacman fue atrapado!",
                            "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                }

                if (this.tableroController.juegoTerminado()) {
                    isRunning = false;
                    JOptionPane.showMessageDialog(this, "¡PACMAN WIN!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                }

                this.labelPuntaje.setText("Puntaje: " + tableroController.getTablero().getPacman().getScore());
                this.panelTablero.repaint();

                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String nombre = JOptionPane.showInputDialog(this, "Pacman ingrese su nombre:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                this.puntajeFileController.guardarPuntaje(nombre.trim(), this.tableroController.getTablero().getPacman().getScore());
            }
            this.dispose();
            new MenuPrincipalSwing();

        }).start();
    }

    private void drawTablero(Graphics g) {
        int[][] mapa = this.tableroController.getTablero().getTablero();
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

        drawJugador(g, this.tableroController.getTablero().getPacman(), cellSize);
        drawJugador(g, this.tableroController.getTablero().getFantasma(), cellSize);
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


}
