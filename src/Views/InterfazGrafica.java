package Views;

import Models.Inventario;
import Models.Producto;
import Models.Sistemas;
import Models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InterfazGrafica extends JFrame {
    private JPanel panelPrincipal;
    private CardLayout cardLayout;

    private Inventario inventario;
    private Sistemas sistema;

    private JTextField userField;
    private JPasswordField passField;

    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;

    private JTextField nombreField, precioField, stockField, fechaVencField;
    private JTextArea descripcionField;

    public InterfazGrafica() {
        inventario = new Inventario();
        sistema = new Sistemas();
        sistema.agregarUsuario(new Usuario("admin", "123")); // Usuario por defecto

        setTitle("Sistema de Inventario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel();
        cardLayout = new CardLayout();
        panelPrincipal.setLayout(cardLayout);

        crearPanelLogin();
        crearPanelMenu();

        add(panelPrincipal);
        setVisible(true);
    }

    private void crearPanelLogin() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        loginPanel.setBackground(Color.WHITE);

        JLabel lblUser = new JLabel("Usuario:");
        JLabel lblPass = new JLabel("Contraseña:");
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        JButton btnLogin = new JButton("Iniciar Sesión");

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; loginPanel.add(lblUser, gbc);
        gbc.gridx = 1; loginPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; loginPanel.add(lblPass, gbc);
        gbc.gridx = 1; loginPanel.add(passField, gbc);
        gbc.gridwidth = 2; gbc.gridy = 2; loginPanel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (sistema.verificarCredenciales(user, pass)) {
                cardLayout.show(panelPrincipal, "menu");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        });

        panelPrincipal.add(loginPanel, "login");
    }

    private void crearPanelMenu() {
        JPanel menuPanel = new JPanel(new BorderLayout());

        // Panel lateral
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(30, 144, 255));

        JButton btnInventario = new JButton("Inventario");
        JButton btnCompras = new JButton("Compras");

        btnInventario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCompras.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelIzquierdo.add(Box.createVerticalStrut(20));
        panelIzquierdo.add(btnInventario);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnCompras);

        JPanel panelContenido = new JPanel(new CardLayout());

        JPanel panelInventario = crearPanelInventario();
        panelContenido.add(panelInventario, "inventario");

        btnInventario.addActionListener(e -> {
            actualizarTabla();
            ((CardLayout) panelContenido.getLayout()).show(panelContenido, "inventario");
        });

        menuPanel.add(panelIzquierdo, BorderLayout.WEST);
        menuPanel.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(menuPanel, "menu");
    }

    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout());

        // Formulario lateral
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        nombreField = new JTextField();
        descripcionField = new JTextArea(3, 20);
        precioField = new JTextField();
        stockField = new JTextField();
        fechaVencField = new JTextField();

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Descripción:"));
        formPanel.add(new JScrollPane(descripcionField));
        formPanel.add(new JLabel("Precio:"));
        formPanel.add(precioField);
        formPanel.add(new JLabel("Stock:"));
        formPanel.add(stockField);
        formPanel.add(new JLabel("Fecha Venc. (yyyy-MM-dd):"));
        formPanel.add(fechaVencField);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock", "Fecha Venc."};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaInventario = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaInventario);

        // Botones
        JPanel btnPanel = new JPanel();
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        btnPanel.add(btnNuevo);
        btnPanel.add(btnActualizar);
        btnPanel.add(btnEliminar);

        // Acciones
        btnNuevo.addActionListener(e -> {
            try {
                String nombre = nombreField.getText();
                String desc = descripcionField.getText();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());
                String fecha = fechaVencField.getText();

                Producto p = new Producto(nombre, desc, precio, stock, fecha);
                inventario.agregarProducto(p);
                actualizarTabla();
                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        btnActualizar.addActionListener(e -> {
            int row = tablaInventario.getSelectedRow();
            if (row != -1) {
                try {
                    String nombre = nombreField.getText();
                    String desc = descripcionField.getText();
                    double precio = Double.parseDouble(precioField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    String fecha = fechaVencField.getText();

                    Producto p = new Producto(nombre, desc, precio, stock, fecha);
                    if (inventario.actualizarProducto(row, p)) {
                        actualizarTabla();
                        limpiarCampos();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Datos inválidos");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = tablaInventario.getSelectedRow();
            if (row != -1) {
                inventario.eliminarProducto(row);
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
            }
        });

        tablaInventario.getSelectionModel().addListSelectionListener(e -> {
            int row = tablaInventario.getSelectedRow();
            if (row != -1) {
                Producto p = inventario.obtenerProducto(row);
                nombreField.setText(p.getNombre());
                descripcionField.setText(p.getDescripcion());
                precioField.setText(String.valueOf(p.getCostoUnitario()));
                stockField.setText(String.valueOf(p.getCantidad()));
                fechaVencField.setText(p.getFechaVencimiento());
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        int id = 1;
        for (Producto p : inventario.getProductos()) {
            modeloTabla.addRow(new Object[]{
                    id++, p.getNombre(), p.getDescripcion(),
                    p.getCostoUnitario(), p.getCantidad(),
                    p.getFechaVencimiento()
            });
        }
    }

    private void limpiarCampos() {
        nombreField.setText("");
        descripcionField.setText("");
        precioField.setText("");
        stockField.setText("");
        fechaVencField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazGrafica::new);
    }
}