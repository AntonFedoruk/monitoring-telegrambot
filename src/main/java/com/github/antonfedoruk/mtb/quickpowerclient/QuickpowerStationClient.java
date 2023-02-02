package com.github.antonfedoruk.mtb.quickpowerclient;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;

import java.util.List;
import java.util.NoSuchElementException;

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
    Station getStationByIdOrThrowNoSuchElementException (Integer id) throws NoSuchElementException;
}