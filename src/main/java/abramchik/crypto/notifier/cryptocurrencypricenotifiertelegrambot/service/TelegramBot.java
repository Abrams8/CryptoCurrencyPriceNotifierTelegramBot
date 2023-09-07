package abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.service;

import abramchik.crypto.notifier.cryptocurrencypricenotifiertelegrambot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;

    private Set<Long> setChatId = new HashSet<>();

    private int counter = 0;

//    {
//        for(int i = 0; i<10; i++){
//            setChatId.add(4242l + i);
//        }
//    }

    @Autowired
    private CheckHavling checkHavling;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId);
                    setChatId.add(chatId);
                    System.out.println(setChatId);
                    break;
                case "/check":
                    sendMessage(chatId, checkHavling.toString());
                    break;
                default:
                    sendMessage(chatId, "Sorry, command is not support yet)");
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    private void sendNotification() {
        if (checkHavling.isResult()) {
            for (Long a : setChatId) {
                String message = "ВНИМАНИЕ!!\n" + checkHavling.toString().toUpperCase() + "\nХАЛВИНГ БЛИЗКО!! \nСДЕЛАЙ СИНХРОНИЗАЦИЮ НА SUNFLOWER LAND!!";
                sendMessage(a, message);
            }
            counter++;
            if (counter >= 3) {
                setChatId.clear();
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
        String answer = "Приветствую! Данный бот пришлет вам уведомление," +
                " когда total supply будет равен 29.990.000 sfl" +
                " (халвинг произойдет на 30.000.000 sfl)." +
                "\n\nЧтобы проверить текущий total supply введите /check\n\nGood luck!";
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
