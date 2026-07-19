
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import java.util.ArrayList;

/**
 * @author DanhNQ
 *
 */
public class HDDTDownloadInVNoPayModel {
	
	public class Codebeautify {
		 Invoice InvoiceObject;


		 // Getter Methods 

		 public Invoice getInvoice() {
		  return InvoiceObject;
		 }

		 // Setter Methods 

		 public void setInvoice(Invoice InvoiceObject) {
		  this.InvoiceObject = InvoiceObject;
		 }
		}
		public class Invoice {
		 Content ContentObject;
		 Signature SignatureObject;


		 // Getter Methods 

		 public Content getContent() {
		  return ContentObject;
		 }

		 public Signature getSignature() {
		  return SignatureObject;
		 }

		 // Setter Methods 

		 public void setContent(Content ContentObject) {
		  this.ContentObject = ContentObject;
		 }

		 public void setSignature(Signature SignatureObject) {
		  this.SignatureObject = SignatureObject;
		 }
		}
		public class Signature {
		 SignedInfo SignedInfoObject;
		 private String SignatureValue;
		 KeyInfo KeyInfoObject;
		 private String _xmlns;
		 private String _Id;


		 // Getter Methods 

		 public SignedInfo getSignedInfo() {
		  return SignedInfoObject;
		 }

		 public String getSignatureValue() {
		  return SignatureValue;
		 }

		 public KeyInfo getKeyInfo() {
		  return KeyInfoObject;
		 }

		 public String get_xmlns() {
		  return _xmlns;
		 }

		 public String get_Id() {
		  return _Id;
		 }

		 // Setter Methods 

		 public void setSignedInfo(SignedInfo SignedInfoObject) {
		  this.SignedInfoObject = SignedInfoObject;
		 }

		 public void setSignatureValue(String SignatureValue) {
		  this.SignatureValue = SignatureValue;
		 }

		 public void setKeyInfo(KeyInfo KeyInfoObject) {
		  this.KeyInfoObject = KeyInfoObject;
		 }

		 public void set_xmlns(String _xmlns) {
		  this._xmlns = _xmlns;
		 }

		 public void set_Id(String _Id) {
		  this._Id = _Id;
		 }
		}
		public class KeyInfo {
		 X509Data X509DataObject;


		 // Getter Methods 

		 public X509Data getX509Data() {
		  return X509DataObject;
		 }

		 // Setter Methods 

		 public void setX509Data(X509Data X509DataObject) {
		  this.X509DataObject = X509DataObject;
		 }
		}
		public class X509Data {
		 private String X509Certificate;


		 // Getter Methods 

		 public String getX509Certificate() {
		  return X509Certificate;
		 }

		 // Setter Methods 

		 public void setX509Certificate(String X509Certificate) {
		  this.X509Certificate = X509Certificate;
		 }
		}
		public class SignedInfo {
		 CanonicalizationMethod CanonicalizationMethodObject;
		 SignatureMethod SignatureMethodObject;
		 Reference ReferenceObject;


		 // Getter Methods 

		 public CanonicalizationMethod getCanonicalizationMethod() {
		  return CanonicalizationMethodObject;
		 }

		 public SignatureMethod getSignatureMethod() {
		  return SignatureMethodObject;
		 }

		 public Reference getReference() {
		  return ReferenceObject;
		 }

		 // Setter Methods 

		 public void setCanonicalizationMethod(CanonicalizationMethod CanonicalizationMethodObject) {
		  this.CanonicalizationMethodObject = CanonicalizationMethodObject;
		 }

		 public void setSignatureMethod(SignatureMethod SignatureMethodObject) {
		  this.SignatureMethodObject = SignatureMethodObject;
		 }

		 public void setReference(Reference ReferenceObject) {
		  this.ReferenceObject = ReferenceObject;
		 }
		}
		public class Reference {
		 Transforms TransformsObject;
		 DigestMethod DigestMethodObject;
		 private String DigestValue;
		 private String _URI;


		 // Getter Methods 

		 public Transforms getTransforms() {
		  return TransformsObject;
		 }

		 public DigestMethod getDigestMethod() {
		  return DigestMethodObject;
		 }

		 public String getDigestValue() {
		  return DigestValue;
		 }

		 public String get_URI() {
		  return _URI;
		 }

		 // Setter Methods 

		 public void setTransforms(Transforms TransformsObject) {
		  this.TransformsObject = TransformsObject;
		 }

		 public void setDigestMethod(DigestMethod DigestMethodObject) {
		  this.DigestMethodObject = DigestMethodObject;
		 }

