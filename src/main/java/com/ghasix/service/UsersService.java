package com.ghasix.service;

import java.util.Map;

import com.ghasix.datas.domain.Users;
import com.ghasix.datas.domain.UsersRepository;
import com.ghasix.manager.AjaxResponseManager;
import com.ghasix.manager.UserJwtManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsersService implements IUsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);

    private AjaxResponseManager ajaxResponseMgr;
    private UserJwtService userJwtSvc;
    private UsersRepository userRepo;

    // 회원 존재여부, 서비스 접근유효성등 검사
    public Map<String, Object> checkUserStatus(String userJwt) { // 여기부터 시작, userJwt는 jwt서비스로 분리? @@
        Map<String, Object> jwtSvcResult = userJwtSvc.getUserDataFromJwt(userJwt);

        if (jwtSvcResult == null) {
            logger.error("'jwtSvcResult' is null!");
            return jwtSvcResult;
        }

        if (ajaxResponseMgr.isResponseCodeSuccess(jwtSvcResult)) {
            logger.error("'jwtSvcResult's result code is NOT success!");
            return jwtSvcResult;
        }

        String email = ajaxResponseMgr.getResponseData(jwtSvcResult, "email");
        Users selectedUser = null;

        try {
            selectedUser = userRepo.findByEmail(email);
        }
        catch (Exception e) {
            logger.error("JPA Exception!", e);
            return ajaxResponseMgr.makeResponse("10100"); // 회원 DB조회중 오류가 발생했습니다.
        }

        if (selectedUser == null) {
            logger.info("Fail to find user. (email:" + email + ")");
            return ajaxResponseMgr.makeResponse("10001"); // 회원 정보가 존재하지 않습니다.
        }

        String status = selectedUser.getStatus();

        if (!status.equals("일반")) {
            if (status.equals("휴면")) {
                logger.info("User found but, status is 'sleep'. (email:" + email + ")");
                return ajaxResponseMgr.makeResponse("10002"); // 휴면중인 회원입니다.
            }
            else if (status.equals("탈퇴")) {
                logger.info("User found but, status is 'deregister'. (email:" + email + ")");
                return ajaxResponseMgr.makeResponse("10003"); // 탈퇴한 회원입니다.
            }
        }

        long id = selectedUser.getId();
        logger.info("User found! (id:" + id + ")");
        return ajaxResponseMgr.makeResponse("id", id);
    }
}