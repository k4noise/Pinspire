package com.k4noise.pinspire.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
}