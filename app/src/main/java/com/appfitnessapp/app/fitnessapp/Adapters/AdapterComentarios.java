package com.appfitnessapp.app.fitnessapp.Adapters;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.Arrays.Valoraciones;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterComentarios extends RecyclerView.Adapter<AdapterComentarios.ComentariosViewHolder>
        implements View.OnClickListener {

    AdapterComentarios adapterPlanes;
    private View.OnClickListener listener;
    ArrayList<Valoraciones> planes;

    public static class ComentariosViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre,txtFecha,txtComentario;
        CircularImageView imgPersona;
        ImageView imgAntes,imgDespues;

        public ComentariosViewHolder (View itemView) {
            super(itemView);

            txtNombre=itemView.findViewById(R.id.txtNombreComen);
            txtFecha=itemView.findViewById(R.id.txtFechaComen);
            txtComentario=itemView.findViewById(R.id.txtComentario);

            imgPersona=itemView.findViewById(R.id.imgPersonaComen);

            imgAntes=itemView.findViewById(R.id.imgAntes);
            imgDespues=itemView.findViewById(R.id.imgDespues);
        }

    }
    public AdapterComentarios(ArrayList<Valoraciones>planes){
        this.planes=planes;
    }
    @Override
    public AdapterComentarios.ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_04_asesoria_comentarios,null,false);
        v.setOnClickListener(this);
        AdapterComentarios.ComentariosViewHolder holder=new AdapterComentarios.ComentariosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComentarios.ComentariosViewHolder holder, int position) {
        Valoraciones plan = planes.get(position);

        holder.txtFecha.setText(plan.getFecha_valoracion());
        holder.txtComentario.setText(plan.getDescripcion_valoracion());
        holder.txtNombre.setText(plan.getNombre_usuario_valoracion());

        if (!plan.getFoto_usuario().equals("nil")){

            try {

                URL urlUsuario = new URL(plan.getFoto_usuario());
                Picasso.get().load(String.valueOf(urlUsuario))
                        .error(R.drawable.ic_imgnull)
                        .fit()
                        .noFade()
                        .into(holder.imgPersona);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

        if (!plan.getImagen_antes().equals("nil")&&!plan.getImagen_despues().equals("nil")){

            try {
                URL urlAntes = new URL(plan.getImagen_antes());
                Picasso.get().load(String.valueOf(urlAntes))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imgAntes);
                URL urlDespues = new URL(plan.getImagen_despues());
                Picasso.get().load(String.valueOf(urlDespues))
                        .error(R.drawable.ic_imgnull)
                        .fit()
                        .noFade()
                        .into(holder.imgDespues);

                /*
                URL urlUsuario = new URL(plan.getFoto_usuario());
                Picasso.get().load(String.valueOf(urlUsuario))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imgPersona);
*/

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {

            holder.imgDespues.setVisibility(View.GONE);
            holder.imgAntes.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return planes.size();
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
