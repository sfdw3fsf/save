package vn.vnptit.sapi.oneapp.models.vnptpay

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.validation.annotation.Validated

/**
 * WalletInfo
 */
@Validated

public class WalletInfo {

	@JsonProperty("name")
	protected String name = null

	@JsonProperty("phoneNumber")
	protected String phone = null

	@JsonProperty("availableBalance")
	protected Long balance = null

	@JsonProperty("walletId")
	private Long walletId = null

	@JsonProperty("walletAccountId")
	private Long walletAccountId = null

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

	public String getName() {
		return name
	}

	public void setName(String name) {
		this.name = name
	}

	public String getPhone() {
		return phone
	}

	public void setPhone(String phone) {
		this.phone = phone
	}

	public Long getBalance() {
		return balance
	}

	public void setBalance(Long balance) {
		this.balance = balance
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true
		}
		if (o == null || getClass() != o.getClass()) {
			return false
		}
		WalletInfo vLogin = (WalletInfo) o
		return Objects.equals(this.name, vLogin.name) &&
				Objects.equals(this.walletId, vLogin.walletId) &&
				Objects.equals(this.walletAccountId, vLogin.walletAccountId) &&
				Objects.equals(this.phone, vLogin.phone) &&
				Objects.equals(this.balance, vLogin.balance)
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, phone, balance, walletId, walletAccountId)
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("class VPayLogin {\n")
		sb.append("    walletId: ").append(toIndentedString(walletId)).append("\n")
		sb.append("    walletAccountId: ").append(toIndentedString(walletAccountId)).append("\n")
		sb.append("    name: ").append(toIndentedString(name)).append("\n")
		sb.append("    phone: ").append(toIndentedString(phone)).append("\n")
		sb.append("    balance: ").append(toIndentedString(balance)).append("\n")
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
