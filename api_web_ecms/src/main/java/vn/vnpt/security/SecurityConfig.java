package vn.vnpt.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.cors.CorsConfiguration;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**",
			// other public endpoints of your API may be appended to this array
			"/login", "/actuator/health" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// No need authentication.
				.antMatchers("/").permitAll() //
				.antMatchers(AUTH_WHITELIST).permitAll()
				// Need authentication.
				.anyRequest().authenticated().and()
				.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
			ApiResult apiResult = new ApiResult(Message.Codes.BSS_00000403);
			apiResult.setMessageDetail(e.getMessage());
			apiResult.setError(String.valueOf(HttpStatus.FORBIDDEN.value()));
			ObjectMapper mapper = new ObjectMapper();
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write(mapper.writeValueAsString(apiResult));
		});
	}
}
