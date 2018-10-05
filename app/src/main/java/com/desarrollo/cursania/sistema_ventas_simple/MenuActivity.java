package com.desarrollo.cursania.sistema_ventas_simple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.desarrollo.cursania.sistema_ventas_simple.activity.ClienteActivity;
import com.desarrollo.cursania.sistema_ventas_simple.activity.ProductoActivity;
import com.desarrollo.cursania.sistema_ventas_simple.activity.VentaActivity;
import com.desarrollo.cursania.sistema_ventas_simple.activity.VentaHistorialActivity;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.ConexionSqliteHelper;
import com.desarrollo.cursania.sistema_ventas_simple.esquemaSqlite.crud.Select;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {
    //instanciamos el control
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //conexion con sqlite
    ConexionSqliteHelper con ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //injeccion
        ButterKnife.bind(this);
        //cargamos el toolbar
        setSupportActionBar(toolbar);
        //crear la base de datos
        con = new ConexionSqliteHelper(MenuActivity.this);

    }

    //injectamos a los botones
    @OnClick(R.id.amIbProducto)
    public void clickProducto(){
        irActivity(ProductoActivity.class);
    }

    @OnClick(R.id.amIbCliente)
    public void clickCliente(){
        irActivity(ClienteActivity.class);
    }

    @OnClick(R.id.amIbVenta)
    public void clickVenta(){
        irActivity(VentaActivity.class);
    }

    @OnClick(R.id.amIbVentaHistorial)
    public void clickVentaHistorial(){
        irActivity(VentaHistorialActivity.class);
    }

    //metodo de llamado actividades  -- Como parametro toma la clase Class<?>, ya que pasaremos diferentes actividades
    void irActivity(Class<?> paramClass){
        //intento para mostrar otras actividades
        Intent intent = new Intent(getApplicationContext(), paramClass);
        //limpiamos la cola de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //llamamos a la actividad
        startActivity(intent);
    }

    //menu lado derecho
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflamos el menu, para incluir el menu personalizado
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //opcion de menu seleccionada
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
                //click opcion de backup
            case R.id.menuBackup:
                //metodo para el backup
                Select.backup(getApplicationContext());
                Toast.makeText(this, "Backup Correcto", Toast.LENGTH_SHORT).show();
                break;

                //click opcion de restore
            case R.id.menuRestore:
                //metodo para el restore
                Select.restaurar(getApplicationContext());
                Toast.makeText(this, "Restauración exitosa", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        //cerramos conexion al destruirse la actividad
        con.close();
        //que realice su trabajo comun
        super.onDestroy();
    }
}
