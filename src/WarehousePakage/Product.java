package WarehousePakage;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class Product {
    private String name;
    private double weightPerQuantity;
    private int id;


    public Product(String n, double w,int id) {
        name = n;
        weightPerQuantity = w;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getWeightPerQuantity() {
        return weightPerQuantity;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.weightPerQuantity, weightPerQuantity) != 0) return false;
        if (id != product.id) return false;
        return name != null ? name.equals(product.name) : product.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(weightPerQuantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + id;
        return result;
    }
}
