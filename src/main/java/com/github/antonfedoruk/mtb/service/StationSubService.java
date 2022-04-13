package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;

import java.util.Optional;

/**
 * Service for manipulating with {@link StationSub}.
 */
public interface StationSubService {

    StationSub save(String chatId, Station station);

    StationSub save(StationSub stationSub);

    Optional<StationSub> findById(Integer id);
}
