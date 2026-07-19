package vn.vnptit.sapi.oneapp.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 7/20/2018.
 */
public class SQLInjectionSafeValidator {
	public static final String SQL_TYPES = "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY"
			+ "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, "
			+ "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR"
			+ "SEQUENCE, RESTORE POINT, PFILE, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, "
			+ "COLUMN, FIELD, OPERATOR";
	private static final String[] sqlRegexps = { "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
			"(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)", "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
			"(.*)[\\s\\)]+(OR)[\\s\\(]+(.*)[=><]+(.*)",
			"(.*)[\\s\\)]+(OR)[\\s\\(]+(.*)([\\s\\(\\)](IN|EXISTS)[\\s\\(\\)])+(.*)", "(.*)(PRIOR|CONNECT\\s+BY)(.*)",
			"(.*)\\s(IN|EXISTS)\\s(.*)[\\(\\)]+(.*)", "(.*)\\s(ALL|ANY|SOME)\\s(.*)[\\(\\)]+(.*)",
			"(.*)\\s(BETWEEN)\\s(.*)\\s(AND)\\s(.*)", };

	private final String[] SQL_KEYS = new String[] { "SELECT", "UNION", "DUAL", "TABLE", "PROCEDURE", "FUNCTION",
			"TRIGGER", "MATERIALIZED VIEW", "LIBRARY", "DBLINK", "INDEX", "CONSTRAINT", "SCHEMA", "DATABASE", "BUCKET",
			"CLUSTER", "SYNONYM", "SESSION", "ROLE", "PACKAGE", "OPERATOR", "SEQUENCE", "RESTORE POINT", "PFILE",
			"CLASS", "CURSOR", "OBJECT", "RULE", "DATASET", "DATASTORE", "COLUMN", "FIELD", "INSERT", "DELETE",
			"SAVEPOINT", "DROP", "ROLLBACK", "CREATE", "ALTER", "TRUNCATE", "LOCK", "RELEASE", "EXISTS", "BETWEEN",
			"CONNECT", "UPDATE", "UPSERT" };

	// pre-build the Pattern objects for faster validation

	private List<Pattern> validationPatterns = getValidationPatterns();

	public boolean isSqlInjectionSafe(String dataString) {
		if (isEmpty(dataString)) {
			return true;
		}
		if (Arrays.stream(this.SQL_KEYS).anyMatch(dataString.toUpperCase()::contains)) {
			return false;
		} else {
			for (Pattern pattern : validationPatterns) {
				if (matches(pattern, dataString)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isSqlInjectionSafe(Map<String, Object> dataMap) {
		for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			String dataString = String.valueOf(entry.getValue() != null ? entry.getValue() : "");
			if (!isSqlInjectionSafe(dataString))
				return false;
		}
		return true;
	}

	public boolean isSqlInjectionSafeM(Map<String, String> dataMap) {
		for (Map.Entry<String, String> entry : dataMap.entrySet()) {
			if (!isSqlInjectionSafe(entry.getValue()))
				return false;
		}
		return true;
	}

	public boolean isGetSqlInjectionSafe(Map<String, List<Object>> dataMap) {
		for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
			List<Object> dataStrings = entry.getValue();
			for (Object dataString : dataStrings) {
				String dataStr = String.valueOf(dataString != null ? dataString : "");
				if (!isSqlInjectionSafe(dataStr))
					return false;
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
		return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
	}

	private boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

//    public static void main(String []args){
//        Pattern pattern = Pattern.compile("(.*)[=><](.*)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
//        Matcher matcher = pattern.matcher("{date=2022-01-17 09:28:45, lyDo=, newPass=, speed=dsl1m, time=2022-01-17 09:28:45}");
//        System.out.println("Hello World: " + (matcher.matches() ? "true" : "false"));
//    }
}
