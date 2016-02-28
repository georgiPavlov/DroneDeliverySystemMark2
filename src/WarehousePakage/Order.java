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
}
