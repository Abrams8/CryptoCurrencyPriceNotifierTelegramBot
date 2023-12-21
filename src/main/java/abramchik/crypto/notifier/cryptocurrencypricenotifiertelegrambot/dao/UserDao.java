package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    String safeUser(User user);

    String followCoin(User user, TraceableCoin traceableCoin);

    boolean findUser(User user);

    List<User> getAllUsers();

    List<TraceableCoin> getAllTraceableCoins();

}
