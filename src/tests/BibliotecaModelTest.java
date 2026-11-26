import com.unilibre.biblioteca.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Suite Completa de Pruebas Unitarias - Modelos y Patrones Biblioteca")
public class BibliotecaModelTest {

    // =========================================================================
    // 1. JERARQUÍA DE USUARIOS (Usuario, UsuarioRegular, Bibliotecario, Administrador)
    // =========================================================================
    @Nested
    @DisplayName("1. Jerarquía de Usuarios")
    class UsuariosTests {

        private static class UsuarioConcretoStub extends Usuario {
            public UsuarioConcretoStub(String id, String nombre, String correo, String contrasenaHash) {
                super(id, nombre, correo, contrasenaHash);
            }

            @Override
            public String obtenerRol() {
                return "ROL_PRUEBA";
            }
        }

        @Test
        @DisplayName("Usuario (abstracta): getters y toString")
        void usuarioBaseGettersYToString() {
            Usuario usuario = new UsuarioConcretoStub("U-01", "Breiner Bonilla", "breiner@unilibre.edu.co", "hash123");

            assertEquals("U-01", usuario.getId());
            assertEquals("Breiner Bonilla", usuario.getNombre());
            assertEquals("breiner@unilibre.edu.co", usuario.getCorreo());
            assertEquals("hash123", usuario.getContrasenaHash());
            assertEquals("ROL_PRUEBA", usuario.obtenerRol());
            assertEquals("Breiner Bonilla - ROL_PRUEBA", usuario.toString());
        }

        @Test
        @DisplayName("UsuarioRegular: rol y notificación en consola")
        void usuarioRegularRolYNotificacion() {
            UsuarioRegular usuario = new UsuarioRegular("UR-1", "Camila", "camila@correo.com", "abc");

            assertEquals("USUARIO_REGULAR", usuario.obtenerRol());
            assertEquals("Camila - USUARIO_REGULAR", usuario.toString());

            ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
            PrintStream salidaOriginal = System.out;
            System.setOut(new PrintStream(salidaCapturada));
            try {
                usuario.recibirNotificacion("Libro disponible");
            } finally {
                System.setOut(salidaOriginal);
            }

            assertEquals("Notificacion para Camila: Libro disponible", salidaCapturada.toString().trim());
        }

        @Test
        @DisplayName("Bibliotecario: rol BIBLIOTECARIO")
        void bibliotecarioRol() {
            Bibliotecario bib = new Bibliotecario("B-01", "Ashly", "ashly@unilibre.edu.co", "hashB");

            assertEquals("B-01", bib.getId());
            assertEquals("Ashly", bib.getNombre());
            assertEquals("ashly@unilibre.edu.co", bib.getCorreo());
            assertEquals("hashB", bib.getContrasenaHash());
            assertEquals("BIBLIOTECARIO", bib.obtenerRol());
            assertEquals("Ashly - BIBLIOTECARIO", bib.toString());
        }

        @Test
        @DisplayName("Administrador: rol ADMINISTRADOR")
        void administradorRol() {
            Administrador admin = new Administrador("A-01", "Jeferson", "jeferson@unilibre.edu.co", "hashA");

            assertEquals("A-01", admin.getId());
            assertEquals("Jeferson", admin.getNombre());
            assertEquals("jeferson@unilibre.edu.co", admin.getCorreo());
            assertEquals("hashA", admin.getContrasenaHash());
            assertEquals("ADMINISTRADOR", admin.obtenerRol());
            assertEquals("Jeferson - ADMINISTRADOR", admin.toString());
        }
    }

    // =========================================================================
    // 2. PATRÓN FACTORY METHOD (UsuarioCreator y subclases)
    // =========================================================================
    @Nested
    @DisplayName("2. Patrón Factory Method - Creadores de Usuario")
    class FactoryMethodTests {

        private static class CreatorConcretoStub extends UsuarioCreator {
            public CreatorConcretoStub(String id, String nombre, String correo, String contrasenaHash) {
                super(id, nombre, correo, contrasenaHash);
            }

            @Override
            public Usuario crearUsuario() {
                return new UsuarioRegular(getId(), getNombre(), getCorreo(), getContrasenaHash());
            }
        }

        @Test
        @DisplayName("UsuarioCreator (abstracta): getters protegidos")
        void usuarioCreatorGettersProtegidos() {
            UsuarioCreator creator = new CreatorConcretoStub("ID-9", "Laura", "laura@correo.com", "xyz");
            Usuario creado = creator.crearUsuario();

            assertNotNull(creado);
            assertEquals("ID-9", creado.getId());
            assertEquals("Laura", creado.getNombre());
            assertEquals("laura@correo.com", creado.getCorreo());
            assertEquals("xyz", creado.getContrasenaHash());
        }

