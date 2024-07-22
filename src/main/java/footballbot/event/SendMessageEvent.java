package footballbot.event;

import org.springframework.context.ApplicationEvent;

public class SendMessageEvent extends ApplicationEvent {

    private final String chatId;

    private final String text;

    public SendMessageEvent(Object source, String chatId, String text) {
        super(source);
        this.chatId = chatId;
        this.text = text;
    }

    public String getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }
}