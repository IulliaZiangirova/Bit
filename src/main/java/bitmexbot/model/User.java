package bitmexbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String password;

    //@OneToMany(mappedBy = "user")
    //private List<Bot> bots;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
