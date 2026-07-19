package vn.vnpt.message;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Message {

	private PropertiesConfiguration properties = null;

	private static Message constant = null;

	private Message() {
	}

	private Message(String code) {
		if (code.contains("_"))
			code = code.replace("_", "-");
		this.code = code;
	}

	private String code = null;

	public String getCode() {
		return code;
	}

	private String content = null;

	public String getContent() {
		return content;
	}

	private void setContent(String content) {
		this.content = content;
	}

	public static Message getMessageItem(String code) {
		Message result = new Message(code);
		if (constant == null) {
			constant = new Message();
			constant.properties = new PropertiesConfiguration();
			constant.properties.setDelimiterParsingDisabled(true);
			constant.properties.setEncoding("UTF-8");
			try {
				constant.properties.load(Message.class.getClassLoader().getResourceAsStream("message.properties"));
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		if (constant != null)
			if (constant.properties != null)
				result.setContent(constant.properties.getString(result.getCode(), "undefined"));
		return result;
	}

	public static Message getMessageItem(Codes code) {
		return getMessageItem(code.name());
	}

	public static String getMessage(String code) {
		Message result = getMessageItem(code);
		return result.getContent();
	}

	public static String getMessage(Codes code) {
		return getMessage(code.name());
	}

	public enum Codes {
		BSS_00000000, BSS_00000204, BSS_00000400, BSS_00000403, BSS_00000404, BSS_00000409, BSS_00000500, BSS_00003000, BSS_00009490, BSS_00009491
	}
}
