package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    CoinDao coinDao;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    @Override
    public String safeUser(User user) {
        if (findUser(user) == false) {
            entityManager.merge(user);
            return "Success! New user has been created! id:" + user.getUserId();
        } else {
            return "Error! User has been already created!";
        }
    }

    @Override
    public String followCoin(User user, Long coinId) {
        System.out.println(user);
        User userUpdate = entityManager.find(User.class, user.getUserId());

        Coin coin = coinDao.getCoinById(coinId);
        if (coin != null) {
            userUpdate.getCoins().add(coin);
            entityManager.merge(userUpdate);
            entityManager.flush();
            return "Done!";
        } else {
            return "Incorrect currency ID!";
        }
    }

    @Override
    public boolean findUser(User user) {
        User userFromDB = entityManager.find(User.class, user.getUserId());
        if (userFromDB == null) {
            return false;
        } else {
            return true;
        }
    }

}
