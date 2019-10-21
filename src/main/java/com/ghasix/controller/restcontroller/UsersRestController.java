package com.ghasix.controller.restcontroller;

import com.ghasix.datas.dto.PostUsersDto;
import com.ghasix.manager.UserJwtManager;
import com.ghasix.service.UsersService;
import com.robi.data.ApiResult;
import com.robi.util.MapUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UsersRestController {

    private final Logger logger = LoggerFactory.getLogger(UsersRestController.class);

    private UsersService usersSvc;
    private UserJwtManager userJwtMgr;

    @GetMapping("/users/{email}/jwt")
    public ApiResult getUserJwt(@PathVariable("email") String email) {
        // Test
        return ApiResult.make(true, MapUtil.toMap("userJwt", userJwtMgr.encodeUserJwt(email)));
    }

    @PostMapping("/users")
    public ApiResult postUser(@RequestBody PostUsersDto postUsersDto) {
        // Test
        return usersSvc.insertUser(postUsersDto.getEmail(), postUsersDto.getName());
    }
}