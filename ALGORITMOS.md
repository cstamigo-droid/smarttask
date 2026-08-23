# ALGORITMOS — SmartTask

Pseudocódigo simple de los tres algoritmos centrales de `GestorTareas`
(Lección 2: simular la lógica en pseudocódigo antes de programarla).

## 1) Agregar tarea

```
FUNCION agregarTarea(nombre, prioridad, urgente):
    SI urgente ES verdadero ENTONCES
        nueva <- crear TareaUrgente(siguienteId, nombre, prioridad)
    SINO
        nueva <- crear TareaNormal(siguienteId, nombre, prioridad)
    FIN SI

    siguienteId <- siguienteId + 1
    agregar nueva a la lista "tareas"
    RETORNAR nueva
FIN FUNCION
```

Idea clave: el id se asigna automáticamente y de forma incremental, y el
tipo de objeto que se crea (Normal o Urgente) depende de un solo flag
booleano — el resto del sistema no necesita saber cuál subclase es,
gracias al polimorfismo.

## 2) Listar tareas (activas / completadas)

```
FUNCION listarActivas():
    resultado <- lista vacía
    PARA CADA tarea EN tareas HACER
        SI tarea.completado ES falso ENTONCES
            agregar tarea a resultado
        FIN SI
    FIN PARA
    RETORNAR resultado
FIN FUNCION

FUNCION listarCompletadas():
    resultado <- lista vacía
    PARA CADA tarea EN tareas HACER
        SI tarea.completado ES verdadero ENTONCES
            agregar tarea a resultado
        FIN SI
    FIN PARA
    RETORNAR resultado
FIN FUNCION
```

Idea clave: se recorre la lista una vez y se filtra según el estado
`completado`, sin modificar la lista original.

## 3) Eliminar tarea (y, de forma análoga, marcar como completada)

```
FUNCION eliminarTarea(id):
    tarea <- buscarPorId(id)
    SI tarea ES nulo ENTONCES
        RETORNAR falso
    FIN SI
    quitar tarea de la lista "tareas"
    RETORNAR verdadero
FIN FUNCION

FUNCION buscarPorId(id):
    PARA CADA tarea EN tareas HACER
        SI tarea.id ES IGUAL A id ENTONCES
            RETORNAR tarea
        FIN SI
    FIN PARA
    RETORNAR nulo
FIN FUNCION
```

Idea clave: primero se busca la tarea por id (función reutilizada también
por `marcarComoCompletada`); si no existe, se retorna `falso` sin tocar
la lista; si existe, se realiza la operación y se retorna `verdadero`.
Este patrón (buscar -> validar -> actuar -> retornar booleano de éxito)
es el mismo que usa `marcarComoCompletada(id)`, solo que en vez de
eliminar, invoca `tarea.marcarCompletada()`.
