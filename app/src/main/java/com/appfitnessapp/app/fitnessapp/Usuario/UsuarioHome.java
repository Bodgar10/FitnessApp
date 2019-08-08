package com.appfitnessapp.app.fitnessapp.Usuario;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterFeed;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UsuarioHome  extends AppCompatActivity {

    ImageButton imgPlan,imgPerfil,imgChat,imgHome;
    private static final int PDF_CODE = 1000 ;


    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    AdapterFeed adapterFeed;
    ArrayList<Feed> feeds;

    RecyclerView recyclerView;

    private static FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed);

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();


        bajarFeed();

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user==null){

            Intent intent=new Intent(UsuarioHome.this, SplashPantalla.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

        imgPlan=findViewById(R.id.imgPlan);
        imgPerfil=findViewById(R.id.imgPerfil);
        imgChat=findViewById(R.id.imgChat);

        imgHome=findViewById(R.id.imgHome);

        imgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioHome.this, UsuarioPlan.class);
                startActivity(intent);

            }
        });

        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioHome.this, UsuarioPerfil.class);
                startActivity(intent);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioHome.this, UsuarioChat.class);
                startActivity(intent);

            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioHome.this, UsuarioChat.class);
                startActivity(intent);

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioHome.this, DetallePdf.class);
                startActivity(intent);

            }
        });



        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feeds=new ArrayList<>();
        adapterFeed=new AdapterFeed(feeds);
        recyclerView.setAdapter(adapterFeed);





        adapterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.VIDEO)){

                    Intent intent = new Intent(UsuarioHome.this, Video.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("video",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

               else if(feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.IMAGEN)){

                    Intent intent = new Intent(UsuarioHome.this, Imagen.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imagen",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                else if(feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.PDF)){

                    if (feeds.get(recyclerView.getChildAdapterPosition(view)).getIs_gratis()){
                        Intent intent = new Intent(UsuarioHome.this, PantallaPDF.class);
                        intent.putExtra("ViewType","internet");
                        Bundle bundle = new Bundle();
                        bundle.putString("pdf",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(UsuarioHome.this, DetallePdf.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("pdf",feeds.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                        bundle.putString("precio",feeds.get(recyclerView.getChildAdapterPosition(view)).getCosto_pdf());

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }

                }

                else {

                    Toast.makeText(UsuarioHome.this, "hola", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    public void bajarFeed(){

      //  feeds.removeAll(feeds);

        dbProvider = new DBProvider();
        dbProvider.tablaFeed().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feeds.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Feed feed = snapshot.getValue(Feed.class);

                        feeds.add(feed);
                        adapterFeed.notifyDataSetChanged();

                    }
                }
                else {
                    Log.e(TAG, "Feed: ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PDF_CODE && resultCode == RESULT_OK && data != null){

            Uri selecPdf=data.getData();
            Intent intent=new Intent(UsuarioHome.this,PantallaPDF.class);
            intent.putExtra("ViewType","storage");
            intent.putExtra("FileUri",selecPdf.toString());
            startActivity(intent);


        }

    }



    private String getDate(Long timeStamp) {

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timeStamp*1000);
        String date = DateFormat.format("dd-MM-yyyy hh:mm",cal).toString();

        return date;
    }
    private void Compare() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ezzeearnRef = rootRef.child(Contants.TABLA_FEED).child("timestamp");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long time = dataSnapshot.getValue(Long.class);
                    assert time != null;
                    Date oldDate = new Date(time);
                    GregorianCalendar oldCalendar = new GregorianCalendar();
                    oldCalendar.setTime(oldDate);
                    GregorianCalendar newCalendar = new GregorianCalendar();


                    Map<String, String> now = ServerValue.TIMESTAMP;
                    newCalendar.setTime((Date) now);

                    if (newCalendar.get(Calendar.DATE) != oldCalendar.get(Calendar.DATE) ||
                            newCalendar.get(Calendar.MONTH) != oldCalendar.get(Calendar.MONTH) ||
                            newCalendar.get(Calendar.YEAR) != oldCalendar.get(Calendar.YEAR)

                            ) {
                        // new day starts toast
                    }


                }else{
                    // toast
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ezzeearnRef.addValueEventListener(eventListener);

    }
    private void saveTime() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child(Contants.TABLA_FEED).child("timestamp").setValue(ServerValue.TIMESTAMP);
    }





}
