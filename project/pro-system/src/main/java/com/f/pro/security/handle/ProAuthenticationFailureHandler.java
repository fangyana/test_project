package com.f.pro.security.handle;

import com.f.pro.common.exception.ValidateCodeException;
import com.f.pro.common.util.R;
import com.f.pro.security.util.SecurityUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈智威
 * @Description
 * @Date 2019/9/6 21:29
 */
@Component
public class ProAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String message;

        if (e instanceof ValidateCodeException) {
            message = e.getMessage();
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        response.setContentType("application/json;charset=utf-8");
        SecurityUtil.writeJavaScript(R.error(message), response);
    }
}
