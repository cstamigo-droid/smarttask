import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la colección de tareas de SmartTask: alta, baja, consulta y
 * cambio de estado. Es la única clase responsable de manipular la lista
 * de tareas (principio de responsabilidad única), lo que mantiene el
 * resto del programa (por ejemplo {@link SmartTask}) enfocado solo en
 * la interacción con el usuario.
 */
public class GestorTareas {

    private List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    /**
     * Crea y agrega una nueva tarea a la colección. Si {@code urgente}
     * es {@code true} se crea una {@link TareaUrgente}; en caso contrario,
     * una {@link TareaNormal}. El id se asigna automáticamente de forma
     * incremental.
     *
     * @param nombre    nombre/descripción de la tarea.
     * @param prioridad prioridad de la tarea.
     * @param urgente   {@code true} si la tarea debe crearse como urgente.
     * @return la tarea recién creada y agregada.
     */
    public Tarea agregarTarea(String nombre, String prioridad, boolean urgente) {
        Tarea nueva;
        if (urgente) {
            nueva = new TareaUrgente(siguienteId, nombre, prioridad);
        } else {
            nueva = new TareaNormal(siguienteId, nombre, prioridad);
        }
        siguienteId++;
        tareas.add(nueva);
        return nueva;
    }

    /**
     * @return la lista completa de tareas.
     */
    public List<Tarea> listarTareas() {
        return tareas;
    }

    /**
     * @return una lista con únicamente las tareas activas (no completadas).
     */
    public List<Tarea> listarActivas() {
        List<Tarea> activas = new ArrayList<>();
        for (Tarea t : tareas) {
            if (!t.isCompletado()) {
                activas.add(t);
            }
        }
        return activas;
    }

    /**
     * @return una lista con únicamente las tareas completadas.
     */
    public List<Tarea> listarCompletadas() {
        List<Tarea> completadas = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.isCompletado()) {
                completadas.add(t);
            }
        }
        return completadas;
    }

    /**
     * Busca una tarea por id y, si existe, la marca como completada.
     *
     * @param id identificador de la tarea a completar.
     * @return {@code true} si la tarea existía y se marcó; {@code false} si no se encontró.
     */
    public boolean marcarComoCompletada(int id) {
        Tarea t = buscarPorId(id);
        if (t == null) {
            return false;
        }
        t.marcarCompletada();
        return true;
    }

    /**
     * Elimina la tarea con el id indicado.
     *
     * @param id identificador de la tarea a eliminar.
     * @return {@code true} si se eliminó una tarea; {@code false} si no existía.
     */
    public boolean eliminarTarea(int id) {
        Tarea t = buscarPorId(id);
        if (t == null) {
            return false;
        }
        tareas.remove(t);
        return true;
    }

    /**
     * Busca una tarea por su id.
     *
     * @param id identificador de la tarea buscada.
     * @return la tarea encontrada, o {@code null} si no existe ninguna con ese id.
     */
    public Tarea buscarPorId(int id) {
        for (Tarea t : tareas) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * @return la cantidad total de tareas gestionadas.
     */
    public int cantidad() {
        return tareas.size();
    }
}
