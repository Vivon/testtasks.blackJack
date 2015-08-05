package com.testtasks.blackJack.server.filters;

import com.testtasks.blackJack.server.common.Consts;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoggerFilter implements Filter {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper =
                new RequestWrapper((HttpServletRequest) request, IOUtils.toByteArray(request.getInputStream()));
        ResponseWrapper responseWrapper =
                new ResponseWrapper((HttpServletResponse) response, response.getOutputStream());
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        loggingRequest(requestWrapper, httpServletRequest.getHeader(Consts.ACCOUNT_ID_REQUEST_ALIAS),
                httpServletRequest.getHeader(Consts.GAME_ID_REQUEST_ALIAS));
        chain.doFilter(requestWrapper, responseWrapper);
        loggingResponse(responseWrapper);
    }

    private void loggingRequest(RequestWrapper request, String accountId, String gameId) throws UnsupportedEncodingException {
        String requestBody = new String(request.getRequestBytes(), CharEncoding.UTF_8);
        LOGGER.info("Request body: {}, accountId({}) and gameId({}) in header", new Object[]{requestBody, accountId, gameId});
    }

    private void loggingResponse(ResponseWrapper response) throws UnsupportedEncodingException {
        String responseBody = new String(response.getResponseBytes(), CharEncoding.UTF_8);
        if (!responseBody.isEmpty()) {
            LOGGER.info("Response body: {}", responseBody);
        }
    }

    @Override
    public void destroy() {

    }
}
