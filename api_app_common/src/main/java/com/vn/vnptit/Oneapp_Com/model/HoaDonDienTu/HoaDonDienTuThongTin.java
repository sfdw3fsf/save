package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vn.vnptit.Oneapp_Com.common.NumberToWord;
import org.springframework.util.StringUtils;

public class HoaDonDienTuThongTin {

	private Integer						phanvung_id;

	private String						pattern;

	private String						serial;

	private String						fkey;

	private String						ma_gd;

	private String						mst;

	private String						ma_nv;

	private String						ten_tt;

	private String						diachi_ct;

	private String						sodt;
	private String						paymenthod;
	//Mã đơn vị quan hệ ngân sách người mua, đối với KHDN
	private String mDVQHNSach;
	//Căn cước công dân người mua, đối với KHCN
	private String cCCDan;
	//Hoá đơn cho thuê tài chính (Hóa đơn đối với hoạt động cho thuê tài chính) Số (1: Hóa đơn dành cho hoạt động thuê tài chính, 0: Hóa đơn không dành cho hoạt động cho thuê tài chính)
	private Integer hDCTTChinh;

	@Autowired NumberToWord numberToWord;
	@JsonProperty("products")
	@Valid
	private List<HoaDonDienTuProduct>	products	= null;

	public HoaDonDienTuThongTin(Integer phanvung_id, String fkey,
			String ma_gd, String mst, String ma_nv, String ten_tt,
			String diachi_ct, String sodt, String paymenthod,
			@Valid List<HoaDonDienTuProduct> products) {
		super();
		this.fkey = fkey;
		this.ma_gd = ma_gd;
		this.mst = mst;
		this.ma_nv = ma_nv;
		this.ten_tt = ten_tt;
		this.diachi_ct = diachi_ct;
		this.sodt = sodt;
		this.products = products;
		this.phanvung_id = phanvung_id;
		this.paymenthod = paymenthod;
	}

	public String getPaymenthod() {
		return paymenthod;
	}

	public void setPaymenthod(String paymenthod) {
		this.paymenthod = paymenthod;
	}

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
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

	public String getMa_nv() {
		return ma_nv;
	}

	public void setMa_nv(String ma_nv) {
		this.ma_nv = ma_nv;
	}


	public String getDiachi_ct() {
		return diachi_ct;
	}

	public void setDiachi_ct(String diachi_ct) {
		this.diachi_ct = diachi_ct;
	}


	public List<HoaDonDienTuProduct> getProducts() {
		return products;
	}

	public void setProducts(List<HoaDonDienTuProduct> products) {
		this.products = products;
	}

	public Integer getPhanvung_id() {
		return phanvung_id;
	}

