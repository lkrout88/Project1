import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProductServiceTest {

    ProductService productService;
    SellerService sellerService;

    @Before
    public void setup(){
        //these are activites done before running any tests.  Instantiate all objects and initialize all variables needed for the test here
        sellerService = new SellerService();
        productService = new ProductService(sellerService);}



    @Test

    public void productServiceEmptyAtStart(){
        List<Product> productList = productService.getAllProducts();
        assertTrue(productList.size() == 0);
    }

    @Test
    public void sellerServiceEmptyAtStart(){
        List<Seller> sellerList = sellerService.getSellerList();
        assertTrue(sellerList.size()==0);
    }

    @Test
    public void testInsertProduct() throws ProductException{
        String testProductName = "soap";
        String testSellerName = "bbb";
        double  testProductPrice = 3.00;
        String testSellerName2 = "bbb";
        Product product = new Product();
        product.setProductName(testProductName);
        product.setSellerName(testSellerName);
        product.setProductPrice(testProductPrice);
        //Seller.seller
        Seller seller = new Seller();
        seller.setSellerName(testSellerName2);

       //try {
           sellerService.insertSeller(seller);
      // } catch (Exception e){

          // throw new ProductException("Seller Name already exists");
        //}

        //try{
            productService.insertProduct(product);
       // }catch (Exception e) {
            //Assert.fail("SellerName must exist in Seller database");
        //}
            assertTrue(productService.getAllProducts().contains(product));
        }

    @Test
    public void testSellerExistsException() throws ProductException{
        String testProductName = "soap";
        String testSellerName = "xxx";
        double  testProductPrice = 3.00;
        String testSellerName2 = "bbb";
        Product product = new Product();
        product.setProductName(testProductName);
        product.setSellerName(testSellerName);
        product.setProductPrice(testProductPrice);
        //Seller.seller
        Seller seller = new Seller();
        seller.setSellerName(testSellerName2);

        //try {
        sellerService.insertSeller(seller);
        // } catch (Exception e){

        // throw new ProductException("Seller Name already exists");
        //}

        try{
        productService.insertProduct(product);
            Assert.fail("SellerName must exist in Seller database");
         }catch (Exception e) {

        }
        //Assert.fail("SellerName must exist in Seller database");
    }

    @Test
    public void testDeleteProducts() throws Exception {
        List<Product> productList = productService.getAllProducts();
        String testProductName = "soap";
        String testSellerName = "bbb";
        double  testProductPrice = 3.00;
        String testSellerName2 = "bbb";
        Product product = new Product();
        //long testProductId = 12345;
        product.setProductName(testProductName);
        product.setSellerName(testSellerName);
        product.setProductPrice(testProductPrice);

        //Seller.seller
        Seller seller = new Seller();
        seller.setSellerName(testSellerName2);

        //try {
        sellerService.insertSeller(seller);
        // } catch (Exception e){

        // throw new ProductException("Seller Name already exists");
        //}

        //try{
        productService.insertProduct(product);
        long productId = product.productId;

        productService.deleteProduct(product.productId);

        productService.getAllProducts();




        // }catch (Exception e) {
        //Assert.fail("SellerName must exist in Seller database");
        //}
        assertTrue(productList.size()==0);
    }


}
