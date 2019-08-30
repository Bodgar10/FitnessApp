package com.appfitnessapp.app.fitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.Arrays;
import java.util.List;


public class Productos extends AppCompatActivity implements PurchasesUpdatedListener {

    BillingClient billingClient;

    Button cargarProducto;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto);

        setBillingClient();

        cargarProducto=findViewById(R.id.btnCargar);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        cargarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (billingClient.isReady()){

                    SkuDetailsParams params=SkuDetailsParams.newBuilder()
                            .setSkusList(Arrays.asList("key","key2"))
                            .setType(BillingClient.SkuType.INAPP)
                            .build();

                    billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {

                            if (responseCode==BillingClient.BillingResponse.OK){

                                cargarProductoRecycler(skuDetailsList);

                            }
                            else {
                                Toast.makeText(Productos.this, "Cannont query", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {

                    Toast.makeText(Productos.this, "El billing no esta listo", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void cargarProductoRecycler(List<SkuDetails> skuDetailsList) {

        AdapterProducto adapterProducto=new AdapterProducto(this,skuDetailsList,billingClient);
        recyclerView.setAdapter(adapterProducto);
    }

    private void setBillingClient() {

        billingClient = BillingClient.newBuilder(this).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                if (responseCode ==BillingClient.BillingResponse.OK){
                    Toast.makeText(Productos.this, "Succes the connect", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Productos.this, ""+responseCode, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onBillingServiceDisconnected() {

                Toast.makeText(Productos.this, "You are disconnect from Billing", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

        Toast.makeText(this, "Purcharse item:"+purchases.size(), Toast.LENGTH_SHORT).show();

    }
}
