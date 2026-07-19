package vn.vnptit.sapi.oneapp.interceptor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import groovy.json.JsonSlurper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vn.vnptit.sapi.oneapp.exceptions.SqlInjectionException;
import vn.vnptit.sapi.oneapp.util.SQLInjectionSafeValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * prevent SQL Injected interceptors
 *
 * @author VietNH
 * @time 2022-02-11
 */
public class SqlInjectInterceptor implements HandlerInterceptor {

    private int NORMAL_LEN;
    private int MAX_LEN;
    private int NUMBER_OF_LONGPARAM;

    public SqlInjectInterceptor(){}
    public SqlInjectInterceptor(int normalLen, int maxLen, int numberOfLongParam){
        NORMAL_LEN = normalLen;
        MAX_LEN = maxLen;
        NUMBER_OF_LONGPARAM = numberOfLongParam;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        CachedBodyHttpServletRequest requestCacheWrapperObject = new CachedBodyHttpServletRequest(request);
        SQLInjectionSafeValidator sqlInjectionSafeValidator = new SQLInjectionSafeValidator(NORMAL_LEN, MAX_LEN, NUMBER_OF_LONGPARAM);
        boolean isList = false;
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                String requestBody = getParamsFromBody(requestCacheWrapperObject);
                Object j = new JsonSlurper().parseText(requestBody);
                if (j != null) {
                    sqlInjectionSafeValidator.isSqlInjectionSafeDynamic(j, false);
                }
            } catch (SqlInjectionException e) {
                throw e;
            } catch (Exception ex){}
        } else {
            Map<String, List<String>> pTestSqlInjection = getParams(requestCacheWrapperObject);
            if (pTestSqlInjection != null && !sqlInjectionSafeValidator.isGetSqlInjectionSafe(pTestSqlInjection)) {
                throw new SqlInjectionException();
            }
        }
        return true;
    }

    private String getParamsFromBody(HttpServletRequest httpRequest) {
        return extractBodyFromRequest(httpRequest);
    }

    private String extractBodyFromRequest(HttpServletRequest httpRequest) {
        String result = "";
        try (Scanner s = new Scanner(httpRequest.getInputStream(), "UTF-8").useDelimiter("\\A")) {
            if (s.hasNext()) {
                result = s.next();
            }
        } catch (Exception e) {

        }
        return result;
    }

    private Map<String, List<String>> getParams(HttpServletRequest httpRequest) {
        try {
            return httpRequest.getParameterMap().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, list -> Arrays.stream(list.getValue())
                                    .filter(value -> value != null && value.length() > 0).collect(Collectors.toList())
                            )
                    );
        } catch (Exception ex) {
            return null;
        }
    }
}
