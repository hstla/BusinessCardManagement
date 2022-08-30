package org.edwith.webbe.cardmanager.dao;
import org.edwith.webbe.cardmanager.dto.BusinessCard;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
    private static String dburl="jdbc:mysql://localhost:3306/connectdb?useSSL=false";
    private static String dbUser="connectuser";
    private static String dbpasswd="connectuser123!@#";

    public BusinessCard addBusinessCard(BusinessCard businessCard){
        String sql="INSERT INTO BusinessCard(name,phone,companyName) VALUES(?,?,?)";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(businessCard);
        try(Connection conn= DriverManager.getConnection(dburl,dbUser,dbpasswd);
            PreparedStatement ps=conn.prepareStatement(sql))
        {
            ps.setString(1, businessCard.getName());
            ps.setString(2, businessCard.getPhone());
            ps.setString(3, businessCard.getCompanyName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return businessCard;
    }



    public List<BusinessCard> searchBusinessCard(String keyword){
        List<BusinessCard> list=new ArrayList<>();
        String sql="SELECT * FROM BusinessCard WHERE name  LIKE ?";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try(Connection conn= DriverManager.getConnection(dburl,dbUser,dbpasswd);
            PreparedStatement ps=conn.prepareStatement(sql)) {
            ps.setString(1,"%"+keyword+"%");

            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    String name=rs.getString("name");
                    String phone=rs.getString("phone");
                    String companyName=rs.getString("companyName");
                    BusinessCard card=new BusinessCard(name,phone,companyName);

                    list.add(card);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return list;
    }
}