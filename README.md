1.  Descripción del Proyecto
BibliotecaApp es un sistema de escritorio desarrollado en Java con interfaz gráfica Swing para la gestión completa de una biblioteca académica. Permite administrar el inventario de libros, registrar usuarios, controlar préstamos y devoluciones, con un diseño visual moderno y una arquitectura en capas bien definida.

2.  Características Principales
Módulo de Autenticación
•	Login con roles: ADMIN y USER
•	Panel izquierdo con reloj en vivo y fecha actual
•	Validación de campos con mensaje de error
•	Soporte de tecla Enter para navegar entre campos

Módulo de Libros
•	Agregar libros con título, autor, URL de portada y reseña
•	Catálogo con tabla y búsqueda en tiempo real
•	Editar y eliminar libros con confirmación
•	Badge de estado: Disponible / Prestado
•	Tecla Enter para navegación entre campos del formulario

Módulo de Préstamos
•	Registro de préstamos con selector de libro disponible y usuario
•	Selector de días de préstamo con fecha referencial de devolución
•	Tabla de préstamos con toggle Activos / Historial completo
•	Devolución de libros con confirmación
•	Combos de libro y usuario se actualizan automáticamente

Módulo de Usuarios
•	Registro de usuarios con nombre y correo electrónico único
•	Tabla con avatar generado por inicial y color dinámico
•	Vista de préstamos por usuario con doble clic
•	Eliminación con validación de préstamos activos
•	Tecla Enter para navegación y guardado

Funciones Adicionales
•	Exportación a CSV de libros, préstamos y usuarios
•	Panel de inicio con estadísticas en tiempo real
•	Reloj en vivo en header y panel de préstamos
•	Sincronización automática entre todos los módulos

3.  Arquitectura del Proyecto
El proyecto sigue el patrón MVC (Modelo - Vista - Controlador) con capas adicionales de servicio y acceso a datos:

Paquete	Clase(s)	Responsabilidad
app	Main.java	Punto de entrada de la aplicación
model	Libro, Usuario, Prestamo	Entidades del dominio
dao	LibroDAO, UsuarioDAO, PrestamoDAO	Acceso directo a base de datos (JDBC)
service	LibroService, UsuarioService, PrestamoService	Lógica de negocio
controller	BibliotecaController	Coordinación entre vista y servicios
view	Login, MainFrame, LibroForm, UsuarioForm, PrestamoForm, ...	Interfaz gráfica Swing
util	BotonModerno, CampoTextoModerno, ConexionBD, Tema, ...	Componentes y utilidades reutilizables

4.  Estructura de Carpetas

BibliotecaApp1/
├── src/
│   ├── app/
│   │   └── Main.java
│   ├── controller/
│   │   └── BibliotecaController.java
│   ├── dao/
│   │   ├── LibroDAO.java
│   │   ├── PrestamoDAO.java
│   │   └── UsuarioDAO.java
│   ├── model/
│   │   ├── Libro.java
│   │   ├── Prestamo.java
│   │   └── Usuario.java
│   ├── service/
│   │   ├── LibroService.java
│   │   ├── PrestamoService.java
│   │   └── UsuarioService.java
│   ├── util/
│   │   ├── BotonModerno.java
│   │   ├── CampoTextoModerno.java
│   │   ├── CampoPasswordModerno.java
│   │   ├── ConexionBD.java
│   │   ├── ExportarExcel.java
│   │   ├── PanelRedondeado.java
│   │   └── Tema.java
│   └── view/
│       ├── LibroForm.java
│       ├── ListaLibrosFrame.java
│       ├── ListaUsuariosFrame.java
│       ├── Login.java
│       ├── MainFrame.java
│       ├── PanelInicio.java
│       ├── PrestamoForm.java
│       └── UsuarioForm.java
└── resources/
    ├── imgLog.jpeg
    └── login.jpeg

5.  Requisitos del Sistema
Componente	Versión mínima	Notas
Java JDK	17 o superior	Se requiere módulo java.desktop para Swing
MySQL	8.0 o superior	Base de datos relacional
MySQL Connector/J	8.0.x	Driver JDBC incluido en el classpath
IDE recomendado	Eclipse / IntelliJ IDEA	Proyecto configurado para Eclipse
Sistema operativo	Windows / Linux / macOS	Multiplataforma

