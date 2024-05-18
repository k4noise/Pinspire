package com.k4noise.pinspire.domain;

import com.k4noise.pinspire.adapter.web.dto.request.PinRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "pin")
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

    public static PinEntity create(PinRequestDto pinDto, UserEntity user, BoardEntity board) {
        PinEntity pin = new PinEntity();
        pin.setTitle(pinDto.title());
        pin.setDescription(pinDto.description());
        pin.setImageUrl(pinDto.imageUrl());
        pin.setUploadedAt(LocalDateTime.now());
        pin.setUser(user);
        pin.setBoard(board);
        return pin;
    }
}