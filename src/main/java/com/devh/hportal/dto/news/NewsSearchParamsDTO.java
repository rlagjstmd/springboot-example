package com.devh.hportal.dto.news;

import com.devh.hportal.constant.news.MainCategory;
import com.devh.hportal.constant.news.Press;
import com.devh.hportal.constant.news.SubCategory;
import lombok.*;

/**
 * <pre>
 * Description :
 *     뉴스 검색에 필요한 파라미터를 갖는 DTO 객체
 * ===============================================
 * Member fields :
 *     String keyword;
 *     Press press;
 *     MainCategory mainCategory;
 *     SubCategory subCategory;
 *     long from;
 *     long to;
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-05-12
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class NewsSearchParamsDTO {
    private String keyword;
    private Press press;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private long fromMillis;
    private long toMillis;
}