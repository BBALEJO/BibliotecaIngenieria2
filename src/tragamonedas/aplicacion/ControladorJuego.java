
package tragamonedas.aplicacion;

import tragamonedas.modelo.MaquinaTragamonedas;

public class ControladorJuego {

    // Atributo
    private MaquinaTragamonedas maquina;

    // Constructor
    public ControladorJuego(MaquinaTragamonedas maquina) {
        this.maquina = maquina;
    }

    // Ejecutar una jugada
    public void jugar(int apuesta) {
        maquina.jugar(apuesta);
    }

    // Obtener la maquina
    public MaquinaTragamonedas obtenerMaquina() {
        return maquina;
    }

    // Obtener el resultado de la jugada
    public String obtenerMensajeResultado() {
        return maquina.getMensajeResultado();
    }
}
