package com.demo.lemonaid.demo.Controller;

import com.demo.lemonaid.demo.Service.UserService;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;
    @Autowired private UserIdSession userIdSession;

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
}
