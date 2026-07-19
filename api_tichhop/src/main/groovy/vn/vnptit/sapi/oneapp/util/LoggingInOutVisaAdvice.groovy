package vn.vnptit.sapi.oneapp.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.commons.lang3.ClassUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.LoggingManaged

import java.lang.reflect.Method
import java.lang.reflect.Parameter

@Aspect
@Component
class LoggingInOutVisaAdvice {

    @Autowired
    LoggingManaged loggingManaged

    static ThreadLocal<ObjectMapper> objectMappers = ThreadLocal.withInitial {
        new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    }

    final String inputPkg = "vn.vnptit.sapi.oneapp.model"

    @Around("@annotation(vn.vnptit.sapi.oneapp.util.LoggingInOutVisa)")
    public Object logging(ProceedingJoinPoint pjp) {
        Method method = MethodSignature.class.cast(pjp.signature).method
        Object[] args = pjp.getArgs()
        Parameter[] parameters = method.getParameters()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("action", method.getName())
        inputLogData.put("class", pjp.getThis().getClass().getSimpleName())

        args.eachWithIndex { arg, idx ->
            if (arg.getClass().getName().startsWith(inputPkg)) {
                inputLogData.put(parameters[idx].getName(), objectMappers.get().writeValueAsString(arg))
            } else if (ClassUtils.isPrimitiveOrWrapper(arg.getClass())) {
                inputLogData.put(parameters[idx].getName(), objectMappers.get().writeValueAsString(arg))
            }
        }

        Object out = pjp.proceed()

        /**/
        if (out instanceof ResponseEntity) {
            inputLogData.put("output", objectMappers.get().writeValueAsString(((ResponseEntity) out).getBody()))
        } else if (out.getClass().getPackage().getName().startsWith(inputPkg)) {
            inputLogData.put("output", objectMappers.get().writeValueAsString(out))
        } else if (ClassUtils.isPrimitiveOrWrapper(out.class)) {
            inputLogData.put("output", objectMappers.get().writeValueAsString(out))
        }

        loggingManaged.info(inputLogData)

        out
    }
}
