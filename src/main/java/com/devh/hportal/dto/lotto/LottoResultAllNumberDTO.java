package com.devh.hportal.dto.lotto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Description :
 *     LottoResult 에서 회차, 모든 숫자 (,로 구분) 갖는 DTO
 * ===============================================
 * Member fields :
 *     private Integer turn
 *     private String allNumber
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-04-01
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LottoResultAllNumberDTO {
    private Integer turn;
    private String allNumber;
}
