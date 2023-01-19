package com.github.antonfedoruk.mtb.job;

import com.github.antonfedoruk.mtb.service.CheckStationStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Job for updating statuses of stations.
 */
@Slf4j
@Component
public class CheckStationStatusesJob {
    private final CheckStationStatusService checkStationStatusService;

    @Autowired
    public CheckStationStatusesJob(CheckStationStatusService checkStationStatusService) {
        this.checkStationStatusService = checkStationStatusService;
    }

    @Scheduled(fixedRateString = "${job.recountStationsCheckFixedRate}")
    public void checkStationStatuses() {
        LocalDateTime start = LocalDateTime.now();

        log.info("Check station statuses job started.");

        checkStationStatusService.checkStationStatuses();

        LocalDateTime end = LocalDateTime.now();

        log.info("Check station statuses job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
