/**
 * Tarea de tipo normal, sin ningún tratamiento especial.
 * Representa el caso estándar de {@link Tarea}.
 */
public class TareaNormal extends Tarea {

    /**
     * Crea una tarea normal.
     *
     * @param id        identificador único de la tarea.
     * @param nombre    nombre/descripción de la tarea.
     * @param prioridad prioridad de la tarea.
     */
    public TareaNormal(int id, String nombre, String prioridad) {
        super(id, nombre, prioridad);
    }

    /**
     * @return siempre {@code "Normal"}.
     */
    @Override
    public String getTipo() {
        return "Normal";
    }
}
