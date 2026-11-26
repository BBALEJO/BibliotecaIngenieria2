
# 📚 Sistema de Gestión Bibliotecaria – Universidad Libre

## 1. Descripción del proyecto

El Sistema de Gestión Bibliotecaria es una aplicación de escritorio desarrollada en Java, orientada a facilitar la administración y gestión de los procesos de una biblioteca universitaria.

El proyecto busca optimizar la gestión de usuarios, libros, préstamos, devoluciones y renovaciones, proporcionando una solución organizada, modular y escalable.

La aplicación implementa diferentes patrones de diseño que permiten mejorar la organización del código, promover la reutilización de componentes y facilitar el mantenimiento del sistema.

## 2. Objetivo general

Desarrollar un sistema de gestión bibliotecaria que permita administrar eficientemente los recursos bibliográficos y los procesos relacionados con el préstamo de libros, aplicando principios de programación orientada a objetos y patrones de diseño de software.

## 3. Funcionalidades principales

El sistema contempla los siguientes módulos:

### Gestión de usuarios
- Registro y administración de usuarios.
- Identificación de diferentes tipos de usuarios.
- Gestión de roles y permisos.
- Administración de información personal.

### Gestión de libros
- Registro de libros.
- Consulta del catálogo bibliográfico.
- Administración de información de los libros.
- Control de disponibilidad de ejemplares.

### Gestión de préstamos
- Registro de préstamos de libros.
- Control de fechas de préstamo y vencimiento.
- Seguimiento del estado de los préstamos.
- Consulta del historial de préstamos.

### Gestión de devoluciones
- Registro de devolución de libros.
- Actualización de disponibilidad.
- Control de préstamos vencidos.

### Renovaciones y multas
- Gestión de renovaciones de préstamos.
- Identificación de préstamos vencidos.
- Aplicación de estrategias para el cálculo de multas.

### Lista de espera y notificaciones
- Administración de listas de espera.
- Organización de solicitudes mediante una estructura FIFO.
- Notificación a los usuarios cuando un libro vuelve a estar disponible.

## 4. Tecnologías utilizadas

| Tecnología | Descripción |
|---|---|
| Java 21 | Lenguaje de programación principal. |
| Java Swing | Desarrollo de la interfaz gráfica de escritorio. |
| Maven | Administración de dependencias y construcción del proyecto. |
| SQLite | Base de datos local. |
| IntelliJ IDEA | Entorno de desarrollo. |
| JUnit 5 | Pruebas unitarias. |
| Git y GitHub | Control de versiones y almacenamiento del código fuente. |

## 5. Patrones de diseño implementados

El proyecto implementa cuatro patrones de diseño:

### 5.1 Factory Method

Permite crear diferentes tipos de usuarios mediante creadores especializados, evitando concentrar toda la lógica de creación en una única clase.

Clases principales:

- `UsuarioCreator`
- `AdministradorCreator`
- `BibliotecarioCreator`
- `UsuarioRegularCreator`

### 5.2 Observer

Permite notificar a los usuarios registrados en una lista de espera cuando un libro vuelve a estar disponible.

Este patrón establece una relación entre los objetos observadores y el objeto que genera los eventos, permitiendo que las notificaciones se realicen automáticamente.

### 5.3 State

Permite representar los diferentes estados por los que puede pasar un préstamo, encapsulando el comportamiento correspondiente a cada estado.

Clases principales:

- `EstadoPrestamo`
- `PrestamoActivo`
- `PrestamoDevuelto`
- `PrestamoVencido`

Este patrón facilita la administración de las transiciones de estado de los préstamos.

### 5.4 Strategy

Permite implementar diferentes estrategias para realizar operaciones del sistema, como el cálculo de multas, evitando que la lógica dependa de una única implementación.

Su objetivo es facilitar la incorporación y modificación de reglas de negocio.

## 6. Arquitectura del proyecto

El sistema está organizado mediante paquetes que permiten separar las responsabilidades de sus componentes.

Estructura general:

```text
BibliotecaIngenieria2/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── unilibre/
│   │               └── biblioteca/
│   │                   ├── model/
│   │                   ├── ui/
│   │                   ├── factory/
│   │                   ├── observer/
│   │                   ├── state/
│   │                   ├── strategy/
│   │                   └── Main.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── unilibre/
│                   └── biblioteca/
│
├── pom.xml
└── README.md
```

La estructura representa la organización lógica del proyecto y puede ampliarse a medida que se incorporen nuevos módulos.

## 7. Requisitos del sistema

Para ejecutar el proyecto se requiere:

- Java JDK 21 o superior.
- IntelliJ IDEA.
- Apache Maven.
- Git (opcional, para clonar el repositorio).

## 8. Instalación y configuración

### Paso 1. Clonar el repositorio

Abrir una terminal y ejecutar:

```bash
git clone https://github.com/BBALEJO/BibliotecaIngenieria2.git
```

### Paso 2. Ingresar al proyecto

```bash
cd BibliotecaIngenieria2
```

### Paso 3. Abrir el proyecto

Abrir IntelliJ IDEA y seleccionar la carpeta del proyecto.

Esperar a que Maven detecte el archivo `pom.xml` y descargue las dependencias necesarias.

### Paso 4. Verificar la versión de Java

Ejecutar en la terminal:

```bash
java -version
```

Verificar que esté instalada la versión 21 o superior.

### Paso 5. Compilar el proyecto

Desde la terminal, ejecutar:

```bash
mvn clean compile
```

Este comando limpia los archivos compilados anteriormente y compila el código fuente.

## 9. Ejecución de la aplicación

### Desde IntelliJ IDEA

1. Abrir el proyecto.
2. Ubicar la clase `Main.java`.
3. Abrir el archivo.
4. Identificar el método `main`.
5. Hacer clic en el botón verde de ejecución.
6. Esperar a que se inicie la aplicación.

La clase principal se encuentra en:

`com.unilibre.biblioteca.Main`

### Desde la terminal

Si el proyecto tiene configurado el plugin Maven Exec, se puede ejecutar mediante:

```bash
mvn exec:java
```

## 10. Ejecución de pruebas unitarias

El proyecto utiliza JUnit 5 para verificar el funcionamiento de sus componentes.

Para ejecutar las pruebas desde Maven:

```bash
mvn test
```

Para ejecutar una compilación completa:

```bash
mvn clean package
```

Este último comando compila el proyecto, ejecuta las pruebas y genera el paquete correspondiente.

## 11. Construcción del proyecto

Para generar el paquete de distribución:

```bash
mvn package
```

El archivo generado se almacena en la carpeta:

`target/`

La posibilidad de ejecutar directamente el JAR generado depende de la configuración del empaquetado y de sus dependencias.

## 12. Beneficios de la implementación

- Organización modular del código.
- Aplicación de principios de programación orientada a objetos.
- Implementación de patrones de diseño.
- Separación de responsabilidades.
- Facilidad de mantenimiento y escalabilidad.
- Automatización de procesos bibliotecarios.
- Mejor administración de préstamos y devoluciones.

## 13. Autores

Proyecto académico desarrollado en el contexto de la Universidad Libre.

## 14. Repositorio

Código fuente:

https://github.com/BBALEJO/BibliotecaIngenieria2

---

**Universidad Libre**

Proyecto de Ingeniería de Software.
