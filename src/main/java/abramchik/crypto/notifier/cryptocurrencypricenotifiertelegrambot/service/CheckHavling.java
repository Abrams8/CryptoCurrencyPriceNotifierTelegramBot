package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.service;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao.CoinDao;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.dao.CoinDaoImpl;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.Coin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.DataInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Service
public class CheckHavling {

    private long currentSupply;

    private boolean result;

    @Autowired
    private CoinDao coinDao = new CoinDaoImpl();

    @Scheduled(fixedDelay = 20000)
    public void checkCryptoPrices() {
        RestTemplate restTemplate = new RestTemplate();

        String pathCoinCounter = "https://api.coinlore.net/api/tickers/?start=0&limit=1";
        DataInfo coinCounter = restTemplate.getForObject(pathCoinCounter, DataInfo.class);
        int coinsNum = coinCounter.getInfo().getCoins_num();
        System.out.println(coinsNum);

        for (int i = 0; i <= coinsNum; i += 100) {
            String path1 = "https://api.coinlore.net/api/tickers/?start=" + i + "&limit=100";
            DataInfo dataInfo1 = restTemplate.getForObject(path1, DataInfo.class);
            List<Coin> coinList1 = Arrays.asList(dataInfo1.getData());

            for (Coin coin : coinList1) {
                coinDao.safeCoin(coin);
            }
            System.out.println(i);
            System.out.println(Arrays.stream(dataInfo1.getData()).toList());
        }


//        ResponseEntity<Coin[]> responseEntityCoins =
//                restTemplate.getForEntity(path, Coin[].class);
//
//        List<Coin> coins = List.copyOf(Arrays.stream(responseEntityCoins.getBody()).toList());
//
//        System.out.println(coins);

    }


//    @Scheduled(fixedDelay = 500)
//    public void checkPreHalving() {
//        RestTemplate restTemplate = new RestTemplate();
//        String sflpath = "https://api.polygonscan.com/api?module=stats&action=tokensupply&contractaddress=0xD1f9c58e33933a993A3891F8acFe05a68E1afC05&apikey=9CWKT6QF3EXZ6MG9Z7N7WT89AVTZCV2W98";
//        SflObject sflObject = restTemplate.getForObject(sflpath, SflObject.class);
//
//        String srt = sflObject.getResult().substring(0, 8);
//        long halving = Long.parseLong(srt);
//        currentSupply = halving;
//
//        long limit = 29990000;
//
//        if (limit <= halving) {
//            result = true;
//        }
//    }
//
//    @Override
//    public String toString() {
//        String totalSupplyStr = String.valueOf(currentSupply).substring(0, 2) + "."
//                + String.valueOf(currentSupply).substring(2, 5) + "."
//                + String.valueOf(currentSupply).substring(5, 8) + " SFL";
//        return "Текущий total supply: " +
//                totalSupplyStr;
//    }
}
