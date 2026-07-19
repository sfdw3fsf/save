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
class MNPClient {

	@Autowired
	HttpManaged httpManaged

	@Value("\${spring.httpservices.mnp.url}")
	String url

	String getUrl(String funcName) {
		def ctx = url.replaceAll("/\$", "")
		return "$ctx/$funcName"
	}

	/**
	 * @param username, fromDate, toDate
	 * @return
	 */
	String pi_oneapp_detail(String username, String fromDate, String toDate) {
		post(getUrl("pi_oneapp_detail"), """{ "user":"$username", "fdate":"$fromDate", "tdate":"$toDate" }""")
	}

	String post(String url, String body) {
		httpManaged.post(["url": url], ["Content-Type": "application/json"], body)
	}
}
