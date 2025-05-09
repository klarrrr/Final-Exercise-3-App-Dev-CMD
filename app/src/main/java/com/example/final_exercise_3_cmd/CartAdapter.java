package com.example.final_exercise_3_cmd;

import static com.example.final_exercise_3_cmd.DecimalFormatter.PrettyFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private final CartRecyclerViewInterface cartRecyclerViewInterface;
    ArrayList<CartProductClass> cartProducts;
    Context myContext;
    OnCartChangeListener cartChangeListener;
    public CartAdapter(Context myContext, ArrayList<CartProductClass> cartProducts, OnCartChangeListener listener, CartRecyclerViewInterface cartRecyclerViewInterface) {
        this.cartRecyclerViewInterface = cartRecyclerViewInterface;
        this.myContext = myContext;
        this.cartProducts = cartProducts;
        this.cartChangeListener = listener;
    }
    public interface OnCartChangeListener {
        void onCartUpdated();
    }
    private void updateTotalAmount() {
        if (cartChangeListener != null) {
            cartChangeListener.onCartUpdated();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.cart_product_layout, parent, false);
        return new MyViewHolder(view, cartRecyclerViewInterface);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartProductClass cpc = cartProducts.get(position);

        // holder is the holder of view hahahah
        holder.tvCartName.setText(cpc.getCartName());
        holder.tvCartPrice.setText(String.format("₱%s", PrettyFormat(Double.parseDouble(cpc.getCartPrice()))));
        holder.tvCartQuantity.setText(String.format("Qty: %s", cpc.getCartQuantity()));
        holder.tvCartSubtotal.setText(String.format("₱%s", PrettyFormat(Double.parseDouble(cpc.getSubTotal()))));
        holder.tvCartImage.setImageResource(cpc.getCartImage());

        // Plus
        holder.btnPlus.setOnClickListener(v -> {
            int qty = Integer.parseInt(cpc.getCartQuantity());
            qty++;
            cpc.setCartQuantity(String.valueOf(qty));
            double subtotal = Double.parseDouble(cpc.getCartPrice()) * qty;
            cpc.setSubTotal(String.format("%.2f", subtotal));
            notifyItemChanged(position);
            updateTotalAmount();
        });

        // Minus
        holder.btnMinus.setOnClickListener(v -> {
            int qty = Integer.parseInt(cpc.getCartQuantity());
            if (qty > 1) {
                qty--;
                cpc.setCartQuantity(String.valueOf(qty));
                double subtotal = Double.parseDouble(cpc.getCartPrice()) * qty;
                cpc.setSubTotal(String.format("%.2f", subtotal));
                notifyItemChanged(position);
                updateTotalAmount();
            }
        });

        // Long press to remove item
        holder.itemView.setOnLongClickListener(v -> {
            new android.app.AlertDialog.Builder(myContext)
                    .setTitle("Remove Item")
                    .setMessage("Are you sure you want to remove this item from the cart?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        cartProducts.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartProducts.size());
                        updateTotalAmount();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });

        //Checkbox
        holder.checkBox.setChecked(cpc.isChecked());

        holder.checkBox.setOnCheckedChangeListener((checkBox, isChecked) -> {
            cpc.setChecked(isChecked); // <-- set checked status in data model
            updateTotalAmount();
        });


    }


    @Override
    public int getItemCount() {
        return cartProducts != null ? cartProducts.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCartName, tvCartPrice, tvCartQuantity, tvCartSubtotal, btnPlus, btnMinus;
        ImageView tvCartImage;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView, CartRecyclerViewInterface cartRecyclerViewInterface) {
            super(itemView);
            tvCartName = itemView.findViewById(R.id.TextCartName);
            tvCartPrice = itemView.findViewById(R.id.TextCartPrice);
            tvCartQuantity = itemView.findViewById(R.id.TextCartQuantity);
            tvCartSubtotal = itemView.findViewById(R.id.TextCartSubtotal);
            tvCartImage = itemView.findViewById(R.id.cartImageView);
            btnPlus = itemView.findViewById(R.id.cartAddQty);
            btnMinus = itemView.findViewById(R.id.cartMinusQty);
            checkBox = itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(
                    v -> {
                        if(cartRecyclerViewInterface != null){
                            int pos = getAdapterPosition();
                            if(pos != RecyclerView.NO_POSITION){
                                cartRecyclerViewInterface.onProductClick(pos);
                            }
                        }
                    }
            );
        }

    }


}
