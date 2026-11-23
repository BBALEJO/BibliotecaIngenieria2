
package tragamonedas.modelo;

public class Jugador {

    // Atributos
    private String nombre;
    private int saldo;

    // Constructor
    public Jugador(String nombre, int saldo) {
        this.nombre = nombre;
        this.saldo = saldo;
    }

    // Obtener nombre
    public String getNombre() {
        return nombre;
    }

    // Obtener saldo
    public int getSaldo() {
        return saldo;
    }

    // Realizar apuesta
    public boolean apostar(int cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            return true;
        }
        return false;
    }

    // Recibir premio
    public void recibirPremio(int cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
        }
    }
}

