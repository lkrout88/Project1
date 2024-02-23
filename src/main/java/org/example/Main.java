package org.example;
import DAO.ProductDAO;
import DAO.SellerDAO;
import Utility.ConnectionSingleton;
import io.javalin.Javalin;
import org.example.Controller.ProductController;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

//import static org.example.Controller.ProductController.productService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        //ProductService productService = new ProductService();
        //SellerService sellerService = new SellerService();
        Connection conn = ConnectionSingleton.getConnection();
        SellerDAO sellerDAO = new SellerDAO(conn);
        SellerService sellerService = new SellerService(sellerDAO);
        ProductService productService = null;
        ProductDAO productDAO = new ProductDAO(conn, productService);
        //SellerService sellerService = new SellerService(sellerDAO);
        productService = new ProductService(sellerDAO, productDAO);
        ProductController productController = new ProductController(productService, sellerService);

        Javalin api = productController.getAPI();
        api.start(9003);
    }
    }
