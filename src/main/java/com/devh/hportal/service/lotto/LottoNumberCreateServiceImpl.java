package com.devh.hportal.service.lotto;

import com.devh.hportal.constant.lotto.CreateType;
import com.devh.hportal.dto.lotto.LottoNumberCreateDTO;
import com.devh.hportal.util.ExceptionUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * Description :
 *     LottoNumberCreateService 구현체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/06/04
 * </pre>
 */
@Service
@Log4j2
public class LottoNumberCreateServiceImpl implements LottoNumberCreateService {

    @Override
    public LottoNumberCreateDTO setLottoNumberList(LottoNumberCreateDTO lottoNumberCreateDTO) {

        final CreateType createType = lottoNumberCreateDTO.getCreateType();
        if(CreateType.DEFAULT.equals(createType))
            setDefaultNumberList(lottoNumberCreateDTO);
        else if(CreateType.RANDOM.equals(createType))
            setNumberList(lottoNumberCreateDTO);

        return lottoNumberCreateDTO;
    }
}
