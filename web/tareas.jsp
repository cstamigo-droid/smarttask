<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Vista JSP de SmartTask (M5 AE2): el servlet manda la lista, la JSP la pinta. --%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>SmartTask Web</title>
<style>
  body{font-family:Segoe UI,Arial;margin:24px;color:#1b2a41;background:#f4f6f8}
  table{border-collapse:collapse;width:100%;background:#fff;margin-bottom:20px}
  th,td{border-bottom:1px solid #e0e6ec;padding:8px 10px;text-align:left;font-size:14px}
  th{background:#1b2a41;color:#fff;font-size:12px}
  .URGENTE{color:#c0182b;font-weight:bold}.NORMAL{color:#2f6f44}
  .done{color:#8a97a5;text-decoration:line-through}
  form{background:#fff;padding:14px;border:1px solid #e0e6ec;border-radius:8px;margin-bottom:12px}
  input,select{padding:6px;margin:4px 6px 4px 0}
  button{background:#1b2a41;color:#fff;border:0;padding:8px 14px;border-radius:5px;cursor:pointer}
</style>
</head>
<body>
<h1>✅ SmartTask</h1>
<p>Gestor de tareas — versión web con vista JSP (<c:out value="${tareas.size()}"/> tareas)</p>
<%-- M5 AE3: error de validación del servidor --%>
<c:if test="${param.error == 'nombre'}">
  <p style="color:#c0182b"><b>No se agregó la tarea:</b> la descripción no puede ir vacía.</p>
</c:if>
<%-- M5 L3: dato de la sesión del usuario --%>
<c:if test="${not empty sessionScope.completadasSesion}">
  <p>🏆 Completadas en esta sesión: <b><c:out value="${sessionScope.completadasSesion}"/></b></p>
</c:if>

<table>
  <tr><th>ID</th><th>Tarea</th><th>Tipo</th><th>Prioridad</th><th>Estado</th></tr>
  <%-- c:if (AE2) — estado vacío en vez de una tabla muerta --%>
  <c:if test="${empty tareas}">
    <tr><td colspan="5">No hay tareas registradas — agrega la primera abajo 👇</td></tr>
  </c:if>
  <c:forEach items="${tareas}" var="t">
    <tr class="${t.completado ? 'done' : ''}">
      <td><c:out value="${t.id}"/></td>
      <td><c:out value="${t.nombre}"/></td>
      <td class="${t.tipo}"><c:out value="${t.tipo}"/></td>
      <td><c:out value="${t.prioridad}"/></td>
      <td>
        <c:choose>
          <c:when test="${t.completado}">COMPLETADA</c:when>
          <c:otherwise>PENDIENTE</c:otherwise>
        </c:choose>
      </td>
    </tr>
  </c:forEach>
</table>

<form method="post" action="tareas">
  <b>Nueva tarea</b><br>
  <input type="hidden" name="accion" value="agregar">
  <input name="nombre" placeholder="Descripción" size="38" required>
  <select name="prioridad"><option>ALTA</option><option>MEDIA</option><option>BAJA</option></select>
  <label><input type="checkbox" name="urgente"> Urgente</label>
  <button>Agregar</button>
</form>

<form method="post" action="tareas">
  <b>Completar / eliminar</b><br>
  <input name="id" type="number" placeholder="ID" required>
  <button name="accion" value="completar">Completar</button>
  <button name="accion" value="eliminar">Eliminar</button>
</form>

</body>
</html>
