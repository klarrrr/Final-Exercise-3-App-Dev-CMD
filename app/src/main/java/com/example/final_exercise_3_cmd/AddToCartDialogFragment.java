package com.example.final_exercise_3_cmd;

import static com.example.final_exercise_3_cmd.DecimalFormatter.PrettyFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

public class AddToCartDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_IMAGE = "image";
    private static final String ARG_NAME = "name";
    private static final String ARG_PRICE = "price";
    private static final String ARG_DESC = "desc";

    private int productImage;
    private String productName, productPrice, productDesc;

    public static AddToCartDialogFragment newInstance(int image, String name, String price, String desc) {
        AddToCartDialogFragment fragment = new AddToCartDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, image);
        args.putString(ARG_NAME, name);
        args.putString(ARG_PRICE, price);
        args.putString(ARG_DESC, desc);
        fragment.setArguments(args);
        return fragment;
    }

    public AddToCartDialogFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_cart_dialog, container, false);

        if (getArguments() != null) {
            productImage = getArguments().getInt(ARG_IMAGE);
            productName = getArguments().getString(ARG_NAME);
            productPrice = getArguments().getString(ARG_PRICE);
            productDesc = getArguments().getString(ARG_DESC);
        }

        ImageView productImageView = view.findViewById(R.id.productImage);
        TextView nameTextView = view.findViewById(R.id.productName);
        TextView priceTextView = view.findViewById(R.id.productPrice);
        EditText quantityEditText = view.findViewById(R.id.quantityEditText);
        Button buttonPlus = view.findViewById(R.id.buttonPlus);
        Button buttonMinus = view.findViewById(R.id.buttonMinus);
        Button buttonClose = view.findViewById(R.id.buttonClose);
        Button buttonAddToCart = view.findViewById(R.id.buttonAddToCart);

        productImageView.setImageResource(productImage);
        nameTextView.setText(productName);
        priceTextView.setText(String.format("₱%s", PrettyFormat(Double.parseDouble(productPrice))));

        // Plus Qty
        buttonPlus.setOnClickListener(v -> {
            int currentQty = getCurrentQuantity(quantityEditText);
            quantityEditText.setText(String.valueOf(currentQty + 1));
        });

        // Minus Qty
        buttonMinus.setOnClickListener(v -> {
            int currentQty = getCurrentQuantity(quantityEditText);
            if (currentQty > 1) {
                quantityEditText.setText(String.valueOf(currentQty - 1));
            }
        });

        // Just dismiss dialog if x button is clicked
        buttonClose.setOnClickListener(v -> dismiss());

        // add to cart button
        buttonAddToCart.setOnClickListener(v -> {
            String qtyText = quantityEditText.getText().toString().trim();
            if (qtyText.isEmpty() || Integer.parseInt(qtyText) <= 0) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "Invalid Quantity!", Snackbar.LENGTH_SHORT).show();
                dismiss();
                return;
            }

            int quantity = Integer.parseInt(qtyText);
            double price = Double.parseDouble(productPrice);
            double subtotal = quantity * price;

            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.addToCart(productName, productPrice, qtyText, String.valueOf(subtotal), productImage, productName, productDesc); // using name as pID
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "Added to cart!", Snackbar.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }

    // Helper function to get current Qty
    private int getCurrentQuantity(EditText quantityEditText) {
        String text = quantityEditText.getText().toString().trim();
        if (text.isEmpty()) return 1;
        int qty = Integer.parseInt(text);
        return Math.max(qty, 1);
    }
}
