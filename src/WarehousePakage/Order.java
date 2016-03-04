package WarehousePakage;

import CoordinatesPakage.PairCoordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class Order {
    private PairCoordinates pairCoordinates;
    private double time;
    private int battery;
    private ArrayList<Product> products;
    private HashMap<Integer,Integer> quantities;
    private String date;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Order(PairCoordinates pairCoordinates, double time, int battery,String date) {
        this.pairCoordinates = pairCoordinates;
        this.time = time;
        this.battery = battery;
        this.date = date;
        products = new ArrayList<>();
        quantities = new HashMap<>();
    }
    public void addProduct(Product product){
        products.add(product);
    }

    public Map<Integer, Integer> getQuantity() {
        return quantities;
    }

    public int returnQuantity(int id){
        return quantities.get(id);
    }



    public void putToQuantityMap(int id,int quantity){
        quantities.put(id,quantity);
    }

    public int getQuantityFromId(int id){
        return quantities.get(id);
    }

    public PairCoordinates getPairCoordinates() {

        return pairCoordinates;
    }

    public double getTime() {
        return time;
    }

    public int getBattery() {
        return battery;
    }

    public ArrayList<Product> getPakageOrder() {
        return products;
    }

    public  void addMap(HashMap<Integer,Integer> map){
        quantities.putAll(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.time, time) != 0) return false;
        if (battery != order.battery) return false;
        if (id != order.id) return false;
        if (pairCoordinates != null ? !pairCoordinates.equals(order.pairCoordinates) : order.pairCoordinates != null)
            return false;
        return date != null ? date.equals(order.date) : order.date == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = pairCoordinates != null ? pairCoordinates.hashCode() : 0;
        temp = Double.doubleToLongBits(time);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + battery;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
