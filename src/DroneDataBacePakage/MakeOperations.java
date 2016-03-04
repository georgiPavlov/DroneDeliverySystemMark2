package DroneDataBacePakage;

import CoordinatesPakage.PairCoordinates;
import DronePakage.Drone;
import DronePakage.DroneDeliveryWindows;
import WarehousePakage.Order;
import WarehousePakage.Product;
import WarehousePakage.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by georgipavlov on 29.02.16.
 */
public class MakeOperations {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/new_schema";
    static final String USER = "root";
    static final String PASS = "111111111111";

    private Warehouse warehouse;
    private Connection conn = null;
    private Statement stmt = null;

    public MakeOperations(Warehouse warehouse) {
        this.warehouse = warehouse;
        try {
            connectionToDataBase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void connectionToDataBase() throws ClassNotFoundException, SQLException {
        //STEP 2: Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");
        //STEP 3: Open a connection
        System.out.println("Connecting to a selected database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected database successfully...");

    }

    public void readFromDB_Supply() throws SQLException {
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

    public void readFromDB_Order() throws SQLException {
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
        ArrayList<Order> orders = new ArrayList<>();
        while (rs.next()){
            int order_id  = rs.getInt("order_id");
            String stringDate = rs.getString("stringDate");
            double weightPerQuantity = rs.getDouble("weightPerQuantity");
            int battery = rs.getInt("battery");
            int quantity = rs.getInt("quantity");
            int product_id  = rs.getInt("id");
            String name  = rs.getString("quantity");
            int X = rs.getInt("X");
            int Y  = rs.getInt("Y");

            Order order = new Order(new PairCoordinates(X,Y),0,battery,stringDate);
            order.addProduct(new Product(name,weightPerQuantity,product_id));
            order.putToQuantityMap(product_id,quantity);
            order.setId(order_id);
            if(orders.contains(order)){
                orders.remove(order);
                orders.add(order);
            }
        }
        for (int i = 0; i < orders.size() ; i++) {
            warehouse.orders.add(orders.get(i));
        }
    }

    public void readFromDB_DRONE_DELIVERY_WINDOWS() throws SQLException {
        String sql = "select * from DRONE_DELIVERY_WINDOWS;";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id_drone  = rs.getInt("id_drone");
            int battery = rs.getInt("battery");
            String start_time = rs.getString("start_time");
            String end_time = rs.getString("end_time");
            warehouse.getDrones().get(id_drone).addDroneDeliveryWindow(
                    new DroneDeliveryWindows(start_time,end_time,battery));
        }
    }



    public void writeFromDB_Supply() throws SQLException {
        stmt = conn.createStatement();
        Map<Product,Integer> mp = warehouse.getProducts();
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Product product = (Product) pair.getKey();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            String productSQL = "INSERT INTO PRODUCT " +
                     "VALUES ('"+product.getId()+"','"+product.getName()+"','"
                    +product.getWeightPerQuantity()+"','NULL')";
            stmt.executeUpdate(productSQL);
            String tableProducts =  "INSERT INTO TableProducts " +
                     "VALUES (NULL,'"+pair.getValue()+"','"+product.getId()+"','1','0')";
            stmt.executeUpdate(tableProducts);
        }
        System.out.println("Inserted records into the table...");
    }

    public void writeFromDB_Delivery() throws SQLException {
        while (!warehouse.orders.isEmpty()){
           Order order =  warehouse.orders.poll();
            String orders_adds =  "INSERT INTO ORDERS_ADDS " +
                    "VALUES (NULL,'"+order.getDate()+"','"+order.getBattery()+"','1')";
            stmt.executeUpdate(orders_adds);
            String sql1 = "select * from DRONE_DELIVERY_WINDOWS WHERE DRONE_DELIVERY_WINDOWS.stringDate =" +
                    "" + order.getDate() + ";";
            ResultSet rs = stmt.executeQuery(sql1);
            int orders_add_key = rs.getInt("id");
            String coordinates =  "INSERT INTO COORDINATES " +
                    "VALUES (NULL,'"+order.getPairCoordinates().getX()+"','"+order.getPairCoordinates().getY()+"')";
            stmt.executeUpdate(coordinates);
            String sql2 = "select * from  COORDINATES WHERE  COORDINATES.X =" +
                    "" + order.getPairCoordinates().getX() + "AND COORDINATES.Y = "+
                    order.getPairCoordinates().getY() +";";
            ResultSet rs2 = stmt.executeQuery(sql2);
            int coordinates_key = rs2.getInt("id");
            ArrayList<Product> products = order.getPakageOrder();
            ResultSet rs3 = null;
            for (int i = 0; i <products.size(); i++) {
                Product product = products.get(i);
                String productSQL = "INSERT INTO PRODUCT " +
                        "VALUES ('"+product.getId()+"','"+product.getName()+"','"
                        +product.getWeightPerQuantity()+"','NULL')";
                stmt.executeUpdate(productSQL);
                String tableProducts =  "INSERT INTO TableProducts " +
                        "VALUES (NULL,'"+order.returnQuantity(product.getId())+"','"+product.getId()+"','0','1')";
                stmt.executeUpdate(tableProducts);
               // ---------------------------------------
                String sql3 = "select * from  TableProducts WHERE  TableProducts.quantity  =" +
                        "" + order.returnQuantity(product.getId()) + "AND TableProducts.product_id  = "+
                        product.getId() +"AND TableProducts.delivered  = 1"+";";
                 rs3 = stmt.executeQuery(sql3);
                int id3 = rs3.getInt("id");

                String orders_2 =  "INSERT INTO ORDERS_2 " +
                        "VALUES (NULL,'"+orders_add_key+"','"+coordinates_key+"','"+id3+"')";
                stmt.executeUpdate(orders_2);
            }
            rs3.close();
            rs.close();
            rs2.close();
        }
    }

    public void writeFromDB_DRONE_DELIVERY_WINDOWS() throws SQLException {
        for (int i = 0; i < warehouse.getDrones().size(); i++) {
            Drone drone = warehouse.getDrones().get(i);
            List<DroneDeliveryWindows> windows = drone.getDroneDeliveryWindowsList();
            for (int j = 0; j < windows.size() ; j++) {
                String windowSQL = "INSERT INTO DRONE_DELIVERY_WINDOWS " +
                        "VALUES (NULL ,'"+windows.get(j).getEndBattery()+"','"+windows.get(j).getStartTime()+"','"
                        +windows.get(j).getEndTime()+"')";
                stmt.executeUpdate(windowSQL);
            }

        }
    }




}
