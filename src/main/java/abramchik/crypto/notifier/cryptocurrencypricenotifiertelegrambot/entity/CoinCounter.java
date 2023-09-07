package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoinCounter {

    Integer coinNum;

}
