package vendor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.*;
import java.io.IOException;
import java.sql.*;
import java.util.Random;

public class Controller1 {
    //I havent filled Date_placed in cart and Discount_app will always be 0 for customers
    public Label textLabel;
    public ListView list1;
    public ListView list2;
    public Label name;
    ArrayList<String> final_arr = new ArrayList<String>(8); //Elements of list1
    List<ArrayList<String>> list1_values = new ArrayList<ArrayList<String>>();
    ArrayList<String> cart_items = new ArrayList<String>(8); //Elements of list2
    List<ArrayList<String>> list2_values = new ArrayList<ArrayList<String>>();
    int cust_id;
    String cart_id = ""; // a unique cart id generated for each customer

    public void sendID(String C_id)
    {
        cust_id= Integer. parseInt(C_id);
    }

    public void button(ActionEvent actionEvent) throws SQLException {

        try{
            System.out.println(cust_id);
            Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendors","root","yashi255");
                conn.createStatement().executeQuery("use vendors");
            PreparedStatement ps = conn.prepareStatement("CALL SelectProducts();");
            ResultSet rs = ps.executeQuery();
            int flag=0;
            ArrayList<String> items = new ArrayList<String>(8);
            while(rs.next())
            {
                items.add(rs.getString(5));
                items.add(rs.getString(1));
                items.add(rs.getString(3));
                items.add(rs.getString(4));
                items.add(rs.getString(2));
                flag=1;
            }
            System.out.println(items);
            for(int i=0;i<items.size();i+=5)
            {

                String temp="Id:"+items.get(i)+" | Name:"+items.get(i+1)+" | Cost:"+items.get(i+2)+" | Quantity:"+items.get(i+3)+" | Seller_id:"+items.get(i+4);
                System.out.println("Id:"+items.get(i)+" Name:"+items.get(i+1)+" Cost:"+items.get(i+2)+"  Quantity:"+items.get(i+3)+"  Seller_id:"+items.get(i+4));
                list1.getItems().add(temp);
                ArrayList<String> values = new ArrayList<String>();
                values.add(items.get(i));
                values.add(items.get(i+1));
                values.add(items.get(i+2));
                values.add(items.get(i+3));
                values.add(items.get(i+4));
                list1_values.add(values);
                final_arr.add(temp);
            }

            if(!rs.next() && flag==0)
            {
                list1.getItems().add("There are no products available");

            }
        }catch(Exception e1){
            System.out.println(e1);
        }

        //textLabel.setText(textField.getText());

    }

    public void add(ActionEvent actionEvent) {
        ObservableList selectedIndices = list1.getSelectionModel().getSelectedIndices();
        System.out.println("Indexes of selected elements to be added in cart");
        for(Object o : selectedIndices){
            System.out.println("index=" + o);
            String count="";
            count=list1_values.get((Integer) o).get(3);
            if(Integer. parseInt(count)==1) {
                list2.getItems().add(final_arr.get((Integer) o));
                list1.getItems().remove(final_arr.get((Integer) o));
                cart_items.add(final_arr.get((Integer) o));
                final_arr.remove(final_arr.get((Integer) o));
                ArrayList<String> values = list1_values.get((Integer) o);
                list1_values.subList((Integer)o, (Integer)o+1).clear();
                ArrayList<String> values5 = new ArrayList<String>();
                values5.add(values.get(0));
                values5.add(values.get(1));
                values5.add(values.get(2));
                values5.add(values.get(3));
                values5.add(values.get(4));
                list2_values.add(values5);
                //Adding in list2_values
            }
            else if(Integer. parseInt(count)>1){
                int change=Integer.parseInt(count)-1;
                ArrayList<String> values = list1_values.get((Integer) o);
                String final_temp="Id:"+values.get(0)+" | Name:"+values.get(1)+" | Cost:"+values.get(2)+" | Quantity:"+Integer.toString(change)+" | Seller_id:"+values.get(4);
                String hello=final_arr.get((Integer) o);
                list1.getItems().remove(hello);
                list1.getItems().add(final_temp);
                final_arr.remove(hello);
                final_arr.add(final_temp);
                list1_values.subList((Integer)o, (Integer)o+1).clear();
                values.set(3,Integer.toString(change));
                list1_values.add(values);

                String final_add="Id:"+values.get(0)+" | Name:"+values.get(1)+" | Cost:"+values.get(2)+" | Quantity:"+"1"+" | Seller_id:"+values.get(4);
                cart_items.add(final_add);
                list2.getItems().add(final_add);
                ArrayList<String> values2 = new ArrayList<String>();
                values2.add(values.get(0));
                values2.add(values.get(1));
                values2.add(values.get(2));
                values2.add("1");
                values2.add(values.get(4));
                list2_values.add(values2);

            }
            System.out.println("Printing Data for 1st list view");
            System.out.println(list1_values);
            System.out.println(final_arr);
            System.out.println("Printing Data for 2st list view");
            System.out.println(list2_values);
            System.out.println(cart_items);
        }
    }

