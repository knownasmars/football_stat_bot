package footballbot.listener;

import footballbot.event.SendMessageEvent;
import footballbot.event.SendPollEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class SendMessageEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SendMessageEventListener.class);

    private final AbsSender absSender;

    @EventListener
    public void onApplicationEvent(SendMessageEvent event) {
        try {
            absSender.execute(event.getSendMessage());
        } catch (TelegramApiException e) {
            logger.error("Error sending message: {}", e.getMessage(), e);
        }
    }

    @EventListener
    public void onApplicationEvent(SendPollEvent event) {
        try {
            absSender.execute(event.getSendPoll());
        } catch (TelegramApiException e) {
            logger.error("Error sending poll: {}", e.getMessage(), e);
        }
    }
}