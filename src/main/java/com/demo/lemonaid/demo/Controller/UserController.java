package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Domain.Pharmacy;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.Service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/receiveId")
    @ResponseBody
    public Map<String, Object> firstVisit(HttpSession session){
        String DeviceId = userService.randomDeviceId();
        session.setAttribute("DeviceId",DeviceId);

        Map<String, Object> map = userService.deviceIdMap(DeviceId);
        return map;
    }//첫 방문

    @PostMapping("/receiveIdAgain")
    @ResponseBody
    public Map<String, Object> secondVisit(HttpServletRequest request, HttpSession session){
        String DeviceId = request.getHeader("DeviceId");

        Cookie [] cookies = request.getCookies();
        for(int i = 0; i < cookies.length; i++){
            System.out.println(cookies[i].getValue());
        }

        session.setAttribute("DeviceId",DeviceId);

        Map<String, Object> map = userService.deviceIdMap(DeviceId);
        return map;
    }//이 후 방문 클라이언트로 부터 받은 header값을 sesseion에 저장

    @GetMapping("/checkLogin")
    @ResponseBody
    public Map<String, Object> firstLogin(){
        Map<String, Object> map = userService.loginInfoMap();
        return map;
    }//check if the login.

    @GetMapping("/login")
    public String login(HttpServletResponse response, HttpServletRequest request) {
        return "Login";
    }//access to survey

    @PostMapping("/saveMapLocation")
    @ResponseBody
    public Map<String, Object> saveMapLocation(@RequestBody Pharmacy pharmacy, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();

        String deviceId = request.getHeader("Authorization");
        //현재 로그인한 유저와 디바이스 아이디를 비교 후 같다면 디비 저장
        if(userService.savePharmacy(deviceId, pharmacy)){
            map.put("isSave",true);
        }else {
            map.put("isSave", false);
        }
        return map;
    }
}
