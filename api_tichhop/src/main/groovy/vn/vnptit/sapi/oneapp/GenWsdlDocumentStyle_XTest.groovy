package vn.vnptit.sapi.oneapp

import groovy.util.slurpersupport.GPathResult
// import vn.com.vnptlamdong.ServicesStub

import java.lang.reflect.Method
import java.lang.reflect.Modifier

class GenWsdlDocumentStyle_XTest {
    static void main(String[] args) {
        def definitions = new XmlSlurper().parse(getClass().getResourceAsStream("/linetestws.xml"))
        def candidateMsgs = (definitions.types.schema.element as GPathResult)

        def candidateOutput = candidateMsgs.findAll { (it.@name as String).endsWith("Response") }
        def candidateInput = candidateMsgs.findAll {
            candidateOutput.any { GPathResult a -> a.@name as String == (it.@name as String) + "Response" }
        }

        new File(System.getProperty("user.dir") + "/src/main/groovy/vn/vnptit/sapi/oneapp/resources/XTestService.groovy").newWriter().withWriter { ws ->

            ws.append("""package vn.vnptit.sapi.oneapp.resources

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.*
import vn.vnptit.sapi.oneapp.models.xtest.*

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("x-test")
class XTestService implements XTestReponseReader {
    @Autowired
    HttpManaged httpManaged
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
""")

            candidateInput.each { GPathResult it ->
                //println it.@name

                ws.append("""
    @PostMapping("/${it.@name}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    List<Object> ${it.@name}(@RequestBody ${it.@name} body) {
        return _exec(body, httpManaged)
    }    
    """)

                new File(System.getProperty("user.dir") + "/src/main/groovy/vn/vnptit/sapi/oneapp/models/xtest/${it.@name}.groovy").newWriter().withWriter { w ->
                    w.append("""package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class ${it.@name} {
""")
                    (it.complexType.sequence.element as GPathResult).each { GPathResult f ->
                        if (f.@type as String == 's:int')
                            w.append("""
    @JsonProperty("${f.@name}")
    public int ${f.@name}""")
                        else
                            w.append("""
    @JsonProperty("${f.@name}")
    public String ${f.@name}""")
                    }
                    w.append("""
}""")
                }
            }

            ws.append("""
}""")
        }
    }
}
