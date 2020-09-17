package com.verizon.trainingdemo.ecomapigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);


    @Override
    public String filterType() {
        return "error";
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
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object e = ctx.get("error.exception");

        if (e != null && e instanceof ZuulException) {
            ZuulException zuulException = (ZuulException) e;
            logger.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);

            // Remove error code to prevent further error handling in follow up filters
            ctx.remove("error.status_code");

            ctx.setResponseBody("Something went wrong");
            ctx.getResponse().setContentType("application/json");
            ctx.setResponseStatusCode(500);
        }

        return null;

    }
}
