package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.service;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.config.BotConfig;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    @Autowired
    private CheckHavling checkHavling;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();
            User user = new User();
            user.setUserId(update.getMessage().getChatId());
            user.setUsername(update.getMessage().getChat().getUserName());

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId);
                    String message = checkHavling.safeNewUserService(user);
                    System.out.println(message);
                    break;
                case "/rules":
                    sendMessage(chatId, "Enter currency ID which you want to follow, direction (UP '+' or DOWN '-'), stop point (example: id90+37540): ");
                    break;
                default:

                    if (messageText.matches("id\\d+[+-][0-9]+[.]?[0-9]*")) {
                        messageText = messageText.substring(2);

                        TraceableCoin traceableCoin = new TraceableCoin();

                        if(messageText.contains("+")){
                            traceableCoin.setDirection(true);
                        }else {
                            traceableCoin.setDirection(false);
                        }

                        String[] messageInfoToArray = messageText.split("-|\\+");
                        traceableCoin.setCoinId(Long.valueOf(messageInfoToArray[0]));
                        traceableCoin.setStopPoint(Double.valueOf(messageInfoToArray[1]));

                        checkHavling.followCurrency(user, traceableCoin);
                        System.out.println("Saved");
                        break;
                    }

                    sendMessage(chatId, "Sorry, command is not support yet)");
            }
        }
    }

    @Scheduled(fixedDelay = 1)
    private void sendNotification() {

            Map<Long, Map<Long, Double>> maaap = checkHavling.checkFollowedCoins();
            if (!maaap.isEmpty()){
                Set<Long> set = maaap.keySet();
                for (Long aa: set) {
                    maaap.get(aa);
                    sendMessage(aa, maaap.get(aa).toString());
                }
            }
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    private void startCommandReceived(long chatId) {
        String answer = "Hi, I'm Crypto BOT! Lets earn some money :)";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
