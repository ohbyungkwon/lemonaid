package com.demo.lemonaid.demo.service;

import com.demo.lemonaid.demo.domain.enums.Gender;
import com.demo.lemonaid.demo.domain.Pharmacy;
import com.demo.lemonaid.demo.domain.User;
import com.demo.lemonaid.demo.exception.CantFindUserException;
import com.demo.lemonaid.demo.repository.PharmacyRepository;
import com.demo.lemonaid.demo.repository.UserRepository;
import com.demo.lemonaid.demo.userDetail.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service(value = "UserService")
@Transactional
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PharmacyRepository pharmacyRepository;
    private HttpServletRequest request;

    @Autowired
    public UserService(UserRepository userRepository, PharmacyRepository pharmacyRepository, HttpServletRequest request){
        this.userRepository = userRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.request = request;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);//기존 정보

        String deviceId = null;

        Cookie[] cookies = request.getCookies();
        for(int i =0; i < cookies.length; i++){
            if(cookies[i].getName().equals("deviceId"))
                deviceId = cookies[i].getValue();
        }
        if(deviceId == null)
            throw new CantFindUserException("유저를 찾을 수 없음");

        if(!user.getId().equals(deviceId)){
            User userTemp = user;
            userRepository.delete(user);//기존 유저를 지움

            userTemp.setId(deviceId);
            userRepository.save(userTemp);
        }
        return createUser(user);
    }

    private UserDetail createUser(User user) {
        String author;
        switch (user.getUserType()) {
            case "3":
                author = "ROLE_ADMIN";
                break;
            case "2":
                author = "ROLE_MASTER_USER";
                break;
            default:
                author = "ROLE_USER";
                break;
        }
        return new UserDetail(user.getEmail(), user.getPassword(), author);
    }

    public User saveUser(String deviceId){
        User user = new User();
        user.setId(deviceId);
        user.setPersonalId("temp");
        user.setGender(Gender.UNKNOWN.toString());

        return userRepository.save(user);
    }

    public Pharmacy savePharmacy(String deviceId, Pharmacy pharmacy){
        User user = userRepository.findUserById(deviceId);
        if(user == null){
            throw new CantFindUserException("해당 유저는 존재하지 않음");
        }
        else {
            return pharmacyRepository.save(pharmacy);
        }
    }

    public User findDuplicate(String deviceId){
        return userRepository.findUserById(deviceId);
    }

    public User setUserRefund(boolean isNeedRefund, String userId){
        User userTemp = userRepository.findUserById(userId);
        if(userTemp == null) {
            throw new CantFindUserException("해당 유저는 존재하지 않음");
        }

        userTemp.setNeedRefund(isNeedRefund);
        return userRepository.save(userTemp);
    }

//    public User getUserRefund(String userId) {
//        User userTemp = userRepository.findUserById(userId);
//        if (userTemp == null){
//            throw new CantFindUserException("해당 유저는 존재하지 않음");
//        }
//        return userTemp;
//    }
}
