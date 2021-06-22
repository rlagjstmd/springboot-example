package com.devh.hportal.repository.lotto;

import com.devh.hportal.entity.lotto.LottoResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <pre>
 * Description :
 *     LottoResultRepository 엔티티와 대응되는 쿼리 수행 인터페이스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
public interface LottoResultDetailRepository extends JpaRepository<LottoResultDetail, Long> {
    /**
     * <pre>
     * Description
     *     회차로 LottoResultDetail 리스트 (순위 1~5) 조회
     * ===============================================
     * Parameters
     *     Integer turn
     * Returns
     *     List<LottoResultDetail>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    @Query("select lottoResultDetail from LottoResultDetail lottoResultDetail where lottoResultDetail.lottoResult.turn =:turn")
    List<LottoResultDetail> getLottoResultDetailListByTurn(Integer turn);
}
