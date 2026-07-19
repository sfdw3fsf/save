package vn.vnpt.database;

public class OracleParam {
	private String parameterName;
	private String OracleDbType;
	private String OracleValue;
	private OracleDirect Direct;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getOracleDbType() {
		return OracleDbType;
	}

	public String getOracleValue() {
		return OracleValue;
	}

	public void setOracleValue(String oracleValue) {
		OracleValue = oracleValue;
	}

	public OracleParam(String parameterName, String oracleDbType, String oracleValue, OracleDirect direct) {
		super();
		this.parameterName = parameterName;
		OracleDbType = oracleDbType;
		OracleValue = oracleValue;
		Direct = direct;
	}

	public enum OracleDirect {
		INPUT, OUPUT
	}

	public OracleDirect getDirect() {
		return Direct;
	}

}
