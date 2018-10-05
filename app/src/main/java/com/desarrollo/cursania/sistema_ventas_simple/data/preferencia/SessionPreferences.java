package com.desarrollo.cursania.sistema_ventas_simple.data.preferencia;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionPreferences {

    //constantes
    private static final String PREF_NAME = "DATA_SESSION";

    private static final String PREF_PRODUCTO   = "PREF_PRODUCTO";
    private static final String PREF_CLIENTE    = "PREF_CLIENTE";
    private static final String PREF_VENTA_CABECERA = "PREF_VENTA_CABECERA";
    private static final String PREF_VENTA_DETALLE  = "PREF_VENTA_DETALLE";

    private final SharedPreferences mPref;
    private  static SessionPreferences INSTANCE;

    public static SessionPreferences get(Context context){
        if (INSTANCE == null){
            INSTANCE = new SessionPreferences(context);
        }
        return INSTANCE;
    }

    public SessionPreferences(Context context) {
        this.mPref = context.getApplicationContext().getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public void setProducto(int codigo){
        SharedPreferences.Editor objEdit = mPref.edit();
        objEdit.putInt(PREF_PRODUCTO, codigo + 1);
        objEdit.apply();
    }

    public int getProducto(){
        return mPref.getInt(PREF_PRODUCTO, 1);
    }

    public void setCliente(int codigo){
        SharedPreferences.Editor objEdit = mPref.edit();
        objEdit.putInt(PREF_CLIENTE, codigo + 1);
        objEdit.apply();
    }

    public int getCliente(){
        return mPref.getInt(PREF_CLIENTE, 1);
    }

    public void setVentaCabecera(int codigo){
        SharedPreferences.Editor objEdit = mPref.edit();
        objEdit.putInt(PREF_VENTA_CABECERA, codigo + 1);
        objEdit.apply();
    }

    public int getVentaCabecera(){
        return mPref.getInt(PREF_VENTA_CABECERA, 1);
    }

    public void setVentaDetalle(int codigo){
        SharedPreferences.Editor objEdit = mPref.edit();
        objEdit.putInt(PREF_VENTA_DETALLE, codigo + 1);
        objEdit.apply();
    }

    public int getVentaDetalle(){
        return mPref.getInt(PREF_VENTA_DETALLE, 1);
    }

}
