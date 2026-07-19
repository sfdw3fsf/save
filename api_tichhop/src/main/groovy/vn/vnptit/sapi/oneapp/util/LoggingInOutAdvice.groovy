package vn.vnptit.sapi.oneapp.util

import com.google.gson.Gson
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
class LoggingInOutAdvice {

    @Autowired
    LoggingManaged loggingManaged

    @Around("@annotation(vn.vnptit.sapi.oneapp.util.LoggingInOut)")
    public Object logging(ProceedingJoinPoint pjp) {
        Method method = MethodSignature.class.cast(pjp.signature).method
        Object[] args = pjp.getArgs()
        Parameter[] parammeters = method.getParameters()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("action", method.getName())
        inputLogData.put("clazz", pjp.getThis().getClass().getSimpleName())
        /*
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            for (Annotation paramAnnotation : parameterAnnotations[argIndex]) {
                paramAnnotation.get
                if (!(paramAnnotation instanceof Data)) {
                    continue;
                }
                Data dataAnnotation = (Data) paramAnnotation;
                if (dataAnnotation.methodName().length() > 0) {
                    Object obj = args[argIndex];
                    Method dataMethod = obj.getClass().getMethod(dataAnnotation.methodName());
                    data.append(dataMethod.invoke(obj));
                    continue;
                }
                data.append(args[argIndex]);
            }
        }*/

        args.eachWithIndex { arg, idx ->
            if (arg.getClass().getName().startsWith("vn.vnptit.sapi.oneapp.model")) {
                inputLogData.put(parammeters[idx].getName(), new Gson().toJson(arg))
            } else if (ClassUtils.isPrimitiveOrWrapper(arg.getClass())) {
                inputLogData.put(parammeters[idx].getName(), new Gson().toJson(arg))
            }
        }

        Object out = pjp.proceed()

        /**/
        if (out instanceof ResponseEntity) {
            inputLogData.put("output", new Gson().toJson(((ResponseEntity) out).getBody()))
        } else if (out.getClass().getPackage().getName().startsWith("vn.vnptit.sapi.oneapp.model")) {
            inputLogData.put("output", new Gson().toJson(out))
        } else if (ClassUtils.isPrimitiveOrWrapper(out.class)) {
            inputLogData.put("output", new Gson().toJson(out))
        }

        loggingManaged.info(inputLogData)

        out
    }
}
