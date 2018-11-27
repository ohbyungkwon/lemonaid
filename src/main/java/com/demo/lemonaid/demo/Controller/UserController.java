package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.Pharmacy;
import com.demo.lemonaid.demo.Error.ApiSavePharmacy;
import com.demo.lemonaid.demo.Exception.DuplicateUserIdException;
import com.demo.lemonaid.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/api/receiveId")
    @ResponseBody
    public Map<String, Object> firstVisit(HttpSession session){
        String DeviceId = userService.randomDeviceId();

        if(userService.findDuplicate(DeviceId)){
            throw new DuplicateUserIdException("유저 아이디가 겹칩니다.");
        }

        session.setAttribute("DeviceId",DeviceId);
        Map<String, Object> map = userService.deviceIdMap(DeviceId);
        return map;
    }//첫 방문

//    @PostMapping("/api/receiveIdAgain")
//    @ResponseBody
//    public Map<String, Object> secondVisit(HttpServletRequest request, HttpSession session){
//        String DeviceId = request.getHeader("DeviceId");
//
//        Cookie [] cookies = request.getCookies();
//        for(int i = 0; i < cookies.length; i++){
//            System.out.println(cookies[i].getValue());
//        }
//
//        session.setAttribute("DeviceId",DeviceId);
//
//        Map<String, Object> map = userService.deviceIdMap(DeviceId);
//        return map;
//    }//이 후 방문 클라이언트로 부터 받은 header값을 sesseion에 저장

//    @GetMapping("/api/checkLogin")
//    @ResponseBody
//    public Map<String, Object> firstLogin(){
//        Map<String, Object> map = userService.loginInfoMap();
//        return map;
//    }//check if the login.

    @GetMapping("/login")
    public String login(HttpServletResponse response) {
        response.setHeader("Location", "login");

        Cookie cookie = new Cookie("state","login");
        cookie.setMaxAge(60*60*24);
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

        if(userService.savePharmacy(deviceId, pharmacy)){
            ApiSavePharmacy api =  ApiSavePharmacy.builder()
                    .name(pharmacy.getName())
                    .lat(pharmacy.getLat())
                    .lon(pharmacy.getLon())
                    .deviceId(deviceId)
                    .build();
            return new ResponseEntity<ApiSavePharmacy>(api, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

}