    public void delete(ActionEvent actionEvent) {
        ArrayList<Integer> indexes = new ArrayList<Integer>(8);
        ObservableList selectedIndices = list2.getSelectionModel().getSelectedIndices();
        for(Object o : selectedIndices){
            indexes.add((Integer) o);
            System.out.println((Integer)o);
            String item=cart_items.get((Integer) o);
            int dele=cart_items.indexOf(item);
            ArrayList<String> temp=list2_values.get(dele);
            list2_values.remove(dele);
            cart_items.remove(item);
            list2.getItems().remove(item);
            int ind=final_arr.indexOf(item);
            if(ind==-1)
            {
                list1.getItems().add(item);
                final_arr.add(item);
                ArrayList<String> values4 = new ArrayList<String>();
                values4.add(temp.get(0));
                values4.add(temp.get(1));
                values4.add(temp.get(2));
                values4.add(temp.get(3));
                values4.add(temp.get(4));
                list1_values.add(values4);

            }
            else
            {
                list1_values.get(ind).set(3,Integer.toString(Integer.valueOf(list1_values.get(ind).get(3))+1));
                ArrayList<String> dont=list1_values.get(ind);
                String hurray="Id:"+dont.get(0)+" | Name:"+dont.get(1)+" | Cost:"+dont.get(2)+" | Quantity:"+dont.get(3)+" | Seller_id:"+dont.get(4);
                list1.getItems().remove(item);
                list1.getItems().add(hurray);
                final_arr.remove(item);
                final_arr.add(hurray);
                list1_values.remove(ind);
                ArrayList<String> values3 = new ArrayList<String>();
                values3.add(dont.get(0));
                values3.add(dont.get(1));
                values3.add(dont.get(2));
                values3.add(dont.get(3));
                values3.add(dont.get(4));
                list1_values.add(values3);

            }
            System.out.println("Printing Data for 1st list view");
            System.out.println(list1_values);
            System.out.println(final_arr);
            System.out.println("Printing Data for 2st list view");
            System.out.println(list2_values);
            System.out.println(cart_items);
        }
    }

