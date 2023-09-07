package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    Long userId;

    @Column(name = "chat_id")
    String chatId;

    @Column(name = "username")
    String username;

}
