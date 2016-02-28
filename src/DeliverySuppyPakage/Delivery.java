package DeliverySuppyPakage;

import Calculations.CalculatePath;
import CoordinatesPakage.PairCoordinates;
import ExceptionsPakage.InputExeption;
import ProjectInterfaces.DeliverySupplyRequest;
import WarehousePakage.Order;
import WarehousePakage.Product;
import WarehousePakage.Warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class Delivery implements DeliverySupplyRequest {
    Warehouse warehouse;
    @Override
    public void startOperation(Scanner scanner, Warehouse warehouse) {
     //delivery <id> <timestamp> <target coordinates> <product name 1> <quantity> <product name 2> <quantity> ...
        this.warehouse = warehouse;
        boolean loop = true;
        int choice =0;
        int quantity=0;
        while (loop){
            try {
                choice = menu1(scanner);
            } catch (InputExeption inputExeption) {
                inputExeption.printStackTrace();
            }
            switch (choice){
                case 1:{
                    try {
                        findProduct(scanner);
                    } catch (InputExeption inputExeption) {
                        inputExeption.printStackTrace();
                    }
                    continue;
                }case 2:{
                    loop = false;
                }
            }

        }
    }

    private int menu1(Scanner scanner) throws InputExeption {
        int choice;
        System.out.println("Enter 1 to deliver product");
        System.out.println("Enter 2 to exit");
        String tryNumber = scanner.nextLine();
        choice = Matcher.returnKey(tryNumber," in adding product/exit");
        return choice;
    }

    public void findProduct(Scanner scanner) throws InputExeption {
        ArrayList<Product> products = new ArrayList<>();
        Product product;
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        int innerLoop = 1;
        while (innerLoop == 1){
            System.out.println("enter quantity");
            String line = scanner.nextLine();
            int quantity = Matcher.returnQuantity(line," in (enter quantity)");
            product = menu2(scanner);


            if(warehouse.containProductNtimes(product,quantity)){
                warehouse.getProductNtimes(product,quantity);
                hashMap.put(product.getId(),quantity);
                products.add(product);

            }else {
                System.out.println("can not make operation!..");
                throw  new InputExeption(" in findProduct");
            }
            innerLoop = menu4(scanner);
        }
        addOrder(products,scanner,hashMap);

    }

    private Product menu2(Scanner scanner) throws InputExeption {
        double choice;
        System.out.println("Enter name");
        String name;
        name = scanner.nextLine();
        name = Matcher.returnName(name,"in adding product/exit");
        System.out.println("Enter weight...");
        String weight = scanner.nextLine();

        choice = Matcher.returnWeight(weight," in adding product/exit");
        System.out.println("Enter id");
        String id = scanner.nextLine();
        int idInt = Matcher.returnQuantity(id," in adding product/exit");
        return new Product(name,choice,idInt);

    }

    private int menu4(Scanner scanner){
        System.out.println("enter 1 to add new product");
        System.out.println("enter 2 to continue to adding coordinates");
        return scanner.nextInt();

    }

    public void addOrder (ArrayList<Product> products,Scanner scanner,HashMap<Integer,Integer> map) throws InputExeption {
        System.out.println("Enter coordinates");
        System.out.println("enter x");
        String xString = scanner.nextLine();
        int x = Matcher.returnQuantity(xString," in adding x coordinates");
        System.out.println("enter y");
        String yString = scanner.nextLine();
        int y = Matcher.returnQuantity(yString," in adding y coordinates");
        CalculatePath path = new CalculatePath();
        System.out.println("Enter date  YYYY-MM-DD HH:MM " );
        String inputDate = scanner.nextLine();
        Matcher.validateDate(inputDate);
        int pathCalc = path.calculatePath(new PairCoordinates(x,y),warehouse);
        Order order = new Order(new PairCoordinates(x,y),(pathCalc + 2)*60_000,pathCalc,inputDate);
        for (int i = 0; i < products.size() ; i++) {
            order.addProduct(products.get(i));
        }
        order.addMap(map);
        warehouse.addOrder(order);
        synchronized (warehouse.orders){
            warehouse.orders.notifyAll();
        }
    }
}
