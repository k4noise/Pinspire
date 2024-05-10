package com.k4noise.pinspire.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Data
@Table(name = "app_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(unique = true)
    String username;

    @Column(unique = true)
    String email;

    String password;
    String displayName;
    String avatarUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<BoardEntity> boards;

    public static UserEntity create(String username, String email, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setDisplayName(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatarUrl("/default.png");
        return user;
    }
}
