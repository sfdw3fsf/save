package vn.vnptit.sapi.oneapp

import org.apache.commons.io.FileUtils
// import vn.com.vnptlamdong.ServicesStub
//import vn.vnpt.LineTestWSStub
import org.tempuri.SubscriberManagementStub

import java.lang.reflect.Method
import java.lang.reflect.Modifier

class GenWsdl {
    static void main(String[] args) {
        def service = new SubscriberManagementStub()
        Class<?> clz = service.getClass()
        Method[] mtd = clz.getMethods()
        Collection<Method> actions = mtd.findAll {
            Modifier.isPublic(it.modifiers) && it.name != 'SubscriberManagementStub'
        }
        new File(System.getProperty("user.dir") + "/src/main/groovy/vn/vnptit/sapi/oneapp/resources/VascServiceNew.groovy").newWriter().withWriter { w ->

            w.append("""package vn.vnptit.sapi.oneapp.resources

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tempuri.*
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("vasc")
class VascServiceNew {
    static {
        AuthHeader header = new AuthHeader()
        header.setStrUserName("hpgdhsx")
        header.setStrPassword("ukJ5cXWl")

        authHeaderE = new AuthHeaderE()
        authHeaderE.setAuthHeader(header)
    }
    
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    OracleManaged oracleManaged
    
    static AuthHeaderE authHeaderE
    
    static ThreadLocal<SubscriberManagementStub> servicesStubThreadLocal = ThreadLocal.withInitial{ new SubscriberManagementStub() }
""")

            actions.each { action ->
                Class<?> output = action.getReturnType()
                //vn.com.vnptlamdong
                if (output.getCanonicalName().startsWith("org.tempuri"))
                    if (action.parameterCount > 0) {
                        Class<?> inputType = action.parameterTypes[0]

                        w.append("""
    @PostMapping("/${action.getName()}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ${action.getName()}(@RequestBody ${inputType.getSimpleName()} body) {
        SubscriberManagementStub service = servicesStubThreadLocal.get()
        try {
            ${output.getSimpleName()} obj = service.${action.getName()}(body, authHeaderE)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
    
    """)
                    } else {
                        w.append("""
    @PostMapping("/${action.getName()}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ${action.getName()}(@RequestBody String body) {
        SubscriberManagementStub service = servicesStubThreadLocal.get()
        try {
            ${output.getSimpleName()} obj = service.${action.getName()}(authHeaderE)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
    
    """)
                    }
            }

            w.append("""
}""")
        }
    }

    static String _textFromUrl(String strUrl) {
        try {

            URL url = new URL(strUrl);

            // read text returned by server
            BufferedReader ins = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer sb = new StringBuffer()
            String line;
            while ((line = ins.readLine()) != null) {
                sb.append(line)
            }
            ins.close();

            sb.toString()
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }
}
