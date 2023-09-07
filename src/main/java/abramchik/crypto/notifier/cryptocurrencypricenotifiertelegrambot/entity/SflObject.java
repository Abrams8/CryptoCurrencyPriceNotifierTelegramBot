package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SflObject implements Serializable {
    String status;
    String message;
    String result;
}