        @Test
        @DisplayName("UsuarioRegularCreator: instancia UsuarioRegular")
        void usuarioRegularCreatorInstanciaCorrecta() {
            UsuarioCreator creator = new UsuarioRegularCreator("UR-10", "Sofía", "sofia@correo.com", "pass1");
            Usuario usuario = creator.crearUsuario();

            assertInstanceOf(UsuarioRegular.class, usuario);
            assertEquals("UR-10", usuario.getId());
            assertEquals("Sofía", usuario.getNombre());
            assertEquals("sofia@correo.com", usuario.getCorreo());
            assertEquals("pass1", usuario.getContrasenaHash());
            assertEquals("USUARIO_REGULAR", usuario.obtenerRol());
        }

        @Test
        @DisplayName("BibliotecarioCreator: instancia Bibliotecario")
        void bibliotecarioCreatorInstanciaCorrecta() {
            UsuarioCreator creator = new BibliotecarioCreator("B-20", "Carlos", "carlos@correo.com", "pass2");
            Usuario usuario = creator.crearUsuario();

            assertInstanceOf(Bibliotecario.class, usuario);
            assertEquals("B-20", usuario.getId());
            assertEquals("Carlos", usuario.getNombre());
            assertEquals("carlos@correo.com", usuario.getCorreo());
            assertEquals("pass2", usuario.getContrasenaHash());
            assertEquals("BIBLIOTECARIO", usuario.obtenerRol());
        }

        @Test
        @DisplayName("AdministradorCreator: instancia Administrador")
        void administradorCreatorInstanciaCorrecta() {
            UsuarioCreator creator = new AdministradorCreator("A-30", "Elena", "elena@correo.com", "pass3");
            Usuario usuario = creator.crearUsuario();

            assertInstanceOf(Administrador.class, usuario);
            assertEquals("A-30", usuario.getId());
            assertEquals("Elena", usuario.getNombre());
            assertEquals("elena@correo.com", usuario.getCorreo());
            assertEquals("pass3", usuario.getContrasenaHash());
            assertEquals("ADMINISTRADOR", usuario.obtenerRol());
        }
    }

    // =========================================================================
    // 3. LIBROS Y PATRÓN OBSERVER (Libro, ListaEspera, SolicitudEspera, ServicioListaEspera)
    // =========================================================================
    @Nested
    @DisplayName("3. Libros y Patrón Observer - Lista de Espera")
    class LibrosYObserverTests {

        @Test
        @DisplayName("Libro: inicialización, actualización de datos y disponibilidad")
        void libroMetadatosYDisponibilidad() {
            Libro libro = new Libro("L-1", "Clean Code", "Robert C. Martin", "9780132350884", 2);
            Libro agotado = new Libro("L-2", "Design Patterns", "GoF", "9780201633610", 0);

            assertEquals("L-1", libro.getId());
            assertEquals("Clean Code", libro.getTitulo());
            assertEquals("Robert C. Martin", libro.getAutor());
            assertEquals("9780132350884", libro.getIsbn());
            assertEquals(2, libro.getEjemplaresDisponibles());
            assertEquals("Clean Code (2 disponibles)", libro.toString());
            assertTrue(libro.consultarDisponibilidad());
            assertFalse(agotado.consultarDisponibilidad());

            libro.actualizarDatos("Clean Code 2da Ed", "R. Martin", "111222");
            assertEquals("Clean Code 2da Ed", libro.getTitulo());
            assertEquals("R. Martin", libro.getAutor());
            assertEquals("111222", libro.getIsbn());
            assertEquals(2, libro.getEjemplaresDisponibles());
        }

        @Test
        @DisplayName("Libro: prestar decrementa stock y falla si está agotado")
        void libroPrestarYExcepcionSinStock() {
            Libro libro = new Libro("L-1", "Refactoring", "Martin Fowler", "333", 1);

            libro.prestar();
            assertEquals(0, libro.getEjemplaresDisponibles());
            assertFalse(libro.consultarDisponibilidad());

            IllegalStateException ex = assertThrows(IllegalStateException.class, libro::prestar);
            assertEquals("No hay ejemplares disponibles.", ex.getMessage());
        }

