package com.desarrollo.cursania.sistema_ventas_simple.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.adaptador.VentaHistorialItemRecycler;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaCabecera;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Metodos;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaHistorialActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avhRvProducto)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    VentaHistorialItemRecycler adaptador;

    @BindView(R.id.avhEtBuscarCliente)
    EditText buscador;

    List<VentaCabecera> ventaCabeceraLista = new ArrayList<>();

    String fecha = "";
    int anio, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_historial);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        layoutManager = new LinearLayoutManager(getApplicationContext());


        calendarioCargarVariables();

        filtrarVentas();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarVentas();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.avhFabBuscarVenta)
    void clickBuscarVenta(){
        new DatePickerDialog(this,calendarioFlotante,anio,mes,dia).show();
    }

    private void calendarioCargarVariables() {
        Calendar calendar = Calendar.getInstance();
        anio = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        fecha = Metodos.getFecha();
    }

    private DatePickerDialog.OnDateSetListener calendarioFlotante = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            anio = year;
            mes = month;
            dia = dayOfMonth; // 1 , 2 -- 01 , 02
            fecha = Metodos.concatenar(new Object[]{anio, "-" , String.format("%02d",(mes +1)), "-" , String.format("%02d", dia)});
            filtrarVentas();
        }
    };

    private void filtrarVentas() {
        Select.seleccionarVentaCabecera(getApplicationContext(), ventaCabeceraLista,fecha, buscador.getText().toString());
        cargarRecycler(ventaCabeceraLista);
    }

    private void cargarRecycler(List<VentaCabecera> ventaCabeceraLista) {
        adaptador = new VentaHistorialItemRecycler(ventaCabeceraLista, new VentaHistorialItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(VentaCabecera ventaCabecera, int position) {
                irActivity(ventaCabecera);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);

    }

    void irActivity(VentaCabecera ventaCabecera){
        Intent intent = new Intent(getApplicationContext(), VentaHistorialDetalleActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("itemVentaCabecera", ventaCabecera);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            filtrarVentas();
        }
    }
}
