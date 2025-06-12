package ejercicio.view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Top10Swing extends JFrame {

    private static final String ARCHIVO = "puntajes.txt";

    public Top10Swing() {
        setTitle("Top 10 Puntajes");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        List<String> top10 = obtenerTop10();
        if (top10.isEmpty()) {
            textArea.setText("No hay puntajes registrados.");
        } else {
            StringBuilder sb = new StringBuilder("--- TOP 10 ---\n\n");
            int pos = 1;
            for (String linea : top10) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    sb.append(pos).append(". ").append(partes[0]).append(" - ").append(partes[1]).append("\n");
                    pos++;
                }
            }
            textArea.setText(sb.toString());
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.addActionListener(e -> {
            this.dispose();
            new MenuPrincipalSwing();
        });
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnVolver);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private List<String> obtenerTop10() {
        List<String> puntajes = new ArrayList<>();
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return puntajes;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null && puntajes.size() < 10) {
                puntajes.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntajes;
    }

    public static void mostrarTop() {
        SwingUtilities.invokeLater(Top10Swing::new);
    }
}
