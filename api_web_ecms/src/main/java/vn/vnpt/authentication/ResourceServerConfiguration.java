package vn.vnpt.authentication;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import vn.vnpt.authentication.config.OAuth2Properties;
import vn.vnpt.authentication.config.OAuth2SignatureVerifierClient;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private final String RESOURCE_ID = "restservice";

	private final OAuth2Properties oAuth2Properties;

	@Value("#{'${oauth2.oauth.url-permit-all}'.split('\\s*,\\s*')}")
	private List<String> urlPermitAlls;

	@Value("#{'${oauth2.oauth.url-permit-all-by-ip-range}'.split('\\s*,\\s*')}")
	private List<String> urlPermitAlByIpRanges;

	@Value("#{'${oauth2.oauth.ip-range-permit-all}'.split('\\s*,\\s*')}")
	private List<String> ipRangePermitAlls;

	public ResourceServerConfiguration(OAuth2Properties oAuth2Properties) {
		this.oAuth2Properties = oAuth2Properties;
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new RestAuthenticationFailureHandler();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http.antMatcher("/abc").authorizeRequests().antMatchers(unpack(urlPermitAlls, "/v2/api-docs")).permitAll();
		if (urlPermitAlByIpRanges != null && urlPermitAlByIpRanges.size() > 0) {
			String[] stringArray = new String[ipRangePermitAlls.size()];
			int index = 0;
			for (String ip : ipRangePermitAlls) {
				stringArray[index++] = "hasIpAddress('" + ip + "')";
			}
			String res = String.join(" or ", stringArray);
			http.authorizeRequests().antMatchers(unpack(urlPermitAlByIpRanges, "/actuator/**")).access(res);
		}
		http.authorizeRequests().antMatchers("/**").authenticated();
//		http.exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint());
//		http.exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint());
	}

	@Bean
	public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
		resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(OAuth2SignatureVerifierClient signatureVerifierClient) {
		return new OAuth2JwtAccessTokenConverter(oAuth2Properties, signatureVerifierClient);
	}

	@Bean
	@Qualifier("loadBalancedRestTemplate")
	public RestTemplate loadBalancedRestTemplate(RestTemplateCustomizer customizer) {
		RestTemplate restTemplate = new RestTemplate();
		customizer.customize(restTemplate);
		return restTemplate;
	}

	@Bean
	@Qualifier("vanillaRestTemplate")
	public RestTemplate vanillaRestTemplate() {
		return new RestTemplate();
	}

	@SafeVarargs
	public static String[] unpack(List<String> list, String... objects) {
		for (String object : objects) {
			list.addAll(Arrays.asList(object));
		}
		return list.toArray(new String[list.size()]);
	}
}
