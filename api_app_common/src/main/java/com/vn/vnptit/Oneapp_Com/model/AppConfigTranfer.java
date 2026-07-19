package com.vn.vnptit.Oneapp_Com.model;

/**
 * @author DanhNQ
 *
 */
public class AppConfigTranfer {
	private Object sdkconfig;
	private Object videoCallConfig ;
	
	public Object getSdkconfig() {
		return sdkconfig;
	}
	public void setSdkconfig(Object sdkconfig) {
		this.sdkconfig = sdkconfig;
	}
	public Object getVideoCallConfig() {
		return videoCallConfig;
	}
	public void setVideoCallConfig(Object videoCallConfig) {
		this.videoCallConfig = videoCallConfig;
	}

	public AppConfigTranfer(AppConfig appConfig) {
		super();
		this.sdkconfig = appConfig.getSdk_config();
		this.videoCallConfig = appConfig.getVideocall_config();
	}
	
	
}
