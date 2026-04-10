package view;

import controller.BibliotecaController;
import model.Libro;
import model.Prestamo;
import model.Usuario;
import util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrestamoForm extends JPanel {

    private final BibliotecaController controller;
    private DefaultTableModel modeloTabla;
    private JTable tablaPrestamos;
    private boolean mostrarSoloActivos = true;   // por defecto muestra activos

    // Referencia al panel de libros para refrescarlo al devolver
    private ListaLibrosFrame listaLibrosRef = null;

    public void setListaLibrosRef(ListaLibrosFrame ref) {
        this.listaLibrosRef = ref;
    }

    public PrestamoForm(BibliotecaController controller) {
        this.controller = controller;
        setBackground(new Color(248, 250, 252));
        setLayout(new BorderLayout(0, 0));
        add(construirFormulario(),     BorderLayout.NORTH);
        add(construirListaPrestamos(), BorderLayout.CENTER);
    }

    // ── FORMULARIO NUEVO PRÉSTAMO ───────────────────────────────────
    private JPanel construirFormulario() {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
        wrapper.setOpaque(false);

        PanelRedondeado card = new PanelRedondeado(Color.WHITE, 16);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(24, 40, 24, 40));
        card.setPreferredSize(new Dimension(720, 300));

        JLabel icono = new JLabel("🔄");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icono.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel titulo = new JLabel("Registrar Nuevo Préstamo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Tema.TEXT_DARK);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sub = new JLabel("Selecciona el libro disponible y el usuario del sistema");
        sub.setFont(Tema.FONT_SMALL);
        sub.setForeground(Tema.TEXT_GRAY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        JSeparator sep = new JSeparator();
        sep.setForeground(Tema.BORDER_LIGHT);
        sep.setMaximumSize(new Dimension(640, 1));

        // Fila combos
        JPanel filaSelects = new JPanel(new GridLayout(1, 2, 16, 0));
        filaSelects.setOpaque(false);
        filaSelects.setMaximumSize(new Dimension(640, 85));
        filaSelects.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Combo libros disponibles
        JPanel colLibro = new JPanel(new BorderLayout(0, 5));
        colLibro.setOpaque(false);
        colLibro.add(crearLabel("📖  Libro disponible *"), BorderLayout.NORTH);
        JComboBox<Libro> comboLibros = crearComboGen();
        colLibro.add(comboLibros, BorderLayout.CENTER);

        // Combo usuarios
        JPanel colUsuario = new JPanel(new BorderLayout(0, 5));
        colUsuario.setOpaque(false);
        colUsuario.add(crearLabel("👤  Usuario *"), BorderLayout.NORTH);
        JComboBox<Usuario> comboUsuarios = crearComboGen();
        colUsuario.add(comboUsuarios, BorderLayout.CENTER);

        filaSelects.add(colLibro);
        filaSelects.add(colUsuario);

        // Cargar datos combos (solo disponibles para libros)
        cargarComboLibros(comboLibros);
        cargarComboUsuarios(comboUsuarios);

        // Fila días + reloj
        JPanel filaHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        filaHora.setOpaque(false);
        filaHora.setMaximumSize(new Dimension(640, 52));
        filaHora.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lDias = new JLabel("📅 Días de préstamo:");
        lDias.setFont(Tema.FONT_BOLD_SM);
        lDias.setForeground(Tema.TEXT_GRAY);
        JSpinner spinDias = new JSpinner(new SpinnerNumberModel(7, 1, 60, 1));
        spinDias.setPreferredSize(new Dimension(60, 36));
        spinDias.setFont(Tema.FONT_BODY);

        JLabel relojRef = new JLabel();
        relojRef.setFont(new Font("Consolas", Font.PLAIN, 11));
        relojRef.setForeground(Tema.TEXT_GRAY);
        SimpleDateFormat fmtR = new SimpleDateFormat("HH:mm:ss — dd/MM/yyyy");
        new javax.swing.Timer(1000, ev -> relojRef.setText("  ⏱ " + fmtR.format(new Date()))).start();
        relojRef.setText("  ⏱ " + fmtR.format(new Date()));

        filaHora.add(lDias); filaHora.add(spinDias); filaHora.add(relojRef);

        JLabel lblError = new JLabel(" ");
        lblError.setFont(Tema.FONT_SMALL);
        lblError.setForeground(Tema.DANGER);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        BotonModerno btnPrestar = new BotonModerno("✔  Confirmar Préstamo",
                new Color(245, 158, 11), new Color(180, 83, 9), 10);
        btnPrestar.setPreferredSize(new Dimension(640, 44));
        btnPrestar.setMaximumSize(new Dimension(640, 44));
        btnPrestar.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(icono);       card.add(Box.createVerticalStrut(4));
        card.add(titulo);      card.add(Box.createVerticalStrut(2));
        card.add(sub);         card.add(Box.createVerticalStrut(12));
        card.add(sep);         card.add(Box.createVerticalStrut(14));
        card.add(filaSelects); card.add(Box.createVerticalStrut(10));
        card.add(filaHora);    card.add(Box.createVerticalStrut(4));
        card.add(lblError);    card.add(Box.createVerticalStrut(10));
        card.add(btnPrestar);
        wrapper.add(card);

        // ── ACCIÓN PRESTAR ──
        btnPrestar.addActionListener(e -> {
            Libro   libro   = (Libro)   comboLibros.getSelectedItem();
            Usuario usuario = (Usuario) comboUsuarios.getSelectedItem();
            if (libro == null || libro.getId() == 0)     { lblError.setText("⚠  Selecciona un libro disponible");   return; }
            if (usuario == null || usuario.getId() == 0)  { lblError.setText("⚠  Selecciona un usuario");            return; }
            lblError.setText(" ");

            int    dias = (int) spinDias.getValue();
            boolean ok  = controller.prestarLibro(libro.getId(), usuario.getId());
            if (ok) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.DAY_OF_MONTH, dias);
                String dev = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
                String hoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                JOptionPane.showMessageDialog(this,
                        "<html><b>✅ Préstamo registrado correctamente</b><br><br>"
                        + "📖 Libro: <b>" + libro.getTitulo()     + "</b><br>"
                        + "👤 Usuario: <b>" + usuario.getNombre() + "</b><br>"
                        + "📅 Fecha préstamo: <b>" + hoy          + "</b><br>"
                        + "📆 Devolución referencial: <b>" + dev  + "</b></html>",
                        "Préstamo Confirmado", JOptionPane.INFORMATION_MESSAGE);
                cargarComboLibros(comboLibros);
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(this,
                        "El libro no está disponible o ya fue prestado.",
                        "Sin disponibilidad", JOptionPane.WARNING_MESSAGE);
            }
        });

        return wrapper;
    }

    // ── TABLA DE PRÉSTAMOS ──────────────────────────────────────────
    private JPanel construirListaPrestamos() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 16, 20));

        // Header con toggle activos/historial
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(new Color(15, 23, 42));
        hdr.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel lTit = new JLabel("📋  Préstamos Activos");
        lTit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lTit.setForeground(Color.WHITE);
        hdr.add(lTit, BorderLayout.WEST);

        // Toggle: Activos / Historial completo
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        togglePanel.setOpaque(false);
        JLabel lFiltro = new JLabel("Ver:");
        lFiltro.setForeground(new Color(148, 163, 184));
        lFiltro.setFont(Tema.FONT_SMALL);

        BotonModerno btnActivos   = new BotonModerno("● Activos",   Tema.PRIMARY, Tema.PRIMARY_DARK, 6);
        BotonModerno btnHistorial = new BotonModerno("☰ Historial", new Color(51,65,85), new Color(30,41,59), 6);
        btnActivos.setPreferredSize(new Dimension(110, 28));
        btnHistorial.setPreferredSize(new Dimension(110, 28));

        togglePanel.add(lFiltro); togglePanel.add(btnActivos); togglePanel.add(btnHistorial);
        hdr.add(togglePanel, BorderLayout.EAST);
        panel.add(hdr, BorderLayout.NORTH);

        // Columnas: #, Libro, Usuario, Fecha Préstamo, Devolución, Estado, idLibro (oculta)
        String[] cols = {"#", "Libro", "Usuario", "Fecha Préstamo", "Devolución", "Estado", "idLibro"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPrestamos = new JTable(modeloTabla);

        // Ocultar columna idLibro (índice 6)
        tablaPrestamos.getColumnModel().getColumn(6).setMinWidth(0);
        tablaPrestamos.getColumnModel().getColumn(6).setMaxWidth(0);
        tablaPrestamos.getColumnModel().getColumn(6).setWidth(0);

        tablaPrestamos.setRowHeight(38);
        tablaPrestamos.setFont(Tema.FONT_BODY);
        tablaPrestamos.setShowVerticalLines(false);
        tablaPrestamos.setShowHorizontalLines(true);
        tablaPrestamos.setGridColor(new Color(241, 245, 249));
        tablaPrestamos.setSelectionBackground(new Color(254, 243, 199));
        tablaPrestamos.setSelectionForeground(new Color(92, 40, 8));
        tablaPrestamos.setFocusable(false);

        JTableHeader th = tablaPrestamos.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setBackground(new Color(248, 250, 252));
        th.setForeground(new Color(71, 85, 105));
        th.setPreferredSize(new Dimension(0, 40));
        th.setReorderingAllowed(false);

        tablaPrestamos.getColumnModel().getColumn(0).setMaxWidth(45);
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(160);
        tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablaPrestamos.getColumnModel().getColumn(4).setPreferredWidth(150);
        tablaPrestamos.getColumnModel().getColumn(5).setPreferredWidth(110);

        // Renderer base
        tablaPrestamos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                if (!sel) {
                    setBackground(r % 2 == 0 ? Color.WHITE : new Color(250, 252, 255));
                    setForeground(new Color(30, 41, 59));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setFont(c == 0 ? new Font("Consolas", Font.PLAIN, 12) : Tema.FONT_BODY);
                setHorizontalAlignment(c == 0 ? SwingConstants.CENTER : SwingConstants.LEFT);
                return this;
            }
        });

        // Renderer badge Estado
        tablaPrestamos.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                p.setBackground(sel ? new Color(254,243,199) : (r%2==0 ? Color.WHITE : new Color(250,252,255)));
                boolean activo = "Activo".equals(v != null ? v.toString() : "");
                JLabel badge = new JLabel(activo ? "● Activo" : "✓ Devuelto") {
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(activo ? new Color(254, 226, 226) : new Color(220, 252, 231));
                        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                        super.paintComponent(g);
                        g2.dispose();
                    }
                };
                badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
                badge.setForeground(activo ? new Color(220, 38, 38) : new Color(22, 163, 74));
                badge.setOpaque(false);
                badge.setPreferredSize(new Dimension(100, 26));
                badge.setHorizontalAlignment(SwingConstants.CENTER);
                p.add(badge);
                return p;
            }
        });

        JScrollPane scroll = new JScrollPane(tablaPrestamos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        panel.add(scroll, BorderLayout.CENTER);

        // Footer con botones
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        footer.setBackground(new Color(248, 250, 252));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        BotonModerno btnDevolver = new BotonModerno("↩  Devolver Libro",
                new Color(16, 185, 129), new Color(5, 150, 105), 8);
        btnDevolver.setPreferredSize(new Dimension(170, 36));

        BotonModerno btnRefresh = new BotonModerno("↻  Actualizar",
                new Color(71, 85, 105), new Color(51, 65, 85), 8);
        btnRefresh.setPreferredSize(new Dimension(130, 36));

        JLabel lblCount = new JLabel();
        lblCount.setFont(Tema.FONT_SMALL);
        lblCount.setForeground(Tema.TEXT_GRAY);
        modeloTabla.addTableModelListener(e -> lblCount.setText(modeloTabla.getRowCount() + " registro(s)"));

        footer.add(btnDevolver); footer.add(btnRefresh);
        footer.add(Box.createHorizontalStrut(8)); footer.add(lblCount);
        panel.add(footer, BorderLayout.SOUTH);

        // Cargar inicial (activos)
        refrescarTabla();

        // ── TOGGLE ACTIVOS / HISTORIAL ──
        btnActivos.addActionListener(e -> {
            mostrarSoloActivos = true;
            lTit.setText("📋  Préstamos Activos");
            btnActivos.setBackground(Tema.PRIMARY);
            refrescarTabla();
        });
        btnHistorial.addActionListener(e -> {
            mostrarSoloActivos = false;
            lTit.setText("📋  Historial Completo de Préstamos");
            btnActivos.setBackground(new Color(51,65,85));
            refrescarTabla();
        });

        // ── ACCIÓN DEVOLVER ──
        btnDevolver.addActionListener(e -> {
            int fila = tablaPrestamos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona un préstamo activo de la tabla.",
                        "Sin selección", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String estado = modeloTabla.getValueAt(fila, 5).toString();
            if (!"Activo".equals(estado)) {
                JOptionPane.showMessageDialog(this,
                        "Este préstamo ya fue devuelto.", "Ya devuelto", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int    idPrestamo    = (int)    modeloTabla.getValueAt(fila, 0);
            String tituloLibro   = modeloTabla.getValueAt(fila, 1).toString();
            String nombreUsuario = modeloTabla.getValueAt(fila, 2).toString();
            int    idLibro       = (int)    modeloTabla.getValueAt(fila, 6);

            int conf = JOptionPane.showConfirmDialog(this,
                    "<html>¿Confirmar devolución?<br><br>"
                    + "📖 Libro: <b>" + tituloLibro   + "</b><br>"
                    + "👤 Usuario: <b>" + nombreUsuario + "</b></html>",
                    "Confirmar devolución", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (conf != JOptionPane.YES_OPTION) return;

            boolean ok = controller.devolverLibro(idPrestamo, idLibro);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "✅ Devolución registrada. El libro vuelve a estar disponible.",
                        "Devolución exitosa", JOptionPane.INFORMATION_MESSAGE);
                refrescarTabla();
                if (listaLibrosRef != null) listaLibrosRef.refrescar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar la devolución.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRefresh.addActionListener(e -> refrescarTabla());
        return panel;
    }

    // ── MÉTODOS AUXILIARES ──────────────────────────────────────────
    public void refrescarTabla() {
        modeloTabla.setRowCount(0);
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ArrayList<Prestamo> lista = mostrarSoloActivos
                ? controller.obtenerPrestamosActivos()
                : controller.obtenerPrestamos();

        for (Prestamo p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getTituloLibro(),
                p.getNombreUsuario(),
                p.getFechaPrestamo()   != null ? fmt.format(p.getFechaPrestamo())   : "—",
                p.getFechaDevolucion() != null ? fmt.format(p.getFechaDevolucion()) : "—",
                p.getFechaDevolucion() != null ? "Devuelto" : "Activo",
                p.getIdLibro()
            });
        }
    }

    private void cargarComboLibros(JComboBox<Libro> combo) {
        combo.removeAllItems();
        combo.addItem(new Libro(0, "— Selecciona libro disponible —", "", true));
        // Solo libros disponibles (como obtenerDisponibles() en Spring)
        for (Libro l : controller.obtenerLibrosDisponibles()) combo.addItem(l);
    }

    private void cargarComboUsuarios(JComboBox<Usuario> combo) {
        combo.removeAllItems();
        combo.addItem(new Usuario(0, "— Selecciona usuario —", ""));
        for (Usuario u : controller.obtenerUsuarios()) combo.addItem(u);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> JComboBox<T> crearComboGen() {
        JComboBox<T> c = new JComboBox<>();
        c.setFont(Tema.FONT_BODY);
        c.setBackground(Color.WHITE);
        c.setBorder(BorderFactory.createLineBorder(Tema.BORDER_LIGHT, 1));
        return c;
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Tema.FONT_BOLD_SM);
        l.setForeground(Tema.TEXT_GRAY);
        return l;
    }
}
