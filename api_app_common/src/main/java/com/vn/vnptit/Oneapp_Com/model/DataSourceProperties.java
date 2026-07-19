
package com.vn.vnptit.Oneapp_Com.model;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author danhd
 *
 */
@Component // Là 1 spring bean

public class DataSourceProperties {
	@Value("${app.poolsize}")
	private Integer poolsize;
	@Value("${app.tinhthanh}")
	private String tinhthanh;

	@Value("${spring.datasource.oneapp.url}")
	private String url_oneapp;
	@Value("${spring.datasource.oneapp.username}")
	private String username_oneapp;
	@Value("${spring.datasource.oneapp.password}")
	private String password_oneapp;
	@Value("${spring.datasource.oneapp.crypto_key}")
	private String crypto_key;
	@Value("${mahoa.key}")
	private String keymahoa;
	
	
	
	public String getKeymahoa() {
		return keymahoa;
	}

	public void setKeymahoa(String keymahoa) {
		this.keymahoa = keymahoa;
	}

	public String getTinhthanh() {
		return tinhthanh;
	}

	public void setTinhthanh(String tinhthanh) {
		this.tinhthanh = tinhthanh;
	}

	public String getUrl_oneapp() {
		return url_oneapp;
	}

	/**
	 * @param url_oneapp the url_oneapp to set
	 */
	public void setUrl_oneapp(String url_oneapp) {
		this.url_oneapp = url_oneapp;
	}

	/**
	 * @return the username_oneapp
	 */
	public String getUsername_oneapp() {
		return username_oneapp;
	}

	/**
	 * @param username_oneapp the username_oneapp to set
	 */
	public void setUsername_oneapp(String username_oneapp) {
		this.username_oneapp = username_oneapp;
	}

	/**
	 * @return the password_oneapp
	 */
	public String getPassword_oneapp() {
		return password_oneapp;
	}

	/**
	 * @param password_oneapp the password_oneapp to set
	 */
	public void setPassword_oneapp(String password_oneapp) {
		this.password_oneapp = password_oneapp;
	}
	
	public String getCrypto_key() {
		return crypto_key;
	}
	public void setCrypto_key(String crypto_key) {
		this.crypto_key = crypto_key;
	}

//	public DataSource getDataSource_oneApp() {
//		return new DataSource(url_oneapp, username_oneapp, password_oneapp);
//	}
//	public DataSource getDataSource_haiphong() {
//		return new DataSource(url_haiphong, username_haiphong, password_haiphong);
//	}
	public DataSource getDataSource_oneApp(String token) {

		try {

			Integer max_pool_size = poolsize;
			String url;
			String username;
			String password;
			String key;

			url = url_oneapp;
			username = username_oneapp;
			password = password_oneapp;
			key = crypto_key;
			

			return new DataSource(url, username, password, max_pool_size, ConstantString.ONEAPP, key);
		} catch (Exception e) {
			return null;

		}

	}
	public DataSource getDataSource_oneApp() {

		try {

			Integer max_pool_size = poolsize;
			String url;
			String username;
			String password;
			String key;

			url = url_oneapp;
			username = username_oneapp;
			password = password_oneapp;
			key = crypto_key;

			return new DataSource(url, username, password, max_pool_size, ConstantString.ONEAPP, key);
		} catch (Exception e) {
			return null;

		}

	}

	public DataSource getDataSource_oneApp_By_Id_Phanvung(Integer phanvung_id) {
		Integer max_pool_size = poolsize;

		try {

			String url;
			String username;
			String password;
			String key;

			url = url_oneapp;
			username = username_oneapp;
			password = password_oneapp;
			key = crypto_key;

			return new DataSource(url, username, password, max_pool_size, ConstantString.ONEAPP, key);
		} catch (Exception e) {
			return null;

		}

	}

}