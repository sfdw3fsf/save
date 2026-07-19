package vn.vnptit.sapi.oneapp.resources.visa


import client.ws.visa.common.CommonBusinessServicesSoapBindingStub
import client.ws.visa.common.WSComImplServiceLocator
import client.ws.visa.report.ReportBusinessServicesSoapBindingStub
import client.ws.visa.report.WSDeviceImplServiceLocator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

@Configuration
class VISAReportConfiguration {
    @Bean("visaReportServicesStubThreadLocal")
    ThreadLocal<ReportBusinessServicesSoapBindingStub> get(@Autowired TrustManager[] trustAllCerts,
                                                                @Value("\${spring.httpservices.visa.url}") String visaUrl
    ){
        ThreadLocal.withInitial {
            SSLContext ctx = SSLContext.getInstance("TLS")
            ctx.init(new KeyManager[0], trustAllCerts, new SecureRandom())
            SSLContext.setDefault(ctx)
            WSDeviceImplServiceLocator locator = new WSDeviceImplServiceLocator()
            locator.setReportBusinessServicesEndpointAddress(visaUrl + "ReportBusinessServices")

            ReportBusinessServicesSoapBindingStub service = (ReportBusinessServicesSoapBindingStub) locator.getReportBusinessServices()
            return service
        }
    }
}
