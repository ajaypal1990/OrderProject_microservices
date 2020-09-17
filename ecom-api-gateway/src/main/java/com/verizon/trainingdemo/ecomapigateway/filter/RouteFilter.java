package com.verizon.trainingdemo.ecomapigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RouteFilter extends ZuulFilter {

    private static  final Logger logger = LoggerFactory.getLogger(RouteFilter.class);

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest =  context.getRequest();
        //System.out.println( "ROUTE FILTER : Request method " + httpServletRequest.getMethod()+ "URI " + httpServletRequest.getRequestURL());
        logger.debug( "ROUTE FILTER : Request method " + httpServletRequest.getMethod()+ "URI " + httpServletRequest.getRequestURL());
        return null;
    }
}
