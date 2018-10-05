package com.desarrollo.cursania.sistema_ventas_simple.adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desarrollo.cursania.sistema_ventas_simple.R;
import com.desarrollo.cursania.sistema_ventas_simple.data.modelo.Cliente;

import java.util.List;

public class ClienteItemRecycler extends RecyclerView.Adapter<ClienteItemRecycler.ViewHolderCliente>{

    List<Cliente> listaCliente;
    OnItemClickListener onItemClickListener;

    public ClienteItemRecycler(List<Cliente> listaCliente, OnItemClickListener onItemClickListener) {
        this.listaCliente = listaCliente;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_cliente,parent, false);
        return new ViewHolderCliente(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        holder.bind(listaCliente.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Cliente cliente, int position);
    }

    class ViewHolderCliente extends RecyclerView.ViewHolder{
        TextView nombre;
        ImageView dir, tel, mail;
        public ViewHolderCliente(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.ricTvNombre);

            dir = itemView.findViewById(R.id.ricIvDireccion);
            tel = itemView.findViewById(R.id.ricIvTelefono);
            mail = itemView.findViewById(R.id.ricIvMail);
        }

        void bind(final Cliente cliente, final OnItemClickListener itemClickListener){

            nombre.setText(cliente.getClie_nombre());

            dir.setVisibility(cliente.getClie_direccion().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            tel.setVisibility(cliente.getClie_num_cel().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            mail.setVisibility(cliente.getClie_email().isEmpty() ? View.INVISIBLE : View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(cliente, getAdapterPosition());
                }
            });
        }
    }
}
