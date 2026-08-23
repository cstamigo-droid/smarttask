import java.util.List;
import java.util.Scanner;

/**
 * Clase principal de SmartTask: un gestor de tareas de consola.
 * <p>
 * Presenta un menú interactivo mediante {@link Scanner} que permite
 * agregar tareas (normales o urgentes), listarlas separadas por estado,
 * marcarlas como completadas, eliminarlas y salir del programa. Toda la
 * lógica de manejo de la colección de tareas se delega en
 * {@link GestorTareas}, manteniendo esta clase enfocada exclusivamente
 * en la interacción con el usuario.
 */
public class SmartTask {

    /**
     * Punto de entrada del programa. Ejecuta el bucle del menú principal
     * hasta que el usuario elige la opción de salir.
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorTareas gestor = new GestorTareas();
        boolean salir = false;

        System.out.println("=== SmartTask - Gestor de Tareas ===");

        while (!salir) {
            mostrarMenu();
            String opcionTexto = scanner.nextLine().trim();
            int opcion;
            try {
                opcion = Integer.parseInt(opcionTexto);
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida. Ingresa un numero del 1 al 5.");
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarTarea(scanner, gestor);
                    break;
                case 2:
                    listarTareas(gestor);
                    break;
                case 3:
                    marcarCompletada(scanner, gestor);
                    break;
                case 4:
                    eliminarTarea(scanner, gestor);
                    break;
                case 5:
                    System.out.println("Saliendo de SmartTask. Hasta luego.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida. Ingresa un numero del 1 al 5.");
            }
        }

        scanner.close();
    }

    /**
     * Imprime el menú principal de opciones.
     */
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("1) Agregar tarea");
        System.out.println("2) Listar tareas");
        System.out.println("3) Marcar tarea como completada");
        System.out.println("4) Eliminar tarea");
        System.out.println("5) Salir");
        System.out.print("Elige una opcion: ");
    }

    /**
     * Pide los datos de una nueva tarea por consola y la agrega al gestor.
     *
     * @param scanner el {@link Scanner} usado para leer la entrada del usuario.
     * @param gestor  el gestor de tareas donde se agregará la nueva tarea.
     */
    private static void agregarTarea(Scanner scanner, GestorTareas gestor) {
        System.out.print("Nombre de la tarea: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Prioridad (ALTA/MEDIA/BAJA): ");
        String prioridad = scanner.nextLine().trim();

        System.out.print("Es urgente? (SI/NO): ");
        String respuesta = scanner.nextLine().trim();
        boolean urgente = respuesta.equalsIgnoreCase("SI");

        Tarea creada = gestor.agregarTarea(nombre, prioridad, urgente);
        System.out.println("Tarea agregada: " + creada.obtenerResumen());
    }

    /**
     * Lista todas las tareas, separadas en activas y completadas.
     *
     * @param gestor el gestor de tareas a consultar.
     */
    private static void listarTareas(GestorTareas gestor) {
        if (gestor.cantidad() == 0) {
            System.out.println("No hay tareas registradas.");
            return;
        }

        List<Tarea> activas = gestor.listarActivas();
        System.out.println("--- Tareas activas (" + activas.size() + ") ---");
        if (activas.isEmpty()) {
            System.out.println("(ninguna)");
        } else {
            for (Tarea t : activas) {
                System.out.println(t.obtenerResumen());
            }
        }

        List<Tarea> completadas = gestor.listarCompletadas();
        System.out.println("--- Tareas completadas (" + completadas.size() + ") ---");
        if (completadas.isEmpty()) {
            System.out.println("(ninguna)");
        } else {
            for (Tarea t : completadas) {
                System.out.println(t.obtenerResumen());
            }
        }
    }

    /**
     * Pide un id por consola e intenta marcar esa tarea como completada.
     *
     * @param scanner el {@link Scanner} usado para leer la entrada del usuario.
     * @param gestor  el gestor de tareas sobre el que se realiza la operación.
     */
    private static void marcarCompletada(Scanner scanner, GestorTareas gestor) {
        System.out.print("ID de la tarea a completar: ");
        int id = leerId(scanner);
        if (id == -1) {
            return;
        }
        boolean ok = gestor.marcarComoCompletada(id);
        if (ok) {
            System.out.println("Tarea " + id + " marcada como completada.");
        } else {
            System.out.println("No existe una tarea con id " + id + ".");
        }
    }

    /**
     * Pide un id por consola e intenta eliminar esa tarea.
     *
     * @param scanner el {@link Scanner} usado para leer la entrada del usuario.
     * @param gestor  el gestor de tareas sobre el que se realiza la operación.
     */
    private static void eliminarTarea(Scanner scanner, GestorTareas gestor) {
        System.out.print("ID de la tarea a eliminar: ");
        int id = leerId(scanner);
        if (id == -1) {
            return;
        }
        boolean ok = gestor.eliminarTarea(id);
        if (ok) {
            System.out.println("Tarea " + id + " eliminada.");
        } else {
            System.out.println("No existe una tarea con id " + id + ".");
        }
    }

    /**
     * Lee un id numérico desde la entrada estándar.
     *
     * @param scanner el {@link Scanner} usado para leer la entrada del usuario.
     * @return el id leído, o {@code -1} si la entrada no era un número válido.
     */
    private static int leerId(Scanner scanner) {
        String texto = scanner.nextLine().trim();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("ID invalido.");
            return -1;
        }
    }
}
