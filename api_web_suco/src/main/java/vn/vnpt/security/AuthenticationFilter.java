package vn.vnpt.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/*
 * public class AuthenticationFilter extends OncePerRequestFilter {
 * 
 * @Override protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException { AppToken appToken =
 * AppToken.getFromRequest(request); if(!appToken.isValid()) { return; }
 * SecurityContextHolder.getContext().setAuthentication(new
 * UsernamePasswordAuthenticationToken("admin", null, Collections.emptyList()));
 * filterChain.doFilter(request, response); } }
 */

public class AuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// AppToken.getFromRequest(HttpServletRequest.class.cast(request));
		// AppToken appToken = AppToken.getFromRequest((HttpServletRequest) request);

		// AppToken.configure(((HttpServletRequest)
		// request).getHeader(HttpHeaders.AUTHORIZATION));
		AppToken token = AppToken.getToken((HttpServletRequest) request);
		if (token != null) {
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(token.getUserName(), null, Collections.emptyList()));
		} else
			SecurityContextHolder.getContext().setAuthentication(null);

		chain.doFilter(request, response);
	}
}
