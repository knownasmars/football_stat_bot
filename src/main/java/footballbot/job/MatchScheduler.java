package footballbot.job;

import footballbot.service.ChatIdService;
import footballbot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MatchScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MatchScheduler.class);
    private static final LocalTime MATCH_TIME = LocalTime.of(19, 0);

    private final MessageService messageService;
    private final ChatIdService chatIdService;

    @Scheduled(fixedRate = 30000)
    public void scheduleDailyJob() {
        logger.warn("Starting daily match reminder job");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMatchDateTime = getNextMatchDateTime(now);

        String formattedDate = nextMatchDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' HH:mm"));
        String question = "Скоро намечается матч: " + formattedDate + ". Вы собираетесь играть?";
        List<String> options = Arrays.asList("Играю!", "Пропускаю");

        Set<String> chatIds = chatIdService.getChatIds();
        for (String chatId : chatIds) {
            messageService.sendPoll(chatId, question, options);
        }
    }

    private LocalDateTime getNextMatchDateTime(LocalDateTime now) {
        LocalDateTime nextWednesday = now.with(DayOfWeek.WEDNESDAY).withHour(MATCH_TIME.getHour()).withMinute(MATCH_TIME.getMinute());
        LocalDateTime nextSunday = now.with(DayOfWeek.SUNDAY).withHour(MATCH_TIME.getHour()).withMinute(MATCH_TIME.getMinute());

        if (now.getDayOfWeek().getValue() >= DayOfWeek.WEDNESDAY.getValue() && now.toLocalTime().isAfter(MATCH_TIME)) {
            nextWednesday = nextWednesday.plusWeeks(1);
        }

        if (now.getDayOfWeek().getValue() >= DayOfWeek.SUNDAY.getValue() && now.toLocalTime().isAfter(MATCH_TIME)) {
            nextSunday = nextSunday.plusWeeks(1);
        }

        return nextWednesday.isBefore(nextSunday) ? nextWednesday : nextSunday;
    }
}