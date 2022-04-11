package com.github.antonfedoruk.mtb.repository;

import com.github.antonfedoruk.mtb.repository.entity.StationSub;
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
 * Integration-level testing for {@link StationSubRepository}.
 */
@ActiveProfiles("test") //задаем использование профиля test.
@DataJpaTest //@DataJpaTest при тестировании базы данных использует только нужные нам классы и не трогает другие.
@AutoConfigureTestDatabase(replace = NONE)
class StationSubRepositoryIT {

    @Autowired
    private StationSubRepository stationSubRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveUsersForMonitoringSub.sql"})
    @Test
    @DisplayName("Should properly get all monitoring subscriptions for user")
    void shouldProperlyGetAllMonitoringSubscriptionsForUser() {
        // when
        Optional<StationSub> monitoringSubFromDB = stationSubRepository.findById(1);

        // then
        Assertions.assertTrue(monitoringSubFromDB.isPresent());
        Assertions.assertEquals(1, monitoringSubFromDB.get().getId());
        List<TelegramUser> users = monitoringSubFromDB.get().getUsers();
        for (int i = 0; i < users.size(); i++) {
            Assertions.assertEquals(String.valueOf(i + 1), users.get(i).getChatId());
            Assertions.assertTrue(users.get(i).isActive());
        }
    }
}