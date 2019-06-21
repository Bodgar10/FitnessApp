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
    URL imageURL = null;
    HttpURLConnection connection = null;
    static DBProvider dbProvider;
    private  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");


    Bitmap bm = null;
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

      /*
        holder.txtHora= (TextView) ServerValue.TIMESTAMP;
        long unixTime = feeds.get(position).timestamp;
        Date date =new Date(unixTime);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        String time = simpleDateFormat.format(date);
        holder.txtHora.setText(time);

*/

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


        /*
        SimpleDateFormat sfdMeses = new SimpleDateFormat("MM", Locale.getDefault());
        String mesesHoy = sfdMeses.format(Calendar.getInstance().getTime());
        int nowMeses = Integer.parseInt(mesesHoy);

*/
        //////////////////////////////////////////////////////////////////////////////////////7


        SimpleDateFormat minutos = new SimpleDateFormat("mm", Locale.getDefault());
        java.util.Date currenTimeZoneMin = new java.util.Date((long) Double.parseDouble(timest.trim())* 1000);
        String textMin = minutos.format(currenTimeZoneMin);
        int min = Integer.parseInt(textMin);


        SimpleDateFormat horas = new SimpleDateFormat("HH", Locale.getDefault());
        java.util.Date currenTimeZoneHoras = new java.util.Date((long)  Double.parseDouble(timest.trim()) * 1000);
        String textHor = horas.format(currenTimeZoneHoras);
        int hour = Integer.parseInt(textHor);


        SimpleDateFormat dias = new SimpleDateFormat("dd", Locale.getDefault());
        java.util.Date currenTimeZoneDias = new java.util.Date((long) Double.parseDouble(timest.trim())* 1000);
        String textDias = dias.format(currenTimeZoneDias);
        int days = Integer.parseInt(textDias);


        /*
        SimpleDateFormat meses = new SimpleDateFormat("MM", Locale.getDefault());
        java.util.Date currenTimeZoneMeses = new java.util.Date((long) Double.parseDouble(timest.trim()) * 1000);
        String textMeses = meses.format(currenTimeZoneMeses);
        int month = Integer.parseInt(textMeses);

*/

        int minutosTotal = nowMin - min;
        int horasTotal = nowHoras - hour;
        int diasTotal = nowDias - days;


        int Total = minutosTotal;

        int Tutu = horasTotal;

        int DiasBien = diasTotal;


        /*
        if (DiasBien>=7){
            if (DiasBien > 30) {

                holder.txtHora.setText("Hace" + (DiasBien / 30) + "meses.");
            } else if (DiasBien > 360) {
                holder.txtHora.setText("Hace" + (DiasBien / 360) + "año.");

            } else {

                holder.txtHora.setText("Hace" + (DiasBien / 7) + "semana.");

            }

        }

        else if (DiasBien<7){
            holder.txtHora.setText("Hace" + DiasBien + "dias.");

        }


        else if (Tutu>=1 && Tutu<24){
            holder.txtHora.setText("Hace" + Tutu + " horas.");

        }

        else if (Total>1 && Total<60){
            holder.txtHora.setText("Hace" + Total + " min.");

        }


        */

        if (Total>=1 && Total<60) {
            holder.txtHora.setText("Hace " + Total + "  min.");

            if (Tutu>=1 && Tutu<24){
                holder.txtHora.setText("Hace " + Tutu + "  horas.");

            }

        }

        else if (DiasBien>=7) {
            if (DiasBien > 30) {

                holder.txtHora.setText("Hace " + (DiasBien / 30) + " meses.");
            } else if (DiasBien > 360) {
                holder.txtHora.setText("Hace " + (DiasBien / 360) + " año.");

            } else {

                holder.txtHora.setText("Hace " + (DiasBien / 7) + " semanas.");

            }
        }
            else if (DiasBien<7){
                holder.txtHora.setText("Hace " + DiasBien + " dias.");

        }








        /*
        if (horasTotal ==0 && (minutosTotal>=1 && minutosTotal <=59)){

            holder.txtHora.setText("Hace"+Total+" min.");
        }



        else if (minutosTotal <60 && (horasTotal>=1 && horasTotal <= 23)){
            holder.txtHora.setText("Hace "+Tutu+" hr.");


        }


*/

        /*

        else if (hour>23&&days>=1){

            if (month==1||month==3||month==5||month==7||month==8||month==10||month==12){

                if (days>=1&&days<=31){

                    holder  .txtHora.setText("hace"+textDias+"dias.");

                }

            }

            else if (month==4||month==6||month==9||month==11){

                if (days>=1&&days<=30){

                    holder  .txtHora.setText("hace"+textDias+"dias.");

                }

            }

            else if (month==2){

                if (days>=1&&days<=28||days<=29){

                    holder  .txtHora.setText("hace"+textDias+"dias.");

                }

            }


        }

*/
        else {
            holder.txtHora.setText("");

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


    /*
    public void  hora(){
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = sfd.format(new Date(timestamp));


    }

*/


    public void Comprobar() {



    }
}
