package com.appfitnessapp.app.fitnessapp.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.EditarPerfil;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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
import java.util.Objects;

public class EditarPerfilAdmin extends AppCompatActivity {


    TextInputEditText edtNombreUsuario,editContrasena,edtCorreo,edtTelefono;
    String id,email,password,name,telefono,imagen;

    CircularImageView imgPersona;
    TextView btnCambiarFoto;
    LinearLayout btnAceptar;



    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    Boolean isFotoReady = false;
    String selectDay;

    public static final int REQUEST_CODE = 1234;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_08_perfil_editar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback=findViewById(R.id.toolbarU);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Editar Perfil");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();

        bajarUsuarios();


        imgPersona=findViewById(R.id.imgPersona);


        btnCambiarFoto=findViewById(R.id.txtCambiarFoto);


        edtNombreUsuario=findViewById(R.id.edtNombreUsuario);
        editContrasena=findViewById(R.id.edtContrasena);
        edtCorreo=findViewById(R.id.edtCorreo);
        edtTelefono=findViewById(R.id.edtTelefono);



        btnAceptar=findViewById(R.id.linearAceptar);
        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(EditarPerfilAdmin.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectImage();
                }
                else {
                    ActivityCompat.requestPermissions(EditarPerfilAdmin.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }

            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtNombre = Objects.requireNonNull(edtNombreUsuario.getText()).toString();
                String edtContra = Objects.requireNonNull(editContrasena.getText()).toString();
                String editCorreo = Objects.requireNonNull(edtCorreo.getText()).toString();
                String editTelefono = Objects.requireNonNull(edtTelefono.getText()).toString();


                if (!imagen.equals(imgPersona)&&imgUri!=null){
                    uploadImage(id,imgUri.toString());
                    Intent intent=new Intent(EditarPerfilAdmin.this, AdminPerfil.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }


                if (!name.equals(edtNombre)){
                    dbProvider.updateName(edtNombre, id);
                    Toast.makeText(EditarPerfilAdmin.this, "Se actualizo el nombre.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarPerfilAdmin.this, AdminPerfil.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                if (!email.equals(editCorreo)){
                    progressDialog.setMessage("Actualizando correo");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    changeEmail(id, editCorreo);
                }
                if (!password.equals(edtContra)){
                    progressDialog.setMessage("Actualizando contraseña");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    changePass(id, edtContra);
                }
                if (!telefono.equals(editTelefono)){
                    dbProvider.updatePhone(editTelefono, id);
                    Toast.makeText(EditarPerfilAdmin.this, "Se actualizo el teléfono.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarPerfilAdmin.this, AdminPerfil.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                if (name.equals(edtNombre)&&email.equals(editCorreo)&&password.equals(edtContra)&&telefono.equals(editTelefono)
                &&imgUri==null){

                    Intent intent=new Intent(EditarPerfilAdmin.this, AdminPerfil.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }



            }
        });


    }


    public void bajarUsuarios(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        if (usuarios.getId_usuario() != null){
                            if (usuarios.getId_usuario().equals(id)) {

                                imagen= usuarios.getFoto_usuario();
                                name = usuarios.getNombre_usuario();
                                email = usuarios.getEmail_usuario();
                                password = usuarios.getContrasena_usuario();
                                telefono = usuarios.getTelefono_usuario();


                                edtNombreUsuario.setText(usuarios.getNombre_usuario());
                                editContrasena.setText(usuarios.getContrasena_usuario());
                                edtCorreo.setText(usuarios.getEmail_usuario());
                                edtTelefono.setText(usuarios.getTelefono_usuario());


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
                                } else {
                                    loadImageFromUrl(usuarios.getFoto_usuario());
                                    progressDialog.dismiss();
                                }
                            }


                        }
                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void uploadImage( final String id,final String imagen) {


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 = mStorage.child(Contants.TABLA_USUARIOS).child(edtNombreUsuario.getText().toString());

        try {
            Bitmap bmp;
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask2 = storageReference1.putBytes(data);

            uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            dbProvider.updatePhoto(uri.toString(), id);
                            progressDialog.dismiss();
                            Toast.makeText(EditarPerfilAdmin.this, "Se actualizo la foto.", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditarPerfilAdmin.this, "Hubo un problema al subir la foto intenta de nuevo.", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    int currentProgess = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress(currentProgess);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==86&& resultCode ==RESULT_OK && data!= null){
            imgUri=data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imgPersona.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(EditarPerfilAdmin.this,"Selecciona un archivo", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectImage();
        }
        else {
            Toast.makeText(EditarPerfilAdmin.this, "Permite el acceso a la galería.", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void loadImageFromUrl(String url) {
        Picasso.get().load(url).into(imgPersona);
    }


    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }


    public void changeEmail(final String id, final String email) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditarPerfilAdmin.this, "Se ha cambiado el correo correctamente.Inicia sesión de nuevo.", Toast.LENGTH_LONG).show();
                    dbProvider.updateEmail(id,email);
                    mAuth.signOut();
                    Intent intent=new Intent(EditarPerfilAdmin.this, SplashPantalla.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(EditarPerfilAdmin.this, "Lo siento, hubo un error al cambiar el correo", Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();

            }
        });
    }
    public void changePass(final String id, final String pass) {

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditarPerfilAdmin.this, "Se ha cambiado la contraseña correctamente.Inicia sesión de nuevo.", Toast.LENGTH_LONG).show();
                    dbProvider.updatePass(id,pass);
                    mAuth.signOut();
                    Intent intent=new Intent(EditarPerfilAdmin.this, SplashPantalla.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(EditarPerfilAdmin.this, "Lo siento, hubo un error al cambiar la contraseña, revisa que la contraseña tenga 6 caracteres o más.", Toast.LENGTH_SHORT).show();

                }
                  progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(this, AdminPerfil.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }


}
