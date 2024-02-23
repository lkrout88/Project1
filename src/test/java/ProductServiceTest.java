import DAO.ProductDAO;
import DAO.SellerDAO;
import Utility.ConnectionSingleton;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProductServiceTest {

    ProductService productService;
    SellerService sellerService;

    ProductDAO productDAO;
    SellerDAO sellerDAO;
    Connection conn = ConnectionSingleton.getConnection();

    @Before
    public void setup() {
        //these are activities done before running any tests.  Instantiate all objects and initialize all variables needed for the test here
        sellerService = new SellerService(sellerDAO);
        productService = new ProductService(sellerDAO, productDAO);
        productDAO = new ProductDAO(conn, productService);
        sellerDAO = new SellerDAO(conn);

    }
    @Before
    public void dbReset(){
        ConnectionSingleton.resetTestDatabase();
    }

    @Test
    public void sellerServiceEmptyAtStart(){
        List<Seller> sellerList = sellerDAO.getAllSellers();
        System.out.println(sellerList);
        assertTrue(sellerList.size()==0);
   }

    @Test

    public void productServiceEmptyAtStart(){
        List<Product> productList = productDAO.getAllProducts();
        assertTrue(productList.size() == 0);
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
           sellerDAO.insertSeller(seller);
        System.out.println(seller);
      // } catch (Exception e){
          // throw new ProductException("Seller Name already exists");
        //}
        //try{
            productDAO.insertProduct(product);
       // }catch (Exception e) {
            //Assert.fail("SellerName must exist in Seller database");
        //}
            assertTrue(productDAO.getAllProducts().contains(product));
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
        sellerDAO.insertSeller(seller);
        // } catch (Exception e){

        // throw new ProductException("Seller Name already exists");
        //}

        try{
        productDAO.insertProduct(product);
            Assert.fail("SellerName must exist in Seller database");
         }catch (Exception e) {

        }
        //Assert.fail("SellerName must exist in Seller database");
    }

    @Test
    public void testDeleteProducts() throws Exception {
        List<Product> productList = productDAO.getAllProducts();
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
        sellerDAO.insertSeller(seller);
        // } catch (Exception e){

        // throw new ProductException("Seller Name already exists");
        //}

        //try{
        productDAO.insertProduct(product);
        long productId = product.productId;

        productDAO.deleteProduct(product);

        productDAO.getAllProducts();




        // }catch (Exception e) {
        //Assert.fail("SellerName must exist in Seller database");
        //}
        assertTrue(productList.size()==0);
    }

//@After
    //public void dbReset(){
       // ConnectionSingleton.resetTestDatabase();
//}
}
