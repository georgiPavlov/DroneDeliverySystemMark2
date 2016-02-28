package WarehousePakage;

import Calculations.CalculateDate;
import Calculations.CalculateParameters;
import DronePakage.Drone;
import DronePakage.DroneDeliveryWindows;

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
           int requaredDrones = CalculateParameters.getNumberOfRequiredDrones(order);
        for (int i = 0; i < requaredDrones ; i++) {
            addToDroneOrder(order);

        }

    }

    private void addToDroneOrder(Order order) {
        for (int i = 0; i < drones.size() ; i++) {
            currentDroneDeliveryWindows(order,drones.get(i));
        }
    }

    private void currentDroneDeliveryWindows(Order order, Drone drone) {
        List<DroneDeliveryWindows> windows = drone.getDroneDeliveryWindowsList();
        for (int i = 0; i < windows.size() ; i++) {
            if(tryWindow(order.getDate(),windows.get(i))){

            }
        }
    }

    private boolean tryWindow(String date, DroneDeliveryWindows droneDeliveryWindows) {
        long [] distanceWindow = CalculateDate.getChronoUnits(droneDeliveryWindows.getStartTime(),
                droneDeliveryWindows.getEndTime());
        long [] distanceDate = CalculateDate.getChronoUnits(droneDeliveryWindows.getStartTime(),
                date);
        if(distanceDate[0] == 0){
            return  false;
        }

        if(tryDistance(distanceDate,distanceWindow)){
            return true;
        }
        return false;
    }

    private boolean tryDistance(long[] distanceDate, long[] distanceWindow) {
        for (int i = 0; i < distanceDate.length; i++) {
            if(distanceDate[i] > distanceWindow[i]){
                return false;
            }
        }
        return true;
    }


}
