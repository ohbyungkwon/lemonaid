package com.demo.lemonaid.demo.Service;

import com.demo.lemonaid.demo.Domain.Enums.Gender;
import com.demo.lemonaid.demo.Domain.Pharmacy;
import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.Dto.SimpleDto;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public String sha256(String str){
        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());

            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();

            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
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
}
