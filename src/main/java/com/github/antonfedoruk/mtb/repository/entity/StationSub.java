package com.github.antonfedoruk.mtb.repository.entity;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.StationStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Monitoring Subscription entity.
 */

@Data
@Entity
@Table(name = "station_sub")
@EqualsAndHashCode(exclude = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationSub {
    @Id
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "last_status", columnDefinition = "ENUM('ONLINE', 'OFFLINE', 'CHARGING', 'ERROR') NOT NULL")
    //  @Enumerated(EnumType.STRING) - to declare that its value should be converted from what is effectively a String
    // (JPA will use the Enum.name() value when storing an entity) in the database to the StationStatus type.
    @Enumerated(EnumType.STRING)
    StationStatus lastStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "station_x_user",
            joinColumns = @JoinColumn(name = "station_sub_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<TelegramUser> users;

    public void addUser(TelegramUser user) {
        if (isNull(users)) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
