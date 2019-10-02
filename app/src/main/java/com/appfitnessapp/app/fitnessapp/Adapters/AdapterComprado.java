package com.appfitnessapp.app.fitnessapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Comprado;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;


public class AdapterComprado extends RecyclerView.Adapter<AdapterComprado.FeedViewHolder>
        implements View.OnClickListener{

    AdapterComprado adapterFeed;

    private View.OnClickListener listener;
    ArrayList<Comprado> comprados;
    static DBProvider dbProvider;

    private static final String TAG = "BAJARINFO:";

    public static class FeedViewHolder extends RecyclerView.ViewHolder{


        TextView txtDescripcion;

        public FeedViewHolder (View itemView) {
            super(itemView);


            txtDescripcion=itemView.findViewById(R.id.txtDescripcion);

        }

    }

    public AdapterComprado(ArrayList<Comprado>comprados){

        this.comprados=comprados;
    }

    @Override
    public AdapterComprado.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_diseno_comprado,null,false);
        v.setOnClickListener(this);

        AdapterComprado.FeedViewHolder holder=new AdapterComprado.FeedViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterComprado.FeedViewHolder holder, int position) {
        Comprado comprado = comprados.get(position);

        holder.txtDescripcion.setText(comprado.getDescripcion());


    }



    @Override
    public int getItemCount() {
        return comprados.size();
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