        @Test
        @DisplayName("Libro (Sujeto Observer): agregar, evitar duplicados, eliminar y auto-eliminar al notificar")
        void libroNotificacionYAutoEliminacionObservadores() {
            Libro libro = new Libro("L-1", "DDD", "Eric Evans", "444", 0);
            AtomicInteger contador = new AtomicInteger(0);

            ObservadorDisponibilidad obs = l -> contador.incrementAndGet();
            libro.agregarObservador(obs);
            libro.agregarObservador(obs); // Duplicado ignorado

            libro.devolver();
            assertEquals(1, libro.getEjemplaresDisponibles());
            assertEquals(1, contador.get());

            libro.eliminarObservador(obs);
            libro.devolver();
            assertEquals(1, contador.get());

            // Observador que se da de baja durante actualizar()
            AtomicInteger llamadasAuto = new AtomicInteger(0);
            ObservadorDisponibilidad autoEliminable = new ObservadorDisponibilidad() {
                @Override
                public void actualizar(Libro l) {
                    llamadasAuto.incrementAndGet();
                    l.eliminarObservador(this);
                }
            };

            libro.agregarObservador(autoEliminable);
            assertDoesNotThrow(libro::devolver);
            assertEquals(1, llamadasAuto.get());
            libro.devolver();
            assertEquals(1, llamadasAuto.get());
        }

        @Test
        @DisplayName("ListaEspera: comportamiento FIFO")
        void listaEsperaColaFifo() {
            ListaEspera cola = new ListaEspera();
            assertTrue(cola.estaVacia());
            assertNull(cola.obtenerSiguiente());

            UsuarioRegular u1 = new UsuarioRegular("U1", "Ana", "ana@mail.com", "1");
            UsuarioRegular u2 = new UsuarioRegular("U2", "Luis", "luis@mail.com", "2");

            cola.agregarUsuario(u1);
            cola.agregarUsuario(u2);

            assertFalse(cola.estaVacia());
            assertSame(u1, cola.obtenerSiguiente());
            assertSame(u2, cola.obtenerSiguiente());
            assertTrue(cola.estaVacia());
        }

        @Test
        @DisplayName("SolicitudEspera: transición de EN ESPERA a NOTIFICADO")
        void solicitudEsperaCambioEstado() {
            Libro libro = new Libro("L1", "Java Efectivo", "Joshua Bloch", "999", 0);
            UsuarioRegular usuario = new UsuarioRegular("U1", "Pedro", "pedro@mail.com", "123");

            SolicitudEspera solicitud = new SolicitudEspera(libro, usuario);
            assertSame(libro, solicitud.getLibro());
            assertSame(usuario, solicitud.getUsuario());
            assertEquals("EN ESPERA", solicitud.getEstado());

            solicitud.notificar();
            assertEquals("NOTIFICADO", solicitud.getEstado());
        }

        @Test
        @DisplayName("ServicioListaEspera: registro, bloqueo de duplicados y notificación FIFO al devolver")
        void servicioListaEsperaFlujoCompleto() {
            ServicioListaEspera servicio = new ServicioListaEspera();
            Libro libro = new Libro("L-10", "Sistemas Operativos", "Tanenbaum", "12345", 0);
            UsuarioRegular u1 = new UsuarioRegular("U-1", "Mateo", "mateo@mail.com", "h1");
            UsuarioRegular u2 = new UsuarioRegular("U-2", "Valeria", "valeria@mail.com", "h2");

            servicio.registrarInteresado(libro, u1);
            servicio.registrarInteresado(libro, u2);

            assertTrue(servicio.tieneSolicitudesPendientes("L-10"));
            assertFalse(servicio.tieneSolicitudesPendientes("L-99"));

            // Bloqueo de registro duplicado activo
            IllegalStateException ex = assertThrows(IllegalStateException.class,
                    () -> servicio.registrarInteresado(libro, u1));
            assertEquals("Este usuario ya está en la lista de espera de ese libro.", ex.getMessage());

            // Primera devolución notifica al primero en cola (u1)
            libro.devolver();
            List<SolicitudEspera> solicitudes = servicio.getSolicitudes();
            assertEquals("NOTIFICADO", solicitudes.get(0).getEstado());
            assertEquals("EN ESPERA", solicitudes.get(1).getEstado());

            // Tras ser notificado, u1 puede volver a registrarse
            assertDoesNotThrow(() -> servicio.registrarInteresado(libro, u1));

            // Segunda devolución notifica a u2
            libro.devolver();
            assertEquals("NOTIFICADO", servicio.getSolicitudes().get(1).getEstado());

            // Actualizar libro sin lista registrada no debe lanzar error
            Libro sinLista = new Libro("L-SIN", "Otro", "Autor", "000", 1);
            assertDoesNotThrow(() -> servicio.actualizar(sinLista));
        }
    }

