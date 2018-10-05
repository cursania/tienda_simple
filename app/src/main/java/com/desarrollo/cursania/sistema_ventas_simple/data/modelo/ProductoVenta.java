package com.desarrollo.cursania.sistema_ventas_simple.data.modelo;

public class ProductoVenta extends Producto {
    private Double prod_precio_venta;
    private int prod_cantidad;
    private Double prod_total;

    public Double getProd_precio_venta() {
        return prod_precio_venta;
    }

    public void setProd_precio_venta(Double prod_precio_venta) {
        this.prod_precio_venta = prod_precio_venta;
    }

    public int getProd_cantidad() {
        return prod_cantidad;
    }

    public void setProd_cantidad(int prod_cantidad) {
        this.prod_cantidad = prod_cantidad;
    }

    public Double getProd_total() {
        return prod_total;
    }

    public void setProd_total(Double prod_total) {
        this.prod_total = prod_total;
    }

    public ProductoVenta(Producto producto, Double prod_precio_venta, int prod_cantidad, Double prod_total) {
        super(producto.getProd_id(), producto.getProd_nombre(), producto.getProd_precio(),
                producto.getProd_ruta_foto(), producto.getProd_seleccionado());
        this.prod_precio_venta = prod_precio_venta;
        this.prod_cantidad = prod_cantidad;
        this.prod_total = prod_total;
    }

    public ProductoVenta() {
    }
}
