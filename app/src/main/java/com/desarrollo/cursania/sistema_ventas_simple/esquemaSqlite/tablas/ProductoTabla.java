package com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas;

public class ProductoTabla {

    public static final String TABLA = "producto";

    public static final String PROD_ID = "prod_id";
    public static final String PROD_NOMBRE = "prod_nombre";
    public static final String PROD_PRECIO = "prod_precio";
    public static final String PROD_RUTA_FOTO = "prod_ruta_foto";

    public static final String CREAR_TABLA_PRODUCTO
            = "CREATE TABLE " + TABLA + "("
            + PROD_ID + " INT PRIMARY KEY,"
            + PROD_NOMBRE + " TEXT,"
            + PROD_PRECIO + " NUMERIC,"
            + PROD_RUTA_FOTO + " TEXT "
            + ") ;";

    public static final String ELIMINAR_TABLA_PRODUCTO = "DROP TABLE IF EXISTS " + TABLA +";";
}
