package vn.vnpt.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import vn.vnpt.security.AppToken;

public class AppRequestContext {

	//private static AppRequestContext appRequestContext = null;
	//private AppToken appToken = null;
	
	public static HttpServletRequest getCurrentRequest() {
//		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
		HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
		Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
		return servletRequest;
	}

	public static AppRequestContext getCurrent() {
//		if (appRequestContext == null)
//			appRequestContext = new AppRequestContext(getCurrentRequest());
//		return appRequestContext;
		return new AppRequestContext(getCurrentRequest());
	}

	private HttpServletRequest request;

	private AppRequestContext(HttpServletRequest request) {
		this.request = request;
	}

	public AppToken getToken() {
//		if (appToken == null)
//			appToken = AppToken.getFromRequest(request);
//		return appToken;
		
		return AppToken.getToken(this.request);
	}

	public String getParameter(String name) {
		return this.request.getParameter(name);
	}

	public Map<String, Object> getParameterMap() {
		Map<String, String[]> map = this.request.getParameterMap();
		Map<String, Object> newMap = new HashMap<>();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			newMap.put(entry.getKey(), entry.getValue()[0]);
		}
		return newMap;
	}

	public String getClientIpAddress() {
		if (request != null) {
			String clientIP = request.getRemoteAddr();
			if(clientIP == null || clientIP.trim().length() == 0)
			{
				final String[] IP_HEADER_NAMES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
						"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
						"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };
				for (String header : IP_HEADER_NAMES) {
					String ipList = request.getHeader(header);
					if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
						clientIP = ipList.split(",")[0];
						break;
					}
				}
			}
			return clientIP;
		}
		return null;
	}

	public int getPhanVungID() {
		if (this.getToken() != null)
			return this.getToken().getPhanVungID();
		return -1;
	}

	public int getDonViID() {
		if (this.getToken() != null)
			return this.getToken().getDonViID();
		return -1;
	}

	public int getNhanVienID() {
		if (this.getToken() != null)
			return this.getToken().getNhanVienID();
		return -1;
	}

	public int getNguoiDungID() {
		if (this.getToken() != null)
			return this.getToken().getNguoiDungID();
		return -1;
	}

	public String getUserName() {
		if (this.getToken() != null)
			return this.getToken().getUserName();
		return null;
	}

	public String getMaNhanVien() {
		if (this.getToken() != null)
			return this.getToken().getMaNhanVien();
		return null;
	}

	public String getClientName() {
		if (request != null)
		{
			String name = request.getRemoteHost();
			if(name == null || name.trim().length() == 0)
				name = this.getClientIpAddress();
			return name;
		}
		return null;
	}
	
	public String getMaTinh() {
		if (this.getToken() != null) {
			return this.getToken().getMaTinh();
		}
		return null;
	}
}
