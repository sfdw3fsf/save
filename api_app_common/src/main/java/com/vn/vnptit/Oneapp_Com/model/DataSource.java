package com.vn.vnptit.Oneapp_Com.model;

/**
 * @author Danh
 *
 */
public class DataSource {
	private String url;
	private String username;
	private String password;
	private Integer maxPoolSize =300;
	private String dbname;
	private String cryptoKey;
	
	public DataSource(String url, String username, String password, Integer maxPoolSize, String dbname, String cryptoKey) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxPoolSize = maxPoolSize;
		this.dbname = dbname;
		this.cryptoKey = cryptoKey;
		}

	public String getUrl() {
		return url;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getCryptoKey() {
		return cryptoKey;
	}
}
