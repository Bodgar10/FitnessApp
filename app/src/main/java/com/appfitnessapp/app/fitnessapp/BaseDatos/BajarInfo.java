package com.appfitnessapp.app.fitnessapp.BaseDatos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

public class BajarInfo {

    private static FirebaseAuth mAuth;
    static DBProvider dbProvider;
    String mensaje;
    //private StorageReference mStorage;

    //Usuarios usuarios;
    DateFormat format;

    private static final String TAG = "BAJARINFO:";



public void bajarFeed(){

    dbProvider = new DBProvider();
    dbProvider.tablaFeed().addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e(TAG, "Feed: " + dataSnapshot);
                    Feed feed = snapshot.getValue(Feed.class);



                    if (Contants.TIPO_USUARIO == Contants.ADMIN) {


                    }
                }


            }

            else {
                Log.e(TAG, "Usuarios 3: ");
            }



        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "ERROR: ");
        }
    });

}


}
