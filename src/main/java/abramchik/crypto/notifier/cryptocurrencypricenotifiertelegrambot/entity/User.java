package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    Long userId;

    @Column(name = "username")
    String username;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "coins_has_users",
            joinColumns = @JoinColumn(name = "users_user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "coins_id", referencedColumnName = "id"))
    Set<Coin> coins = new HashSet<>();

}
