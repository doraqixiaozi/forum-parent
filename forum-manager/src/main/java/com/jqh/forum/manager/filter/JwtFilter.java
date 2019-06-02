package com.jqh.forum.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 几米
 * @Date: 2019/6/1 14:58
 * @Description: web模块儿的zuul只需要转发头信息就可以了，而作为权限要求高的manager模块儿则先做一次权限验证以减轻其他模块儿压力
 */
@Component
public class JwtFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (request.getMethod().equals("OPTIONS")){
            return null;//放行分发的方法
        }
        if (request.getRequestURI().contains("login")){
            return null;//放行登录的方法
        }

        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization)){
            //如果有相应的头信息存在则放行且进行解析以便其他模块儿使用
            if(authorization.startsWith("Bearer ")) {
                //如果还是以这个开头的
                //拿到令牌
                String token = authorization.substring(7);
                //进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null && roles.equals("admin")) {
                        //转发头信息并且放行
                        currentContext.addZuulRequestHeader("Authorization",authorization);
                        return null;//放行
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        currentContext.setSendZuulResponse(false);//没有权限不放行
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足,禁止访问");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
