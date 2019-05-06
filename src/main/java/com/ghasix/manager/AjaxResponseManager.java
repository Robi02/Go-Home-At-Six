package com.ghasix.manager;

import java.util.HashMap;
import java.util.Map;

import com.ghasix.manager.CodeMsgManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AjaxResponseManager extends AbsManager {
    
    private final Logger logger = LoggerFactory.getLogger(AjaxResponseManager.class);

    private final String RESPONSE_KEY_TID       = "tId";
    private final String RESULT_KEY_RESULT_CODE = "resultCode";
    private final String RESULT_KEY_RESULT_MSG  = "resultMsg";
    private final String RESULT_KEY_RESULT_DATA = "resultData";
    
    private final long   RESPONSE_TID_DEFAULT   = 0;
    private final String RESULT_CODE_DEFAULT    = "00000";
    private final String RESULT_CODE_UNKNOWN    = "99999";
    private final String RESULT_MSG_DEFAULT     = null;
    private final String RESULT_DATA_DEFAULT    = null;

    private CodeMsgManager codeMsgMgr;

    private void setCodeWithMsg(Map<String, Object> inMap, String code) {
        inMap.put(RESULT_KEY_RESULT_CODE, code);
        inMap.put(RESULT_KEY_RESULT_MSG, codeMsgMgr.getMsg(code));
    }

    private Map<String, Object> makeMapFromKeyValAry(Object... resultDataKeyValAry) {
        if (resultDataKeyValAry == null) {
			logger.error("'resultDataKeyValAry' is null!");
			return makeResponse(RESULT_CODE_UNKNOWN);
		}
		
		if (resultDataKeyValAry.length % 2 != 0) {
			logger.error("'keyValAry' is NOT multiple of 2!");
			return makeResponse(RESULT_CODE_UNKNOWN);
		}
		
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		String key = null;
		Object val = null;
		
		for (int i = 0; i < resultDataKeyValAry.length; i += 2) {
			if (!(resultDataKeyValAry[i] instanceof String)) {
                logger.warn("'resultDataKeyValAry[" + i + "]' is NOT instance of String. The value '" +
                            resultDataKeyValAry[i + 1] + "' will lose!");
				continue;
			}

			key = (String)resultDataKeyValAry[i];		// odd column for key
			val = (Object)resultDataKeyValAry[i + 1];	// even column for value
			
			if (key == null || key.length() == 0) {
                logger.warn("'resultDataKeyValAry[" + i + "]' is null or zero length. The value '" +
                            val + "' will lose!");
				continue;
			}
			
			responseDataMap.put(key, val);
        }

        return responseDataMap;
    }

    public void setTid(Map<String, Object> inMap, String tId) {
        inMap.put(RESPONSE_KEY_TID, tId);
    }

    public boolean isResponseCodeSuccess(Map<String, Object> inMap) {
        String resultCode = (String) inMap.get(RESULT_KEY_RESULT_CODE);

        if (resultCode == null) {
            return false;
        }

        if (!resultCode.equals(RESULT_CODE_DEFAULT)) {
            return false;
        }

        return true;
    }

    public String getResponseData(Map<String, Object> inMap, String key) {
        if (inMap == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> returnDataMap = (Map<String, Object>) inMap.get(RESULT_KEY_RESULT_DATA);
        
        if (returnDataMap == null) {
            return null;
        }

        Object rtObj = returnDataMap.get(key);

        if (rtObj == null) {
            return null;
        }

        return rtObj.toString();
    }

    public Map<String, Object> makeResponse() {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        
        rtMap.put(RESPONSE_KEY_TID, RESPONSE_TID_DEFAULT);
        setCodeWithMsg(rtMap, RESULT_CODE_DEFAULT);
        rtMap.put(RESULT_KEY_RESULT_DATA, null);
        return rtMap;
    }

    public Map<String, Object> makeResponse(String resultCode) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        
        rtMap.put(RESPONSE_KEY_TID, RESPONSE_TID_DEFAULT);
        setCodeWithMsg(rtMap, resultCode);
        rtMap.put(RESULT_KEY_RESULT_DATA, null);
        return rtMap;
    }

    public Map<String, Object> makeResponse(Map<String, Object> resultData) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        
        rtMap.put(RESPONSE_KEY_TID, RESPONSE_TID_DEFAULT);
        setCodeWithMsg(rtMap, RESULT_CODE_DEFAULT);
        rtMap.put(RESULT_KEY_RESULT_DATA, resultData);
        return rtMap;
    }

    public Map<String, Object> makeResponse(Object... resultDataKeyValAry) {
        return makeResponse(makeMapFromKeyValAry(resultDataKeyValAry));
    }

    public Map<String, Object> makeResponse(String resultCode, Map<String, Object> resultData) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        
        rtMap.put(RESPONSE_KEY_TID, RESPONSE_TID_DEFAULT);
        setCodeWithMsg(rtMap, resultCode);
        rtMap.put(RESULT_KEY_RESULT_DATA, resultData);
        return rtMap;
    }

    public Map<String, Object> makeResponse(String resultCode, Object... resultDataKeyValAry) {
        return makeResponse(resultCode, makeMapFromKeyValAry(resultDataKeyValAry));
    }
}