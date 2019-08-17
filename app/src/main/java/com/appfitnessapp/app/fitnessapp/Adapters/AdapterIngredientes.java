package com.appfitnessapp.app.fitnessapp.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Asesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.IngredientesViewHolder>
        implements View.OnClickListener {

    AdapterIngredientes adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Ingredientes> ingredientes;
    private static final String TAG = "BAJARINFO:";


    public static class IngredientesViewHolder extends RecyclerView.ViewHolder{


        TextView txtNombreIngrediente,txtCantidad;

        public IngredientesViewHolder (View itemView) {
            super(itemView);


            txtNombreIngrediente=itemView.findViewById(R.id.txtNombreIngrediente);
            txtCantidad=itemView.findViewById(R.id.txtCantidad);



        }

    }

    public AdapterIngredientes(ArrayList<Ingredientes>ingredientes){

        this.ingredientes=ingredientes;
    }

    @Override
    public AdapterIngredientes.IngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_07_diseno_ingredientes,null,false);
        v.setOnClickListener(this);

        AdapterIngredientes.IngredientesViewHolder holder=new AdapterIngredientes.IngredientesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIngredientes.IngredientesViewHolder holder, int position) {
        Ingredientes ingrediente = ingredientes.get(position);



        holder.txtCantidad.setText(ingrediente.getCantidad());
        holder.txtNombreIngrediente.setText(ingrediente.getNombre_ingrediente());


    }



    @Override
    public int getItemCount() {
        return ingredientes.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {



        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }


}


