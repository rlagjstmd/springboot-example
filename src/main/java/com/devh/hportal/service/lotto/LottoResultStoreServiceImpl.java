package com.devh.hportal.service.lotto;

import com.devh.hportal.constant.lotto.LottoIndex;
import com.devh.hportal.constant.lotto.NewsResultStoreField;
import com.devh.hportal.dto.lotto.LottoResultStoreDTO;
import com.devh.hportal.entity.lotto.LottoResultStore;
import com.devh.hportal.util.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LottoResultStoreServiceImpl implements LottoResultStoreService {
    /* DI */
    private final RestHighLevelClient restHighLevelClient;
    private final ElasticsearchOperations elasticsearchOperations;

    private final Logger logger = LoggerFactory.getLogger(LottoResultStoreServiceImpl.class);

    @Override
    public boolean bulkLottoResultStoreDTOList(List<LottoResultStoreDTO> lottoResultStoreDTOList) {
//        final String indexName = "micro_lotto_store";
        boolean result = false;

        try {
            BulkRequest bulkRequest = new BulkRequest();

            lottoResultStoreDTOList.forEach(lottoResultStoreDTO -> {
//                logger.info(lottoResultStoreDTO.toString());
                IndexRequest indexRequest = new IndexRequest(LottoIndex.INDEX_NAME_PRE.getValue());
                indexRequest.id(lottoResultStoreDTO.getRowId()).source(lottoResultStoreDTO.getMapForElasticsearch());
                bulkRequest.add(indexRequest);
            });

            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

            if(bulkResponse.hasFailures()) {
                logger.error(bulkResponse.buildFailureMessage());
                result = false;
            } else {
                logger.info(String.format("Bulk Success. [%d docs, %f seconds]",
                        bulkResponse.getItems().length,
                        (bulkResponse.getTook().millis() / (double) 1000)));
                result = true;
            }

        } catch (Exception e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            result = false;
        }

        return result;
    }


    @Override
    public Map<String, Object> getTotalAggregationStoreAddressMap() {
        Map<String, Object> addressMap;

        Query query = new NativeSearchQueryBuilder()
                .addAggregation(
                        AggregationBuilders
                                .terms(ALIAS_ADDRESS1)
                                .field(NewsResultStoreField.STORE_ADDRESS1.getSnakeCase())
                                .order(BucketOrder.key(true))
                                .size(50)
                                .subAggregation(
                                        AggregationBuilders
                                                .terms(ALIAS_ADDRESS2)
                                                .field(NewsResultStoreField.STORE_ADDRESS2.getSnakeCase())
                                                .order(BucketOrder.key(true))
                                                .size(200)
                                                .subAggregation(
                                                        AggregationBuilders
                                                                .terms(ALIAS_ADDRESS3)
                                                                .field(NewsResultStoreField.STORE_ADDRESS3.getSnakeCase())
                                                                .order(BucketOrder.key(true))
                                                                .size(500)
                                                )
                                )
                )
                .build();

        SearchHits<LottoResultStore> searchHits = elasticsearchOperations.search(query, LottoResultStore.class);

        Aggregations addressGroupAggregations = searchHits.getAggregations();
        addressMap = getStoreAddressGroupAggregationFunction().apply(addressGroupAggregations);

        return addressMap;
    }

    @Override
    public List<Map<String, Object>> getAddress1StoreCountMapList() {
        List<Map<String, Object>> addressMapList;

        Query query = new NativeSearchQueryBuilder()
                .addAggregation(
                        AggregationBuilders
                                .terms(ALIAS_ADDRESS1)
                                .field(NewsResultStoreField.STORE_ADDRESS1.getSnakeCase())
                                .order(BucketOrder.key(true))
                                .size(50)
                )
                .build();

        SearchHits<LottoResultStore> searchHits = elasticsearchOperations.search(query, LottoResultStore.class);
        Aggregations addressGroupAggregations = searchHits.getAggregations();
        addressMapList = getStoreAddress1CountAggregationFunction().apply(addressGroupAggregations);

        return addressMapList;
    }

    @Override
    public List<Map<String, Object>> getAddress2StoreCountMapList(String address1) {
        List<Map<String, Object>> addressMapList;
        Query query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders
                                .boolQuery()
                                .must(
                                        QueryBuilders.termQuery(NewsResultStoreField.STORE_ADDRESS1.getSnakeCase(), address1)
                                )
                )
                .addAggregation(
                        AggregationBuilders
                                .terms(ALIAS_ADDRESS2)
                                .field(NewsResultStoreField.STORE_ADDRESS2.getSnakeCase())
                                .order(BucketOrder.key(true))
                                .size(50)
                )
                .build();
        SearchHits<LottoResultStore> searchHits = elasticsearchOperations.search(query, LottoResultStore.class);
        Aggregations addressGroupAggregations = searchHits.getAggregations();
        addressMapList = getStoreAddress2CountAggregationFunction().apply(addressGroupAggregations);
        return addressMapList;
    }

    @Override
    public List<LottoResultStoreDTO> getAddress3StoreDTOList(String address1, String address2) {
        List<LottoResultStoreDTO> addressList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders
                                .boolQuery()
                                .must(
                                        QueryBuilders.termQuery(NewsResultStoreField.STORE_ADDRESS1.getSnakeCase(), address1)
                                )
                                .must(
                                        QueryBuilders.termQuery(NewsResultStoreField.STORE_ADDRESS2.getSnakeCase(), address2)
                                )
                )
                .build();
        SearchHits<LottoResultStore> searchHits = elasticsearchOperations.search(query, LottoResultStore.class);
        for (SearchHit<LottoResultStore> searchHit : searchHits.getSearchHits())
            addressList.add(entityToDto(searchHit.getContent()));

        return addressList;
    }
}
