package footballbot.job;

import footballbot.service.ChatIdService;
import footballbot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class MatchScheduler {

    private final MessageService messageService;

    private final ChatIdService chatIdService;

    @Scheduled(fixedRate = 300000)
    public void sendMatchReminder() {
        LocalDate nextMatchDate = getNextMatchDate();
        String formattedDate = nextMatchDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        String reminderMessage = "Скоро намечается матч: " + formattedDate + " в 19:00. Вы собираетесь играть?";

        Set<String> chatIds = chatIdService.getChatIds();
        for (String chatId : chatIds) {
            messageService.sendVoteMessage(chatId, reminderMessage, formattedDate);
        }
    }

    private LocalDate getNextMatchDate() {
        LocalDate today = LocalDate.now();
        LocalDate nextWednesday = today.with(DayOfWeek.WEDNESDAY);
        LocalDate nextSunday = today.with(DayOfWeek.SUNDAY);

        if (today.getDayOfWeek().getValue() >= DayOfWeek.WEDNESDAY.getValue()) {
            nextWednesday = nextWednesday.plusWeeks(1);
        }

        if (today.getDayOfWeek().getValue() >= DayOfWeek.SUNDAY.getValue()) {
            nextSunday = nextSunday.plusWeeks(1);
        }

        return nextWednesday.isBefore(nextSunday) ? nextWednesday : nextSunday;
    }
}