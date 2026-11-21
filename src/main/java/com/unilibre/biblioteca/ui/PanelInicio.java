package com.unilibre.biblioteca.ui;

import com.unilibre.biblioteca.model.BibliotecaDatos;
import com.unilibre.biblioteca.model.Prestamo;
import com.unilibre.biblioteca.model.SolicitudEspera;

import javax.swing.*;
import java.awt.*;

/** Panel de resumen general de la biblioteca. */
public class PanelInicio extends JPanel {
    private final BibliotecaDatos datos;
    private final JLabel libros = valor();
    private final JLabel usuarios = valor();
    private final JLabel prestamosActivos = valor();
    private final JLabel multasPendientes = valor();
    private final JLabel solicitudesEspera = valor();

    public PanelInicio(BibliotecaDatos datos) {
        this.datos = datos;
        setLayout(new BorderLayout(18, 18));
        setBackground(new Color(245, 245, 240));
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JLabel encabezado = new JLabel("Resumen de la biblioteca");
        encabezado.setFont(new Font("Arial", Font.BOLD, 28));
        add(encabezado, BorderLayout.NORTH);

        JPanel tarjetas = new JPanel(new GridLayout(2, 3, 16, 16));
        tarjetas.setOpaque(false);
        tarjetas.add(tarjeta("Libros registrados", libros));
        tarjetas.add(tarjeta("Usuarios registrados", usuarios));
        tarjetas.add(tarjeta("Préstamos activos", prestamosActivos));
        tarjetas.add(tarjeta("Multas pendientes", multasPendientes));
        tarjetas.add(tarjeta("Solicitudes en espera", solicitudesEspera));

        JLabel nota = new JLabel("Sistema de gestión bibliotecaria · Ingeniería de Software II");
        nota.setForeground(new Color(95, 105, 98));
        nota.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel centro = new JPanel(new BorderLayout(10, 20));
        centro.setOpaque(false);
        centro.add(tarjetas, BorderLayout.CENTER);
        centro.add(nota, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);
        refrescar();
    }

    public void refrescar() {
        libros.setText(String.valueOf(datos.getLibros().size()));
        usuarios.setText(String.valueOf(datos.getUsuarios().size()));
        prestamosActivos.setText(String.valueOf(datos.getPrestamos().stream()
                .filter(p -> !"DEVUELTO".equals(p.getEstado().obtenerNombre())).count()));
        multasPendientes.setText(String.valueOf(datos.getMultas().stream()
                .filter(m -> !m.isPagada()).count()));
        solicitudesEspera.setText(String.valueOf(datos.getServicioListaEspera().getSolicitudes().stream()
                .filter(s -> "EN ESPERA".equals(s.getEstado())).count()));
    }

    private JLabel valor() {
        JLabel label = new JLabel("0");
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(new Color(42, 82, 65));
        return label;
    }

    private JPanel tarjeta(String titulo, JLabel valor) {
        JPanel panel = new JPanel(new BorderLayout(8, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 218)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 15));
        etiqueta.setForeground(new Color(75, 85, 78));
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(valor, BorderLayout.CENTER);
        return panel;
    }
}
