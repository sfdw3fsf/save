
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import java.util.List;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import org.springframework.util.StringUtils;

/**
 * @author DanhNQ
 *
 */
public class HoaDonDienTuAdjustInvActionInput {

	private Integer	phanvung_id;
	private String	pattern;
	private String	serial;
	private String	fkey;
	private String	attachFile;
	private Integer	convert;

	private String	type;
	private String	fkey_new;
	private String	cusCode;
	private String	cusName;
	private String	cusAddress;
	private String	cusPhone;
	private String	cusTaxCode;
	private String	kind_of_Payment;
	private String	kindOfService;
	private Integer	vthem_extra;
	private Integer	total;
	private Integer	vatrate;
	private Integer	vatamount;
	private Integer	amount;
	private String	amount_words;
	private String	ma_nv;
	private String ma_kh;
	//Mã đơn vị quan hệ ngân sách người mua, đối với KHDN
	private String mDVQHNSach;
	//Căn cước công dân người mua, đối với KHCN
	private String cCCDan;
	//Hoá đơn cho thuê tài chính (Hóa đơn đối với hoạt động cho thuê tài chính) Số (1: Hóa đơn dành cho hoạt động thuê tài chính, 0: Hóa đơn không dành cho hoạt động cho thuê tài chính)
	private Integer hDCTTChinh;
	//Trạng thái thanh toán
	private Integer paymentStatus;

	public String getMa_kh() {
		return ma_kh;
	}

	public void setMa_kh(String ma_kh) {
		this.ma_kh = ma_kh;
	}

	public Integer getVatrate() {
		return vatrate;
	}

	public void setVatrate(Integer vatrate) {
		this.vatrate = vatrate;
	}

	public Integer getVatamount() {
		return vatamount;
	}

	public void setVatamount(Integer vatamount) {
		this.vatamount = vatamount;
	}

	private List<HoaDonDienTuPrd> products;

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

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public String getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(String attachFile) {
		this.attachFile = attachFile;
	}

	public Integer getConvert() {
		return convert;
	}

	public void setConvert(Integer convert) {
		this.convert = convert;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFkey_new() {
		return fkey_new;
	}

	public void setFkey_new(String fkey_new) {
		this.fkey_new = fkey_new;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusAddress() {
		return cusAddress;
	}

	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}

	public String getCusPhone() {
		return cusPhone;
	}

	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}

	public String getCusTaxCode() {
		return cusTaxCode;
	}

	public void setCusTaxCode(String cusTaxCode) {
		this.cusTaxCode = cusTaxCode;
	}

	public String getKind_of_Payment() {
		return kind_of_Payment;
	}

	public void setKind_of_Payment(String kind_of_Payment) {
		this.kind_of_Payment = kind_of_Payment;
	}

	public String getKindOfService() {
		return kindOfService;
	}

	public void setKindOfService(String kindOfService) {
		this.kindOfService = kindOfService;
	}

	public Integer getVthem_extra() {
		return vthem_extra;
	}

	public void setVthem_extra(Integer vthem_extra) {
		this.vthem_extra = vthem_extra;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}


	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAmount_words() {
		return amount_words;
	}

	public void setAmount_words(String amount_words) {
		this.amount_words = amount_words;
	}

	public String getMa_nv() {
		return ma_nv;
	}

	public void setMa_nv(String ma_nv) {
		this.ma_nv = ma_nv;
	}

	public List<HoaDonDienTuPrd> getProducts() {
		return products;
	}

