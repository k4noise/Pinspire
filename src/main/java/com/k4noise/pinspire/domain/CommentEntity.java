package com.k4noise.pinspire.domain;

import com.k4noise.pinspire.adapter.web.dto.request.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "pin_id")
    PinEntity pin;

    LocalDateTime createdAt;

    public static CommentEntity create(CommentRequestDto commentDto, UserEntity user, PinEntity pin) {
        CommentEntity comment = new CommentEntity();
        comment.setText(commentDto.text());
        comment.setUser(user);
        comment.setPin(pin);
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }
}
