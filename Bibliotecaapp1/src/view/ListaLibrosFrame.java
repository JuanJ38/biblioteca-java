package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import model.Libro;
import controller.BibliotecaController;

public class ListaLibrosFrame extends JPanel {

    private BibliotecaController controller;
    private DefaultTableModel modelo;
    private JTable tabla;
    private boolean placeholderActivo = true;

    public ListaLibrosFrame(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        //  PANEL SUPERIOR: título + buscador 
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setBackground(new Color(0, 150, 150));
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel lblTitulo = new JLabel("Listado de libros");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);

        // Campo de búsqueda limpio — sin alpha, sin complicaciones
        JTextField txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setPreferredSize(new Dimension(240, 32));
        txtBuscar.setBackground(new Color(0, 120, 120));
        txtBuscar.setForeground(new Color(200, 235, 235));
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 180, 180), 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtBuscar.setText("Buscar por título o autor...");

        // FocusListener solo para apariencia visual del placeholder
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
                    txtBuscar.setForeground(new Color(200, 235, 235));
                    txtBuscar.setText("Buscar por título o autor...");
                    cargarLibros(); // restaurar todos al salir sin texto
                }
            }
        });

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(txtBuscar, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        //  TABLA 
        String[] columnas = {"ID", "Título", "Autor", "Disponibilidad"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(34);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setSelectionBackground(new Color(220, 245, 245));
        tabla.setSelectionForeground(new Color(0, 80, 80));
        tabla.setFocusable(false);

        JTableHeader tableHeader = tabla.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableHeader.setBackground(new Color(245, 250, 250));
        tableHeader.setForeground(new Color(60, 60, 60));
        tableHeader.setPreferredSize(new Dimension(0, 36));
        tableHeader.setReorderingAllowed(false);

        tabla.getColumnModel().getColumn(0).setMaxWidth(55);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(280);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(130);

        // Renderer filas alternas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                if (!sel) {
                    setBackground(r % 2 == 0 ? Color.WHITE : new Color(248, 252, 252));
                    setForeground(new Color(40, 40, 40));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });

        // Renderer columna Disponibilidad (sobreescribe el anterior solo en col 3)
        tabla.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JLabel lbl = new JLabel();
                lbl.setOpaque(true);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                String val = v != null ? v.toString() : "";
                if ("Disponible".equals(val)) {
                    lbl.setBackground(sel ? new Color(180, 230, 180) : new Color(232, 245, 233));
                    lbl.setForeground(new Color(27, 94, 32));
                    lbl.setText("● Disponible");
                } else {
                    lbl.setBackground(sel ? new Color(230, 180, 180) : new Color(253, 235, 236));
                    lbl.setForeground(new Color(183, 28, 28));
                    lbl.setText("● Prestado");
                }
                return lbl;
            }
        });

        // Renderer columna ID centrado
        tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (!sel) setBackground(r % 2 == 0 ? Color.WHITE : new Color(248, 252, 252));
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        //  BOTONES 
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(new Color(248, 250, 250));
        panelBotones.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
            new Color(220, 220, 220)));

        JButton btnEditar   = crearBoton("Editar libro",   new Color(52, 73, 94));
        JButton btnEliminar = crearBoton("Eliminar libro", new Color(192, 57, 43));
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarLibros();

        //  BUSCADOR: DocumentListener limpio 
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { ejecutarFiltro(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { ejecutarFiltro(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { ejecutarFiltro(); }

            private void ejecutarFiltro() {
                // Si el placeholder está activo, NO filtrar
                if (placeholderActivo) return;
                String q = txtBuscar.getText().trim().toLowerCase();
                modelo.setRowCount(0);
                for (Libro l : controller.obtenerLibros()) {
                    if (q.isEmpty()
                            || l.getTitulo().toLowerCase().contains(q)
                            || l.getAutor().toLowerCase().contains(q)) {
                        agregarFila(l);
                    }
                }
            }
        });

        // EVENTO EDITAR 
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecciona un libro de la tabla.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int id        = (int)    modelo.getValueAt(fila, 0);
            String titulo = (String) modelo.getValueAt(fila, 1);
            String autor  = (String) modelo.getValueAt(fila, 2);
            boolean disp  = "Disponible".equals(modelo.getValueAt(fila, 3));

            JTextField fTitulo = new JTextField(titulo);
            JTextField fAutor  = new JTextField(autor);
            JPanel form = new JPanel(new GridLayout(4, 1, 6, 6));
            form.add(new JLabel("Nuevo título:")); form.add(fTitulo);
            form.add(new JLabel("Nuevo autor:"));  form.add(fAutor);

            int res = JOptionPane.showConfirmDialog(this, form,
                "Editar libro", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION) return;

            if (fTitulo.getText().trim().isEmpty() || fAutor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Los campos no pueden estar vacíos.",
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Libro libro = new Libro(id, fTitulo.getText().trim(),
                                    fAutor.getText().trim(), disp);
            controller.actualizarLibro(libro);
            JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.");
            refrescar();
        });

        //  EVENTO ELIMINAR 
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecciona un libro de la tabla.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String tituloSel = modelo.getValueAt(fila, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el libro \"" + tituloSel + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) return;

            controller.eliminarLibro((int) modelo.getValueAt(fila, 0));
            JOptionPane.showMessageDialog(this, "Libro eliminado.");
            refrescar();
        });
    }

    public void refrescar() { cargarLibros(); }

    private void cargarLibros() {
        modelo.setRowCount(0);
        for (Libro l : controller.obtenerLibros()) {
            agregarFila(l);
        }
    }

    // Método centralizado para agregar fila — evita duplicar lógica
    private void agregarFila(Libro l) {
        modelo.addRow(new Object[]{
            l.getId(),
            l.getTitulo(),
            l.getAutor(),
            l.isDisponible() ? "Disponible" : "Prestado"
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
        btn.setPreferredSize(new Dimension(150, 36));
        Color hover = color.darker();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(color); }
        });
        return btn;
    }
}