package com.note.hyowon.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

/**
 * Class        : CommonDTO
 * Desc         : 모든 DTO의 조상, 로그용 컬럼값들과 매핑
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonDTO {

    private LocalDateTime lastChgDateTime;

    // TODO 구현 예정 어노테이션 (토큰 사용)
//    @LastModifiedBy
//    @CreatedBy
//    private String LAST_CHG_USER_ID;
//
//    private String LAST_CHG_IP;

}
