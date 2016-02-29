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
    private int id;
    private long[] distance;

    public History(int id, long[] distance) {
        this.id = id;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public long[] getDistance() {
        return distance;
    }
}
