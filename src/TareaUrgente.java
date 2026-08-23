/**
 * Tarea de tipo urgente. Se distingue visualmente de una tarea normal
 * anteponiendo el marcador {@code "[!] "} a su resumen, para demostrar
 * cómo el polimorfismo permite que cada subclase de {@link Tarea}
 * personalice su propio comportamiento.
 */
public class TareaUrgente extends Tarea {

    /**
     * Crea una tarea urgente.
     *
     * @param id        identificador único de la tarea.
     * @param nombre    nombre/descripción de la tarea.
     * @param prioridad prioridad de la tarea.
     */
    public TareaUrgente(int id, String nombre, String prioridad) {
        super(id, nombre, prioridad);
    }

    /**
     * @return siempre {@code "URGENTE"}.
     */
    @Override
    public String getTipo() {
        return "URGENTE";
    }

    /**
     * Antepone {@code "[!] "} al resumen generado por la clase padre
     * ({@link Tarea#obtenerResumen()}), demostrando polimorfismo mediante
     * sobreescritura y reutilización con {@code super}.
     *
     * @return el resumen de la tarea urgente, marcado con "[!] ".
     */
    @Override
    public String obtenerResumen() {
        return "[!] " + super.obtenerResumen();
    }
}
