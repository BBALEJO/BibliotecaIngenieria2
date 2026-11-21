package com.unilibre.biblioteca.ui;

import com.unilibre.biblioteca.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PanelMultas extends JPanel {
    private final BibliotecaDatos datos;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID multa", "Préstamo", "Usuario", "Libro", "Días de retraso", "Valor (COP)", "Estado"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private final CalculadoraMulta calculadora = new CalculadoraMulta(new MultaEstandar(1000));

    public PanelMultas(BibliotecaDatos datos) {
        this.datos = datos;
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(245, 245, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Gestión de multas");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        add(titulo, BorderLayout.NORTH);

        tabla.setRowHeight(25);
        tabla.setAutoCreateRowSorter(true);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Detectar retrasos / Actualizar");
        JButton btnPagar = new JButton("Registrar pago de multa");
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(btnActualizar);
        acciones.add(btnPagar);
        add(acciones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> actualizarMultas());
        btnPagar.addActionListener(e -> registrarPago());
        actualizarMultas();
    }

    public void refrescar() {
        actualizarMultas();
    }

    private void actualizarMultas() {
        // Genera una multa por cada préstamo devuelto tarde o que ya esté vencido.
        for (Prestamo prestamo : datos.getPrestamos()) {
            LocalDate fechaCorte;
            if (prestamo.getFechaDevolucion() != null) {
                fechaCorte = prestamo.getFechaDevolucion();
            } else {
                fechaCorte = LocalDate.now();
            }

            long dias = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), fechaCorte);
            if (dias <= 0) continue;

            boolean yaExiste = datos.getMultas().stream()
                    .anyMatch(m -> m.getPrestamo().getId().equals(prestamo.getId()));
            if (!yaExiste) {
                Multa multa = new Multa("M-" + prestamo.getId(), prestamo, (int) dias);
                multa.setValor(calculadora.calcular((int) dias));
                datos.getMultas().add(multa);
            }
        }

        modelo.setRowCount(0);
        for (Multa multa : datos.getMultas()) {
            Prestamo p = multa.getPrestamo();
            modelo.addRow(new Object[]{multa.getId(), p.getId(), p.getUsuario().getNombre(),
                    p.getLibro().getTitulo(), multa.getDiasRetraso(),
                    String.format("$%,.0f", multa.getValor()), multa.isPagada() ? "PAGADA" : "PENDIENTE"});
        }
    }

    private void registrarPago() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una multa de la tabla.");
            return;
        }
        int filaModelo = tabla.convertRowIndexToModel(fila);
        String id = modelo.getValueAt(filaModelo, 0).toString();
        Multa multa = datos.getMultas().stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
        if (multa == null) return;
        if (multa.isPagada()) {
            JOptionPane.showMessageDialog(this, "Esta multa ya está pagada.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Confirmas el pago de " + String.format("$%,.0f COP", multa.getValor()) + "?",
                "Confirmar pago", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            multa.registrarPago();
            actualizarMultas();
            JOptionPane.showMessageDialog(this, "Pago registrado correctamente.");
        }
    }
}
