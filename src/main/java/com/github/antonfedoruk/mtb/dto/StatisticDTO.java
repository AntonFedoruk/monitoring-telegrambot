package com.github.antonfedoruk.mtb.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO for getting bot statistics.
 */
@Data
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticDTO {
    final  int activeUserCount;
    final int inactiveUserCount;
    final List<StationStatDTO> stationStatDTOs;
    final double averageGroupCountByUser;
}
