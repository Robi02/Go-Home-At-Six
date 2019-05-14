package com.ghasix.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ghasix.datas.domain.Commutes;
import com.ghasix.datas.domain.CommutesRepository;
import com.ghasix.datas.domain.Users;
import com.ghasix.datas.dto.PostCommutesDto;
import com.ghasix.datas.dto.PutCommutesDto;
import com.ghasix.datas.result.ApiResult;
import com.ghasix.manager.ApiResultManager;
import com.robi.util.MapUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommutesService implements ICommutesService {

    private final Logger logger = LoggerFactory.getLogger(CommutesService.class);

    private UsersService usersSvc;
    private CommutesRepository commutesRepo;
    private ApiResultManager apiResultMgr;

    @Override
    public ApiResult selectCommutesAll(String userJwt) {
        if (userJwt == null) {
            logger.error("'userJwt' is null!");
            return apiResultMgr.make("00202", ApiResult.class); // 회원토큰 값이 비었습니다.
        }

        ApiResult checkUserSvcResult = usersSvc.checkUserStatus(userJwt);

        if (checkUserSvcResult.checkResultCodeSuccess() == false) {
            logger.error("'checkUserSvcResult's response code is FAIL!");
            return checkUserSvcResult;
        }

        Users ownUser = (Users) checkUserSvcResult.getResultData("selectedUser");
        
        if (ownUser == null) {
            logger.error("'ownUser' is null!");
            return apiResultMgr.make("00102", ApiResult.class); // 서버에서 값 획득에 실패했습니다.
        }

        // JPA - Select
        List<Commutes> selectedCommutesList = null;

        try {
            /**
             *      SELECT *
             *      FROM commutes
             *      WHERE own_user_id = {ownUser.id}
             *      ORDER BY id DESC;
             */
            selectedCommutesList = commutesRepo.findByOwnUserIdOrderByIdDesc(ownUser);
        }
        catch (Exception e) {
            logger.error("JPA Exception!", e);
            return apiResultMgr.make("20101", ApiResult.class); // 출퇴근기록 DB조회중 오류가 발생했습니다.
        }

        logger.info("Select own_user_id '" + ownUser.getId() + "'s all commutes SUCCESS! (selectedCommutesList:" +
                    selectedCommutesList.toString() + ")");
        return apiResultMgr.make(MapUtil.toMap("selectedCommutesList", selectedCommutesList), ApiResult.class);
    }

    @Override
    public ApiResult selectCommutesByTime(String userJwt, long beginTime, long endTime) {
        // 여기부터 시작! @@
        return null;
    }

    @Override
    public ApiResult selectCommutesById(String userJwt, long commuteId) {
        if (userJwt == null) {
            logger.error("'userJwt' is null!");
            return apiResultMgr.make("00202", ApiResult.class); // 회원토큰 값이 비었습니다.
        }

        ApiResult checkUserSvcResult = usersSvc.checkUserStatus(userJwt);

        if (checkUserSvcResult.checkResultCodeSuccess() == false) {
            logger.error("'checkUserSvcResult's response code is FAIL!");
            return checkUserSvcResult;
        }

        Users ownUser = (Users) checkUserSvcResult.getResultData("selectedUser");
        
        if (ownUser == null) {
            logger.error("'ownUser' is null!");
            return apiResultMgr.make("00102", ApiResult.class); // 서버에서 값 획득에 실패했습니다.
        }

        // JPA - Select
        Optional<Commutes> selectedCommutesOp = null;
        Commutes selectedCommutes = null;

        try {
            /**
             *      SELECT *
             *      FROM commutes
             *      WHERE id = {commuteId} and own_user_id = {ownUser.id}
             */
            selectedCommutesOp = commutesRepo.findByIdAndOwnUserId(commuteId, ownUser);
            selectedCommutes = (selectedCommutesOp.isPresent() ? selectedCommutesOp.get() : null);
        }
        catch (Exception e) {
            logger.error("JPA Exception!", e);
            return apiResultMgr.make("20101", ApiResult.class); // 출퇴근기록 DB조회중 오류가 발생했습니다.
        }

        logger.info("Select SUCCESS! (selectedCommutes:" + selectedCommutes + ")");
        return apiResultMgr.make(MapUtil.toMap("selectedCommutes", selectedCommutes), ApiResult.class);
    }

    @Override
    public ApiResult insertCommutes(String userJwt, PostCommutesDto postCommutesDto) {
        if (userJwt == null) {
            logger.error("'userJwt' is null!");
            return apiResultMgr.make("00202", ApiResult.class); // 회원토큰 값이 비었습니다.
        }

        if (postCommutesDto == null) {
            logger.error("'postCommutesDto' is null!");
            return apiResultMgr.make("00107", ApiResult.class); // 필수 인자값이 비었습니다.
        }

        ApiResult checkUserSvcResult = usersSvc.checkUserStatus(userJwt);

        if (checkUserSvcResult.checkResultCodeSuccess() == false) {
            logger.error("'checkUserSvcResult's response code is FAIL!");
            return checkUserSvcResult;
        }

        Users ownUser = (Users) checkUserSvcResult.getResultData("selectedUser");
        
        if (ownUser == null) {
            logger.error("'ownUser' is null!");
            return apiResultMgr.make("00102", ApiResult.class); // 서버에서 값 획득에 실패했습니다.
        }

        // JPA - Insert
        Commutes insertedCommutes = null;

        try {
            insertedCommutes = Commutes.builder().ownUserId(ownUser)
                                                 .commuteCompanyName(postCommutesDto.getCommuteCompanyName())
                                                 .checkInTime(postCommutesDto.getCheckInTime())
                                                 .checkOutTime(postCommutesDto.getCheckInTime())
                                                 .memo(postCommutesDto.getMemo())
                                                 .build();
            
            if (commutesRepo.save(insertedCommutes) == null) {
                logger.error("'.save()' returns null!");
                throw new Exception();
            }
        }
        catch (Exception e) {
            logger.error("JPA Insert Exception!", e);
            return apiResultMgr.make("20102", ApiResult.class); // 출퇴근기록 DB추가중 오류가 발생했습니다.
        }

        logger.info("Insert new commutes SUCCESS! (insertedCommutes:" + insertedCommutes.toString() + ")");
        return apiResultMgr.make(ApiResult.class);
    }

    @Override
    public ApiResult updateCommutes(String userJwt, PutCommutesDto putCommutesDto) {
        return null;
    }

    @Override
    public ApiResult deleteCommutes(String userJwt) {
        return null;
    }
}