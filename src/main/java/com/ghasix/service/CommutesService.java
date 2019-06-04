package com.ghasix.service;

import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;

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

        logger.info("Select commutes SUCCESS! (email:" + ownUser.getEmail() + ")");
        return apiResultMgr.make(MapUtil.toMap("selectedCommutesList", selectedCommutesList), ApiResult.class);
    }

    @Override
    public ApiResult selectCommutesByTime(String userJwt, long beginTime, long endTime) {
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
             *      WHERE ({checkInTime} BETWEEN {checkInTime} AND {checkOutTime}) AND (own_user_id = {ownUser.id})
             */
            selectedCommutesList = commutesRepo.findByOwnUserIdAndCheckInTimeBetweenOrderByCheckInTimeDesc(ownUser, beginTime, endTime);
        }
        catch (Exception e) {
            logger.error("JPA Exception!", e);
            return apiResultMgr.make("20101", ApiResult.class); // 출퇴근기록 DB조회중 오류가 발생했습니다.
        }

        logger.info("Select commutes SUCCESS! (email:" + ownUser.getEmail() + ", time:" + beginTime + "-" + endTime + ")");
        return apiResultMgr.make(MapUtil.toMap("selectedCommutesList", selectedCommutesList), ApiResult.class);
    }

    @Override
    public ApiResult selectCommutesById(String userJwt, long commutesId) {
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
            selectedCommutesOp = commutesRepo.findByIdAndOwnUserId(commutesId, ownUser);
            selectedCommutes = (selectedCommutesOp.isPresent() ? selectedCommutesOp.get() : null);
        }
        catch (Exception e) {
            logger.error("JPA Select Exception!", e);
            return apiResultMgr.make("20101", ApiResult.class); // 출퇴근기록 DB조회중 오류가 발생했습니다.
        }

        logger.info("Select commutes SUCCESS! (ownUser:" + ownUser.getEmail() + ", commutesId:" + commutesId + ")");
        return apiResultMgr.make(MapUtil.toMap("selectedCommutes", selectedCommutes), ApiResult.class);
    }

    @Override
    public ApiResult selectCommutesLast(String userJwt) {
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

        Commutes lastCommutes = null;

        // JPS - Select
        try {
            /**
             *      SELECT *
             *      FROM commutes
             *      WHERE own_user_id = {ownUser.id} AND check_out_time = 0
             */
            List<Commutes> selectedCommutesList = commutesRepo.findByOwnUserIdAndCheckOutTime(ownUser, 0L);

            // 퇴근시간이 기입안된 결과가 여러개면, 가장 오래된 결과부터 반환
            if (selectedCommutesList != null && selectedCommutesList.size() > 0) {
                lastCommutes = selectedCommutesList.get(0);
            }

            // 조회결과가 없으면 가장 마지막 정상출퇴근 기록을 재조회하여 반환
            if (lastCommutes == null) {
                /**
                 *      SELECT *
                 *      FROM commutes
                 *      WHERE own_user_id = {ownUser.id}
                 *      LIMIT 1
                 */
                selectedCommutesList = commutesRepo.findTop1ByOwnUserIdOrderByCheckInTimeDesc(ownUser);
                
                if (selectedCommutesList != null && selectedCommutesList.size() > 0) {
                    lastCommutes = selectedCommutesList.get(0);
                }
            }
        }
        catch (Exception e) {
            logger.error("JPA Select Exception!", e);
            return apiResultMgr.make("20101", ApiResult.class); // 출퇴근기록 DB조회중 오류가 발생했습니다.
        }

        logger.info("Select last commutes SUCCESS! (ownUser:" + ownUser.getEmail() + ")");
        return apiResultMgr.make(MapUtil.toMap("lastCommutes", lastCommutes), ApiResult.class);
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

        final String commuteCompanyName = postCommutesDto.getCommuteCompanyName();
        final long checkInTime = postCommutesDto.getCheckInTime();
        final long checkOutTime = postCommutesDto.getCheckOutTime();
        final String memo = postCommutesDto.getMemo();

        if (commuteCompanyName == null || commuteCompanyName.length() == 0) {
            logger.error("'commuteCompanyName' is null or zero-length! (commuteCompanyName:" + commuteCompanyName + ")");
            return apiResultMgr.make("00106", ApiResult.class); // 올바르지않은 값을 전달받았습니다.
        }

        if (checkInTime < -1 || checkOutTime < -1) {
            logger.error("'checkInTime' or 'checkOutTime' less than -1! (checkInTime:" + checkInTime + ", checkOutTime:" + checkOutTime + ")");
            return apiResultMgr.make("00106", ApiResult.class); // 올바르지않은 값을 전달받았습니다.
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
            /**
             *      INSERT INTO commutes(...) VALUES(...);
             */
            insertedCommutes = Commutes.builder().ownUserId(ownUser)
                                                 .commuteCompanyName(commuteCompanyName)
                                                 .checkInTime(checkInTime)
                                                 .checkOutTime(checkOutTime)
                                                 .memo(memo)
                                                 .build();
            
            if ((insertedCommutes = commutesRepo.save(insertedCommutes)) == null) {
                logger.error("'.save()'for insert return null!");
                throw new Exception();
            }
        }
        catch (Exception e) {
            logger.error("JPA Insert Exception!", e);
            return apiResultMgr.make("20102", ApiResult.class); // 출퇴근기록 DB추가중 오류가 발생했습니다.
        }

        logger.info("Insert new commutes SUCCESS! (ownUser:" + ownUser.getEmail() + ")");
        return apiResultMgr.make(MapUtil.toMap("insertedCommutes", insertedCommutes), ApiResult.class);
    }

    @Override
    public ApiResult updateCommutes(String userJwt, long commutesId, PutCommutesDto putCommutesDto) {
        if (userJwt == null) {
            logger.error("'userJwt' is null!");
            return apiResultMgr.make("00202", ApiResult.class); // 회원토큰 값이 비었습니다.
        }

        if (putCommutesDto == null) {
            logger.error("'putCommutesDto' is null!");
            return apiResultMgr.make("00107", ApiResult.class); // 필수 인자값이 비었습니다.
        }

        final String commuteCompanyName = putCommutesDto.getCommuteCompanyName();
        final long checkInTime = putCommutesDto.getCheckInTime();
        final long checkOutTime = putCommutesDto.getCheckOutTime();
        final String memo = putCommutesDto.getMemo();

        if (commutesId < 0) {
            logger.error("'commutesId' less than zero! (commutesId:" + commutesId + ")");
            return apiResultMgr.make("00106", ApiResult.class); // 올바르지않은 값을 전달받았습니다.
        }

        if (commuteCompanyName == null || commuteCompanyName.length() == 0) {
            logger.error("'commuteCompanyName' is null or zero-length! (commuteCompanyName:" + commuteCompanyName + ")");
            return apiResultMgr.make("00106", ApiResult.class); // 올바르지않은 값을 전달받았습니다.
        }

        if (checkInTime < -1 || checkOutTime < -1) {
            logger.error("'checkInTime' or 'checkOutTime' less than -1! (checkInTime:" + checkInTime + ", checkOutTime:" + checkOutTime + ")");
            return apiResultMgr.make("00106", ApiResult.class); // 올바르지않은 값을 전달받았습니다.
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

        // JPA - Update (Select and Save)
        Commutes updatedCommutes = null;

        try {
            Commutes originCommutes = (Commutes) selectCommutesById(userJwt, commutesId).getResultData("selectedCommutes");

            if (originCommutes == null) {
                logger.error("'originCommutes' is null. FAIL to find origin data!");
                throw new Exception();
            }

            /**
             *      UPDATE commutes SET(... = ...)
             *      WHERE id = {commutesId} AND own_user_id = {ownUser.id};
             */
            updatedCommutes = (originCommutes.toBuilder()).commuteCompanyName(commuteCompanyName)
                                                          .checkInTime(checkInTime == 0 ? originCommutes.getCheckInTime() : checkInTime)
                                                          .checkOutTime(checkOutTime)
                                                          .memo(memo)
                                                          .build();

            if (commutesRepo.save(updatedCommutes) == null) {
                logger.error("'.save()'for update return null!");
                throw new Exception();
            }
        }
        catch (Exception e) {
            logger.error("JPA Update Exception!", e);
            return apiResultMgr.make("20103", ApiResult.class); // 출퇴근기록 DB변경중 오류가 발생했습니다.
        }

        logger.info("Update commutes SUCCESS! (ownUser:" + ownUser.getEmail() + ", commutesId:" + commutesId + ")");
        return apiResultMgr.make(MapUtil.toMap("updatedCommutes", updatedCommutes), ApiResult.class);
    }

    @Override @Transactional
    public ApiResult deleteCommutes(String userJwt, long commutesId) {
        if (userJwt == null) {
            logger.error("'userJwt' is null!");
            return apiResultMgr.make("00202", ApiResult.class); // 회원토큰 값이 비었습니다.
        }

        if (commutesId < 0) {
            logger.error("'commutesId' less than zero! (commutesId:" + commutesId + ")");
            return apiResultMgr.make("00106", ApiResult.class); // 올바르지않은 값을 전달받았습니다.
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

        Integer deletedRows = null;

        // JPA - Delete
        try {
            /**
             *      DELETE FROM commutes
             *      WHERE id = {id} AND own_user_id = {ownUser.id}
             */
            deletedRows = commutesRepo.deleteByIdAndOwnUserId(commutesId, ownUser);
        }
        catch (Exception e) {
            logger.error("JPA Delete Exception!", e);
            return apiResultMgr.make("20104", ApiResult.class); // 출퇴근기록 DB삭제중 오류가 발생했습니다.
        }

        if (deletedRows > 0) {
            logger.info("No commutes for delete! (ownUser:" + ownUser.getEmail() + ", commutesId:" + commutesId + ")");
        }
        else {
            logger.info("Delete commutes SUCCESS! (ownUser:" + ownUser.getEmail() + ", commutesId:" + commutesId + ")");
        }
        
        return apiResultMgr.make(ApiResult.class);
    }
}