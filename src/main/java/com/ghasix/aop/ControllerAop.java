package com.ghasix.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ghasix.manager.AjaxResponseManager;
import com.ghasix.manager.TidManager;
import com.ghasix.util.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.AllArgsConstructor;

@Aspect
@Component
@AllArgsConstructor
public class ControllerAop {

    private final Logger logger = LoggerFactory.getLogger(ControllerAop.class);
    
    private TidManager tidMgr;
    private AjaxResponseManager ajaxResponseMgr;

    @Around("execution(* com.ghasix.controller..*.*(..))") // 컨트롤러 AoP
    public Object aroundController(ProceedingJoinPoint pjp) {
        String oldLogLayerStr = LogUtil.changeLogLayer(LogUtil.LAYER_CTR);
        long startTime = System.currentTimeMillis();
        boolean ctrResult = true;
        Object ctrReturn = null;

        // tId 생성(Transaction ID)
        String tId = tidMgr.generateTid();
        LogUtil.changeTid(tId);

        // 클라이언트 정보 획득(IP, Methods, URI, ...) and Start logging
        logger.info("STARTS - " + getClientInfo());

        try {
            // 컨트롤러 로직 수행   
            try {
                ctrReturn = pjp.proceed();
            }
            catch (Throwable t) {
                logger.error("Controller Exception!", t);
                ctrResult = false;
            }

            if (ctrReturn != null && ctrReturn instanceof Map) {
                ajaxResponseMgr.setTid((Map<String, Object>) ctrReturn, tId);
            }
        }
        catch (Throwable t) {
            logger.error("Controller AoP Exception!", t);
            ctrResult = false;
        }

        // 결과 로깅 및 반환
        logger.info("FINISH - Result:" + ctrResult + " (TimeElapsed:" + (System.currentTimeMillis() - startTime) + "ms)");
        LogUtil.changeLogLayer(oldLogLayerStr);
        return ctrReturn;
    }

    // Spring RequestContextHolder로부터 HttpServletRequst를 반환
    private HttpServletRequest getServletRequest() {
        ServletRequestAttributes servletReqAttrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletReqAttrs.getRequest();
    }
    
    // Client 정보 반환
    private final String IP_RELATED_HEADERS[] = { "X-FORWARDED-FOR",
                                                  "Proxy-Client-IP",
                                                  "WL-Proxy-Client-IP",
                                                  "HTTP_CLIENT_IP",
                                                  "HTTP_X_FORWARDED_FOR" };

    private String getClientInfo() {
        HttpServletRequest request = getServletRequest();

        if (request == null) {
            logger.error("'request' is null!");
            return null;
        }

        String clientIp = null;

        for (int i = 0; i < IP_RELATED_HEADERS.length; i++) {
            if ((clientIp = request.getHeader(IP_RELATED_HEADERS[i])) != null) {
                break;
            }
        }

        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }

        return request.getMethod() + " " + request.getRequestURI() + "(IP:" + clientIp + ")";
    }

    // 호출 메서드정보 반환
    public String getMethodInfo(ProceedingJoinPoint pjp) {
        Signature sign = pjp.getSignature();
        String packageName = sign.getDeclaringTypeName();
        String methodName = sign.getName();

        return packageName + "." + methodName + "()";
    }
}