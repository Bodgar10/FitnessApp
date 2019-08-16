package com.appfitnessapp.app.fitnessapp.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Ejercicios;
import com.appfitnessapp.app.fitnessapp.Arrays.ImagenesEjercicios;
import com.appfitnessapp.app.fitnessapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterImagenes extends RecyclerView.Adapter<AdapterImagenes.EjericiosViewHolder>
        implements View.OnClickListener {

    AdapterImagenes adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<ImagenesEjercicios> ejercicios;
    private static final String TAG = "BAJARINFO:";


    public static class EjericiosViewHolder extends RecyclerView.ViewHolder{



        ImageView imgEjercicio;

        public EjericiosViewHolder (View itemView) {
            super(itemView);


            imgEjercicio=itemView.findViewById(R.id.imgEjercicio);


        }

    }

    public AdapterImagenes(ArrayList<ImagenesEjercicios>ejercicios){

        this.ejercicios=ejercicios;
    }

    @Override
    public AdapterImagenes.EjericiosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_20_diseno_img,null,false);
        v.setOnClickListener(this);

        AdapterImagenes.EjericiosViewHolder holder=new AdapterImagenes.EjericiosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImagenes.EjericiosViewHolder holder, int position) {
        ImagenesEjercicios ejercicio = ejercicios.get(position);



        if (!ejercicio.getImagen().equals("nil")){

            try {
                URL urlfeed = new URL(ejercicio.getImagen());
                Picasso.get().load(String.valueOf(urlfeed))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imgEjercicio);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

    }



    @Override
    public int getItemCount() {
        return ejercicios.size();
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



