package com.k4noise.pinspire.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "pins")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String description;
    String imageUrl;
    LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    BoardEntity board;

    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    List<CommentEntity> comments;

    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    List<LikeEntity> likes;
}