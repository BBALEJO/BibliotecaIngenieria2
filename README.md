
# Pruebas Unitarias – Clase Usuario

## 1. Descripción

Este archivo contiene pruebas unitarias para verificar el correcto funcionamiento de la clase abstracta `Usuario`, perteneciente al paquete `com.unilibre.biblioteca.model`.

Debido a que `Usuario` es una clase abstracta, se implementa una clase interna llamada `UsuarioConcretoStub`, que permite instanciar un objeto de prueba sin necesidad de modificar la clase original.

Las pruebas se desarrollan utilizando **JUnit 5**.

## 2. Objetivos de las pruebas

Se busca comprobar:

- La correcta inicialización de los atributos de un usuario.
- El funcionamiento de los métodos getters.
- La implementación del método abstracto `obtenerRol()`.
- El formato de salida del método `toString()`.

## 3. Estructura del código

La clase de prueba contiene dos métodos principales:

### Prueba 1: `debeInicializarAtributosYGetters()`

Verifica que los atributos del usuario se inicialicen correctamente y que los métodos getters devuelvan los valores esperados.

| Atributo | Valor esperado |
|---|---|
| ID | U-01 |
| Nombre | Breiner Bonilla |
| Correo | breiner@unilibre.edu.co |
| Contraseña Hash | hash123 |
| Rol | ROL_PRUEBA |

### Prueba 2: `toStringDebeFormatearNombreYRol()`

Comprueba que el método `toString()` concatene correctamente el nombre del usuario y su rol, separados por un guion.

**Resultado esperado:**

`Breiner Bonilla - ROL_PRUEBA`

## 4. Tecnologías utilizadas

- Java.
- JUnit 5.
- Maven.
- IntelliJ IDEA.

## 5. Dependencia Maven

Para ejecutar las pruebas se requiere incluir JUnit 5 en el archivo `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
```

## 6. Ubicación del archivo

La clase de prueba debe estar ubicada en:

`src/test/java/com/unilibre/biblioteca/model/UsuarioTest.java`

## 7. Ejecución de las pruebas

Las pruebas pueden ejecutarse de dos maneras:

### Desde IntelliJ IDEA

1. Abrir el proyecto.
2. Ubicar `UsuarioTest.java`.
3. Hacer clic derecho sobre el archivo.
4. Seleccionar `Run 'UsuarioTest'`.
5. Revisar los resultados en la ventana de ejecución.

### Desde la terminal con Maven

Ejecutar el siguiente comando desde la raíz del proyecto:

```bash
mvn test
```

Este comando compila y ejecuta las pruebas unitarias del proyecto.

## 8. Resultados esperados

Si las pruebas se ejecutan correctamente, Maven mostrará un resultado similar a:

```text
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

Esto indica que las dos pruebas fueron ejecutadas satisfactoriamente.

## 9. ¿Cómo ejecutar la aplicación completa?

Para ejecutar el sistema de biblioteca desde IntelliJ IDEA:

1. Abrir el proyecto en IntelliJ IDEA.
2. Ubicar la clase `Main.java`.
3. Abrir el archivo.
4. Identificar el método `main`.
5. Hacer clic en el botón verde de ejecución.
6. Esperar a que el sistema inicie.

### Comandos Maven

| Comando | Función |
|---|---|
| `mvn clean` | Limpia archivos compilados anteriores. |
| `mvn test` | Ejecuta las pruebas unitarias. |
| `mvn package` | Compila, ejecuta las pruebas y empaqueta el proyecto. |
| `mvn exec:java` | Ejecuta la clase principal si está configurado el plugin correspondiente. |

## 10. Importancia de las pruebas unitarias

Las pruebas permiten detectar errores en la inicialización de objetos, verificar el comportamiento de los métodos y garantizar que la clase `Usuario` cumpla con los resultados esperados.

Además, facilitan el mantenimiento del sistema y reducen el riesgo de introducir errores al realizar modificaciones futuras.
