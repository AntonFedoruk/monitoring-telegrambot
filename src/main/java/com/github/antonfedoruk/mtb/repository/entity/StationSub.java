package com.github.antonfedoruk.mtb.repository.entity;

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
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationSub {
    @Id
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "last_update_id")
    Integer lastUpdateId;

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
