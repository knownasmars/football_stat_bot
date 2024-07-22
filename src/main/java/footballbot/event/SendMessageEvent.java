package footballbot.event;

import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SendMessageEvent extends ApplicationEvent {
    private final SendMessage sendMessage;

    public SendMessageEvent(Object source, String chatId, String text) {
        super(source);
        this.sendMessage = new SendMessage(chatId, text);
    }

    public SendMessageEvent(Object source, SendMessage sendMessage) {
        super(source);
        this.sendMessage = sendMessage;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }
}