package com.appfitnessapp.app.fitnessapp.menu_nuevo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.appfitnessapp.app.fitnessapp.Arrays.Inscritos;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.PantallaPDF;
import com.appfitnessapp.app.fitnessapp.Usuario.RutinaUsuario;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.MenuPerfilSinPago;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.MenuPerfilU;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.Menu_Asesorias_U;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.Menu_ConocenosU;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.Menu_EbooksU;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.Menu_Inicio_U;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.Menu_PdfsU;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.Menu_RecetariosU;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.Menu_RutinasU;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.androidquery.util.AQUtility.getContext;

public class Menu_Usuario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static FirebaseAuth mAuth;



    BajarInfo bajarInfo;
    DBProvider dbProvider;


    private ProgressDialog progressDialog;

    private static final String TAG = "Principal: ";

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__usuario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        bajarInfo = new BajarInfo();
        dbProvider = new DBProvider();



        mAuth = FirebaseAuth.getInstance();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportDefaultUsuario();

        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_imagen);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_imagen);
        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_imagen);
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_imagen);
        navigationView.getMenu().getItem(4).setActionView(R.layout.menu_imagen);
        navigationView.getMenu().getItem(5).setActionView(R.layout.menu_imagen);
        navigationView.getMenu().getItem(6).setActionView(R.layout.menu_imagen);
        navigationView.getMenu().getItem(7).setActionView(R.layout.menu_imagen);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu__usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        boolean fragmentTransaction = false;
        Fragment fragment = null;
        switch (item.getItemId()) {

            case R.id.nav_inicio:
                fragment= new Menu_Inicio_U();
                fragmentTransaction = true;

                break;


            case R.id.nav_conocenos:
                fragment= new Menu_ConocenosU();
                fragmentTransaction = true;

                break;

            case R.id.nav_asesorias:
                fragment= new Menu_Asesorias_U();
                fragmentTransaction = true;

                break;


            case R.id.nav_rutinas:

                fragment=new Menu_Asesorias_U();
                fragmentTransaction = true;

                break;

            case R.id.nav_pdfs:
                fragment=new Menu_PdfsU();
                fragmentTransaction = true;

                break;


            case R.id.nav_ebooks:
                fragment=new Menu_EbooksU();
                fragmentTransaction = true;

                break;

            case R.id.nav_recetarios:
                fragment=new Menu_RecetariosU();
                fragmentTransaction = true;

                break;

            case R.id.nav_perfil:

                fragment=new MenuPerfilSinPago();
                fragmentTransaction = true;

                break;

            case R.id.nav_cerrarsesion:
                mAuth.signOut();
                Intent intent=new Intent(Menu_Usuario.this, IniciarSesion.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                break;
        }

        if (fragmentTransaction){

            getSupportFragmentManager().beginTransaction().replace(R.id.content_usuario,fragment).commit();
            getSupportActionBar().setTitle(item.getTitle()); }



        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void getSupportDefaultUsuario(){
        NavigationView navigationView=findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_usuario,new Menu_Inicio_U()).commit();

        MenuItem item =navigationView.getMenu().getItem(0);
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());

    }


}
