/**
 * Clase abstracta que representa una tarea genérica dentro de SmartTask.
 * <p>
 * Encapsula los datos comunes a toda tarea (id, nombre, prioridad y estado
 * de completado) y define el comportamiento común a través de la interfaz
 * {@link Accionable}. Las subclases concretas ({@link TareaNormal},
 * {@link TareaUrgente}) solo deben aportar su tipo específico mediante el
 * método abstracto {@link #getTipo()}, lo que permite tratar cualquier
 * tarea de forma polimórfica.
 */
public abstract class Tarea implements Accionable {

    private int id;
    private String nombre;
    private String prioridad;
    private boolean completado;

    /**
     * Crea una nueva tarea. El estado de completado siempre inicia en
     * {@code false}.
     *
     * @param id        identificador único de la tarea.
     * @param nombre    nombre/descripción de la tarea.
     * @param prioridad prioridad de la tarea (por ejemplo: "ALTA", "MEDIA", "BAJA").
     */
    public Tarea(int id, String nombre, String prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.completado = false;
    }

    /**
     * @return el identificador único de la tarea.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id nuevo identificador de la tarea.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return el nombre de la tarea.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre nuevo nombre de la tarea.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return la prioridad de la tarea.
     */
    public String getPrioridad() {
        return prioridad;
    }

    /**
     * @param prioridad nueva prioridad de la tarea.
     */
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return {@code true} si la tarea está completada, {@code false} en caso contrario.
     */
    public boolean isCompletado() {
        return completado;
    }

    /**
     * @param completado nuevo estado de completado de la tarea.
     */
    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    /**
     * Marca la tarea como completada.
     */
    @Override
    public void marcarCompletada() {
        this.completado = true;
    }

    /**
     * Devuelve el tipo concreto de la tarea (por ejemplo "Normal" o
     * "URGENTE"). Cada subclase define su propio tipo, lo que permite
     * el polimorfismo al construir el resumen.
     *
     * @return el tipo de la tarea.
     */
    public abstract String getTipo();

    /**
     * Construye un resumen legible de la tarea con el formato:
     * {@code "[id] nombre (prioridad) - tipo - estado"}.
     *
     * @return el resumen de la tarea.
     */
    @Override
    public String obtenerResumen() {
        String estado = completado ? "COMPLETADA" : "ACTIVA";
        return "[" + id + "] " + nombre + " (" + prioridad + ") - " + getTipo() + " - " + estado;
    }

    /**
     * @return la representación textual de la tarea, igual a {@link #obtenerResumen()}.
     */
    @Override
    public String toString() {
        return obtenerResumen();
    }
}
