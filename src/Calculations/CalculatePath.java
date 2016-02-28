package Calculations;

import CoordinatesPakage.PairCoordinates;
import WarehousePakage.Warehouse;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class CalculatePath {
    public int calculatePath(PairCoordinates pairCoordinates, Warehouse warehouse){
        // ((x1 - x2)^2 + (y1 - y2)^2)^-2
        double operation1 = Math.pow(pairCoordinates.getX() - warehouse.getPairCoordinates().getX(),2);
        double operation2 = Math.pow(pairCoordinates.getY() - warehouse.getPairCoordinates().getY(),2);
        double result = Math.pow(operation1 + operation2,-2);
        return (int)result;
    }
}
