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
public class Timer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    //어느방의 시간인지 확인
    @Column(name = "roomId", nullable = false)
    private String roomId;
    //어느 유저의 시간인지 확인
    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(columnDefinition = "0")
    private String stTime;  // 시작 시간
    @Column(columnDefinition = "0")
    private String edTime;  // 정지 시간

    public void updateStTime(String stTime){
        this.stTime = stTime;
    }

    public void updateEdTime(String edTime){
        this.edTime = edTime;
    }

}

