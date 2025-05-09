package com.example.final_exercise_3_cmd;

import static com.example.final_exercise_3_cmd.DecimalFormatter.PrettyFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailFragment extends Fragment {

    ImageView prodImg;
    TextView detailName, detailPrice, detailDesc;
    BottomNavigationView bottomNav;
    int img;
    String dName, dPrice, dDesc, pID;

    // Required empty constructor
    public DetailFragment() {}

    public DetailFragment(int img, String dName, String dPrice, String dDesc, String pID) {
        this.img = img;
        this.dName = dName;
        this.dPrice = dPrice;
        this.dDesc = dDesc;
        this.pID = pID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Extract arguments if needed
        if (getArguments() != null) {
            img = getArguments().getInt("img");
            dName = getArguments().getString("name");
            dPrice = getArguments().getString("price");
            dDesc = getArguments().getString("desc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragDetail = inflater.inflate(R.layout.fragment_detail, container, false);

        prodImg = fragDetail.findViewById(R.id.imageDetail);
        detailName = fragDetail.findViewById(R.id.detailName);
        detailPrice = fragDetail.findViewById(R.id.detailPrice);
        detailDesc = fragDetail.findViewById(R.id.detailDescription);

        prodImg.setImageResource(img);
        detailName.setText(dName);
        detailPrice.setText(String.format("₱%s", PrettyFormat(Double.parseDouble(dPrice))));
        detailDesc.setText(dDesc);
        return fragDetail;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Require activity throws exception, getActivity() does not
        bottomNav = requireActivity().findViewById(R.id.botNav);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.cart) {
                showDialog();
                return true;
            }
            return false;
        });
    }

    // Will show the slide dialog from bottom
    private void showDialog() {
        AddToCartDialogFragment dialog = AddToCartDialogFragment.newInstance(img, dName, dPrice, dDesc);
        dialog.show(getParentFragmentManager(), "AddToCartDialog");
    }

}
