package Calculations;

import ConstantsPakage.Constants;
import ExceptionsPakage.TooManyDronesRequired;
import WarehousePakage.Order;
import WarehousePakage.Product;

import java.util.ArrayList;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class CalculateParameters {
    private static double orderWeight(Order order){
        ArrayList<Product> products = order.getPakageOrder();
        double weight = 0;
        for (int i = 0; i <products.size() ; i++) {
            weight += products.get(i).getWeightPerQuantity() * order.getQuantityFromId(products.get(i).getId());
        }
        return weight;
    }

    public static int getNumberOfRequiredDrones(Order order){
        double weight = orderWeight(order);
        double requiredDrones  = weight / Constants.MAX_CAPASITY;
        if(requiredDrones > Constants.MAX_DRONES){
            try {
                throw new TooManyDronesRequired();
            } catch (TooManyDronesRequired tooManyDrones) {
                tooManyDrones.printStackTrace();
            }
        }
        if (requiredDrones == (int)requiredDrones)
        {
            return  (int)requiredDrones;
        }else {
            return ((int)requiredDrones)+1;
        }


    }
}
