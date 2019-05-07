package com.ghasix.service;

import java.util.Map;

import com.ghasix.datas.domain.Commutes;
import com.ghasix.datas.domain.CommutesRepository;
import com.ghasix.datas.domain.Users;
import com.ghasix.datas.dto.PostCommutesDto;
import com.ghasix.datas.dto.PutCommutesDto;
import com.ghasix.manager.AjaxResponseManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommutesService implements ICommutesService {

    private final Logger logger = LoggerFactory.getLogger(CommutesService.class);

    private AjaxResponseManager ajaxResponseMgr;
    private UsersService usersSvc;
    private CommutesRepository commutesRepo;

    @Override
    public Map<String, Object> selectCommutesAll(String userJwt) {
        return null;
    }

    @Override
    public Map<String, Object> selectCommutesById(String userJwt, long commuteId) {
        return null;
    }

    @Override
    public Map<String, Object> selectCommutesByTime(String userJwt, long beginTime, long EndTime) {
        return null;
    }

    @Override
    public Map<String, Object> insertCommutes(String userJwt, PostCommutesDto postCommutesDto) {
        if (userJwt == null) {
            logger.error("'userJwt' is null!");
            return ajaxResponseMgr.makeResponse("00202"); // 회원토큰 값이 비었습니다.
        }

        if (postCommutesDto == null) {
            logger.error("'postCommutesDto' is null!");
            return ajaxResponseMgr.makeResponse("00107"); // 필수 인자값이 비었습니다.
        }

        Map<String, Object> checkUserResultMap = usersSvc.checkUserStatus(userJwt);

        if (ajaxResponseMgr.isResponseCodeSuccess(checkUserResultMap) == false) {
            logger.error("'checkUserResultMap's response code is FAIL!");
            return checkUserResultMap;
        }

        Users ownUser = (Users) ajaxResponseMgr.getResponseData(checkUserResultMap, "selectedUser");
        
        if (ownUser == null) {
            logger.error("'ownUser' is null!");
            return ajaxResponseMgr.makeResponse("00102"); // 서버에서 값 획득에 실패했습니다.
        }

        // JPA
        Commutes insertCommutes = null;

        try {
            insertCommutes = Commutes.builder().ownUserId(ownUser)
                                               .commuteCompanyName(postCommutesDto.getCommuteCompanyName())
                                               .checkInTime(postCommutesDto.getCheckInTime())
                                               .checkOutTime(postCommutesDto.getCheckInTime())
                                               .memo(postCommutesDto.getMemo())
                                               .build();
            commutesRepo.save(insertCommutes);
        }
        catch (Exception e) {
            logger.error("JPA Exception!", e);
            return ajaxResponseMgr.makeResponse("20102"); // 출퇴근기록 DB추가중 오류가 발생했습니다.
        }

        logger.info("Insert new commutes SUCCESS! (insertCommutes:" + insertCommutes.toString() + ")");
        return ajaxResponseMgr.makeResponse();
    }

    @Override
    public Map<String, Object> updateCommutes(String userJwt, PutCommutesDto putCommutesDto) {
        return null;
    }

    @Override
    public Map<String, Object> deleteCommutes(String userJwt) {
        return null;
    }
}