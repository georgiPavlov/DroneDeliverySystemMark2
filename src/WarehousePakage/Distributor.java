package WarehousePakage;

import DronePakage.Drone;

import java.util.List;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class Distributor extends Thread {
    private Warehouse warehouse;
    private boolean loop = true;
    private List<Drone> drones;



    public Distributor(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.drones = warehouse.getDrones();
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }


    @Override
    public void run() {
        while (loop) {
            synchronized (warehouse.orders) {
                while (warehouse.orders.isEmpty()) {
                    try {
                        warehouse.orders.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
              startOperation(warehouse.orders.poll());
            }

        }
    }

    private void startOperation(Order order) {


    }
}
