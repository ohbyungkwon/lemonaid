package com.demo.lemonaid.demo.controller;

import com.demo.lemonaid.demo.domain.Pharmacy;
import com.demo.lemonaid.demo.dto.ApiSavePharmacy;
import com.demo.lemonaid.demo.dto.SimpleDto;
import com.demo.lemonaid.demo.service.UserService;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class UserController {
    private UserService userService;
    private UserIdSession userIdSession;

    @Autowired
    UserController(UserService userService, UserIdSession userIdSession){
        this.userService = userService;
        this.userIdSession = userIdSession;
    }

    @PostMapping("/api/receiveId")
    @ResponseBody
    public ResponseEntity<SimpleDto.ReciveMap> firstVisit(HttpServletResponse response){
        String deviceId = UUID.randomUUID().toString();

        if(userService.findDuplicate(deviceId) != null){
            deviceId = UUID.randomUUID().toString();
        }

        userService.saveUser(deviceId);
        SimpleDto.ReciveMap receiveMap = SimpleDto.ReciveMap.builder()
                .isState(1)
                .deviceId(deviceId)
                .build();

        return new ResponseEntity<>(receiveMap, HttpStatus.OK);
    }//첫 방문

    @GetMapping("/login")
    public String login(HttpServletResponse response) {
        response.setHeader("Location", "login");
        Cookie cookie = new Cookie("state","login");
        response.addCookie(cookie);
        return "Login";
    }//access to survey
    //파라미터로 어디로부터의 접근인지 받아서 리다이렉트를 다르게한다.
    //만약 메인에서 로그인을 성공한다면 빈페이지를 출력하고 response를 담고
    //그렇지 않다고 하면 order 페이지를 출력하고 response를 담는다.

    @PostMapping("/api/saveMapLocation")
    @ResponseBody
    public ResponseEntity<ApiSavePharmacy> saveMapLocation(@RequestBody Pharmacy pharmacy, HttpServletRequest request){
        String deviceId = request.getHeader("Authorization");
        pharmacy.setDeviceId(deviceId);
        //현재 로그인한 유저와 디바이스 아이디를 비교 후 같다면 디비 저장

        if(userService.savePharmacy(deviceId, pharmacy) != null){
            ApiSavePharmacy api =  ApiSavePharmacy.builder()
                    .name(pharmacy.getName())
                    .lat(pharmacy.getLat())
                    .lon(pharmacy.getLon())
                    .deviceId(deviceId)
                    .build();
            return new ResponseEntity<>(api, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/setRefund")
    @ResponseBody
    public ResponseEntity<SimpleDto> setRefund(@RequestParam(name = "is_need_refund") boolean isNeedRefund, @CookieValue("deviceId") String deviceId){
        if(!userIdSession.isAnonymouse())
            return ResponseEntity.badRequest().build();

        userService.setUserRefund(isNeedRefund, deviceId);

        SimpleDto simpleDto = SimpleDto.builder()
                .message("success")
                .build();

        return new ResponseEntity<>(simpleDto, HttpStatus.OK);
    }

//    @GetMapping("/api/getRefund")
//    @ResponseBody
//    public ResponseEntity<SimpleDto.Refund> getRefund(HttpSession session){
//        if(!userIdSession.isAnonymouse())
//            return ResponseEntity.badRequest().build();
//
//        String deviceId = session.getAttribute("deviceId").toString();
//        boolean isNeedRefund = userService.getUserRefund(deviceId).isNeedRefund();
//
//        SimpleDto.Refund refund = SimpleDto.Refund.builder()
//                .isNeedRefund(isNeedRefund)
//                .build();
//
//        return new ResponseEntity<>(refund, HttpStatus.OK);
//    }
}
