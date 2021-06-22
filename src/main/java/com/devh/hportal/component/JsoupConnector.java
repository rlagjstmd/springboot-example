package com.devh.hportal.component;

import com.devh.hportal.util.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Description :
 *     URL을 통해 Document 객체를 반환하는 객체
 * ===============================================
 * Member fields :
 *     Logger logger
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-28
 * </pre>
 */
@Component
@RequiredArgsConstructor
public class JsoupConnector {
    private final Logger logger = LoggerFactory.getLogger(JsoupConnector.class);

    /**
     * <pre>
     * Description
     *     Jsoup을 통해 url로부터 Document 객체를 반환
     * ===============================================
     * Parameters
     *     String url
     * Returns
     *     정상적으로 연결된 경우 Docuemnt, 에러시 null 반환
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-01
     * </pre>
     */
    public Document getDocumentFromURL(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (HttpStatusException httpStatusException) {
            try {Thread.sleep(1000L);} catch(InterruptedException ignored) {}
            if(httpStatusException.getStatusCode() == 404) {    /* 404 Not Found */
                logger.warn("Page Not Found. url: " + url);
                return null;
            }
            else {
                logger.warn("[" + httpStatusException.getMessage() + "] try to reconnect... " + url);
                return getDocumentFromURL(url);
            }

        } catch (Exception e) {
            try {Thread.sleep(1000L);} catch(InterruptedException ignored) {}
//            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            logger.warn("[" + e.getMessage() + "] try to reconnect... " + url);
            return getDocumentFromURL(url);
        }
    }
}
