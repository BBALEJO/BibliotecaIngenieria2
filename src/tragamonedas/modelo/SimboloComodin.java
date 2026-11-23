
package tragamonedas.modelo;

public class SimboloComodin extends Simbolo {

    // Constructor
    public SimboloComodin(String nombre, int multiplicadorBase) {
        super(nombre, multiplicadorBase);
    }

    // Calcular premio
    @Override
    public int calcularPremio(int apuesta) {
        return apuesta * multiplicadorBase * 2;
    }
}
