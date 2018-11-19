package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.UserDetail.UserDetail;
import com.demo.lemonaid.demo.session.UserIdSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        return createUser(user);
    }

    private UserDetail createUser(User user) {
        String author = "";
        switch (user.getUserType()) {
            case "3":
                author = "ROLE_TOCKER";
                break;
            case "2":
                author = "ROLE_ADMIN";
                break;
            default:
                author = "ROLE_USER";
                break;
        }
        UserDetail loginUser = new UserDetail(user.getEmail(), user.getPassword(), author);

        return loginUser;
    }

    public String RandomDeviceId(){
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

    public Map<String, Object> DeviceIdMap(String DeviceId){
        Map<String, Object> map = new HashMap<>();
        if(userRepository.findUserById(DeviceId) == null){
            User user = new User();
            user.setId(DeviceId);
            user.setPersonal_id("temp");
            user.setGender("-1");
            if(userRepository.save(user) != null) {
                map.put("isState", "success");
                map.put("DeviceId", DeviceId);
            }else{
                map.put("isState","fail");
            }
        }else{
            map.put("isState","previous");
            map.put("DeviceId", DeviceId);
        }
        return map;
    }
}
