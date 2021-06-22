package com.devh.hportal.result;

import com.devh.hportal.util.ExceptionUtils;
import org.json.simple.JSONObject;

/**
 * <pre>
 * Description :
 *     REST 호출을 통해 반환되는 결과를 담은 Map
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/21
 * </pre>
 */
public class ResultToJsonConverter {

    private final JSONObject resultJson;

    private ResultToJsonConverter() {
        this.resultJson = new JSONObject();
    }

    public static ResultToJsonConverter init() {
        return new ResultToJsonConverter();
    }

    public void putResultMapToResultJson(Object resultObject) {
        this.resultJson.put("resultJson", resultObject);
    }

    public void putResultMapToExceptionInformation(Exception e) {
        this.resultJson.put("resultException", ExceptionUtils.getInstance().toJson(e));
    }

    public JSONObject get() {
        return this.resultJson;
    }
}
