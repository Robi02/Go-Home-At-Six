package com.ghasix.datas.result;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class ApiResult {
    
    public static final String KEY_TID              = "tId";
    public static final String KEY_RESULT_CODE      = "resultCode";
    public static final String KEY_RESULT_MSG       = "resultMsg";
    public static final String KEY_RESULT_DATA      = "resultDatas";

    public static final String VAL_TID_DEFAULT      = null;
    public static final String VAL_CODE_SUCCESS     = "00000";
    public static final String VAL_CODE_SYSFAIL     = "99999";
    public static final String VAL_MSG_DEFAULT      = null;
    
    private Map<String, Object> apiResult;
    // 새로운 필드 추가 가능. 단 getter존재 시, 컨트롤러 API응답값에도 필드가 추가될수 있음에 유의!

    public Map<String, Object> getApiResult(){
        return this.apiResult;
    }

    public boolean checkResultCodeSuccess() {
        if (this.apiResult == null) {
            return false;
        }

        String resultCode = this.apiResult.get(KEY_RESULT_CODE).toString();

        if (resultCode == null) {
            return false;
        }

        return resultCode.equals(VAL_CODE_SUCCESS);
    }

    public void addResultData(Map<String, Object> addResultData) {
        if (this.apiResult == null || addResultData == null) {
            return;
        }

        Map<String, Object> resultDataMap = (Map<String, Object>) this.apiResult.get(KEY_RESULT_DATA);

        if (resultDataMap == null) {
            return;
        }

        resultDataMap.putAll(addResultData);
    }

    public Object getResultData(String key) {
        if (this.apiResult == null) {
            return null;
        }

        Map<String, Object> resultDataMap = (Map<String, Object>) this.apiResult.get(KEY_RESULT_DATA);

        if (resultDataMap == null) {
            return null;
        }

        return resultDataMap.get(key);
    }

    public void controllerCompact(String tId) {
        if (this.apiResult == null) {
            return;
        }

        this.apiResult.put(KEY_TID, tId);

        Map<String, Object> resultDataMap = (Map<String, Object>) this.apiResult.get(KEY_RESULT_DATA);

        if (resultDataMap != null) {
            if (resultDataMap.size() == 0) {
                this.apiResult.put(KEY_RESULT_DATA, null);
            }
        }
    }
}