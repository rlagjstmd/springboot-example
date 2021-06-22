package com.devh.hportal.util;

import com.devh.hportal.constant.news.NewsIndex;
import com.devh.hportal.entity.news.News;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * Description :
 *     News 고유 ID, Index명 을 생성해주는 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-05-05
 * </pre>
 */
public class ElasticsearchCreator {
    private static final SimpleDateFormat INDEX_DATE_FORMAT = new SimpleDateFormat("yyyy-MM");

    public static String createNewsIndexName(News news) {
        return
                NewsIndex.INDEX_NAME_PRE.getValue() + NewsIndex.INDEX_DELIMITER.getValue() +    /* news_ */
                        INDEX_DATE_FORMAT.format(new Date(news.getPubMillis()));                        /* news_2021-05 */
    }
}
