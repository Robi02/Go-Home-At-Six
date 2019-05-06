package com.ghasix.service;

import java.util.Map;

import com.ghasix.datas.dto.PostCommutesDto;
import com.ghasix.datas.dto.PutCommutesDto;

public interface ICommutesService {

    public Map<String, Object> selectCommutesAll(String userJwt);
    public Map<String, Object> selectCommutesById(String userJwt, long commuteId);
    public Map<String, Object> selectCommutesByTime(String userJwt, long beginTime, long EndTime);
    public Map<String, Object> insertCommutes(String userJwt, PostCommutesDto postCommutesDto);
    public Map<String, Object> updateCommutes(String userJwt, PutCommutesDto updateCommutesDto);
    public Map<String, Object> deleteCommutes(String userJwt);
}