package com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Cliente;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Producto;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaDetalle;
import com.desarrollo.cursania.sistema_ventas_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Metodos;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.ConexionSqliteHelper;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.ClienteTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.ProductoTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.VentaDetalleTabla;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Select {

    private static ConexionSqliteHelper con = null;
    private static SQLiteDatabase db = null;

    private static List<Object> seleccionarRegistros(Context context, String tabla){
        List<Object> listaRetorno = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor cLista = db.query(tabla,null,null,null,null,null,null);

        while (cLista.moveToNext()){
            switch (tabla){

                case ClienteTabla.TABLA:
                    listaRetorno.add(
                            new Cliente(
                                    cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NUM_CEL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                            )
                    );
                    break;

                case ProductoTabla.TABLA:
                    listaRetorno.add(
                            new Producto(
                                    cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                    cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                                    false
                            )
                    );
                    break;
            }
        }

        return listaRetorno;
    }

    public static void seleccionarClientes(Context context, List<Cliente> lista,String buscar){

        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ClienteTabla.TABLA);

        for (Object item : tempLista){
            Cliente _item = (Cliente)item;

            if (buscar.length() > 0){

                if (_item.getClie_nombre().length() >= buscar.length()){

                    String cadenaRecortada = _item.getClie_nombre().substring(0, buscar.length());

                    if (buscar.equals(cadenaRecortada))lista.add(_item);
                }

            }else{
                lista.add(_item);
            }

        }
    }



    public static void seleccionarProductos(Context context, List<Producto> lista,String buscar){

        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ProductoTabla.TABLA);

        for (Object item : tempLista){
            Producto _item = (Producto)item;

            if (buscar.length() > 0){

                if (_item.getProd_nombre().length() >= buscar.length()){

                    String cadenaRecortada = _item.getProd_nombre().substring(0, buscar.length());

                    if (buscar.equals(cadenaRecortada))lista.add(_item);
                }

            }else{
                lista.add(_item);
            }

        }
    }

    public static void seleccionarVentaCabecera(Context context,List<VentaCabecera> lista,String fecha , String buscar){

        lista.clear();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor tempLista ;

        String query = Metodos.concatenar(new Object[]{"select * from ", VentaCabeceraTabla.TABLA , " where "
            , VentaCabeceraTabla.VC_FECHA , " = ? order by ", VentaCabeceraTabla.VC_FECHA , " , ", VentaCabeceraTabla.VC_HORA, " desc"});

        tempLista = db.rawQuery(query, new String[]{fecha});

        while (tempLista.moveToNext()){

            VentaCabecera item = new VentaCabecera(
                    tempLista.getInt(tempLista.getColumnIndex(VentaCabeceraTabla.VC_ID)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_FECHA)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_HORA)),
                    tempLista.getDouble(tempLista.getColumnIndex(VentaCabeceraTabla.VC_MONTO)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_COMENTARIO)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.CLIE_NOMBRE))
            );

            if (buscar.length() > 0){

                if (item.getClie_nombre().length() >= buscar.length()){

                    String cadenaRecortada = item.getClie_nombre().substring(0, buscar.length());

                    if (buscar.equals(cadenaRecortada))lista.add(item);
                }

            }else{
                lista.add(item);
            }
        }
    }

    public static void seleccionarVentaDetalle(Context context, List<VentaDetalle> lista, int vc_id){
        lista.clear();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor tempLista ;

        String query = Metodos.concatenar(new Object[]{"select * from ", VentaDetalleTabla.TABLA , " where "
                , VentaDetalleTabla.VC_ID , " = ? order by ", VentaDetalleTabla.PROD_NOMBRE , " desc"});

        tempLista = db.rawQuery(query, new String[]{String.valueOf(vc_id)});

        while (tempLista.moveToNext()) {

            lista.add(new VentaDetalle(
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VD_ID)),
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VD_CANTIDAD)),
                    tempLista.getDouble(tempLista.getColumnIndex(VentaDetalleTabla.VD_PRECIO)),
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VC_ID)),
                    tempLista.getString(tempLista.getColumnIndex(VentaDetalleTabla.PROD_NOMBRE)),
                    tempLista.getString(tempLista.getColumnIndex(VentaDetalleTabla.PROD_RUTA_FOTO))
            ));
        }
    }

    public static void backup(Context context){
        StringBuilder cadenaCompuesta = new StringBuilder();
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        for (String tablaSeleccionada : new String[]{ClienteTabla.TABLA, ProductoTabla.TABLA, VentaCabeceraTabla.TABLA, VentaDetalleTabla.TABLA}){

            cadenaCompuesta.append("/////").append(tablaSeleccionada.toUpperCase()).append("/////").append("\n");

            Cursor cLista = db.query(tablaSeleccionada,null,null,null,null,null,null);

            while (cLista.moveToNext()){
                switch (tablaSeleccionada){

                    case ClienteTabla.TABLA:
                        cadenaCompuesta.append(
                                new Cliente(
                                        cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NUM_CEL)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                                ).componer("|")).append("\n");
                        break;

                    case ProductoTabla.TABLA:
                        cadenaCompuesta.append(
                                new Producto(
                                        cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                        cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                                        false
                                ).componer("|")).append("\n");
                        break;
                    case VentaCabeceraTabla.TABLA:
                        cadenaCompuesta.append(
                                new VentaCabecera(
                                        cLista.getInt(cLista.getColumnIndex(VentaCabeceraTabla.VC_ID)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_FECHA)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_HORA)),
                                        cLista.getDouble(cLista.getColumnIndex(VentaCabeceraTabla.VC_MONTO)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_COMENTARIO)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.CLIE_NOMBRE))
                                ).componer("|")).append("\n");
                        break;

                    case VentaDetalleTabla.TABLA:
                        cadenaCompuesta.append(
                                new VentaDetalle(
                                        cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VD_ID)),
                                        cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VD_CANTIDAD)),
                                        cLista.getDouble(cLista.getColumnIndex(VentaDetalleTabla.VD_PRECIO)),
                                        cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VC_ID)),
                                        cLista.getString(cLista.getColumnIndex(VentaDetalleTabla.PROD_NOMBRE)),
                                        cLista.getString(cLista.getColumnIndex(VentaDetalleTabla.PROD_RUTA_FOTO))
                                ).componer("|")).append("\n");
                        break;
                }
        }
    }

    try {
            String nombre = "tienda.txt";
            File tarjeta = Environment.getExternalStorageDirectory();
            File file = new File(tarjeta.getAbsolutePath(), nombre);

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
        osw.write(cadenaCompuesta.toString());
        osw.flush();
        osw.close();
    }catch (IOException e){
        e.printStackTrace();
    }
    }

    public static void restaurar(Context context){

        String nombreDeTabla = null;
        boolean bIngreso = true;
        List<VentaCabecera> list = new ArrayList<>();
        int contador = 0, numeroAnterior = 0;
        File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(tarjeta.getAbsolutePath(),"tienda.txt");

        try {
            FileInputStream oFile = new FileInputStream(file);
            InputStreamReader archivo = new InputStreamReader(oFile);
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();

            while (linea != null){
                bIngreso =  linea.equals("/////" + ClienteTabla.TABLA.toUpperCase() + "/////") ||
                            linea.equals("/////" + ProductoTabla.TABLA.toUpperCase() + "/////") ||
                            linea.equals("/////" + VentaCabeceraTabla.TABLA.toUpperCase() + "/////")||
                            linea.equals("/////" + VentaDetalleTabla.TABLA.toUpperCase() + "/////");


                if (bIngreso){
                    nombreDeTabla = linea.equals("/////" + ClienteTabla.TABLA.toUpperCase() + "/////") ? ClienteTabla.TABLA :
                                    linea.equals("/////" + ProductoTabla.TABLA.toUpperCase() + "/////") ? ProductoTabla.TABLA :
                                    linea.equals("/////" + VentaCabeceraTabla.TABLA.toUpperCase() + "/////") ? VentaCabeceraTabla.TABLA :
                                            VentaDetalleTabla.TABLA;
                }else {
                    switch (nombreDeTabla){
                        case ClienteTabla.TABLA :
                            Cliente cliente = new Cliente(linea,"|");
                            cliente.setClie_id(SessionPreferences.get(context).getCliente());
                            Insert.registrar(context, cliente, ClienteTabla.TABLA);
                            break;

                        case ProductoTabla.TABLA :
                            Producto producto = new Producto(linea, "|");
                            producto.setProd_id(SessionPreferences.get(context).getProducto());
                            Insert.registrar(context, producto, ProductoTabla.TABLA);
                            break;

                        case VentaCabeceraTabla.TABLA :
                            VentaCabecera ventaCabecera = new VentaCabecera(linea, "|");
                            ventaCabecera.setVc_id(SessionPreferences.get(context).getVentaCabecera());
                            Insert.registrar(context,ventaCabecera, VentaCabeceraTabla.TABLA);
                            list.add(ventaCabecera);

                            break;

                        case VentaDetalleTabla.TABLA :
                            VentaDetalle ventaDetalle = new VentaDetalle(linea, "|");
                            if (numeroAnterior == 0){
                                numeroAnterior = ventaDetalle.getVd_id();
                                ventaDetalle.setVc_id(list.get(contador).getVc_id());
                            }else {
                                if (numeroAnterior == ventaDetalle.getVd_id()){
                                    ventaDetalle.setVc_id(list.get(contador).getVc_id());
                                }else {
                                    numeroAnterior = ventaDetalle.getVd_id();
                                    contador +=1;
                                    ventaDetalle.setVc_id(list.get(contador).getVc_id());
                                }
                            }
                            ventaDetalle.setVd_id(SessionPreferences.get(context).getVentaDetalle());
                            Insert.registrar(context, ventaDetalle, VentaDetalleTabla.TABLA);
                            break;
                    }
                }
                linea = br.readLine();
            }
            br.close();
            archivo.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
