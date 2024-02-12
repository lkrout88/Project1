package org.example.Service;

import org.example.Controller.ProductController;
import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class ProductService {
    public ProductService(SellerService sellerService) {
        this.sellerService = sellerService;

        productList = new ArrayList<>();
    }

    // dependency injector add seller service into this product service

    SellerService sellerService;
    //ProductController productController;
    List<Product> productList;


    public List<Product> getAllProducts() {
        return productList;
    }

    //Below method will return true- if the seller is already in the database-
    public boolean checkSellerNameExists(Product p)
            throws ProductException {
        if (p.getProductName() == null || p.getSellerName() == null || p.getProductPrice() == 0) {
            throw new ProductException("Product Name and Seller Name cannot be blank an Product Price must be > 0");
        }
        // sellerService = new SellerService();
        List<Seller> sellerList = sellerService.getSellerList();
        System.out.println("seller list" + sellerList.size());
        for (int i = 0; i < sellerList.size(); i++) {
            if (p.sellerName.equals(sellerList.get(i).getSellerName())) {
                /*long id = (long) (Math.random() * Long.MAX_VALUE);
                p.setProductId(id);
                productList.add(p);
             */
                return true;
            }
        }
        return false;

    }

    //this method will check the value (true or false returned from the checkSellerNameExists method before adding the product
    public Product insertProduct(Product p) throws ProductException {
        boolean sellerExists = checkSellerNameExists(p);
        // 201 - resource created
        // List<Product> productList = new ArrayList<>();
        if (sellerExists) {

            long id = (long) (Math.random() * Long.MAX_VALUE);
            p.setProductId(id);
            productList.add(p);
            System.out.println("" + productList.size());
            //if productService returns false then do the rest

        } else {
            throw new ProductException("SellerName must exist in Seller database");
        }
        return p;
    }

    //method below returns the product details when a product id is entered by the client
    public Product getProductById(long id) {
        // long ids= Long.parseLong(String.valueOf((id)));
        for (int i = 0; i < productList.size(); i++) {
            Product currentProduct = productList.get(i);
            if (currentProduct.getProductId() == id) {

                //System.out.println("current product" + currentProduct);
                return currentProduct;

            }
        }
        return null;
    }

    public Product deleteProduct(long productId) {

        Product productToDelete = getProductById(productId);

        if (productToDelete != null) {
            productList.remove(productToDelete);
        }
        return productToDelete;

    }

    //Method will update the product values when the client does a put
    public Product updateProduct(long id, Product updatedProduct) {
        boolean sellerExists;

        Product productToUpdate = getProductById(id);

        if (productToUpdate != null) {
            try {
                if (checkSellerNameExists(updatedProduct)) {
                    productToUpdate.setProductName(updatedProduct.getProductName());
                    productToUpdate.setProductPrice(updatedProduct.getProductPrice());
                    productToUpdate.setSellerName(updatedProduct.getSellerName());
                    //keep product ID the same
                   // productToUpdate.setProductId(id);
                } else {

                    return null;
                }
            } catch (Exception e) {
                return null;
            }
            return productToUpdate;
        }
        return null;

    }

    }











   /* below method is not being used because it was not a good way to determine if the seller exists before added the product.
   keep this just in case I need it.
    public Product insertProduct(Product p) throws ProductException {


        if (p.getProductName() == null || p.getSellerName() == null || p.getProductPrice() == 0) {
            throw new ProductException("Product Name and Seller Name cannot be blank an Product Price must be > 0");
        }
        //sellerService = new SellerService();
        List<Seller> sellerList = sellerService.getSellerList();
        System.out.println("seller list "+ sellerList.size());
                     for (int i = 0; i < sellerList.size(); i++)
                   if (p.sellerName.equals(sellerList.get(i).getSellerName())) {
                    long id = (long) (Math.random() * Long.MAX_VALUE);
                     p.setProductId(id);
                     productList.add(p);
               // return p;
            } return p;
        //else throw new ProductException("Seller Name must exist in Seller database");
    }
*/

