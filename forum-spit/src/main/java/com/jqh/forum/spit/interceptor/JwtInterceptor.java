package com.jqh.forum.spit.interceptor;


import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 几米
 * @Date: 2019/5/30 12:47
 * @Description: 权限验证拦截器, 主要是对后续请求中的需要进行权限验证的操作进行简化
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //无论如何都放行，只对有token的进行一个简单封装以简化业务里的操作
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization)) {
            //如果包含有头信息就进行解析
            if (authorization.startsWith("Bearer ")) {
                //如果还是以这个开头的
                //拿到令牌
                String token = authorization.substring(7);
                //进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null && roles.equals("admin")) {
                        request.setAttribute("claims_admin", claims);//如果是admin
                    }
                    if (roles != null && roles.equals("user")) {
                        request.setAttribute("claims_user", claims);//如果是user
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("令牌不正确");
                }
            }
        }
        return true;
    }
}
