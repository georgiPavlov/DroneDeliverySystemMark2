package DeliverySuppyPakage;

import ProjectInterfaces.DeliverySupplyRequest;
import WarehousePakage.Warehouse;

import java.util.Scanner;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class Delivery implements DeliverySupplyRequest {
    @Override
    public void startOperation(Scanner scanner, Warehouse warehouse) {
        System.out.println("Delivery");
    }
}
