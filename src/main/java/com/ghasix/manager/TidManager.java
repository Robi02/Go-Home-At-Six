package com.ghasix.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TidManager extends AbsManager {

    private final Logger logger = LoggerFactory.getLogger(TidManager.class);

    private long accessCnt = 0;

    public String generateTid() {
        return String.valueOf(accessCnt++);
    }
}