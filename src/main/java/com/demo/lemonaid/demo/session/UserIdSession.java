package com.demo.lemonaid.demo.session;

import com.demo.lemonaid.demo.Domain.User;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserIdSession {
    private User TempUser;
}
