package Entity;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "total_stock",
                query = "SELECT sum(stockQuantity) FROM Stock"),
        @NamedQuery(name = "stock_products",
                query = "select new Entity.Result(p.name, s.stockQuantity) from Product p inner join p.stock s"),
        @NamedQuery(name = "stock_zero",
                query = "select new Entity.Result(p.name, s.stockQuantity) from Product p inner join p.stock s where s.stockQuantity = '0'"),
        @NamedQuery(name = "sufficient_stock",
                query = "select new Entity.Result(p.name, s.stockQuantity) from Product p inner join p.stock s where s.stockQuantity > '10'")
})


@Entity
@Table(name = "stock", schema = "hibernateproject")
public class Stock {

    private static final String STOCK_SEQUENCE = "stock_id_sequence";
    private static final String STOCK_GENERATOR = "stock_generator";

    @Id
    @SequenceGenerator(name = "STOCK_GENERATOR", sequenceName = STOCK_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = STOCK_GENERATOR)
    private int id;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return getId() == stock.getId() &&
                getStockQuantity() == stock.getStockQuantity() &&
                Objects.equals(getProduct(), stock.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStockQuantity(), getProduct());
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
