package com.appfitnessapp.app.fitnessapp.Compra;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.List;

public class Compra extends AppCompatActivity implements PurchasesUpdatedListener {

    private BillingClient mBillingClient;

    Button btn_three_buy_health,btn_ten_buy_health,btn_twenty_buy_health,btn_fifty_buy_health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        btn_three_buy_health=findViewById(R.id.btn_three_buy_health);
        btn_ten_buy_health=findViewById(R.id.btn_ten_buy_health);
        btn_twenty_buy_health=findViewById(R.id.btn_twenty_buy_health);
        btn_fifty_buy_health=findViewById(R.id.btn_fifty_buy_health);


        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.
                    // BUTONLARI AKTIF ET
                    enableOrDisableButtons(true);

                } else {
                    Toast.makeText(Compra.this, "Ödeme sistemi için google play hesabını kontrol ediniz", Toast.LENGTH_SHORT).show();
                    enableOrDisableButtons(false);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                enableOrDisableButtons(false);
                Toast.makeText(Compra.this, "Ödeme sistemi şuanda geçerli değil", Toast.LENGTH_SHORT).show();
            }
        });

        btn_fifty_buy_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            buySubscription("50_buy_health");


            }
        });

        btn_ten_buy_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyProduct("10_buy_health");


            }
        });

        btn_three_buy_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyProduct("3_buy_health");

            }
        });

        btn_twenty_buy_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyProduct("20_buy_health");

            }
        });

    }

    private void enableOrDisableButtons(boolean isEnabled) {
        btn_three_buy_health.setEnabled(isEnabled);
        btn_ten_buy_health.setEnabled(isEnabled);
        btn_twenty_buy_health.setEnabled(isEnabled);
        btn_fifty_buy_health.setEnabled(isEnabled);
    }

    private void buyProduct(String skuId) {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSku(skuId)
                .setType(BillingClient.SkuType.INAPP)
                .build();
        mBillingClient.launchBillingFlow(this, flowParams);
    }

    private void buySubscription(String skuId) {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSku(skuId)
                .setType(BillingClient.SkuType.SUBS)
                .build();
        mBillingClient.launchBillingFlow(this, flowParams);
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            for (final Purchase purchase : purchases) {
                mBillingClient.consumeAsync(purchase.getPurchaseToken(), new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(int responseCode, String purchaseToken) {
                        if (responseCode == BillingClient.BillingResponse.OK) {
                            //sendTransactionData(purchase.getPurchaseToken(), purchase.getSku());
                        }
                    }
                });
            }
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user canceling the purchase flow.
            billingCanceled();

        } else {
            billingCanceled();
        }
    }

    private void billingCanceled() {

    }
}
