package com.github.antonfedoruk.mtb.service;

/**
 * Service for checking station statuses.
 */
public interface CheckStationStatusService {
    /**
     * Check station statuses and notify subscribers about it.
     */
    void checkStationStatuses();
}
