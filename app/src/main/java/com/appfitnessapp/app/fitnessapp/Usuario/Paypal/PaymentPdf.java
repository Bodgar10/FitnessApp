package com.appfitnessapp.app.fitnessapp.Usuario.Paypal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import org.json.JSONException;
import org.json.JSONObject;


public class PaymentPdf extends AppCompatActivity {

    TextView txtId,txtAmount,txtStatus;

    String idUsuario,meses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuario =extras.getString("id_usuario");
            meses =extras.getString("meses");


        }



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

            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount .setText("$"+ paymentAmount);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
