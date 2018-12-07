package com.demo.lemonaid.demo.userDetail;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetail extends User {
    public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username,password,authorities);
    }

    public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities){
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserDetail(String username, String password, String s) {
        super(username, password, AuthorityUtils.createAuthorityList(s));
    }
}
