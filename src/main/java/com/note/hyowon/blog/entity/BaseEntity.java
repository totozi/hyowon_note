package com.note.hyowon.blog.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
abstract class BaseEntity {

    @LastModifiedDate
    @Column(name = "LAST_CHG_DATETIME")
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime lastChgDateTime;

//    @LastModifiedBy
//    @Column(length = 50, name = "LAST_CHG_USER_ID")
//    private String LAST_CHG_USER_ID;
//
//    @LastModifiedBy
//    @Column(length = 50, name = "LAST_CHG_IP")
//    private String LAST_CHG_IP;

}
