package com.ghasix.service;

import com.ghasix.datas.dto.PostCommutesDto;
import com.ghasix.datas.dto.PutCommutesDto;
import com.robi.data.ApiResult;

public interface ICommutesService {

    public ApiResult selectCommutesAll(String userJwt);
    public ApiResult selectCommutesById(String userJwt, long commutesId);
    public ApiResult selectCommutesByTime(String userJwt, long beginTime, long endTime);
    public ApiResult selectCommutesLast(String userJwt);
    public ApiResult insertCommutes(String userJwt, PostCommutesDto postCommutesDto);
    public ApiResult updateCommutes(String userJwt, long commutesId, PutCommutesDto updateCommutesDto);
    public ApiResult deleteCommutes(String userJwt, long commutesId);
}