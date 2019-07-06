package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EditarPerfil extends AppCompatActivity {

    CircularImageView imgPersona;
    TextView btnCambiarFoto,btnCambiarContra;
    TextInputEditText edtNombreUsuario,edtCorreo;
    EditText editContrasena;

    Spinner spinnerPeso,spinnerEstatura,spinnerBuscando;
    LinearLayout btnAceptar;

    public static final int REQUEST_CODE = 1234;
    private Uri imgUri;

    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;



    private static final String TAG = "BAJARINFO:";

    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    String id,email,password,name,imagen;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_22_editar_perfil);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Editar Perfil");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();

        Bundle extras = getIntent().getExtras();
        assert extras != null;
         imagen = extras.getString("imagen");
            email = extras.getString("nombre");
            password = extras.getString("correo");
            name = extras.getString("contrasena");




        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {

                   }
            }
        };
        mAuth = FirebaseAuth.getInstance();
     //   bajarUsuarios();


        imgPersona=findViewById(R.id.imgPersona);

        btnCambiarFoto=findViewById(R.id.txtCambiarFoto);
        btnCambiarContra=findViewById(R.id.txtCambiarContra);

        edtNombreUsuario=findViewById(R.id.edtNombreUsuario);
        editContrasena=findViewById(R.id.editContrasena);
        edtCorreo=findViewById(R.id.edtCorreo);


        spinnerPeso=findViewById(R.id.spinnerPeso);
        spinnerEstatura=findViewById(R.id.spinnerEstatura);
        spinnerBuscando=findViewById(R.id.spinnerBuscando);

        btnAceptar=findViewById(R.id.linearAceptar);


        edtNombreUsuario.setText(name);
        editContrasena.setText(email);
        editContrasena.setText(password);
        loadImageFromUrl(imagen);
//        imgPersona.setImageResource(Integer.parseInt(imagen));

        //name=edtNombreUsuario.getText().toString();
      //  email=edtCorreo.getText().toString();
      //  password=editContrasena.getText().toString();
       // imagen=imgPersona.getDrawable().toString();
       // loadImageFromUrl(imagen);

        ArrayAdapter<String> altura = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.altura);

        ArrayAdapter<String> peso = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.peso);

        ArrayAdapter<String> buscando = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.buscando);


        spinnerEstatura.setAdapter(altura);
        spinnerEstatura.setPrompt("¿Cual es tu altura?");
        spinnerPeso.setAdapter(peso);
        spinnerPeso.setPrompt("¿Cual es tu peso?");
        spinnerBuscando.setAdapter(buscando);
        spinnerBuscando.setPrompt("¿Que estas buscando?");

        spinnerEstatura.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerEstatura.getSelectedView()).setTextColor(Color.GRAY);
            }
        });

        spinnerPeso.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerPeso.getSelectedView()).setTextColor(Color.GRAY);
            }
        });
        spinnerBuscando.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerBuscando.getSelectedView()).setTextColor(Color.GRAY);
            }
        });


        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);

            }
        });

        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contra = editContrasena.getText().toString();
                if (contra.isEmpty()){
                    Toast.makeText(EditarPerfil.this, "Necesitas escribir una contraseña", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Actualizando contraseña");
                    progressDialog.show();
                    changePass(contra);

                }


            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(this.getApplicationContext().getContentResolver(), imgUri);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG,20,bytes);
                imgPersona.setImageBitmap(bm);

                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Guardando Imagen");
                dialog.show();
                final StorageReference ref = mStorage.child(Contants.TABLA_USUARIOS).child(edtNombreUsuario.getText().toString());

                ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //Dimiss dialog when success
                        //Display success toast msg
                        dialog.dismiss();
                        Toast.makeText(EditarPerfil.this,"Imagen guardada",Toast.LENGTH_SHORT).show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                dbProvider.updatePhoto(uri.toString(), id);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded " + (int) progress + "%");

                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void bajarUsuarios(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();


        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);


                        if (usuarios.getId_usuario().equals(id)){

                            edtNombreUsuario.setText(usuarios.getNombre_usuario());
                            edtCorreo.setText(usuarios.getEmail_usuario());
                            editContrasena.setText(usuarios.getContrasena_usuario());

                        }

                        if (usuarios.getFoto_usuario().equals("nil")) {
                            try {
                                URL urlfeed = new URL(usuarios.getFoto_usuario());
                                Picasso.get().load(String.valueOf(urlfeed))
                                        .error(R.mipmap.ic_launcher)
                                        .fit()
                                        .noFade()
                                        .into(imgPersona);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                        else {
                            loadImageFromUrl(usuarios.getFoto_usuario());
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
    }




    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imgPersona);
    }

    public void changeEmail(final String id, final String email) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditarPerfil.this, "Se ha cambiado el correo correctamente.", Toast.LENGTH_SHORT).show();
                    dbProvider.updateEmail(email, id);
                } else {
                    // Log.e(TAG, "Error : " + task.getResult());

                    Toast.makeText(EditarPerfil.this, "Lo siento, hubo un error al cambiar el correo", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }
        });
    }


    public void changePass(final String pass) {
        user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditarPerfil.this, "Se ha cambiado la contraseña correctamente.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditarPerfil.this, "Lo siento, hubo un error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

}
