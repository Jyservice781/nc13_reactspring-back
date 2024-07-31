package com.nc13.react_board.controller;


import com.nc13.react_board.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {

    @RequestMapping("authSuccess")
    public ResponseEntity<Map<String, Object>> authSuccess(Authentication authentication) {
        // ResponseEntity<>
        // status 코드를 같이 넘겨줄 수 있음
        // 어떤 값을 넣어서 보내준다면 <> 안에 내용을 채워주고
        // 아니라면 void 를 사용해서 넘겨줄 수 있다.
        Map<String, Object> resultMap = new HashMap<>();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        // 기존의 데이터에서 필요한 부분만 돌려보내기 위해 줄였음
        resultMap.put("result", "success");
        resultMap.put("id", userDTO.getId());
        resultMap.put("nickname", userDTO.getNickname());
        resultMap.put("role", userDTO.getRole());

        return ResponseEntity.ok(resultMap);
    }

    @RequestMapping("authFail")
    public ResponseEntity<Map<String,Object>> authFail(){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "fail");

        return ResponseEntity.ok(resultMap);
    }

    @RequestMapping("logOutSuccess")
    public ResponseEntity<Void> logOutSuccess(){
        System.out.println("log out success!!");

        return ResponseEntity.ok().build();
    }
}