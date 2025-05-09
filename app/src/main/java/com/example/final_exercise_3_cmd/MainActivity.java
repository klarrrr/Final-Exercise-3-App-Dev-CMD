package com.example.final_exercise_3_cmd;
import static com.example.final_exercise_3_cmd.MainFragment.products;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<CartProductClass> cartProducts;
    ImageButton cartBtn;
    ConstraintLayout navPanel;
    TextView cmd6Btn;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        cartProducts = new ArrayList<>();
        navPanel = findViewById(R.id.NavPanel);
        cmd6Btn = navPanel.findViewById(R.id.cmd6Btn);
        cartBtn = navPanel.findViewById(R.id.cartBtn);

        //
        db = openOrCreateDatabase("cmd_shop", MainActivity.this.MODE_PRIVATE, null);

        db.execSQL(" CREATE TABLE IF NOT EXISTS sellers ( s_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, s_email TEXT NOT NULL, s_pass TEXT NOT NULL, s_fname TEXT NOT NULL, s_lname TEXT NOT NULL, date_created DATETIME DEFAULT CURRENT_TIMESTAMP ); ");

        db.execSQL(" CREATE TABLE IF NOT EXISTS buyers ( b_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,    b_email TEXT NOT NULL,b_pass TEXT NOT NULL, b_fname TEXT NOT NULL, b_lname TEXT NOT NULL,date_created DATETIME DEFAULT CURRENT_TIMESTAMP);");

        db.execSQL(" CREATE TABLE IF NOT EXISTS products (p_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, p_name TEXT NOT NULL, p_description TEXT NOT NULL, p_price REAL NOT NULL, p_image TEXT NOT NULL, p_s_id INTEGER NOT NULL, date_created DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(p_s_id) REFERENCES sellers(s_id));");

        db.execSQL(" CREATE TABLE IF NOT EXISTS carts (c_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, p_id INTEGER NOT NULL, b_id INTEGER NOT NULL, quantity INTEGER NOT NULL DEFAULT 1, date_created DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(p_id) REFERENCES products(p_id), FOREIGN KEY(b_id) REFERENCES buyers(b_id) ); ");

        cartBtn.setOnClickListener(v -> {
            CartFragment cartFragment = CartFragment.newInstance(cartProducts);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, cartFragment)
                    .addToBackStack(null)  // if want the user to be able to navigate back
                    .commit();
        });

        cmd6Btn.setOnClickListener(view -> getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new MainFragment())
                .addToBackStack(null)
                .commit());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, MainFragment.class, null)
                    .commit();
        }
    }

    public void addToCart(String name, String price, String quantity, String subtotal, Integer img, String pID, String cartDesc) {
        boolean flag = true;

        // Checks if product already exists in the array list using product id
        for(int i = 0; i < cartProducts.size(); i++){
            // if equals to the pid
            if(Objects.equals(cartProducts.get(i).getpID(), pID)){
                flag = false;
                // First parse to integer and double
                int newCartQuantity = Integer.parseInt(cartProducts.get(i).cartQuantity) + Integer.parseInt(quantity);
                double newSubTotal = Double.parseDouble(cartProducts.get(i).subTotal) + Double.parseDouble(subtotal);

                // Then modify its corresponding properties in the object
                cartProducts.get(i).cartQuantity = Integer.toString(newCartQuantity);
                cartProducts.get(i).subTotal = Double.toString(newSubTotal);
            }
        }
        // If it does not exist the flag will stay true
        // and will just proceed on add a new CartProductClass object
        if(flag)
            cartProducts.add(new CartProductClass(name, price, quantity, subtotal, img, pID, cartDesc));
    }

    @Override
    public void onItemClick(int position) {
        // Modify the Values of the product_details.xml based on the Position of the RecyclerView
        // get access of the product id

        int img = products.get(position).getImage();
        String dName = products.get(position).getProductName();
        String dPrice = products.get(position).getProductPrice();
        String dDesc = products.get(position).getProductDescription();
        String pID = products.get(position).getpID();

        DetailFragment df = new DetailFragment(img, dName, dPrice, dDesc, pID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, df, null)
                .setReorderingAllowed(true)
                .addToBackStack("detail")
                .commit();
    }
}
