package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataInfo {

    Coin[] data;
    Info info;

}
