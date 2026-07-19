package vn.vnptit.sapi.oneapp

import groovy.util.slurpersupport.GPathResult

class GenVasc {
    static void main(String[] args) {
        def definitions = new XmlSlurper().parse(getClass().getResourceAsStream("/vasc.xml"))
        def candidateMsgs = (definitions.types.schema.element as GPathResult)
        def candidateOutput = candidateMsgs.findAll { (it.@name as String).endsWith("Response") }
        def candidateInput = candidateMsgs.findAll {
            candidateOutput.any { GPathResult a -> a.@name as String == (it.@name as String) + "Response" }
        }

        def complexTypes = (definitions.types.schema.complexType as GPathResult)
        complexTypes.each { GPathResult it ->
            new File(System.getProperty("user.dir") + "/src/main/groovy/vn/vnptit/sapi/oneapp/models/vasc/${it.@name}.groovy").newWriter().withWriter { w ->
                w.append("""package vn.vnptit.sapi.oneapp.models.vasc
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class ${it.@name} {
""")
                (it.sequence.element as GPathResult).each { GPathResult f ->
                    if (f.@type as String == 's:int')
                        w.append("""
    @JsonProperty("${f.@name}")
    public int ${f.@name}""")
                    else if (f.@type as String == 's:string')
                        w.append("""
    @JsonProperty("${f.@name}")
    public String ${f.@name}""")
                    else
                        w.append("""
    @JsonProperty("${f.@name}")
    public Object ${f.@name}""")
                }
                w.append("""
}""")
            }
        }
    }
}
