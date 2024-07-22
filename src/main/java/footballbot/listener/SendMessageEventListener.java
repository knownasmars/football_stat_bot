package footballbot.listener;

import footballbot.api.ZeonFootballBot;
import footballbot.event.SendMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class SendMessageEventListener implements ApplicationListener<SendMessageEvent> {

    private final ZeonFootballBot bot;

    @Autowired
    public SendMessageEventListener(ZeonFootballBot bot) {
        this.bot = bot;
    }

    @Override
    public void onApplicationEvent(SendMessageEvent event) {
        SendMessage message = event.getSendMessage();

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }
}