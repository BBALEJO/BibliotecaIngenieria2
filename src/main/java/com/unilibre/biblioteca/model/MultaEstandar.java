
package com.unilibre.biblioteca.model;

public class MultaEstandar implements PoliticaMulta {

    private double valorPorDia;

    public MultaEstandar(double valorPorDia) {
        this.valorPorDia = valorPorDia;
    }

    @Override
    public double calcularMulta(int diasRetraso) {

        if (diasRetraso <= 0) {
            return 0;
        }

        return diasRetraso * valorPorDia;
    }
}
