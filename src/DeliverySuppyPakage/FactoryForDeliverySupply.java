package DeliverySuppyPakage;

import ProjectInterfaces.DeliverySupplyRequest;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class FactoryForDeliverySupply {
    public DeliverySupplyRequest maker(int choice){
        switch (choice) {
            case 1: {
                return  new Delivery();
            }case 2: {
                return new Supply();
            }
        }
        return null;
    }
}
