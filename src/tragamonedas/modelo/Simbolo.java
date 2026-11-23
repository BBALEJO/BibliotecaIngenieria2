
package tragamonedas.modelo;

public abstract class Simbolo {

    // Atributos
    protected String nombre;
    protected int multiplicadorBase;

    // Constructor
    public Simbolo(String nombre, int multiplicadorBase) {
        this.nombre = nombre;
        this.multiplicadorBase = multiplicadorBase;
    }

    // Obtener nombre
    public String getNombre() {
        return nombre;
    }

    // Calcular premio
    public abstract int calcularPremio(int apuesta);
}
