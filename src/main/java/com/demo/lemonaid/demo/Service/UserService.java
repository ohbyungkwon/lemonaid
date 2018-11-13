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

@Service(value = "UserService")
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserIdSession userIdSession;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        return new UserDetail(user.getEmail(), user.getPassword(), "2");
    }

    public String JsonParseUserId(String id){
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject JsonId = (JSONObject) jsonParser.parse(id);

            userIdSession.setTempUserId(JsonId.get("TempUserId").toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return userIdSession.getTempUserId();
    }
}
