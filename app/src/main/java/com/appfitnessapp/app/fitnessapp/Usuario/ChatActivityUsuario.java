package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.FirebaseChatMainApp;
import com.appfitnessapp.app.fitnessapp.R;

public class ChatActivityUsuario extends AppCompatActivity {

    private Toolbar mToolbar;

    public static void startActivity(Context context,
                                     String name, String uid, String id_admin, String id_servicio) {
        Intent intent = new Intent(context, ChatActivityUsuario.class);
        intent.putExtra(Contants.ARG_RECEIVER, name);
        intent.putExtra(Contants.ARG_RECEIVER_UID, uid);
        intent.putExtra(Contants.ARG_FIREBASE_TOKEN, id_admin);
        intent.putExtra(Contants.ID_SERVICIO, id_servicio);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindViews();
        init();
    }

    private void init() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_chat,
                ChatFragmentUsuario.newInstance(getIntent().getExtras().getString(Contants.ARG_RECEIVER),
                        getIntent().getExtras().getString(Contants.ARG_RECEIVER_UID),
                        getIntent().getExtras().getString(Contants.ARG_FIREBASE_TOKEN),
                        getIntent().getExtras().getString(Contants.TOKEN),
                        getIntent().getExtras().getString(Contants.ID_SERVICIO)),
                ChatFragmentUsuario.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void bindViews()
    {
        //    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseChatMainApp.setChatActivityOpen(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseChatMainApp.setChatActivityOpen(false);
    }

}
