package WarehousePakage;

import ConstantsPakage.DroneConstants;
import CoordinatesPakage.PairCoordinates;
import DronePakage.Drone;
import HistoryPakage.History;
import TimetablePakage.Timetable;

import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class Warehouse {
    private Map<Product, Integer> products;
    private PairCoordinates pairCoordinates;
    private List<Timetable> timetables;
    public Queue<Order> orders;
    private List<Drone> drones;
    private List<History> histories;
    private Distributor distributor;

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Warehouse(PairCoordinates pairCoordinates) {
        products = new ConcurrentHashMap<>();
        this.pairCoordinates = pairCoordinates;
        timetables = new CopyOnWriteArrayList<>();
        orders = new ConcurrentLinkedQueue<>();
        this.pairCoordinates = pairCoordinates;
        drones = new CopyOnWriteArrayList<>();
        histories = new CopyOnWriteArrayList<>();
        for (int i = 0; i < DroneConstants.MAX_DRONES; i++) {
            drones.add(new Drone());
        }
        distributor = new Distributor(this);
        distributor.start();


    }

    public List<History> getHistories() {
        return histories;
    }

    public void addHistory(History history){
        histories.add(history);
    }

    public History setHistory(int index){
        return histories.get(index);
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public Order returnLastOrder(){
        return orders.poll();
    }



    public PairCoordinates getPairCoordinates() {
        return pairCoordinates;
    }

    public void addTimeTable(Timetable timetable){
        timetables.add(timetable);
    }

    public Timetable getTimetableIndex(int i){
        return timetables.get(i);
    }


    public void addProduct(Product p,int quantity) {
        if (products.containsKey(p)) {
            int times = products.get(p) + quantity;
            products.put(p, times);
        } else {
            products.put(p, quantity);
        }
    }

    public void addHashTable(Map<Product,Integer> timeStampTable) {
        Iterator it = timeStampTable.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (products.containsKey(pair.getKey())) {
                int times = products.get(pair.getKey())+ (Integer)pair.getValue();
                products.put((Product)pair.getKey(), times);
            } else {
                products.put((Product) pair.getKey(), (Integer) pair.getValue());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

    }

    // Проверява дали този продукт го има в поне даденото количество
    public  boolean containProductNtimes(Product p, int times) {
        if (products.containsKey(p)) {
            if (products.get(p) >= times) {
                return true;
            }
        }
        return false;
    }
    // Взима т.е. маха от опашката този продукт колкото количество е въведено.
    // Използва се след проверката
    // дали го има в warehouse-а

    public void getProductNtimes(Product p, int times) {
        if (products.get(p) > times) {
            int quantityLeft = products.get(p) - times;
            products.put(p, quantityLeft);
        } else
            products.remove(p);
    }


}
