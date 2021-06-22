package com.devh.hportal.service.lotto;


import com.devh.hportal.dto.lotto.LottoResultDetailDTO;
import com.devh.hportal.entity.lotto.LottoResult;
import com.devh.hportal.entity.lotto.LottoResultDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description :
 *     LottoResultDetail 엔티티 관련 인테퍼이스
 * ===============================================
 * Member fields :
 *     Nothing
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
public interface LottoResultDetailService {
    /**
     * <pre>
     * Description
     *     회차로 LottoResultDetailDTO 리스트 (순위 1~5) 반환
     * ===============================================
     * Parameters
     *     Integer turn
     * Returns
     *     List<LottoResultDetailDTO>
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    List<LottoResultDetailDTO> getDTOListByTurn(Integer turn);

    /**
     * <pre>
     * Description
     *     순위 1~5의 LottoResultDetailDTO 리스트를 테이블에 insert
     * ===============================================
     * Parameters
     *     List<LottoResultDetailDTO> lottoResultDetailDTOList
     * Returns
     *     Boolean
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    Boolean saveDTOList(List<LottoResultDetailDTO> lottoResultDetailDTOList);

    /**
     * <pre>
     * Description
     *     LottoResultDetailDTO -> LottoResultDetail 변환
     *     lottoResult의 경우 DTO에서 turn 정보를 가져와 turn만 세팅하여 만들면,
     *     쿼리 수행 시 해당 값으로만 외래키 참조된 정보를 사용하므로 상관이 없다.
     * ===============================================
     * Parameters
     *     LottoResultDetailDTO lottoResultDetailDTO
     * Returns
     *     LottoResultDetail
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    default LottoResultDetail dtoToEntity(LottoResultDetailDTO lottoResultDetailDTO) {
        return LottoResultDetail.builder()
                .rank(lottoResultDetailDTO.getRank())
                .totalPrize(lottoResultDetailDTO.getTotalPrize())
                .perPersonPrize(lottoResultDetailDTO.getPerPersonPrize())
                .totalWinnerCount(lottoResultDetailDTO.getTotalWinnerCount())
                .lottoResult(LottoResult.builder().turn(lottoResultDetailDTO.getTurn()).build())
                .build();
    }

    /**
     * <pre>
     * Description
     *     LottoResultDetail -> LottoResultDetailDTO 변환
     * ===============================================
     * Parameters
     *     LottoResultDetail lottoResultDetail
     * Returns
     *     LottoResultDetailDTO
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    default LottoResultDetailDTO entityToDto(LottoResultDetail lottoResultDetail) {
        return LottoResultDetailDTO.builder()
                .rank(lottoResultDetail.getRank())
                .totalPrize(lottoResultDetail.getTotalPrize())
                .perPersonPrize(lottoResultDetail.getPerPersonPrize())
                .totalWinnerCount(lottoResultDetail.getTotalWinnerCount())
                .turn(lottoResultDetail.getLottoResult().getTurn())
                .build();

    }

    /**
     * <pre>
     * Description
     *     순위 1~5의 LottoResultDetailDTO 리스트를 LottoResultDetail 리스트로 변환
     *     동일 인터페이스 내의 dtoToEntity 이용
     * ===============================================
     * Parameters
     *     List<LottoResultDetailDTO> lottoResultDetailDTOList
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
    default List<LottoResultDetail> dtoListToEntityList(List<LottoResultDetailDTO> lottoResultDetailDTOList) {
        List<LottoResultDetail> lottoResultDetailList = new ArrayList<>();

        lottoResultDetailDTOList.forEach(lottoResultDetailDTO -> {
            lottoResultDetailList.add(dtoToEntity(lottoResultDetailDTO));
        });

        return lottoResultDetailList;
    }

    /**
     * <pre>
     * Description
     *     순위 1~5의 LottoResultDetail 리스트를 LottoResultDetailDTO 리스트로 변환
     *     동일 인터페이스 내의 entityToDto 이용
     * ===============================================
     * Parameters
     *     List<LottoResultDetail> lottoResultDetailList
     * Returns
     *     List<LottoResultDetailDTO>
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-28
     * </pre>
     */
    default List<LottoResultDetailDTO> entityListToDtoList(List<LottoResultDetail> lottoResultDetailList) {
        List<LottoResultDetailDTO> lottoResultDetailDTOList = new ArrayList<>();

        lottoResultDetailList.forEach(lottoResultDetail -> {
            lottoResultDetailDTOList.add(entityToDto(lottoResultDetail));
        });

        return lottoResultDetailDTOList;
    }
}
