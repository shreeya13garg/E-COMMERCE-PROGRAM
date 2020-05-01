package vendor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CustomerInfo {
    public TextField name;
    public TextField add;
    public TextField pin;
    public TextField contact;
    public TextField pass;
    public TextField id;

    public void submit(ActionEvent actionEvent) {
        String a = "";
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendors","root","yashi255");
            conn.createStatement().executeQuery("use vendors");
            PreparedStatement ps1 = conn.prepareStatement("CALL SelectC_id();");
            ResultSet rs1 = ps1.executeQuery();
            ArrayList<Integer> C_ids = new ArrayList<Integer>(8);
            int flag=0;
            while(rs1.next())
            {
                C_ids.add(Integer.valueOf(rs1.getString(1)));
                flag=1;
            }
            System.out.println(C_ids);
            if(flag==0)
            {
                a="1";
                System.out.println(a);
            }
            else if(flag==1)
            {
                a=String.valueOf(Collections.max(C_ids)+1);
            }
            PreparedStatement ps = conn.prepareStatement("insert into customer(C_id,C_name,C_email,C_password,C_address,C_pin,C_contact) values(?,?,?,?,?,?,?);");
            ps.setString(1,a);
            ps.setString(2,name.getText());
            ps.setString(3,id.getText());
            ps.setString(4,pass.getText());
            ps.setString(5, add.getText());
            ps.setString(6, pin.getText());
            ps.setString(7, contact.getText());
            int x = ps.executeUpdate();
            if(x > 0) {
                System.out.println("Registration Successfully");
            }else
            {
                System.out.println("Registration failed");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();

        }catch(Exception e1){
            System.out.println("fdssdf"+e1);

        }

    }
}
