package vn.vnpt.database;

public class ConnectionProperties {
	private String poolType;
	private String username;
	private String password;
	private String url;
	private int maxPoolSize;
	private int maxStatements;
	private int maxConnReuseTIme;
	private int maxConnReuseCount;
	private int abandonedConnTimeoutSecond;
	private int connWaitTimeoutSecond;
	private int inactiveConnTimeoutSecond;
	private int queryTimeoutSecond = 0;

	public String getPoolType() {
		return poolType;
	}

	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(int maxStatements) {
		this.maxStatements = maxStatements;
	}

	public int getMaxConnReuseTIme() {
		return maxConnReuseTIme;
	}

	public void setMaxConnReuseTIme(int maxConnReuseTIme) {
		this.maxConnReuseTIme = maxConnReuseTIme;
	}

	public int getMaxConnReuseCount() {
		return maxConnReuseCount;
	}

	public void setMaxConnReuseCount(int maxConnReuseCount) {
		this.maxConnReuseCount = maxConnReuseCount;
	}

	public int getAbandonedConnTimeoutSecond() {
		return abandonedConnTimeoutSecond;
	}

	public void setAbandonedConnTimeoutSecond(int abandonedConnTimeoutSecond) {
		this.abandonedConnTimeoutSecond = abandonedConnTimeoutSecond;
	}

	public int getConnWaitTimeoutSecond() {
		return connWaitTimeoutSecond;
	}

	public void setConnWaitTimeoutSecond(int connWaitTimeoutSecond) {
		this.connWaitTimeoutSecond = connWaitTimeoutSecond;
	}

	public int getInactiveConnTimeoutSecond() {
		return inactiveConnTimeoutSecond;
	}

	public void setInactiveConnTimeoutSecond(int inactiveConnTimeoutSecond) {
		this.inactiveConnTimeoutSecond = inactiveConnTimeoutSecond;
	}

	public int getQueryTimeoutSecond() {
		return queryTimeoutSecond;
	}

	public void setQueryTimeoutSecond(int queryTimeoutSecond) {
		this.queryTimeoutSecond = queryTimeoutSecond;
	}
}
