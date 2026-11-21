package com.unilibre.biblioteca.model;

public class Multa {
    private final String id;
    private double valor;
    private final int diasRetraso;
    private final Prestamo prestamo;
    private boolean pagada;

    public Multa(String id, Prestamo prestamo, int diasRetraso) {
        this.id = id;
        this.prestamo = prestamo;
        this.diasRetraso = diasRetraso;
        this.valor = 0;
        this.pagada = false;
    }

    public String getId() { return id; }
    public double getValor() { return valor; }
    public int getDiasRetraso() { return diasRetraso; }
    public Prestamo getPrestamo() { return prestamo; }
    public boolean isPagada() { return pagada; }
    public void setValor(double valor) { this.valor = valor; }
    public void registrarPago() { this.pagada = true; }
}
