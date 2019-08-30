package com.appfitnessapp.app.fitnessapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.SkuDetails;

import java.util.List;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ViewHolder> {


    Productos productos;

    List<SkuDetails> skuDetails;

    BillingClient billingClient;

    public AdapterProducto(Productos productos, List<SkuDetails> skuDetails, BillingClient billingClient) {
        this.productos = productos;
        this.skuDetails = skuDetails;
        this.billingClient = billingClient;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(productos.getBaseContext()).inflate(R.layout.producto,parent,false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtName.setText(skuDetails.get(position).getTitle());

        holder.setProductosClickListener(new ProductosClickListener() {
            @Override
            public void onProductsClick(View view, int position) {

                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails.get(position))
                        .build();

                 billingClient.launchBillingFlow(productos,billingFlowParams);
            }
        });

    }

    @Override
    public int getItemCount() {
        return skuDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtName;

        ProductosClickListener productosClickListener;

        public void setProductosClickListener(ProductosClickListener productosClickListener) {
            this.productosClickListener = productosClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName=itemView.findViewById(R.id.txtProductoName);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            productosClickListener.onProductsClick(v,getAdapterPosition());
        }
    }
}
