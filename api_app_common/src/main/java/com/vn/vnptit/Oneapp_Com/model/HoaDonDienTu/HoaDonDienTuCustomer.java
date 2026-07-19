package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import java.util.Objects;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.util.TextUtils;

import com.vn.vnptit.Oneapp_Com.common.Utils;

public class HoaDonDienTuCustomer {

	private Integer	phanvung_id;

	private String	ten_kh;

	private String	ma_gd;

	private String	mst;

	private String	diachi_ct;

	private String	bank_acc_name;

	private String	bank_name;

	private String	so_tk;

	private String	email;

	private String	so_fax;

	private String	dienthoai;

	private String	loaikh_id;

	public HoaDonDienTuCustomer(Integer phanvung_id, String ten_kh,
			String ma_gd, String mst, String diachi_ct, String bank_acc_name,
			String bank_name, String so_tk, String email, String so_fax,
			String dienthoai, String loaikh_id) {
		super();
		this.phanvung_id = phanvung_id;
		this.ten_kh = ten_kh;
		this.ma_gd = ma_gd;
		this.mst = mst;
		this.diachi_ct = diachi_ct;
		this.bank_acc_name = bank_acc_name;
		this.bank_name = bank_name;
		this.so_tk = so_tk;
		this.email = email;
		this.so_fax = so_fax;
		this.dienthoai = dienthoai;
		this.loaikh_id = loaikh_id;
	}

	public Number getPhanvung_id() {
		return phanvung_id;
	}

	public void setPhanvung_id(Integer phanvung_id) {
		this.phanvung_id = phanvung_id;
	}

	public String getTen_kh() {
		return ten_kh;
	}

	public void setTen_kh(String ten_kh) {
		this.ten_kh = ten_kh;
	}

	public String getMa_gd() {
		return ma_gd;
	}

	public void setMa_gd(String ma_gd) {
		this.ma_gd = ma_gd;
	}

	public String getMst() {
		return mst;
	}

	public void setMst(String mst) {
		this.mst = mst;
	}

	public String getDiachi_ct() {
		return diachi_ct;
	}

	public void setDiachi_ct(String diachi_ct) {
		this.diachi_ct = diachi_ct;
	}

	public String getBank_acc_name() {
		return bank_acc_name;
	}

