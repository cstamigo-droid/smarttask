import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos de SmartTask (M5 AE4, patrón DAO + singleton):
 * las tareas persisten en SQLite y sobreviven reinicios del servidor.
 */
public class TareaDAO {

    private static final String URL =
            "jdbc:sqlite:" + System.getProperty("smarttask.db", "C:/dev/smarttask.db");
    private static Connection conexion;

    private static synchronized Connection con() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");  // en Tomcat el driver no se descubre solo
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver SQLite no encontrado", e);
            }
            conexion = DriverManager.getConnection(URL);
        }
        return conexion;
    }

    public static void init() throws SQLException {
        try (Statement st = con().createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS tareas ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "nombre TEXT NOT NULL, prioridad TEXT NOT NULL, "
                    + "urgente INTEGER NOT NULL DEFAULT 0, "
                    + "completado INTEGER NOT NULL DEFAULT 0)");
        }
    }

    public static void insertar(String nombre, String prioridad, boolean urgente) throws SQLException {
        try (PreparedStatement ps = con().prepareStatement(
                "INSERT INTO tareas (nombre, prioridad, urgente) VALUES (?,?,?)")) {
            ps.setString(1, nombre);
            ps.setString(2, prioridad);
            ps.setInt(3, urgente ? 1 : 0);
            ps.executeUpdate();
        }
    }

    /** Reconstruye las tareas con su subclase correcta (polimorfismo + BD). */
    public static List<Tarea> listar() throws SQLException {
        List<Tarea> lista = new ArrayList<>();
        try (PreparedStatement ps = con().prepareStatement(
                "SELECT id, nombre, prioridad, urgente, completado FROM tareas");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tarea t = rs.getInt("urgente") == 1
                        ? new TareaUrgente(rs.getInt("id"), rs.getString("nombre"), rs.getString("prioridad"))
                        : new TareaNormal(rs.getInt("id"), rs.getString("nombre"), rs.getString("prioridad"));
                t.setCompletado(rs.getInt("completado") == 1);
                lista.add(t);
            }
        }
        return lista;
    }

    /** @return true si el id existía y quedó completado. */
    public static boolean completar(int id) throws SQLException {
        try (PreparedStatement ps = con().prepareStatement(
                "UPDATE tareas SET completado = 1 WHERE id = ? AND completado = 0")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public static void eliminar(int id) throws SQLException {
        try (PreparedStatement ps = con().prepareStatement("DELETE FROM tareas WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static boolean vacia() throws SQLException {
        try (Statement st = con().createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM tareas")) {
            rs.next();
            return rs.getInt(1) == 0;
        }
    }
}
