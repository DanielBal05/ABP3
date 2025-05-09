package Models;

import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> productos;
    private double presupuesto;
    private int espacio;

    // Constructor vacío (por defecto)
    public Inventario() {
        this.productos = new ArrayList<>();
        this.presupuesto = 0;
        this.espacio = 0;
    }

    // Constructor con parámetros
    public Inventario(double presupuesto, int espacio) {
        this.productos = new ArrayList<>();
        this.presupuesto = presupuesto;
        this.espacio = espacio;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(int index) {
        if (index >= 0 && index < productos.size()) {
            productos.remove(index);
        }
    }

    // ✅ Ahora devuelve boolean para usar en condicionales
    public boolean actualizarProducto(int index, Producto producto) {
        if (index >= 0 && index < productos.size()) {
            productos.set(index, producto);
            return true;
        }
        return false;
    }

    public Producto obtenerProducto(int index) {
        if (index >= 0 && index < productos.size()) {
            return productos.get(index);
        }
        return null;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getEspacio() {
        return espacio;
    }

    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }
}