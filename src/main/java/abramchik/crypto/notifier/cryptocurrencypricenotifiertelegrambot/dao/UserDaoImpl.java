package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository
@Log4j
public class UserDaoImpl implements UserDao {

    @Autowired
    CoinDao coinDao;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    @Override
    public String safeUser(User user) {
        try {
            if (!findUser(user)) {
                entityManager.merge(user);
                return "Success! New user has been created! id:" + user.getUserId();
            } else {
                return "Error! User has been already created!";
            }
        } catch (Exception e) {
            log.info("Something wrong in UserDao(safeUser): \n" + e.getMessage());
            return null;
        }
    }

    @Override
    public String followCoin(User user, TraceableCoin traceableCoin) {
        try {
            User userUpdate = entityManager.find(User.class, user.getUserId());
            Coin coin = coinDao.getCoinById(traceableCoin.getCoinId());
            if (coin != null) {
                userUpdate.getCoins().add(coin);
                entityManager.merge(userUpdate);
                entityManager.flush();
                entityManager.createNativeQuery("UPDATE coins_has_users set stop_point = ?, direction = ? WHERE users_user_id = ? AND coins_id = ?;")
                        .setParameter(1, traceableCoin.getStopPoint())
                        .setParameter(2, traceableCoin.getDirection())
                        .setParameter(3, user.getUserId())
                        .setParameter(4, traceableCoin.getCoinId())
                        .executeUpdate();
                return "Done!";
            } else {
                return "Incorrect currency ID!";
            }
        } catch (Exception e) {
            log.info("Something wrong in UserDao(followCoin): \n" + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean findUser(User user) {
        try {
            User userFromDB = entityManager.find(User.class, user.getUserId());
            if (userFromDB == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.info("Something wrong in UserDao(findUser): \n" + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return entityManager.createQuery("SELECT c from User c", User.class).getResultList();
        } catch (Exception e) {
            log.info("Something wrong in UserDao(getAllUsers): \n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TraceableCoin> getAllTraceableCoins() {
        try {
            return entityManager.createQuery("SELECT c from TraceableCoin c", TraceableCoin.class).getResultList();
        } catch (Exception e) {
            log.info("Something wrong in UserDao(getAllTraceableCoins): \n" + e.getMessage());
            return null;
        }
    }
}
