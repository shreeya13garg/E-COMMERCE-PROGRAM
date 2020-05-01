package vendor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Previous {

    public TableView<ModelTable> prev;
    public TableColumn<ModelTable,String> ID;
    public TableColumn<ModelTable,String> Name;
    public TableColumn<ModelTable,String> Cost;
    public TableColumn<ModelTable,String> Quantity;
    public TableColumn<ModelTable,String> S_id;
    ObservableList<ModelTable> obList = FXCollections.observableArrayList();

    int customer_id;
    String Cart_id;
    public void sendID(int cust_id,String C_id)
    {
        customer_id=cust_id;
        Cart_id=C_id;
    }
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
        Parent root = loader.load();
        Payment payment = loader.getController();
        System.out.println("Printing");
        System.out.println(Cart_id);
        payment.sendID(customer_id,Cart_id);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene paymentscene = new Scene(root);
        paymentscene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(paymentscene);
        window.show();
    }

    public void show(ActionEvent actionEvent) {
        Connection con = null;
        try {
            con = DBConnector.getConnection();
            con.createStatement().executeQuery("use vendors");
            PreparedStatement ps = con.prepareStatement("select * from cart where C_id=?");
            System.out.println("Previous orders customer id");
            System.out.println(customer_id);
            ps.setString(1, String.valueOf(customer_id));
            ResultSet rs = ps.executeQuery();
            int flag=0;
            while(rs.next())
            {
                flag=1;
                obList.add(new ModelTable(rs.getString("P_id"),rs.getString("P_name"),rs.getString("p_cost"),rs.getString("p_quantity"),rs.getString("s_id")));

            }
            if(flag==0)
            {
                System.out.println("There are no items in the cart");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        S_id.setCellValueFactory(new PropertyValueFactory<>("S_id"));
        prev.setItems(obList);

        /*
        try{
            System.out.println(customer_id);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendors","root","yashi255");
            PreparedStatement ps = conn.prepareStatement("select * from carts where C_id=?");
            ps.setString(1, String.valueOf(customer_id));
            ResultSet rs = ps.executeQuery();
            int flag=0;
            ArrayList<String> items = new ArrayList<String>(8);
            //To see previous orders of customer use the query select * from carts where C_id=? where ?=customer_id
            while(rs.next())
            {

                items.add(rs.getString(3));
                items.add(rs.getString(4));
                items.add(rs.getString(5));
                items.add(rs.getString(6));
                items.add(rs.getString(7));
                System.out.println(items);
                flag=1;
            }
            if(flag==0)
            {
                System.out.println("There are no items in the cart");

            }
        }catch(Exception e1){
            System.out.println(e1);
        }

         */
    }
}
