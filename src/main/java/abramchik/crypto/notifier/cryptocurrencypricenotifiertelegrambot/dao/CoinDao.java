package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;

public interface CoinDao {

    void safeCoin(Coin coin);

    void updateCoin(long id);

    Coin getCoinById(long id);

    Coin getCoinBySymbol(String symbol);

    void addСoinTracking(String coinSymbol, Long userId);

    void removeСoinTracking(String coinSymbol, Long userId);

    void removeAllCoinTracking(Long userId);
}
