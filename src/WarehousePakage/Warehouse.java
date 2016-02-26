package WarehousePakage;

import CoordinatesPakage.PairCoordinates;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class Warehouse {
    private Map<Product, Integer> products;
    private PairCoordinates pairCoordinates;



    public Warehouse(PairCoordinates pairCoordinates) {
        products = new ConcurrentHashMap<>();
        this.pairCoordinates = pairCoordinates;
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
