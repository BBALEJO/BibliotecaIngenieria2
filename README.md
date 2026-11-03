
# Sistema de Gestión de Biblioteca
## Alcance
Fijándonos en los usuarios que tenemos en los requerimientos es necesario un login para separar permisos de cada uno, además de eso el catálogo de los libros debe estar disponible para consulta cuando se necesite, el catálogo debe ser diferente para cada usuario.

## 1. Tipo de recurso
Biblioteca

## 2. Lógica de negocio
El sistema de gestión de biblioteca permitirá administrar el préstamo, devolución y renovación de libros, así como controlar la disponibilidad de los ejemplares, los usuarios y las sanciones por retrasos.

El sistema contará con diferentes roles de usuario (administrador, bibliotecario y usuario), establecerá límites de préstamo, gestionará listas de espera y mantendrá un historial de uso por cada libro.

## 3. Requerimientos del sistema

### 3.1. Requerimientos funcionales

| ID | Requerimiento | Pedido por |
|:---|:---|:---|
| RF1 | El sistema debe verificar el tipo de usuario. | Angélica |
| RF2 | El sistema debe permitir el préstamo de libros por un máximo de 15 días. | Breiner |
| RF3 | El sistema debe permitir sacar un máximo de 4 libros simultáneamente. | Angélica |
| RF4 | El sistema debe permitir renovar un libro un máximo de 2 veces. |  Breiner |
| RF5 | El sistema debe aplicar un cobro de 600 pesos por cada día de retraso y por cada libro. | Angélica |
| RF6 | El sistema debe manejar los siguientes roles: administrador, bibliotecario y usuario. | Breiner|
| RF7 | El sistema debe mantener un historial de uso por cada libro. | Angélica |
| RF8 | El sistema debe implementar una lista de espera para los libros más solicitados. | Angélica |
| RF10 | El sistema debe permitir consultar qué libros están disponibles y cuáles se encuentran en lista de espera. |  Breiner |

### 3.2. Requerimientos no funcionales

| ID | Requerimiento |
|:---|:---|
| RNF1 | El sistema debe estar disponible las 24 horas del día, los 7 días de la semana (24/7). |
| RNF2 | El sistema debe permitir la conexión de hasta 10 usuarios simultáneamente. |
| RNF3 | El sistema debe almacenar las contraseñas de los usuarios de forma segura. |
| RNF4 | El sistema debe responder a las solicitudes en menos de 2 segundos. |

## 4. Reglas de negocio

| ID | Regla | Descripción |
|:---|:---|:---|
| RN1 | Duración del préstamo | Cada préstamo tendrá una duración máxima de 15 días. |
| RN2 | Límite de libros | Cada usuario podrá tener máximo 4 libros prestados simultáneamente. |
| RN3 | Renovaciones | Cada libro podrá renovarse máximo 2 veces. |
| RN4 | Multas | Se cobrarán 600 pesos por cada día de retraso y por cada libro. |
| RN5 | Roles | El sistema manejará tres tipos de usuarios: administrador, bibliotecario y usuario. |
| RN6 | Lista de espera | Los libros que no estén disponibles podrán tener una lista de espera. |
| RN7 | Historial | Cada libro deberá contar con un registro de su historial de uso. |

## 5. Resumen

El sistema busca facilitar la administración de una biblioteca mediante la automatización de los préstamos, devoluciones, renovaciones, multas y control de disponibilidad de libros.

Además, garantizará la gestión de usuarios mediante roles, la seguridad de la información y la disponibilidad del servicio desde diferentes dispositivos.
