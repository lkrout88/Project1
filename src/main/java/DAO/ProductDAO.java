package DAO;

import org.example.Model.Product;
import org.example.Service.ProductService;
import org.example.Service.SellerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    Connection conn;


    SellerService sellerService;
    ProductService productService;

    public ProductDAO(Connection conn, ProductService productService) {
        this.conn = conn;
        //this.sellerService = sellerService;
        this.productService = productService;
    }

    public void insertProduct(Product p) {
        try {
            PreparedStatement ps = conn.prepareStatement("insert into PRODUCT" +
                    " (productId, productName, productPrice, sellerName) " +
                    "values (?, ?, ?, ?)");
            //long productId = p.getProductId();
            //int productIdInt = (int) productId;
            ps.setLong(1, p.getProductId());
            ps.setString(2, p.getProductName());
            ps.setDouble(3, p.getProductPrice());
            ps.setString(4, p.getSellerName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productResults = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from PRODUCT");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                int productPrice = rs.getInt("productPrice");
                String sellerName = rs.getString("sellerName");
                Product p = new Product(productId, productName, productPrice, sellerName);
                productResults.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productResults;
    }


    public void deleteProduct(Product productToDelete) {
        //need to convert the long id I'm getting from deleteProduct in product service to an int to use to
        //delete from the table
       // Product productToDelete = getProductById(productId);
        //Product currentProduct = null;

        try {
            PreparedStatement ps = conn.prepareStatement("delete from PRODUCT where productId = ?");
            ps.setLong(1, productToDelete.productId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateProduct(Product updatedProduct){
        try{
            PreparedStatement ps = conn.prepareStatement("UPDATE PRODUCT SET productName = ?, productPrice = ?, sellerName = ? WHERE productId = ?");

            ps.setString(1, updatedProduct.getProductName());
            ps.setDouble(2, updatedProduct.getProductPrice());
            ps.setString(3, updatedProduct.getSellerName());
            ps.setLong(4, updatedProduct.getProductId());
            System.out.println(updatedProduct.getProductName() + " " + updatedProduct.getProductPrice() + " " + updatedProduct.getSellerName() +" "+ updatedProduct.getProductId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }





}