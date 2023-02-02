package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.antonfedoruk.mtb.command.CommandName.LIST_STATION_SUB;

@DisplayName("Unit-level testing for ListStationSubCommand")
class ListStationSubCommandTest {

    @Test
    @DisplayName("Should properly shows list StationSub")
    void shouldProperlyShowsListStationSub() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(1L);
        telegramUser.setActive(true);

        List<StationSub> stationSubsList = new ArrayList<>();
        stationSubsList.add(populateStationSub(1, "stsub1"));
        stationSubsList.add(populateStationSub(2, "stsub2"));
        stationSubsList.add(populateStationSub(3, "stsub3"));
        stationSubsList.add(populateStationSub(4, "stsub4"));
        stationSubsList.add(populateStationSub(5, "stsub5"));

        telegramUser.setStationSubs(stationSubsList);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(Optional.of(telegramUser));

        ListStationSubCommand command = new ListStationSubCommand(sendBotMessageService, telegramUserService);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(telegramUser.getChatId());
        Mockito.when(message.getText()).thenReturn(LIST_STATION_SUB.getCommandName());
        update.setMessage(message);

        String collectedStations = "Я знайшов всі підписки на моніторинг станції: \n\n" +
                telegramUser.getStationSubs().stream()
                        .map(it -> "Станція: " + it.getTitle() + " , ID = '" + it.getId() + "' \n")
                        .collect(Collectors.joining());
        // when
        command.execute(update);
        // then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), collectedStations);
    }

    private StationSub populateStationSub(Integer id, String title) {
        StationSub stationSub = new StationSub();
        stationSub.setId(id);
        stationSub.setTitle(title);
        return stationSub;
    }
}