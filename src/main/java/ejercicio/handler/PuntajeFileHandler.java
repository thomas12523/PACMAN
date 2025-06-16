package ejercicio.handler;


import java.io.*;
import java.util.*;

public final class PuntajeFileHandler {
    private static PuntajeFileHandler instance;
    private static final String ARCHIVO = "src/main/java/ejercicio/puntajes.txt";

    public static PuntajeFileHandler getInstance(){
        if (instance == null){
            instance = new PuntajeFileHandler();
        }
        return instance;
    }
    public  void guardarPuntaje(String nombre, int puntaje) {
        List<String> lineas = new ArrayList<>();

        // Leer líneas existentes
        File file = new File(ARCHIVO);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    lineas.add(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Agregar nueva línea
        lineas.add(nombre + "," + puntaje);

        // Ordenar de mayor a menor
        lineas.sort((a, b) -> {
            int p1 = Integer.parseInt(a.split(",")[1]);
            int p2 = Integer.parseInt(b.split(",")[1]);
            return Integer.compare(p2, p1);
        });

        // Escribir de nuevo (máximo top 10)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (int i = 0; i < Math.min(10, lineas.size()); i++) {
                writer.write(lineas.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarTop() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            System.out.println("No hay puntajes registrados.");
            return;
        }

        System.out.println("\n--- TOP 10 ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            int pos = 1;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                System.out.println(pos + ". " + partes[0] + " - " + partes[1]);
                pos++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
