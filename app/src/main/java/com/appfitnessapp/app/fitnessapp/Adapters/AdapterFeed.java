package com.appfitnessapp.app.fitnessapp.Adapters;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.FeedViewHolder>
        implements View.OnClickListener{

    AdapterFeed adapterFeed;

    private View.OnClickListener listener;
    ArrayList<Feed> feeds;
    static DBProvider dbProvider;

    private static final String TAG = "BAJARINFO:";

    public static class FeedViewHolder extends RecyclerView.ViewHolder{


        ImageView imgAdmin,imgFeed;
        TextView txtNombreAdmin;
        TextView txtDescripcion;
        TextView txtPrecio;
        TextView txtHora;
        TextView txtTipoFeed;
        TextView urlTipo;
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
            isGratis=true;


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

        } else if (feed.getTipo_feed().equals(Contants.IMAGEN)) {
            holder.txtPrecio.setVisibility(View.GONE);
            holder.txtTipoFeed.setVisibility(View.GONE);
        } else if (feed.getTipo_feed().equals(Contants.PDF) && feed.is_gratis) {
            holder.txtPrecio.setText("GRATIS");
            if (feed.getTipo_feed().equals(Contants.PDF) && !feed.is_gratis) {
                holder.txtPrecio.setText("12");
            }
        }


        //---------------------------------------------------------------------------------------------------------
        String timest = feed.getTimestamp();


        /////////////////////////////////////////////////////////////////////////////////////////////////



        SimpleDateFormat sfdMin = new SimpleDateFormat("mm", Locale.getDefault());
        String minutosHoy = sfdMin.format(Calendar.getInstance().getTime());
        int nowMin = Integer.parseInt(minutosHoy);

        SimpleDateFormat sfdHoras = new SimpleDateFormat("HH", Locale.getDefault());
        String horasHoy = sfdHoras.format(Calendar.getInstance().getTime());
        int nowHoras = Integer.parseInt(horasHoy);

        SimpleDateFormat sfdDias = new SimpleDateFormat("dd", Locale.getDefault());
        String diasHoy = sfdDias.format(Calendar.getInstance().getTime());
        int nowDias = Integer.parseInt(diasHoy);



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////7



        SimpleDateFormat minutos = new SimpleDateFormat("mm",Locale.getDefault());
        java.util.Date currenTimeZoneMin = new java.util.Date((long) Double.parseDouble(timest.trim())* 1000);
        String textMin = minutos.format(currenTimeZoneMin);
        int min = Integer.parseInt(textMin);

        SimpleDateFormat horas = new SimpleDateFormat("HH",Locale.getDefault());
        java.util.Date currenTimeZoneHoras = new java.util.Date((long)  Double.parseDouble(timest.trim()) * 1000);
        String textHor = horas.format(currenTimeZoneHoras);
        int hour = Integer.parseInt(textHor);


        SimpleDateFormat dias = new SimpleDateFormat("dd", Locale.getDefault());
        java.util.Date currenTimeZoneDias = new java.util.Date((long) Double.parseDouble(timest.trim())* 1000);
        String textDias = dias.format(currenTimeZoneDias);
        int days = Integer.parseInt(textDias);


////////////////////////////////////////////////////////////////////////////////////////////


        int minutosTotal = nowMin - min;
        int horasTotal = nowHoras - hour;
        int diasTotal = nowDias - days;

        int minTotal = minutosTotal;

        int hourTotal = horasTotal;

        int daysTotal = diasTotal;


        if (daysTotal>=7) {
            if (daysTotal > 30) {

                holder.txtHora.setText("Hace " + (daysTotal / 30) + " meses.");
            } else if (daysTotal > 360) {
                holder.txtHora.setText("Hace " + (daysTotal / 360) + " año.");

            } else {

                holder.txtHora.setText("Hace " + (daysTotal / 7) + " semanas.");

            }
        }

        else if (daysTotal<7){
            if (daysTotal==1){
                holder.txtHora.setText("Ayer a las " +hour+ ":" + min +". ");

            }
            else
                holder.txtHora.setText("Hace " + daysTotal + " dias.");
            if (daysTotal==0) {
                if (hourTotal >= 1 && hourTotal < 24) {
                    holder.txtHora.setText("Hace " + hourTotal + "  hr.");

                }
                else if (hourTotal <1) {

                    if (minTotal <60) {
                        holder.txtHora.setText("Hace " + minTotal+ "  min.");

                    }

                }

            }

        }


//---------------------------------------------------------------------------------------------------------------


        if (!feed.getImagen_feed().equals("nil")){
           // Log.e(TAG, "Imagen admin: "+ feed.getImagen_admin());
            Log.e(TAG, "Imagen feed: "+ feed.getImagen_feed());


            try {
                URL urlfeed = new URL(feed.getImagen_feed());
                Picasso.get().load(String.valueOf(urlfeed))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imgFeed);

                /*
                URL urlAdmin = new URL(feed.getImagen_feed());
                Picasso.get().load(String.valueOf(urlAdmin))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(holder.imgAdmin);

                */
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



    private Long getCurrentTime(){

        Long timeStamp =System.currentTimeMillis()/1000;
        return timeStamp;

    }

    private String getDate(Long timeStamp) {

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timeStamp*1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm",cal).toString();

        return date;
    }

    private void saveTime(TextView fecha) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child(Contants.TABLA_FEED).child("timestamp").setValue(ServerValue.TIMESTAMP);
    }

}
