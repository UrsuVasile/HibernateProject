package Entity;

import java.util.Objects;

public class Result {

    public String name;

    public int stockQuantity;

    public Product product;

    public Stock stock;


    public Result() {
    }

    public Result(String name, int stock_quantity) {
        this.name = name;
        this.stockQuantity = stock_quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result result = (Result) o;
        return stockQuantity == result.stockQuantity &&
                Objects.equals(name, result.name) &&
                Objects.equals(product, result.product) &&
                Objects.equals(stock, result.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stockQuantity, product, stock);
    }

    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
