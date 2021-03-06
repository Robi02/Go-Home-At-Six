package com.ghasix.service;

import java.util.Map;

import com.ghasix.datas.domain.Users;
import com.ghasix.datas.domain.UsersRepository;
import com.ghasix.datas.enums.UsersStatus;
import com.robi.data.ApiResult;
import com.robi.util.MapUtil;
import com.robi.util.RestHttpUtil;
import com.robi.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsersService implements IUsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);

    private UserJwtService userJwtSvc;
    private UsersRepository userRepo;

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
            return ApiResult.make(false);
        }

        String responseStr = RestHttpUtil.urlConnection("http://localhost:40000/users/api/jwt/validate",
                                                        RestHttpUtil.METHOD_POST,
                                                        MapUtil.toMap("Content-Type", "application/json;charset=utf-8"),
                                                        MapUtil.toMap("userJwt", userJwt, "audience", "ghasix"));
        JSONObject rpyObj = null;
        ApiResult rpyApiRst = null;
        
        try {
            rpyObj = new JSONObject(responseStr);
            rpyApiRst = ApiResult.make(rpyObj);
        }
        catch (JSONException e) {
            logger.error("Exception!", e);
            return ApiResult.make(false, "10000"); // 회원관련 오류가 발생했습니다.
        }

        if (!rpyApiRst.getResult()) {
            return rpyApiRst;
        }

        boolean isUserSelected = true;
        String email = rpyApiRst.getDataAsStr("email");
        Users selectedUser = null;

        try { // JPA - Select
            selectedUser = userRepo.findByEmail(email);

            if (selectedUser == null) {
                isUserSelected = false;
            }
        }
        catch (Exception e) {
            logger.error("JPA Insert Exception!", e);
            return ApiResult.make(false, "10102"); // 회원 DB조회중 오류가 발생했습니다.
        }

        if (!isUserSelected) { // ghasix 신규회원의 경우 추가
            String fullName = rpyApiRst.getDataAsStr("fullName");
            ApiResult insertRst = insertUser(email, fullName);

            if (!insertRst.getResult()) {
                return insertRst;
            }

            selectedUser = (Users) insertRst.getData("insertedUser");
        }

        return ApiResult.make(true, MapUtil.toMap("selectedUser", selectedUser));
    }

    // 회원 추가
    public ApiResult insertUser(String email, String name) {
        if (email == null || name == null) {
            logger.error("'email' or 'name' is null! (email:" + email + ", name:" + name + ")");
            return ApiResult.make(false, "00101"); // 필수 인자값이 비었습니다.
        }

        email = email.trim();
        name = name.trim();

        if (StringUtil.isEmail(email) == false) {
            logger.error("'email' is NOT correct! (email:" + email + ")");
            return ApiResult.make(false, "00103"); // 올바른 이메일 형식이 아닙니다.
        }

        if (name.length() == 0) {
            logger.error("'name's length is zero!");
            return ApiResult.make(false, "00105"); // 이름이 너무 짧습니다.
        }

        Users selectedUser = null;

        try { // JPA - Select
            selectedUser = userRepo.findByEmail(email);
        }
        catch (Exception e) {
            logger.error("JPA Select Exception!", e);
            return ApiResult.make(false, "10101"); // 회원 DB조회중 오류가 발생했습니다.
        }

        if (selectedUser != null) {
            logger.error("'selectedUser' is NOT null! email duplicated! (email:" + email + ")");
            return ApiResult.make(false, "00104"); // 사용중인 이메일입니다.
        }

        Users insertedUser = null;

        try { // JPA - Insert
            long curTime = System.currentTimeMillis();
            insertedUser = Users.builder().email(email)
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
            return ApiResult.make(false, "10102"); // 회원 DB추가중 오류가 발생했습니다.
        }

        logger.info("New users inserted! (insertedUser:" + insertedUser.toString() + ")");
        return ApiResult.make(true, MapUtil.toMap("insertedUser", insertedUser));
    }
}