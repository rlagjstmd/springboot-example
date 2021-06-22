package com.devh.hportal.repository.lotto;

import com.devh.hportal.entity.lotto.LottoResultStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LottoResultStoreRepository extends ElasticsearchRepository<LottoResultStore, String> {
}
