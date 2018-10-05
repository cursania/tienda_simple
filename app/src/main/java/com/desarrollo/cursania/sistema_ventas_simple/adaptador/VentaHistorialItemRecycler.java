package com.desarrollo.cursania.sistema_ventas_simple.adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.VentaCabecera;

import java.util.List;

public class VentaHistorialItemRecycler extends RecyclerView.Adapter<VentaHistorialItemRecycler.ViewHolderVentaHistorial>{

    List<VentaCabecera> listaCabecera;
    OnItemClickListener itemClickListener;

    public VentaHistorialItemRecycler(List<VentaCabecera> listaCabecera, OnItemClickListener itemClickListener) {
        this.listaCabecera = listaCabecera;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderVentaHistorial onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_venta_historial, parent, false);
        return new ViewHolderVentaHistorial(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVentaHistorial holder, int position) {
        holder.bind(listaCabecera.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaCabecera.size();
    }

    public interface OnItemClickListener{
        void onItemClick(VentaCabecera ventaCabecera,int position);
    }

    class ViewHolderVentaHistorial extends RecyclerView.ViewHolder{
        TextView nombre, fecha, hora, total;

        public ViewHolderVentaHistorial(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.rivhTvNombre);
            fecha = itemView.findViewById(R.id.rivhTvFecha);
            hora = itemView.findViewById(R.id.rivhTvHora);
            total = itemView.findViewById(R.id.rivhTvTotal);
        }

        void bind(final VentaCabecera ventaCabecera, final OnItemClickListener clickListener){
            nombre.setText(ventaCabecera.getClie_nombre());
            fecha.setText(ventaCabecera.getVc_fecha());
            hora.setText(ventaCabecera.getVc_hora());
            total.setText(String.valueOf(ventaCabecera.getVc_monto()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(ventaCabecera, getAdapterPosition());
                }
            });
        }
    }
}
