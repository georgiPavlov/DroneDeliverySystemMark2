package DroneDataBacePakage;

import WarehousePakage.Product;
import WarehousePakage.Warehouse;

import java.sql.*;

/**
 * Created by georgipavlov on 29.02.16.
 */
public class MakeOperations {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/new_schema";
    static final String USER = "root";
    static final String PASS = "azsamgeorgi1321";

    private Warehouse warehouse;
    private Connection conn = null;
    private Statement stmt = null;

    public MakeOperations(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    private void connectionToDataBase() throws ClassNotFoundException, SQLException {
        //STEP 2: Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");
        //STEP 3: Open a connection
        System.out.println("Connecting to a selected database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected database successfully...");
    }

    private void readFromDB_Supply(Statement stmt) throws SQLException {
        String sql = "SELECT TableProducts.quantity,PRODUCT.*\n" +
                     "from TableProducts JOIN PRODUCT\n" +
                     "ON TableProducts.product_id = PRODUCT.id\n" +
                     "where TableProducts.supply = 1;";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id  = rs.getInt("id");
            int quantity  = rs.getInt("quantity");
            double weightPerQuantity = rs.getDouble("weightPerQuantity");
            String name = rs.getString("name");
            warehouse.addProduct(new Product(name,weightPerQuantity,id),quantity);
        }
    }

    private void readFromDB_Order(Statement stmt) throws SQLException {
        String sql = "SELECT ORDERS_2.id as order_id,ORDERS_ADDS.stringDate,ORDERS_ADDS.battery,a1.*,COORDINATES.X,COORDINATES.Y\n" +
                "from ORDERS_2 \n" +
                "JOIN (\n" +
                "SELECT TableProducts.quantity,PRODUCT.id,PRODUCT.name,PRODUCT.weightPerQuantity\n" +
                "from TableProducts JOIN PRODUCT\n" +
                "ON TableProducts.product_id = PRODUCT.id\n" +
                "where TableProducts.delivered = 1 \n" +
                ") as a1 \n" +
                "ON ORDERS_2.quantity_id = a1.id\n" +
                "join COORDINATES\n" +
                "on ORDERS_2.coordinates_id = COORDINATES.id\n" +
                "join ORDERS_ADDS\n" +
                "on ORDERS_2.orders_adds_id= ORDERS_ADDS.id";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int order_id  = rs.getInt("order_id");
            double weightPerQuantity = rs.getDouble("weightPerQuantity");
            String name = rs.getString("name");
            int id  = rs.getInt("id");
            int quantity  = rs.getInt("quantity");
            double weightPerQuantity = rs.getDouble("weightPerQuantity");
            String name = rs.getString("name");
            warehouse.addProduct(new Product(name,weightPerQuantity,id),quantity);
        }
    }


}