	public void setProducts(List<HoaDonDienTuPrd> products) {
		this.products = products;
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

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String toXml() {

		StringBuilder str = new StringBuilder();

		str.append("<![CDATA[<AdjustInv>");
		str.append("<key>" + fkey_new + "</key>");
		str.append("<CusCode>" + cusCode + "</CusCode>");
		str.append("<CusName>" + cusName + "</CusName>");
		str.append("<CusAddress>" + cusAddress + "</CusAddress>");
		str.append("<CusPhone>" + cusPhone + "</CusPhone>");
		str.append("<CusTaxCode>" + (!StringUtils.isEmpty(cusTaxCode) ? cusTaxCode : "") + "</CusTaxCode>");
		str.append("<PaymentMethod>" + kind_of_Payment + "</PaymentMethod>");
		str.append("<KindOfService>" + kindOfService + "</KindOfService>");

		str.append("<Type>" + type + "</Type>");
		if (vthem_extra == 1)
			str.append("<Extra>" + fkey_new + "</Extra>");
		str.append("<Products>");

		for (HoaDonDienTuPrd pro : products) {

			str.append("<Product>");
			str.append("<ProdName>" + toIndentedString(pro.getProdName())
					+ "</ProdName>");
			str.append("<ProdUnit>" + pro.getProdUnit() + "</ProdUnit>");
			str.append("<ProdQuantity>" + pro.getProdQuantity()
					+ "</ProdQuantity>");
			if (pro.getProdPrice().toString() != "")
				str.append("<ProdPrice>" + Math.abs((pro.getProdPrice()))
						+ "</ProdPrice>");
			else
				str.append(
						"<ProdPrice>" + pro.getProdPrice() + "</ProdPrice>");

			if (pro.getAmount().toString() != "")
				str.append(
						ConstantString.KEY_AMOUNT + Math.abs(pro.getAmount()) + ConstantString.KEY_CLOSEAMOUNT);
			else
				str.append(ConstantString.KEY_AMOUNT + pro.getAmount() + ConstantString.KEY_CLOSEAMOUNT);

			if (!pro.getTotal().toString().equals(""))
				str.append("<Total>" + Math.abs(pro.getTotal()) + "</Total>");
			else
				str.append("<Total>" + pro.getTotal() + "</Total>");

			str.append("<VATRate>" + pro.getVat_rate() + "</VATRate>");

			if (!pro.getVat_amount().toString().equals(""))
				str.append("<VATAmount>" + Math.abs(pro.getVat_amount()) + "</VATAmount>");
			else
				str.append("<VATAmount>" + pro.getVat_amount() + "</VATAmount>");
			str.append("</Product>");
		}
		str.append("</Products>");
		if (!total.toString().equals(""))
			str.append("<Total>" + Math.abs(total) + "</Total>");
		else
			str.append("<Total>" + total + "</Total>");

		str.append("<VATRate>" + vatrate + "</VATRate>");
		if (!vatamount.toString().equals(""))
			str.append("<VATAmount>" + Math.abs(vatamount) + "</VATAmount>");
		else
			str.append("<VATAmount>" + vatamount + "</VATAmount>");

		if (!amount.toString().equals(""))
			str.append(ConstantString.KEY_AMOUNT + Math.abs(amount) + ConstantString.KEY_CLOSEAMOUNT);
		else
			str.append(ConstantString.KEY_AMOUNT + amount + ConstantString.KEY_CLOSEAMOUNT);

		str.append("<AmountInWords>" + amount_words + "</AmountInWords>");
		str.append("<Teller>" + ma_nv + "</Teller>");
		str.append("<MaKhachHang>" + ma_kh + "</MaKhachHang>");
		if (!StringUtils.isEmpty(mDVQHNSach))
			str.append("<MDVQHNSach>" + mDVQHNSach + "</MDVQHNSach>");
		if (!StringUtils.isEmpty(cCCDan))
			str.append("<CCCDan>" + cCCDan + "</CCCDan>");

		str.append("<HDCTTChinh>" + (hDCTTChinh != null ? hDCTTChinh : 0) + "</HDCTTChinh>");
		str.append("<PaymentStatus>" + paymentStatus + "</PaymentStatus>");
		str.append("</AdjustInv>]]>");
		return str.toString();

	}

	public String toXmlV2() {

		StringBuilder str = new StringBuilder();

		str.append("<AdjustInv>");
		str.append("<key>" + fkey_new + "</key>");
		str.append("<CusCode>" + cusCode + "</CusCode>");
		str.append("<CusName><![CDATA[" + cusName + "]]></CusName>");
		str.append("<CusAddress><![CDATA[" + cusAddress + "]]></CusAddress>");
		str.append("<CusPhone><![CDATA[" + cusPhone + "]]></CusPhone>");
		str.append("<CusTaxCode><![CDATA[" + (!StringUtils.isEmpty(cusTaxCode) ? cusTaxCode : "") + "]]></CusTaxCode>");
		str.append("<PaymentMethod>" + kind_of_Payment + "</PaymentMethod>");
		str.append("<KindOfService>" + kindOfService + "</KindOfService>");

		str.append("<Type>" + type + "</Type>");
		if (vthem_extra == 1)
			str.append("<Extra>" + fkey_new + "</Extra>");
		str.append("<Products>");

		for (HoaDonDienTuPrd pro : products) {

			str.append("<Product>");
			str.append("<ProdName><![CDATA[" + toIndentedString(pro.getProdName())
					+ "]]></ProdName>");
			str.append("<ProdUnit>" + pro.getProdUnit() + "</ProdUnit>");
			str.append("<ProdQuantity>" + pro.getProdQuantity()
					+ "</ProdQuantity>");
			if (pro.getProdPrice().toString() != "")
				str.append("<ProdPrice>" + Math.abs((pro.getProdPrice()))
						+ "</ProdPrice>");
			else
				str.append(
						"<ProdPrice>" + pro.getProdPrice() + "</ProdPrice>");

			if (pro.getAmount().toString() != "")
				str.append(
						ConstantString.KEY_AMOUNT + Math.abs(pro.getAmount()) + ConstantString.KEY_CLOSEAMOUNT);
			else
				str.append(ConstantString.KEY_AMOUNT + pro.getAmount() + ConstantString.KEY_CLOSEAMOUNT);

			if (!pro.getTotal().toString().equals(""))
				str.append("<Total>" + Math.abs(pro.getTotal()) + "</Total>");
			else
				str.append("<Total>" + pro.getTotal() + "</Total>");

			str.append("<VATRate>" + pro.getVat_rate() + "</VATRate>");

			if (!pro.getVat_amount().toString().equals(""))
				str.append("<VATAmount>" + Math.abs(pro.getVat_amount()) + "</VATAmount>");
			else
				str.append("<VATAmount>" + pro.getVat_amount() + "</VATAmount>");
			str.append("</Product>");
		}
		str.append("</Products>");
		if (!total.toString().equals(""))
			str.append("<Total>" + Math.abs(total) + "</Total>");
		else
			str.append("<Total>" + total + "</Total>");

		str.append("<VATRate>" + vatrate + "</VATRate>");
		if (!vatamount.toString().equals(""))
			str.append("<VATAmount>" + Math.abs(vatamount) + "</VATAmount>");
		else
			str.append("<VATAmount>" + vatamount + "</VATAmount>");

		if (!amount.toString().equals(""))
			str.append(ConstantString.KEY_AMOUNT + Math.abs(amount) + ConstantString.KEY_CLOSEAMOUNT);
		else
			str.append(ConstantString.KEY_AMOUNT + amount + ConstantString.KEY_CLOSEAMOUNT);

		str.append("<AmountInWords>" + amount_words + "</AmountInWords>");
		str.append("<Teller>" + ma_nv + "</Teller>");
		str.append("<MaKhachHang>" + ma_kh + "</MaKhachHang>");
		if (!StringUtils.isEmpty(mDVQHNSach))
			str.append("<MDVQHNSach>" + mDVQHNSach + "</MDVQHNSach>");
		if (!StringUtils.isEmpty(cCCDan))
			str.append("<CCCDan>" + cCCDan + "</CCCDan>");

		str.append("<HDCTTChinh>" + (hDCTTChinh != null ? hDCTTChinh : 0) + "</HDCTTChinh>");
		str.append("<PaymentStatus>" + paymentStatus + "</PaymentStatus>");
		str.append("</AdjustInv>");
		return str.toString();

	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return null;
		}
		return o.toString().replace("\n", "\n    ");
	}
}
