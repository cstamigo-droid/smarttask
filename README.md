# SmartTask — Proyecto evaluado Módulo 4

Gestor de tareas de consola en Java. Permite agregar tareas (normales o
urgentes), listarlas separadas por estado, marcarlas como completadas y
eliminarlas, todo mediante un menú interactivo por consola (`Scanner`).

## Requisitos

- Java 21+ (probado con Java 26: `javac`/`java`/`javadoc`/`jar` en el PATH).
- Para los tests: `lib\junit-console.jar` (JUnit Platform Console Standalone),
  ya incluido en este proyecto en `lib\`.

## Cómo compilar y ejecutar

### Opción rápida (doble clic)

Ejecuta `RUN.bat` — corre el `.jar` ya compilado en `dist\SmartTask.jar`.

### Desde cero (PowerShell / cmd, parado en la carpeta del proyecto)

```powershell
# 1) Compilar el código fuente
javac -d build src\*.java

# 2) Crear el JAR ejecutable
jar --create --file dist\SmartTask.jar --main-class SmartTask -C build .

# 3) Ejecutar
java -jar dist\SmartTask.jar
```

### Compilar y correr los tests (JUnit5)

```powershell
javac -cp "lib\*;build" -d build test\*.java
java -jar lib\junit-console.jar execute --class-path "build" --scan-class-path --details=tree
```

Resultado esperado: **6 de 6 tests pasan** (0 fallidos).

### Generar el JavaDoc

```powershell
javadoc -d docs -encoding UTF-8 -charset UTF-8 src\*.java
```

Abre `docs\index.html` en el navegador para ver la documentación.

## Estructura del proyecto

```
Proyecto_M4_SmartTask/
├── src/                     Código fuente (Accionable, Tarea, TareaNormal,
│                             TareaUrgente, GestorTareas, SmartTask)
├── test/                    GestorTareasTest.java (JUnit5)
├── lib/                     junit-console.jar (para correr los tests sin instalar nada)
├── build/                   .class compilados (generado por javac)
├── dist/                    SmartTask.jar (ejecutable)
├── docs/                    JavaDoc generado (index.html)
├── CAPTURAS/                demo_ejecucion.txt + demo_input.txt (evidencia de ejecución real)
├── ALGORITMOS.md            pseudocódigo de agregar/listar/eliminar
├── README.md                este archivo
├── RUN.bat                  doble clic para ejecutar el .jar
└── SmartTask_Tests.zip      copia de test/ para entrega
```

## Mapa de lecciones del Módulo 4 → dónde se cumplen

| Lección | Contenido | Dónde se cumple |
|---|---|---|
| **L1** | Estructura de un programa Java + método `main` | `src\SmartTask.java` (clase con `public static void main(String[] args)`); estructura de carpetas `src/`, `test/`, `dist/`, `docs/` de este README. |
| **L2** | Algoritmos / pseudocódigo | `ALGORITMOS.md` (pseudocódigo de agregar, listar y eliminar tareas) implementado en `src\GestorTareas.java`. |
| **L3** | Clases, objetos y estructuras de control | `src\Tarea.java` (clase con campos, constructor, condicionales en `obtenerResumen()`); `src\SmartTask.java` (`while`, `switch`, `if`) controla el flujo del menú. |
| **L4** | Menú interactivo con `Scanner` + documentación con JavaDoc | `src\SmartTask.java` (bucle `while` + `Scanner` + `switch` de 5 opciones); todas las clases y métodos públicos tienen comentarios `/** ... */` (ver `docs\index.html`). |
| **L5** | Encapsulamiento | `src\Tarea.java` (campos `private` con getters/setters); `src\GestorTareas.java` (la lista `tareas` es `private`, solo se accede mediante los métodos públicos del gestor). |
| **L6** | Interfaces, herencia, polimorfismo y responsabilidad única (SRP) | Interfaz `src\Accionable.java`; herencia `Tarea` → `TareaNormal` / `TareaUrgente` (`src\TareaNormal.java`, `src\TareaUrgente.java`); polimorfismo en `TareaUrgente.obtenerResumen()` (sobreescribe y antepone `"[!] "`) y en `getTipo()`; SRP: `GestorTareas` solo gestiona la colección, `SmartTask` solo gestiona la interacción por consola. |
| **L7** | Tests unitarios con JUnit5 | `test\GestorTareasTest.java` — 6 tests (`testAgregarTarea`, `testAgregarVariasYListar`, `testMarcarComoCompletada`, `testListarActivasYCompletadas`, `testEliminarTarea`, `testPolimorfismoTareaUrgente`), todos en verde. |

## Entregables y dónde están

| Entregable | Ubicación |
|---|---|
| Código fuente | `src\*.java` |
| Tests JUnit5 | `test\GestorTareasTest.java` (también empaquetados en `SmartTask_Tests.zip`) |
| JAR ejecutable | `dist\SmartTask.jar` |
| Lanzador de doble clic | `RUN.bat` |
| JavaDoc generado | `docs\index.html` |
| Evidencia de ejecución real (demo) | `CAPTURAS\demo_ejecucion.txt` (+ `CAPTURAS\demo_input.txt` con el guion de entrada usado) |
| Pseudocódigo de algoritmos | `ALGORITMOS.md` |
| Este documento | `README.md` |

## Nota sobre cobertura de código (medida con JaCoCo 0.8.12)

Se midió la cobertura real con **JaCoCo**. Reporte HTML: `coverage_report/index.html`.

Suite de **10 tests** (`GestorTareasTest.java` + `TareaTest.java`), todos en verde. Cobertura de líneas:

| Clase | Cobertura |
|-------|-----------|
| `GestorTareas` | **100%** |
| `Tarea` | **100%** |
| `TareaNormal` | **100%** |
| `TareaUrgente` | **100%** |
| **Lógica de negocio (modelo + gestor)** | **100%** ✅ (supera el mínimo del 80%) |
| `SmartTask` (menú de consola / capa de presentación) | 0% |

`SmartTask` es la **capa de UI interactiva** (bucle `Scanner`): por convención no
se cubre con pruebas unitarias (se validó manualmente ejecutando el `.jar`, ver
`CAPTURAS/demo_ejecucion.txt`). El "código base" evaluable (modelo `Tarea` +
lógica `GestorTareas`) está al **100%**.

## Capa web (M5 AE1 - 17-jul-2026)
El gestor del M4 ahora tambien corre en el navegador: web\TareasServlet.java (GET lista, POST agrega/completa/elimina, POST-redirect-GET). Desplegado en C:\dev\apache-tomcat-9.0.120\webapps\smarttask\ y verificado por HTTP (agregar tarea urgente y completar #3 OK). El dominio de consola quedo intacto - misma logica, nueva vista. URL: http://localhost:8080/smarttask/tareas