    public void proceed(ActionEvent actionEvent) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendors", "root", "yashi255");
            conn.createStatement().executeQuery("use vendors");
            PreparedStatement ps1 = conn.prepareStatement("CALL DeleteProducts();");
            int rs1 = ps1.executeUpdate();
            System.out.println(rs1);
            if (rs1 >= 0) {
                List<ArrayList<String>> list_cart = new ArrayList<ArrayList<String>>();
                for (int k = 0; k < list2_values.size(); k++) {
                    ArrayList<String> append1 = list2_values.get(k);
                    String product_id = append1.get(0);
                    int flag = 0;
                    for (int m = 0; m < list_cart.size(); m++) {
                        if (product_id.equals(list_cart.get(m).get(0))) {
                            list_cart.get(m).set(3, String.valueOf(Integer.parseInt(list_cart.get(m).get(3)) + 1));
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        list_cart.add(append1);
                    }
                }
                for (int i = 0; i < list1_values.size(); i++) {
                    Random rand = new Random();
                    ArrayList<String> append = list1_values.get(i);
                    PreparedStatement ps = conn.prepareStatement("insert into products(p_id,s_id,p_name,p_cost,p_quantity) values(?,?,?,?,?);");
                    ps.setString(1, append.get(0));
                    ps.setString(2, append.get(4));
                    ps.setString(3, append.get(1));
                    ps.setString(4, append.get(2));
                    ps.setString(5, append.get(3));
                    int x = ps.executeUpdate();
                    if (x > 0) {
                        System.out.println("Registration Successfully");
                    } else {
                        System.out.println("Registration failed");
                    }
                }
                PreparedStatement ps2 = conn.prepareStatement("CALL SelectCart_id();");
                ResultSet rs2 = ps2.executeQuery();
                ArrayList<Integer> Cart_ids = new ArrayList<Integer>(8);
                int flag = 0;
                while (rs2.next()) {
                    Cart_ids.add(Integer.valueOf(rs2.getString(1)));
                    flag = 1;
                }
                System.out.println(Cart_ids);
                if (flag == 0) {
                    cart_id = "1";
                    System.out.println(cart_id);
                } else if (flag == 1) {
                    cart_id = String.valueOf(Collections.max(Cart_ids) + 1);
                }
                for (int i = 0; i < list_cart.size(); i++) {
                    ArrayList<String> append = list_cart.get(i);
                    PreparedStatement ps = conn.prepareStatement("insert into cart(Cart_id,C_id,P_id,P_name,p_cost,p_quantity,s_id) values(?,?,?,?,?,?,?);");
                    System.out.println("Customer_id");
                    System.out.println( LoginController.Id);
                    ps.setString(1, cart_id);
                    ps.setString(2, LoginController.Id);
                    ps.setString(3, append.get(0));
                    ps.setString(4, append.get(1));
                    ps.setString(5, append.get(2));
                    ps.setString(6, append.get(3));
                    ps.setString(7, append.get(4));
                    int x = ps.executeUpdate();
                    if (x > 0) {
                        System.out.println("Registration Successfully");
                    } else {
                        System.out.println("Registration failed");
                    }
                }


            }
            System.out.println("Hello");
            Random rand=new Random();
            int i=rand.nextInt(15);
            i+=1;
            String Did=Integer.toString(i);
            String cid=LoginController.Id;
            String rid="-1";
            String Cartid=cart_id;                      //assign Cart_id here
            String bkid="-1";
            System.out.println(Did);
            System.out.println(cid);
            System.out.println(rid);
            System.out.println(Cartid);
            System.out.println(bkid);
            PreparedStatement ps2 = conn.prepareStatement("insert into vendors.deliveries(D_id,c_id,r_id,Cart_id,bk_id,Delivery_status,delivery_time) values(?,?,?,?,?,?,?);");
            ps2.setString(1, Did);
            ps2.setString(2, cid);
            ps2.setString(3, rid);
            ps2.setString(4, Cartid);
            ps2.setString(5, bkid);
            ps2.setString(6, "0");
            ps2.setString(7, "12:00");
            int y = ps2.executeUpdate();
        }catch(Exception e1){
            System.out.println(e1);
        }

        //Type your code here

            FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
            Parent root = loader.load();
            Payment payment = loader.getController();
            System.out.println("Printing");
            System.out.println(cart_id);
            payment.sendID(Integer.valueOf(LoginController.Id),cart_id);
            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene paymentscene = new Scene(root);
            paymentscene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
            window.setScene(paymentscene);
            window.show();


    }
    public void view(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(scene);
        window.show();

    }
}
