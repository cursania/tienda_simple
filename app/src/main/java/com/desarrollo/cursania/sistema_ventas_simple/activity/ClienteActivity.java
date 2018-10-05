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
import com.desarrollo.cursania.sistema_ventas_simple.adaptador.ClienteItemRecycler;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Cliente;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteActivity extends AppCompatActivity {

    //region variables con la instancia de controles
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.acRvCliente)
    RecyclerView recyclerView;

    @BindView(R.id.acEtBuscarCliente)
    EditText buscador;

    //endregion

    //objetos utiles para la carga del recycler
    RecyclerView.LayoutManager layoutManager;

    //objeto para la personalizacion del item del recycler
    ClienteItemRecycler adaptador;

    //lista de objetos clientes
    List<Cliente> listaCliente = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        //injeccion
        ButterKnife.bind(this);
        //toolbar en activity
        setSupportActionBar(toolbar);
        //ocultar el teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //cargamos al layout manajer con la instacia del contexto
        layoutManager = new LinearLayoutManager(getApplicationContext());

        cargarLista();
        //interaccion con el buscador
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

    @OnClick(R.id.acFabNuevoCliente)
    public void clickNuevoCliente(){
        irActivity(true, new Cliente());
    }

    private void cargarLista() {
        Select.seleccionarClientes(getApplicationContext(), listaCliente, buscador.getText().toString());

        cargarRecycler(listaCliente);
    }

    private void cargarRecycler(List<Cliente> listaCliente) {

        adaptador = new ClienteItemRecycler(listaCliente, new ClienteItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Cliente cliente, int position) {
                irActivity(false, cliente);
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adaptador);

    }

    private void irActivity(boolean bNuevo, Cliente cliente) {
        Intent intent = new Intent(getApplicationContext(), ClienteDetalleActivity.class);

        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemCliente", cliente);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarLista();
        }
    }
}