	public void setPhanvung_id(Integer phanvung_id) {
		this.phanvung_id = phanvung_id;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getTen_tt() {
		return ten_tt;
	}

	public void setTen_tt(String ten_tt) {
		this.ten_tt = ten_tt;
	}

	public String getSodt() {
		return sodt;
	}

	public void setSodt(String sodt) {
		this.sodt = sodt;
	}

	public String getmDVQHNSach() {
		return mDVQHNSach;
	}

	public void setmDVQHNSach(String mDVQHNSach) {
		this.mDVQHNSach = mDVQHNSach;
	}

	public String getcCCDan() {
		return cCCDan;
	}

	public void setcCCDan(String cCCDan) {
		this.cCCDan = cCCDan;
	}

	public Integer gethDCTTChinh() {
		return hDCTTChinh;
	}

	public void sethDCTTChinh(Integer hDCTTChinh) {
		this.hDCTTChinh = hDCTTChinh;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HoaDonDienTuThongTin invItem = (HoaDonDienTuThongTin) o;
		return Objects.equals(this.fkey, invItem.fkey)
				&& Objects.equals(this.ma_gd, invItem.ma_gd)
				&& Objects.equals(this.mst, invItem.mst)
				&& Objects.equals(this.ma_nv, invItem.ma_nv)
				&& Objects.equals(this.ten_tt, invItem.ten_tt)
				&& Objects.equals(this.diachi_ct, invItem.diachi_ct)
				&& Objects.equals(this.sodt, invItem.sodt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fkey, ma_gd, mst, ma_nv, ten_tt, diachi_ct, sodt);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class InvItem {\n");
		sb.append("    ma_gd: ").append(toIndentedString(ma_gd)).append("\n");
		sb.append("    mst: ").append(toIndentedString(mst)).append("\n");
		sb.append("    ma_nv: ").append(toIndentedString(ma_nv)).append("\n");
		sb.append("    vten_tt: ").append(toIndentedString(ten_tt))
				.append("\n");
		sb.append("    diachi_ct: ").append(toIndentedString(diachi_ct))
				.append("\n");
		sb.append("    vsodt: ").append(toIndentedString(sodt)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	public String toXml(int tt78) {
		StringBuilder str = new StringBuilder();
		str.append("<Invoices>");
		str.append("<Inv>");
		str.append("<key>" + fkey + "</key>");
		str.append("<Invoice>");
		str.append("<CusCode>" + ma_gd + "</CusCode>");

		str.append(Utils.checkData(ten_tt) ? "<CusName><![CDATA[" + ten_tt + "]]></CusName>" : "<CusName></CusName>");

		str.append(Utils.checkData(diachi_ct) ? "<CusAddress><![CDATA[" + diachi_ct + "]]></CusAddress>" : "<CusAddress></CusAddress>");

		str.append(Utils.checkData(sodt) ? "<CusPhone>" + sodt + "</CusPhone>" : "<CusPhone></CusPhone>");

		str.append(Utils.checkData(mst) ? "<CusTaxCode><![CDATA[" + mst + "]]></CusTaxCode>" : "<CusTaxCode></CusTaxCode>");

		str.append("<PaymentMethod>" + paymenthod + "</PaymentMethod>");
		str.append("<KindOfService></KindOfService>");
		str.append("<Products>");

		Double tongtien_hddt = 0d;
		Double tongvat_hddt = 0d;
		Double tong_hddt = 0d;

		for (HoaDonDienTuProduct it : products) {
			if (!TextUtils.isEmpty(it.getProdName())) {
				Double iamount = it.getAmount();
				if(tt78==1) {
					str.append(it.toXml78());
					tongtien_hddt += it.getTien();
				}else {
					str.append(it.toXml());
					tongtien_hddt += iamount;
				}
				tongvat_hddt += it.getVat();
				tong_hddt += it.getTongTien();
			}
		}

//		tong_hddt = Math.abs(tong_hddt);
//		tongvat_hddt = Math.abs(tongvat_hddt);

		str.append("</Products>");
		str.append("<Total>" + tongtien_hddt + "</Total>");
		str.append("<DiscountAmount></DiscountAmount>");
		str.append("<VATRate>10</VATRate>");
		str.append("<VATAmount>" + tongvat_hddt + "</VATAmount>");
		str.append("<Amount>" + tong_hddt + "</Amount>");

		//String tienkt = "";
		// Định dạng số để tránh bị chuyển thành dạng số mũ
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(0);  // Không có phần thập phân
		df.setGroupingUsed(false);  // Không sử dụng dấu phân tách hàng nghìn
		String tienkt = df.format(tong_hddt);

//		if (tong_hddt.toString().contains("-"))
//			tienkt = tienkt.replace("-", "");

		String tbc = numberToWord.num2String (Long.parseLong(tienkt)) + " đồng";
		str.append("<AmountInWords><![CDATA[" + numberToWord.capitalizeFirstLetter( tbc) + "]]></AmountInWords>");
		str.append("<Teller>" + ma_nv + "</Teller>");
		if (!StringUtils.isEmpty(mDVQHNSach))
			str.append("<MDVQHNSach>" + mDVQHNSach + "</MDVQHNSach>");
		if (!StringUtils.isEmpty(cCCDan))
			str.append("<CCCDan>" + cCCDan + "</CCCDan>");

		str.append("<HDCTTChinh>" + (hDCTTChinh != null ? hDCTTChinh : 0) + "</HDCTTChinh>");
		str.append("</Invoice>");
		str.append("</Inv>");
		str.append("</Invoices>");

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


//	String _doc3so(int x) {
//
//		HashMap<Integer, String> bt = new HashMap<Integer, String>();
//		bt.put(2, " hai");
//		bt.put(3, " ba");
//		bt.put(4, " bốn");
//		bt.put(6, " sáu");
//		bt.put(7, " bẩy");
//		bt.put(8, " tám");
//		bt.put(9, " chín");
//
//		String so = String.format("%03d", x);
//		int tram = so.charAt(0) - 48;
//		int chuc = so.charAt(1) - 48;
//		int donvi = so.charAt(2) - 48;
//
//		StringBuilder sb = new StringBuilder();
//		sb.append(bt.containsKey(tram) ? bt.get(tram) + " trăm" : ((tram == 1) ? " một trăm" : " năm trăm"));
//
//		if (bt.containsKey(chuc)) {
//			sb.append(bt.get(chuc)).append(" mươi");
//		} else if (tram > 0 && chuc == 0 && donvi > 0) {
//			sb.append(" lẻ");
//		} else if (chuc == 1) {
//			sb.append(" mười");
//		} else if (chuc == 5) {
//			sb.append(" năm mươi");
//		} else if (chuc == 0) {
//			sb.append(" lẻ");
//		}
//
//		if (bt.containsKey(donvi)) {
//			sb.append(bt.get(donvi));
//		} else if (donvi == 1 && chuc < 2) {
//			sb.append(" một");
//		} else if (donvi == 1) {
//			sb.append(" mốt");
//		} else if (donvi == 5 && chuc > 0) {
//			sb.append(" lăm");
//		} else if (donvi == 5) {
//			sb.append(" năm");
//		}
//
//		return sb.toString();
//	}

}
