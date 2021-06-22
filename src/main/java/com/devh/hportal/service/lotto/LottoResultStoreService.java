package com.devh.hportal.service.lotto;

import com.devh.hportal.dto.lotto.LottoResultStoreDTO;
import com.devh.hportal.entity.lotto.LottoResultStore;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.*;
import java.util.function.Function;

/**
 * <pre>
 * Description :
 *     LottoResultStore 엔티티 관련 인터페이스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/12
 * </pre>
 */
public interface LottoResultStoreService {
    final String ALIAS_ADDRESS1 = "address1";
    final String ALIAS_ADDRESS2 = "address2";
    final String ALIAS_ADDRESS3 = "address3";
    final String ALIAS_TOTAL = "total";

    boolean bulkLottoResultStoreDTOList(List<LottoResultStoreDTO> lottoResultStoreDTOList);
    Map<String, Object> getTotalAggregationStoreAddressMap();
    List<Map<String, Object>> getAddress1StoreCountMapList();
    List<Map<String, Object>> getAddress2StoreCountMapList(String address1);
    List<LottoResultStoreDTO> getAddress3StoreDTOList(String address1, String address2);

    default LottoResultStoreDTO entityToDto(LottoResultStore lottoResultStore) {
        if(lottoResultStore.getStoreLocation() != null)
            return LottoResultStoreDTO.builder()
                    .rowId(lottoResultStore.getRowId())
                    .rank(lottoResultStore.getRank())
                    .method(lottoResultStore.getMethod())
                    .storeNumber(lottoResultStore.getStoreNumber())
                    .storeName(lottoResultStore.getStoreName())
                    .storeAddress(lottoResultStore.getStoreAddress())
                    .storeAddress1(lottoResultStore.getStoreAddress1())
                    .storeAddress2(lottoResultStore.getStoreAddress2())
                    .storeAddress3(lottoResultStore.getStoreAddress3())
                    .storeLatitude(lottoResultStore.getStoreLocation().getLat())
                    .storeLongitude(lottoResultStore.getStoreLocation().getLon())
                    .storePhone(lottoResultStore.getStorePhone())
                    .storeMapId(lottoResultStore.getStoreMapId())
                    .turn(lottoResultStore.getTurn())
                    .build();
        else
            return LottoResultStoreDTO.builder()
                    .rowId(lottoResultStore.getRowId())
                    .rank(lottoResultStore.getRank())
                    .method(lottoResultStore.getMethod())
                    .storeNumber(lottoResultStore.getStoreNumber())
                    .storeName(lottoResultStore.getStoreName())
                    .storeAddress(lottoResultStore.getStoreAddress())
                    .storeAddress1(lottoResultStore.getStoreAddress1())
                    .storeAddress2(lottoResultStore.getStoreAddress2())
                    .storeAddress3(lottoResultStore.getStoreAddress3())
                    .storeLatitude(-9999d)
                    .storeLongitude(-9999d)
                    .storePhone(lottoResultStore.getStorePhone())
                    .storeMapId(lottoResultStore.getStoreMapId())
                    .turn(lottoResultStore.getTurn())
                    .build();
    }

    default LottoResultStore dtoToEntity(LottoResultStoreDTO lottoResultStoreDTO) {
        return LottoResultStore.builder()
                .rowId(lottoResultStoreDTO.getRowId())
                .rank(lottoResultStoreDTO.getRank())
                .method(lottoResultStoreDTO.getMethod())
                .storeNumber(lottoResultStoreDTO.getStoreNumber())
                .storeName(lottoResultStoreDTO.getStoreName())
                .storeAddress(lottoResultStoreDTO.getStoreAddress())
                .storeAddress1(lottoResultStoreDTO.getStoreAddress1())
                .storeAddress2(lottoResultStoreDTO.getStoreAddress2())
                .storeAddress3(lottoResultStoreDTO.getStoreAddress3())
                .storeLocation(new GeoPoint(lottoResultStoreDTO.getStoreLatitude(), lottoResultStoreDTO.getStoreLongitude()))
                .storePhone(lottoResultStoreDTO.getStorePhone())
                .storeMapId(lottoResultStoreDTO.getStoreMapId())
                .turn(lottoResultStoreDTO.getTurn())
                .build();
    }

