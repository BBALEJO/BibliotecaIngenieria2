
package tragamonedas.modelo;

public class SimboloNormal extends Simbolo {

    // Constructor
    public SimboloNormal(String nombre, int multiplicadorBase) {
        super(nombre, multiplicadorBase);
    }

    // Calcular premio
    @Override
    public int calcularPremio(int apuesta) {
        return apuesta * multiplicadorBase;
    }
}
