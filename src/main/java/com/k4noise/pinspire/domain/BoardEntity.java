package com.k4noise.pinspire.domain;

import com.k4noise.pinspire.adapter.web.dto.request.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Entity
@Data
@Table(name = "board")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    List<PinEntity> pins;

    public static BoardEntity create(BoardRequestDto boardDto, UserEntity user) {
        BoardEntity board = new BoardEntity();
        board.setName(boardDto.name());
        board.setDescription(boardDto.description());
        board.setUser(user);
        board.setPins(Collections.emptyList());
        return board;
    }
}