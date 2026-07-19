package vn.vnpt.annotations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class FeignRetryAspect {

    private static final Logger log = LoggerFactory.getLogger(FeignRetryAspect.class);

    private final RetryTemplate retryTemplate;

    public FeignRetryAspect() {
        this.retryTemplate = new RetryTemplate();
    }

    @Around("@annotation(vn.vnpt.annotations.FeignRetry)")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = this.getCurrentMethod(joinPoint);
        FeignRetry feignRetry = method.getAnnotation(FeignRetry.class);
        
        retryTemplate.setBackOffPolicy(this.prepareBackOffPolicy(feignRetry));
        retryTemplate.setRetryPolicy(this.prepareSimpleRetryPolicy(feignRetry));

        return retryTemplate.execute(arg0 -> {
            int retryCount = arg0.getRetryCount();
            log.info("Sending request method: {}, max attempt: {}, delay: {}, retryCount: {}",
                    method.getName(),
                    feignRetry.maxAttempt(),
                    feignRetry.backoff().delay(),
                    retryCount
            );
            return joinPoint.proceed(joinPoint.getArgs());
        });
    }

    private BackOffPolicy prepareBackOffPolicy(FeignRetry feignRetry) {
        if (feignRetry.backoff().multiplier() != 0) {
            ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
            backOffPolicy.setInitialInterval(feignRetry.backoff().delay());
            backOffPolicy.setMaxInterval(feignRetry.backoff().maxDelay());
            backOffPolicy.setMultiplier(feignRetry.backoff().multiplier());
            return backOffPolicy;
        } else {
            FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
            fixedBackOffPolicy.setBackOffPeriod(feignRetry.backoff().delay());
            return fixedBackOffPolicy;
        }
    }

    private SimpleRetryPolicy prepareSimpleRetryPolicy(FeignRetry feignRetry) {
        Map<Class<? extends Throwable>, Boolean> policyMap = new HashMap<>();
        // policyMap.put(RetryableException.class, true);      // Connection refused or time out
        // policyMap.put(ClientException.class, true);      // Load balance does not available (cause of RunTimeException)
        for (Class<? extends Throwable> t : feignRetry.include()) {
            policyMap.put(t, true);
        }
        return new SimpleRetryPolicy(feignRetry.maxAttempt(), policyMap, true);
    }

    private Method getCurrentMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
