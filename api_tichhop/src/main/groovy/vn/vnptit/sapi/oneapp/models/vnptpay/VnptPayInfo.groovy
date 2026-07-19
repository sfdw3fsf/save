package vn.vnptit.sapi.oneapp.models.vnptpay

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.validation.annotation.Validated

/**
 * VPayLogin
 */
@Validated

public class VnptPayInfo {

	@JsonProperty("url")
	protected String url = null

	@JsonProperty("walletId")
	private Long walletId = null

	@JsonProperty("walletAccountId")
	private Long walletAccountId = null

	@JsonProperty("token")
	private String token = null

	public Long getWalletId() {
		return walletId
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId
	}

	public Long getWalletAccountId() {
		return walletAccountId
	}

	public void setWalletAccountId(Long walletAccountId) {
		this.walletAccountId = walletAccountId
	}

	public String getToken() {
		return token
	}

	public void setToken(String token) {
		this.token = token
	}

	public String getUrl() {
		return url
	}

	public void setUrl(String url) {
		this.url = url
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true
		}
		if (o == null || getClass() != o.getClass()) {
			return false
		}
		VnptPayInfo vLogin = (VnptPayInfo) o
		return Objects.equals(this.url, vLogin.url) &&
				Objects.equals(this.walletId, vLogin.walletId) &&
				Objects.equals(this.walletAccountId, vLogin.walletAccountId) &&
				Objects.equals(this.token, vLogin.token)
	}

	@Override
	public int hashCode() {
		return Objects.hash(url, walletId, walletAccountId, token)
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("class VPayLogin {\n")
		sb.append("    walletId: ").append(toIndentedString(walletId)).append("\n")
		sb.append("    walletAccountId: ").append(toIndentedString(walletAccountId)).append("\n")
		sb.append("    token: ").append(toIndentedString(token)).append("\n")
		sb.append("    url: ").append(toIndentedString(url)).append("\n")
		sb.append("}")
		return sb.toString()
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null"
		}
		return o.toString().replace("\n", "\n    ")
	}
}
