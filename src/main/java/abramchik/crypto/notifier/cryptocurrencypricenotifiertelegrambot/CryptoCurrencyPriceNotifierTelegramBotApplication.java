package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoCurrencyPriceNotifierTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCurrencyPriceNotifierTelegramBotApplication.class, args);
    }

}
