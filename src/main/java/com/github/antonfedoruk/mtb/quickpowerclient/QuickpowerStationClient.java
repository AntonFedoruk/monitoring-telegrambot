package com.github.antonfedoruk.mtb.quickpowerclient;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;

import java.util.List;

/**
 * Quickpower client corresponds to stations.
 */
public interface QuickpowerStationClient {

    /**
     * Get all the {@link Station}.
     *
     * @return the collection of the {@link Station} objects.
     */
    List<Station> getStationList();

    /**
     * Get {@link Station} by provided ID.
     *
     * @param id provided ID.
     * @return {@link Station} object.
     */
    Station getStationById(Integer id);
}