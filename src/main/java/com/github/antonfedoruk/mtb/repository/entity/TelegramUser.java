package com.github.antonfedoruk.mtb.repository.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

/**
 * Telegram User entity.
 */
@Data
@Entity
@Table(name = "tg_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramUser {
    @Id
    @Column(name = "chat_id")
    String chatId;

    @Column(name = "active")
    boolean active;

    //Это поле использует джоины, написанные в StationSub сущности.
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    List<StationSub> stationSubs;
}
