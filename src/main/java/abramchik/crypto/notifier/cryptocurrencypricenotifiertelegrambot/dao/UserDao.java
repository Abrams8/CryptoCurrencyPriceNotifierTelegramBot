package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;

public interface UserDao {

    String safeUser(User user);

    String followCoin(User user, Long coinId);

    boolean findUser(User user);

}
