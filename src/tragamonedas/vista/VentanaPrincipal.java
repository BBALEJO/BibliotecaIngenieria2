
package tragamonedas.vista;

import tragamonedas.modelo.Jugador;
import tragamonedas.modelo.MaquinaTragamonedas;
import tragamonedas.modelo.Simbolo;
import tragamonedas.aplicacion.ControladorJuego;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.Font;

public class VentanaPrincipal extends JFrame {

    // Componentes visuales
    private JLabel simbolo1;
    private JLabel simbolo2;
    private JLabel simbolo3;
    private JLabel etiquetaSaldo;

    private JTextField campoApuesta;
    private JButton botonJugar;
    private JButton botonGuardar;
    private JButton botonCargar;

    // Objetos del juego
    private ControladorJuego controlador;

    public VentanaPrincipal() {

        Jugador jugador = new Jugador("Jugador", 100);

        MaquinaTragamonedas maquina = new MaquinaTragamonedas(jugador);
        controlador = new ControladorJuego(maquina);

        setTitle("Maquina Tragamonedas");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 3, 10, 15));

        simbolo1 = new JLabel("?");
        simbolo2 = new JLabel("?");
        simbolo3 = new JLabel("?");

        simbolo1.setHorizontalAlignment(JLabel.CENTER);
        simbolo2.setHorizontalAlignment(JLabel.CENTER);
        simbolo3.setHorizontalAlignment(JLabel.CENTER);

        simbolo1.setFont(new Font("Arial", Font.BOLD, 30));
        simbolo2.setFont(new Font("Arial", Font.BOLD, 30));
        simbolo3.setFont(new Font("Arial", Font.BOLD, 30));

        etiquetaSaldo = new JLabel("Saldo: $" + jugador.getSaldo());

        campoApuesta = new JTextField("10");
        botonJugar = new JButton("JUGAR");
        botonGuardar = new JButton("GUARDAR PARTIDA");
        botonCargar = new JButton("CARGAR PARTIDA");

        panel.add(simbolo1);
        panel.add(simbolo2);
        panel.add(simbolo3);

        panel.add(etiquetaSaldo);
        panel.add(campoApuesta);
        panel.add(botonJugar);

        panel.add(botonGuardar);
        panel.add(botonCargar);
        panel.add(new JLabel(""));

        add(panel);

        botonJugar.addActionListener(e -> realizarJugada());
        botonGuardar.addActionListener(e -> guardarPartida());
        botonCargar.addActionListener(e -> cargarPartida());
    }

    // Realizar jugada
    private void realizarJugada() {

        try {
            int apuesta = Integer.parseInt(campoApuesta.getText());

            if (apuesta <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La apuesta debe ser mayor que cero."
                );
                return;
            }

            controlador.jugar(apuesta);

            actualizarSimbolos();
            actualizarSaldo();

            JOptionPane.showMessageDialog(
                    this,
                    controlador.obtenerMensajeResultado()
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingresa una apuesta valida usando numeros enteros."
            );
        }
    }

    // Actualizar simbolos
    private void actualizarSimbolos() {

        Simbolo primero = controlador.obtenerMaquina()
                .getRodillos().get(0).getSimboloVisible();

        Simbolo segundo = controlador.obtenerMaquina()
                .getRodillos().get(1).getSimboloVisible();

        Simbolo tercero = controlador.obtenerMaquina()
                .getRodillos().get(2).getSimboloVisible();

        simbolo1.setText(primero == null ? "?" : primero.getNombre());
        simbolo2.setText(segundo == null ? "?" : segundo.getNombre());
        simbolo3.setText(tercero == null ? "?" : tercero.getNombre());
    }

    // Actualizar saldo
    private void actualizarSaldo() {
        etiquetaSaldo.setText(
                "Saldo: $" + controlador.obtenerMaquina()
                        .getJugadorActual().getSaldo()
        );
    }

    // Guardar partida
    private void guardarPartida() {
        controlador.obtenerMaquina().guardarPartida();

        JOptionPane.showMessageDialog(
                this,
                controlador.obtenerMensajeResultado()
        );
    }

    // Cargar partida
    private void cargarPartida() {
        controlador.obtenerMaquina().cargarPartida();

        actualizarSaldo();

        JOptionPane.showMessageDialog(
                this,
                controlador.obtenerMensajeResultado()
        );
    }
}






