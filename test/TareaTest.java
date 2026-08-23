import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del modelo {@link Tarea}, ejercitadas a traves de sus
 * subclases concretas ({@link TareaNormal} y {@link TareaUrgente}), ya que
 * {@code Tarea} es abstracta y no puede instanciarse directamente.
 */
class TareaTest {

    @Test
    void testConstructorYGetters() {
        Tarea t = new TareaNormal(5, "Leer", "ALTA");
        assertEquals(5, t.getId());
        assertEquals("Leer", t.getNombre());
        assertEquals("ALTA", t.getPrioridad());
        assertFalse(t.isCompletado());
        assertEquals("Normal", t.getTipo());
    }

    @Test
    void testSetters() {
        Tarea t = new TareaNormal(1, "A", "BAJA");
        t.setId(9);
        t.setNombre("B");
        t.setPrioridad("MEDIA");
        t.setCompletado(true);
        assertEquals(9, t.getId());
        assertEquals("B", t.getNombre());
        assertEquals("MEDIA", t.getPrioridad());
        assertTrue(t.isCompletado());
    }

    @Test
    void testMarcarCompletadaYResumen() {
        Tarea t = new TareaNormal(2, "Estudiar", "ALTA");
        assertTrue(t.obtenerResumen().contains("ACTIVA"));
        t.marcarCompletada();
        assertTrue(t.isCompletado());
        assertTrue(t.obtenerResumen().contains("COMPLETADA"));
        // toString() debe coincidir con obtenerResumen()
        assertEquals(t.obtenerResumen(), t.toString());
    }

    @Test
    void testPolimorfismoResumenUrgente() {
        Tarea urgente = new TareaUrgente(3, "Emergencia", "ALTA");
        assertEquals("URGENTE", urgente.getTipo());
        // La TareaUrgente antepone "[!]" al resumen (polimorfismo por sobrescritura)
        assertTrue(urgente.obtenerResumen().startsWith("[!]"));
    }
}
