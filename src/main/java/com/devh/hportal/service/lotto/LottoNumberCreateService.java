package com.devh.hportal.service.lotto;

import com.devh.hportal.dto.lotto.LottoNumberCreateDTO;
import com.devh.hportal.util.ExceptionUtils;

import java.security.DrbgParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface LottoNumberCreateService {
    LottoNumberCreateDTO setLottoNumberList(LottoNumberCreateDTO lottoNumberCreateDTO);

    default void setNumberList(LottoNumberCreateDTO lottoNumberCreateDTO) {
        List<int[]> resultList = new ArrayList<>();
        for(int i = 0 ; i < lottoNumberCreateDTO.getGameCount() ; ++i)
            resultList.add(createLottoNumber(lottoNumberCreateDTO));

        lottoNumberCreateDTO.setResultList(resultList);
    }

    default int[] createLottoNumber(LottoNumberCreateDTO lottoNumberCreateDTO) {
        final int numberCount = lottoNumberCreateDTO.getNumberCount();
        final int endNumber = lottoNumberCreateDTO.getEndNumber();
        final int startNumber = lottoNumberCreateDTO.getStartNumber();

        final int randomBound = endNumber - startNumber + 1;

        int[] result = new int[numberCount];
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance("DRBG",
                    DrbgParameters.instantiation(128, DrbgParameters.Capability.RESEED_ONLY, null));
        } catch (NoSuchAlgorithmException ignored) {secureRandom = new SecureRandom();}
        int pushCount = 0;
        while(pushCount < numberCount) {
            int randomNumber = secureRandom.nextInt(randomBound) + startNumber;

            /* check duplicate */
            boolean isDuplicate = false;
            if(pushCount > 0) {
                for(int value : result) {
                    if(value == randomNumber)
                        isDuplicate = true;
                }
            }
            if(isDuplicate)
                continue;

            result[pushCount] = randomNumber;
            ++pushCount;
        }

        Arrays.sort(result);

        return result;
    }

    default void setDefaultNumberList(LottoNumberCreateDTO lottoNumberCreateDTO) {
        lottoNumberCreateDTO.setStartNumber(1);
        lottoNumberCreateDTO.setEndNumber(45);
        lottoNumberCreateDTO.setNumberCount(6);
        lottoNumberCreateDTO.setGameCount(5);
        setNumberList(lottoNumberCreateDTO);
    }
}
