package vn.vnptit.sapi.oneapp.models.einvoice

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty

public class InvCustomer {

	@JsonProperty("ten_kh")
	private String ten_kh

	@JsonProperty("ma_gd")
	private String ma_gd

	@JsonProperty("mst")
	private String mst

	@JsonProperty("diachi_ct")
	private String diachi_ct

	@JsonProperty("bank_acc_name")
	private String bank_acc_name

	@JsonProperty("bank_name")
	private String bank_name

	@JsonProperty("so_tk")
	private String so_tk

	@JsonProperty("vemail")
	private String vemail

	@JsonProperty("vso_fax")
	private String vso_fax

	@JsonProperty("dienthoai")
	private String dienthoai

	@JsonProperty("vloaikh_id")
	private String vloaikh_id




	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getTen_kh() {
		return ten_kh
	}

	public void setTen_kh(String ten_kh) {
		this.ten_kh = ten_kh
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getMa_gd() {
		return ma_gd
	}

	public void setMa_gd(String ma_gd) {
		this.ma_gd = ma_gd
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getMst() {
		return mst
	}

	public void setMst(String mst) {
		this.mst = mst
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getDiachi_ct() {
		return diachi_ct
	}

	public void setDiachi_ct(String diachi_ct) {
		this.diachi_ct = diachi_ct
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getBank_acc_name() {
		return bank_acc_name
	}

	public void setBank_acc_name(String bank_acc_name) {
		this.bank_acc_name = bank_acc_name
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getBank_name() {
		return bank_name
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getSo_tk() {
		return so_tk
	}

	public void setSo_tk(String so_tk) {
		this.so_tk = so_tk
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getVemail() {
		return vemail
	}

	public void setVemail(String vemail) {
		this.vemail = vemail
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getVso_fax() {
		return vso_fax
	}

	public void setVso_fax(String vso_fax) {
		this.vso_fax = vso_fax
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getDienthoai() {
		return dienthoai
	}

	public void setDienthoai(String dienthoai) {
		this.dienthoai = dienthoai
	}

	/**
	 * Get token
	 *
	 * @return token
	 */
	@ApiModelProperty(value = "")
	public String getVloaikh_id() {
		return vloaikh_id
	}

	public void setVloaikh_id(String vloaikh_id) {
		this.vloaikh_id = vloaikh_id
	}


	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true
		}
		if (o == null || getClass() != o.getClass()) {
			return false
		}
		InvCustomer invCus = (InvCustomer) o
		return Objects.equals(this.ten_kh, invCus.ten_kh) &&
				Objects.equals(this.ma_gd, invCus.ma_gd) &&
				Objects.equals(this.mst, invCus.mst) &&
				Objects.equals(this.diachi_ct, invCus.diachi_ct) &&
				Objects.equals(this.bank_acc_name, invCus.bank_acc_name) &&
				Objects.equals(this.bank_name, invCus.bank_name) &&
				Objects.equals(this.so_tk, invCus.so_tk) &&
				Objects.equals(this.vemail, invCus.vemail) &&
				Objects.equals(this.vso_fax, invCus.vso_fax) &&
				Objects.equals(this.dienthoai, invCus.dienthoai) &&
				Objects.equals(this.vloaikh_id, invCus.vloaikh_id)
	}

	@Override
	public int hashCode() {
		return Objects.hash(ten_kh, ma_gd, mst, diachi_ct, bank_acc_name, bank_name, so_tk,
				vemail, vso_fax, dienthoai,vloaikh_id)
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("class VPayWallet {\n")

		sb.append("    ten_kh: ").append(toIndentedString(ten_kh)).append("\n")
		sb.append("    ma_gd: ").append(toIndentedString(ma_gd)).append("\n")
		sb.append("    mst: ").append(toIndentedString(mst)).append("\n")
		sb.append("    diachi_ct: ").append(toIndentedString(diachi_ct)).append("\n")
		sb.append("    bank_acc_name: ").append(toIndentedString(bank_acc_name)).append("\n")
		sb.append("    bank_name: ").append(toIndentedString(bank_name)).append("\n")
		sb.append("    so_tk: ").append(toIndentedString(so_tk)).append("\n")
		sb.append("    vemail: ").append(toIndentedString(vemail)).append("\n")
		sb.append("    vso_fax: ").append(toIndentedString(vso_fax)).append("\n")
		sb.append("    dienthoai: ").append(toIndentedString(dienthoai)).append("\n")
		sb.append("    vloaikh_id: ").append(toIndentedString(vloaikh_id)).append("\n")
		sb.append("}")
		return sb.toString()
	}

	public String toXml() {
		StringBuilder str = new StringBuilder()
		str.append("<Customers>")
		str.append("<Customer>")
		str.append("<Name><![CDATA[$ten_kh]]></Name>")
		str.append("<Code>$ma_gd</Code>")
		str.append("<TaxCode><![CDATA[$mst]]></TaxCode>")
		str.append("<Address><![CDATA[$diachi_ct]]></Address>")
		str.append("<BankAccountName></BankAccountName>")

		if (bank_name?.trim())
			str.append("<BankName><![CDATA[$bank_name]]></BankName>")
		else
			str.append("<BankName></BankName>")
		str.append("<BankNumber>$so_tk</BankNumber>")
		str.append("<Email>$vemail</Email>")
		str.append("<Fax>$vso_fax</Fax>")
		str.append("<Phone>$dienthoai</Phone>")
		// Hoangpkn : 21/07/2018. Khong day thong tin nguoi lien he len nuwa. do do dai tren invoice quy dinh 100 ky tu. a Phan trao doi thong tin nay khong quan trong
		//str.Append("<ContactPerson>" + txtTenKH.Text + "</ContactPerson>");
		//str.Append("<RepresentPerson>" + txtTenKH.Text + "</RepresentPerson>");
		str.append("<ContactPerson></ContactPerson>")
		str.append("<RepresentPerson></RepresentPerson>")
		// end hoang
//		if (vloaikh_id != "1")
//			str.append("<CusType>1</CusType>")
//		else
//			str.append("<CusType>0</CusType>")
		str.append("<CusType>0</CusType>")
		str.append("</Customer>")
		str.append("</Customers>")
		String xml = str.toString().replaceAll("]]>", "]]]]><![CDATA[>")
		return "<![CDATA[$xml]]>".toString()
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
