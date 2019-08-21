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
import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterAsesorias extends RecyclerView.Adapter<AdapterAsesorias.AsesoriasViewHolder>
        implements View.OnClickListener {

    AdapterAsesorias adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Usuarios> asesorias;
    private static final String TAG = "BAJARINFO:";


    public static class AsesoriasViewHolder extends RecyclerView.ViewHolder{


        TextView txtNombre,txtKilos,txtObjetivo;
        ImageView imagen;

        public AsesoriasViewHolder (View itemView) {
            super(itemView);



            txtNombre=itemView.findViewById(R.id.txtNombreDiseno);
            txtKilos=itemView.findViewById(R.id.txtKilosDiseno);
            txtObjetivo=itemView.findViewById(R.id.txtObjetivo);

            imagen=itemView.findViewById(R.id.imgPersonaDiseno);



        }

    }

    public AdapterAsesorias(ArrayList<Usuarios>asesorias){

        this.asesorias=asesorias;
    }

    @Override
    public AdapterAsesorias.AsesoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_03_diseno,null,false);
        v.setOnClickListener(this);

        AdapterAsesorias.AsesoriasViewHolder holder=new AdapterAsesorias.AsesoriasViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAsesorias.AsesoriasViewHolder holder, int position) {
        Usuarios asesoria = asesorias.get(position);



        holder.txtNombre.setText(asesoria.getNombre_usuario());
        holder.txtKilos.setText(asesoria.getPeso_actual());
        holder.txtObjetivo.setText(asesoria.getObjetivo());




        if (!asesoria.getFoto_usuario().equals("nil")){

            try {
                URL urlAdmin = new URL(asesoria.getFoto_usuario());
                Picasso.get().load(String.valueOf(urlAdmin))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imagen);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }



    }



    @Override
    public int getItemCount() {
        return asesorias.size();
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


