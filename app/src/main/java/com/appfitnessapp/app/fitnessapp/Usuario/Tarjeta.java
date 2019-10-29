package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.R;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class Tarjeta extends AppCompatActivity {

    Stripe stripe;
    Integer amount;
    String name;
    Card card;
    Token tok;
    CardInputWidget mCardInputWidget;

    LinearLayout btnCancelar,btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_08_tarjeta);

        Bundle extras = getIntent().getExtras();
        // amount = extras.getInt("plan_price");
        // name = extras.getString("plan_name");

        stripe = new Stripe(this,"pk_test_yutzt6kpRO9nQLsSQGr2h9G100xG7Z0cSi");

       mCardInputWidget =findViewById(R.id.card_input_widget);


        btnCancelar=findViewById(R.id.btnCancelarTarjeta);
        btnComprar=findViewById(R.id.btnComprarTarjeta);



        card = mCardInputWidget.getCard();


        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    // onClickSomething("4242424242424242", "12", "2020", "123");
                    //cards();



            }



        });
    }

    public void onClickSomething(String cardNumber, String cardExpMonth, String cardExpYear, String cardCVC) {

    }

    public void cards(){

        Card cards = new Card(
                "4242424242424242",
                Integer.parseInt("12"),
                Integer.parseInt("2020"),
                "123"
        );

        cards.validateNumber();
        cards.validateCVC();

        card = mCardInputWidget.getCard();
        card.setCurrency("usd");
        card.setName("Theodhor Pandeli");
        card.setAddressZip("1000");


        stripe.createToken(card, "pk_test_yutzt6kpRO9nQLsSQGr2h9G100xG7Z0cSi", new TokenCallback() {
            public void onSuccess(Token token) {
                // TODO: Send Token information to your backend to initiate a charge
                Toast.makeText(getApplicationContext(), "Token created: " + token.getId(), Toast.LENGTH_LONG).show();
                tok = token;
                // new StripeCharge(token.getId()).execute();

            }

            public void onError(Exception error) {
                Log.d("Stripe", error.getLocalizedMessage());
            }
        });
    }


    /*
    public class StripeCharge extends AsyncTask<String, Void, String> {
        String token;

        public StripeCharge(String token) {
            this.token = token;
        }

        @Override
        protected String doInBackground(String... params) {
            new Thread() {
                @Override
                public void run() {
                    postData(name,token,""+amount);
                }
            }.start();
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result",s);
        }
    }


*/
    public void postData(String description, String token,String amount) {
        // Create a new HttpClient and Post Header
        try {
            URL url = new URL("https://api.stripe.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            //  conn.setDoInput(true);
            conn.setDoOutput(true);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new NameValuePair("method", "charge"));
            params.add(new NameValuePair("description", description));
            params.add(new NameValuePair("source", token));
            params.add(new NameValuePair("amount", amount));

            OutputStream os = null;

            os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //    writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        Log.e("Query",result.toString());
        return result.toString();
    }

    public class NameValuePair{
        String name,value;

        public NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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
