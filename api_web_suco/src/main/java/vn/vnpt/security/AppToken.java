package vn.vnpt.security;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;

import com.nimbusds.jwt.JWTParser;

public class AppToken {
	
	//private static AppToken appToken = null;
	
	public static AppToken getToken(HttpServletRequest request)
	{
		AppToken t = new AppToken(request.getHeader(HttpHeaders.AUTHORIZATION));
		if(t.isValid())
			return t;
		return null;
		//return new AppToken(request.getHeader(HttpHeaders.AUTHORIZATION));
	}
	
//	public static void configure(String headerAuthorization)
//	{
//		AppToken item = new AppToken(headerAuthorization);
//		if(item.isValid())
//			appToken = item;
//		else
//			appToken = null;
//	}
	
    private final String BEARER_TYPE = "Bearer";

    private String value = null;
    private Map<String, Object> claims = null;

    private AppToken(String headerAuthorizationString) { 
    	this.value = headerAuthorizationString;
    }

    public Map<String, Object> getClaims() {
    	if(claims == null)
    	{
			try {
				String encodedValue = getEncodedValue();
				if(!(encodedValue == null || encodedValue == ""))
					claims = JWTParser.parse(encodedValue).getJWTClaimsSet().getClaims();
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
        return claims;
    }

    public String getValue() {
        return value;
    }

    public String getEncodedValue() {

        if (!ObjectUtils.isEmpty(this.value) && (value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
            return value.substring(BEARER_TYPE.length()).trim();
        }

        return value;
    }

    public Object getProperty(String name) {
        return getClaims() == null ? null : getClaims().get(name);
    }

    public int getPhanVungID()
    {
    	Object p = this.getProperty("id_tinhthanh");
    	if(p != null)
    		return Integer.valueOf(p.toString());
    	return -1;
    }

    public String getUserName()
    {
    	Object p = this.getProperty("user_name");
    	if(p != null)
    		return p.toString();
    	return null;
    }

    public int getNhanVienID()
    {
    	Object p = this.getProperty("id_nhanvien_dhsx");
    	if(p != null)
    		return Integer.valueOf(p.toString());
    	return -1;
    }

    public String getMaNhanVien()
    {
    	Object p = this.getProperty("ma_nv");
    	if(p != null)
    		return p.toString();
    	return null;
    }
    
    public boolean isValid()
    {
    	return (!(getPhanVungID() == -1 || getNhanVienID() == -1 || getUserName() == null || getMaNhanVien() == null || getNguoiDungID() == -1));
    }

    public int getDonViID()
    {
    	Object p = this.getProperty("id_donvi");
    	if(p != null)
    		return Integer.valueOf(p.toString());
    	return -1;
    }

    public int getNguoiDungID()
    {
    	Object p = this.getProperty("nguoidung_id");
    	if(p != null)
    		return Integer.valueOf(p.toString());
    	return -1;
    }
//    
//    public static AppToken getFromRequest(HttpServletRequest request)
//    {
//    	final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//    	return new AppToken(header);
//    }

	public String getMaTinh() {
		Object p = this.getProperty("ma_tinh");
    	if(p != null)
    		return p.toString();
    	return null;
	}
    
}
