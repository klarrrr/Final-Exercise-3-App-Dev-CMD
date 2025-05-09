package com.example.final_exercise_3_cmd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;

// The MainFrag Class essentially grabs the strings from strings-array in strings.xml
// It merges the information into one big object, stored in a class called MainProductClass

public class MainFragment extends Fragment {

    // ArrayList of MainProductClass object
    static ArrayList<MainProductClass> products = new ArrayList<>();
    RecyclerView recyclerView;
    ProductAdapter adapter;

    // This set of ArrayList will be used for string-array storage from strings.xml
    ArrayList<String> cartNames, cartPrices, cartQuantities, cartSubtotal;

    public MainFragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        if (getArguments() == null) {
            cartNames = new ArrayList<>();
            cartPrices = new ArrayList<>();
            cartQuantities = new ArrayList<>();
            cartSubtotal = new ArrayList<>();
        }else {
            cartNames = getArguments().getStringArrayList("cartNames");
            cartPrices = getArguments().getStringArrayList("cartPrices");
            cartQuantities = getArguments().getStringArrayList("cartQuantities");
            cartSubtotal = getArguments().getStringArrayList("cartSubtotals");
        }

        recyclerView = view.findViewById(R.id.myRecyclerView);

        // Each grid has their own height and width
        // To replicate the similar look of staggered cards in Shopee
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        setMyAdapter();
        return view;
    }

    private void setMyAdapter() {
        // The string-array from the strings.xml
        String[] productNames = getResources().getStringArray(R.array.products);
        String[] productPrices = getResources().getStringArray(R.array.prices);
        String[] imageNames = getResources().getStringArray(R.array.images);
        String[] productDesc = getResources().getStringArray(R.array.product_desc);
        String[] productIDs = getResources().getStringArray(R.array.product_id);

        products.clear();

        for (int i = 0; i < productNames.length; i++) {
            int imageResId = getResources().getIdentifier(imageNames[i], "drawable", requireContext().getPackageName());
            products.add(new MainProductClass(productNames[i], productPrices[i], productDesc[i], imageResId, productIDs[i]));
        }

        // the adapter binds the info we got to recycler view
        adapter = new ProductAdapter(requireContext(), products, (RecyclerViewInterface) getActivity());
        recyclerView.setAdapter(adapter);

    }

}
