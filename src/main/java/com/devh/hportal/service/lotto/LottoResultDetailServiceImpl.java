package com.devh.hportal.service.lotto;

import com.devh.hportal.dto.lotto.LottoResultDetailDTO;
import com.devh.hportal.entity.lotto.LottoResultDetail;
import com.devh.hportal.repository.lotto.LottoResultDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Description :
 *     LottoResultDetailService 구현체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class LottoResultDetailServiceImpl implements LottoResultDetailService {
    /* DI */
    private final LottoResultDetailRepository lottoResultDetailRepository;

    @Override
    public List<LottoResultDetailDTO> getDTOListByTurn(Integer turn) {
        List<LottoResultDetail> lottoResultDetailList = lottoResultDetailRepository.getLottoResultDetailListByTurn(turn);
        return entityListToDtoList(lottoResultDetailList);
    }

    @Override
    public Boolean saveDTOList(List<LottoResultDetailDTO> lottoResultDetailDTOList) {
        List<LottoResultDetail> lottoResultDetailList = dtoListToEntityList(lottoResultDetailDTOList);
        List<LottoResultDetail> saveResultList = lottoResultDetailRepository.saveAll(lottoResultDetailList);
        return saveResultList.size() == lottoResultDetailDTOList.size();
    }
}
