package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerService {

    List<Seller> sellerList;

    public SellerService(){
        sellerList = new ArrayList<>();
    }

    public  List<Seller> getSellerList(){
        return sellerList;
    }

    public void insertSeller (Seller seller) throws ProductException {
        Main.log.info("ADD:  Attempting to add a Seller:" + seller.sellerName);
        for (int i = 0; i < sellerList.size(); i++) {
           // seller = sellerList.get(i);
            if (seller.sellerName.equals(sellerList.get(i).getSellerName())) {
                //System.out.println(""+ seller + sellerList.get(i));
                Main.log.warn("ADD:  Seller name already exists: " + seller.sellerName);
                throw new ProductException("Seller Name already exists");
            }

        }
       // System.out.println(seller);
        sellerList.add(seller);
    }
}
