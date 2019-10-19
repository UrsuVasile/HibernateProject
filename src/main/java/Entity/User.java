package Entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    private static final String USER_SEQUENCE = "user_id_sequence";
    private static final String USER_GENERATOR = "user_generator";

    @Id
    @SequenceGenerator(name="USER_GENERATOR", sequenceName = USER_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = USER_GENERATOR)
    private int id;

    @Column(name = "username")
    private String username;

    @Column
    private String password;

    @Column
    private String rol;

    public static String getUserSequence() {
        return USER_SEQUENCE;
    }

    public static String getUserGenerator() {
        return USER_GENERATOR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(rol, user.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, rol);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
