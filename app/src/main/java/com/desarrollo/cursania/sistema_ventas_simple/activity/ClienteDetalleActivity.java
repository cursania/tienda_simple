package com.desarrollo.cursania.sistema_ventas_simple.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Cliente;
import com.desarrollo.cursania.sistema_ventas_simple.data.preferencia.SessionPreferences;
import com.desarrollo.cursania.sistema_ventas_simple.data.util.Mensaje;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Delete;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Insert;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Update;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.tablas.ClienteTabla;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteDetalleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.acdTietTel)
    EditText telefono;

    @BindView(R.id.acdTietEmail)
    EditText email;

    @BindView(R.id.acdTietNombre)
    EditText nombre;

    @BindView(R.id.acdTietDireccion)
    EditText direccion;

    @BindView(R.id.acdLLAgregar)
    LinearLayout agregar;

    @BindView(R.id.acdLLModificar)
    LinearLayout modificar;

    Boolean bNuevo = true , bModificado = false ;

    Cliente cliente;

    Mensaje mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //ocultar el teclado por primera ves
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN );

        //boton de retorno en el toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensaje = new Mensaje(getApplicationContext());

        if (getIntent().hasExtra("bNuevo")){
            bNuevo = getIntent().getBooleanExtra("bNuevo", true);
        }

        agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
        modificar.setVisibility(!bNuevo ? View.VISIBLE : View.INVISIBLE);

        if (!bNuevo){
            cliente = (Cliente)getIntent().getSerializableExtra("itemCliente");
            cargarVista(cliente);
        }
        nombre.requestFocus();

    }

    @OnClick(R.id.acdBAgregar)
    void clickAgregar(){

        if (nombre.getText().length() > 0) {
            int codigo = SessionPreferences.get(getApplicationContext()).getCliente();

            Insert.registrar(getApplicationContext(),
                    new Cliente(
                            codigo,
                            nombre.getText().toString(),
                            telefono.getText().toString(),
                            email.getText().toString(),
                            direccion.getText().toString()),
                    ClienteTabla.TABLA);

            bModificado = true;
            mensaje.mensajeToasGuardar();
            cargarVista(new Cliente(0, "", "", "", ""));
        }else{
            mensaje.mensajeToas("Ingrese un nombre");
            nombre.requestFocus();
        }

    }

    @OnClick(R.id.acdBModificar)
    void clickModificar(){

        if (nombre.getText().length() > 0) {
            Update.actualizar(getApplicationContext(),
            new Cliente(
                    cliente.getClie_id(),
                    nombre.getText().toString(),
                    telefono.getText().toString(),
                    email.getText().toString(),
                    direccion.getText().toString()),
                    ClienteTabla.TABLA);

            bModificado = true;
            mensaje.mensajeToasGuardar();
            salirActivity();
        }
        else {
            mensaje.mensajeToas("Ingrese un nombre");
            nombre.requestFocus();
        }

    }
    @OnClick(R.id.acdBEliminar)
    void clickEliminar(){

        new AlertDialog.Builder(this)
                .setTitle("Cliente")
                .setMessage("¿Desea eliminar el cliente?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete.eliminar(getApplicationContext(), cliente.getClie_id(),ClienteTabla.TABLA);
                        bModificado = true;
                        mensaje.mensajeToasEliminar();
                        salirActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

    private void cargarVista(Cliente cliente) {

        nombre.setText(cliente.getClie_nombre());
        telefono.setText(cliente.getClie_num_cel());
        email.setText(cliente.getClie_email());
        direccion.setText(cliente.getClie_direccion());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        if (bModificado){
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}
