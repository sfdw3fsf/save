package vn.vnpt.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.client.RestTemplate;

public class callHttp {

	@Autowired
	RestTemplate restTemplate;

	public static void optionsCall(String message) {
//		String url = String.format(
//				"https://api.telegram.org/bot1562050146:AAG9M2wIsDR339HPoMFequVstBmEdxMQTtQ/sendMessage?chat_id=-479570070&text=%s",
//				message);
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.getForObject(url, String.class);
	}

	public Object httpsRq(HttpRequest httpRequest) {
		String uri = "https://" + httpRequest.getURI();
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, Object.class);

	}

//	public static void sendSmsVinaphone(String SoDienthoai, String Noidung) {
//		String url = String.format(
//				"http://10.156.3.79:8080/?type=2&username=sms&password=sms&smsc=900&from=VINAPHONE&to=%1$s&text=%2$s",
//				SoDienthoai, Noidung);
//		System.out.println(url);
//		RestTemplate restTemplate = new RestTemplate();
//		String result = restTemplate.getForObject(url, String.class);
//		System.out.println(result);
//
//	}

}
