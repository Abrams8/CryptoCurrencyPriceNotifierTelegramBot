package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.service;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.config.BotConfig;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.TraceableCoin;
import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    @Autowired
    private MainService mainService;

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
                    String message = mainService.safeNewUserService(user);
                    System.out.println(message);
                    break;
                case "/rules":
                    sendMessage(chatId, "Enter currency ID which you want to follow, direction (UP '+' or DOWN '-'), stop point (example: id90+37540): ");
                    break;
                default:

                    if (messageText.matches("id\\d+[+-][0-9]+[.]?[0-9]*")) {
                        messageText = messageText.substring(2);

                        TraceableCoin traceableCoin = new TraceableCoin();

                        if (messageText.contains("+")) {
                            traceableCoin.setDirection(true);
                        } else {
                            traceableCoin.setDirection(false);
                        }

                        String[] messageInfoToArray = messageText.split("-|\\+");
                        traceableCoin.setCoinId(Long.valueOf(messageInfoToArray[0]));
                        traceableCoin.setStopPoint(Double.valueOf(messageInfoToArray[1]));

                        mainService.followCurrency(user, traceableCoin);
                        System.out.println("Saved");
                        break;
                    }

                    sendMessage(chatId, "Sorry, command is not support yet)");
            }
        }
    }

    @Scheduled(fixedDelay = 1)
    private void sendNotification() {
        Map<Long, Map<Long, Double>> allFollowedCoinsByUsers = mainService.checkFollowedCoins();

        try {
            if (!allFollowedCoinsByUsers.isEmpty()) {
                Set<Long> set = allFollowedCoinsByUsers.keySet();
                for (Long i : set) {
                    allFollowedCoinsByUsers.get(i);
                    sendMessage(i, allFollowedCoinsByUsers.get(i).toString());
                }
            }
        } catch (Exception e) {
            log.info("Something wrong in TelegramBot(sendNotification): \n" + e.getMessage());
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
        try {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(textToSend);
            execute(message);
        } catch (Exception e) {
            log.info("Something wrong in TelegramBot(sendMessage): \n" + e.getMessage());
        }
    }
}
