
package tragamonedas.modelo;

import tragamonedas.interfaces.Giratorio;
import java.util.ArrayList;
import java.util.Random;

public class Rodillo implements Giratorio {

    // Atributos
    private ArrayList<Simbolo> listaSimbolos;
    private Simbolo simboloVisible;
    private Random aleatorio;

    // Constructor
    public Rodillo() {
        listaSimbolos = new ArrayList<>();
        aleatorio = new Random();
    }

    // Agregar simbolos
    public void agregarSimbolo(Simbolo simbolo) {
        listaSimbolos.add(simbolo);
    }

    // Girar rodillo
    @Override
    public void girar() {

        if (listaSimbolos.isEmpty()) {
            simboloVisible = null;
            return;
        }

        int posicion = aleatorio.nextInt(listaSimbolos.size());

        simboloVisible = listaSimbolos.get(posicion);
    }

    // Obtener simbolo visible
    public Simbolo getSimboloVisible() {
        return simboloVisible;
    }
}
