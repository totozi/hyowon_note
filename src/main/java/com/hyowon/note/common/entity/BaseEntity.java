package com.hyowon.note.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "REG_DATETIME", updatable = false, nullable = false)
    private LocalDateTime regDatetime;

    @LastModifiedDate
    @Column(name ="UPD_DATETIME", updatable = true, nullable = true)
    private LocalDateTime updDatetime;

}
