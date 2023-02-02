package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.command.annotation.AdminCommand;
import com.github.antonfedoruk.mtb.dto.StatisticDTO;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StatisticService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;

@AdminCommand
public class StatCommand implements Command {
    private final StatisticService statisticService;
    private final SendBotMessageService sendBotMessageService;

    public static String STAT_MESSAGE = "✨<b>Статистика</b>✨\n\n"
                    + " - Кількість активних користувачів: %s\n"
                    + " - Кількість неактивних користувачів: %s\n"
                    + " - середня кількість станцій яка відслідковується: %s\n\n"

                    + "<b>Станції які відслідковуються:</b>\n"
                    + "%s";

    public StatCommand(SendBotMessageService sendBotMessageService, StatisticService statisticService) {
        this.statisticService = statisticService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        StatisticDTO statisticDTO = statisticService.countBotStatistic();

        String collectedStations = statisticDTO.getStationStatDTOs().stream()
                .map(stationStatDTO -> String.format("%s (id = %s) - %s відслідковувань",
                        stationStatDTO.getTitle(),
                        stationStatDTO.getId(),
                        stationStatDTO.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(chatId, String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collectedStations));
    }
}