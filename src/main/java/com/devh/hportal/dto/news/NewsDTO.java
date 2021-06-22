package com.devh.hportal.dto.news;

import com.devh.hportal.constant.news.MainCategory;
import com.devh.hportal.constant.news.Press;
import com.devh.hportal.constant.news.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Description :
 *     News Entity에 대응되는 DTO
 * ===============================================
 * Member fields :
 *     String newsId;
 *     String articleId;
 *     Press press;
 *     MainCategory mainCategory;
 *     SubCategory subCategory;
 *     String originalLink;
 *     Long pubMillis;
 *     String title;
 *     String summary;
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-05-05
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsDTO {
    private String newsId;
    private String articleId;
    private Press press;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private String originalLink;
    private Long pubMillis;
    private String title;
    private String summary;
}