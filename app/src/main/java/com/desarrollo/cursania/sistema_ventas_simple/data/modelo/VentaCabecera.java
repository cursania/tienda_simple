package com.desarrollo.cursania.sistema_ventas_simple.data.modelo;

import com.desarrollo.cursania.sistema_ventas_simple.data.util.Metodos;

import java.io.Serializable;

public class VentaCabecera implements Serializable{
    private int vc_id;
    private String vc_fecha;
    private String vc_hora;
    private Double vc_monto;
    private String vc_comentario;
    private String clie_nombre;

    public int getVc_id() {
        return vc_id;
    }

    public void setVc_id(int vc_id) {
        this.vc_id = vc_id;
    }

    public String getVc_fecha() {
        return vc_fecha;
    }

    public void setVc_fecha(String vc_fecha) {
        this.vc_fecha = vc_fecha;
    }

    public String getVc_hora() {
        return vc_hora;
    }

    public void setVc_hora(String vc_hora) {
        this.vc_hora = vc_hora;
    }

    public Double getVc_monto() {
        return vc_monto;
    }

    public void setVc_monto(Double vc_monto) {
        this.vc_monto = vc_monto;
    }

    public String getVc_comentario() {
        return vc_comentario;
    }

    public void setVc_comentario(String vc_comentario) {
        this.vc_comentario = vc_comentario;
    }

    public String getClie_nombre() {
        return clie_nombre;
    }

    public void setClie_nombre(String clie_nombre) {
        this.clie_nombre = clie_nombre;
    }

    public VentaCabecera(int vc_id, String vc_fecha, String vc_hora, Double vc_monto, String vc_comentario, String clie_nombre) {
        this.vc_id = vc_id;
        this.vc_fecha = vc_fecha;
        this.vc_hora = vc_hora;
        this.vc_monto = vc_monto;
        this.vc_comentario = vc_comentario;
        this.clie_nombre = clie_nombre;
    }

    public VentaCabecera() {

    }

    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter, new Object[]{
                vc_id,
                vc_fecha,
                vc_hora,
                vc_monto,
                vc_comentario,
                clie_nombre
        });
    }

    public VentaCabecera(String cadenaLeida, String caracter){
        this.vc_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.vc_fecha = Metodos.cadenaDescomponer(cadenaLeida,2,caracter);
        this.vc_hora = Metodos.cadenaDescomponer(cadenaLeida,3,caracter);
        this.vc_monto = Double.parseDouble(Metodos.cadenaDescomponer(cadenaLeida,4,caracter));
        this.vc_comentario = Metodos.cadenaDescomponer(cadenaLeida,5,caracter);
        this.clie_nombre = Metodos.cadenaDescomponer(cadenaLeida,6,caracter);
    }
}
