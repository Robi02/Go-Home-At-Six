package com.ghasix.controller.restcontroller;

import com.ghasix.domain.Commutes;
import com.ghasix.domain.CommutesRepository;
import com.ghasix.dto.PostCommutesDto;
import com.ghasix.dto.PutCommutesDto;

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

    private CommutesRepository commutesRepository;

    @GetMapping("/commutes/{id}") // 특정 출퇴근 기록 조회
    public String getCommutesById(@PathVariable("id") long id,
                                  @RequestHeader("userJwt") String userJwt) {
        return "TESTING";
    }

    @GetMapping("/commutes/time/all") // 출퇴근 기록 전체 조회
    public String getCommutesTimeAll(@RequestHeader("userJwt") String userJwt) {
        return "TESTING";
    }

    @GetMapping("/commutes/time/{beginTime}-{endTime}") // 출퇴근 기록 범위 조회
    public String getCommutesTimeBetween(@PathVariable("beginTime") long beginTime,
                                         @PathVariable("endTime") long endTime,
                                         @RequestHeader("userJwt") String userJwt) {
        return "TESTING";
    }

    @PostMapping("/commutes") // 출퇴근 기록 추가
    public String postCommutes(@RequestHeader("userJwt") String userJwt,
                               @RequestBody PostCommutesDto postCommutesDto) {
        logger.info("postCommutesDto:" + postCommutesDto.toString());
        commutesRepository.save(Commutes.builder().commute_companay_name(postCommutesDto.getCommuteCompanyName())
                                                  .check_in_time(postCommutesDto.getCheckInTime())
                                                  .check_out_time(postCommutesDto.getCheckInTime())
                                                  .memo(postCommutesDto.getMemo())
                                                  .build());
        return "TESTING";
    }

    @PutMapping("/commutes/{id}") // 특정 출퇴근 기록 수정
    public String putCommutes(@PathVariable("id") long id,
                              @RequestHeader("userJwt") String userJwt,
                              @RequestBody PutCommutesDto putCommutesDto) {
        return "TESTING";
    }

    @DeleteMapping("/commutes/{id}") // 특정 출퇴근 기록 삭제
    public String deleteCommutes(@PathVariable("id") long id,
                                 @RequestHeader("userJwt") String userJwt) {
        return "TESTING";
    }
}