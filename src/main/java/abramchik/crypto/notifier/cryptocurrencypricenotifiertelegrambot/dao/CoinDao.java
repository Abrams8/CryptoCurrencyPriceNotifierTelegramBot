package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;

import java.util.List;
import java.util.Map;

public interface CoinDao {

    void safeCoin(Coin coin);

    void updateCoin(long id);

    Coin getCoinById(Long coinId);

    Coin getCoinBySymbol(String symbol);

    void addСoinTracking(String coinSymbol, Long userId);

    void removeСoinTracking(TraceableCoin traceableCoin);

    void removeAllCoinTracking(Long userId);

    List<Coin> getAllCoins();

    Map<Long, Double> getAllCoinIdAndCoinPrices();
}
