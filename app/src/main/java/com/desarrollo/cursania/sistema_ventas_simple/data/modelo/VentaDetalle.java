package com.desarrollo.cursania.sistema_ventas_simple.data.modelo;

import com.desarrollo.cursania.sistema_ventas_simple.data.util.Metodos;

public class VentaDetalle {
    private int vd_id;
    private int vd_cantidad;
    private Double vd_precio;
    private int vc_id;
    private String prod_nombre;
    private String prod_ruta_foto;

    public int getVd_id() {
        return vd_id;
    }

    public void setVd_id(int vd_id) {
        this.vd_id = vd_id;
    }

    public int getVd_cantidad() {
        return vd_cantidad;
    }

    public void setVd_cantidad(int vd_cantidad) {
        this.vd_cantidad = vd_cantidad;
    }

    public Double getVd_precio() {
        return vd_precio;
    }

    public void setVd_precio(Double vd_precio) {
        this.vd_precio = vd_precio;
    }

    public int getVc_id() {
        return vc_id;
    }

    public void setVc_id(int vc_id) {
        this.vc_id = vc_id;
    }

    public String getProd_nombre() {
        return prod_nombre;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nombre = prod_nombre;
    }

    public String getProd_ruta_foto() {
        return prod_ruta_foto;
    }

    public void setProd_ruta_foto(String prod_ruta_foto) {
        this.prod_ruta_foto = prod_ruta_foto;
    }

    public VentaDetalle(int vd_id, int vd_cantidad, Double vd_precio, int vc_id, String prod_nombre, String prod_ruta_foto) {
        this.vd_id = vd_id;
        this.vd_cantidad = vd_cantidad;
        this.vd_precio = vd_precio;
        this.vc_id = vc_id;
        this.prod_nombre = prod_nombre;
        this.prod_ruta_foto = prod_ruta_foto;
    }

    public VentaDetalle() {

    }

    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter, new Object[]{
                vc_id,
                vd_cantidad,
                vd_precio,
                vc_id,
                prod_nombre,
                prod_ruta_foto
        });
    }

    public VentaDetalle(String cadenaLeida, String caracter){
        this.vd_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.vd_cantidad = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,2,caracter));
        this.vd_precio = Double.parseDouble(Metodos.cadenaDescomponer(cadenaLeida,3,caracter));
        this.vc_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,4,caracter));
        this.prod_nombre = Metodos.cadenaDescomponer(cadenaLeida,5,caracter);
        this.prod_ruta_foto = Metodos.cadenaDescomponer(cadenaLeida,6,caracter);
    }
}
