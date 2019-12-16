package com.appfitnessapp.app.fitnessapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterAdmin  extends RecyclerView.Adapter<AdapterAdmin.ChatViewHolder>
        implements View.OnClickListener {

    AdapterAdmin adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Usuarios> asesorias;
    private static final String TAG = "BAJARINFO:";


    public static class ChatViewHolder extends RecyclerView.ViewHolder{


        TextView txtNombre;
        ImageView imagen;

        public ChatViewHolder (View itemView) {
            super(itemView);

            txtNombre=itemView.findViewById(R.id.txtNombreAsesoria);
            imagen=itemView.findViewById(R.id.imgAsesor1);



        }

    }

    public AdapterAdmin(ArrayList<Usuarios>asesorias){

        this.asesorias=asesorias;
    }

    @Override
    public AdapterAdmin.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_diseno,null,false);
        v.setOnClickListener(this);

        AdapterAdmin.ChatViewHolder holder=new AdapterAdmin.ChatViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdmin.ChatViewHolder holder, int position) {
        Usuarios asesoria = asesorias.get(position);



        holder.txtNombre.setText(asesoria.getNombre_usuario());

        if (!asesoria.getFoto_usuario().equals("nil")){

            try {
                URL urlAdmin = new URL(asesoria.getFoto_usuario());
                Picasso.get().load(String.valueOf(urlAdmin))
                        .error(R.drawable.ic_imgnull)
                        .fit()
                        .centerCrop()
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