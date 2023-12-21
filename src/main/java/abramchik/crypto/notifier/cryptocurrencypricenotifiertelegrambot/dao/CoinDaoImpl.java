package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CoinDaoImpl implements CoinDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    @Override
    public void safeCoin(Coin coin) {
        entityManager.merge(coin);
    }

    @Override
    public void updateCoin(long id) {

    }

    @Override
    public Coin getCoinById(Long coinId) {
        Coin coin = entityManager.find(Coin.class, coinId);
        return coin;
    }

    @Override
    public Coin getCoinBySymbol(String symbol) {
        return null;
    }

    @Override
    public void addСoinTracking(String coinSymbol, Long userId) {

    }

    @Override
    public void removeСoinTracking(TraceableCoin traceableCoin) {
        TraceableCoin dd = entityManager.find(TraceableCoin.class, traceableCoin);
        entityManager.remove(dd);
    }

    @Override
    public void removeAllCoinTracking(Long userId) {

    }

    @Override
    public List<Coin> getAllCoins() {
        List<Coin> allCoins = entityManager.createQuery("SELECT c from Coin c", Coin.class).getResultList();
        return allCoins;
    }

    @Override
    public Map<Long, Double> getAllCoinIdAndCoinPrices() {

        List<Coin> allCoins = entityManager.createQuery("SELECT c from Coin c", Coin.class).getResultList();
        Map<Long, Double> coinsIdAndPrice = new HashMap<>();

        for (Coin coin : allCoins) {
            coinsIdAndPrice.put(coin.getId(), coin.getPrice_usd());
        }

        return coinsIdAndPrice;
    }
}
