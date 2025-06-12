package ejercicio.view;

import javax.swing.*;

public class MenuPrincipalSwing extends JFrame {

    public MenuPrincipalSwing() {
        setTitle("Menú Principal - Pacman");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem itemJugar = new JMenuItem("Jugar");
        JMenuItem itemTop10 = new JMenuItem("Top 10");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemJugar.addActionListener(e -> {
            dispose(); // Cierra el menú principal
            new ViewSwing(); // Abre la ventana del juego
        });

        itemTop10.addActionListener(e -> {
            dispose();
            new Top10Swing();
        });

        itemSalir.addActionListener(e -> System.exit(0));

        menu.add(itemJugar);
        menu.add(itemSalir);
        menu.add(itemTop10);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setVisible(true);
    }

}

