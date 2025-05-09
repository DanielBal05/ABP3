package Views;

import Models.Inventario;
import Models.Presupuesto;
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
    private Presupuesto presupuesto;

    private JTextField userField, newUserField, nombreField, descripcionField, costoField, cantidadField, fechaVencField;
    private JPasswordField passField, newPassField;
    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;

    public InterfazGrafica() {
        inventario = new Inventario();
        sistema = new Sistemas();

        setTitle("Sistema de Inventario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel();
        cardLayout = new CardLayout();
        panelPrincipal.setLayout(cardLayout);

        crearPanelRegistro();
        crearPanelLogin();
        crearPanelPresupuesto();
        crearPanelMenu();

        add(panelPrincipal);
        setVisible(true);
    }

    private void crearPanelRegistro() {
        JPanel registroPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        registroPanel.setBackground(Color.WHITE);

        JLabel lblNewUser = new JLabel("Nuevo Usuario:");
        JLabel lblNewPass = new JLabel("Nueva Contraseña:");
        newUserField = new JTextField(15);
        newPassField = new JPasswordField(15);
        JButton btnRegistrar = new JButton("Registrar");

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; registroPanel.add(lblNewUser, gbc);
        gbc.gridx = 1; registroPanel.add(newUserField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; registroPanel.add(lblNewPass, gbc);
        gbc.gridx = 1; registroPanel.add(newPassField, gbc);
        gbc.gridwidth = 2; gbc.gridy = 2; registroPanel.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> {
            String newUser = newUserField.getText();
            String newPass = new String(newPassField.getPassword());
            if (!newUser.isEmpty() && !newPass.isEmpty()) {
                sistema.agregarUsuario(new Usuario(newUser, newPass));
                JOptionPane.showMessageDialog(this, "Usuario registrado con éxito");
                cardLayout.show(panelPrincipal, "login");
            } else {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
            }
        });

        panelPrincipal.add(registroPanel, "registro");
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
                cardLayout.show(panelPrincipal, "presupuesto");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        });

        panelPrincipal.add(loginPanel, "login");
    }

    private void crearPanelPresupuesto() {
        JPanel presupuestoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        presupuestoPanel.setBackground(Color.WHITE);

        JLabel lblPresupuesto = new JLabel("Ingrese el presupuesto de la empresa:");
        JTextField presupuestoField = new JTextField(15);
        JButton btnContinuar = new JButton("Continuar");

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; presupuestoPanel.add(lblPresupuesto, gbc);
        gbc.gridx = 1; presupuestoPanel.add(presupuestoField, gbc);
        gbc.gridwidth = 2; gbc.gridy = 1; presupuestoPanel.add(btnContinuar, gbc);

        btnContinuar.addActionListener(e -> {
            try {
                double monto = Double.parseDouble(presupuestoField.getText());
                presupuesto = new Presupuesto(monto);
                inventario.setPresupuesto(monto);
                cardLayout.show(panelPrincipal, "menu");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor válido para el presupuesto");
            }
        });

        panelPrincipal.add(presupuestoPanel, "presupuesto");
    }

    private void crearPanelMenu() {
        JPanel menuPanel = new JPanel(new BorderLayout());

        // Panel lateral
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(30, 144, 255));

        JButton btnInventario = new JButton("Inventario");
        JButton btnEntradas = new JButton("Entradas");
        JButton btnSalidas = new JButton("Salidas");

        btnInventario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntradas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalidas.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelIzquierdo.add(Box.createVerticalStrut(20));
        panelIzquierdo.add(btnInventario);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnEntradas);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnSalidas);

        JPanel panelContenido = new JPanel(new CardLayout());

        JPanel panelInventario = crearPanelInventario();
        JPanel panelEntradas = crearPanelEntradas();
        JPanel panelSalidas = crearPanelSalidas();

        panelContenido.add(panelInventario, "inventario");
        panelContenido.add(panelEntradas, "entradas");
        panelContenido.add(panelSalidas, "salidas");

        btnInventario.addActionListener(e -> {
            actualizarTabla();
            ((CardLayout) panelContenido.getLayout()).show(panelContenido, "inventario");
        });

        btnEntradas.addActionListener(e -> {
            actualizarTablaEntradas(panelEntradas);
            ((CardLayout) panelContenido.getLayout()).show(panelContenido, "entradas");
        });

        btnSalidas.addActionListener(e -> {
            actualizarTablaSalidas(panelSalidas);
            ((CardLayout) panelContenido.getLayout()).show(panelContenido, "salidas");
        });

        menuPanel.add(panelIzquierdo, BorderLayout.WEST);
        menuPanel.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(menuPanel, "menu");
    }

    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout());

        // Formulario lateral
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Gestionar Producto"));

        nombreField = new JTextField();
        descripcionField = new JTextField();
        costoField = new JTextField();
        cantidadField = new JTextField();
        fechaVencField = new JTextField();

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Descripción:"));
        formPanel.add(descripcionField);
        formPanel.add(new JLabel("Costo Unitario:"));
        formPanel.add(costoField);
        formPanel.add(new JLabel("Cantidad:"));
        formPanel.add(cantidadField);
        formPanel.add(new JLabel("Fecha de Vencimiento:"));
        formPanel.add(fechaVencField);

        JButton btnAgregar = new JButton("Agregar Producto");
        JButton btnActualizar = new JButton("Actualizar Producto");
        JButton btnEliminar = new JButton("Eliminar Producto");

        formPanel.add(btnAgregar);
        formPanel.add(btnActualizar);
        formPanel.add(btnEliminar);

        // Acción para agregar un producto
        btnAgregar.addActionListener(e -> {
            try {
                String nombre = nombreField.getText();
                String descripcion = descripcionField.getText();
                double costo = Double.parseDouble(costoField.getText());
                int cantidad = Integer.parseInt(cantidadField.getText());
                String fechaVenc = fechaVencField.getText();

                Producto producto = new Producto(nombre, descripcion, costo, cantidad, fechaVenc);
                inventario.agregarProducto(producto);
                actualizarTabla();
                limpiarCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese datos válidos");
            }
        });

        // Acción para actualizar un producto
        btnActualizar.addActionListener(e -> {
            int selectedRow = tablaInventario.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    String nombre = nombreField.getText();
                    String descripcion = descripcionField.getText();
                    double costo = Double.parseDouble(costoField.getText());
                    int cantidad = Integer.parseInt(cantidadField.getText());
                    String fechaVenc = fechaVencField.getText();

                    Producto productoActualizado = new Producto(nombre, descripcion, costo, cantidad, fechaVenc);
                    if (inventario.actualizarProducto(selectedRow, productoActualizado)) {
                        actualizarTabla();
                        limpiarCampos();
                        JOptionPane.showMessageDialog(this, "Producto actualizado con éxito");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese datos válidos");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar");
            }
        });

        // Acción para eliminar un producto
        btnEliminar.addActionListener(e -> {
            int selectedRow = tablaInventario.getSelectedRow();
            if (selectedRow != -1) {
                inventario.eliminarProducto(selectedRow);
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Producto eliminado con éxito");
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            }
        });

        // Tabla para mostrar el inventario
        String[] columnas = {"Nombre", "Descripción", "Costo Unitario", "Cantidad", "Fecha de Vencimiento"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaInventario = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaInventario);

        // Acción para cargar datos del producto seleccionado en el formulario
        tablaInventario.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tablaInventario.getSelectedRow();
            if (selectedRow != -1) {
                Producto producto = inventario.obtenerProducto(selectedRow);
                if (producto != null) {
                    nombreField.setText(producto.getNombre());
                    descripcionField.setText(producto.getDescripcion());
                    costoField.setText(String.valueOf(producto.getCostoUnitario()));
                    cantidadField.setText(String.valueOf(producto.getCantidad()));
                    fechaVencField.setText(producto.getFechaVencimiento());
                }
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEntradas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla para mostrar los productos con checkbox
        String[] columnas = {"Nombre", "Descripción", "Cantidad", "Pedido Completado"};
        DefaultTableModel modeloEntradas = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }
        };
        JTable tablaEntradas = new JTable(modeloEntradas);
        JScrollPane scroll = new JScrollPane(tablaEntradas);

        JButton btnActualizarEntradas = new JButton("Actualizar Inventario");

        btnActualizarEntradas.addActionListener(e -> {
            for (int i = 0; i < modeloEntradas.getRowCount(); i++) {
                boolean completado = (boolean) modeloEntradas.getValueAt(i, 3);
                if (completado) {
                    inventario.eliminarProducto(i);
                }
            }
            actualizarTablaEntradas(panel);
            JOptionPane.showMessageDialog(this, "Inventario actualizado");
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnActualizarEntradas, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelSalidas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla para mostrar los productos disponibles
        String[] columnas = {"ID", "Nombre", "Cantidad"};
        DefaultTableModel modeloSalidas = new DefaultTableModel(columnas, 0);
        JTable tablaSalidas = new JTable(modeloSalidas);
        JScrollPane scroll = new JScrollPane(tablaSalidas);

        // Formulario lateral
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Salida"));

        JTextField idField = new JTextField();
        JTextField cantidadField = new JTextField();
        JComboBox<String> tipoSalida = new JComboBox<>(new String[]{"Venta", "Desperdicio"});
        JButton btnRegistrarSalida = new JButton("Registrar Salida");

        formPanel.add(new JLabel("ID del Producto:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Cantidad:"));
        formPanel.add(cantidadField);
        formPanel.add(new JLabel("Tipo de Salida:"));
        formPanel.add(tipoSalida);
        formPanel.add(btnRegistrarSalida);

        btnRegistrarSalida.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int cantidad = Integer.parseInt(cantidadField.getText());
                String tipo = (String) tipoSalida.getSelectedItem();

                Producto producto = inventario.obtenerProducto(id);
                if (producto != null && producto.getCantidad() >= cantidad) {
                    producto.setCantidad(producto.getCantidad() - cantidad);
                    if (producto.getCantidad() == 0) {
                        inventario.eliminarProducto(id);
                    }
                    actualizarTablaSalidas(panel);
                    JOptionPane.showMessageDialog(this, "Salida registrada como " + tipo);
                } else {
                    JOptionPane.showMessageDialog(this, "Cantidad no válida o producto no encontrado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese datos válidos");
            }
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.EAST);

        return panel;
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        for (Producto producto : inventario.getProductos()) {
            modeloTabla.addRow(new Object[]{
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getCostoUnitario(),
                    producto.getCantidad(),
                    producto.getFechaVencimiento()
            });
        }
    }

    private void actualizarTablaEntradas(JPanel panelEntradas) {
        JScrollPane scrollPane = (JScrollPane) panelEntradas.getComponent(0);
        JTable tablaEntradas = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel modeloEntradas = (DefaultTableModel) tablaEntradas.getModel();
        modeloEntradas.setRowCount(0);

        for (Producto producto : inventario.getProductos()) {
            modeloEntradas.addRow(new Object[]{producto.getNombre(), producto.getDescripcion(), producto.getCantidad(), false});
        }
    }

    private void actualizarTablaSalidas(JPanel panelSalidas) {
        JScrollPane scrollPane = (JScrollPane) panelSalidas.getComponent(0);
        JTable tablaSalidas = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel modeloSalidas = (DefaultTableModel) tablaSalidas.getModel();
        modeloSalidas.setRowCount(0);

        for (int i = 0; i < inventario.getProductos().size(); i++) {
            Producto producto = inventario.getProductos().get(i);
            modeloSalidas.addRow(new Object[]{i, producto.getNombre(), producto.getCantidad()});
        }
    }

    private void limpiarCampos() {
        nombreField.setText("");
        descripcionField.setText("");
        costoField.setText("");
        cantidadField.setText("");
        fechaVencField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazGrafica::new);
    }
}