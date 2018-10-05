package com.desarrollo.cursania.sistema_ventas_simple.adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaDetalle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VentaHistorialItemProductoRecycler extends RecyclerView.Adapter<VentaHistorialItemProductoRecycler.ViewHolderVentaHistorialItemProducto>{

    List<VentaDetalle> listaDetalle;
    TextView total;

    public VentaHistorialItemProductoRecycler(List<VentaDetalle> listaDetalle, TextView total) {
        this.listaDetalle = listaDetalle;
        this.total = total;
    }

    @NonNull
    @Override
    public ViewHolderVentaHistorialItemProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_venta_historial_producto, parent, false);

        return new ViewHolderVentaHistorialItemProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVentaHistorialItemProducto holder, int position) {
        holder.bind(listaDetalle.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDetalle.size();
    }

    public void sumarItems(){
        Double aDouble = 0.0;
        for (VentaDetalle item : listaDetalle){
            aDouble += item.getVd_precio() * item.getVd_cantidad();
        }

        total.setText(String.valueOf(aDouble));
    }

    class ViewHolderVentaHistorialItemProducto extends RecyclerView.ViewHolder{

        ImageView imagen;
        TextView nombre, cantidad, precio, total;


        public ViewHolderVentaHistorialItemProducto(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.rivhpCivImagen);
            nombre = itemView.findViewById(R.id.rivhpTvNombre);
            cantidad = itemView.findViewById(R.id.rivhpEtCantidad);
            precio = itemView.findViewById(R.id.rivhpEtPrecio);
            total = itemView.findViewById(R.id.rivhpTvTotal);
        }
        void bind (final VentaDetalle ventaDetalle){
            nombre.setText(ventaDetalle.getProd_nombre());
            cantidad.setText(String.valueOf(ventaDetalle.getVd_cantidad()));
            precio.setText(String.valueOf(ventaDetalle.getVd_precio()));
            total.setText(String.valueOf(ventaDetalle.getVd_precio() * ventaDetalle.getVd_cantidad()));

            if (ventaDetalle.getProd_ruta_foto().length() <= 1 || ventaDetalle.getProd_ruta_foto().isEmpty()){
                Picasso.get().load(R.drawable.caja_producto).into(imagen);
            }else {
                Picasso.get().load(ventaDetalle.getProd_ruta_foto())
                        .resize(65,65)
                        .error(R.drawable.caja_producto_error)
                        .centerCrop()
                        .into(imagen);
            }
        }
    }
}
