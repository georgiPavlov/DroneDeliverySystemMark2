package DeliverySuppyPakage;

import CoordinatesPakage.PairCoordinates;
import DroneDataBasePakage.MakeOperations;
import ExceptionsPakage.InputExeption;
import ProjectInterfaces.DeliverySupplyRequest;
import WarehousePakage.Warehouse;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class Request {
    private Warehouse currentWarehouse;
    private Scanner scanner;

    public Request(Warehouse currentWarehouse) {
        this.currentWarehouse = currentWarehouse;
        scanner = new Scanner(System.in);
    }

    public void addToWarehouse() throws InputExeption {
        FactoryForDeliverySupply factory = new FactoryForDeliverySupply();
        DeliverySupplyRequest request;
        System.out.println("Enter operation (1)delivery (2)supply (3)exit");
        boolean loop = true;

        while (loop) {
            int choice;
            String tryNumber = scanner.nextLine();
            choice =  Matcher.returnKey(tryNumber,"in (Enter operation (1)delivery (2)supply)");
            if(choice == 3){
                loop = false;
                System.out.println("Good bye...");
                continue;
            }
            request = factory.maker(choice);
            request.startOperation(scanner,currentWarehouse);
        }
    }

    public static void main(String[] args) throws InputExeption {
        Warehouse warehouse = new Warehouse(new PairCoordinates(41,41));
        MakeOperations operations = new MakeOperations(warehouse);
        try {
            operations.readFromDB_Supply();
            operations.readFromDB_Order();
            operations.readFromDB_DRONE_DELIVERY_WINDOWS();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Request request = new Request(warehouse);
        request.addToWarehouse();
        try {
            operations.writeFromDB_Supply();
            operations.writeFromDB_Delivery();
            operations.writeFromDB_DRONE_DELIVERY_WINDOWS();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
