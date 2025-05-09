package Models;

public class Producto {
    private String nombre;
    private String descripcion;
    private double costoUnitario;
    private int cantidad;
    private String fechaVencimiento;

    public Producto(String nombre, String descripcion, double costoUnitario, int cantidad, String fechaVencimiento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getCostoTotal() {
        return costoUnitario * cantidad;
    }
}