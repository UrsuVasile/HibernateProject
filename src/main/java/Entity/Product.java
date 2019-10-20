package Entity;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "show_all_products",
                query = "SELECT p FROM Product p")
})

@Entity
@Table(name = "products")
public class Product {

    private static final String PRODUCTS_SEQUENCE = "products_id_sequence";
    private static final String PRODUCTS_GENERATOR = "products_generator";

    @Id
    @SequenceGenerator(name="PRODUCTS_GENERATOR", sequenceName = PRODUCTS_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = PRODUCTS_GENERATOR)
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private Description description;

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + description +
                '}';
    }
}
