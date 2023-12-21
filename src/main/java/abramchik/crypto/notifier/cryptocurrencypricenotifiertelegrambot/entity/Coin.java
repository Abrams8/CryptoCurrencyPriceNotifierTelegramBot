package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "coins")
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coin {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "symbol")
    String symbol;

    @Column(name = "name")
    String name;

    @Column(name = "price_usd")
    Double price_usd;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "coins_has_users",
//            joinColumns = @JoinColumn(name = "coins_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "users_user_id", referencedColumnName = "user_id"))
//    private Set<User> users = new HashSet<>();


    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", priceUsd='" + price_usd + '\'' +
                '}' + "\n";
    }
}