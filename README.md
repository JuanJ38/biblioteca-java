
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

--


---

<img width="1786" height="1078" alt="image" src="https://github.com/user-attachments/assets/2f3c15a7-b748-49bb-84f8-1bfb4fa05bdd" />

############

<img width="2250" height="1293" alt="image" src="https://github.com/user-attachments/assets/357ef9f6-6fd3-4516-a0ce-8d1d6142cbe4" />
##################
<img width="2243" height="1298" alt="image" src="https://github.com/user-attachments/assets/a66ed82e-b693-45d9-bbe1-76415cf8b917" />

###############
<img width="2256" height="1304" alt="image" src="https://github.com/user-attachments/assets/5a46082a-7635-42f1-856a-7ccf7f2603d5" />
################
<img width="2264" height="1342" alt="image" src="https://github.com/user-attachments/assets/e379577c-762c-48bf-937f-0d49142ccf86" />

###############
<img width="2262" height="1345" alt="image" src="https://github.com/user-attachments/assets/42a35406-ca58-41b3-8de3-6cdbb83d840a" />
##################
<img width="2276" height="1333" alt="image" src="https://github.com/user-attachments/assets/c50875f5-da3d-4938-87fd-c723aeeaa5c3" />
####################
<img width="2268" height="1325" alt="image" src="https://github.com/user-attachments/assets/eee90c0b-c476-4d6c-82b8-7ade991722d3" />







