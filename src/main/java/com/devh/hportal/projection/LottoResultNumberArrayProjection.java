package com.devh.hportal.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * <pre>
 * Description :
 *     LottoResult 엔티티에서 숫자를 concat한 컬럼 사영 인터페이스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/04/01
 * </pre>
 */
public interface LottoResultNumberArrayProjection {
    @Value("#{target.turn}")
    String getTurn();
    @Value("#{target.number1+','+target.number2+','+target.number3+','+target.number4+','+target.number5+','+target.number6+','+target.number7}")
    String getAllNumber();
}
