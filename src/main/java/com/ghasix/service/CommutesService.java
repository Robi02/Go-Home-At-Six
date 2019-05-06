package com.ghasix.service;

import java.util.Map;

import com.ghasix.datas.domain.Commutes;
import com.ghasix.datas.domain.CommutesRepository;
import com.ghasix.datas.dto.PostCommutesDto;
import com.ghasix.datas.dto.PutCommutesDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommutesService implements ICommutesService {

    private final Logger logger = LoggerFactory.getLogger(CommutesService.class);

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
        commutesRepo.save(Commutes.builder().commute_companay_name(postCommutesDto.getCommuteCompanyName())
                                            .check_in_time(postCommutesDto.getCheckInTime())
                                            .check_out_time(postCommutesDto.getCheckInTime())
                                            .memo(postCommutesDto.getMemo())
                                            .build());
        return null;
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