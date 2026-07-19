package vn.vnptit.sapi.oneapp.models.einvoice

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty

public class InvPhieuTT {

	@JsonProperty("loaiHinhId")
	private String loaiHinhId

	@JsonProperty("seriId")
	private String seriId

	@JsonProperty("seri")
	private String seri

	@JsonProperty("soSeri")
	private String soSeri

	@JsonProperty("phieuTTId")
	private String phieuTTId


	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getLoaiHinhId() {
		return loaiHinhId
	}

	public void setLoaiHinhId(String loaiHinhId) {
		this.loaiHinhId = loaiHinhId
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getSeriId() {
		return seriId
	}

	public void setSeriId(String seriId) {
		this.seriId = seriId
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getSeri() {
		return seri
	}

	public void setSeri(String seri) {
		this.seri = seri
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getSoSeri() {
		return soSeri
	}

	public void setSoSeri(String soSeri) {
		this.soSeri = soSeri
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getPhieuTTId() {
		return phieuTTId
	}

	public void setPhieuTTId(String phieuTTId) {
		this.phieuTTId = phieuTTId
	}





	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true
		}
		if (o == null || getClass() != o.getClass()) {
			return false
		}
		InvPhieuTT invPhieu = (InvPhieuTT) o
		return Objects.equals(this.loaiHinhId, invPhieu.loaiHinhId) &&
				Objects.equals(this.seriId, invPhieu.seriId) &&
				Objects.equals(this.seri, invPhieu.seri) &&
				Objects.equals(this.soSeri, invPhieu.soSeri) &&
				Objects.equals(this.phieuTTId, invPhieu.phieuTTId)
	}

	@Override
	public int hashCode() {
		return Objects.hash(loaiHinhId, seriId, seri, soSeri, phieuTTId)
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("class InvPhieuTT {\n")

		sb.append("    loaiHinhId: ").append(toIndentedString(loaiHinhId)).append("\n")
		sb.append("    seriId: ").append(toIndentedString(seriId)).append("\n")
		sb.append("    seri: ").append(toIndentedString(seri)).append("\n")
		sb.append("    soSeri: ").append(toIndentedString(soSeri)).append("\n")
		sb.append("    phieuTTId: ").append(toIndentedString(phieuTTId)).append("\n")

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