6.  Configuración de Base de Datos
Crear la base de datos
CREATE DATABASE biblioteca_db;
USE biblioteca_db;

Tabla libros
CREATE TABLE libros (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    titulo     VARCHAR(200) NOT NULL,
    autor      VARCHAR(150) NOT NULL,
    imagen     VARCHAR(500),
    resena     TEXT,
    disponible BOOLEAN DEFAULT TRUE
);

Tabla usuarios
CREATE TABLE usuarios (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(150) NOT NULL,
    correo  VARCHAR(200) NOT NULL UNIQUE
);

Tabla prestamos
CREATE TABLE prestamos (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    id_libro         INT NOT NULL,
    id_usuario       INT NOT NULL,
    fecha_prestamo   DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion DATETIME,
    FOREIGN KEY (id_libro)   REFERENCES libros(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

Credenciales de acceso (demo)
INSERT INTO usuarios (nombre, correo) VALUES ('admin', '');
Las credenciales de login se validan en BibliotecaController. Por defecto:
Usuario	Contraseña	Rol
admin	1234	ADMIN — acceso completo
user	1234	USER — solo lectura y préstamos

7.  Cómo Ejecutar el Proyecto
Desde Eclipse
•	Importar el proyecto: File → Import → Existing Projects into Workspace
•	Agregar el conector JDBC al Build Path: clic derecho en el proyecto → Build Path → Add External JARs → seleccionar mysql-connector-java-x.x.x.jar
•	Verificar la cadena de conexión en ConexionBD.java
•	Ejecutar Main.java como Java Application

Cadena de conexión — ConexionBD.java
private static final String URL  = "jdbc:mysql://localhost:3306/biblioteca_db";
private static final String USER = "root";
private static final String PASS = "tu_contraseña";

8.  Mejoras Aplicadas — v3.1
#	Módulo	Mejora
1	ListaLibrosFrame	Columna Imagen (URL) ocultada — dato se conserva internamente
2	Login	Ícono candado correctamente centrado en el formulario
3	PrestamoForm	Combos de libro y usuario ahora son campos de instancia con método refrescarCombos()
4	PrestamoForm	Al devolver un libro el combo de libros disponibles se actualiza automáticamente
5	LibroForm	Al agregar libro se notifica a PrestamoForm para refrescar combos
6	UsuarioForm	Al agregar usuario se notifica a PrestamoForm para refrescar combos
7	LibroForm	Enter en Título mueve foco a Autor; Enter en Autor mueve a URL; Enter en URL guarda
8	UsuarioForm	Enter en Nombre mueve foco a Correo; Enter en Correo ejecuta guardado
9	MainFrame	Referencias cruzadas entre formularios correctamente inicializadas

9.  Patrones de Diseño Utilizados
Patrón	Dónde se aplica
MVC (Modelo-Vista-Controlador)	Separación completa entre capas model, view y controller
DAO (Data Access Object)	LibroDAO, UsuarioDAO, PrestamoDAO encapsulan el acceso a BD
Service Layer	LibroService, UsuarioService, PrestamoService contienen la lógica de negocio
Observer (parcial)	DocumentListener para búsqueda en tiempo real; Timer para reloj en vivo
Composite	Componentes personalizados: BotonModerno, CampoTextoModerno, PanelRedondeado
Reference Passing	MainFrame inyecta referencias cruzadas para sincronizar módulos

10.  Tecnologías y Librerías
Tecnología	Uso en el proyecto
Java SE 17+	Lenguaje principal de desarrollo
Java Swing	Interfaz gráfica de usuario (GUI)
JDBC	Conexión y operaciones con la base de datos
MySQL 8	Almacenamiento persistente de datos
CardLayout	Navegación entre paneles sin abrir nuevas ventanas
DefaultTableModel	Modelo de datos para las tablas JTable
javax.swing.Timer	Reloj en vivo y actualizaciones periódicas
DocumentListener	Búsqueda en tiempo real sobre tablas
TableCellRenderer	Badges personalizados de estado en tablas
