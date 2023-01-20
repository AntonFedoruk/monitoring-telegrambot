package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.github.antonfedoruk.mtb.command.AbstractCommandTest.prepareUpdate;
import static com.github.antonfedoruk.mtb.command.CommandName.DELETE_STATION_SUB;

@DisplayName("Unit-level testing for DeleteStationSubCommand")
class DeleteStationSubCommandTest {
    private Command command;
    private SendBotMessageService sendBotMessageService;
    TelegramUserService telegramUserService;
    StationSubService stationSubService;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        stationSubService = Mockito.mock(StationSubService.class);

        command = new DeleteStationSubCommand(sendBotMessageService, telegramUserService, stationSubService);
    }

    @Test
    @DisplayName("Should properly return empty subscription list")
    void shouldProperlyReturnEmptySubscriptionList() {
        // given
        Long chatId = 123L;
        Update update = prepareUpdate(chatId, DELETE_STATION_SUB.getCommandName());

        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(new TelegramUser()));

        String expectedMessage = "Поки відсутній моніторинг станцій. Щоб почати відслідковування станцій скористайся /addstationsub";
        // when
        command.execute(update);
        // then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    @DisplayName("Should properly return subscription list")
    void shouldProperlyReturnSubscriptionList() {
        // given
        Long chatId = 123L;
        Update update = prepareUpdate(chatId, DELETE_STATION_SUB.getCommandName());
        TelegramUser telegramUser = new TelegramUser();
        StationSub ss1 = new StationSub();
        ss1.setId(111);
        ss1.setTitle("SS1 title");
        telegramUser.setStationSubs(Collections.singletonList(ss1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = "Для видалення моніторингу станції - передай комадну разом з ID станції.\n" +
                "Наприклад: /deletestationsub 777 \n\n" +
                "Ось список всіх доступних станцій:) \n\n" +
                "'назва станції' - 'ID_станції' \n\n" +
                "SS1 title - 111 \n";
        // when
        command.execute(update);
        // then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    @DisplayName("Should reject by invalid station id")
    void shouldRejectByInvalidStationId() {
        // given
        Long chatId = 123L;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_STATION_SUB.getCommandName(), "groupSubId"));
        TelegramUser telegramUser = new TelegramUser();
        StationSub ss1 = new StationSub();
        ss1.setId(111);
        ss1.setTitle("SS1 title");
        telegramUser.setStationSubs(Collections.singletonList(ss1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = "Невірний формат ID станції.\n" +
                "ID має бути цілим додатнім числом";
        // when
        command.execute(update);
        // then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }
    
    @Test
    @DisplayName("Should properly delete by station id")
    void shouldProperlyDeleteByStationId() {
        // given
        Long chatId = 23456L;
        Integer stationId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_STATION_SUB.getCommandName(), stationId));

        StationSub ss1 = new StationSub();
        ss1.setId(111);
        ss1.setTitle("SS1 title");
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        telegramUser.setStationSubs(Collections.singletonList(ss1));
        ArrayList<TelegramUser> users = new ArrayList<>();
        users.add(telegramUser);
        ss1.setUsers(users);
        Mockito.when(stationSubService.findById(stationId)).thenReturn(Optional.of(ss1));
        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));

        String expectedMessage = "Скасовано підписку на станцію: SS1 title";
        // when
        command.execute(update);
        // then
        users.remove(telegramUser);
        Mockito.verify(stationSubService).save(ss1);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    @DisplayName("Should do not exist by station ID")
    void shouldDoNotExistByStationId() {
        // given
        Long chatId = 23456L;
        Integer stationId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_STATION_SUB.getCommandName(), stationId));

        Mockito.when(stationSubService.findById(stationId)).thenReturn(Optional.empty());

        String expectedMessage = "Вказана станція не знайдена =/";
        // when
        command.execute(update);
        // then
        Mockito.verify(stationSubService).findById(stationId);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }
}