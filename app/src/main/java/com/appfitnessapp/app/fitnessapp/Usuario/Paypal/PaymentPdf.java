package com.appfitnessapp.app.fitnessapp.Usuario.Paypal;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.Formulario;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.Menu_Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PaymentPdf extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus;
    DBProvider dbProvider;

    String id, idUsuario, url, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuario = extras.getString("id_usuario");
            url = extras.getString("url");
            descripcion = extras.getString("descripcion");

        }


        Log.e("descripcion:", "descr" + descripcion);

        dbProvider = new DBProvider();


        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);

        Intent intent = getIntent();


        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {


            Toast.makeText(this, "La compra se realizo", Toast.LENGTH_SHORT).show();
            String key = dbProvider.tablaPdf().push().getKey();
            dbProvider.crearTablaPdf(key, idUsuario, url, descripcion);
            new CountDownTimer(2000, 1) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(PaymentPdf.this, Menu_Usuario.class);
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
