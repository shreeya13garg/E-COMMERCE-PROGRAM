package vendor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View implements Initializable {
    public ListView list1;
    public ListView list2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Implementing rank function
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendors", "root", "yashi255");
            PreparedStatement ps = conn.prepareStatement("select p_name, rank() over (order by p_cost desc) as cost_rank from products order by cost_rank;");
            ResultSet rs = ps.executeQuery();
            int flag = 0;
            ArrayList<String> items = new ArrayList<String>(8);
            while (rs.next()) {
                String temp="Name:"+rs.getString(1)+" | Rank: "+rs.getString(2);
                list1.getItems().add(temp);
                flag = 1;
            }
            if(flag==0)
            {
                list1.getItems().add("There are no products available");
            }
            //Rank function with partitioning
            PreparedStatement ps2 = conn.prepareStatement("select p_name,p_cost,p_quantity,s_id,rank() over (PARTITION BY s_id) as R from products order by s_id,p_cost;");
            ResultSet rs2 = ps2.executeQuery();
            int flag2 = 0;
            while (rs2.next()) {
                String temp2="S_id: "+rs2.getString(4)+" | Name:"+rs2.getString(1)+" | Cost: "+rs2.getString(2)+" | Quantity: "+rs2.getString(3);
                list2.getItems().add(temp2);
                flag2 = 1;
            }
            if(flag2==0)
            {
                list2.getItems().add("There are no products available");
            }

        }catch(Exception e1){
            System.out.println(e1);
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample1.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene paymentscene = new Scene(root);
        paymentscene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(paymentscene);
        window.show();
    }
}
