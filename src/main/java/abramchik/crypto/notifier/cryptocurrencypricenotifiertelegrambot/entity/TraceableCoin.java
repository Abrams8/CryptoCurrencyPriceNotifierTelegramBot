package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "coins_has_users")
@Entity
public class TraceableCoin implements Serializable{

    @Id
    @Column(name = "coins_id")
    Long coinId;

    @Id
    @Column(name = "users_user_id")
    Long userId;

    @Column(name = "stop_point")
    Double stopPoint;

    @Column(name = "direction")
    Boolean direction;

}
