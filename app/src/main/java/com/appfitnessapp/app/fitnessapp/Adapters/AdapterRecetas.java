package com.appfitnessapp.app.fitnessapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Arrays.Asesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Recetas;
import com.appfitnessapp.app.fitnessapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterRecetas extends RecyclerView.Adapter<AdapterRecetas.RecetasViewHolder>
        implements View.OnClickListener {

    AdapterRecetas adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Recetas> recetas;
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

    public AdapterRecetas(ArrayList<Recetas>recetas){

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
        Recetas receta = recetas.get(position);



        holder.txtTiempoComida.setText(receta.getTiempo_comida());
        holder.txtNombreComida.setText(receta.getNombre_comida());




        if (!receta.getImagen_receta().equals("nil")){
            Log.e(TAG, "Imagen admin: "+ receta.getImagen_receta());


            try {


                URL urlAdmin = new URL(receta.getImagen_receta());
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



