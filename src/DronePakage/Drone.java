package DronePakage;

import WarehousePakage.Order;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class Drone {
    private List<DroneDeliveryWindows> droneDeliveryWindowsList;
    private Queue<Order> orders ;

    public void addDroneDeliveryWindow(DroneDeliveryWindows droneDeliveryWindows){
        droneDeliveryWindowsList.add(droneDeliveryWindows);
    }

    public Drone(){
        List<DroneDeliveryWindows> droneDeliveryWindowsList = new CopyOnWriteArrayList<>();
        Queue<Order> orders  = new ConcurrentLinkedQueue<>();
    }

    public List<DroneDeliveryWindows> getDroneDeliveryWindowsList() {
        return droneDeliveryWindowsList;
    }

    public Queue<Order> getOrders() {
        return orders;
    }


}
