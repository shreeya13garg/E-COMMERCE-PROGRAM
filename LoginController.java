/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendor;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author 96892
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static String Id;
     @FXML
    public Label new1;
    @FXML 
    public TextField name;
    @FXML
    public TextField email;
    @FXML
    public TextField pass;
    @FXML
    public ToggleGroup S=new ToggleGroup();
    @FXML
    public RadioButton s;
    @FXML
    public RadioButton c;
    @FXML
    public RadioButton d;
    @FXML
    public RadioButton b;
    @FXML
    public RadioButton f;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    @FXML
    public ImageView bg;
    @FXML
    public void Backgrounden(){
        bg.setEffect(new GaussianBlur(10));
    }
    @FXML
    public void Backgroundex(){
        bg.setEffect(new GaussianBlur(0));
    }
    
    @FXML
    public void login(MouseEvent event) throws IOException{ 
        try{
          Class.forName("com.mysql.cj.jdbc.Driver");/////////////////////
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendors","root","yashi255");////////////
          Statement stmt=conn.createStatement();
          System.out.println("Connection Established....");
          String n=name.getText();
          String e=email.getText();
          String p=pass.getText();
          stmt.executeQuery("use vendors;");
          String q="";
          if(s.isSelected()){
              q="Select * from seller where s_name='"+n+"' and s_password='"+p+"' and s_emailid='"+e+"'";
              System.out.println(q);
              ResultSet rs=stmt.executeQuery(q);
              if(rs.next()){
             
                  int s1=rs.getInt("s_id");
                  System.out.println(s1);
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("seller.fxml"));
            Parent root = loader.load();
             
            //Get controller of scene2
            SellerController sellerController = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            sellerController.sendID(s1);
                  
                  
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         // Parent root = FXMLLoader.load(getClass().getResource("seller.fxml"));
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
              }else{
                  JOptionPane.showMessageDialog(null,"INCORRECT LOGIN DETAILS. PLEASE TRY AGAIN.");
              }
          stmt.close();
                conn.close();
                
          }else if(c.isSelected()){
              
                q="Select * from customer where C_name='"+n+"' and C_password='"+p+"' and C_email='"+e+"'";
             
              ResultSet rs=stmt.executeQuery(q);
              if(rs.next()){
             
                  int s1=rs.getInt("C_id");
                  Id=rs.getString("C_id");;
                  
                  System.out.println(s1);
                  stmt.close();
                conn.close();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();


          FXMLLoader loader = new FXMLLoader(getClass().getResource("sample1.fxml"));
          Parent root = loader.load();
          Controller1 control = loader.getController();
          control.sendID(Id);
          Scene scene = new Scene(root);
          scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
          stage.setScene(scene);
          stage.show();
              }else{
                  JOptionPane.showMessageDialog(null,"INCORRECT LOGIN DETAILS. PLEASE TRY AGAIN.");
              }
          
              
          }else if(d.isSelected()){
                 q="Select * from delivery_guy where D_name='"+n+"' and D_password='"+p+"' and Email_id='"+e+"'";
             
              ResultSet rs=stmt.executeQuery(q);
              if(rs.next()){
             
                  int s1=rs.getInt("D_id");
                  Id=rs.getString("D_id");
                  System.out.println(s1);
                  stmt.close();
                conn.close();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
          Parent root = FXMLLoader.load(getClass().getResource("delivery_guy.fxml"));
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
              }else{
                  JOptionPane.showMessageDialog(null,"INCORRECT LOGIN DETAILS. PLEASE TRY AGAIN.");
              }
          
          }else if(f.isSelected()){
               q="Select * from organiser where Organising_group='"+n+"' and password='"+p+"' and emailid='"+e+"'";
             
              ResultSet rs=stmt.executeQuery(q);
              if(rs.next()){
             
                  int s1=rs.getInt("Organiser_id");
                  Id=rs.getString("Organiser_id");
                  System.out.println(s1);
                  stmt.close();
                conn.close();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
          Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
              }else{
                  JOptionPane.showMessageDialog(null,"INCORRECT LOGIN DETAILS. PLEASE TRY AGAIN.");
              }
          }else if(b.isSelected()){
                q="Select * from bulk_buyers where r_name='"+n+"' and r_password='"+p+"' and r_emailid='"+e+"'";
             
              ResultSet rs=stmt.executeQuery(q);
              if(rs.next()){
             
                  int s1=rs.getInt("r_id");
                  Id=rs.getString("r_id");
                  System.out.println(s1);
                  stmt.close();
                conn.close();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
          Parent root = FXMLLoader.load(getClass().getResource("BulkBuyersQueries.fxml"));
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
              }else{
                  JOptionPane.showMessageDialog(null,"INCORRECT LOGIN DETAILS. PLEASE TRY AGAIN.");
              }
          }
                
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }

        
//          Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//          Parent root = FXMLLoader.load(getClass().getResource("seller.fxml"));
//          Scene scene = new Scene(root);
//          stage.setScene(scene);
//          stage.show();
    }
    @FXML
    public void register(MouseEvent event) throws IOException{
        String loc="";
        if(s.isSelected())
        {
            loc="seller_register.fxml";
        }
        else if(c.isSelected())
        {
            loc="Customer_info.fxml";
        }
        else if(d.isSelected())
        {
            System.err.println("Not Valid");
            return;
        }
        else if(b.isSelected())
        {
         loc="BulkBuyersLogIn.fxml";
        }
        else if(f.isSelected())
        {
            loc="SignUp_page_Organiser.fxml";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(loc));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
    }
     @FXML
    public void fest(MouseEvent event) throws IOException{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Festseller.fxml"));
            Parent root = loader.load();
           // FestsellerController festsellerController = loader.getController();
          Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
          
    }
}
