package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarCorreo extends AppCompatActivity {

    Button btnCambiar;
    EditText edtCorreo;
    TextView txtCam;

    private static FirebaseAuth mAuth;
    private static final String TAG = "INGRESAR:";
    static DBProvider dbProvider;
    private String id;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_22_cambiarcorreo);


        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        dbProvider = new DBProvider();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        edtCorreo=findViewById(R.id.EmailEdtUCambiar);

        btnCambiar=findViewById(R.id.btnCambiarCorreoU);

        txtCam=findViewById(R.id.txtCam);



        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = edtCorreo.getText().toString();

                if (correo.isEmpty()){

                    Toast.makeText(CambiarCorreo.this, "Necesitas escribir un  corroe", Toast.LENGTH_SHORT).show();
                }else {

                    progressDialog.setMessage("Actualizando correo");
                    progressDialog.show();
                    changeEmail(id,correo);

                }
            }
        });

    }

    public void changeEmail(final String id, final String email) {

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //sendVerificationEmail();
                    Toast.makeText(CambiarCorreo.this, "Se ha cambiado el correo correctamente, revisa tu email y valídalo y vuelve a iniciar sesión.", Toast.LENGTH_SHORT).show();
                    dbProvider.updateEmail(email,id);
                  //  mAuth.signOut();
                   // Intent intent=new Intent(CambiarCorreo.this,IniciarSesion.class);
                   // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   // startActivity(intent);
                } else {
                    // Log.e(TAG, "Error : " + task.getResult());

                    Toast.makeText(CambiarCorreo.this, "Lo siento, hubo un error al cambiar el correo", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }
        });
    }


    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // after email is sent just logout the user and finish this activit
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }


}
