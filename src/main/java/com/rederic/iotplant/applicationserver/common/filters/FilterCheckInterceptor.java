package com.rederic.iotplant.applicationserver.common.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rederic.iotplant.applicationserver.common.util.WebTokenUtil;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FilterCheckInterceptor implements Filter {

    private static List releaseList = new ArrayList();
    private static List accessTokenKeyList;


    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws JsonProcessingException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://59.110.142.242:8013");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization, token");
        String wxuser = httpRequest.getHeader("wxuser");

        try {
            if(wxuser != null && wxuser.equals("xcx")) {
                request.setAttribute("userid", "1");
                request.setAttribute("username", "xcx");
                chain.doFilter(request, response);
            } else {
                String e = ObjectUtils.toString(httpRequest.getRequestURL());
                Iterator webtoken = releaseList.iterator();

                while(webtoken.hasNext()) {
                    String url = (String)webtoken.next();
                    if(e.indexOf(url) > 0) {
                        chain.doFilter(request, response);
                        return;
                    }
                }

                String webtoken1 = httpRequest.getHeader("token");
                if(webtoken1 != null && webtoken1.length() > 7) {
                    Iterator url1 = accessTokenKeyList.iterator();

                    while(url1.hasNext()) {
                        String key = (String)url1.next();
                        Map tUserMap = WebTokenUtil.parseWebToken(webtoken1, key);
                        request.setAttribute("userid", tUserMap.get("id"));
                        request.setAttribute("username", tUserMap.get("username"));
                        if(tUserMap != null) {
                            chain.doFilter(request, response);
                            return;
                        }
                    }
                }

                chain.doFilter(request, response);
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        }
    }

    public void destroy() {
    }

    static {
        releaseList.add("/user/login");
        releaseList.add("/netgate-server/img/");
        releaseList.add("/netgate-server/");
        accessTokenKeyList = new ArrayList();
        accessTokenKeyList.add("tUser");
    }
}