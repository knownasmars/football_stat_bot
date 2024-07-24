package footballbot.event;

import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

public class SendPollEvent extends ApplicationEvent {
    private final SendPoll sendPoll;

    public SendPollEvent(Object source, SendPoll sendPoll) {
        super(source);
        this.sendPoll = sendPoll;
    }

    public SendPoll getSendPoll() {
        return sendPoll;
    }
}