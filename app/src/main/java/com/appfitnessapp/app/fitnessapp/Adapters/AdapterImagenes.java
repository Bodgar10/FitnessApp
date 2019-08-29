package com.appfitnessapp.app.fitnessapp.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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


        TextView txtNombre;

        ImageView imgEjercicio,imgEjercicio2,imgEjercicio3;

        public EjericiosViewHolder (View itemView) {
            super(itemView);

            txtNombre=itemView.findViewById(R.id.txtNombre);


            imgEjercicio=itemView.findViewById(R.id.imgEjercicio);
            imgEjercicio2=itemView.findViewById(R.id.imgEjercicio2);
            imgEjercicio3=itemView.findViewById(R.id.imgEjercicio3);



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


        holder.txtNombre.setText(ejercicio.getNombre());


        if (!ejercicio.getImagen_1().equals("nil")&&!ejercicio.getImagen_2().equals("nil")&&!ejercicio.getImagen_3().equals("nil")){

            try {
                URL urlfeed = new URL(ejercicio.getImagen_1());
                Picasso.get().load(String.valueOf(urlfeed))
                        .error(R.drawable.ic_imgnull)
                        .fit()
                        .noFade()
                        .into(holder.imgEjercicio);

                URL urlfeed1 = new URL(ejercicio.getImagen_2());
                Picasso.get().load(String.valueOf(urlfeed1))
                        .error(R.drawable.ic_imgnull)
                        .fit()
                        .noFade()
                        .into(holder.imgEjercicio2);

                URL urlfeed2 = new URL(ejercicio.getImagen_3());
                Picasso.get().load(String.valueOf(urlfeed2))
                        .error(R.drawable.ic_imgnull)
                        .fit()
                        .noFade()
                        .into(holder.imgEjercicio3);

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



