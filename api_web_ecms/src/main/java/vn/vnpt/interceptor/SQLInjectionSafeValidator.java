package vn.vnpt.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Created by User on 7/20/2018.
 */
public class SQLInjectionSafeValidator implements HandlerInterceptor {
    public static final String SQL_TYPES = "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY" +
                    "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, " +
                    "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR" +
                    "SEQUENCE, RESTORE POINT, PFILE, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, " +
                    "COLUMN, FIELD, OPERATOR";
    private static final String[] sqlRegexps = {"(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)",
            "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
//            "(?i)(.*)(\\b)+WHERE(\\b)+(\\w)*\\s.*(.*)",
            /**/
            // "(.*)(/\\*|\\*//*|;){1,}(.*)",
            "(.*)(-){2,}(.*)",
            "(.*)(PRIOR|CONNECT\\s+BY)(.*)",
            "(.*)(OR)(.*)[><]+(.*)",
            "(.*)(OR)(.*)([\\s\\(\\)](IN|EXISTS)[\\s\\(\\)])+(.*)",
            "(.*)(IN|EXISTS)(.*)[\\(\\)]+(.*)",
            // "(.*)[><](.*)",
            "(.*)(ALL|ANY|SOME)(.*)[\\(\\)]+(.*)",
            "(.*)(BETWEEN)(.*)(AND)(.*)",
    };

    // pre-build the Pattern objects for faster validation

    private List<Pattern> validationPatterns = getValidationPatterns();

    public boolean isSqlInjectionSafe(String dataString) {
        if (isEmpty(dataString)) {
            return true;
        }
        for (Pattern pattern : validationPatterns) {
            if (matches(pattern, dataString)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSqlInjectionSafe(Map<String, Object> dataMap) {
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String dataString = String.valueOf(entry.getValue() != null ? entry.getValue() : "");
            if (!isSqlInjectionSafe(dataString)) return false;
        }
        return true;
    }

    public boolean isGetSqlInjectionSafe(Map<String, List<Object>> dataMap) {
        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            List<Object> dataStrings = entry.getValue();
            for (Object dataString : dataStrings) {
                String dataStr = String.valueOf(dataString != null ? dataString : "");
                if (!isSqlInjectionSafe(dataStr)) return false;
            }
        }
        return true;
    }

    private boolean matches(Pattern pattern, String dataString) {
        Matcher matcher = pattern.matcher(dataString);
        return matcher.matches();
    }

    private static List<Pattern> getValidationPatterns() {
        List<Pattern> patterns = new ArrayList<Pattern>();
        for (String sqlExpression : sqlRegexps) {
            patterns.add(getPattern(sqlExpression));
        }
        return patterns;
    }

    private static Pattern getPattern(String regEx) {
        return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    private boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
