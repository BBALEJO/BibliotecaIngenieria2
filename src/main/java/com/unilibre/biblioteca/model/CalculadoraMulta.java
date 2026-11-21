
package com.unilibre.biblioteca.model;

public class CalculadoraMulta {

    private PoliticaMulta politica;

    public CalculadoraMulta(PoliticaMulta politica) {
        this.politica = politica;
    }

    public double calcular(int diasRetraso) {
        return politica.calcularMulta(diasRetraso);
    }

    public void cambiarPolitica(PoliticaMulta politica) {
        this.politica = politica;
    }
}
