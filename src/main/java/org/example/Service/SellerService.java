package org.example.Service;

import DAO.SellerDAO;
import org.example.Exception.ProductException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerService {
    SellerDAO sellerDAO;
    public SellerService(SellerDAO sellerDAO){
        this.sellerDAO = sellerDAO;
    }
    List<Seller> sellerList;

    //old code before adding the SellerDAO
   // public SellerService(){
       // sellerList = new ArrayList<>();
   // }

    public List<Seller> getAllSeller(){
        List<Seller> sellerList = sellerDAO.getAllSellers();
        return sellerList;
    }
    // old code before added the sellerDAO
   // public  List<Seller> getSellerList(){
     //   return sellerList;
   // }

    public Seller insertSeller (Seller seller) throws ProductException {
        //List<Seller> sellerList;
        Main.log.info("ADD:  Attempting to add a Seller:" + seller.sellerName);
        List<Seller> existingSeller = getAllSeller();
        for (int i = 0; i < existingSeller.size(); i++) {
           // seller = sellerList.get(i);
            if (seller.sellerName.equals(existingSeller.get(i).getSellerName())) {
                //System.out.println(""+ seller + sellerList.get(i));
                Main.log.warn("ADD:  Seller name already exists: " + seller.sellerName);
                throw new ProductException("Seller Name already exists");
            }

        }
       // do I need this line?  isn't the DAO adding it?
        //sellerList.add(seller);
        sellerDAO.insertSeller(seller);
        return seller;
    }
}
