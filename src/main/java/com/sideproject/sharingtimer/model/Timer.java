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
    @Column
    private String stTime;  // 최초 시작 시간 (년/일/월/시)
    @Column
    private String utTime;  // 업데이트 (재시작 및 정지 시간)
    @Column
    private String asTime;  // 누적된 시간
    @Column
    private String status;  // 시간 동작 상태

    public void recordStTime(String stTime){
        this.stTime = stTime;
    }

    public void updateUtTime(String edTime){
        this.utTime = edTime;
    }

    public void updateAsTime(String asTime){
        this.asTime = asTime;
    }

    public void updateStatus(String status){
        this.status = status;
    }

}

