package HistoryPakage;

import DronePakage.DroneDeliveryWindows;
import WarehousePakage.Order;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class History {
    private Queue<Order> orders ;

    public History(){
        Queue<Order> orders  = new ConcurrentLinkedQueue<>();
    }

    public Queue<Order> getOrders() {
        return orders;
    }

}
