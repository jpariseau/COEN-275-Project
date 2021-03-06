package dbutil;

import dataabstractions.Labor;
import dataabstractions.Order;
import dataabstractions.Part;
import dataabstractions.ShopData;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author David Obatake
 */
public class OrderDAO {

    private static ArrayList<ShopData> orderData;
    private static Connection conn;

    public static void setConnection(Connection conn) {
        OrderDAO.conn = conn;
    }

    public static void setFromDB() {
        String query = "SELECT * FROM orders";
        orderData = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Order o = new Order(rs.getInt("order_id"));
                PartDAO.setFromDB(o.getTag());
                int numItems = PartDAO.getData().size();
                for (int i = 0; i < numItems; i++)
                    o.addItem(PartDAO.getData().get(i));
                o.setCustomer(CustomerDAO.getSingleData(rs.getInt("customer_id")));
                orderData.add(o);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        System.out.println("Test");
        System.out.println(orderData);
    }

    public static Order getSingleData(int ID) {
        for (ShopData i : orderData) {
            Order order = (Order) i;
            if (order.getTag() == ID) {
                return order;
            }
        }
        return null;
    }

    public static ArrayList<ShopData> getData() {
        return orderData;
    }

    public static void insertData(Order o) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String query = "INSERT INTO orders (customer_id) " +
                "VALUES (?);";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, o.getCustomer().getID());
            //ps.setString(3, df.parse(new Date().toString()).toString());
            ps.executeUpdate();
            Order orderTag = (Order) orderData.get(orderData.size() - 1);
            PartDAO.insertData(o.getItems(), orderTag.getTag() + 1);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
