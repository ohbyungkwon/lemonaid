package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.Enums.Gender;
import com.demo.lemonaid.demo.Domain.Pharmacy;
import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Exception.CantFindUserException;
import com.demo.lemonaid.demo.Repository.PharmacyRepository;
import com.demo.lemonaid.demo.Repository.UserRepository;
import com.demo.lemonaid.demo.UserDetail.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;

@Service(value = "UserService")
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PharmacyRepository pharmacyRepository;
    private HttpSession session;

    @Autowired
    public UserService(UserRepository userRepository, PharmacyRepository pharmacyRepository, HttpSession session){
        this.userRepository = userRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.session = session;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        String deviceId = session.getAttribute("deviceId").toString();
        User userTemp = userRepository.findUserById(deviceId);

        if(!user.getId().equals(deviceId)){
            userRepository.delete(user);
            user.setId(deviceId);
            userRepository.delete(userTemp);
            userRepository.save(user);
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

    public User setUserRefund(boolean isNeedRefund, String userEmail){
        User userTemp = userRepository.findUserByEmail(userEmail);
        if(userTemp == null) {
            throw new CantFindUserException("해당 유저는 존재하지 않음");
        }

        userTemp.setNeedRefund(isNeedRefund);
        return userRepository.save(userTemp);
    }

    public User getUserRefund(String userEmail) {
        User userTemp = userRepository.findUserByEmail(userEmail);
        if (userTemp == null){
            throw new CantFindUserException("해당 유저는 존재하지 않음");
        }
        return userTemp;
    }
}
