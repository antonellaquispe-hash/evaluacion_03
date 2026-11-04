# Laboratorio 16: Gestión de eventos
## Configuración de Spring Initializr (según la captura)

La captura muestra Gradle seleccionado y configuración Properties, pero el texto del Laboratorio 16 pide proyectos **Maven** y `application.yml`. Para seguir la rúbrica, usa Maven, Java y Java 17; selecciona YAML si generas los proyectos desde Initializr. Las dependencias base por servicio son Spring Web, Spring Data JPA, Validation y MySQL Driver. Eureka Server se agrega a `eureka-server`; Eureka Discovery Client a los tres servicios y al Gateway; Spring Cloud Gateway al `api-gateway`. El ZIP ya incluye esas dependencias y configuraciones en el proyecto multimódulo.

**Para abrirlo:** extrae el ZIP y abre la carpeta raíz `laboratorio16-gestion-eventos`, donde está el `pom.xml` padre. No abras cada subcarpeta como proyecto raíz. Cada módulo interno corresponde a un proceso/servicio separado, porque así lo requieren los puertos y la arquitectura del laboratorio.

**Estudiante:** Quispe Alburqueque Antonella Isabel  
**Sección:** __________________

## Situación y objetivo
Una organización necesita controlar eventos, asistentes e inscripciones. El sistema crea los servicios `event-service`, `attendee-service` y `registration-service`; cada servicio tiene su propio proyecto Maven, puerto y base MySQL. Eureka registra los servicios y API Gateway centraliza el acceso. Inscripción consulta por REST Evento y Asistente y verifica estado y capacidad.

## Requisitos
Java 17+, Maven 3.6.3+ y XAMPP con MySQL iniciado. El proyecto raíz es Maven multimódulo; abrir la carpeta completa `laboratorio16-gestion-eventos` en IDE.

## Preparar MySQL en XAMPP
1. En el panel XAMPP inicia **MySQL**.
2. Importa `sql/crear_bases.sql` en phpMyAdmin (o permite que el usuario de MySQL cree bases: las URLs incluyen `createDatabaseIfNotExist=true`). Se crean `bd_evento`, `bd_asistente`, `bd_inscripcion`.
3. La configuración dev usa usuario `root` y contraseña vacía por defecto. Si tu XAMPP tiene contraseña, define `DB_PASSWORD` antes de arrancar los servicios. Por ejemplo en PowerShell: `$env:DB_PASSWORD='tu_clave'`.

## Compilar e iniciar
Desde la carpeta raíz: `mvn clean package`.
Iniciar cada aplicación en terminal separada y en este orden:

```powershell
mvn -pl eureka-server spring-boot:run
mvn -pl event-service spring-boot:run
mvn -pl attendee-service spring-boot:run
mvn -pl registration-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

Eureka: `http://localhost:8761`. API pública por Gateway: `http://localhost:8080`. En producción, crea las tres bases primero, empaqueta con `mvn clean package` y ejecuta cada JAR con `--spring.profiles.active=prod`.

## Perfiles y valores
Cada servicio define `application.yml`, `application-dev.yml` y `application-prod.yml` (también test para pruebas). `dev` usa localhost, puertos de desarrollo, `root`, contraseña configurable y `ddl-auto: update`. `prod` permite cambiar `SERVER_PORT`, `EUREKA_URL`, `DB_URL`, `DB_USER`, `DB_PASSWORD` mediante variables de entorno y usa `ddl-auto: validate`, para no modificar esquema automáticamente. No se deben guardar credenciales productivas en el repositorio.

## Endpoints vía Gateway

| Servicio | Ruta pública | Puerto interno | Base |
|---|---|---:|---|
| Evento | `/api/eventos` | 8081 | `bd_evento` |
| Asistente | `/api/asistentes` | 8082 | `bd_asistente` |
| Inscripción | `/api/inscripciones` | 8083 | `bd_inscripcion` |

GET/POST se usan en la colección; GET/PUT/DELETE en `/id`. El Gateway quita el prefijo `/api` antes de enviar la petición.

## Aceptación y Postman
Importa `postman/Laboratorio16.postman_collection.json` en Postman. Ejecuta solicitudes en orden: crear evento, crear asistente, crear inscripción, capacidad inválida (espera HTTP 400), consultar eventos y referencia inválida (espera HTTP 400). Las solicitudes guardan IDs como variables de colección. Eureka y los cinco procesos deben estar activos.

## Diagrama y sustentación
Consulta `diagrama.md`. Respuesta sugerida: “Separé el dominio en tres servicios para que cada uno gestione una responsabilidad y una base propia. Eureka permite encontrarlos por nombre y Gateway ofrece una entrada única. Cada API persiste con Spring Data JPA/Hibernate y MySQL. Inscripción consulta Evento y Asistente por REST y comprueba la capacidad. Las anotaciones de validación rechazan datos incorrectos con HTTP 400; los IDs inexistentes devuelven 404 y los conflictos 409.”

## Cinco conclusiones
1. Separar Evento, Asistente e Inscripción reduce el acoplamiento y hace más claro el límite de cada responsabilidad.
2. Una base de datos por servicio evita que todos dependan del mismo esquema y permite evolucionar cada modelo con autonomía.
3. Eureka y API Gateway facilitan el descubrimiento y ofrecen una puerta de entrada uniforme para el cliente.
4. DTO y Bean Validation permiten proteger las reglas de datos antes de persistir y responder errores comprensibles.
5. La comunicación REST permite que Inscripción valide referencias y capacidad; también introduce dependencia de disponibilidad, por lo que en una solución real se agregan tiempos límite y resiliencia.

## Alcance
El laboratorio usa servicios de Evento, Asistente e Inscripción más infraestructura Eureka y Gateway; todo se entrega dentro de un solo proyecto Maven multimódulo, pero se ejecuta como procesos independientes. La colección Postman sirve como evidencia reproducible; las capturas deben obtenerse al ejecutar localmente con XAMPP y los servicios activos.

