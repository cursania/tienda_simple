package com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.ClienteTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.ProductoTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.VentaDetalleTabla;

public class ConexionSqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "venta_simple";

    public ConexionSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductoTabla.CREAR_TABLA_PRODUCTO);
        db.execSQL(ClienteTabla.CREAR_TABLA_CLIENTE);
        db.execSQL(VentaCabeceraTabla.CREAR_TABLA_VENTA_CABECERA);
        db.execSQL(VentaDetalleTabla.CREAR_TABLA_VENTA_DETALLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(ProductoTabla.ELIMINAR_TABLA_PRODUCTO);
        db.execSQL(ClienteTabla.ELIMINAR_TABLA_CLIENTE);
        db.execSQL(VentaCabeceraTabla.ELIMINAR_TABLA_VENTA_CABECERA);
        db.execSQL(VentaDetalleTabla.ELIMINAR_TABLA_VENTA_DETALLE);
    }
}