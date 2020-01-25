package com.absonworld.mySpringBootRestApp;


import com.absonworld.mySpringBootRestApp.service.H2JDBCService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;

@Component
@Aspect
public class AuthenticationAspect {
    H2JDBCService service = new H2JDBCService();

    @Before("execution(* com.absonworld.mySpringBootRestApp.controller.UserController.*(..))")
    public void initElements(/*@CookieValue(name = "sessionId") String sessionId*/) {
        System.out.println("Authentication Happening here for session :");
        //boolean isValidSession = service.validateSession(sessionId);
        //System.out.println("isValidSession: "+isValidSession);
        //your custom logic that has to be executed before the `clickThis`
    }
}
