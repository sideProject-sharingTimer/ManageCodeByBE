package com.sideproject.sharingtimer.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private Long kakaoId;

    //카카오 유저정보를 받을 생성자 ,
    public User(String username, String password, String email,Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.kakaoId = kakaoId;

    }


}
