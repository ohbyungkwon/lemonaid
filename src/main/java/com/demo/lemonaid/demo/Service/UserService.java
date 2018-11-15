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

import java.util.ArrayList;
import java.util.Collections;

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

    public String JsonParseUserId(String id){
        String ParsedID = "";
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject JsonId = (JSONObject) jsonParser.parse(id);

            ParsedID = JsonId.get("TempUserId").toString();
            System.out.println(ParsedID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ParsedID;
   }
}
