package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;

import java.util.ArrayList;
import java.util.List;

public class ProductController {

    static ProductService productService;
    static SellerService sellerService;

    public ProductController(ProductService productService, SellerService sellerService) {
        this.productService = productService;
        this.sellerService = sellerService;


    }

    List<Product> productList = new ArrayList<>();

    public Javalin getAPI() {
        Javalin api = Javalin.create();

        api.get("/health/",
                context ->
                {
                    context.result("the server is UP");
                }
        );
        //api.get("/product/", context ->
        // {
        //    this.getAllProductHandler(context);
        // });

        api.get("/product/", ProductController::getAllProductHandler);
        api.post("/product/", ProductController::postProductHandler);
        api.get("/seller/", ProductController::getAllSellersHandler);
        api.post("/seller/", ProductController::postSellerHandler);
        api.get("/product/{productId}", ProductController::getProductByIdHandler);
        api.delete("/product/{productId}", ProductController::deleteProductByIdHandler);
        api.put("/product/{productId}", ProductController::updateProductByIdHandler);
        return api;

    }

    public static void getAllProductHandler(Context context) {
        List<Product> productList = productService.getAllProducts();
        context.json(productList);

    }

    public static void postProductHandler(Context context) {
        //ObjectMapper om = new ObjectMapper();

        // try {
        // Product p = om.readValue(context.body(), Product.class);
        // productService.insertProduct(p);
        // context.status(201);
        try {
            ObjectMapper om = new ObjectMapper();
            Product p = om.readValue(context.body(), Product.class);
            Product newProduct = productService.insertProduct(p);
            context.status(201);
            context.json(newProduct);
        } catch (JsonProcessingException | ProductException e) {
            context.result(e.getMessage());
            context.status(400);
        }
        //context.json(p);

//                context.json(p);

        // } catch (JsonProcessingException e) {
        //    Jackson was unable to parse the JSON, probably due to user error, so 400
        //   context.status(400);
        // } catch (ProductException e) {
        // context.result(e.getMessage());
        //  context.status(400);
        // }

    }

    public static void getAllSellersHandler(Context context) {

        List<Seller> sellerList = sellerService.getAllSeller();
        context.json(sellerList);
    }

    public static void postSellerHandler(Context context) {
        ObjectMapper om = new ObjectMapper();
        try {
            Seller s = om.readValue(context.body(), Seller.class);
            sellerService.insertSeller(s);
            // 201 - resource created
            context.status(201);
        } catch (JsonProcessingException f) {
            //    Jackson was unable to parse the JSON, probably due to user error, so 400
            context.status(400);

        /*}catch (JsonProcessingException f){
            context.result(f.getMessage());
        /*    context.status(400);

         */
        } catch (ProductException e) {
            context.result(e.getMessage());
        }
    }

    public static void getProductByIdHandler(Context context) {
        long id = Long.parseLong(context.pathParam("productId"));
        //long ids= Long.parseLong(String.valueOf((id)));

        //ObjectMapper om = new ObjectMapper();
        // try {
        //Product g = om.readValue(context.body(), Product.class);
        Product product = productService.getProductById(id);
        if (product == null) {
            context.status(404);
            context.result("Product not found");
        } else {
            context.json(product);
            context.status(200);
        }
    }

    public static void deleteProductByIdHandler(Context context) {
        long id = Long.parseLong(context.pathParam("productId"));
        //long ids= Long.parseLong(String.valueOf((id)));

        //ObjectMapper om = new ObjectMapper();
        // try {
        //Product g = om.readValue(context.body(), Product.class);
        Product productToDelete = productService.deleteProduct(id);
        if (productToDelete == null) {
            context.status(404);
            context.result("Product not found");
        } else {
            context.json(productToDelete);
            context.status(200);
        }
    }

    public static void updateProductByIdHandler(Context context) {
        //System.out.println(context.pathParam("productId"));
        long id = Long.parseLong(context.pathParam("productId"));

        try {
            ObjectMapper om = new ObjectMapper();
            Product productToUpdate = om.readValue(context.body(), Product.class);
            //long id = Long.parseLong(context.pathParam("productId"));
            Product updatedProduct = productService.updateProduct(id, productToUpdate);
            if (updatedProduct == null) {
                context.status(404);
                context.result("Product not found");
            } else {
                context.json(productToUpdate);
                context.status(200);
            }


        } catch (Exception e) {
            context.status(500);
            context.result("Error updating product");
        }

    }
}