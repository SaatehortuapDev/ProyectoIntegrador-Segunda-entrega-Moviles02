# Explicacion tecnica de EduPerformance

## Resumen ejecutivo

EduPerformance es un backend academico construido con Java 21 y Spring Boot. Su objetivo es administrar usuarios, perfiles, estudiantes, profesores, cursos, calificaciones y asistencias desde una API REST documentada, validada y preparada para integrarse con clientes web o moviles.

La version actual del proyecto incorpora persistencia con PostgreSQL, DTOs de entrada y salida, reglas de negocio en servicios, manejo global de excepciones, documentacion OpenAPI/Swagger, referencia Scalar, coleccion de Postman, Dockerfile y configuracion de despliegue en Render.

## Alcance funcional

El sistema cubre los procesos principales de una gestion academica:

- Registro y consulta de usuarios.
- Administracion de informacion complementaria mediante perfiles.
- Asociacion de usuarios con estudiantes y profesores.
- Creacion y actualizacion de cursos.
- Registro, consulta y calculo de calificaciones.
- Registro y consulta de asistencias.
- Documentacion y prueba de endpoints mediante Swagger y Postman.

No se incluyen en esta entrega modulos de pagos, autenticacion con roles completa, frontend productivo ni analitica avanzada.

## Arquitectura del sistema

El proyecto usa una arquitectura multicapa:

| Capa | Responsabilidad |
|------|-----------------|
| Controller | Recibe peticiones HTTP, valida cuerpos con `@Valid` y devuelve respuestas REST. |
| Service | Ejecuta reglas de negocio, operaciones transaccionales y mapeo entre entidades y DTOs. |
| Repository | Usa Spring Data JPA para consultar y persistir informacion en PostgreSQL. |
| Entity | Representa el modelo de dominio y sus relaciones con anotaciones JPA. |
| DTO | Define contratos de entrada y salida para no exponer directamente las entidades. |
| Exception | Centraliza la forma de responder errores de negocio y errores generales. |
| Config | Configura seguridad, CORS y documentacion OpenAPI. |

Esta separacion permite mantener el codigo organizado, facilitar pruebas futuras y evitar que la logica de negocio quede mezclada con controladores o consultas de base de datos.

## Modelo de dominio y base de datos

Las entidades principales son:

- `Usuario`: datos base como nombre, apellido, edad, email y password.
- `Perfil`: datos complementarios del usuario, con relacion uno a uno.
- `Estudiante`: rol academico asociado a un usuario y a cursos matriculados.
- `Profesor`: rol docente asociado a un usuario y a cursos dictados.
- `Cursos`: informacion del curso y profesor asignado.
- `Calificacion`: nota asociada a estudiante y curso.
- `Asistencia`: registro de presencia o ausencia por estudiante, curso y fecha.

Relaciones relevantes:

- `Usuario` tiene un `Perfil` mediante relacion uno a uno.
- `Usuario` puede tener varios `Cursos` asignados.
- `Estudiante` se relaciona con `Cursos` mediante tabla intermedia.
- `Profesor` se relaciona con `Cursos` mediante tabla intermedia.
- `Calificacion` conecta estudiante, curso y nota.
- `Asistencia` conecta estudiante, curso, fecha y estado de asistencia.

Diagrama relacional:

![Diagrama relacional](./diagrama-relacional.png)

## API REST

Los controladores exponen rutas bajo el prefijo `/api`.

| Modulo | Ruta base | Funcionalidad |
|--------|-----------|---------------|
| Usuarios | `/api/usuarios` | Crear, listar, consultar, actualizar y eliminar usuarios. |
| Perfiles | `/api/perfiles` | Administrar datos complementarios de usuarios. |
| Estudiantes | `/api/estudiantes` | Administrar estudiantes y sus datos asociados. |
| Profesores | `/api/profesores` | Administrar profesores y sus datos asociados. |
| Cursos | `/api/cursos` | Administrar cursos y profesor asignado. |
| Calificaciones | `/api/calificaciones` | Administrar notas y consultar rendimiento por curso. |
| Asistencias | `/api/asistencias` | Administrar asistencia y consultar por curso/fecha. |

Endpoints especializados:

```text
GET /api/calificaciones/estudiante/{estudianteId}/curso/{cursoId}
GET /api/calificaciones/promedio/estudiante/{estudianteId}/curso/{cursoId}
GET /api/asistencias/curso/{cursoId}/fecha/{fecha}
```

## Reglas de negocio implementadas

### Calificaciones

