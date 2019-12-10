package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro.AsesoriaRegistro;
import com.appfitnessapp.app.fitnessapp.Usuario.Paypal.Config;
import com.appfitnessapp.app.fitnessapp.Usuario.Paypal.PaymentDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class MetodoPago extends AppCompatActivity {


    RadioButton btnTarjeta,btnPaypal;
    TextView txtResumen,txtPlan;
    LinearLayout btnPagar;
    TextView txtTotal;

    DBProvider dbProvider;


    public static final int PAYPAL_REQUEST_CODE=7171;

    private static PayPalConfiguration config=new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(Config.PAYPAL_CLIENTE_ID);


    String amount="";

    String pago,meses,id;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_07_metodo_pago);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Pago");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtResumen=findViewById(R.id.txtResumen);
        txtPlan=findViewById(R.id.txtTipoPlan);
        txtTotal=findViewById(R.id.txtTotalPago);

        dbProvider = new DBProvider();

        btnPagar=findViewById(R.id.linearRealizarPago);

        btnPaypal=findViewById(R.id.btnPaypal);
        btnTarjeta=findViewById(R.id.btnTarjeta);


        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pago =extras.getString("costo");
            meses =extras.getString("meses");
        }






        String simbolo="$";

       // txtTotal.setText("$ "+pago);
        txtResumen.setText("$ "+pago);
        txtPlan.setText(meses+"meses");

        txtTotal.setText(simbolo+pago);


        Intent intent =new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);



        btnPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnPaypal.isChecked()){

                    btnTarjeta.setChecked(false);
                }

            }
        });

        btnTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnTarjeta.isChecked()){

                    btnPaypal.setChecked(false);

                }
            }
        });



        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (btnPaypal.isChecked()){
                    btnTarjeta.setChecked(false);

                    processPayment();

                }

                else if (btnTarjeta.isChecked()){

                    btnPaypal.setChecked(false);
                    Intent intent1 =new Intent(MetodoPago.this,Tarjeta.class);
                    startActivity(intent1);

                }


            }
        });


    }




    private void processPayment(){

        amount = pago;



        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"MXN",txtPlan.getText().toString(),
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this,PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){

            if (resultCode == RESULT_OK){

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation != null){

                    try {

                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,PaymentDetails.class)
                        .putExtra("PaymentDetails",paymentDetails)
                        .putExtra("PaymentAmount",amount)
                                .putExtra("id_usuario",id)
                                .putExtra("meses",meses));
                        finish();




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }




            }

            else if(resultCode == Activity.RESULT_CANCELED) {

                Toast.makeText(this, "Se cancelo la compra.", Toast.LENGTH_SHORT).show();
            }

        }

        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){

            Toast.makeText(this, "Invalido", Toast.LENGTH_SHORT).show();

        }
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
        finish();
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }

}
