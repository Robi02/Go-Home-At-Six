package com.ghasix.controller.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.ghasix.datas.dto.PostCommutesDto;
import com.ghasix.datas.dto.PutCommutesDto;
import com.ghasix.manager.AjaxResponseManager;
import com.ghasix.manager.CodeMsgManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CommutesRestController {

    private final Logger logger = LoggerFactory.getLogger(CommutesRestController.class);

    AjaxResponseManager ajaxResponseMgr;

    @GetMapping("/commutes/{id}") // 특정 출퇴근 기록 조회
    public Map<String, Object> getCommutesById(@PathVariable("id") long id,
                                               @RequestHeader("userJwt") String userJwt) {
        return null;
    }

    @GetMapping("/commutes/time/all") // 출퇴근 기록 전체 조회
    public Map<String, Object> getCommutesTimeAll(@RequestHeader("userJwt") String userJwt) {
        return null;
    }

    @GetMapping("/commutes/time/{beginTime}-{endTime}") // 출퇴근 기록 범위 조회
    public Map<String, Object> getCommutesTimeBetween(@PathVariable("beginTime") long beginTime,
                                                      @PathVariable("endTime") long endTime,
                                                      @RequestHeader("userJwt") String userJwt) {
        return null;
    }

    @PostMapping("/commutes") // 출퇴근 기록 추가
    public Map<String, Object> postCommutes(@RequestHeader("userJwt") String userJwt,
                                            @RequestBody PostCommutesDto postCommutesDto) {
        /*logger.info("postCommutesDto:" + postCommutesDto.toString());
        commutesRepository.save(Commutes.builder().commute_companay_name(postCommutesDto.getCommuteCompanyName())
                                                  .check_in_time(postCommutesDto.getCheckInTime())
                                                  .check_out_time(postCommutesDto.getCheckInTime())
                                                  .memo(postCommutesDto.getMemo())
                                                  .build());*/
        Map<String, Object> echoMap = new HashMap<String, Object>();
        echoMap.put("userJwt", userJwt);
        echoMap.put("postCommutesDto", postCommutesDto);
        return ajaxResponseMgr.makeResponse("00000", echoMap);
    }

    @PutMapping("/commutes/{id}") // 특정 출퇴근 기록 수정
    public Map<String, Object> putCommutes(@PathVariable("id") long id,
                                           @RequestHeader("userJwt") String userJwt,
                                           @RequestBody PutCommutesDto putCommutesDto) {
        return null;
    }

    @DeleteMapping("/commutes/{id}") // 특정 출퇴근 기록 삭제
    public Map<String, Object> deleteCommutes(@PathVariable("id") long id,
                                              @RequestHeader("userJwt") String userJwt) {
        return null;
    }
}