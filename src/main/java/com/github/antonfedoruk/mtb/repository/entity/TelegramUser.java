package com.github.antonfedoruk.mtb.repository.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

/**
 * Telegram User entity.
 */
@Data
@Entity
@Table(name = "tg_user")
@EqualsAndHashCode(exclude ="stationSubs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramUser {
    @Id
    @Column(name = "chat_id")
    Long chatId;

    @Column(name = "active")
    boolean active;

    //Это поле использует джоины, написанные в StationSub сущности.
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    List<StationSub> stationSubs;
}
