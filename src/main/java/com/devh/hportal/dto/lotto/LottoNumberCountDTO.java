package com.devh.hportal.dto.lotto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <pre>
 * Description :
 *     숫자별 카운트 맵을 갖는 DTO
 * ===============================================
 * Member fields :
 *     Map<Integer, Integer> numberCountData
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/04/01
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LottoNumberCountDTO {
    Map<Integer, Integer> numberCountData;
}
