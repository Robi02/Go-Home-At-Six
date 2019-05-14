package com.ghasix.manager;

import java.util.HashMap;
import java.util.Map;

import com.ghasix.datas.result.ApiResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ApiResultManager {

    private final Logger logger = LoggerFactory.getLogger(ApiResultManager.class);
    private CodeMsgManager codeMsgMgr;

    private ApiResult getInstance(String tId, String resultCode, String resultMsg, Map<String, Object> resultData, Class<? extends ApiResult> resultClazz) {
        if (resultClazz == ApiResult.class) {
            tId         = (tId          == null ? ApiResult.VAL_TID_DEFAULT   : tId);
            resultCode  = (resultCode   == null ? ApiResult.VAL_CODE_SUCCESS  : resultCode);
            resultMsg   = (resultMsg    == null ? ApiResult.VAL_MSG_DEFAULT   : resultMsg);

            Map<String, Object> resultDataMap = new HashMap<String, Object>();

            resultDataMap.put(ApiResult.KEY_TID, tId);
            resultDataMap.put(ApiResult.KEY_RESULT_CODE, resultCode);
            resultDataMap.put(ApiResult.KEY_RESULT_MSG, codeMsgMgr.getMsg(resultCode));
            resultDataMap.put(ApiResult.KEY_RESULT_DATA, resultData);

            return new ApiResult(resultDataMap);
        }

        return null;
    }

    // 성공응답 + 응답데이터 없음
    public ApiResult make(Class<? extends ApiResult> resultClazz) {
        return getInstance(null, null, null, null, resultClazz);
    }

    // 응답코드 + 응답데이터 없음
    public ApiResult make(String resultCode, Class<? extends ApiResult> resultClazz) {
        return getInstance(null, resultCode, null, null, resultClazz);
    }

    // 성공응답 + 응답데이터
    public ApiResult make(Map<String, Object> resultData, Class<? extends ApiResult> resultClazz) {
        return getInstance(null, null, null, resultData, resultClazz);
    }

    // 응답코드 + 응답데이터
    public ApiResult make(String resultCode, Map<String, Object> resultData, Class<? extends ApiResult> resultClazz) {
        return getInstance(null, resultCode, null, resultData, resultClazz);
    }
}