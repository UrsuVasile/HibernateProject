package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name ="descriptions")
public class Description {


    private static final String DESCRIPTION_SEQUENCE = "description_id_sequence";
    private static final String DESCRIPTION_GENERATOR = "description_generator";

    @Id
    @SequenceGenerator(name="DESCRIPTION_GENERATOR", sequenceName = DESCRIPTION_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = DESCRIPTION_GENERATOR)

    @Column
    private int id;

    @Column
    private String description;

    @OneToOne(cascade = CascadeType.ALL)   //mapedBy
    @JoinColumn(name = "product_id", nullable = false)    // JoinColumn setez foregn Key-ul
    private Product product;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "Description{" +
                "description='" + description + '\'' +
                '}';
    }
}
