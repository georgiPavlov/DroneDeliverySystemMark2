package WarehousePakage;

import Calculations.CalculateDate;
import Calculations.CalculateParameters;
import ConstantsPakage.DroneConstants;
import DronePakage.Drone;
import DronePakage.DroneDeliveryWindows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String endDate = getEndTimeRequaredForBattery(order.getBattery(),order.getDate());
        boolean inAnotherTimeWindow;
        int index=0;

        do{
            inAnotherTimeWindow = false;
            for (int i = 0; i < windows.size() ; i++) {
              if(tryWindow(endDate,windows.get(index))){
                  endDate = addTime(endDate,windows.get(index));
              index = i;
               inAnotherTimeWindow = true;
              break;
              }
            }
        } while (inAnotherTimeWindow);

        String finishDate;
        boolean  isBetweenTwoWindows;
        do{
            isBetweenTwoWindows = false;
            do{
                inAnotherTimeWindow = false;
                finishDate = calculateFinishDate(endDate,order.getBattery());
                for (int i = 0; i < windows.size() ; i++) {
                    if(tryWindow(finishDate,windows.get(index))){
                        finishDate = addTime(finishDate,windows.get(index));
                        index = i;
                        inAnotherTimeWindow = true;
                        break;
                    }
                }
            }while (inAnotherTimeWindow);
            if(tryItGoesToWindow(endDate,finishDate){
                isBetweenTwoWindows = true;
                finishDate = addNewTime(finishDate,windows.get(mainWindowsIndex));
            }


        }while (isBetweenTwoWindows);


    }

    private String addNewTime(String finishDate, DroneDeliveryWindows droneDeliveryWindows) {

    }

    int mainWindowsIndex = 0;

    private String calculateFinishDate(String endDate, int battery) {

    }

    private boolean tryItGoesToWindow(String endDate, String finishDate ) {

        return false;
    }

    private String addTime(String endDate, DroneDeliveryWindows droneDeliveryWindows) {
        long [] distanceWindow = CalculateDate.getChronoUnits(droneDeliveryWindows.getStartTime(),
                droneDeliveryWindows.getEndTime());
        long [] distanceDate = CalculateDate.getChronoUnits(droneDeliveryWindows.getStartTime(),
                endDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime firstLocalDateTime = LocalDateTime.parse(endDate, formatter);
        p:for (int i = 0; i < distanceDate.length; i++) {
            if(distanceDate[i] < distanceWindow[i]){
               switch (i){
                   case 0:{
                       firstLocalDateTime=firstLocalDateTime.plusYears(distanceWindow[i] - distanceDate[i]);
                       continue p;
                   }case 1:{
                       firstLocalDateTime=firstLocalDateTime.plusMonths(distanceWindow[i] - distanceDate[i]);
                       continue p;
                   }case 2:{
                       firstLocalDateTime=firstLocalDateTime.plusDays(distanceWindow[i] - distanceDate[i]);
                       continue p;
                   }case 3:{
                       firstLocalDateTime=firstLocalDateTime.plusHours(distanceWindow[i] - distanceDate[i]);
                       continue p;
                   }case 4:{
                       firstLocalDateTime=firstLocalDateTime.plusMinutes(distanceWindow[i] - distanceDate[i]);
                       continue p;
                   }case 5:{
                       firstLocalDateTime=firstLocalDateTime.plusSeconds(distanceWindow[i] - distanceDate[i]);
                   }
               }
            }

        }
        return firstLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

    private String getEndTimeRequaredForBattery(int battery, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime firstLocalDateTime = LocalDateTime.parse(date, formatter);
        firstLocalDateTime = firstLocalDateTime.minusMinutes(battery/ DroneConstants.CHARGING_RATE);
        String date2 = firstLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return date2;
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
            if(distanceDate[i] < distanceWindow[i]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String date = "2015-12-12 12:12:31";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime firstLocalDateTime = LocalDateTime.parse(date, formatter);
        firstLocalDateTime = firstLocalDateTime.minusMinutes(30);
        String date2 = firstLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(date2);
    }


}