		 public void setDigestValue(String DigestValue) {
		  this.DigestValue = DigestValue;
		 }

		 public void set_URI(String _URI) {
		  this._URI = _URI;
		 }
		}
		public class DigestMethod {
		 private String _Algorithm;


		 // Getter Methods 

		 public String get_Algorithm() {
		  return _Algorithm;
		 }

		 // Setter Methods 

		 public void set_Algorithm(String _Algorithm) {
		  this._Algorithm = _Algorithm;
		 }
		}
		public class Transforms {
		 Transform TransformObject;


		 // Getter Methods 

		 public Transform getTransform() {
		  return TransformObject;
		 }

		 // Setter Methods 

		 public void setTransform(Transform TransformObject) {
		  this.TransformObject = TransformObject;
		 }
		}
		public class Transform {
		 private String _Algorithm;


		 // Getter Methods 

		 public String get_Algorithm() {
		  return _Algorithm;
		 }

		 // Setter Methods 

		 public void set_Algorithm(String _Algorithm) {
		  this._Algorithm = _Algorithm;
		 }
		}
		public class SignatureMethod {
		 private String _Algorithm;


		 // Getter Methods 

		 public String get_Algorithm() {
		  return _Algorithm;
		 }

		 // Setter Methods 

		 public void set_Algorithm(String _Algorithm) {
		  this._Algorithm = _Algorithm;
		 }
		}
		public class CanonicalizationMethod {
		 private String _Algorithm;


		 // Getter Methods 

		 public String get_Algorithm() {
		  return _Algorithm;
		 }

		 // Setter Methods 

		 public void set_Algorithm(String _Algorithm) {
		  this._Algorithm = _Algorithm;
		 }
		}
		public class Content {
		 private String ArisingDate;
		 private String InvoiceName;
		 private String InvoicePattern;
		 private String SerialNo;
		 private String InvoiceNo;
		 private String Kind_of_Payment;
		 private String ComName;
		 private String ComTaxCode;
		 private String ComAddress;
		 private String ComPhone;
		 private String ComBankNo;
		 private String ComBankName;
		 private String CusCode;
		 private String CusName;
		 private String CusTaxCode;
		 private String CusPhone;
		 private String CusAddress;
		 private String CusBankName;
		 private String CusBankNo;
		 private String Total;
		 private String VAT_Amount;
		 private String Amount;
		 private String Amount_words;
		 private String Buyer;
		 private String KindOfService;
		 private String VAT_Rate;
		 private String Discount_Rate;
		 private String Discount_Amount;
		 Products ProductsObject;
		 private String Extra;
		 private String MaThanhToan;
		 private String qrCodeData;
		 private String isAdjust;
		 private String SignDate;
		 private String _Id;


		 // Getter Methods 

		 public String getArisingDate() {
		  return ArisingDate;
		 }

		 public String getInvoiceName() {
		  return InvoiceName;
		 }

		 public String getInvoicePattern() {
		  return InvoicePattern;
		 }

		 public String getSerialNo() {
		  return SerialNo;
		 }

		 public String getInvoiceNo() {
		  return InvoiceNo;
		 }

		 public String getKind_of_Payment() {
		  return Kind_of_Payment;
		 }

		 public String getComName() {
		  return ComName;
		 }

		 public String getComTaxCode() {
		  return ComTaxCode;
		 }

		 public String getComAddress() {
		  return ComAddress;
		 }

		 public String getComPhone() {
		  return ComPhone;
		 }

		 public String getComBankNo() {
		  return ComBankNo;
		 }

		 public String getComBankName() {
		  return ComBankName;
		 }

		 public String getCusCode() {
		  return CusCode;
		 }

		 public String getCusName() {
		  return CusName;
		 }

		 public String getCusTaxCode() {
		  return CusTaxCode;
		 }

		 public String getCusPhone() {
		  return CusPhone;
		 }

		 public String getCusAddress() {
		  return CusAddress;
		 }

		 public String getCusBankName() {
		  return CusBankName;
		 }

		 public String getCusBankNo() {
		  return CusBankNo;
		 }

		 public String getTotal() {
		  return Total;
		 }

		 public String getVAT_Amount() {
		  return VAT_Amount;
		 }

		 public String getAmount() {
		  return Amount;
		 }

		 public String getAmount_words() {
		  return Amount_words;
		 }

		 public String getBuyer() {
		  return Buyer;
		 }

		 public String getKindOfService() {
		  return KindOfService;
		 }

