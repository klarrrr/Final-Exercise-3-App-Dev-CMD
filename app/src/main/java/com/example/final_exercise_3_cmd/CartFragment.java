package com.example.final_exercise_3_cmd;

import static com.example.final_exercise_3_cmd.DecimalFormatter.PrettyFormat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartFragment extends Fragment implements CartRecyclerViewInterface{

    RecyclerView rvCart;
    CartAdapter CartAdapter;
    Button checkOutBtn;
    TextView textTotal;
    CheckBox checkBoxSelectAll;
    ArrayList<CartProductClass> cartProducts;

    public CartFragment() {}

    public static CartFragment newInstance(ArrayList<CartProductClass> cartProducts) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putSerializable("cartProducts", cartProducts);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateTotalText() {
        double total = 0.0;
        for (CartProductClass item : cartProducts) {
            if (item.isChecked()) {
                total += Double.parseDouble(item.getSubTotal());
            }
        }
        textTotal.setText(String.format("₱%s", PrettyFormat(total)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        if (getArguments() == null) {
            cartProducts = new ArrayList<>();
        } else {
            cartProducts = (ArrayList<CartProductClass>) getArguments().getSerializable("cartProducts");
        }

        rvCart = root.findViewById(R.id.recyclerViewCart);
        rvCart.setLayoutManager(new LinearLayoutManager(root.getContext()));

        CartAdapter = new CartAdapter(root.getContext(), cartProducts, this::updateTotalText, this);

        // Gawa ng interface na item on click
        // Pag cinlick anywhere in the linear layout of product
        // mapapasa yung details sa detail fragment

        rvCart.setAdapter(CartAdapter);

        textTotal = root.findViewById(R.id.textTotal);
        checkOutBtn = root.findViewById(R.id.checkOutBtn);
        checkBoxSelectAll = root.findViewById(R.id.selectAll);

        updateTotalText(); // Calculate total only for checked

        checkBoxSelectAll.setOnClickListener(v -> {
            boolean checkAll = checkBoxSelectAll.isChecked();
            for (CartProductClass item : cartProducts) {
                item.setChecked(checkAll);
            }
            CartAdapter.notifyDataSetChanged();
            updateTotalText();
        });

        checkOutBtn.setOnClickListener(view -> {
            Dialog youSureDialog = new Dialog(root.getContext());
            youSureDialog.setContentView(R.layout.checkout_layout);

            Button cancelBtn = youSureDialog.findViewById(R.id.warnCancelBtn);
            Button yesBtn = youSureDialog.findViewById(R.id.warnYesBtn);
            TextView totalText = root.findViewById(R.id.textTotal);

            cancelBtn.setOnClickListener(v -> youSureDialog.dismiss());

            yesBtn.setOnClickListener(v -> {
                ArrayList<CartProductClass> toRemove = new ArrayList<>();
                double totalPaid = 0.0;

                for (CartProductClass item : cartProducts) {
                    if (item.isChecked()) {
                        totalPaid += Double.parseDouble(item.getSubTotal());
                        toRemove.add(item);
                    }
                }

                if (toRemove.isEmpty()) {
                    Toast.makeText(getContext(), "No items selected to checkout", Toast.LENGTH_SHORT).show();
                    youSureDialog.dismiss();
                    return;
                }

                cartProducts.removeAll(toRemove);
                CartAdapter.notifyDataSetChanged();
                updateTotalText();
                checkBoxSelectAll.setChecked(false);

                Toast.makeText(getContext(), "Success! You paid ₱" + PrettyFormat(totalPaid), Toast.LENGTH_LONG).show();
                youSureDialog.dismiss();
            });

            youSureDialog.show();
        });

        return root;
    }

    @Override
    public void onProductClick(int position) {
        // Modify the Values of the product_details.xml based on the Position of the RecyclerView
        // get access of the product id

        // Imbis na position
        // get Product in Product ArrayList via product ID

        int img = cartProducts.get(position).getCartImage();
        String dName = cartProducts.get(position).getCartName();
        String dPrice = cartProducts.get(position).getCartPrice();
        String dDesc = cartProducts.get(position).getProdDesc();
        String pID = cartProducts.get(position).getpID();

        DetailFragment df = new DetailFragment(img, dName, dPrice, dDesc, pID);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, df, null)
                .setReorderingAllowed(true)
                .addToBackStack("detail")
                .commit();
    }
}
