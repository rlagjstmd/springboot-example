package com.devh.hportal.dto.lotto;

import com.devh.hportal.constant.lotto.CreateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * Description : 
 *     번호 생성 결과를 담는 DTO
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021/06/04
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LottoNumberCreateDTO {
    private CreateType createType;
    private int startNumber;
    private int endNumber;
    private int numberCount;
    private int gameCount;
    private List<int[]> resultList;
}
