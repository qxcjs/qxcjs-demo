package com.qxcjs.demo.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Log4j2
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("登录失败!", e);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write("登录失败");
        out.flush();
        out.close();
    }


    private void setResult(AuthenticationException exception) {
        log.error("登录失败", exception);
//        if (exception instanceof LockedException) {
//            return Result.fail(E10007);
//        } else if (exception instanceof CredentialsExpiredException) {
//            return Result.fail(E10008);
//        } else if (exception instanceof AccountExpiredException) {
//            return Result.fail(E10009);
//        } else if (exception instanceof DisabledException) {
//            return Result.fail(E10010);
//        } else if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
//            return Result.fail(E10011);
//        } else if (exception instanceof RememberMeAuthenticationException) {
//            return Result.fail(E10012);
//        }
//        return Result.fail(E10013);
    }
}
