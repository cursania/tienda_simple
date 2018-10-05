package com.desarrollo.cursania.sistema_ventas_simple.activity;

import android.app.Activity;
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
import android.widget.EditText;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.adaptador.ProductoItemRecycler;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Producto;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Mensaje;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Metodos;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avRvProducto)
    RecyclerView recyclerView;

    @BindView(R.id.avEtBuscarProducto)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;

    ProductoItemRecycler adaptador;

    List<Producto> listaProducto = new ArrayList<>();
    List<Producto> listaProductoSeleccionados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //ocultar el teclado al iniciar la activity
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //cargamos el layout con la actividad
        layoutManager = new LinearLayoutManager(getApplicationContext());

        cargarLista();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cargarLista();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.avFabNuevoProducto)
    void clickVentaNueva()
    {
        listaProductoSeleccionados.clear();
        for (Producto item : listaProducto){
            if (item.getProd_seleccionado())listaProductoSeleccionados.add(item);
        }

        if (listaProductoSeleccionados.size() > 0){
            irActivity();
        }else{
            new Mensaje(getApplicationContext()).mensajeToas("No ha seleccionado productos");
        }
    }

    private void cargarLista() {

        Select.seleccionarProductos(getApplicationContext(), listaProducto, buscador.getText().toString());
        cargarRecycler(listaProducto);
    }

    private void cargarRecycler(final List<Producto> listaProducto) {

        adaptador = new ProductoItemRecycler(listaProducto, new ProductoItemRecycler.OnItemClickListener() {
            @Override
            public void OnClickItem(Producto producto, int posicion) {
                listaProducto.get(posicion).setProd_seleccionado(!producto.getProd_seleccionado());
                cargarRecycler(listaProducto);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);
    }

    private void irActivity() {
        Intent intent = new Intent(getApplicationContext(), VentaNuevaActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("listaProducto", Metodos.convertirProductoListaATexto(listaProductoSeleccionados));

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarLista();
        }
    }


}
