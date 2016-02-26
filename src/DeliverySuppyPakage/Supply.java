package DeliverySuppyPakage;

import ExceptionsPakage.InputExeption;
import ProjectInterfaces.DeliverySupplyRequest;
import WarehousePakage.Product;
import WarehousePakage.Warehouse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class Supply implements DeliverySupplyRequest {
    @Override
    public void startOperation(Scanner scanner, Warehouse warehouse) {
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
                        Product product = menu2(scanner);
                        quantity = menu3(scanner);
                        warehouse.addProduct(product,quantity);
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
        System.out.println("Enter 1 to add product");
        System.out.println("Enter 2 to exit");
        String tryNumber = scanner.nextLine();
        choice = Matcher.returnKey(tryNumber," in adding product/exit");
        return choice;
    }

    private Product menu2(Scanner scanner) throws InputExeption {
        double choice;
        System.out.println("Enter id");
        String id = scanner.nextLine();
        int idInt = Matcher.returnQuantity(id," in adding product/exit");
        System.out.println("Enter date  YYYY-MM-DD HH:MM " );
        String inputDate = scanner.nextLine();
        System.out.println("You enter  " +  validateDate(inputDate) + " date" );

        System.out.println("Enter name");
        String name;
        name = scanner.nextLine();
        name = Matcher.returnName(name,"in adding product/exit");
        System.out.println("Enter weight");
        String weight = scanner.nextLine();
        choice = Matcher.returnWeight(weight," in adding product/exit");

        return new Product(name,choice,idInt);
    }

    private int menu3(Scanner scanner)  throws InputExeption{
        int choice;
        System.out.println("Enter quantity");
        String quantity  = scanner.nextLine();
        choice = Matcher.returnQuantity(quantity,"in adding quantity");
        return choice;
    }

    public boolean validateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM");
        try {
            sdf.parse(date);
            return true;
        }
        catch(ParseException ex) {
            return false;
        }
    }
}
