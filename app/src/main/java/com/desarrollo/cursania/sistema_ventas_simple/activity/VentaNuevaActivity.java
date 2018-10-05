package com.desarrollo.cursania.sistema_ventas_simple.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.adaptador.VentaItemRecycler;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Cliente;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Producto;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.ProductoVenta;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.sistema_ventas_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Mensaje;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Metodos;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Insert;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Select;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.VentaCabeceraTabla;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaNuevaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avnRvProductosSeleccioandos)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager  layoutManager;
    VentaItemRecycler adaptador;

    Boolean bModificado;

    @BindView(R.id.avnActvCliente)
    AutoCompleteTextView listaAutoCliente;

    @BindView(R.id.avnTietTotal)
    EditText totalVenta;

    @BindView(R.id.avnTietObservacion)
    EditText comentario;

    List<ProductoVenta> listaProductoVenta = new ArrayList<>();

    Mensaje mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_nueva);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        mensaje = new Mensaje(getApplicationContext());
        
        cargarAutoComplet();

        if (getIntent().hasExtra("listaProducto")){
            List<Producto> tempProducto = Metodos.convertirProductoTextoALista(getIntent().getStringExtra("listaProducto"));

            for (Producto item : tempProducto){
                listaProductoVenta.add(new ProductoVenta(item, item.getProd_precio(), 1, item.getProd_precio()));
            }
            cargarRecycler();
        }
    }

    @OnClick(R.id.avnBNuevo)
    void clickNuevaVenta(){
        if (listaProductoVenta.size() > 0){
            if (listaAutoCliente.getText().length() > 0){
                registrarVentaNueva();
            }else {
                mensaje.mensajeToas("Ingrese un cliente");
            }
        }else {
            mensaje.mensajeToas("No hay productos en la lista");
        }
    }

    private void registrarVentaNueva() {

        int codigoCabecera = SessionPreferences.get(getApplicationContext()).getVentaCabecera();
        Insert.registrar(getApplicationContext(),
                new VentaCabecera(
                        codigoCabecera,
                        Metodos.getFecha(),
                        Metodos.getHora(),
                        Double.parseDouble(totalVenta.getText().toString()),
                        comentario.getText().toString(),
                        listaAutoCliente.getText().toString()
                ), VentaCabeceraTabla.TABLA);

        Insert.registrarVentaDetalle(getApplicationContext(), listaProductoVenta,codigoCabecera);

        bModificado = true;
        mensaje.mensajeToas("Venta realizada");
        salirActivity();

    }

    private void cargarRecycler() {
        adaptador = new VentaItemRecycler(listaProductoVenta, totalVenta);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);
        adaptador.sumarLista();
    }

    private void cargarAutoComplet() {
        List<Cliente> tempListaCliente = new ArrayList<>();
        Select.seleccionarClientes(getApplicationContext(), tempListaCliente, "");

        String[] nombre = new String[tempListaCliente.size()];

        int i = 0;
        for (Cliente item : tempListaCliente){
            nombre[i] = item.getClie_nombre();
            i++;
        }
        ArrayAdapter<String> adaptadorCliente = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombre);
        listaAutoCliente.setAdapter(adaptadorCliente);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        salirActivity();
    }

    void salirActivity(){
        if (bModificado){
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}