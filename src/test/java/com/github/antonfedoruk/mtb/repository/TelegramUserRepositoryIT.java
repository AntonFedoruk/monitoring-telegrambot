package com.github.antonfedoruk.mtb.repository;

import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Integration-level testing for {@link TelegramUserRepository}.
 */
@ActiveProfiles("test") //задаем использование профиля test.
@DataJpaTest //@DataJpaTest при тестировании базы данных использует только нужные нам классы и не трогает другие.
@AutoConfigureTestDatabase(replace = NONE)
class TelegramUserRepositoryIT {
    @Autowired
    private TelegramUserRepository telegramUserRepository;

    // Хорошо бы иметь подготовленную базу данных перед запуском наших тестов. Для этого дела есть такой подход:
    // добавить к тесту @Sql и передать ей коллекцию имен скриптов(они буду лежать по пути ./src/test/resources/ + путь,
    // указанный в аннотации.), которые нужно запустить перед началом теста
    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/addUsers.sql"})
    @Test
    @DisplayName("Should properly find all active users")
    void shouldProperlyFindAllActiveUsers() {
        // when
        List<TelegramUser> allActiveUsers = telegramUserRepository.findAllByActiveTrue();
        // then
        Assertions.assertEquals(5, allActiveUsers.size());
    }

    @Test
    @DisplayName("Should properly save telegram user")
    void shouldProperlySaveTelegramUser() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("88888888");
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);
        // when
        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());
        // then
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(telegramUser, saved.get());
    }
}