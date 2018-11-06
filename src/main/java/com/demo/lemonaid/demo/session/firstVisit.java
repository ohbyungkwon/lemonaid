package com.demo.lemonaid.demo.session;

import com.demo.lemonaid.demo.Domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope(value ="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class firstVisit {

}
