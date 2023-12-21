package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.service;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao.CoinDao;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao.CoinDaoImpl;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao.UserDao;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao.UserDaoImpl;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.DataInfo;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

@Data
@Transactional
@Service
public class CheckHavling {

    private long currentSupply;

    private boolean result;

    @Autowired
    private CoinDao coinDao = new CoinDaoImpl();

    @Autowired
    private UserDao userDao = new UserDaoImpl();

    @Scheduled(fixedDelay = 1)
    public void checkCryptoPrices() {
        RestTemplate restTemplate = new RestTemplate();

        String pathCoinCounter = "https://api.coinlore.net/api/tickers/?start=0&limit=1";
        DataInfo coinCounter = restTemplate.getForObject(pathCoinCounter, DataInfo.class);
        int coinsNum = coinCounter.getInfo().getCoins_num();

        System.out.println(coinsNum);

        System.out.println("Start checking currencies...");

        for (int i = 0; i <= coinsNum; i += 100) {
            String path1 = "https://api.coinlore.net/api/tickers/?start=" + i + "&limit=100";
            DataInfo dataInfo1 = restTemplate.getForObject(path1, DataInfo.class);
            List<Coin> coinList1 = Arrays.asList(dataInfo1.getData());

            for (Coin coin : coinList1) {
                coinDao.safeCoin(coin);
            }
        }

        System.out.println("Checked currencies!");

    }

    public String safeNewUserService(User user) {
        return userDao.safeUser(user);
    }

    public String followCurrency(User user, TraceableCoin traceableCoin) {
        userDao.followCoin(user, traceableCoin);
        return "";
    }

    public Map<Long, Map<Long, Double>> checkFollowedCoins() {

        List<TraceableCoin> allTraceableCoins = userDao.getAllTraceableCoins();
        Map<Long, Double> allCoinIdAndCoinPrices = coinDao.getAllCoinIdAndCoinPrices();

        Map<Long, Map<Long, Double>> notifyMap = new HashMap<>();
        List<TraceableCoin> mustBeDeletedCoins = new ArrayList<>();

        for (TraceableCoin traceableCoin : allTraceableCoins) {
            Double actualPrice = allCoinIdAndCoinPrices.get(traceableCoin.getCoinId());

            if(traceableCoin.getStopPoint() <= actualPrice && traceableCoin.getDirection()){
                Map<Long, Double> coinIdAndActualPrice = new HashMap<>();
                coinIdAndActualPrice.put(traceableCoin.getCoinId(), actualPrice);
                notifyMap.put(traceableCoin.getUserId(), coinIdAndActualPrice);
                mustBeDeletedCoins.add(traceableCoin);

            }else if (traceableCoin.getStopPoint() >= actualPrice && !traceableCoin.getDirection()){
                Map<Long, Double> coinIdAndActualPrice = new HashMap<>();
                coinIdAndActualPrice.put(traceableCoin.getCoinId(), actualPrice);
                notifyMap.put(traceableCoin.getUserId(), coinIdAndActualPrice);
                mustBeDeletedCoins.add(traceableCoin);
            }
        }

        for (TraceableCoin traceableCoin : mustBeDeletedCoins) {
            coinDao.removeСoinTracking(traceableCoin);
        }
        return notifyMap;
    }
}
