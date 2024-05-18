package com.k4noise.pinspire.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "pin_like")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "pin_id")
    PinEntity pin;

    public static LikeEntity create(UserEntity user, PinEntity pin) {
        LikeEntity like = new LikeEntity();
        like.setUser(user);
        like.setPin(pin);
        return like;
    }
}