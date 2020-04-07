package com.f.pro.security.handle;

import com.f.pro.common.util.R;
import com.f.pro.security.domaim.LoginType;
import com.f.pro.security.domaim.ProSecurityUser;
import com.f.pro.security.util.JwtUtil;
import com.f.pro.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ProAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${project.url.address}")
    private String url;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof ProSecurityUser) {
            ProSecurityUser userDetail = (ProSecurityUser) authentication.getPrincipal();
            //存储认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //生成token
            String s = JwtUtil.generateToken(userDetail);
            // 是短信登录返回token
            if (LoginType.sms.equals(userDetail.getLoginType())) {
                SecurityUtil.writeJavaScript(R.ok(s), response);
            }
        }
    }
}
