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
class SmcsClient {

	@Autowired
	HttpManaged httpManaged

	@Value("\${spring.httpservices.smcs.url}")
	String url

	/**
	 * @param username, fromDate, toDate, service
	 * @return
	 */
	String dashDCRS(String service, String username, String fromDate, String toDate) {
		post("""{ "account":"$username", "fdate":"$fromDate", "tdate":"$toDate", "service":"$service"}""")
	}

	String post(String body) {
		httpManaged.post(["url": url], ["Content-Type": "application/json"], body)
	}
}
