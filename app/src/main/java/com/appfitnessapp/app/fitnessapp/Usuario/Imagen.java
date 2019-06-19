package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Imagen extends AppCompatActivity {

    ImageView imgImagen;

    private static final String TAG = "BAJARINFO:";

    static DBProvider dbProvider;

    String tipo;

  //  private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
   // private DatabaseReference reference =firebaseDatabase.getReference();
   // private DatabaseReference childrefrence = reference.child("url_tipo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_imagen);

        imgImagen=findViewById(R.id.imgImagen);


        dbProvider = new DBProvider();

        //ids = FirebaseAuth.getInstance().getCurrentUser().getUid();
         //id=FirebaseStorage.getInstance().getReference().child("url_tipo");


        Bundle extras = getIntent().getExtras();
        assert extras != null;
        tipo =extras.getString("imagen");

      //  mStorage= FirebaseStorage.getInstance().getReference();


        bajarFeed();

        }


    public void bajarFeed(){

        dbProvider = new DBProvider();
        dbProvider.tablaFeed().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.e(TAG, "Feed: " + dataSnapshot);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Feed feed = snapshot.getValue(Feed.class);

                        if (feed.getTipo_feed() != null) {
                        if (feed.getTipo_feed().equals(tipo)) {

                            loadImageFromUrl(feed.getUrl_tipo());


                        }


                    }
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


    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imgImagen);
    }

    }
