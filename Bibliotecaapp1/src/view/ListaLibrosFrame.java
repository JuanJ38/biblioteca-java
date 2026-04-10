package view;

import controller.BibliotecaController;
import model.Libro;
import util.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ListaLibrosFrame extends JPanel {

    private final BibliotecaController controller;
    private DefaultTableModel modelo;
    private JTable tabla;
    private boolean placeholderActivo = true;

    public ListaLibrosFrame(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(248, 250, 252));
        add(construirHeader(),  BorderLayout.NORTH);
        add(construirTabla(),   BorderLayout.CENTER);
        add(construirBotones(), BorderLayout.SOUTH);
        cargarLibros();
    }

    private JPanel construirHeader() {
        JPanel h = new JPanel(new BorderLayout(12, 0));
        h.setBackground(new Color(15, 23, 42));
        h.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        JLabel ico = new JLabel("📖");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        JLabel lbl = new JLabel("Catálogo de Libros");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        left.add(ico); left.add(lbl);

        JTextField txtBuscar = new JTextField(22);
        txtBuscar.setFont(Tema.FONT_BODY);
        txtBuscar.setPreferredSize(new Dimension(260, 36));
        txtBuscar.setBackground(new Color(30, 41, 59));
        txtBuscar.setForeground(new Color(200, 210, 220));
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)));
        txtBuscar.setText("🔍  Buscar por título o autor...");

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (placeholderActivo) { txtBuscar.setText(""); placeholderActivo = false; txtBuscar.setForeground(Color.WHITE); }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBuscar.getText().isEmpty()) {
                    placeholderActivo = true;
                    txtBuscar.setText("🔍  Buscar por título o autor...");
                    txtBuscar.setForeground(new Color(200,210,220));
                    cargarLibros();
                }
            }
        });
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filtrar(txtBuscar.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filtrar(txtBuscar.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(txtBuscar.getText()); }
        });

        h.add(left, BorderLayout.WEST);
        h.add(txtBuscar, BorderLayout.EAST);
        return h;
    }

    private JScrollPane construirTabla() {
        // Columnas: ID | Título | Autor | Imagen | Reseña | Estado
        String[] cols = {"#", "Título", "Autor", "Imagen (URL)", "Reseña", "Estado"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(42);
        tabla.setFont(Tema.FONT_BODY);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new Color(241, 245, 249));
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionBackground(new Color(236, 253, 245));
        tabla.setSelectionForeground(new Color(6, 78, 59));
        tabla.setFocusable(false);

        JTableHeader th = tabla.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setBackground(new Color(248, 250, 252));
        th.setForeground(new Color(71, 85, 105));
        th.setPreferredSize(new Dimension(0, 42));
        th.setReorderingAllowed(false);
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(226, 232, 240)));

        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(220);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);

        // Renderer base
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                if (!sel) {
                    setBackground(r % 2 == 0 ? Color.WHITE : new Color(250, 252, 255));
                    setForeground(new Color(30, 41, 59));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setFont(c == 0 ? new Font("Consolas", Font.PLAIN, 12) : Tema.FONT_BODY);
                if (c == 0) setHorizontalAlignment(SwingConstants.CENTER);
                else        setHorizontalAlignment(SwingConstants.LEFT);
                // URL de imagen: color azul
                if (c == 3) setForeground(sel ? new Color(6,78,59) : new Color(59,130,246));
                return this;
            }
        });

        // Renderer Estado (badge)
        tabla.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                p.setBackground(sel ? new Color(236,253,245) : (r%2==0 ? Color.WHITE : new Color(250,252,255)));
                String val  = v != null ? v.toString() : "";
                boolean disp = "Disponible".equals(val);
                JLabel badge = new JLabel("  " + (disp ? "● Disponible" : "● Prestado") + "  ") {
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(disp ? new Color(220,252,231) : new Color(254,226,226));
                        g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,20,20);
                        super.paintComponent(g);
                        g2.dispose();
                    }
                };
                badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
                badge.setForeground(disp ? new Color(22,163,74) : new Color(220,38,38));
                badge.setOpaque(false);
                badge.setPreferredSize(new Dimension(110, 26));
                badge.setHorizontalAlignment(SwingConstants.CENTER);
                p.add(badge);
                return p;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private JPanel construirBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        p.setBackground(new Color(248, 250, 252));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        BotonModerno btnEditar   = new BotonModerno("✏  Editar",   new Color(59,130,246), new Color(37,99,235),  8);
        BotonModerno btnEliminar = new BotonModerno("🗑  Eliminar", Tema.DANGER, new Color(185,28,28), 8);
        BotonModerno btnRefresh  = new BotonModerno("↻  Actualizar", new Color(71,85,105), new Color(51,65,85),  8);

        for (BotonModerno b : new BotonModerno[]{btnEditar, btnEliminar, btnRefresh})
            b.setPreferredSize(new Dimension(140, 36));

        p.add(btnEditar); p.add(btnEliminar); p.add(btnRefresh);

        JLabel lblCount = new JLabel();
        lblCount.setFont(Tema.FONT_SMALL);
        lblCount.setForeground(Tema.TEXT_GRAY);
        modelo.addTableModelListener(e -> lblCount.setText(modelo.getRowCount() + " libro(s)"));
        p.add(Box.createHorizontalStrut(10));
        p.add(lblCount);

        // EDITAR con imagen y reseña
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { mostrarSinSeleccion(); return; }

            int     id     = (int)    modelo.getValueAt(fila, 0);
            String  titulo = (String) modelo.getValueAt(fila, 1);
            String  autor  = (String) modelo.getValueAt(fila, 2);
            String  imagen = (String) modelo.getValueAt(fila, 3);
            String  resena = (String) modelo.getValueAt(fila, 4);
            boolean disp   = "Disponible".equals(modelo.getValueAt(fila, 5));

            JTextField fTit  = new JTextField(titulo);
            JTextField fAut  = new JTextField(autor);
            JTextField fImg  = new JTextField(imagen);
            JTextArea  fRes  = new JTextArea(resena, 3, 20);
            fRes.setLineWrap(true); fRes.setWrapStyleWord(true);
            JScrollPane scrollRes = new JScrollPane(fRes);
            JCheckBox chkDisp = new JCheckBox("Disponible", disp);

            JPanel form = new JPanel(new GridLayout(10, 1, 4, 4));
            form.add(new JLabel("Título:")); form.add(fTit);
            form.add(new JLabel("Autor:"));  form.add(fAut);
            form.add(new JLabel("URL imagen:")); form.add(fImg);
            form.add(new JLabel("Reseña:")); form.add(scrollRes);
            form.add(new JLabel("Estado:")); form.add(chkDisp);
            form.setPreferredSize(new Dimension(400, 300));

            int res = JOptionPane.showConfirmDialog(this, form,
                "Editar libro #" + id, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION) return;
            if (fTit.getText().trim().isEmpty() || fAut.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título y autor no pueden estar vacíos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Libro libro = new Libro(id, fTit.getText().trim(), fAut.getText().trim(),
                                    chkDisp.isSelected(), fImg.getText().trim(), fRes.getText().trim());
            controller.actualizarLibro(libro);
            JOptionPane.showMessageDialog(this, "✅ Libro actualizado correctamente.");
            refrescar();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { mostrarSinSeleccion(); return; }
            String tit = modelo.getValueAt(fila, 1).toString();
            int conf = JOptionPane.showConfirmDialog(this,
                "<html>¿Eliminar el libro <b>\"" + tit + "\"</b>?<br>Esta acción no se puede deshacer.</html>",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (conf != JOptionPane.YES_OPTION) return;
            controller.eliminarLibro((int) modelo.getValueAt(fila, 0));
            JOptionPane.showMessageDialog(this, "Libro eliminado.");
            refrescar();
        });

        btnRefresh.addActionListener(e -> refrescar());
        return p;
    }

    public void refrescar() { cargarLibros(); }

    private void cargarLibros() {
        modelo.setRowCount(0);
        for (Libro l : controller.obtenerLibros()) agregarFila(l);
    }

    private void agregarFila(Libro l) {
        modelo.addRow(new Object[]{
            l.getId(), l.getTitulo(), l.getAutor(),
            l.getImagen(), l.getResena(),
            l.isDisponible() ? "Disponible" : "Prestado"
        });
    }

    private void filtrar(String q) {
        if (placeholderActivo) return;
        String txt = q.trim().toLowerCase();
        modelo.setRowCount(0);
        for (Libro l : controller.obtenerLibros()) {
            if (txt.isEmpty() || l.getTitulo().toLowerCase().contains(txt)
                || l.getAutor().toLowerCase().contains(txt)) agregarFila(l);
        }
    }

    private void mostrarSinSeleccion() {
        JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla.", "Sin selección", JOptionPane.INFORMATION_MESSAGE);
    }
}