    // =========================================================================
    // 4. PRÉSTAMOS Y PATRÓN STATE (Prestamo, PrestamoActivo, PrestamoVencido, PrestamoDevuelto)
    // =========================================================================
    @Nested
    @DisplayName("4. Préstamos y Patrón State")
    class PrestamosYStateTests {

        private Usuario usuario;
        private Libro libro;

        @BeforeEach
        void setUp() {
            usuario = new UsuarioRegular("U-1", "Juan", "juan@mail.com", "123");
            libro = new Libro("L-1", "Algoritmos", "Cormen", "777", 2);
        }

        @Test
        @DisplayName("Prestamo: creación exitosa en ACTIVO y fallo si libro sin ejemplares")
        void prestamoCreacionYValidacionDisponibilidad() {
            LocalDate limite = LocalDate.now().plusDays(14);
            Prestamo prestamo = new Prestamo("P-1", usuario, libro, limite);

            assertEquals("P-1", prestamo.getId());
            assertSame(usuario, prestamo.getUsuario());
            assertSame(libro, prestamo.getLibro());
            assertEquals(LocalDate.now(), prestamo.getFechaInicio());
            assertEquals(limite, prestamo.getFechaLimite());
            assertNull(prestamo.getFechaDevolucion());
            assertEquals(0, prestamo.getRenovaciones());
            assertInstanceOf(PrestamoActivo.class, prestamo.getEstado());
            assertEquals("ACTIVO", prestamo.getEstado().obtenerNombre());
            assertEquals(1, libro.getEjemplaresDisponibles());

            Libro agotado = new Libro("L-2", "Agotado", "Autor", "000", 0);
            IllegalStateException ex = assertThrows(IllegalStateException.class,
                    () -> new Prestamo("P-2", usuario, agotado, limite));
            assertEquals("No hay ejemplares disponibles.", ex.getMessage());
        }

        @Test
        @DisplayName("PrestamoActivo: máximo 2 renovaciones de 7 días y devolución")
        void prestamoActivoRenovacionesYDevolucion() {
            LocalDate limiteInicial = LocalDate.now().plusDays(7);
            Prestamo prestamo = new Prestamo("P-1", usuario, libro, limiteInicial);

            assertTrue(prestamo.puedeRenovarse());

            // Renovación 1
            prestamo.renovar();
            assertEquals(1, prestamo.getRenovaciones());
            assertEquals(limiteInicial.plusDays(7), prestamo.getFechaLimite());

            // Renovación 2
            prestamo.renovar();
            assertEquals(2, prestamo.getRenovaciones());
            assertEquals(limiteInicial.plusDays(14), prestamo.getFechaLimite());
            assertFalse(prestamo.puedeRenovarse());

            // Renovación 3 falla
            IllegalStateException ex = assertThrows(IllegalStateException.class, prestamo::renovar);
            assertEquals("Se alcanzo el maximo de renovaciones.", ex.getMessage());

            // Devolución exitosa
            prestamo.devolver();
            assertEquals(LocalDate.now(), prestamo.getFechaDevolucion());
            assertEquals(2, libro.getEjemplaresDisponibles());
            assertInstanceOf(PrestamoDevuelto.class, prestamo.getEstado());
            assertEquals("DEVUELTO", prestamo.getEstado().obtenerNombre());
        }

        @Test
        @DisplayName("PrestamoVencido: detecta vencimiento por fecha, bloquea renovación y permite devolución")
        void prestamoVencidoComportamiento() {
            Prestamo prestamo = new Prestamo("P-1", usuario, libro, LocalDate.now().minusDays(2));

            // Al intentar renovar vencido, cambia estado a VENCIDO y lanza excepción
            IllegalStateException ex = assertThrows(IllegalStateException.class, prestamo::renovar);
            assertEquals("No se puede renovar un préstamo vencido.", ex.getMessage());
            assertInstanceOf(PrestamoVencido.class, prestamo.getEstado());
            assertEquals("VENCIDO", prestamo.getEstado().obtenerNombre());

            // Al devolver un préstamo vencido, pasa a DEVUELTO y restaura ejemplar
            prestamo.devolver();
            assertEquals(LocalDate.now(), prestamo.getFechaDevolucion());
            assertEquals(2, libro.getEjemplaresDisponibles());
            assertInstanceOf(PrestamoDevuelto.class, prestamo.getEstado());
        }

