package com.robi.data;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonAutoDetect(fieldVisibility     = Visibility.NONE,
                getterVisibility    = Visibility.NONE,
                setterVisibility    = Visibility.NONE,
                isGetterVisibility  = Visibility.NONE)
@JsonPropertyOrder({"trace_id", "result_code", "result_msg", "result_data"})
public class ApiResult {

    private static final Logger logger = LoggerFactory.getLogger(ApiResult.class);

    public static final String KEY_API_TRACE_ID     = "trace_id";
    public static final String KEY_API_RESULT_CODE  = "result_code";
    public static final String KEY_API_RESULT_MSG   = "result_msg";
    public static final String KEY_API_RESULT_DATA  = "result_data";
    
    public static final String DEFAULT_API_RESULT_CODE_POSSITIVE    = "O";
    public static final String DEFAULT_API_RESULT_CODE_NEGATIVE     = "X";
    public static final String DEFAULT_API_RESULT_MSG_POSITIVE      = "OK";
    public static final String DEFAULT_API_RESULT_MSG_NEGATIVE      = "FAIL";

    protected boolean result;

    @JsonProperty(KEY_API_TRACE_ID)
    protected String trace_id;
    
    @JsonProperty(KEY_API_RESULT_CODE)
    protected String result_code;

    @JsonProperty(KEY_API_RESULT_MSG)
    protected String result_msg;

    @JsonProperty(KEY_API_RESULT_DATA)
    protected Map<String, Object> result_data;

    private ApiResult(boolean result, String code, String msg, Map<String, Object> data) {
        this.result = result;
        this.trace_id = null;
        this.result_code = (code == null ? (result ? DEFAULT_API_RESULT_CODE_POSSITIVE : DEFAULT_API_RESULT_CODE_NEGATIVE) : code);
        this.result_msg = (msg == null ? (result ? DEFAULT_API_RESULT_MSG_POSITIVE : DEFAULT_API_RESULT_MSG_NEGATIVE) : msg);
        this.result_data = data;
    }

    public static ApiResult make(boolean result) {
        return ApiResult.make(result, null, null, null);
    }

    public static ApiResult make(boolean result, String code) {
        return ApiResult.make(result, code, null, null);
    }

    public static ApiResult make(boolean result, Map<String, Object> data) {
        return ApiResult.make(result, null, null, data);
    }

    public static ApiResult make(boolean result, String code, Map<String, Object> data) {
        return ApiResult.make(result, code, null, data);
    }

    public static ApiResult make(boolean result, String code, String msg, Map<String, Object> data) {
        return new ApiResult(result, code, msg, data);
    }

    public boolean getResult() {
        return this.result;
    }

    public void addData(Map<String, Object> dataMap) {
        if (this.result_data == null) {
            this.result_data = dataMap;
        }
        else {
            this.result_data.putAll(dataMap);
        }
    }

    public Object addData(String key, Object data) {
        if (this.result_data == null) {
            this.result_data = new HashMap<String, Object>();
        }

        return this.result_data.put(key, data);
    }

    public String getTraceId() {
        return this.trace_id;
    }

    public void setTraceId(String traceId) {
        this.trace_id = traceId;
    }

    public String getResultCode() {
        return this.result_code;
    }

    public void setResultCode(String code) {
        this.result_code = code;
    }

    public String getResultMsg() {
        return this.result_msg;
    }

    public void setResultMsg(String msg) {
        this.result_msg = msg;
    }

    public Map<String, Object> getData() {
        return this.result_data;
    }

    public Object getData(String key) {
        if (this.result_data == null) {
            return null;
        }

        return this.result_data.get(key);
    }

    public void setData(Map<String, Object> dataMap) {
        this.result_data = dataMap;
    }

    @Override
    public String toString() {
        String jsonFormDataStr = null;

        try {
            jsonFormDataStr = new JSONObject(this.result_code).toString();
        }
        catch (JSONException e) {
            logger.error("Exception!", e);
        }

        return "{\"" + KEY_API_TRACE_ID + "\":\"" + this.trace_id + "\",\"" +
            KEY_API_RESULT_CODE + "\":\"" + this.result_code + "\",\"" +
            KEY_API_RESULT_MSG + "\":\"" + this.result_msg + "\",\"" +
            KEY_API_RESULT_DATA + "\":" + jsonFormDataStr + "}";
    }
}