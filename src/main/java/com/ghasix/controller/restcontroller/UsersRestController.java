package com.ghasix.controller.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.ghasix.datas.dto.PostUsersDto;
import com.ghasix.manager.AjaxResponseManager;
import com.ghasix.manager.UserJwtManager;
import com.ghasix.service.UsersService;

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
    private AjaxResponseManager ajaxResponseMgr;

    @GetMapping("/users/{email}/jwt")
    public Map<String, Object> getUserJwt(@PathVariable("email") String email) {
        // Test
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("userJwt", userJwtMgr.encodeUserJwt(email));
        return ajaxResponseMgr.makeResponse(rtMap);
    }

    @PostMapping("/users")
    public Map<String, Object> postUser(@RequestBody PostUsersDto postUsersDto) {
        // Test
        return usersSvc.insertUser(postUsersDto.getEmail(), postUsersDto.getName());
    }
}