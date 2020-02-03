package com.absonworld.mySpringBootRestApp;


import com.absonworld.mySpringBootRestApp.service.H2JDBCService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

 import javax.ws.rs.NotAuthorizedException;
@Component
@Aspect
public class AuthenticationAspect {
    H2JDBCService service = new H2JDBCService();

    @Before("execution(* com.absonworld.mySpringBootRestApp.controller.UserController.*(..))")
    public void initElements( JoinPoint jp /*@CookieValue(name = "sessionId") String sessionId*/) throws HttpClientErrorException.Unauthorized {
        String sessionId= jp.getArgs()[0].toString();
        System.out.println( "SessionId:"+sessionId);
        System.out.println("Authentication Happening here for session :");

        if(!(jp.getSignature().getName().contains("validate")|| jp.getSignature().getName().contains("saveUser"))) {
            boolean isValidSession = service.validateSession(sessionId);
            System.out.println("isValidSession: " + isValidSession);
            if (!isValidSession) {
                throw new NotAuthorizedException("ambiguous-account-no-mail");
            }
        }
        //your custom logic that has to be executed before the `clickThis`
    }
}
