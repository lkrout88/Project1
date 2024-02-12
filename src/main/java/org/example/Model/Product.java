package org.example.Model;

import java.util.Objects;

public class Product {
    public long productId;
    public String productName;
    public int productPrice;
    public String sellerName;


    public Product() {

    }

    public Product(long productId, String productName, int productPrice, String sellerName){
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellerName = sellerName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Objects.equals(productName, product.productName) && Objects.equals(sellerName, product.sellerName)
                && productPrice == product.productPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId,productName, productPrice, sellerName);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
