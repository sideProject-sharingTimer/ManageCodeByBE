package com.sideproject.sharingtimer.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //상속받는 자식 클래스에게 부모의 멤버를 컬럼으로 인식하도록한다.
@EntityListeners(AuditingEntityListener.class) // 변동사항에 대한 업데이트
@Getter
public class TimeStamped {

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
