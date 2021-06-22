package com.devh.hportal.controller.schedule;

import com.devh.hportal.result.ResultToJsonConverter;
import com.devh.hportal.scheduler.lotto.LottoScheduler;
import com.devh.hportal.scheduler.lotto.constant.LottoScheduleConstant;
import com.devh.hportal.scheduler.news.YonhapNewsScheduler;
import com.devh.hportal.scheduler.news.constant.NewsScheduleConstant;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequestMapping("api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final LottoScheduler lottoScheduler;
    private final YonhapNewsScheduler yonhapNewsScheduler;

    @GetMapping("start")
    @ApiOperation(value = "데이터 수집 스케쥴러 즉시 시작", tags = "schedule")
    public ResponseEntity<Object> getStartSchedule() {
        ResultToJsonConverter resultToJsonConverter = ResultToJsonConverter.init();

        lottoScheduler.scheduleStart();
        yonhapNewsScheduler.scheduleStart();

        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("lottoScheduleStatus", getLottoSchedulerStatusMap());
        statusMap.put("newsScheduleStatus", getNewsSchedulerStatusMap());
        resultToJsonConverter.putResultMapToResultJson(statusMap);

        return ResponseEntity.ok().body(resultToJsonConverter.get());
    }

    private Map<LottoScheduleConstant.Status, LottoScheduleConstant.Status> getLottoSchedulerStatusMap() {
        Map<LottoScheduleConstant.Status, LottoScheduleConstant.Status> statusMap = new HashMap<>();
        statusMap.put(LottoScheduleConstant.Status.CURRENT_STATUS, lottoScheduler.getSchedulerStatus());
        return statusMap;
    }
    private Map<NewsScheduleConstant.Status, NewsScheduleConstant.Status> getNewsSchedulerStatusMap() {
        Map<NewsScheduleConstant.Status, NewsScheduleConstant.Status> statusMap = new HashMap<>();
        statusMap.put(NewsScheduleConstant.Status.CURRENT_STATUS, yonhapNewsScheduler.getSchedulerStatus());
        return statusMap;
    }
}
