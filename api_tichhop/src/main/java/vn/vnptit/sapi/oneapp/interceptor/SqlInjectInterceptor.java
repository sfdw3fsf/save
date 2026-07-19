package vn.vnptit.sapi.oneapp.interceptor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
        SQLInjectionSafeValidator sqlInjectionSafeValidator = new SQLInjectionSafeValidator();
        boolean isList = false;
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                List<Map<String, Object>> lstTestSqlInjection = new Gson().fromJson(getParamsFromBody(requestCacheWrapperObject), new TypeToken<List>() {
                }.getType());
                isList = true;
                if (lstTestSqlInjection != null && lstTestSqlInjection.size() > 0) {
                    for (Map<String, Object> pTestSqlInjection : lstTestSqlInjection) {
                        if (pTestSqlInjection != null && !sqlInjectionSafeValidator.isSqlInjectionSafe(pTestSqlInjection)) {
                            throw new SqlInjectionException(); 
                        }
                    }
                }
            } catch (Exception e) {

            }
            if (!isList) {
                Map<String, Object> pTestSqlInjection = new Gson().fromJson(getParamsFromBody(requestCacheWrapperObject), new TypeToken<Map<String, Object>>() {
                }.getType());

                if (pTestSqlInjection != null && !sqlInjectionSafeValidator.isSqlInjectionSafe(pTestSqlInjection)) {
                    throw new SqlInjectionException();
                }
            }
        } else {
            Map<String, List<Object>> pTestSqlInjection = getParams(requestCacheWrapperObject);
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

    private Map<String, List<Object>> getParams(HttpServletRequest httpRequest) {
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
