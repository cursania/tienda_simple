package com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas;

public class VentaCabeceraTabla {

    public static final String TABLA = "venta_cabecera";

    public static final String VC_ID    = "vc_id";
    public static final String VC_FECHA = "vc_fecha";
    public static final String VC_HORA  = "vc_hora";
    public static final String VC_MONTO = "vc_monto";
    public static final String VC_COMENTARIO    = "vc_comentario";
    public static final String CLIE_NOMBRE      = "clie_nombre";

    public static final String CREAR_TABLA_VENTA_CABECERA
            = " CREATE TABLE " + TABLA + " ("
            + VC_ID + " INT PRIMARY KEY,"
            + VC_FECHA + " TEXT,"
            + VC_HORA + " TEXT,"
            + VC_MONTO + " NUMERIC,"
            + VC_COMENTARIO + " TEXT,"
            + CLIE_NOMBRE + " TEXT );";

    public static final String ELIMINAR_TABLA_VENTA_CABECERA = "DROP TABLE IF EXISTS " + TABLA + ";";

}
