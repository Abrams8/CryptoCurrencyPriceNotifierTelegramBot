package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CoinDaoImpl implements CoinDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void safeCoin(Coin coin) {
        entityManager.merge(coin);
    }

    @Override
    public void updateCoin(long id) {

    }

    @Override
    public Coin getCoinById(long id) {
        return null;
    }

    @Override
    public Coin getCoinBySymbol(String symbol) {
        return null;
    }

    @Override
    public void addСoinTracking(String coinSymbol, Long userId) {

    }

    @Override
    public void removeСoinTracking(String coinSymbol, Long userId) {

    }

    @Override
    public void removeAllCoinTracking(Long userId) {

    }
}
