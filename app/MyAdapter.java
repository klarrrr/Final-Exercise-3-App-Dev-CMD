
 class MyAdapter{
    String productName;
    String productPrice;
    String productDescription;
    int image;


    public MyAdapter(String productName, String productPrice, String productDescription, int image) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.image = image;
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
 }
