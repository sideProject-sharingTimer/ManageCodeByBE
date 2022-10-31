package com.sideproject.sharingtimer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String roomId; // UUID로 받을 예정



}
