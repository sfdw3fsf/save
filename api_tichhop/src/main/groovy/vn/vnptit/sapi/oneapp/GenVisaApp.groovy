package vn.vnptit.sapi.oneapp

import client.ws.visa.application.ApplicationBusinessServicesSoapBindingStub

import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Parameter

class GenVisaApp {
    static void main(String[] args) {
        def service = new ApplicationBusinessServicesSoapBindingStub()
        Class<?> clz = service.getClass()
        Method[] mtd = clz.getMethods()
        Collection<Method> actions = mtd.findAll {
            Modifier.isPublic(it.modifiers) && it.name != 'ApplicationBusinessServicesSoapBindingStub' && !it.name.startsWith("_") && !["addAttachment", "clearAttachments", "clearHeaders", "equals", "extractAttachments", "getAttachments", "getClass", "getHeader", "getHeaders", "getPassword", "getPortName", "getResponseHeader", "getResponseHeaders", "getTimeout", "getUsername", "hashCode", "notify", "notifyAll", "removeProperty", "setHeader", "setMaintainSession", "setUsername", "setPassword", "setPortName", "setTimeout", "toString", "wait"].contains(it.name)
        }

        actions.each { action ->
            Class<?> output = action.getReturnType()
            Parameter[] parameters = action.getParameters()
            String firstLetStr = action.getName().substring(0, 1)
            String remLetStr = action.getName().substring(1)
            String actionName = firstLetStr.toUpperCase() + remLetStr

            new File(System.getProperty("user.dir") + "/src/main/groovy/vn/vnptit/sapi/oneapp/models/visaapp/${actionName}.groovy").newWriter().withWriter { w ->
                w.append("""package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import client.ws.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ${actionName} {
""")
                parameters.each {
                    w.append("""
    @JsonProperty("${it.getName()}")
    public ${it.getType().simpleName} ${it.getName()}""")
                }


                w.append("""
}""")
            }
        }
    }
}
