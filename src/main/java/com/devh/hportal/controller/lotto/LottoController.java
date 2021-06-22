package com.devh.hportal.controller.lotto;

import com.devh.hportal.dto.lotto.LottoNumberCreateDTO;
import com.devh.hportal.result.ResultToJsonConverter;
import com.devh.hportal.scheduler.lotto.LottoScheduler;
import com.devh.hportal.scheduler.lotto.constant.LottoScheduleConstant;
import com.devh.hportal.service.lotto.LottoNumberCreateService;
import com.devh.hportal.service.lotto.LottoResultService;
import com.devh.hportal.service.lotto.LottoResultStoreService;
import com.devh.hportal.util.ExceptionUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/lotto")
@RequiredArgsConstructor
public class LottoController {
    private final Logger logger = LoggerFactory.getLogger(LottoController.class);

    /* DI */
    private final LottoScheduler lottoScheduler;
    private final LottoResultStoreService lottoResultStoreService;
    private final LottoResultService lottoResultService;
    private final LottoNumberCreateService lottoNumberCreateService;

    @GetMapping("number-count-list")
    @ApiOperation(value = "번호별 출현 횟수", tags = "lotto")
    public ResponseEntity<Object> getAllNumberList() {
        logger.info("[GET] /api/lotto/number-count-list");
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultService.getLottoNumberCountDTO());
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @GetMapping("number-create")
    @ApiOperation(value = "번호 생성", tags = "lotto")
    public ResponseEntity<Object> getNumberCreate(LottoNumberCreateDTO lottoNumberCreateDTO) {
        logger.info("[GET] /api/lotto/number-create " + lottoNumberCreateDTO);
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoNumberCreateService.setLottoNumberList(lottoNumberCreateDTO));
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("aggregation/store-address")
    @ApiOperation(value = "판매점 집계", tags = "lotto")
    public ResponseEntity<Object> postAggregationStoreAddress() {
        logger.info("[GET] /api/lotto/aggregation/store-address");
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getTotalAggregationStoreAddressMap());
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;

    }

    @GetMapping("store-information/address1")
    @ApiOperation(value = "행정구역1 판매점 집계", tags = "lotto")
    public ResponseEntity<Object> getStoreInformationAboutAddress1() {
        logger.info("[GET] /api/lotto/store-information/address1");
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getAddress1StoreCountMapList());
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @GetMapping("store-information/address2/{address1}")
    @ApiOperation(value = "행정구역1의 행정구역2별 집계", tags = "lotto")
    public ResponseEntity<Object> getStoreInformationAboutAddress2(@PathVariable("address1") String address1) {
        logger.info("[GET] /api/lotto/store-information/address2/"+address1);
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getAddress2StoreCountMapList(address1));
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @GetMapping("store-information/address3/{address1}/{address2}")
    @ApiOperation(value = "행정구역1의 행정구역2에 해당하는 행정구역3별 집계 ", tags = "lotto")
    public ResponseEntity<Object> getStoreInformationAboutAddress3(@PathVariable("address1") String address1, @PathVariable("address2") String address2) {
        logger.info("[GET] /api/lotto/store-information/address3/"+address1+"/"+address2);
        ResponseEntity<Object> result;
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();
        try {
            resultToJsonConverter.putResultMapToResultJson(lottoResultStoreService.getAddress3StoreDTOList(address1, address2));
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.OK);
        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            resultToJsonConverter.putResultMapToExceptionInformation(e);
            result = new ResponseEntity<>(resultToJsonConverter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }
}
