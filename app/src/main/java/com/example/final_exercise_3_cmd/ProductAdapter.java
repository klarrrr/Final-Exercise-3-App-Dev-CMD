package com.example.final_exercise_3_cmd;

import static com.example.final_exercise_3_cmd.DecimalFormatter.PrettyFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<MainProductClass> products;

    // Constructor
    public ProductAdapter(Context context, ArrayList<MainProductClass> products, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.products = products;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    // View holders can hold list of views
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);
        // 'view' is the holder, recyclerViewInterface is the view being held
        // We do this because later we want to set the whole view area to navigate
        // through detail fragment using onClickListener
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        // The moment recycler view is bind to the view holder, set the following details
        MainProductClass product = products.get(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("₱%s", PrettyFormat(Double.parseDouble(product.getProductPrice()))));
        holder.imageView.setImageResource(product.getImage());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName, productPrice;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);

            // The entire is view is considered as group elements
            // So an onClickListener can be set in here
            itemView.setOnClickListener(
                    v -> {
                        if(recyclerViewInterface != null){
                            int pos = getAdapterPosition();
                            if(pos != RecyclerView.NO_POSITION){
                                recyclerViewInterface.onItemClick(pos);
                            }
                        }
                    }
            );
        }
    }
}
