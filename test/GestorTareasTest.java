import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite de pruebas unitarias para {@link GestorTareas}, usando JUnit5.
 * Cubre alta, listado, cambio de estado, eliminación y el comportamiento
 * polimórfico de {@link TareaUrgente}.
 */
class GestorTareasTest {

    private GestorTareas gestor;

    /**
     * Crea un {@link GestorTareas} nuevo antes de cada prueba, para que
     * los tests sean independientes entre sí.
     */
    @BeforeEach
    void setUp() {
        gestor = new GestorTareas();
    }

    /**
     * Verifica que al agregar una tarea la cantidad suba a 1, que se le
     * asigne un id y que el tipo sea el correcto según el flag "urgente".
     */
    @Test
    void testAgregarTarea() {
        Tarea t = gestor.agregarTarea("Estudiar Java", "ALTA", false);

        assertEquals(1, gestor.cantidad());
        assertEquals(1, t.getId());
        assertEquals("Normal", t.getTipo());
        assertFalse(t.isCompletado());
    }

    /**
     * Verifica que agregar varias tareas y luego listarlas devuelva
     * una lista del tamaño correcto, con ids incrementales.
     */
    @Test
    void testAgregarVariasYListar() {
        gestor.agregarTarea("Tarea 1", "ALTA", false);
        gestor.agregarTarea("Tarea 2", "MEDIA", true);
        gestor.agregarTarea("Tarea 3", "BAJA", false);

        List<Tarea> tareas = gestor.listarTareas();

        assertEquals(3, tareas.size());
        assertEquals(1, tareas.get(0).getId());
        assertEquals(2, tareas.get(1).getId());
        assertEquals(3, tareas.get(2).getId());
    }

    /**
     * Verifica que marcarComoCompletada retorne true y deje la tarea
     * completada cuando el id existe, y retorne false cuando no existe.
     */
    @Test
    void testMarcarComoCompletada() {
        Tarea t = gestor.agregarTarea("Pagar cuentas", "ALTA", false);

        boolean resultado = gestor.marcarComoCompletada(t.getId());

        assertTrue(resultado);
        assertTrue(t.isCompletado());
        assertFalse(gestor.marcarComoCompletada(999));
    }

    /**
     * Verifica que listarActivas y listarCompletadas separen correctamente
     * las tareas según su estado.
     */
    @Test
    void testListarActivasYCompletadas() {
        Tarea t1 = gestor.agregarTarea("Tarea activa", "ALTA", false);
        Tarea t2 = gestor.agregarTarea("Tarea completada", "MEDIA", false);
        gestor.marcarComoCompletada(t2.getId());

        List<Tarea> activas = gestor.listarActivas();
        List<Tarea> completadas = gestor.listarCompletadas();

        assertEquals(1, activas.size());
        assertEquals(1, completadas.size());
        assertEquals(t1.getId(), activas.get(0).getId());
        assertEquals(t2.getId(), completadas.get(0).getId());
    }

    /**
     * Verifica que eliminarTarea retorne true al eliminar una tarea
     * existente, false si no existe, y que la cantidad baje.
     */
    @Test
    void testEliminarTarea() {
        Tarea t = gestor.agregarTarea("Tarea a eliminar", "BAJA", false);

        assertEquals(1, gestor.cantidad());

        boolean eliminada = gestor.eliminarTarea(t.getId());
        boolean noExiste = gestor.eliminarTarea(999);

        assertTrue(eliminada);
        assertFalse(noExiste);
        assertEquals(0, gestor.cantidad());
    }

    /**
     * Verifica el comportamiento polimórfico de TareaUrgente: su tipo
     * debe ser "URGENTE" y su resumen debe comenzar con el marcador "[!] ".
     */
    @Test
    void testPolimorfismoTareaUrgente() {
        Tarea t = gestor.agregarTarea("Entregar informe", "ALTA", true);

        assertNotNull(t);
        assertTrue(t instanceof TareaUrgente);
        assertEquals("URGENTE", t.getTipo());
        assertTrue(t.obtenerResumen().startsWith("[!] "));
    }
}
