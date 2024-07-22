package footballbot.service;

import footballbot.event.SendMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public MessageService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void sendResponse(String chatId, String text) {
        SendMessageEvent event = new SendMessageEvent(this, chatId, text);
        eventPublisher.publishEvent(event);
    }
}