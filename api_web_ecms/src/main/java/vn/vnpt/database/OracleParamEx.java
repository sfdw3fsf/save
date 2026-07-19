package vn.vnpt.database;

public class OracleParamEx<T> {
	private String parameterName;
	private T oracleValue;
	private OracleType oracleDbType;
	private OracleDirect direct;

	public OracleParamEx(T oracleValue, String parameterName, vn.vnpt.database.OracleParamEx.OracleType oracleDbType,
			vn.vnpt.database.OracleParamEx.OracleDirect direct) {
		this.parameterName = parameterName;
		this.oracleDbType = oracleDbType;
		this.oracleValue = oracleValue;
		this.direct = direct;
	}

	public T getOracleValueString() {
		return oracleValue;
	}

	public T getOracleValue() {
		return oracleValue;
	}

	public T getOracleValueDouble() {
		return oracleValue;
	}

	public T getOracleValueDate() {
		return oracleValue;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public OracleType getOracleDbType() {
		return oracleDbType;
	}

	public OracleDirect getDirect() {
		return direct;
	}

	public enum OracleDirect {
		INPUT, OUPUT
	}

	public enum OracleType {
		CURSOR, NUMBER, VARCHAR2, DATE, ARRAY
	}

}
