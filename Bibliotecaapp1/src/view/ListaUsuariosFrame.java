package view;

import controller.BibliotecaController;
import model.Prestamo;
import model.Usuario;
import util.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListaUsuariosFrame extends JPanel {

    private final BibliotecaController controller;
    private DefaultTableModel modelo;
    private JTable tabla;
    private boolean placeholderActivo = true;

    public ListaUsuariosFrame(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(248, 250, 252));

        add(construirHeader(),  BorderLayout.NORTH);
        add(construirTabla(),   BorderLayout.CENTER);
        add(construirBotones(), BorderLayout.SOUTH);

        cargarUsuarios();
    }

    private JPanel construirHeader() {
        JPanel h = new JPanel(new BorderLayout(12, 0));
        h.setBackground(new Color(15, 23, 42));
        h.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        JLabel ico = new JLabel("👥");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        JLabel lbl = new JLabel("Gestión de Usuarios");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        JLabel hint = new JLabel("  · Doble clic para ver préstamos del usuario");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(new Color(100, 116, 139));
        left.add(ico); left.add(lbl); left.add(hint);

        JTextField txtBuscar = new JTextField(22);
        txtBuscar.setFont(Tema.FONT_BODY);
        txtBuscar.setPreferredSize(new Dimension(260, 36));
        txtBuscar.setBackground(new Color(30, 41, 59));
        txtBuscar.setForeground(new Color(200, 210, 220));
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
        txtBuscar.setText("🔍  Buscar por nombre o correo...");

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (placeholderActivo) { txtBuscar.setText(""); placeholderActivo = false;
                    txtBuscar.setForeground(Color.WHITE); }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBuscar.getText().isEmpty()) {
                    placeholderActivo = true;
                    txtBuscar.setText("🔍  Buscar por nombre o correo...");
                    txtBuscar.setForeground(new Color(200,210,220));
                    cargarUsuarios();
                }
            }
        });
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filtrar(txtBuscar.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filtrar(txtBuscar.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(txtBuscar.getText()); }
        });

        h.add(left,      BorderLayout.WEST);
        h.add(txtBuscar, BorderLayout.EAST);
        return h;
    }

    private JScrollPane construirTabla() {
        String[] cols = {"#", "Usuario", "Correo electrónico", "Préstamos activos"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(46);
        tabla.setFont(Tema.FONT_BODY);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new Color(241, 245, 249));
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionBackground(new Color(239, 246, 255));
        tabla.setSelectionForeground(new Color(29, 78, 216));
        tabla.setFocusable(false);

        JTableHeader th = tabla.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setBackground(new Color(248, 250, 252));
        th.setForeground(new Color(71, 85, 105));
        th.setPreferredSize(new Dimension(0, 42));
        th.setReorderingAllowed(false);
        th.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(226,232,240)));

        tabla.getColumnModel().getColumn(0).setMaxWidth(55);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(260);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(280);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(130);

        // Renderer ID
        tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(new Font("Consolas", Font.PLAIN, 12));
                if (!sel) setBackground(r % 2 == 0 ? Color.WHITE : new Color(250,252,255));
                return this;
            }
        });

        // Renderer Nombre con avatar
        tabla.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
                p.setBackground(sel ? new Color(239,246,255)
                    : (r % 2 == 0 ? Color.WHITE : new Color(250,252,255)));

                String nombre  = v != null ? v.toString() : "?";
                String inicial = nombre.isEmpty() ? "?" : String.valueOf(nombre.charAt(0)).toUpperCase();

                Color[] COLORS = {
                    new Color(16,185,129), new Color(59,130,246),
                    new Color(245,158,11), new Color(239,68,68),
                    new Color(139,92,246)
                };
                Color bg = COLORS[Math.abs(nombre.hashCode()) % COLORS.length];

                JLabel av = new JLabel(inicial) {
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(bg);
                        g2.fillOval(0,0,getWidth()-1,getHeight()-1);
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
                        FontMetrics fm = g2.getFontMetrics();
                        int x = (getWidth() - fm.stringWidth(inicial)) / 2;
                        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                        g2.drawString(inicial, x, y);
                        g2.dispose();
                    }
                };
                av.setPreferredSize(new Dimension(30, 30));
                av.setOpaque(false);

                JLabel lNombre = new JLabel(nombre);
                lNombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                lNombre.setForeground(new Color(30, 41, 59));

                p.add(av); p.add(lNombre);
                return p;
            }
        });

        // Renderer correo
        tabla.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setForeground(sel ? new Color(29,78,216) : new Color(59,130,246));
                setFont(Tema.FONT_BODY);
                if (!sel) setBackground(r % 2 == 0 ? Color.WHITE : new Color(250,252,255));
                setBorder(BorderFactory.createEmptyBorder(0,12,0,12));
                return this;
            }
        });

        // Renderer préstamos activos (badge)
        tabla.getColumnModel().getColumn(3).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                p.setBackground(sel ? new Color(239,246,255) : (r%2==0 ? Color.WHITE : new Color(250,252,255)));
                int cant = v instanceof Integer ? (Integer) v : 0;
                JLabel badge = new JLabel(cant > 0 ? "● " + cant + " activo(s)" : "✓ Sin activos") {
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(cant > 0 ? new Color(254,226,226) : new Color(220,252,231));
                        g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,20,20);
                        super.paintComponent(g);
                        g2.dispose();
                    }
                };
                badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
                badge.setForeground(cant > 0 ? new Color(220,38,38) : new Color(22,163,74));
                badge.setOpaque(false);
                badge.setPreferredSize(new Dimension(120, 26));
                badge.setHorizontalAlignment(SwingConstants.CENTER);
                p.add(badge);
                return p;
            }
        });

        // Doble clic: mostrar préstamos del usuario
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    if (fila == -1) return;
                    int    id     = (int)    modelo.getValueAt(fila, 0);
                    String nombre = (String) modelo.getValueAt(fila, 1);
                    mostrarPrestamosDeUsuario(id, nombre);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    /** Muestra en un diálogo los préstamos del usuario seleccionado */
    private void mostrarPrestamosDeUsuario(int idUsuario, String nombreUsuario) {
        ArrayList<Prestamo> prestamos = controller.obtenerPrestamosPorUsuario(idUsuario);
        if (prestamos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "<html>El usuario <b>" + nombreUsuario + "</b> no tiene préstamos registrados.</html>",
                "Sin préstamos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] cols = {"#", "Libro", "Fecha préstamo", "Devolución", "Estado"};
        DefaultTableModel mdl = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Prestamo p : prestamos) {
            mdl.addRow(new Object[]{
                p.getId(),
                p.getTituloLibro(),
                p.getFechaPrestamo() != null ? fmt.format(p.getFechaPrestamo()) : "—",
                p.getFechaDevolucion() != null ? fmt.format(p.getFechaDevolucion()) : "—",
                p.getFechaDevolucion() != null ? "Devuelto" : "Activo"
            });
        }
        JTable tbl = new JTable(mdl);
        tbl.setRowHeight(34);
        tbl.setFont(Tema.FONT_BODY);
        tbl.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tbl.setFocusable(false);

        JScrollPane scroll = new JScrollPane(tbl);
        scroll.setPreferredSize(new Dimension(620, 250));

        JOptionPane.showMessageDialog(this,
            new Object[]{"<html><b>📋 Préstamos de: " + nombreUsuario + "</b> — " + prestamos.size() + " registro(s)</html>", scroll},
            "Historial de préstamos", JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel construirBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        p.setBackground(new Color(248, 250, 252));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        BotonModerno btnEliminar = new BotonModerno("🗑  Eliminar", Tema.DANGER, new Color(185,28,28), 8);
        BotonModerno btnRefresh  = new BotonModerno("↻  Actualizar", new Color(71,85,105), new Color(51,65,85), 8);
        BotonModerno btnVerPrest = new BotonModerno("📋  Ver Préstamos", new Color(59,130,246), new Color(37,99,235), 8);
        btnEliminar.setPreferredSize(new Dimension(140, 36));
        btnRefresh.setPreferredSize(new Dimension(140, 36));
        btnVerPrest.setPreferredSize(new Dimension(160, 36));

        p.add(btnEliminar); p.add(btnVerPrest); p.add(btnRefresh);

        JLabel lblCount = new JLabel();
        lblCount.setFont(Tema.FONT_SMALL);
        lblCount.setForeground(Tema.TEXT_GRAY);
        modelo.addTableModelListener(e -> lblCount.setText(modelo.getRowCount() + " usuario(s)"));
        p.add(Box.createHorizontalStrut(10));
        p.add(lblCount);

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE); return;
            }
            int    id     = (int)    modelo.getValueAt(fila, 0);
            String nombre = (String) modelo.getValueAt(fila, 1);
            int prestActivos = (Integer) modelo.getValueAt(fila, 3);
            if (prestActivos > 0) {
                JOptionPane.showMessageDialog(this,
                    "<html>No se puede eliminar a <b>" + nombre + "</b>.<br>Tiene <b>" + prestActivos + "</b> préstamo(s) activo(s).</html>",
                    "Tiene préstamos activos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int conf = JOptionPane.showConfirmDialog(this,
                "<html>¿Eliminar al usuario <b>\"" + nombre + "\"</b>?<br>Esta acción no se puede deshacer.</html>",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (conf != JOptionPane.YES_OPTION) return;
            boolean ok = controller.eliminarUsuario(id);
            if (ok) JOptionPane.showMessageDialog(this, "✅ Usuario eliminado.");
            else    JOptionPane.showMessageDialog(this,
                "No se pudo eliminar. Puede tener préstamos registrados.", "Error", JOptionPane.ERROR_MESSAGE);
            refrescar();
        });

        btnVerPrest.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(this, "Selecciona un usuario.", "Sin selección", JOptionPane.INFORMATION_MESSAGE); return; }
            int    id     = (int)    modelo.getValueAt(fila, 0);
            String nombre = (String) modelo.getValueAt(fila, 1);
            mostrarPrestamosDeUsuario(id, nombre);
        });

        btnRefresh.addActionListener(e -> refrescar());
        return p;
    }

    public void refrescar() { cargarUsuarios(); }

    private void cargarUsuarios() {
        modelo.setRowCount(0);
        for (Usuario u : controller.obtenerUsuarios()) {
            int activos = controller.obtenerPrestamosPorUsuario(u.getId())
                    .stream()
                    .filter(pr -> pr.getFechaDevolucion() == null)
                    .mapToInt(pr -> 1).sum();
            modelo.addRow(new Object[]{ u.getId(), u.getNombre(), u.getCorreo(), activos });
        }
    }

    private void filtrar(String q) {
        if (placeholderActivo) return;
        String txt = q.trim().toLowerCase();
        modelo.setRowCount(0);
        for (Usuario u : controller.obtenerUsuarios()) {
            if (txt.isEmpty()
                || u.getNombre().toLowerCase().contains(txt)
                || u.getCorreo().toLowerCase().contains(txt)) {
                int activos = controller.obtenerPrestamosPorUsuario(u.getId())
                        .stream().filter(pr -> pr.getFechaDevolucion() == null)
                        .mapToInt(pr -> 1).sum();
                modelo.addRow(new Object[]{ u.getId(), u.getNombre(), u.getCorreo(), activos });
            }
        }
    }
}
