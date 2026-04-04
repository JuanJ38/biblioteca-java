package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import model.Usuario;
import controller.BibliotecaController;

public class ListaUsuariosFrame extends JPanel {

    private BibliotecaController controller;
    private DefaultTableModel modelo;
    private JTable tabla;
    private boolean placeholderActivo = true;

    public ListaUsuariosFrame(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ── HEADER ────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setBackground(new Color(40, 70, 100));
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel lblTitulo = new JLabel("Listado de usuarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);

        JTextField txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setPreferredSize(new Dimension(240, 32));
        txtBuscar.setBackground(new Color(30, 55, 80));
        txtBuscar.setForeground(new Color(180, 210, 230));
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 100, 140), 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtBuscar.setText("Buscar por nombre o correo...");

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (placeholderActivo) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(Color.WHITE);
                    placeholderActivo = false;
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBuscar.getText().isEmpty()) {
                    placeholderActivo = true;
                    txtBuscar.setForeground(new Color(180, 210, 230));
                    txtBuscar.setText("Buscar por nombre o correo...");
                    cargarUsuarios();
                }
            }
        });

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(txtBuscar, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ── TABLA ─────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Correo electrónico"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(36);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setSelectionBackground(new Color(210, 225, 240));
        tabla.setSelectionForeground(new Color(20, 50, 80));
        tabla.setFocusable(false);

        JTableHeader tableHeader = tabla.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableHeader.setBackground(new Color(245, 248, 252));
        tableHeader.setForeground(new Color(60, 60, 60));
        tableHeader.setPreferredSize(new Dimension(0, 36));
        tableHeader.setReorderingAllowed(false);

        tabla.getColumnModel().getColumn(0).setMaxWidth(55);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(260);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(300);

        // Renderer ID centrado
        tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (!sel) setBackground(r % 2 == 0 ? Color.WHITE : new Color(248, 250, 253));
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        // Renderer Nombre con avatar de inicial
        tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
                p.setOpaque(true);
                p.setBackground(sel
                    ? new Color(210, 225, 240)
                    : (r % 2 == 0 ? Color.WHITE : new Color(248, 250, 253)));

                String nombre  = v != null ? v.toString() : "?";
                String inicial = nombre.length() > 0
                    ? String.valueOf(nombre.charAt(0)).toUpperCase() : "?";

                JLabel avatar = new JLabel(inicial) {
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(new Color(40, 70, 100));
                        g2.fillOval(0, 0, getWidth(), getHeight());
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                        FontMetrics fm = g2.getFontMetrics();
                        int x = (getWidth()  - fm.stringWidth(inicial)) / 2;
                        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                        g2.drawString(inicial, x, y);
                        g2.dispose();
                    }
                };
                avatar.setPreferredSize(new Dimension(26, 26));
                avatar.setOpaque(false);

                JLabel lblNombre = new JLabel(nombre);
                lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                lblNombre.setForeground(new Color(40, 40, 40));

                p.add(avatar);
                p.add(lblNombre);
                return p;
            }
        });

        // Renderer Correo en azul
        tabla.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setForeground(sel ? new Color(20, 50, 80) : new Color(40, 90, 150));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (!sel) setBackground(r % 2 == 0 ? Color.WHITE : new Color(248, 250, 253));
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        // ── BOTONES ───────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(new Color(248, 250, 252));
        panelBotones.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
            new Color(220, 225, 235)));

        JButton btnEliminar = crearBoton("Eliminar usuario", new Color(192, 57, 43));
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarUsuarios();

        // ── BUSCADOR ─────────────────────────────────────
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { ejecutarFiltro(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { ejecutarFiltro(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { ejecutarFiltro(); }

            private void ejecutarFiltro() {
                if (placeholderActivo) return;
                String q = txtBuscar.getText().trim().toLowerCase();
                modelo.setRowCount(0);
                for (Usuario u : controller.obtenerUsuarios()) {
                    if (q.isEmpty()
                            || u.getNombre().toLowerCase().contains(q)
                            || u.getCorreo().toLowerCase().contains(q)) {
                        agregarFila(u);
                    }
                }
            }
        });

        // ── EVENTO ELIMINAR ───────────────────────────────
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecciona un usuario de la tabla.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int    idUsuario  = (int)    modelo.getValueAt(fila, 0);
            String nombreSel  = (String) modelo.getValueAt(fila, 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar al usuario \"" + nombreSel + "\"?\n"
                + "Esta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) return;

            boolean ok = controller.eliminarUsuario(idUsuario);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "Usuario eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar. Puede tener préstamos activos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            refrescar();
        });
    }

    public void refrescar() { cargarUsuarios(); }

    private void cargarUsuarios() {
        modelo.setRowCount(0);
        for (Usuario u : controller.obtenerUsuarios()) {
            agregarFila(u);
        }
    }

    private void agregarFila(Usuario u) {
        modelo.addRow(new Object[]{
            u.getId(),
            u.getNombre(),
            u.getCorreo()
        });
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(170, 36));
        Color hover = color.darker();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(color); }
        });
        return btn;
    }
}