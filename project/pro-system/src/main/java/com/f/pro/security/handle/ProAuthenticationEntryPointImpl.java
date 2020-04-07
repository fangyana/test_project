package com.f.pro.security.handle;

import cn.hutool.http.Status;
import lombok.extern.slf4j.Slf4j;
import com.f.pro.security.util.SecurityUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.f.pro.common.util.R;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Slf4j
@Component
public class ProAuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("请求访问: " + request.getRequestURI() + " 接口， 经jwt认证失败，无法访问系统资源.");
        SecurityUtil.writeJavaScript(R.error(Status.HTTP_UNAUTHORIZED, "请求访问:" + request.getRequestURI() + "接口,经jwt 认证失败,无法访问系统资源"), response);
    }
}
