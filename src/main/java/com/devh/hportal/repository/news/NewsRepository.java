package com.devh.hportal.repository.news;

import com.devh.hportal.entity.news.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * <pre>
 * Description :
 *     뉴스 레파지토리 (Elasticsearch)
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-05-12
 * </pre>
 */
public interface NewsRepository extends ElasticsearchRepository<News, String> {

}