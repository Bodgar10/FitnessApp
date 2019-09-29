package com.appfitnessapp.app.fitnessapp.Usuario.Paypal;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.DatosUsuario;
import com.appfitnessapp.app.fitnessapp.Usuario.Formulario;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentDetails extends AppCompatActivity {

    TextView txtId,txtAmount,txtStatus;
    DBProvider dbProvider;

    String id,idUsuario,meses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuario =extras.getString("id_usuario");
            meses =extras.getString("meses");


        }


        dbProvider = new DBProvider();


        txtId=findViewById(R.id.txtId);
        txtAmount=findViewById(R.id.txtAmount);
        txtStatus=findViewById(R.id.txtStatus);

        Intent intent =getIntent();


        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {


            SimpleDateFormat dateDia = new SimpleDateFormat("dd", Locale.getDefault());
            SimpleDateFormat dateMes = new SimpleDateFormat("MM", Locale.getDefault());
            SimpleDateFormat dateAnio = new SimpleDateFormat("yyyy", Locale.getDefault());

            Date date = new Date();

            int diaHoy = Integer.parseInt(dateDia.format(date));
            int mesHoy = Integer.parseInt(dateMes.format(date));
            int anioHoy = Integer.parseInt(dateAnio.format(date));

            String diaLim;
            String mesLim;
            String anioLim;

            if (meses.equals("12")){
                anioHoy +=1;
            }

            else if (meses.equals("3")){

                if(mesHoy==12){
                    mesHoy= 03;
                    anioHoy +=1;
                }
                else if (mesHoy==11){
                    mesHoy= 02;
                    anioHoy +=1;
                }

                else if (mesHoy==10){
                    mesHoy= 01;
                    anioHoy +=1;
                }
                else {
                    mesHoy +=3;

                }
            }

            else {
                if (mesHoy==012){
                    mesHoy = 06;
                    anioHoy +=1;
                }
                else if(mesHoy==11){
                    mesHoy = 05;
                    anioHoy +=1;
                }

                else if (mesHoy==10){
                    mesHoy = 04;
                    anioHoy +=1;
                }
                else if (mesHoy==9){
                    mesHoy = 3;
                    anioHoy +=1;
                }
                else if (mesHoy==8){
                    mesHoy = 2;
                    anioHoy +=1;
                }
                else if (mesHoy==7){
                    mesHoy = 1;
                    anioHoy +=1;
                }

                else {
                    mesHoy+=6;
                }
            }

            Toast.makeText(this, "La compra se realizo", Toast.LENGTH_SHORT).show();
            String key = dbProvider.tablaInscritos().push().getKey();
            dbProvider.subirIncritos(diaHoy+"-"+mesHoy+"-"+anioHoy,key,true,idUsuario);
            dbProvider.updateIsPagado(idUsuario,true);
            new CountDownTimer(2000,1){

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Intent intent =new Intent(PaymentDetails.this, Formulario.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();

                }
            }.start();


            //txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            //txtAmount .setText("$"+ paymentAmount);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
