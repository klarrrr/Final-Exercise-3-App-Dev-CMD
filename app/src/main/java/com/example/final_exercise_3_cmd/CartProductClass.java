package com.example.final_exercise_3_cmd;

public class CartProductClass {

    String cartName, cartPrice, cartQuantity, subTotal, cartDesc;
    int cartImage;
    String pID;

    // constructor for the cart product object
    public CartProductClass(String cartName, String cartPrice, String cartQuantity, String subTotal, int cartImage, String pID, String cartDesc){
        this.cartName = cartName;
        this.cartPrice = cartPrice;
        this.cartQuantity = cartQuantity;
        this.subTotal = subTotal;
        this.cartImage = cartImage;
        this.pID = pID;
        this.cartDesc = cartDesc;
    }

    public String getCartName(){
        return cartName;
    }

    public String getCartPrice(){
        return cartPrice;
    }

    public String getCartQuantity(){
        return cartQuantity;
    }

    public String getSubTotal(){
        return subTotal;
    }

    public int getCartImage(){
        return cartImage;
    }

    public String getpID(){
        return pID;
    }

    public String getProdDesc(){
        return cartDesc;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    // Check if checked
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


}
