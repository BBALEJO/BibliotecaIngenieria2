
package tragamonedas.modelo;

import tragamonedas.persistencia.ArchivoJugador;
import java.util.ArrayList;

public class MaquinaTragamonedas {

    // Atributos
    private ArrayList<Rodillo> rodillos;
    private Jugador jugadorActual;
    private String mensajeResultado;
    private ArchivoJugador archivoJugador;
    private int apuestaActual;

    // Constructor
    public MaquinaTragamonedas(Jugador jugador) {
        jugadorActual = jugador;
        rodillos = new ArrayList<>();
        mensajeResultado = "Bienvenido al tragamonedas.";
        archivoJugador = new ArchivoJugador();
        apuestaActual = 0;

        for (int i = 0; i < 3; i++) {
            rodillos.add(new Rodillo());
        }

        configurarRodillos();
    }

    // Configurar simbolos
    private void configurarRodillos() {
        for (Rodillo rodillo : rodillos) {
            rodillo.agregarSimbolo(new SimboloNormal("Cereza", 2));
            rodillo.agregarSimbolo(new SimboloNormal("Campana", 3));
            rodillo.agregarSimbolo(new SimboloNormal("Estrella", 4));
            rodillo.agregarSimbolo(new SimboloComodin("Comodin", 5));
        }
    }

    // Obtener rodillos
    public ArrayList<Rodillo> getRodillos() {
        return rodillos;
    }

    // Obtener jugador actual
    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    // Ejecutar una jugada
    public void jugar(int apuesta) {

        if (!jugadorActual.apostar(apuesta)) {
            mensajeResultado = "Saldo insuficiente o apuesta no valida.";
            return;
        }

        apuestaActual = apuesta;

        for (Rodillo rodillo : rodillos) {
            rodillo.girar();
        }

        evaluarCombinacion();
    }

    // Evaluar combinacion
    public void evaluarCombinacion() {

        Simbolo primero = rodillos.get(0).getSimboloVisible();
        Simbolo segundo = rodillos.get(1).getSimboloVisible();
        Simbolo tercero = rodillos.get(2).getSimboloVisible();

        if (primero != null && segundo != null && tercero != null
                && primero.getNombre().equals(segundo.getNombre())
                && segundo.getNombre().equals(tercero.getNombre())) {

            int premio = primero.calcularPremio(apuestaActual);
            jugadorActual.recibirPremio(premio);

            mensajeResultado = "¡GANASTE! Premio: $" + premio;

        } else {
            mensajeResultado = "No hubo premio. ¡Intentalo nuevamente!";
        }
    }

    // Obtener mensaje
    public String getMensajeResultado() {
        return mensajeResultado;
    }

    // Guardar partida
    public void guardarPartida() {
        archivoJugador.guardarJugador(jugadorActual);
        mensajeResultado = "Partida guardada correctamente.";
    }

    // Cargar partida
    public void cargarPartida() {

        Jugador jugadorGuardado = archivoJugador.leerJugador();

        if (jugadorGuardado != null) {
            jugadorActual = jugadorGuardado;
            mensajeResultado = "Partida cargada correctamente.";
        } else {
            mensajeResultado = "No se pudo cargar la partida.";
        }
    }
}


