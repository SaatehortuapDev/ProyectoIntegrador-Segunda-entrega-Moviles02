# Manual de Ejecución Paso a Paso en Postman

Este es el instructivo detallado de cómo debes operar la aplicación **Postman** paso a paso para probar el funcionamiento de la API EduPerformance. A continuación se incluye exactamente qué datos viajan en el cuerpo (*Body > raw*) de cada petición.

---

## 🛠️ Paso 0: Preparación
1. **Inicia el servidor:** Abre tu IDE (Eclipse, IntelliJ o VS Code) y ejecuta la aplicación Spring Boot. Espera a ver el mensaje de que el servidor inició en el puerto `8080`.
2. **Abre Postman.**
3. Ve a la parte superior izquierda y haz clic en el botón **Import**.
4. Arrastra y suelta el archivo `EduPerformance_Postman_Collection.json` (que está en la carpeta `/doc`).
5. En el panel izquierdo (Collections), despliega la colección **"EduPerformance - Master Integration Cycle"**.

---

## 📝 Ejecución del Flujo

*Abre cada carpeta en el panel izquierdo y haz clic en cada petición (request). Luego, haz clic en el botón azul **Send** (Enviar) arriba a la derecha.*

### Carpeta: 🚀 1. Alta de Comunidad
1. **Clic en `1.1 Crear Usuario 1 (Con Perfil)`**
   * **Payload (Body > raw):**
     ```json
     {
         "nombre": "Laura",
         "apellido": "Martinez",
         "edad": 20,
         "email": "laura.{{$timestamp}}@univ.edu",
         "perfil": {
             "direccion": "Calle Falsa 123",
             "telefono": "555-1020"
         }
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Abajo, en la pestaña "Body", debes ver un código `201 Created` y un JSON con el ID generado. (Postman guardará este ID automáticamente).

2. **Clic en `1.2 Crear Usuario 2 (Sin Perfil)`**
   * **Payload (Body > raw):**
     ```json
     {
         "nombre": "Pedro",
         "apellido": "Sosa",
         "edad": 24,
         "email": "pedro.{{$timestamp}}@univ.edu"
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Debe salir status `201 Created`.

3. **Clic en `1.3 Crear Usuario 3 (Profesor)`**
   * **Payload (Body > raw):**
     ```json
     {
         "nombre": "Dr. Alan",
         "apellido": "Turing",
         "edad": 55,
         "email": "alan.{{$timestamp}}@univ.edu",
         "perfil": {
             "direccion": "Laboratorio 4",
             "telefono": "555-9090"
         }
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Debe salir status `201 Created`.

### Carpeta: 🏫 2. Roles y Cursos
4. **Clic en `2.1 Registrar Estudiante 1`**
   * **Payload (Body > raw):**
     ```json
     {
         "usuario": {
             "id": {{usrEst1Id}}
         }
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Status `201 Created`.

5. **Clic en `2.2 Registrar Estudiante 2`**
   * **Payload (Body > raw):**
     ```json
     {
         "usuario": {
             "id": {{usrEst2Id}}
         }
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Status `201 Created`.

6. **Clic en `2.3 Registrar Profesor`**
   * **Payload (Body > raw):**
     ```json
     {
         "usuario": {
             "id": {{usrProfId}}
         }
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Status `201 Created`.

7. **Clic en `2.4 Crear Curso: Matemáticas`**
   * **Payload (Body > raw):**
     ```json
     {
         "nombre": "Matemáticas Discretas",
         "descripcion": "Lógica y algoritmos"
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Status `201 Created`.

8. **Clic en `2.5 Crear Curso: Física`**
   * **Payload (Body > raw):**
     ```json
     {
         "nombre": "Física Cuántica",
         "descripcion": "Mecánica avanzada"
     }
     ```
   * **Acción:** Haz clic en **Send**.
   * **Verificación:** Status `201 Created`.

### Carpeta: ⛓️ 3. Orquestación y Matrículas
*(Estas peticiones no llevan Payload en el Body, toda la información viaja en la URL)*

9. **Clic en `3.1 Asignar Profesor a Matemáticas`**
   * **Acción:** Haz clic en **Send**. 
   * **Verificación:** Status `200 OK`.
10. **Clic en `3.2 Matricular Est. 1 en Matemáticas`**
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK`.
11. **Clic en `3.3 Matricular Est. 1 en Física`**
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK`.
12. **Clic en `3.4 Matricular Est. 2 en Matemáticas`**
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK`.

### Carpeta: 📊 4. Transacciones Operativas
13. **Clic en `4.1 Nota: Estudiante 1 (Matemáticas) -> 4.5`**
    * **Payload (Body > raw):**
      ```json
      {
          "nota": 4.5,
          "estudiante": { "id": {{est1Id}} },
          "curso": { "id": {{cursoMatId}} }
      }
      ```
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK` o `201 Created`.

14. **Clic en `4.2 Nota: Estudiante 1 (Matemáticas) -> 5.0`**
    * **Payload (Body > raw):**
      ```json
      {
          "nota": 5.0,
          "estudiante": { "id": {{est1Id}} },
          "curso": { "id": {{cursoMatId}} }
      }
      ```
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK` o `201 Created`.

15. **Clic en `4.3 Nota: Estudiante 2 (Matemáticas) -> 3.2`**
    * **Payload (Body > raw):**
      ```json
      {
          "nota": 3.2,
          "estudiante": { "id": {{est2Id}} },
          "curso": { "id": {{cursoMatId}} }
      }
      ```
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK` o `201 Created`.

16. **Clic en `4.4 Asistencia: Estudiante 1 (Física)`**
    * **Payload (Body > raw):**
      ```json
      {
          "fecha": "{{$isoTimestamp}}",
          "presente": true,
          "estudiante": { "id": {{est1Id}} },
          "curso": { "id": {{cursoFisId}} }
      }
      ```
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK` o `201 Created`. (Postman inserta la fecha de hoy automáticamente).

### Carpeta: 🚫 5. Pruebas de Estrés y Excepciones
*En esta sección demostramos que el sistema no permite errores. Busca los textos rojos.*
17. **Clic en `5.1 Validar Error por Nota Negativa`**
    * **Payload (Body > raw):**
      ```json
      {
          "nota": -1.5,
          "estudiante": { "id": {{est1Id}} },
          "curso": { "id": {{cursoMatId}} }
      }
      ```
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** El status DEBE SER `400 Bad Request` en color rojo. El cuerpo debe mostrar un mensaje diciendo que la nota debe estar entre 0.0 y 5.0.

18. **Clic en `5.2 Validar Error por Matrícula Duplicada`**
    * **Payload:** Vacío (Información en URL).
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** El status DEBE SER `400 Bad Request` en color rojo. El mensaje dirá que el estudiante ya está matriculado.

19. **Clic en `5.3 Validar Actualización Inexistente`**
    * **Payload (Body > raw):**
      ```json
      { "nombre": "Fantasma" }
      ```
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** El status DEBE SER `404 Not Found`.

### Carpeta: 📈 6. Consultas y Reportes
20. **Clic en `6.1 Promedio Estudiante 1 (Matemáticas)`**
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK`. El resultado numérico de la respuesta debe ser `4.75` exacto.
21. **Clic en `6.2 Estudiantes en Matemáticas`**
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Te arrojará un arreglo `[ ]` con toda la información de los dos estudiantes que matriculaste.

### Carpeta: 🧹 7. Mantenimiento (Teardown)
22. **Clic en `7.1 Eliminar Curso Física`**
    * **Acción:** Haz clic en **Send**.
    * **Verificación:** Status `200 OK` o `204 No Content`.

---
## ⚡ Bonus: ¿Quieres ejecutar TODO en 1 segundo?
Si no quieres dar clic 22 veces en el botón Send, haz esto:
1. Pasa el ratón por encima del nombre de la carpeta principal de la colección en el lado izquierdo.
2. Haz clic en los tres puntos `...` y selecciona **Run collection**.
3. En la nueva ventana, simplemente dale al botón azul gigante **Run EduPerformance...**
4. Postman ejecutará las 22 peticiones de manera consecutiva y te mostrará una lista con cuadritos verdes de éxito.
