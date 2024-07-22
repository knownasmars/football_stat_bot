package footballbot.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChatIdService {

    private final Set<String> chatIds = new HashSet<>();

    public void addChatId(String chatId) {
        chatIds.add(chatId);
    }

    public Set<String> getChatIds() {
        return chatIds;
    }
}