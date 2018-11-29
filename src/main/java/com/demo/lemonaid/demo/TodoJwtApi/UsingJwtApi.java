package com.demo.lemonaid.demo.TodoJwtApi;

import com.demo.lemonaid.demo.Domain.Pharmacy;
import com.demo.lemonaid.demo.Dto.ApiSavePharmacy;
import com.demo.lemonaid.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class UsingJwtApi {
    private UserService userService;

    @Autowired
    UsingJwtApi(UserService userService){
        this.userService = userService;
    }

    // TODO : 로그인한 유저만 접근 할 수 있는 API
    @PostMapping("/jwt/saveMapLocation")
    @ResponseBody
    public ResponseEntity<ApiSavePharmacy> saveMapLocation(@RequestBody Pharmacy pharmacy, HttpServletRequest request){
        String deviceId = request.getHeader("DeviceId");

        pharmacy.setDeviceId(deviceId);

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

    // TODO : 모든 사용자가 접근할 수 있는 API
    @GetMapping("/jwt/disease")
    @ResponseBody
    public Map<String, Object> disease(){
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @GetMapping("/jwt/intro")
    @ResponseBody
    public Map<String, Object> disease_choice(@RequestParam String disease_name) {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @GetMapping("/jwt/review")
    @ResponseBody
    public Map<String, Object> review () {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}

