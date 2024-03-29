package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * {@link Service} for handling {@link TelegramUser} entity.
 */
public interface TelegramUserService {

    /**
     * Save provided {@link TelegramUser} entity.
     *
     * @param telegramUser provided telegram user.
     */
    void save(TelegramUser telegramUser);

    /**
     * Retrieve all active {@link TelegramUser}.
     *
     * @return the collection of the active {@link TelegramUser} objects.
     */
    List<TelegramUser> findAllActiveUsers();

    /**
     * Find {@link TelegramUser} by chatId.
     *
     * @param chatId provided by Chat ID
     * @return {@link TelegramUser} with provided chat ID or null otherwise.
     */
    Optional<TelegramUser> findByChatId(Long chatId);

    /**
     * Retrieve all inactive {@link TelegramUser}
     *
     * @return the collection of the inactive {@link TelegramUser} objects.
     */
    List<TelegramUser> findAllInActiveUsers();
}
