package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.dto.StatisticDTO;

/**
 * Service for getting bot statistics.
 */
public interface StatisticService {
    StatisticDTO countBotStatistic();
}