El servicio de calificaciones valida que toda nota este dentro del rango academico permitido:

```text
0.0 <= nota <= 5.0
```

Si una nota no cumple la regla, se lanza `ReglaNegocioException` y el sistema responde con error `400 Bad Request`.

Tambien se implementa el calculo de promedio por estudiante y curso. Si no existen notas registradas, el promedio retornado es `0.0`.

### Asistencias

El servicio de asistencias impide registrar o actualizar asistencias con fecha futura. Esta regla protege la trazabilidad del proceso academico y evita registros anticipados.

Cuando la fecha no es valida, el sistema responde con `400 Bad Request`.

### Cursos y relaciones academicas

Los cursos pueden asociarse con un usuario profesor mediante `usuarioId`. En los DTOs de respuesta se devuelve el nombre del profesor en lugar de exponer el objeto completo, evitando ciclos de serializacion y reduciendo el ruido de la respuesta.

## Manejo de errores

El proyecto cuenta con `GlobalExceptionHandler`, que transforma excepciones en respuestas JSON consistentes:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "La nota debe estar entre 0.0 y 5.0",
  "timestamp": "2026-05-22T21:00:00"
}
```

Tipos de errores gestionados:

- `ReglaNegocioException`: errores de reglas de negocio, responde `400`.
- `IllegalArgumentException`: argumentos invalidos, responde `400`.
- `RuntimeException`: recursos no encontrados o errores operativos, responde `404`.
- `Exception`: errores no controlados, responde `500`.

## Seguridad y CORS

El proyecto incluye Spring Security con una configuracion temporal que desactiva CSRF y permite todas las peticiones mientras se desarrolla:

```java
.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
```

Tambien existe configuracion CORS para facilitar el consumo desde clientes frontend o moviles. Para produccion, la configuracion de seguridad debe endurecerse con autenticacion, autorizacion por roles y origenes permitidos especificos.

## Persistencia y configuracion

La base de datos configurada es PostgreSQL. Las credenciales se cargan desde variables de entorno o desde un archivo `.env` mediante `spring.config.import`.

Variables requeridas:

```properties
DB_URL=jdbc:postgresql://localhost:5432/eduperformance
DB_USERNAME=postgres
DB_PASSWORD=tu_password
```

Configuracion JPA principal:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Documentacion de API

La documentacion interactiva se genera con Springdoc OpenAPI.

Rutas disponibles en ejecucion local:

```text
http://localhost:8080/swagger-ui.html
http://localhost:8080/api-docs
```

Tambien se habilita Scalar API Reference apuntando al documento OpenAPI configurado.

## Pruebas con Postman

La carpeta `doc` incluye:

- `EduPerformance_CRUD_Completo_Postman.json`: coleccion de Postman para probar los endpoints principales.
- `Guia_Uso_CRUD_Postman.md`: guia de uso de la coleccion.

Esta coleccion permite validar el flujo CRUD completo y probar consultas especificas de calificaciones y asistencias.

## Docker y despliegue

El `Dockerfile` usa una compilacion en dos etapas:

1. Compila el proyecto con Maven y Java 21.
2. Ejecuta el `.jar` final sobre una imagen liviana de Eclipse Temurin 21 JRE Alpine.

Comandos de referencia:

```bash
docker build -t eduperformance-api .
docker run -p 8080:8080 --env-file .env eduperformance-api
```

El archivo `render.yaml` define:

- Servicio web Docker llamado `cesde-backend-api`.
- Base de datos PostgreSQL llamada `cesde-db`.
- Variables de entorno conectadas automaticamente desde la base de datos de Render.
- Perfil activo `prod`.

## Guia rapida de ejecucion local

1. Instalar Java 21 y PostgreSQL.
2. Crear la base de datos.
3. Configurar el archivo `.env` en la raiz.
4. Ejecutar:

```powershell
.\mvnw.cmd spring-boot:run
```

En sistemas Unix:

```bash
./mvnw spring-boot:run
```

5. Abrir Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Participantes

- Sergio Aterhortua
- David Marin
- Yeison Angel
- Alison Diaz
- Carolina Martinez

## Conclusiones tecnicas

EduPerformance evoluciono de un CRUD basico a una API academica con separacion por capas, reglas de negocio, DTOs, manejo estructurado de errores, documentacion interactiva y capacidad de despliegue. Esta base permite continuar con autenticacion real, roles, pruebas automatizadas, frontend movil/web y nuevas funcionalidades academicas sin romper la arquitectura existente.

