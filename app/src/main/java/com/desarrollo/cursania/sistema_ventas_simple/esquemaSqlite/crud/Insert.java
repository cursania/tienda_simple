package com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Cliente;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Producto;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.ProductoVenta;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaDetalle;
import com.desarrollo.cursania.sistema_ventas_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.ConexionSqliteHelper;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.ClienteTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.ProductoTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.VentaDetalleTabla;

import java.util.List;

public class Insert {

    public static void registrar(Context context, Object param, String tabla) {

        ConexionSqliteHelper con = new ConexionSqliteHelper(context);

        SQLiteDatabase db = con.getWritableDatabase();

        ContentValues values = new ContentValues();

        switch (tabla)
        {
            case ClienteTabla.TABLA:
                Cliente cliente = (Cliente)param;

                values.put(ClienteTabla.CLIE_ID, cliente.getClie_id());
                values.put(ClienteTabla.CLIE_NOMBRE, cliente.getClie_nombre());
                values.put(ClienteTabla.CLIE_NUM_CEL, cliente.getClie_num_cel());
                values.put(ClienteTabla.CLIE_EMAIL, cliente.getClie_email());
                values.put(ClienteTabla.CLIE_DIRECCION, cliente.getClie_direccion());

                db.insert(ClienteTabla.TABLA,  ClienteTabla.CLIE_ID,  values);
                SessionPreferences.get(context).setCliente(cliente.getClie_id());
                break;

            case ProductoTabla.TABLA:

                Producto producto = (Producto)param;

                values.put(ProductoTabla.PROD_ID, producto.getProd_id());
                values.put(ProductoTabla.PROD_NOMBRE, producto.getProd_nombre());
                values.put(ProductoTabla.PROD_PRECIO, producto.getProd_precio());
                values.put(ProductoTabla.PROD_RUTA_FOTO, producto.getProd_ruta_foto());

                db.insert(ProductoTabla.TABLA, ProductoTabla.PROD_ID,  values);
                SessionPreferences.get(context).setProducto(producto.getProd_id());
                break;

            case VentaCabeceraTabla.TABLA:

                VentaCabecera ventaCab = (VentaCabecera)param;

                values.put(VentaCabeceraTabla.VC_ID, ventaCab.getVc_id());
                values.put(VentaCabeceraTabla.VC_FECHA, ventaCab.getVc_fecha());
                values.put(VentaCabeceraTabla.VC_HORA, ventaCab.getVc_hora());
                values.put(VentaCabeceraTabla.VC_MONTO, ventaCab.getVc_monto());
                values.put(VentaCabeceraTabla.VC_COMENTARIO, ventaCab.getVc_comentario());
                values.put(VentaCabeceraTabla.CLIE_NOMBRE, ventaCab.getClie_nombre());

                db.insert(VentaCabeceraTabla.TABLA,  VentaCabeceraTabla.VC_ID,  values);
                SessionPreferences.get(context).setVentaCabecera(ventaCab.getVc_id());
                break;

            case VentaDetalleTabla.TABLA:
                VentaDetalle ventaDet = (VentaDetalle)param;


                values.put(VentaDetalleTabla.VD_ID, ventaDet.getVd_id());
                values.put(VentaDetalleTabla.VC_ID, ventaDet.getVc_id());
                values.put(VentaDetalleTabla.VD_CANTIDAD, ventaDet.getVd_cantidad());
                values.put(VentaDetalleTabla.VD_PRECIO, ventaDet.getVd_precio());
                values.put(VentaDetalleTabla.PROD_NOMBRE, ventaDet.getProd_nombre());
                values.put(VentaDetalleTabla.PROD_RUTA_FOTO, ventaDet.getProd_ruta_foto());

                db.insert(VentaDetalleTabla.TABLA, VentaDetalleTabla.VD_ID,  values);
                SessionPreferences.get(context).setVentaDetalle(ventaDet.getVd_id());
                break;
        }
    }

    public static void registrarVentaDetalle(Context context , List<ProductoVenta> lista, int vc_id) {
        int codigo = 0;
        for (ProductoVenta item : lista){
            codigo = SessionPreferences.get(context).getVentaDetalle();
            registrar(context, new VentaDetalle(codigo,
                    item.getProd_cantidad(),
                    item.getProd_precio_venta(),
                    vc_id,
                    item.getProd_nombre(),
                    item.getProd_ruta_foto()), VentaDetalleTabla.TABLA);
        }
    }
}