package vendor;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Payment implements Initializable{
    public TableView<ModelTable> product;
    public TableColumn<ModelTable,String> ID;
    public TableColumn<ModelTable,String> Name;
    public TableColumn<ModelTable,String> Cost;
    public TableColumn<ModelTable,String> Quantity;
    public TableColumn<ModelTable,String> S_id;
    public Label label;
    public Button btn1;
    ObservableList<ModelTable> obList = FXCollections.observableArrayList();
    int customer_id;
    String Cart_id;
    public void sendID(int cust_id,String cart_id)
    {
        customer_id=cust_id;
        Cart_id=cart_id;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }



    public void Next(ActionEvent actionEvent) throws IOException {

        Parent View_next = FXMLLoader.load(getClass().getResource("Final.fxml"));
        Scene finalscene = new Scene(View_next);
        finalscene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(finalscene);
        window.show();

    }

    public void previous(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("previous.fxml"));
        Parent root = loader.load();
        Previous previous = loader.getController();
        System.out.println("Prinitng Customer id");
        System.out.println(customer_id);
        previous.sendID(customer_id,Cart_id);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene previous_scene = new Scene(root);
        previous_scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(previous_scene);
        window.show();
    }

    public void Show(ActionEvent actionEvent) {
        Connection con = null;
        double sum=0;
        try {
            con = DBConnector.getConnection();
            con.createStatement().executeQuery("use vendors");
            PreparedStatement ps = con.prepareStatement("select * from cart where Cart_id=?");
            System.out.println("Success");
            System.out.println(Cart_id);
            ps.setString(1,Cart_id);
            ResultSet rs = ps.executeQuery();
            int flag=0;
            while(rs.next())
            {
                flag=1;
                double a=Double.parseDouble(rs.getString("p_cost"));
                double b=Double.parseDouble(rs.getString("p_quantity"));
                System.out.println(a);
                System.out.println(b);
                sum=sum+a*b;
                //sum=sum+Integer.valueOf(rs.getString("p_cost"))*Integer.valueOf(rs.getString("p_quantity"));
                obList.add(new ModelTable(rs.getString("P_id"),rs.getString("P_name"),rs.getString("p_cost"),rs.getString("p_quantity"),rs.getString("s_id")));

            }
            label.setText(String.valueOf(sum)+" Rupees");

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
        product.setItems(obList);

    }
}