    default List<LottoResultStoreDTO> entityListToDtoList(List<LottoResultStore> lottoResultStoreList) {

        List<LottoResultStoreDTO> lottoResultStoreDTOList = new ArrayList<>();

        lottoResultStoreList.forEach(lottoResultStore -> {
            lottoResultStoreDTOList.add(entityToDto(lottoResultStore));
        });

        return lottoResultStoreDTOList;
    }

    default List<LottoResultStore> dtoListToEntityList(List<LottoResultStoreDTO> lottoResultStoreDTOList) {

        List<LottoResultStore> lottoResultStoreESList = new ArrayList<>();

        lottoResultStoreDTOList.forEach(lottoResultStoreDTO -> {
            lottoResultStoreESList.add(dtoToEntity(lottoResultStoreDTO));
        });

        return lottoResultStoreESList;
    }


    default Function<Aggregations, Map<String, Object>> getStoreAddressGroupAggregationFunction() {
        return aggregations -> {
            if(aggregations != null) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                putResultMapToEachAddress1Map(aggregations, resultMap);
                return resultMap;
            }
            return Collections.emptyMap();
        };
    }

    default Function<Aggregations, List<Map<String, Object>>> getStoreAddress1CountAggregationFunction() {
        return aggregations -> {
            if(aggregations != null) {
                List<Map<String, Object>> resultList = new ArrayList<>();
                putResultMapToEachAddress1MapList(aggregations, resultList);
                return resultList;
            }
            return Collections.emptyList();
        };
    }

    default Function<Aggregations, List<Map<String, Object>>> getStoreAddress2CountAggregationFunction() {
        return aggregations -> {
            if(aggregations != null) {
                List<Map<String, Object>> resultList = new ArrayList<>();
                putResultMapToEachAddress2MapList(aggregations, resultList);
                return resultList;
            }
            return Collections.emptyList();
        };
    }

    default Function<Aggregations, List<Map<String, Object>>> getStoreAddress3CountAggregationFunction() {
        return aggregations -> {
            if(aggregations != null) {
                List<Map<String, Object>> resultList = new ArrayList<>();
                putResultMapToEachAddress3MapList(aggregations, resultList);
                return resultList;
            }
            return Collections.emptyList();
        };
    }


    private void putResultMapToEachAddress1Map(Aggregations aggregations, Map<String, Object> resultMap) {
        Aggregation address1Aggregation = aggregations.get(ALIAS_ADDRESS1);
        if(address1Aggregation == null)
            return;

        List<? extends Terms.Bucket> address1Buckets = ((Terms) address1Aggregation).getBuckets();
        if(address1Buckets == null)
            return;

        for(Terms.Bucket address1Bucket : address1Buckets) {
            Map<String, Object> eachAddress1Map = new LinkedHashMap<>();
            eachAddress1Map.put(ALIAS_TOTAL, address1Bucket.getDocCount());
            putEachAddress1MapToEachAddress2Map(address1Bucket, eachAddress1Map);
            resultMap.put(address1Bucket.getKeyAsString(), eachAddress1Map);
        }
    }

    private void putResultMapToEachAddress1MapList(Aggregations aggregations, List<Map<String, Object>> resultList) {
        Aggregation address1Aggregation = aggregations.get(ALIAS_ADDRESS1);
        if(address1Aggregation == null)
            return;

        List<? extends Terms.Bucket> address1Buckets = ((Terms) address1Aggregation).getBuckets();
        if(address1Buckets == null)
            return;

        for(Terms.Bucket address1Bucket : address1Buckets) {
            Map<String, Object> eachAddress1Map = new LinkedHashMap<>();
            eachAddress1Map.put(ALIAS_ADDRESS1, address1Bucket.getKeyAsString());
            eachAddress1Map.put(ALIAS_TOTAL, address1Bucket.getDocCount());
            resultList.add(eachAddress1Map);
        }
    }

    private void putEachAddress1MapToEachAddress2Map(Terms.Bucket address1Bucket, Map<String, Object> eachAddress1Map) {
        Aggregation address2Aggregation = address1Bucket.getAggregations().get(ALIAS_ADDRESS2);
        if(address2Aggregation == null)
            return;

        List<? extends Terms.Bucket> address2Buckets = ((Terms) address2Aggregation).getBuckets();
        if(address2Buckets == null)
            return;

        for(Terms.Bucket address2Bucket : address2Buckets) {
            Map<String, Object> eachAddress2Map = new LinkedHashMap<>();
            eachAddress2Map.put(ALIAS_TOTAL, address2Bucket.getDocCount());
            putEachAddress2MapToEachAddress3Map(address2Bucket, eachAddress2Map);
            eachAddress1Map.put(address2Bucket.getKeyAsString(), eachAddress2Map);
        }
    }

    private void putResultMapToEachAddress2MapList(Aggregations aggregations, List<Map<String, Object>> resultList) {
        Aggregation address2Aggregation = aggregations.get(ALIAS_ADDRESS2);
        if(address2Aggregation == null)
            return;

        List<? extends Terms.Bucket> address2Buckets = ((Terms) address2Aggregation).getBuckets();
        if(address2Buckets == null)
            return;

        for(Terms.Bucket address2Bucket : address2Buckets) {
            Map<String, Object> eachAddress2Map = new LinkedHashMap<>();
            eachAddress2Map.put(ALIAS_ADDRESS2, address2Bucket.getKeyAsString());
            eachAddress2Map.put(ALIAS_TOTAL, address2Bucket.getDocCount());
            resultList.add(eachAddress2Map);
        }
    }

    private void putEachAddress2MapToEachAddress3Map(Terms.Bucket address2Bucket, Map<String, Object> eachAddress2Map) {
        Aggregation address3Aggregation = address2Bucket.getAggregations().get(ALIAS_ADDRESS3);
        if(address3Aggregation == null)
            return;

        List<? extends Terms.Bucket> address3Buckets = ((Terms) address3Aggregation).getBuckets();
//      eachAddress2Map.putAll(address3Buckets.stream().collect(Collectors.toMap(Terms.Bucket::getKeyAsString, Terms.Bucket::getDocCount)));
        if(address3Buckets == null)
            return;

        Map<String, Object> eachAddress3Map = new LinkedHashMap<>();
        for(Terms.Bucket address3Bucket : address3Buckets) {
            eachAddress3Map.put(address3Bucket.getKeyAsString(), address3Bucket.getDocCount());
        }
        eachAddress2Map.putAll(eachAddress3Map);
    }

    private void putResultMapToEachAddress3MapList(Aggregations aggregations, List<Map<String, Object>> resultList) {
        Aggregation address3Aggregation = aggregations.get(ALIAS_ADDRESS3);
        if(address3Aggregation == null)
            return;

        List<? extends Terms.Bucket> address3Buckets = ((Terms) address3Aggregation).getBuckets();
        if(address3Buckets == null)
            return;

        for(Terms.Bucket address3Bucket : address3Buckets) {
            Map<String, Object> eachAddress3Map = new LinkedHashMap<>();
            eachAddress3Map.put(ALIAS_ADDRESS2, address3Bucket.getKeyAsString());
            eachAddress3Map.put(ALIAS_TOTAL, address3Bucket.getDocCount());
            resultList.add(eachAddress3Map);
        }
    }

}