	public void setBank_acc_name(String bank_acc_name) {
		this.bank_acc_name = bank_acc_name;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getSo_tk() {
		return so_tk;
	}

	public void setSo_tk(String so_tk) {
		this.so_tk = so_tk;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSo_fax() {
		return so_fax;
	}

	public void setSo_fax(String so_fax) {
		this.so_fax = so_fax;
	}

	public String getDienthoai() {
		return dienthoai;
	}

	public void setDienthoai(String dienthoai) {
		this.dienthoai = dienthoai;
	}

	public String getLoaikh_id() {
		return loaikh_id;
	}

	public void setLoaikh_id(String loaikh_id) {
		this.loaikh_id = loaikh_id;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HoaDonDienTuCustomer invCus = (HoaDonDienTuCustomer) o;
		return Objects.equals(this.ten_kh, invCus.ten_kh)
				&& Objects.equals(this.ma_gd, invCus.ma_gd)
				&& Objects.equals(this.mst, invCus.mst)
				&& Objects.equals(this.diachi_ct, invCus.diachi_ct)
				&& Objects.equals(this.bank_acc_name, invCus.bank_acc_name)
				&& Objects.equals(this.bank_name, invCus.bank_name)
				&& Objects.equals(this.so_tk, invCus.so_tk)
				&& Objects.equals(this.email, invCus.email)
				&& Objects.equals(this.so_fax, invCus.so_fax)
				&& Objects.equals(this.dienthoai, invCus.dienthoai)
				&& Objects.equals(this.loaikh_id, invCus.loaikh_id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ten_kh, ma_gd, mst, diachi_ct, bank_acc_name,
				bank_name, so_tk, email, so_fax, dienthoai, loaikh_id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class VPayWallet {\n");
		sb.append("    ten_kh: ").append(toIndentedString(ten_kh))
				.append("\n");
		sb.append("    ma_gd: ").append(toIndentedString(ma_gd)).append("\n");
		sb.append("    mst: ").append(toIndentedString(mst)).append("\n");
		sb.append("    diachi_ct: ").append(toIndentedString(diachi_ct))
				.append("\n");
		sb.append("    bank_acc_name: ")
				.append(toIndentedString(bank_acc_name)).append("\n");
		sb.append("    bank_name: ").append(toIndentedString(bank_name))
				.append("\n");
		sb.append("    so_tk: ").append(toIndentedString(so_tk)).append("\n");
		sb.append("    vemail: ").append(toIndentedString(email))
				.append("\n");
		sb.append("    vso_fax: ").append(toIndentedString(so_fax))
				.append("\n");
		sb.append("    dienthoai: ").append(toIndentedString(dienthoai))
				.append("\n");
		sb.append("    vloaikh_id: ").append(toIndentedString(loaikh_id))
				.append("\n");
		sb.append("}");
		return sb.toString();
	}

	public String toXml() {
		StringBuilder str = new StringBuilder();
		str.append("<Customers>");
		str.append("<Customer>");
		str.append("<Name><![CDATA[" + Utils.replaceForXlm(ten_kh) + "]]></Name>");
		str.append("<Code>" + ma_gd + "</Code>");

		str.append(Utils.checkData(mst) ? "<TaxCode>" + mst + "</TaxCode>" : "<TaxCode></TaxCode>");
//		if (!TextUtils.isEmpty(mst) && !TextUtils.isEmpty(mst.trim())) {
//			str.append("<TaxCode>" + mst + "</TaxCode>");
//		} else {
//			str.append("<TaxCode></TaxCode>");
//		}

		str.append(Utils.checkData(diachi_ct) ? "<Address><![CDATA[" + Utils.replaceForXlm( diachi_ct) + "]]></Address>" : "<Address></Address>");
//		if (!TextUtils.isEmpty(diachi_ct)
//				&& !TextUtils.isEmpty(diachi_ct.trim()))
//			str.append("<Address>" + Utils.replaceForXlm( diachi_ct) + "</Address>");
//		else
//			str.append("<Address></Address>");

		str.append(Utils.checkData(bank_acc_name) ? "<BankAccountName><![CDATA[" + bank_acc_name
				+ "]]></BankAccountName>" : "<BankAccountName></BankAccountName>");
//		if (!TextUtils.isEmpty(bank_acc_name)
//				&& !TextUtils.isEmpty(bank_acc_name.trim()))
//			str.append("<BankAccountName>" + bank_acc_name
//					+ "</BankAccountName>");
//		else
//			str.append("<BankAccountName></BankAccountName>");

		str.append(Utils.checkData(bank_name) ? "<BankName><![CDATA[" + Utils.replaceForXlm(bank_name)
				+ "]]></BankName>" : "<BankName></BankName>");
//		if (!TextUtils.isEmpty(bank_name)
//				&& !TextUtils.isEmpty(bank_name.trim()))
//			str.append("<BankName>" + Utils.replaceForXlm(bank_name)
//					+ "</BankName>");
//		else
//			str.append("<BankName></BankName>");

		str.append(Utils.checkData(so_tk) ? "<BankNumber>" + so_tk + "</BankNumber>" : "<BankNumber></BankNumber>");
//		if (!TextUtils.isEmpty(so_tk) && !TextUtils.isEmpty(so_tk.trim()))
//			str.append("<BankNumber>" + so_tk + "</BankNumber>");
//		else
//			str.append("<BankNumber></BankNumber>");

		str.append(Utils.checkData(email) ? "<Email>" + StringEscapeUtils.escapeHtml(email)
				+ "</Email>" : "<Email></Email>");
//		if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(email.trim()))
//			str.append("<Email>" + StringEscapeUtils.escapeHtml(email)
//					+ "</Email>");
//		else
//			str.append("<Email></Email>");

		str.append(Utils.checkData(so_fax) ? "<Fax>" + so_fax + "</Fax>" : "<Fax></Fax>");
//		if (!TextUtils.isEmpty(so_fax) && !TextUtils.isEmpty(so_fax.trim()))
//			str.append("<Fax>" + so_fax + "</Fax>");
//		else
//			str.append("<Fax></Fax>");

		str.append(Utils.checkData(dienthoai) ? "<Phone>" + dienthoai + "</Phone>" : "<Phone></Phone>");
//		if (!TextUtils.isEmpty(dienthoai)
//				&& !TextUtils.isEmpty(dienthoai.trim()))
//			str.append("<Phone>" + dienthoai + "</Phone>");
//		else
//			str.append("<Phone></Phone>");

		// Hoangpkn : 21/07/2018. Khong day thong tin nguoi lien he len nuwa. do do dai
		// tren invoice quy dinh 100 ky tu. a Phan trao doi thong tin nay khong quan
		// trong
		str.append("<ContactPerson></ContactPerson>");
		str.append("<RepresentPerson></RepresentPerson>");
		// end hoang
		str.append((loaikh_id.equals("1") && Utils.checkData(mst)) ? "<CusType>1</CusType>" : "<CusType>0</CusType>");
//		if (loaikh_id.equals("1") && !TextUtils.isEmpty(mst)
//				&& !TextUtils.isEmpty(mst.trim()))
//			str.append("<CusType>1</CusType>");
//		else
//			str.append("<CusType>0</CusType>");
		str.append("</Customer>");
		str.append("</Customers>");
//		return "<![CDATA[" + str + "]]>";
		return str.toString();
	}


	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return null;
		}
		return o.toString().replace("\n", "\n    ");
	}

}
