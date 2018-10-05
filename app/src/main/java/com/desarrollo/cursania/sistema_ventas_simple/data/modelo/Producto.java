package com.desarrollo.cursania.sistema_ventas_simple.data.modelo;

import com.desarrollo.cursania.sistema_ventas_simple.data.util.Metodos;

import java.io.Serializable;

public class Producto implements Serializable {
    private int prod_id;
    private String prod_nombre;
    private Double prod_precio;
    private String prod_ruta_foto;
    private Boolean prod_seleccionado;

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_nombre() {
        return prod_nombre;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nombre = prod_nombre;
    }

    public Double getProd_precio() {
        return prod_precio;
    }

    public void setProd_precio(Double prod_precio) {
        this.prod_precio = prod_precio;
    }

    public String getProd_ruta_foto() {
        return prod_ruta_foto;
    }

    public void setProd_ruta_foto(String prod_ruta_foto) {
        this.prod_ruta_foto = prod_ruta_foto;
    }

    public Boolean getProd_seleccionado() {
        return prod_seleccionado;
    }

    public void setProd_seleccionado(Boolean prod_seleccionado) {
        this.prod_seleccionado = prod_seleccionado;
    }

    public Producto(int prod_id, String prod_nombre, Double prod_precio, String prod_ruta_foto, Boolean prod_seleccionado) {
        this.prod_id = prod_id;
        this.prod_nombre = prod_nombre;
        this.prod_precio = prod_precio;
        this.prod_ruta_foto = prod_ruta_foto;
        this.prod_seleccionado = prod_seleccionado;
    }

    public Producto() {

    }

    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter, new Object[]{
                prod_id,
                prod_nombre,
                prod_precio,
                prod_ruta_foto
        });
    }

    public Producto(String cadenaLeida,String caracter){
        this.prod_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida, 1, caracter));
        this.prod_nombre = Metodos.cadenaDescomponer(cadenaLeida, 2, caracter);
        this.prod_precio = Double.parseDouble(Metodos.cadenaDescomponer(cadenaLeida, 3, caracter));
        this.prod_ruta_foto = Metodos.cadenaDescomponer(cadenaLeida, 4, caracter);
    }
}
