# Documentacion de la API CrudGyl

API REST desarrollada con Spring Boot para gestionar clientes, tipos de producto, productos, ventas y detalles de venta. Usa Spring Web MVC, Spring Data JPA, Hibernate y MySQL.

Base URL local:

```text
http://localhost:8080
```

Cuando se ejecuta con Docker Compose, la API queda expuesta en el puerto configurado por `BACK_PORT`, actualmente `8080`.

## Arquitectura general

La aplicacion esta organizada en capas:

- `controller`: expone los endpoints HTTP y recibe/envia JSON.
- `service`: contiene la logica de negocio.
- `repository`: accede a MySQL mediante Spring Data JPA.
- `entity`: representa las tablas de base de datos.
- `dto/request`: define los cuerpos JSON que recibe la API.
- `dto/response`: define los cuerpos JSON que devuelve la API.
- `mapper`: convierte entre entidades JPA y DTOs.

Flujo general de una peticion:

```text
Cliente HTTP -> Controller -> Service -> Repository -> MySQL
                         <- DTO Response <- Entity
```

## Configuracion de base de datos

Archivo principal:

```text
src/main/resources/application.properties
```

Propiedades usadas:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/producto_gyl?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:appuser}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:secret123}
server.port=${BACK_PORT:8080}
```

Esto permite dos formas de ejecucion:

- Local/IntelliJ: usa los valores por defecto si no existen variables de entorno.
- Docker Compose: recibe `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` y `BACK_PORT` desde `docker-compose.yml`.

Para levantar MySQL con Docker:

```powershell
docker compose up -d mysql
```

Para levantar toda la aplicacion:

```powershell
docker compose up -d
```

## Validaciones y errores

Los DTOs de request usan validaciones de Jakarta Validation:

- `@NotNull`: el campo no puede ser `null`.
- `@NotBlank`: el texto no puede estar vacio.
- `@Email`: el texto debe tener formato de email.
- `@Positive`: el numero debe ser mayor a cero.
- `@Min`: el numero debe ser mayor o igual al minimo indicado.
- `@NotEmpty`: la lista no puede estar vacia.

Si un recurso no existe, los servicios lanzan `RecursoNoEncontradoException`.

Si no hay stock suficiente para una venta o detalle de venta, se lanza `IllegalArgumentException` con un mensaje similar a:

```text
Stock insuficiente para el producto {nombreProducto}
```

Actualmente no hay un `@ControllerAdvice` global en el proyecto, por lo que Spring maneja estas excepciones con su respuesta por defecto.

## Clientes

Recurso base:

```text
/api/clientes
```

Un cliente representa a una persona que puede realizar ventas. Tiene borrado logico mediante el campo interno `activo`. Al eliminar un cliente, no se borra fisicamente de la base: se marca como inactivo.

### Campos de entrada

```json
{
  "nombreCliente": "Juan",
  "apellidoCliente": "Perez",
  "correoCliente": "juan.perez@mail.com",
  "telefonoCliente": "1122334455",
  "direccionCliente": "Av. Siempre Viva 123"
}
```

Validaciones:

- `nombreCliente`: obligatorio y no vacio.
- `apellidoCliente`: obligatorio y no vacio.
- `correoCliente`: obligatorio, no vacio y con formato de email.
- `telefonoCliente`: obligatorio y no vacio.
- `direccionCliente`: obligatorio y no vacio.

### Respuesta

```json
{
  "idCliente": 1,
  "nombreCliente": "Juan",
  "apellidoCliente": "Perez",
  "correoCliente": "juan.perez@mail.com",
  "telefonoCliente": "1122334455",
  "direccionCliente": "Av. Siempre Viva 123"
}
```

### Endpoints

| Metodo | Ruta | Descripcion | Estado |
|---|---|---|---|
| POST | `/api/clientes` | Crea un cliente | `201 Created` |
| GET | `/api/clientes` | Lista clientes activos | `200 OK` |
| GET | `/api/clientes/id/{idCliente}` | Busca un cliente activo por ID | `200 OK` |
| PUT | `/api/clientes/{idCliente}` | Actualiza un cliente activo | `202 Accepted` |
| DELETE | `/api/clientes/id/{idCliente}` | Marca el cliente como inactivo | `202 Accepted` |
| GET | `/api/clientes/nombre/{nombreCliente}` | Busca clientes activos por nombre exacto | `200 OK` |

### Ejemplo: crear cliente

```http
POST /api/clientes
Content-Type: application/json
```

```json
{
  "nombreCliente": "Juan",
  "apellidoCliente": "Perez",
  "correoCliente": "juan.perez@mail.com",
  "telefonoCliente": "1122334455",
  "direccionCliente": "Av. Siempre Viva 123"
}
```

## Tipos de producto

Recurso base:

```text
/api/tipos-producto
```

Un tipo de producto clasifica productos. Por ejemplo: bebidas, limpieza, almacen, etc.

### Campos de entrada

```json
{
  "nombreTipoProducto": "Bebidas"
}
```

Validaciones:

- `nombreTipoProducto`: obligatorio y no vacio.

### Respuesta

```json
{
  "idTipoProducto": 1,
  "nombreTipoProducto": "Bebidas"
}
```

### Endpoints

| Metodo | Ruta | Descripcion | Estado |
|---|---|---|---|
| POST | `/api/tipos-producto` | Crea un tipo de producto | `201 Created` |
| GET | `/api/tipos-producto` | Lista todos los tipos de producto | `200 OK` |
| GET | `/api/tipos-producto/id/{idTipoProducto}` | Busca un tipo por ID | `200 OK` |
| PUT | `/api/tipos-producto/{idTipoProducto}` | Actualiza un tipo | `202 Accepted` |
| DELETE | `/api/tipos-producto/id/{idTipoProducto}` | Elimina fisicamente el tipo | `202 Accepted` |
| GET | `/api/tipos-producto/nombre/{nombreTipoProducto}` | Busca tipos por nombre exacto | `200 OK` |

## Productos

Recurso base:

```text
/api/productos
```

Un producto pertenece a un tipo de producto y tiene precio, stock y estado interno `activo`. Al eliminar un producto, se hace borrado logico: se marca como inactivo.

### Campos de entrada

```json
{
  "nombre": "Coca Cola 1.5L",
  "precio": 2500.0,
  "stock": 20,
  "idTipoProducto": 1
}
```

Validaciones:

- `nombre`: obligatorio y no vacio.
- `precio`: obligatorio y mayor a cero.
- `stock`: obligatorio y mayor o igual a cero.
- `idTipoProducto`: obligatorio. Debe existir un tipo de producto con ese ID.

### Respuesta

```json
{
  "id": 1,
  "nombre": "Coca Cola 1.5L",
  "precio": 2500.0,
  "stock": 20,
  "tipoProducto": {
    "idTipoProducto": 1,
    "nombreTipoProducto": "Bebidas"
  }
}
```

### Endpoints

| Metodo | Ruta | Descripcion | Estado |
|---|---|---|---|
| POST | `/api/productos` | Crea un producto asociado a un tipo existente | `201 Created` |
| GET | `/api/productos` | Lista productos | `200 OK` |
| GET | `/api/productos/id/{id}` | Busca un producto activo por ID | `200 OK` |
| PUT | `/api/productos/{id}` | Actualiza un producto | `202 Accepted` |
| DELETE | `/api/productos/id/{id}` | Marca el producto como inactivo | `202 Accepted` |
| GET | `/api/productos/nombre/{nombre}` | Busca productos por nombre exacto | `200 OK` |

Nota: el metodo de listado actual usa `findAll()`, por lo que puede devolver productos activos e inactivos. La busqueda por ID exige que el producto este activo.

## Ventas

Recurso base:

```text
/api/ventas
```

Una venta pertenece a un cliente y contiene una lista de productos vendidos. La venta calcula automaticamente:

- `fechaVenta`: fecha actual del servidor.
- `totalVenta`: suma de subtotales de sus detalles.
- `subtotal` de cada item: `precioUnitario * cantidad`.

Al crear una venta, se descuenta stock de cada producto. Si no hay stock suficiente, la venta falla.

### Campos de entrada

```json
{
  "idCliente": 1,
  "items": [
    {
      "idProducto": 1,
      "cantidad": 2
    },
    {
      "idProducto": 2,
      "cantidad": 1
    }
  ]
}
```

Validaciones:

- `idCliente`: obligatorio. Debe existir un cliente con ese ID.
- `items`: obligatorio y no puede estar vacio.
- `items[].idProducto`: obligatorio. Debe existir un producto activo con ese ID.
- `items[].cantidad`: obligatoria y mayor o igual a 1.

### Respuesta

```json
{
  "idVenta": 1,
  "fechaVenta": "2026-05-07",
  "totalVenta": 7500.00,
  "idCliente": 1,
  "nombreCliente": "Juan",
  "apellidoCliente": "Perez",
  "detalles": [
    {
      "idDetalleVenta": 1,
      "cantidad": 2,
      "precioUnitario": 2500.00,
      "subtotal": 5000.00,
      "idVenta": 1,
      "idProducto": 1,
      "nombreProducto": "Coca Cola 1.5L"
    }
  ]
}
```

### Endpoints

| Metodo | Ruta | Descripcion | Estado |
|---|---|---|---|
| POST | `/api/ventas` | Crea una venta con uno o mas items | `201 Created` |
| GET | `/api/ventas` | Lista todas las ventas con sus detalles | `200 OK` |
| GET | `/api/ventas/id/{idVenta}` | Busca una venta por ID | `200 OK` |
| PUT | `/api/ventas/{idVenta}` | Reemplaza cliente e items de una venta | `202 Accepted` |
| DELETE | `/api/ventas/id/{idVenta}` | Elimina la venta y sus detalles | `202 Accepted` |
| GET | `/api/ventas/cliente/{idCliente}` | Lista ventas de un cliente | `200 OK` |

### Reglas de negocio de ventas

Al crear una venta:

1. Busca el cliente por `idCliente`.
2. Crea una venta con fecha actual y total inicial `0`.
3. Por cada item:
   - Busca el producto activo.
   - Valida que haya stock suficiente.
   - Descuenta el stock.
   - Guarda el detalle con precio unitario y subtotal.
4. Calcula el total final de la venta.
5. Devuelve la venta con sus detalles.

Al actualizar una venta:

1. Busca la venta existente.
2. Busca el nuevo cliente.
3. Restaura el stock de los detalles anteriores.
4. Elimina los detalles anteriores.
5. Crea los nuevos detalles.
6. Descuenta el stock nuevo.
7. Recalcula el total.

Al eliminar una venta:

1. Busca la venta.
2. Restaura el stock de sus productos.
3. Elimina sus detalles.
4. Elimina la venta fisicamente.

## Detalles de venta

Recurso base:

```text
/api/detalles-venta
```

Un detalle de venta representa un producto dentro de una venta, con cantidad, precio unitario y subtotal. Normalmente se crean automaticamente al crear una venta, pero tambien existen endpoints para administrarlos manualmente.

### Campos de entrada

```json
{
  "cantidad": 2,
  "idVenta": 1,
  "idProducto": 1
}
```

Validaciones:

- `cantidad`: obligatoria y mayor o igual a 1.
- `idVenta`: obligatorio. Debe existir una venta con ese ID.
- `idProducto`: obligatorio. Debe existir un producto activo con ese ID.

### Respuesta

```json
{
  "idDetalleVenta": 1,
  "cantidad": 2,
  "precioUnitario": 2500.00,
  "subtotal": 5000.00,
  "idVenta": 1,
  "idProducto": 1,
  "nombreProducto": "Coca Cola 1.5L"
}
```

### Endpoints

| Metodo | Ruta | Descripcion | Estado |
|---|---|---|---|
| POST | `/api/detalles-venta` | Agrega un detalle a una venta existente | `201 Created` |
| GET | `/api/detalles-venta` | Lista todos los detalles | `200 OK` |
| GET | `/api/detalles-venta/id/{idDetalleVenta}` | Busca un detalle por ID | `200 OK` |
| PUT | `/api/detalles-venta/{idDetalleVenta}` | Actualiza venta, producto o cantidad del detalle | `202 Accepted` |
| DELETE | `/api/detalles-venta/id/{idDetalleVenta}` | Elimina el detalle | `202 Accepted` |
| GET | `/api/detalles-venta/venta/{idVenta}` | Lista detalles de una venta | `200 OK` |

### Reglas de negocio de detalles

Al crear un detalle:

1. Busca la venta.
2. Busca el producto activo.
3. Valida stock suficiente.
4. Descuenta stock.
5. Calcula subtotal.
6. Guarda el detalle.
7. Recalcula el total de la venta.

Al actualizar un detalle:

1. Restaura stock del producto anterior.
2. Valida stock del producto nuevo.
3. Descuenta el nuevo stock.
4. Actualiza cantidad, producto y venta.
5. Recalcula el total de la venta anterior y de la venta nueva.

Al eliminar un detalle:

1. Restaura el stock del producto.
2. Elimina el detalle fisicamente.
3. Recalcula el total de la venta.

## Modelo de datos

Relaciones principales:

```text
TipoProducto 1 --- N Producto
Cliente      1 --- N Venta
Venta        1 --- N DetalleVenta
Producto     1 --- N DetalleVenta
```

Tablas principales:

| Entidad | Tabla | Descripcion |
|---|---|---|
| `Cliente` | `clientes` | Clientes de la aplicacion |
| `TipoProducto` | `tipo_producto` | Categorias o tipos de productos |
| `Producto` | `productos` | Productos con precio, stock y tipo |
| `Venta` | `venta` | Cabecera de venta: cliente, fecha y total |
| `DetalleVenta` | `detalle_venta` | Lineas de venta: producto, cantidad, precio y subtotal |

## Orden recomendado para probar la API

1. Crear un tipo de producto.
2. Crear un producto usando el `idTipoProducto` creado.
3. Crear un cliente.
4. Crear una venta usando el `idCliente` y uno o mas `idProducto`.
5. Consultar la venta por ID.
6. Verificar que el stock del producto haya disminuido.

## Ejemplo completo de prueba

### 1. Crear tipo de producto

```http
POST /api/tipos-producto
Content-Type: application/json
```

```json
{
  "nombreTipoProducto": "Bebidas"
}
```

### 2. Crear producto

```http
POST /api/productos
Content-Type: application/json
```

```json
{
  "nombre": "Agua mineral 1.5L",
  "precio": 1200.0,
  "stock": 10,
  "idTipoProducto": 1
}
```

### 3. Crear cliente

```http
POST /api/clientes
Content-Type: application/json
```

```json
{
  "nombreCliente": "Ana",
  "apellidoCliente": "Gomez",
  "correoCliente": "ana.gomez@mail.com",
  "telefonoCliente": "1166778899",
  "direccionCliente": "San Martin 500"
}
```

### 4. Crear venta

```http
POST /api/ventas
Content-Type: application/json
```

```json
{
  "idCliente": 1,
  "items": [
    {
      "idProducto": 1,
      "cantidad": 2
    }
  ]
}
```

Respuesta esperada: venta creada con total `2400.00` si el precio unitario del producto era `1200.0`.

## Observaciones tecnicas

- Clientes y productos usan borrado logico mediante `activo = false`.
- Tipos de producto, ventas y detalles se eliminan fisicamente.
- La busqueda por nombre es exacta, no parcial.
- La API no define paginacion; los listados devuelven listas completas.
- La aplicacion usa `spring.jpa.hibernate.ddl-auto=update`, por lo que Hibernate actualiza el esquema automaticamente al iniciar.
- `spring.jpa.show-sql=true` muestra las consultas SQL en logs.
