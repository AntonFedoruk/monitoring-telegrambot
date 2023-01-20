package com.github.antonfedoruk.mtb.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * DTO for showing group id and title without data.
 */
@Data
@EqualsAndHashCode(exclude = {"title", "activeUserCount"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationStatDTO {
    final Integer id;
    final String title;
    final Integer activeUserCount;
}
