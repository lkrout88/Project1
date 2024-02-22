package DAO;

import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {
    Connection conn;

    public SellerDAO (Connection conn){
        this.conn = conn;
    }

    public List<Seller> getAllSellers(){
        List<Seller> sellerResults = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from SELLERS");
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                String sellerName = resultSet.getString("name");
                Seller s = new Seller(sellerName);
                sellerResults.add(s);
                //System.out.println(sellerResults);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return sellerResults;
    }
    public void insertSeller(Seller s){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into " +
                    "SELLERS (name) values (?)");
            ps.setString(1, s.getSellerName());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
