
package com.vn.vnptit.Oneapp_Com.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author DanhNQ
 *
 */
public class AppConfig {
	private String sdk_config;
	private String videocall_config;

	public Object getSdk_config() {
		if (!sdk_config.isEmpty()) {
			try {
				return new Gson().fromJson(sdk_config, new TypeToken<Sdkconfig>() {
				}.getType());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return null;
	}

	public void setSdk_config(String sdk_config) {
		this.sdk_config = sdk_config;
	}

	public Object getVideocall_config() {

		if (!videocall_config.isEmpty()) {
			try {
				return new Gson().fromJson(videocall_config, new TypeToken<VideoCallConfig>() {
				}.getType());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return null;
	}

	public void setVideocall_config(String videocall_config) {
		this.videocall_config = videocall_config;
	}

}
