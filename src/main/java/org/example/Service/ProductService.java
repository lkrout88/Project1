package org.example.Service;

import DAO.ProductDAO;
import DAO.SellerDAO;
import org.example.Exception.ProductException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;

import java.util.List;



public class ProductService {
   /*
    Create a constructor that takes in (injects) the seller service into the product service and
    then the this assigns the sellerService to this instance of the Product Service

   */
   SellerDAO sellerDAO;
   ProductDAO productDAO;

   public ProductService(SellerDAO sellerDAO, ProductDAO productDAO){
       this.sellerDAO = sellerDAO;
       this.productDAO = productDAO;
   }

   /*
   old service before adding productDAO

    public ProductService(SellerService sellerService) {
        this.sellerService = sellerService;

        productList = new ArrayList<>();
    }
    */
    // dependency injector add seller service into this product service

    SellerService sellerService;
    //ProductController productController;
    List<Product> productList;


    public List<Product> getAllProducts() {
        List<Product> productList = productDAO.getAllProducts();
        //System.out.println(productList);
        return productList;
    }

    //Below method will return true- if the seller is already in the database-
    public boolean checkSellerNameExists(Product p)
            throws ProductException {
        if (p.getProductName() == null || p.getSellerName() == null || p.getProductPrice() == 0) {
            throw new ProductException("Product Name and Seller Name cannot be blank an Product Price must be > 0");
        }
        // sellerService = new SellerService();
        //List<Seller> sellerList = sellerService.getAllSeller();
        List<Seller> existingSeller = sellerDAO.getAllSellers();
        //System.out.println("seller list" + sellerList.size());
        for (int i = 0; i < existingSeller.size(); i++) {
            if (p.sellerName.equals(existingSeller.get(i).getSellerName())) {
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
        Main.log.info("ADD: Attempting to add a Product:");
        boolean sellerExists = checkSellerNameExists(p);
        //System.out.println(sellerExists);
        // 201 - resource created
        // List<Product> productList = new ArrayList<>();
        if (sellerExists) {

            long id = (long) (Math.random() * Long.MAX_VALUE);
            p.setProductId(id);
            productDAO.insertProduct(p);
            //System.out.println(p.sellerName);
            //System.out.println("" + productList.size());
            //if productService returns false then do the rest

        } else {
            Main.log.warn("ADD: Seller does not exist" + p.getSellerName());
            throw new ProductException("SellerName must exist in Seller database");
        }
        return p;

    }

    //method below returns the product details when a product id is entered by the client
    public Product getProductById(long id) {
        // long ids= Long.parseLong(String.valueOf((id)));
        List<Product> existingProducts = productDAO.getAllProducts();
        for (int i = 0; i < existingProducts.size(); i++) {
            Product currentProduct = existingProducts.get(i);
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
            productDAO.deleteProduct(productToDelete);
        }
        return productToDelete;

    }



    //Method will update the product values when the client does a put.  This method will call other methods
    //to check if
    public Product updateProduct(long id, Product updatedProduct) {
        boolean sellerExists;

        Product productToUpdate = getProductById(id);

        if (productToUpdate != null) {
            try {
                if (checkSellerNameExists(updatedProduct)) {
                    productToUpdate.setProductName(updatedProduct.getProductName());
                   productToUpdate.setProductPrice(updatedProduct.getProductPrice());
                    productToUpdate.setSellerName(updatedProduct.getSellerName());
                   // productToUpdate.setProductId(updatedProduct.getProductId());
                    //keep product ID the same
                   // productToUpdate.setProductId(id);
                    //productDAO.updateProduct(updatedProduct);
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

