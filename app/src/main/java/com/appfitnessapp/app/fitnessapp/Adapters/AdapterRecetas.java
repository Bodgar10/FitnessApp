package com.appfitnessapp.app.fitnessapp.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.PlanAlimenticio;
import com.appfitnessapp.app.fitnessapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterRecetas extends RecyclerView.Adapter<AdapterRecetas.RecetasViewHolder>
        implements View.OnClickListener {

    AdapterRecetas adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<PlanAlimenticio> recetas;
    private static final String TAG = "BAJARINFO:";


    public static class RecetasViewHolder extends RecyclerView.ViewHolder{


        TextView txtTiempoComida,txtNombreComida;
        ImageView imagen;

        public RecetasViewHolder (View itemView) {
            super(itemView);



            txtTiempoComida=itemView.findViewById(R.id.txtTiempoComida);
            txtNombreComida=itemView.findViewById(R.id.txtNombreComida);

            imagen=itemView.findViewById(R.id.imgComida);



        }

    }

    public AdapterRecetas(ArrayList<PlanAlimenticio>recetas){

        this.recetas=recetas;
    }

    @Override
    public AdapterRecetas.RecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_09_diseno,null,false);
        v.setOnClickListener(this);

        AdapterRecetas.RecetasViewHolder holder=new AdapterRecetas.RecetasViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecetas.RecetasViewHolder holder, int position) {
        PlanAlimenticio receta = recetas.get(position);



        holder.txtTiempoComida.setText(receta.getTipo_alimento());
        holder.txtNombreComida.setText(receta.getNombre_alimento());



        if (!receta.getImagen_alimento().equals("nil")){

            try {
                URL urlfeed = new URL(receta.getImagen_alimento());
                Picasso.get().load(String.valueOf(urlfeed))
                        .fit()
                        .centerInside()
                        .error(R.mipmap.ic_launcher)
                        .into(holder.imagen);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }


    }



    @Override
    public int getItemCount() {
        return recetas.size();
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



