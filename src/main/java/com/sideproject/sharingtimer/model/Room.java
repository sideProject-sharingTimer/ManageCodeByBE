package com.sideproject.sharingtimer.model;

import com.sideproject.sharingtimer.util.exception.CustomException;
import com.sideproject.sharingtimer.util.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private int limitCnt;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private List<User> userList = new ArrayList<>();

    public void updateUserList(User user){
        this.getUserList().add((user));
    }

    public void checkLimitCnt(int userCnt){
        if(this.limitCnt > userCnt){
            throw new CustomException(ErrorCode.OVER_LIMIT_COUNT);
        }
    }

}