        @Test
        @DisplayName("PrestamoDevuelto: rechaza tanto renovar como volver a devolver")
        void prestamoDevueltoEsEstadoTerminal() {
            Prestamo prestamo = new Prestamo("P-1", usuario, libro, LocalDate.now().plusDays(5));
            prestamo.devolver();

            IllegalStateException exRenovar = assertThrows(IllegalStateException.class, prestamo::renovar);
            assertEquals("No se puede renovar un préstamo devuelto.", exRenovar.getMessage());

            IllegalStateException exDevolver = assertThrows(IllegalStateException.class, prestamo::devolver);
            assertEquals("Este préstamo ya fue devuelto.", exDevolver.getMessage());
        }
    }

    // =========================================================================
    // 5. MULTAS Y PATRÓN STRATEGY (Multa, MultaEstandar, CalculadoraMulta)
    // =========================================================================
    @Nested
    @DisplayName("5. Multas y Patrón Strategy")
    class MultasYStrategyTests {

        @Test
        @DisplayName("Multa: ciclo de vida completo (creación, asignación de valor y pago)")
        void multaCicloDeVida() {
            Usuario usuario = new UsuarioRegular("U1", "Andrés", "andres@mail.com", "123");
            Libro libro = new Libro("L1", "Redes", "Kurose", "888", 1);
            Prestamo prestamo = new Prestamo("P1", usuario, libro, LocalDate.now().minusDays(4));

            Multa multa = new Multa("M-01", prestamo, 4);

            assertEquals("M-01", multa.getId());
            assertSame(prestamo, multa.getPrestamo());
            assertEquals(4, multa.getDiasRetraso());
            assertEquals(0.0, multa.getValor());
            assertFalse(multa.isPagada());

            multa.setValor(8000.0);
            assertEquals(8000.0, multa.getValor());

            multa.registrarPago();
            assertTrue(multa.isPagada());
        }

        @Test
        @DisplayName("MultaEstandar: cálculo proporcional y retorno 0 para días no positivos")
        void multaEstandarCalculo() {
            PoliticaMulta politica = new MultaEstandar(2000.0);

            assertEquals(0.0, politica.calcularMulta(-3));
            assertEquals(0.0, politica.calcularMulta(0));
            assertEquals(2000.0, politica.calcularMulta(1));
            assertEquals(10000.0, politica.calcularMulta(5));
        }

        @Test
        @DisplayName("CalculadoraMulta: delegación y cambio dinámico de estrategia")
        void calculadoraMultaCambioDeEstrategia() {
            CalculadoraMulta calculadora = new CalculadoraMulta(new MultaEstandar(1500.0));
            assertEquals(4500.0, calculadora.calcular(3));

            PoliticaMulta politicaDoble = dias -> dias > 0 ? dias * 3000.0 : 0.0;
            calculadora.cambiarPolitica(politicaDoble);

            assertEquals(9000.0, calculadora.calcular(3));
            assertEquals(0.0, calculadora.calcular(0));
        }
    }

    // =========================================================================
    // 6. ALMACÉN DE DATOS EN MEMORIA (BibliotecaDatos)
    // =========================================================================
    @Nested
    @DisplayName("6. Almacén Compartido BibliotecaDatos")
    class BibliotecaDatosTests {

        @Test
        @DisplayName("BibliotecaDatos: inicialización de listas vacías y servicio de lista de espera")
        void bibliotecaDatosInicializacionYMutabilidad() {
            BibliotecaDatos datos = new BibliotecaDatos();

            assertNotNull(datos.getLibros());
            assertNotNull(datos.getUsuarios());
            assertNotNull(datos.getPrestamos());
            assertNotNull(datos.getMultas());
            assertNotNull(datos.getServicioListaEspera());

            assertTrue(datos.getLibros().isEmpty());
            assertTrue(datos.getUsuarios().isEmpty());
            assertTrue(datos.getPrestamos().isEmpty());
            assertTrue(datos.getMultas().isEmpty());

            Libro libro = new Libro("L1", "Cálculo", "Stewart", "101", 1);
            Usuario usuario = new UsuarioRegular("U1", "María", "maria@mail.com", "123");
            Prestamo prestamo = new Prestamo("P1", usuario, libro, LocalDate.now().plusDays(7));
            Multa multa = new Multa("M1", prestamo, 0);

            datos.getLibros().add(libro);
            datos.getUsuarios().add(usuario);
            datos.getPrestamos().add(prestamo);
            datos.getMultas().add(multa);

            assertEquals(1, datos.getLibros().size());
            assertEquals(1, datos.getUsuarios().size());
            assertEquals(1, datos.getPrestamos().size());
            assertEquals(1, datos.getMultas().size());
        }
    }
}