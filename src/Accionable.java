/**
 * Interfaz que define las operaciones comunes que puede realizar
 * cualquier elemento accionable dentro de SmartTask (por ejemplo, una tarea).
 * <p>
 * Al depender de esta interfaz en vez de una clase concreta, el resto del
 * sistema puede tratar de forma uniforme cualquier tipo de tarea que la
 * implemente (principio de programar contra abstracciones).
 */
public interface Accionable {

    /**
     * Marca el elemento como completado.
     */
    void marcarCompletada();

    /**
     * Genera un resumen textual legible del elemento, pensado para
     * mostrarse por consola.
     *
     * @return una cadena con el resumen del elemento.
     */
    String obtenerResumen();
}
