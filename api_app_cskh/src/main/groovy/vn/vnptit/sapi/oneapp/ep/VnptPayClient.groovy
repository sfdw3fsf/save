package vn.vnptit.sapi.oneapp.ep

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged

/**
 * @author VietNH
 *
 */
@Component
class VnptPayClient {

	@Autowired
	HttpManaged httpManaged

	@Value("\${spring.httpservices.vnptpay.url}")
	String vnptpayUrl
	@Value("\${spring.httpservices.vnptpay.username}")
	String vnptpayUser
	@Value("\${spring.httpservices.vnptpay.password}")
	String vnptpayPw

	String getUrl(String funcName) {
		def ctx = vnptpayUrl.replaceAll("/\$", "")
		return "$ctx/$funcName"
	}

	/**
	 * @Author VietNH
	 */
	String myvnpt_vnptpay_map(String listSdt) {
		post(getUrl("myvnpt_vnptpay_map"), """{"list": "$listSdt"}""")
	}

	/**
	 * @Author VietNH
	 */
	String check_hrm_code(String hrmCode) {
		postAuth(getUrl("check_hrm_code"), """{"hrm_code": "$hrmCode"}""")
	}

	/**
	 * @Author VietNH
	 */
	String mytv_ott_ps_data(String listSdt) {
		post(getUrl("mytv_ott_ps_data"), """{"list": "$listSdt"}""")
	}

	String post(String url, String body) {
		httpManaged.post(["url": url], ["Content-Type": "application/json"], body)
	}

	String postAuth(String url, String body) {
		httpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": "eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJ1bmtub3duIiwidHBzIjoiMTAwMCIsInJvbGVzIjpbXSwiaXNzIjoibHVvbmdodiIsInJlc291cmNlcyI6WyJnL2NoZWNrX2hybV9jb2RlIiwiZy9jaGVja19tb2JpbGVfY29kZSJdLCJjYXRlZ29yaWVzIjpbXSwiZXhwIjowLCJzeXMiOiJtZWRpYSIsImlwcyI6WyIqIl0sImp0aSI6ImQ5NTZkNmExLWMwNmItNDc0OC1iOWQ4LWY5OTE1NzgwZDExNyJ9.3w77jBIoww6QnXYOyf39p3Ao/qkW66viELOJ2gzBmfBma9YPfnixtEvXOWAybKQNwFgTawXlLobnclDeyyq8BA=="], body)
	}
}
