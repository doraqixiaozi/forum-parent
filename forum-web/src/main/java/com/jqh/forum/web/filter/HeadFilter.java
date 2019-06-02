package com.jqh.forum.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 几米
 * @Date: 2019/6/1 14:11
 * @Description: 因为网关转发会丢失头信息，而头信息带了jwt验证，所在过滤器把头信息给补回来
 */
@Component
public class HeadFilter extends ZuulFilter {
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
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization)){
            //如果有相应的头信息存在就要做处理传给转发的请求
            currentContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }
}
