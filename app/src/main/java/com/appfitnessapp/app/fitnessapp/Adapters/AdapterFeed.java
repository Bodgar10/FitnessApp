package com.appfitnessapp.app.fitnessapp.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.DetallePdf;
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.HomeSinRegistro;
import com.appfitnessapp.app.fitnessapp.Usuario.Imagen;
import com.appfitnessapp.app.fitnessapp.Usuario.PantallaPDF;
import com.appfitnessapp.app.fitnessapp.Usuario.Video;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.FeedViewHolder>
        implements View.OnClickListener{

    AdapterFeed adapterFeed;

    private View.OnClickListener listener;
    ArrayList<Feed> feeds;
    URL imageURL = null;
    HttpURLConnection connection = null;

    Bitmap bm = null;
    private static final String TAG = "BAJARINFO:";

    public static class FeedViewHolder extends RecyclerView.ViewHolder{


        ImageView imgAdmin,imgFeed;
        TextView txtNombreAdmin,txtDescripcion,txtPrecio,txtHora,txtTipoFeed,urlTipo;
        Boolean isGratis;

        public FeedViewHolder (View itemView) {
            super(itemView);

            imgAdmin=itemView.findViewById(R.id.imgPostPersona);
            imgFeed=itemView.findViewById(R.id.imagPostU);

            txtNombreAdmin=itemView.findViewById(R.id.txtNombrePost);
            txtDescripcion=itemView.findViewById(R.id.txtDescripcionPost);
            txtPrecio=itemView.findViewById(R.id.txtTipoPostPrecio);
            txtHora=itemView.findViewById(R.id.txtTiempoPost);
            txtTipoFeed=itemView.findViewById(R.id.btnPostTipo);
            isGratis=false;


        }

    }

    public AdapterFeed(ArrayList<Feed>feeds){

        this.feeds=feeds;
    }

    @Override
    public AdapterFeed.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_14_diseno,null,false);
        v.setOnClickListener(this);

        AdapterFeed.FeedViewHolder holder=new AdapterFeed.FeedViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterFeed.FeedViewHolder holder, int position) {
        Feed feed = feeds.get(position);



        holder.txtNombreAdmin.setText(feed.getNombre_admin());
        holder.txtDescripcion.setText(feed.getDescripcion());
        holder.txtPrecio.setText(feed.getCosto_pdf());
        holder.txtHora.setText(feed.getTimestamp());
        holder.txtTipoFeed.setText(feed.getTipo_feed());




            if (feed.getTipo_feed().equals(Contants.VIDEO)) {
                holder.txtPrecio.setVisibility(View.GONE);
              //  Intent intent = new Intent(HomeSinRegistro.this, Video.class);
                //startActivity(intent);

            } else if (feed.getTipo_feed().equals(Contants.IMAGEN)) {
                holder.txtPrecio.setVisibility(View.GONE);
                holder.txtTipoFeed.setVisibility(View.GONE);

                //Intent intent = new Intent(HomeSinRegistro.this, Imagen.class);
                //startActivity(intent);
            } else if ( feed.getTipo_feed().equals(Contants.PDF)&&!feed.is_grati) {
              //  feed.is_grati=false;
                holder.txtPrecio.setText("GRATIS");
                //Intent intent = new Intent(HomeSinRegistro.this, DetallePdf.class);
                //startActivity(intent);

                if (feed.getTipo_feed().equals(Contants.PDF)&&feed.is_grati) {
                    holder.txtPrecio.setText("12");
                    //    feed.is_grati=true;

                    //  Intent intent1 = new Intent(HomeSinRegistro.this, PantallaPDF.class);
                    //intent1.putExtra("ViewType","internet");
                    //startActivity(intent1);

                }

                else {
                }
            }

        SimpleDateFormat minutos = new SimpleDateFormat("mm", Locale.getDefault());
        java.util.Date currenTimeZoneMin=new java.util.Date((long)1559177288*1000);
        String textMin = minutos.format(currenTimeZoneMin);
        int min=Integer.parseInt(textMin);


        SimpleDateFormat horas = new SimpleDateFormat("HH", Locale.getDefault());
        java.util.Date currenTimeZoneHoras=new java.util.Date((long)1559177288*1000);
        String textHor = horas.format(currenTimeZoneHoras);
        int hour=Integer.parseInt(textHor);



        if (hour ==0 && (min>=1 && min <=59)){

            holder.txtHora.setText("Hace "+textMin+" min");

        }
        else if (min <60 && (hour>=1 && hour <= 23)){
            holder.txtHora.setText("Hace "+textHor+" hr.");

        }
        else {
            holder.txtHora.setText("");

        }





        if (!feed.getImagen_feed().equals("nil")&&!feed.getImagen_admin().equals("nil")){
            Log.e(TAG, "Imagen admin: "+ feed.getImagen_admin());
            Log.e(TAG, "Imagen feed: "+ feed.getImagen_feed());


            try {
                URL urlfeed = new URL(feed.getImagen_feed());
                Picasso.get().load(String.valueOf(urlfeed))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imgFeed);

                URL urlAdmin = new URL(feed.getImagen_feed());
                Picasso.get().load(String.valueOf(urlAdmin))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imgAdmin);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }



        }




    }



    @Override
    public int getItemCount() {
        return feeds.size();
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
