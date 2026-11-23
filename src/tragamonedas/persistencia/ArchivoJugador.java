
package tragamonedas.persistencia;

import tragamonedas.modelo.Jugador;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ArchivoJugador {

    // Guardar la informacion del jugador
    public void guardarJugador(Jugador jugador) {

        try {
            PrintWriter archivo = new PrintWriter(
                    new FileWriter("jugador.txt"));

            archivo.println(jugador.getNombre());
            archivo.println(jugador.getSaldo());

            archivo.close();

        } catch (IOException e) {
            System.out.println("Error al guardar el jugador.");
        }
    }

    // Leer la informacion del jugador
    public Jugador leerJugador() {

        try {
            BufferedReader archivo = new BufferedReader(
                    new FileReader("jugador.txt"));

            String nombre = archivo.readLine();
            String saldoTexto = archivo.readLine();

            archivo.close();

            int saldo = Integer.parseInt(saldoTexto);

            return new Jugador(nombre, saldo);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el jugador.");
            return null;
        }
    }
}



