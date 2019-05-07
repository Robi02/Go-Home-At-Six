package com.ghasix.service;

import java.util.Map;

import com.ghasix.datas.domain.Users;
import com.ghasix.datas.domain.UsersRepository;
import com.ghasix.manager.AjaxResponseManager;

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
    public Map<String, Object> checkUserStatus(String userJwt) {
        Map<String, Object> jwtSvcResult = userJwtSvc.getUserDataFromJwt(userJwt);

        if (jwtSvcResult == null) {
            logger.error("'jwtSvcResult' is null!");
            return jwtSvcResult;
        }

        if (ajaxResponseMgr.isResponseCodeSuccess(jwtSvcResult) == false) {
            logger.error("'jwtSvcResult's result code is NOT success!");
            return jwtSvcResult;
        }

        String email = (String) ajaxResponseMgr.getResponseData(jwtSvcResult, "userId");
        Users selectedUser = null;

        try {
            selectedUser = userRepo.findByEmail(email);
        }
        catch (Exception e) {
            logger.error("JPA Exception!", e);
            return ajaxResponseMgr.makeResponse("10101"); // 회원 DB조회중 오류가 발생했습니다.
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

        Long accessibleTime = selectedUser.getAccessibleTime();

        if (accessibleTime != null && System.currentTimeMillis() < accessibleTime) {
            logger.info("User found but, accessibleTime NOT reached. (accessibleTime:" + accessibleTime + ")");
            return ajaxResponseMgr.makeResponse("10004"); // 아직 사용할 수 없는 계정입니다.
        }

        logger.info("User found and authorized! (selectedUser:" + selectedUser.toString() + ")");
        return ajaxResponseMgr.makeResponse("00000", "selectedUser", selectedUser);
    }
}