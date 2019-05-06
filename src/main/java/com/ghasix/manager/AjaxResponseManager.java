package com.ghasix.manager;

import java.util.HashMap;
import java.util.Map;

import com.ghasix.manager.CodeMsgManager;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AjaxResponseManager extends AbsManager {
    
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

    public void setTid(Map<String, Object> inMap, long tId) {
        inMap.put(RESPONSE_KEY_TID, tId);
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

    public Map<String, Object> makeResponse(String resultCode, Map<String, Object> resultData) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        
        rtMap.put(RESPONSE_KEY_TID, RESPONSE_TID_DEFAULT);
        setCodeWithMsg(rtMap, resultCode);
        rtMap.put(RESULT_KEY_RESULT_DATA, resultData);
        return rtMap;
    }
}