		 public String getVAT_Rate() {
		  return VAT_Rate;
		 }

		 public String getDiscount_Rate() {
		  return Discount_Rate;
		 }

		 public String getDiscount_Amount() {
		  return Discount_Amount;
		 }

		 public Products getProducts() {
		  return ProductsObject;
		 }

		 public String getExtra() {
		  return Extra;
		 }

		 public String getMaThanhToan() {
		  return MaThanhToan;
		 }

		 public String getQrCodeData() {
		  return qrCodeData;
		 }

		 public String getIsAdjust() {
		  return isAdjust;
		 }

		 public String getSignDate() {
		  return SignDate;
		 }

		 public String get_Id() {
		  return _Id;
		 }

		 // Setter Methods 

		 public void setArisingDate(String ArisingDate) {
		  this.ArisingDate = ArisingDate;
		 }

		 public void setInvoiceName(String InvoiceName) {
		  this.InvoiceName = InvoiceName;
		 }

		 public void setInvoicePattern(String InvoicePattern) {
		  this.InvoicePattern = InvoicePattern;
		 }

		 public void setSerialNo(String SerialNo) {
		  this.SerialNo = SerialNo;
		 }

		 public void setInvoiceNo(String InvoiceNo) {
		  this.InvoiceNo = InvoiceNo;
		 }

		 public void setKind_of_Payment(String Kind_of_Payment) {
		  this.Kind_of_Payment = Kind_of_Payment;
		 }

		 public void setComName(String ComName) {
		  this.ComName = ComName;
		 }

		 public void setComTaxCode(String ComTaxCode) {
		  this.ComTaxCode = ComTaxCode;
		 }

		 public void setComAddress(String ComAddress) {
		  this.ComAddress = ComAddress;
		 }

		 public void setComPhone(String ComPhone) {
		  this.ComPhone = ComPhone;
		 }

		 public void setComBankNo(String ComBankNo) {
		  this.ComBankNo = ComBankNo;
		 }

		 public void setComBankName(String ComBankName) {
		  this.ComBankName = ComBankName;
		 }

		 public void setCusCode(String CusCode) {
		  this.CusCode = CusCode;
		 }

		 public void setCusName(String CusName) {
		  this.CusName = CusName;
		 }

		 public void setCusTaxCode(String CusTaxCode) {
		  this.CusTaxCode = CusTaxCode;
		 }

		 public void setCusPhone(String CusPhone) {
		  this.CusPhone = CusPhone;
		 }

		 public void setCusAddress(String CusAddress) {
		  this.CusAddress = CusAddress;
		 }

		 public void setCusBankName(String CusBankName) {
		  this.CusBankName = CusBankName;
		 }

		 public void setCusBankNo(String CusBankNo) {
		  this.CusBankNo = CusBankNo;
		 }

		 public void setTotal(String Total) {
		  this.Total = Total;
		 }

		 public void setVAT_Amount(String VAT_Amount) {
		  this.VAT_Amount = VAT_Amount;
		 }

		 public void setAmount(String Amount) {
		  this.Amount = Amount;
		 }

		 public void setAmount_words(String Amount_words) {
		  this.Amount_words = Amount_words;
		 }

		 public void setBuyer(String Buyer) {
		  this.Buyer = Buyer;
		 }

		 public void setKindOfService(String KindOfService) {
		  this.KindOfService = KindOfService;
		 }

		 public void setVAT_Rate(String VAT_Rate) {
		  this.VAT_Rate = VAT_Rate;
		 }

		 public void setDiscount_Rate(String Discount_Rate) {
		  this.Discount_Rate = Discount_Rate;
		 }

		 public void setDiscount_Amount(String Discount_Amount) {
		  this.Discount_Amount = Discount_Amount;
		 }

		 public void setProducts(Products ProductsObject) {
		  this.ProductsObject = ProductsObject;
		 }

		 public void setExtra(String Extra) {
		  this.Extra = Extra;
		 }

		 public void setMaThanhToan(String MaThanhToan) {
		  this.MaThanhToan = MaThanhToan;
		 }

		 public void setQrCodeData(String qrCodeData) {
		  this.qrCodeData = qrCodeData;
		 }

		 public void setIsAdjust(String isAdjust) {
		  this.isAdjust = isAdjust;
		 }

		 public void setSignDate(String SignDate) {
		  this.SignDate = SignDate;
		 }

		 public void set_Id(String _Id) {
		  this._Id = _Id;
		 }
		}
		public class Products {
		 ArrayList < Object > Product = new ArrayList < Object > ();
		}

}
