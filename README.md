# EduPerformance

Optimizar la gestión académica por medio de una plataforma moderna, tanto para el estudiante como para el docente.

## Introducción / Contexto

- Los estudiantes y profesores carecen de herramientas integradas que les permitan gestionar eficazmente el tiempo, las tareas y los proyectos académicos.
- Una plataforma académica moderna mejora la productividad, fomenta la organización y contribuye al éxito académico, con impacto positivo tanto en la comunidad estudiantil como en la gestión docente.
- Gestión académica universitaria enfocada en la administración de cursos, tareas, proyectos y comunicación entre estudiantes y docentes.

## Objetivos

**Objetivo General**

- Desarrollar un proyecto fullstack (backend, frontend y bases de datos) robusto que soporte la gestión académica universitaria de manera eficiente y escalable.

**Objetivos Específicos**

- Diseñar la arquitectura backend utilizando Spring Boot y JPA bajo un patrón Multicapa (Controller - Service - Repository).
- Implementar servicios REST y lógica de negocio avanzada para la gestión de estudiantes, docentes, calificaciones y cursos.
- Garantizar seguridad, escalabilidad e interoperabilidad (CORS, Swagger, Global Exceptions) para consumir los datos desde cualquier Front-End.
- Documentar el proyecto siguiendo estrictos estándares profesionales, arquitectónicos y comerciales.

---

## 🚀 Actualizaciones y Características Implementadas (v1.0 API)

La arquitectura backend inicial ha sido elevada a categoría empresarial, contando ahora con las siguientes fortalezas:

1. **Lógica de Negocio Avanzada:**
   - **Motor de Calificaciones:** Cálculo automático de promedios ("al vuelo") y bloqueo estricto contra notas fuera del rango permitido (0.0 - 5.0).
   - **Control de Asistencia:** Restricciones anti-fraude que bloquean el registro de asistencias con fechas futuras.
   - **Matrículas Protegidas:** Sistema de inyección cruzada que evita la doble matriculación de un estudiante en el mismo curso.

2. **Preparación Arquitectónica para Frontend:**
   - **CORS Configurado:** Las políticas de navegadores están habilitadas para aceptar peticiones de puertos estándares (ej. `:3000`, `:4200`) de React, Angular o Vue.
   - **Manejo Global de Excepciones (@ControllerAdvice):** Si se viola una regla (ej. intentar matricularse dos veces), el back-end no explota; arroja un JSON estructurado (Code 400) que el frontend puede leer y mostrar elegantemente al usuario.
   - **Swagger / OpenAPI 3:** Integración total. Al iniciar el servidor en `localhost:8080/swagger-ui.html` se visualiza una página gráfica interactiva donde todo el equipo puede documentarse y probar las rutas de la API en vivo.

3. **Código y Arquitectura Profesionales:**
   - **Documentación de Código Optimizado:** Se reemplazaron bloques de documentación extensos por comentarios concisos y declarativos (One-liners) a lo largo de todas las capas del aplicativo (Models, Repositories, Services, Controllers). Esto maximiza la mantenibilidad y facilita la lectura ágil del equipo de desarrollo.
   - **Presentación Técnica Ejecutiva:** Hemos consolidado toda la esencia del aplicativo en un nuevo manifiesto de arquitectura.

Para revisar en detalle la documentación técnica, manuales para Swagger, Casos de Uso y la [Explicación Técnica Comercial (Enterprise-Grade)](./doc/Explicacion_Tecnica_EduPerformance.md), visita la **[Carpeta `/doc` de este repositorio](./doc)**.

---

## Alcance del Proyecto (Scope)

**Qué se ha desarrollado (Backend):**
- Módulos robustos de gestión académica (Estudiantes, Perfiles, Profesores, Cursos, Calificaciones y Asistencias).
- API REST blindada con manejo de errores JSON estandarizado.
- Arquitectura Limpia basada en Inversión de Control de Spring.
- Documentación y Entorno visual automatizado (Swagger).

**Qué NO se va a desarrollar en esta versión (fuera de alcance):**
- Integración con sistemas externos de matrícula financiera o pagos.
- Funcionalidades avanzadas de analítica académica profunda (ej. Machine Learning).

---

## Tecnologías y Herramientas (Tech Stack)

- **Backend**: Spring Initializr, Java 21, Spring Data JPA, Lombok, Springdoc (Swagger OpenAPI 3).
- **Frontend**: React + Vite + Tailwind CSS.
- **Base de datos**: PostgreSQL en producción, H2 en desarrollo inicial.
- **Otras herramientas**: Git, GitHub, Postman, Swagger UI.

---

## Integrantes del Equipo

| Nombre            | Rol principal                  | Correo / Usuario GitHub           |
|-------------------|--------------------------------|-----------------------------------|
| Sergio Atehortua  | Líder / Backend                | salejandro.atehortua@udea.edu.co  |
| Alison Díaz       | Frontend Lead                  | alinson_04@hotmail.com            |
| Jeison ossa       | Backend / Base de datos        | @yeisonossa3010@gmail.com         |

---

## Diagrama de Clases del Dominio (v1)

![Diagrama de Dominion v1](/doc/diagrama-dominio-v1.png)
