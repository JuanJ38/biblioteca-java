
> Sistema de gestión de biblioteca académica — Java Swing + MySQL

---

##  Funcionalidades

###  Login
- Roles: `ADMIN` y `USER`
- Reloj en vivo con fecha actual
- Navegación con tecla `Enter`

###  Libros
- Agregar, editar y eliminar libros
- Búsqueda en tiempo real
- Badge de estado: `Disponible` / `Prestado`

###  Préstamos
- Registrar y devolver préstamos
- Toggle Activos / Historial completo
- Combos se actualizan automáticamente

###  Usuarios
- Registro con correo único
- Avatar generado por inicial
- Historial de préstamos por usuario con doble clic

###  Extra
- Exportación a CSV
- Panel de inicio con estadísticas en tiempo real
- Sincronización automática entre módulos

---

##  Arquitectura
MVC + DAO + Service Layer

| Paquete | Responsabilidad |
|---------|----------------|
| `model` | Entidades: Libro, Usuario, Prestamo |
| `dao` | Acceso directo a base de datos (JDBC) |
| `service` | Lógica de negocio |
| `controller` | Coordinación vista ↔ servicios |
| `view` | Interfaz gráfica Swing |
| `util` | Componentes personalizados y utilidades |

---

##  Estructura
BibliotecaApp/
├── src/
│   ├── app/
│   ├── controller/
│   ├── dao/
│   ├── model/
│   ├── service/
│   ├── util/
│   └── view/
└── resources/

---

##  Configuración

**1. Crear la base de datos**
```sql
CREATE DATABASE biblioteca_db;
```

**2. Crear las tablas**
```sql
CREATE TABLE libros (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    titulo     VARCHAR(200) NOT NULL,
    autor      VARCHAR(150) NOT NULL,
    imagen     VARCHAR(500),
    resena     TEXT,
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE usuarios (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    correo VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE prestamos (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    id_libro         INT NOT NULL,
    id_usuario       INT NOT NULL,
    fecha_prestamo   DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion DATETIME,
    FOREIGN KEY (id_libro)   REFERENCES libros(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);
```

**3. Editar credenciales en `src/util/ConexionBD.java`**
```java


**4. Agregar el driver al Build Path**
mysql-connector-java-x.x.x.jar → Build Path → Add External JARs

**5. Ejecutar `Main.java`**

---

##  Credenciales demo

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| `admin` | `1234` | Administrador |
| `user` | `1234` | Lector |

---

##  Requisitos

- Java JDK 17 o superior
- MySQL 8.0 o superior
- MySQL Connector/J
- Eclipse o IntelliJ IDEA

---


