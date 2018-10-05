package com.desarrollo.cursania.sistema_ventas_simple.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.adaptador.VentaHistorialItemProductoRecycler;
import com.desarrollo.cursania.sistema_ventas_simple.adaptador.VentaHistorialItemRecycler;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaDetalle;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Mensaje;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Delete;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaHistorialDetalleActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avhdActvCliente)
    TextInputEditText cliente;

    @BindView(R.id.avhdActvFecha)
    TextInputEditText fecha;

    @BindView(R.id.avhdTietObservacion)
    TextInputEditText observacion;

    @BindView(R.id.avhdRvProductosSeleccionados)
    RecyclerView recyclerView;

    @BindView(R.id.avhdTietTotal)
    TextInputEditText total;

    RecyclerView.LayoutManager layoutManager;
    VentaHistorialItemProductoRecycler adaptador;

    List<VentaDetalle> listaDetalle = new ArrayList<>();

    boolean bCancelado = false;

    VentaCabecera ventaCabecera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_historial_detalle);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        if (getIntent().hasExtra("itemVentaCabecera")){
            ventaCabecera = (VentaCabecera)getIntent().getSerializableExtra("itemVentaCabecera");
            cliente.setText(ventaCabecera.getClie_nombre());
            fecha.setText(ventaCabecera.getVc_fecha().concat(" ").concat(ventaCabecera.getVc_hora()));
            observacion.setText(ventaCabecera.getVc_comentario());
        }
        
        cargarDetalle();

    }

    @OnClick(R.id.avhdBCancelar)
    void onClickCancelar(){

        new AlertDialog.Builder(this)
                .setTitle("Ventas realizadas")
                .setMessage("¿Deseas cancelar esta venta?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete.eliminarVenta(getApplicationContext(), listaDetalle, ventaCabecera.getVc_id());
                        new Mensaje(getApplicationContext()).mensajeToas("Venta cancelada");
                        bCancelado = true;
                        salirActivity();
                    }
                })
                .setNegativeButton(android.R.string.no,null).show();
    }

    private void cargarDetalle() {
        Select.seleccionarVentaDetalle(getApplicationContext(), listaDetalle, ventaCabecera.getVc_id());
        cargarRecycler();
    }

    private void cargarRecycler() {
        adaptador = new VentaHistorialItemProductoRecycler(listaDetalle, total);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);
        adaptador.sumarItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                salirActivity();
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
        if (bCancelado){
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}
