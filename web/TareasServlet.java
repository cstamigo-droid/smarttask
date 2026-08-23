import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Capa web de SmartTask (M5 AE1): el gestor de tareas del Módulo 4 ahora se
 * usa desde el navegador en vez de la consola. Mismo modelo de dominio
 * (GestorTareas / Tarea / TareaNormal / TareaUrgente), nueva vista.
 * doGet lista las tareas; doPost agrega, completa o elimina.
 */
@WebServlet("/tareas")
public class TareasServlet extends HttpServlet {

    // M5 AE4: las tareas viven en la BD (TareaDAO) y sobreviven reinicios.
    static {
        try {
            TareaDAO.init();
            if (TareaDAO.vacia()) {
                TareaDAO.insertar("Revisar bandeja de postventa", "ALTA", true);
                TareaDAO.insertar("Preparar clase del bootcamp", "MEDIA", false);
            }
        } catch (java.sql.SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // M5 AE2 + AE4: la BD es la fuente de verdad; la JSP presenta
        try {
            req.setAttribute("tareas", TareaDAO.listar());
        } catch (java.sql.SQLException e) {
            throw new ServletException("Error leyendo tareas de la BD", e);
        }
        // M5 L5 (MVC): la vista vive en WEB-INF — solo se llega por este controlador
        req.getRequestDispatcher("/WEB-INF/tareas.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        try {
            if ("agregar".equals(accion)) {
                String nombre = req.getParameter("nombre");
                // M5 AE3: validar en el servidor antes de tocar la BD
                if (nombre == null || nombre.trim().isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/tareas?error=nombre");
                    return;
                }
                TareaDAO.insertar(nombre.trim(),
                        req.getParameter("prioridad"),
                        req.getParameter("urgente") != null);
            } else {
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    if ("completar".equals(accion)) {
                        if (TareaDAO.completar(id)) {
                            // M5 L3: contador de productividad por SESIÓN de usuario
                            Integer c = (Integer) req.getSession().getAttribute("completadasSesion");
                            req.getSession().setAttribute("completadasSesion", c == null ? 1 : c + 1);
                        }
                    } else if ("eliminar".equals(accion)) TareaDAO.eliminar(id);
                } catch (NumberFormatException e) { /* id inválido: recargar sin cambios */ }
            }
        } catch (java.sql.SQLException e) {
            throw new ServletException("Error escribiendo en la BD de tareas", e);
        }
        resp.sendRedirect(req.getContextPath() + "/tareas");
    }
}
