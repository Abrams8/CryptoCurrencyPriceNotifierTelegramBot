package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j
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
        try {
            return entityManager.find(Coin.class, coinId);
        } catch (Exception e) {
            log.info("Something wrong in CoinDao(getCoinById): \n" + e.getMessage());
            return null;
        }
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
        try {
            TraceableCoin dd = entityManager.find(TraceableCoin.class, traceableCoin);
            entityManager.remove(dd);
        } catch (Exception e) {
            log.info("Something wrong in CoinDao(removeСoinTracking): \n" + e.getMessage());
        }

    }

    @Override
    public void removeAllCoinTracking(Long userId) {

    }

    @Override
    public List<Coin> getAllCoins() {
        try {
            return entityManager.createQuery("SELECT c from Coin c", Coin.class).getResultList();
        } catch (Exception e) {
            log.info("Something wrong in CoinDao(getAllCoins): \n" + e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Long, Double> getAllCoinIdAndCoinPrices() {

        try {
            List<Coin> allCoins = entityManager.createQuery("SELECT c from Coin c", Coin.class).getResultList();
            Map<Long, Double> coinsIdAndPrice = new HashMap<>();

            for (Coin coin : allCoins) {
                coinsIdAndPrice.put(coin.getId(), coin.getPrice_usd());
            }
            return coinsIdAndPrice;
        } catch (Exception e) {
            log.info("Something wrong in CoinDao(getAllCoinIdAndCoinPrices): \n" + e.getMessage());
            return null;
        }
    }
}
