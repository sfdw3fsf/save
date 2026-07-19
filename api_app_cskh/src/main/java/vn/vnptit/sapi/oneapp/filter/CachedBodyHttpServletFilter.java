package vn.vnptit.sapi.oneapp.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.vnptit.sapi.oneapp.interceptor.CachedBodyHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CachedBodyHttpServletFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getRequestURL().toString().endsWith("/data-upload")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            try {
                CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                        new CachedBodyHttpServletRequest(httpServletRequest);
                filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
            } catch (org.apache.catalina.connector.ClientAbortException e) {
                System.out.println("Client dong ket noi");
            } catch (IOException e) {
                if (e.getCause() instanceof java.io.EOFException) {
                    System.out.println("Client dong ket noi");
                } else {
                    throw e;
                }
            }
        }
    }
}
