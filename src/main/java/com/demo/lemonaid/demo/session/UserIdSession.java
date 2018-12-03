package com.demo.lemonaid.demo.session;

import com.demo.lemonaid.demo.Domain.User;
import com.demo.lemonaid.demo.UserDetail.UserDetail;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserIdSession {
    private Authentication authentication;

    public UserIdSession(){
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public String getName(){
        UserDetail userDetail = (UserDetail) this.authentication.getPrincipal();
        return userDetail.getUsername();
    }

    public String getAuthor(){
        return String.valueOf(authentication.getAuthorities().toArray()[0]);
    }
}
