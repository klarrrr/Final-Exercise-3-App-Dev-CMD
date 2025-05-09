package com.example.final_exercise_3_cmd;

public class MainProductClass {
    String productName, productPrice, productDescription;
    int image;
    String pID;

    public MainProductClass(String productName, String productPrice, String productDescription, int image, String pID) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.image = image;
        this.pID = pID;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getImage() {
        return image;
    }

    public String getpID(){
        return pID;
    }
}
