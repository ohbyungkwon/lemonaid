package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.Pharmacy;
import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Exception.CantFindUser;
import com.demo.lemonaid.demo.Repository.PharmacyRepository;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.UserDetail.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service(value = "UserService")
@Transactional
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PharmacyRepository pharmacyRepository;

    @Autowired
    public UserService(UserRepository userRepository, PharmacyRepository pharmacyRepository){
        this.userRepository = userRepository;
        this.pharmacyRepository = pharmacyRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        return createUser(user);
    }

    private UserDetail createUser(User user) {
        String author = "";
        switch (user.getUserType()) {
            case "3":
                author = "ADMIN";
                break;
            case "2":
                author = "MASTER_USER";
                break;
            default:
                author = "USER";
                break;
        }
        UserDetail loginUser = new UserDetail(user.getEmail(), user.getPassword(), author);

        return loginUser;
    }

    public String randomDeviceId(){
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < 20; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }

    public Map<String, Object> deviceIdMap(String DeviceId){
        Map<String, Object> map = new HashMap<>();
        if(userRepository.findUserById(DeviceId) == null){
            User user = new User();
            user.setId(DeviceId);
            user.setPersonal_id("temp");
            user.setGender("-1");
            if(userRepository.save(user) != null) {
                map.put("isState", 1);
                map.put("DeviceId", DeviceId);
            }else{
                map.put("isState",-1);
            }
        }else{
            map.put("isState",0);
            map.put("DeviceId", DeviceId);
        }
        return map;
    }

    public Map<String, Object> loginInfoMap(){
        Map<String, Object> map = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().equals("anonymousUser"))
            map.put("isLogin","0");
        else
            map.put("isLogin","1");
        return map;
    }

    public boolean savePharmacy(String deviceId, Pharmacy pharmacy){
        User user = userRepository.findUserById(deviceId);
        if(user == null){
            throw new CantFindUser("해당 유저는 존재하지 않음");
        }
        else {
            if(pharmacyRepository.save(pharmacy) != null){
                return true;
            }else{ return false; }
        }
    }

    public boolean findDuplicate(String DeviceId){
        if(userRepository.findUserById(DeviceId) != null){
            return true;
        }else return false;
    }
}
