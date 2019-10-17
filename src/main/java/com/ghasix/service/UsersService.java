package com.ghasix.service;

import java.util.Map;

import com.ghasix.datas.domain.Users;
import com.ghasix.datas.domain.UsersRepository;
import com.ghasix.datas.enums.UsersStatus;
import com.ghasix.datas.result.ApiResult;
import com.ghasix.manager.ApiResultManager;
import com.robi.util.MapUtil;
import com.robi.util.RestHttpUtil;
import com.robi.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsersService implements IUsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);

    private UserJwtService userJwtSvc;
    private UsersRepository userRepo;
    private ApiResultManager apiResultMgr;

    // 회원 존재여부, 서비스 접근유효성등 검사
    public ApiResult checkUserStatus(String userJwt) {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);

        JSONObject postJsonObj = null;
        
        try {
            postJsonObj = new JSONObject();
            postJsonObj.put("userJwt", userJwt);
        }
        catch (JSONException e) {
            logger.error("Exception!", e);
            return new ApiResult(null);
        }

        HttpEntity<String> httpEntity = new HttpEntity<String>(postJsonObj.toString(), httpHeader);

        RestTemplate authsRest = RestHttpUtil.getInstance();
        ResponseEntity<String> responseStr = authsRest.postForEntity(
            "http://localhost:50000/users/api/jwt/validate",
            httpEntity,
            String.class);
        
        JSONObject rpyObj = null;
        
        try {
            rpyObj = new JSONObject(responseStr.getBody());
        }
        catch (JSONException e) {
            logger.error("Exception!", e);
            return new ApiResult(null);
        }
     
        logger.info(rpyObj.toString());
        return new ApiResult(null);
    }

    // 회원 추가
    public ApiResult insertUser(String email, String name) {
        if (email == null || name == null) {
            logger.error("'email' or 'name' is null! (email:" + email + ", name:" + name + ")");
            return apiResultMgr.make("00101", ApiResult.class); // 필수 인자값이 비었습니다.
        }

        email = email.trim();
        name = name.trim();

        if (StringUtil.isEmail(email) == false) {
            logger.error("'email' is NOT correct! (email:" + email + ")");
            return apiResultMgr.make("00103", ApiResult.class); // 올바른 이메일 형식이 아닙니다.
        }

        if (name.length() == 0) {
            logger.error("'name's length is zero!");
            return apiResultMgr.make("00105", ApiResult.class); // 이름이 너무 짧습니다.
        }

        Users selectedUser = null;

        try { // JPA - Select
            selectedUser = userRepo.findByEmail(email);
        }
        catch (Exception e) {
            logger.error("JPA Select Exception!", e);
            return apiResultMgr.make("10101", ApiResult.class); // 회원 DB조회중 오류가 발생했습니다.
        }

        if (selectedUser != null) {
            logger.error("'selectedUser' is NOT null! email duplicated! (email:" + email + ")");
            return apiResultMgr.make("00104", ApiResult.class); // 사용중인 이메일입니다.
        }

        try { // JPA - Insert
            long curTime = System.currentTimeMillis();
            Users insertedUser = Users.builder().email(email)
                                                .name(name)
                                                .accessLevel(1)
                                                .status(UsersStatus.NORMAL)
                                                .joinTime(curTime)
                                                .lastLoginTime(0L)
                                                .accessibleTime(curTime)
                                                .build();

            if (userRepo.save(insertedUser) == null) {
                logger.error("'.save()' returns null!");
                throw new Exception();
            }
        }
        catch (Exception e) {
            logger.error("JPA Insert Exception!", e);
            return apiResultMgr.make("10102", ApiResult.class); // 회원 DB추가중 오류가 발생했습니다.
        }

        logger.info("New users inserted! (email:" + email + ")");
        return apiResultMgr.make(ApiResult.class);
    }
}