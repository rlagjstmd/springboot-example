package com.devh.hportal.entity.news;

import com.devh.hportal.constant.news.MainCategory;
import com.devh.hportal.constant.news.Press;
import com.devh.hportal.constant.news.SubCategory;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * <pre>
 * Description : 
 *     마지막으로 수집한 뉴스 상황에 대한 엔티티 (Database)
 * ===============================================
 * Member fields : 
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-05-09
 * </pre>
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewsLastRead {
    @Id
    private String rowId;
    @Enumerated(EnumType.STRING)
    private Press press;
    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;
    private String articleId;
    private Long pubMillis;
    private Long scheduledMillis;
}