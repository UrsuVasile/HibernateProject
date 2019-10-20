package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "descriptions")
public class Description {


    private static final String DESCRIPTION_SEQUENCE = "description_id_sequence";
    private static final String DESCRIPTION_GENERATOR = "description_generator";

    @Id
    @SequenceGenerator(name = "DESCRIPTION_GENERATOR", sequenceName = DESCRIPTION_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DESCRIPTION_GENERATOR)

    @Column
    private int id;

    @Column
    private String type;

    @Column
    private String color;

    @Column
    private String description;

    @OneToOne(cascade = CascadeType.ALL)   //mapedBy
    @JoinColumn(name = "product_id", nullable = false, unique = true)    // JoinColumn setez foregn Key-ul
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;
        Description that = (Description) o;
        return id == that.id &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getColor(), that.getColor()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getType(), getColor(), getDescription(), product);
    }

    @Override
    public String toString() {
        return "Description{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
