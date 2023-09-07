package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coins")
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coin {

    @Id
    @Column(name = "id")
    Integer id;

    @Column(name = "symbol")
    String symbol;

    @Column(name = "name")
    String name;

    @Column(name = "price_usd")
    Double price_usd;


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