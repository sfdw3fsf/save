package com.vn.vnptit.Oneapp_Com.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.underscore.lodash.U;
import com.vn.vnptit.Oneapp_Com.model.CapNhatKetQuaHDDTParam;
import com.vn.vnptit.Oneapp_Com.model.ConfirmPaymentFkeyResponse;
import com.vn.vnptit.Oneapp_Com.model.DownloadInvNoPayResultModel;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.ThongtinServiceHDDTModel;
import com.vn.vnptit.Oneapp_Com.model.UpdateCusResponse;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.ConfirmPaymentDetailFkeyResult;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTAdjustInvActionResponse;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConfirmPaymentInputModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConfirmPaymentRes;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForStoreFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForStoreFkeyOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForStoreOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForVerifyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForVerifyOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDeliverInvFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDeliverInvFkeyOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvFkeyNoPayHCMOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvFkeyNoPayOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvPDFFkeyNoPayOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvPDFInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvPDFOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyNoPayInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyNoPayModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyRes;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewNoPayInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewNoPayOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetSerialByPatternInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetSerialByPatternOutPut;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusFkeyOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusRes;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTReplaceInvResponse2;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTReplaceInvoiceActionResponse;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTResponse;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTUnConfirmPaymentRes;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTcancelInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTcancelInvOutput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuAdjustInvActionInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuCustomer;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuInvNoPayModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuThongTin;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTureReplaceInvoiceActionInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTureplaceInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.ImportAndPublishInvResponse;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.UnConfirmPaymentFkeyInputModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.UnConfirmPaymentFkeyResult;

public class SoapHttps {

	@Autowired
	RestTemplate restTemplate;
	static final String XMLDEVELOP = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";

	@SuppressWarnings("unchecked")
	public static ResponseObject downloadInvNoPay(HoaDonDienTuInvNoPayModel input, ThongtinServiceHDDTModel ttcnn) {
		ResponseObject modelResponse = new ResponseObject();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvNoPay");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInvNoPay  xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<invToken>" + input.getInvToken() + "</invToken>");
		xmlBody.append("<userName>" + StringEscapeUtils.escapeHtml(ttcnn.getUser()) + "</userName>");
		xmlBody.append("<userPass>" + StringEscapeUtils.escapeHtml(ttcnn.getPasswword()) + "</userPass>");
		xmlBody.append("</downloadInvNoPay>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvNoPayResponse");
			jsonData.remove("-xmlns");
			System.out.println(jsonData);

			DownloadInvNoPayResultModel curResponse = (DownloadInvNoPayResultModel) Converter.string2Object(U.toJson(jsonData), DownloadInvNoPayResultModel.class);
			if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith("ERR:")) {
				if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage("Không tìm thấy hóa đơn với chuỗi token truyền vào");
				} else if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}

			}
			if (!curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith("ERR:")) {
				JSONObject xmlJSONObj = XML.toJSONObject(curResponse.getDownloadInvNoPayResult());
				String jsonPrettyPrintString = xmlJSONObj.toString(4);
				ObjectMapper mapper = new ObjectMapper();

				Object map = null;
				try {
					map = mapper.readValue(jsonPrettyPrintString, Object.class);
				} catch (JsonMappingException e1) {

					map = null;

				} catch (JsonProcessingException e1) {
					map = null;
				}
				if (map == null) {
					modelResponse.setError("400");
					modelResponse.setError_code(ErrorCode.BSS_00004008);
					return modelResponse;
				}
				Map<String, Object> ttHD = (Map<String, Object>) map;
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);

				if (ttcnn.getTt78() == 1) {
					Map<String, Object> hDon = (Map<String, Object>) ttHD.get("HDon");
					if (hDon != null) {

						Map<String, Object> dLHDon = (Map<String, Object>) hDon.get("DLHDon");
						Map<String, Object> nDHDon = (Map<String, Object>) dLHDon.get("NDHDon");
						Map<String, Object> dSHHDVu = (Map<String, Object>) nDHDon.get("DSHHDVu");
						Map<String, Object> tToan = (Map<String, Object>) nDHDon.get("TToan");
						Map<String, Object> tHTTLTSuat = (Map<String, Object>) tToan.get("THTTLTSuat");

						Map<String, Object> nBan = (Map<String, Object>) nDHDon.get("NBan");
						Map<String, Object> nMua = (Map<String, Object>) nDHDon.get("NMua");

						// Map<String, Object> tTKhac;
						Map<String, Object> tTKhacNMua;
						try {
							// tTKhac = (Map<String, Object>) nBan.get("TTKhac");
							tTKhacNMua = (Map<String, Object>) nMua.get("TTKhac");
						} catch (Exception e) {
							// TODO: handle exception
							// tTKhac = null;
							tTKhacNMua = null;
						}
						// if (tTKhac != null) {
						// // mục đích -> TTin luôn trả về mảng
						// Object listTTin = tTKhac.get("TTin");
						// List<?> listprTTin = convertData(listTTin);
						//// List<?> listprTTin = null;
						//// if (!ArrayList.class.getName().equals(listTTin.getClass().getName())) {
						//// listprTTin = Utils.convertHashmaptoList((HashMap<String, Object>)
						// listTTin);
						//// } else {
						//// listprTTin = (List<?>) listTTin;
						//// }
						// tTKhac.replace("TTin", listprTTin);
						// }

						if (dSHHDVu != null) {
							// mục đích -> HHDVu luôn trả về mảng
							Object listprd = dSHHDVu.get("HHDVu");
							List<?> listpr = convertData(listprd);
							dSHHDVu.replace("HHDVu", listpr);
						}

						if (!(tHTTLTSuat == null)) {
							// mục đích -> LTSuat luôn trả về mảng
							Object lTSuat = tHTTLTSuat.get("LTSuat");
							List<?> listlTSuat = convertData(lTSuat);
							tHTTLTSuat.replace("LTSuat", listlTSuat);
						}

						if (tTKhacNMua != null) {
							// mục đích -> TTin luôn trả về mảng
							Object listTTin1 = tTKhacNMua.get("TTin");
							List<?> listprTTin1 = convertData(listTTin1);
							tTKhacNMua.put("TTin", listprTTin1);
						}

						modelResponse.setData(hDon);
						return modelResponse;
					}
				} else {
					Map<String, Object> dsContent = (Map<String, Object>) ttHD.get("Invoice");
					if (dsContent != null) {
						Map<String, Object> dsProduct = (Map<String, Object>) dsContent.get("Content");
						Map<String, Object> product = (Map<String, Object>) dsProduct.get("Products");
						Object listprd = product.get("Product");
						List<?> listpr = convertData(listprd);
						product.replace("Product", listpr);

						modelResponse.setData(dsContent);
						return modelResponse;
					}
				}
				modelResponse.setData(ttHD);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject downloadInvNoPay1(HoaDonDienTuInvNoPayModel input, ThongtinServiceHDDTModel ttcnn) {
		ResponseObject modelResponse = new ResponseObject();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvNoPay");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInvNoPay  xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<invToken>" + input.getInvToken() + "</invToken>");
		xmlBody.append("<userName>" + StringEscapeUtils.escapeHtml(ttcnn.getUser()) + "</userName>");
		xmlBody.append("<userPass>" + StringEscapeUtils.escapeHtml(ttcnn.getPasswword()) + "</userPass>");
		xmlBody.append("</downloadInvNoPay>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvNoPayResponse");
			jsonData.remove("-xmlns");
			System.out.println(jsonData);

			DownloadInvNoPayResultModel curResponse = (DownloadInvNoPayResultModel) Converter.string2Object(U.toJson(jsonData), DownloadInvNoPayResultModel.class);
			if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith("ERR:")) {
				if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage("Không tìm thấy hóa đơn với chuỗi token truyền vào");
				} else if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}

			}
			if (!curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith("ERR:")) {
				JSONObject xmlJSONObj = XML.toJSONObject(curResponse.getDownloadInvNoPayResult());
				String jsonPrettyPrintString = xmlJSONObj.toString(4);
				ObjectMapper mapper = new ObjectMapper();

				Object map = null;
				try {
					map = mapper.readValue(jsonPrettyPrintString, Object.class);
				} catch (JsonMappingException e1) {

					map = null;

				} catch (JsonProcessingException e1) {
					map = null;
				}
				if (map == null) {
					modelResponse.setError("400");
					modelResponse.setError_code(ErrorCode.BSS_00004008);
					return modelResponse;
				}
				Map<String, Object> ttHD = (Map<String, Object>) map;
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);

//				if (ttcnn.getTt78() == 1) {
//					Map<String, Object> hDon = (Map<String, Object>) ttHD.get("HDon");
//					if (hDon != null) {
//
//						Map<String, Object> dLHDon = (Map<String, Object>) hDon.get("DLHDon");
//						Map<String, Object> nDHDon = (Map<String, Object>) dLHDon.get("NDHDon");
//						Map<String, Object> dSHHDVu = (Map<String, Object>) nDHDon.get("DSHHDVu");
//						Map<String, Object> tToan = (Map<String, Object>) nDHDon.get("TToan");
//						Map<String, Object> tHTTLTSuat = (Map<String, Object>) tToan.get("THTTLTSuat");
//
//						Map<String, Object> nBan = (Map<String, Object>) nDHDon.get("NBan");
//						Map<String, Object> nMua = (Map<String, Object>) nDHDon.get("NMua");
//
//						// Map<String, Object> tTKhac;
//						Map<String, Object> tTKhacNMua;
//						try {
//							// tTKhac = (Map<String, Object>) nBan.get("TTKhac");
//							tTKhacNMua = (Map<String, Object>) nMua.get("TTKhac");
//						} catch (Exception e) {
//							// TODO: handle exception
//							// tTKhac = null;
//							tTKhacNMua = null;
//						}
//						// if (tTKhac != null) {
//						// // mục đích -> TTin luôn trả về mảng
//						// Object listTTin = tTKhac.get("TTin");
//						// List<?> listprTTin = convertData(listTTin);
//						//// List<?> listprTTin = null;
//						//// if (!ArrayList.class.getName().equals(listTTin.getClass().getName())) {
//						//// listprTTin = Utils.convertHashmaptoList((HashMap<String, Object>)
//						// listTTin);
//						//// } else {
//						//// listprTTin = (List<?>) listTTin;
//						//// }
//						// tTKhac.replace("TTin", listprTTin);
//						// }
//
//						if (dSHHDVu != null) {
//							// mục đích -> HHDVu luôn trả về mảng
//							Object listprd = dSHHDVu.get("HHDVu");
//							List<?> listpr = convertData(listprd);
//							dSHHDVu.replace("HHDVu", listpr);
//						}
//
//						if (!(tHTTLTSuat == null)) {
//							// mục đích -> LTSuat luôn trả về mảng
//							Object lTSuat = tHTTLTSuat.get("LTSuat");
//							List<?> listlTSuat = convertData(lTSuat);
//							tHTTLTSuat.replace("LTSuat", listlTSuat);
//						}
//
//						if (tTKhacNMua != null) {
//							// mục đích -> TTin luôn trả về mảng
//							Object listTTin1 = tTKhacNMua.get("TTin");
//							List<?> listprTTin1 = convertData(listTTin1);
//							tTKhacNMua.put("TTin", listprTTin1);
//						}
//
//						modelResponse.setData(hDon);
//						return modelResponse;
//					}
//				} else {
//					Map<String, Object> dsContent = (Map<String, Object>) ttHD.get("Invoice");
//					if (dsContent != null) {
//						Map<String, Object> dsProduct = (Map<String, Object>) dsContent.get("Content");
//						Map<String, Object> product = (Map<String, Object>) dsProduct.get("Products");
//						Object listprd = product.get("Product");
//						List<?> listpr = convertData(listprd);
//						product.replace("Product", listpr);
//
//						modelResponse.setData(dsContent);
//						return modelResponse;
//					}
//				}
				modelResponse.setData(ttHD);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	private static List<?> convertData(Object data) {
		List<?> listpr;
		if (!data.getClass().isAssignableFrom(ArrayList.class)) {

			listpr = Utils.convertHashmaptoList((HashMap<String, Object>) data);
		} else {
			listpr = (List<?>) data;
		}
		return listpr;
	}

	public static ResponseObject downloadInvNoPayxml(HoaDonDienTuInvNoPayModel input, ThongtinServiceHDDTModel ttcnn) {
		ResponseObject modelResponse = new ResponseObject();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvNoPay");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInvNoPay  xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<invToken>" + input.getInvToken() + "</invToken>");
		xmlBody.append("<userName>" + StringEscapeUtils.escapeHtml(ttcnn.getUser()) + "</userName>");
		xmlBody.append("<userPass>" + StringEscapeUtils.escapeHtml(ttcnn.getPasswword()) + "</userPass>");
		xmlBody.append("</downloadInvNoPay>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvNoPayResponse");
			jsonData.remove("-xmlns");
			System.out.println(jsonData);

			DownloadInvNoPayResultModel curResponse = (DownloadInvNoPayResultModel) Converter.string2Object(U.toJson(jsonData), DownloadInvNoPayResultModel.class);
			if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith("ERR:")) {
				if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage("Không tìm thấy hóa đơn với chuỗi token truyền vào");
				} else if (curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}

			}
			if (!curResponse.getDownloadInvNoPayResult().toUpperCase().startsWith("ERR:")) {
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.HDDTRESPONSE, curResponse.getDownloadInvNoPayResult());

				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject adjustInvAction(HoaDonDienTuAdjustInvActionInput input, ThongtinServiceHDDTModel ttcnn) {
		ResponseObject modelResponse = new ResponseObject();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/AdjustInvoiceAction");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<AdjustInvoiceAction xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<Account>" + ttcnn.getAccount() + "</Account>");
		xmlBody.append("<ACpass>" + ttcnn.getaCpass() + "</ACpass>");
		xmlBody.append("<xmlInvData>" + input.toXml() + "</xmlInvData>");
		xmlBody.append("<username>" + ttcnn.getUser() + "</username>");
		// if (!ttcnn.getTt78().equals(1)) {
		xmlBody.append("<pass>" + ttcnn.getPasswword() + "</pass>");
		// } else {
		// xmlBody.append(
		// "<password>" + ttcnn.getPasswword() + "</password>");
		// }
		xmlBody.append("<fkey>" + input.getFkey() + "</fkey>");

		xmlBody.append("<AttachFile>" + input.getAttachFile() + "</AttachFile>");
		xmlBody.append("<convert>" + input.getConvert() + "</convert>");
		xmlBody.append("<pattern>" + input.getPattern() + "</pattern>");
		xmlBody.append("<serial>" + input.getSerial() + "</serial>");
		xmlBody.append("</AdjustInvoiceAction>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.AdjustInvoiceActionResponse");
			jsonData.remove("-xmlns");

			HDDTAdjustInvActionResponse curResponse = (HDDTAdjustInvActionResponse) Converter.string2Object(U.toJson(jsonData), HDDTAdjustInvActionResponse.class);

			if (curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith("ERR:")) {
				if (curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_KHONG_COQUYEN_THEM_KH);
				} else if (curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR20)) {
					modelResponse.setMessage(ConstantString.MESSAGE_PATTERN_SERIAL_KHONG_PHUHOP);
				} else if (curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage(curResponse.getAdjustInvoiceActionResult());
				} else if (curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_USERNAM_KHONG_PHUHOP);

				} else if (curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR5)) {
					modelResponse.setMessage("Không phát hành được hóa đơn");
				} else if (curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith("ERR:10")) {
					modelResponse.setMessage("Lô có số hóa đơn vượt quá max cho phép");
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}
			}

			if (!curResponse.getAdjustInvoiceActionResult().toUpperCase().startsWith("ERR:")) {
				// OK:01GTKT3/001;AA/12E;key1_2
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getAdjustInvoiceActionResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid.toString());
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject replaceInv(HoaDonDienTureplaceInvInput input, ThongtinServiceHDDTModel ttcnn) {
		ResponseObject modelResponse = new ResponseObject();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/replaceInv");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<replaceInv xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<Account>" + ttcnn.getAccount() + "</Account>");
		xmlBody.append("<ACpass>" + ttcnn.getaCpass() + "</ACpass>");
		xmlBody.append("<xmlInvData>" + input.toXml() + "</xmlInvData>");
		xmlBody.append("<username>" + ttcnn.getUser() + "</username>");
		// if (!ttcnn.getTt78().equals(1)) {
		xmlBody.append("<pass>" + ttcnn.getPasswword() + "</pass>");
		// } else {
		// xmlBody.append(
		// "<password>" + ttcnn.getPasswword() + "</password>");
		// }
		xmlBody.append(" <fkey>" + input.getFkey() + "</fkey>");
		xmlBody.append("<convert>" + input.getConvert() + "</convert>");
		xmlBody.append("</replaceInv>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.replaceInvResponse");
			jsonData.remove("-xmlns");


			HDDTReplaceInvResponse2 curResponse = (HDDTReplaceInvResponse2) Converter.string2Object(U.toJson(jsonData), HDDTReplaceInvResponse2.class);

			if (curResponse.getReplaceInvResult().toUpperCase().startsWith("ERR")) {
				if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_KHONG_COQUYEN_THEM_KH);
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR20)) {
					modelResponse.setMessage(ConstantString.MESSAGE_PATTERN_SERIAL_KHONG_PHUHOP);
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage("Không tồn tại hóa đơn cần thay thế");
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR3)) {
					modelResponse.setMessage(ConstantString.MESSAGE_XML_KHONG_DUNG_QUYDINH);
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR5)) {
					modelResponse.setMessage("Có lỗi trong quá trình thay thế hóa đơn; Lỗi phát hành hóa đơn");
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage("Dải hóa đơn cũ đã hết");
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_USERNAM_KHONG_PHUHOP);
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR8)) {
					modelResponse.setMessage("Hóa đơn đã được thay thế rồi. Không thể thay thế nữa.");
				} else if (curResponse.getReplaceInvResult().toUpperCase().startsWith(ConstantString.ERR9)) {
					modelResponse.setMessage("Trạng thái hóa đơn ko được thay thế.");
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}

			}

			if (!curResponse.getReplaceInvResult().toUpperCase().startsWith("ERR")) {
				// OK:01GTKT3/001;AA/12E;0000001
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getReplaceInvResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid.toString());
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject replaceInvoiceAction(HoaDonDienTureReplaceInvoiceActionInput input, ThongtinServiceHDDTModel ttcnn) {
		ResponseObject modelResponse = new ResponseObject();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/ReplaceInvoiceAction");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<ReplaceInvoiceAction xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<Account>" + ttcnn.getAccount() + "</Account>");
		xmlBody.append("<ACpass>" + ttcnn.getaCpass() + "</ACpass>");
		xmlBody.append("<xmlInvData>" + input.toXml() + "</xmlInvData>");
		xmlBody.append("<username>" + ttcnn.getUser() + "</username>");
		// if (!ttcnn.getTt78().equals(1)) {
		xmlBody.append("<pass>" + ttcnn.getPasswword() + "</pass>");
		// } else {
		// xmlBody.append(
		// "<password>" + ttcnn.getPasswword() + "</password>");
		// }
		xmlBody.append(" <fkey>" + input.getFkey() + "</fkey>");
		xmlBody.append(" <Attachfile>" + input.getAttachFile() + "</Attachfile>");
		xmlBody.append("<convert>" + input.getConvert() + "</convert>");
		xmlBody.append("<pattern>" + input.getPattern() + "</pattern>");
		xmlBody.append("<serial>" + input.getSerial() + "</serial>");
		xmlBody.append("</ReplaceInvoiceAction>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.ReplaceInvoiceActionResponse");
			jsonData.remove("-xmlns");


			HDDTReplaceInvoiceActionResponse curResponse = (HDDTReplaceInvoiceActionResponse) Converter.string2Object(U.toJson(jsonData), HDDTReplaceInvoiceActionResponse.class);

			if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR")) {
				if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR:13")) {
					modelResponse.setMessage("Lỗi trùng fkey");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR:14")) {
					modelResponse.setMessage("Lỗi trong quá trình thực hiện cấp số hóa đơn");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR:15")) {
					modelResponse.setMessage("Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR:19")) {
					modelResponse.setMessage("Pattern truyền vào không giống với hóa đơn cần điều chỉnh");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR:21")) {
					modelResponse.setMessage("Trùng Fkey truyền vào");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR:29")) {
					modelResponse.setMessage("Lỗi chứng thư hết hạn");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR:30")) {
					modelResponse.setMessage("Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_KHONG_COQUYEN_THEM_KH);
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR20)) {
					modelResponse.setMessage(ConstantString.MESSAGE_PATTERN_SERIAL_KHONG_PHUHOP);
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage("Không tồn tại hóa đơn cần thay thế");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR3)) {
					modelResponse.setMessage(ConstantString.MESSAGE_XML_KHONG_DUNG_QUYDINH);
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR5)) {
					modelResponse.setMessage("Có lỗi trong quá trình thay thế hóa đơn; Lỗi phát hành hóa đơn");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage("Dải hóa đơn cũ đã hết");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_USERNAM_KHONG_PHUHOP);
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR8)) {
					modelResponse.setMessage("Hóa đơn đã được thay thế rồi. Không thể thay thế nữa.");
				} else if (curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith(ConstantString.ERR9)) {
					modelResponse.setMessage("Trạng thái hóa đơn ko được thay thế.");
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}

			}

			if (!curResponse.getReplaceInvoiceActionResult().toUpperCase().startsWith("ERR")) {
				// OK:01GTKT3/001;AA/12E;0000001
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getReplaceInvoiceActionResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid.toString());
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject listInvByCus(HDDTListInvByCusModel input, ThongtinServiceHDDTModel ttcnn) {
		ResponseObject modelResponse = new ResponseObject();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/listInvByCus");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<listInvByCus xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<cusCode>" + input.getCusCode() + "</cusCode>");
		xmlBody.append("<fromDate>" + input.getFromDate() + "</fromDate>");
		xmlBody.append("<toDate>" + input.getToDate() + "</toDate>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</listInvByCus>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.listInvByCusResponse");
			jsonData.remove("-xmlns");

			HDDTListInvByCusRes curResponse = (HDDTListInvByCusRes) Converter.string2Object(U.toJson(jsonData), HDDTListInvByCusRes.class);

			if (curResponse.getListInvByCusResult().toUpperCase().startsWith("ERR")) {
				if (curResponse.getListInvByCusResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getListInvByCusResult().toUpperCase().startsWith(ConstantString.ERR3)) {
					modelResponse.setMessage("Không tồn tại khách hàng tương ứng với mã khách hàng");
				} else if (curResponse.getListInvByCusResult().toUpperCase().startsWith("ERR:4")) {
					modelResponse.setMessage("Công ty chưa được đăng kí mẫu hóa đơn nào");
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}
			}

			if (!curResponse.getListInvByCusResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				ArrayList value = null;
				try {
					value = xmlMapper.readValue(curResponse.getListInvByCusResult(), ArrayList.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid.toString());
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject getInvViewFkey(HDDTGetInvViewFkeyInput input, ThongtinServiceHDDTModel ttcnn) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/getInvViewFkey");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<getInvViewFkey xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<fkey>" + input.getFkey() + "</fkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</getInvViewFkey>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.getInvViewFkeyResponse");
			jsonData.remove("-xmlns");
			HDDTGetInvViewFkeyRes curResponse = (HDDTGetInvViewFkeyRes) Converter.string2Object(U.toJson(jsonData), HDDTGetInvViewFkeyRes.class);
			if (curResponse.getGetInvViewFkeyResult().toUpperCase().startsWith("ERR")) {

				Integer errorCode = Integer.parseInt(curResponse.getGetInvViewFkeyResult().replace("ERR:", ""));

				switch (errorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;

				case 6:
					modelResponse.setMessage("Chuỗi Fkey không chính xác");
					break;
				case 7:
					modelResponse.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
					break;

				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
			}

			if (!curResponse.getGetInvViewFkeyResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				ArrayList value = null;
				try {
					value = xmlMapper.readValue(curResponse.getGetInvViewFkeyResult(), ArrayList.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid.toString());
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static HDDTResponse hoaDonUpdateCus(HoaDonDienTuCustomer input, ThongtinServiceHDDTModel ttcnn) {

		HDDTResponse modelResponse = new HDDTResponse();
		UUID uuid = UUID.randomUUID();
		String url = ttcnn.getUrl() + ttcnn.getPublicservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/UpdateCus");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<UpdateCus xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<XMLCusData>" + input.toXml() + "</XMLCusData>");
		xmlBody.append("<username>" + StringEscapeUtils.escapeHtml(ttcnn.getUser()) + "</username>");
		// if (!ttcnn.getTt78().equals(1)) {
		xmlBody.append("<pass>" + ttcnn.getPasswword() + "</pass>");
		// } else {
		// xmlBody.append("<password>" + ttcnn.getPasswword() + "</password>");
		// }
		xmlBody.append("<convert>0</convert>");
		xmlBody.append("</UpdateCus>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		modelResponse.setRequest_id(xmlBody.toString());
		modelResponse.setUrl(url);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			modelResponse.setOutput(response.toString());
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.UpdateCusResponse");
			jsonData.remove("-xmlns");

			UpdateCusResponse cusResponse = (UpdateCusResponse) Converter.string2Object(U.toJson(jsonData), UpdateCusResponse.class);

			switch (cusResponse.getUpdateCusResult()) {
			case -1:
				modelResponse.setMessage(ConstantString.MESSAGE_TK_KHONG_COQUYEN_THEM_KH);
				break;
			case -2:
				modelResponse.setMessage("Không import được khách hàng vào db");
				break;
			case -3:
				modelResponse.setMessage(ConstantString.MESSAGE_XML_KHONG_DUNG_QUYDINH);
				break;
			default:
				modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
				break;
			}
			if (cusResponse.getUpdateCusResult() == 1) {
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(cusResponse.getUpdateCusResult());
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);

			} else {
				modelResponse.setData(null);
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = (HDDTResponse) Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;

		}

	}

	public static HDDTResponse importAndPublishInv(HoaDonDienTuThongTin input, ThongtinServiceHDDTModel ttcnn) {

		HDDTResponse modelResponse = new HDDTResponse();

		String url = ttcnn.getUrl() + ttcnn.getPublicservice();
		RestTemplate restTemplate = new RestTemplate();
		UUID uuid = UUID.randomUUID();
		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/ImportAndPublishInv");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<ImportAndPublishInv xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<Account>" + StringEscapeUtils.escapeHtml(ttcnn.getAccount()) + "</Account>");
		xmlBody.append("<ACpass>" + StringEscapeUtils.escapeHtml(ttcnn.getaCpass()) + "</ACpass>");
		xmlBody.append("<xmlInvData>" + input.toXml(ttcnn.getTt78()) + "</xmlInvData>");
		xmlBody.append("<username>" + StringEscapeUtils.escapeHtml(ttcnn.getUser()) + "</username>");

		if (!ttcnn.getTt78().equals(1)) {
			xmlBody.append("<pass>" + ttcnn.getPasswword() + "</pass>");
		} else {
			xmlBody.append("<password>" + ttcnn.getPasswword() + "</password>");
		}

		xmlBody.append("<pattern>" + StringEscapeUtils.escapeHtml(input.getPattern()) + "</pattern>");
		xmlBody.append("<serial>" + StringEscapeUtils.escapeHtml(input.getSerial()) + "</serial>");
		xmlBody.append("<convert>0</convert>");
		xmlBody.append("</ImportAndPublishInv>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		modelResponse.setRequest_id(xmlBody.toString());
		modelResponse.setUrl(url);
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			modelResponse.setOutput(response.toString());
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.ImportAndPublishInvResponse");
			jsonData.remove("-xmlns");

			ImportAndPublishInvResponse cusResponse = (ImportAndPublishInvResponse) Converter.string2Object(U.toJson(jsonData), ImportAndPublishInvResponse.class);

			if (cusResponse.getResult().toUpperCase().startsWith("ERR")) {
				String[] retInfo = cusResponse.getResult().split("ERR:");
				int error = Integer.parseInt(retInfo[1]);
				switch (error) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_KHONG_COQUYEN_THEM_KH);
					break;
				case 3:
					modelResponse.setMessage(ConstantString.MESSAGE_XML_KHONG_DUNG_QUYDINH);
					break;
				case 5:
					modelResponse.setMessage("Không phát hành được hóa đơn");
					break;
				case 7:
					modelResponse.setMessage(ConstantString.MESSAGE_USERNAM_KHONG_PHUHOP);
					break;
				case 10:
					modelResponse.setMessage("Lô có số hóa đơn vượt quá max cho phép");
					break;
				case 20:
					modelResponse.setMessage(ConstantString.MESSAGE_PATTERN_SERIAL_KHONG_PHUHOP);
					break;
				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
				modelResponse.setData(null);
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004009);

			} else {
				String[] result = cusResponse.getResult().split(";");
				String maGDFull = "";
				String invoiceNo = "";
				if (result.length > 1) {
					if (result[1].split("_").length > 1) {
						String[] rtInvs = result[1].split("_", 2);
						maGDFull = rtInvs[0];
						invoiceNo = rtInvs[1];
					}
				} else {
					maGDFull = cusResponse.getResult();
					invoiceNo = "";
				}

				CapNhatKetQuaHDDTParam data = new CapNhatKetQuaHDDTParam();
				data.setP_fkey(input.getFkey());
				data.setP_invoice_no(invoiceNo);
				data.setP_ma_gd_full(maGDFull);
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(data);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = (HDDTResponse) Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;

		}
	}

	public static HDDTResponse confirmPaymentFkey(HoaDonDienTuThongTin hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn) {

		HDDTResponse modelResponse = new HDDTResponse();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();
		UUID uuid = UUID.randomUUID();
		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/confirmPaymentFkey");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<confirmPaymentFkey xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<lstFkey>" + StringEscapeUtils.escapeHtml(hoaDonDienTuThongTin.getFkey()) + "</lstFkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("<pattern>" + StringEscapeUtils.escapeHtml(hoaDonDienTuThongTin.getPattern()) + "</pattern>");
		xmlBody.append("<serial>" + StringEscapeUtils.escapeHtml(hoaDonDienTuThongTin.getSerial()) + "</serial>");
		xmlBody.append("</confirmPaymentFkey>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		modelResponse.setRequest_id(xmlBody.toString());
		modelResponse.setUrl(url);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			modelResponse.setOutput(response.toString());
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.confirmPaymentFkeyResponse");

			jsonData.remove("-xmlns");

			ConfirmPaymentFkeyResponse fkeyResponse = (ConfirmPaymentFkeyResponse) Converter.string2Object(U.toJson(jsonData), ConfirmPaymentFkeyResponse.class);

			if (fkeyResponse.getResult().toUpperCase().startsWith("ERR")) {
				Integer retInfo = Integer.parseInt(fkeyResponse.getResult().replace("ERR:", ""));
				// String[] retInfo = fkeyResponse.getResult().split("\\:");
				switch (retInfo) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_KHONG_COQUYEN_THEM_KH);
					break;
				case 2:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_TIMTHAY_HOADON);
					break;
				case 7:
					modelResponse.setMessage("Không phân phối được");
					break;
				case 13:
					modelResponse.setMessage("Hóa đơn đã gạch nợ rồi");
					break;
				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}

				modelResponse.setData(null);
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004010);

			} else {
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(fkeyResponse.getResult());
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = (HDDTResponse) Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject confirmPayment(HDDTConfirmPaymentInputModel hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/confirmPayment");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");

		xmlBody.append("<confirmPayment xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<lstInvToken>" + hoaDonDienTuThongTin.getLstInvToken() + "</lstInvToken>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");

		xmlBody.append("</confirmPayment>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.confirmPaymentResponse");

			jsonData.remove("-xmlns");

			HDDTConfirmPaymentRes curResponse = (HDDTConfirmPaymentRes) Converter.string2Object(U.toJson(jsonData), HDDTConfirmPaymentRes.class);

			if (curResponse.getConfirmPaymentResult().toUpperCase().startsWith("ERR")) {
				Integer ErrorCode = Integer.parseInt(curResponse.getConfirmPaymentResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;
				case 2:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_TIMTHAY_HOADON);
					break;
				case 7:
					modelResponse.setMessage("Không thanh toán được");
					break;
				case 13:
					modelResponse.setMessage("Chuỗi token đúng nhưng của hóa đơn đã thanh toán");
					break;
				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
			}

			if (!curResponse.getConfirmPaymentResult().toUpperCase().startsWith("ERR")) {
				// OK:
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getConfirmPaymentResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid.toString());
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject unConfirmPayment(HDDTConfirmPaymentInputModel hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn, String uuid) {

		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/unConfirmPayment");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");

		xmlBody.append("<unConfirmPayment xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<lstInvToken>" + hoaDonDienTuThongTin.getLstInvToken() + "</lstInvToken>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");

		xmlBody.append("</unConfirmPayment>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.unConfirmPaymentResponse");

			jsonData.remove("-xmlns");

			HDDTUnConfirmPaymentRes curResponse = (HDDTUnConfirmPaymentRes) Converter.string2Object(U.toJson(jsonData), HDDTUnConfirmPaymentRes.class);

			if (curResponse.getUnConfirmPaymentResult().toUpperCase().startsWith("ERR")) {
				Integer ErrorCode = Integer.parseInt(curResponse.getUnConfirmPaymentResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;
				case 2:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_TIMTHAY_HOADON);
					break;
				case 7:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_BO_GACHNO);
					break;
				case 8:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_THUOC_PHANQUYEN_KYHIEU);
					break;
				case 13:
					modelResponse.setMessage(ConstantString.MESSAGE_FKEY_CHUA_HD_CHUA_THANHTOAN);
					break;
				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
			}

			if (!curResponse.getUnConfirmPaymentResult().toUpperCase().startsWith("ERR")) {
				// OK:
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getUnConfirmPaymentResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject unConfirmPaymentFkey(UnConfirmPaymentFkeyInputModel hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn, String uuid) {

		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/UnConfirmPaymentFkey");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");

		xmlBody.append("<UnConfirmPaymentFkey xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<lstFkey>" + hoaDonDienTuThongTin.getLstFkey() + "</lstFkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");

		xmlBody.append("</UnConfirmPaymentFkey>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.UnConfirmPaymentFkeyResponse");

			jsonData.remove("-xmlns");
			System.out.println(jsonData);
			UnConfirmPaymentFkeyResult curResponse = (UnConfirmPaymentFkeyResult) Converter.string2Object(U.toJson(jsonData), UnConfirmPaymentFkeyResult.class);

			if (curResponse.getUnConfirmPaymentFkeyResult().toUpperCase().startsWith("ERR")) {
				Integer ErrorCode = Integer.parseInt(curResponse.getUnConfirmPaymentFkeyResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;
				case 2:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_TIMTHAY_HOADON);
					break;
				case 7:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_BO_GACHNO);
					break;
				case 8:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_THUOC_PHANQUYEN_KYHIEU);
					break;
				case 13:
					modelResponse.setMessage(ConstantString.MESSAGE_FKEY_CHUA_HD_CHUA_THANHTOAN);
					break;
				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
			}

			if (!curResponse.getUnConfirmPaymentFkeyResult().toUpperCase().startsWith("ERR")) {
				// OK:
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getUnConfirmPaymentFkeyResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ResponseObject confirmPaymentDetailFkey(UnConfirmPaymentFkeyInputModel hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn, String uuid) {

		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/confirmPaymentDetailFkey");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");

		xmlBody.append("<confirmPaymentDetailFkey xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<lstFkey>" + hoaDonDienTuThongTin.getLstFkey() + "</lstFkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");

		xmlBody.append("</confirmPaymentDetailFkey>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.UnConfirmPaymentFkeyResponse");

			jsonData.remove("-xmlns");

			ConfirmPaymentDetailFkeyResult curResponse = (ConfirmPaymentDetailFkeyResult) Converter.string2Object(U.toJson(jsonData), ConfirmPaymentDetailFkeyResult.class);

			if (curResponse.getConfirmPaymentDetailFkeyResult().toUpperCase().startsWith("ERR")) {
				Integer ErrorCode = Integer.parseInt(curResponse.getConfirmPaymentDetailFkeyResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;
				case 2:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_TIMTHAY_HOADON);
					break;
				case 7:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_BO_GACHNO);
					break;
				case 8:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_THUOC_PHANQUYEN_KYHIEU);
					break;
				case 13:
					modelResponse.setMessage(ConstantString.MESSAGE_FKEY_CHUA_HD_CHUA_THANHTOAN);
					break;
				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
			}

			if (!curResponse.getConfirmPaymentDetailFkeyResult().toUpperCase().startsWith("ERR")) {
				// OK:
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getConfirmPaymentDetailFkeyResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject cancelInv(HDDTcancelInvInput hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn, String uuid) {

		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/cancelInv");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<cancelInv xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<Account>" + ttcnn.getAccount() + "</Account>");
		xmlBody.append("<ACpass>" + ttcnn.getaCpass() + "</ACpass>");
		xmlBody.append("<fkey>" + hoaDonDienTuThongTin.getFkey() + "</fkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");

		xmlBody.append("</cancelInv>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.cancelInvResponse");

			jsonData.remove("-xmlns");

			HDDTcancelInvOutput curResponse = (HDDTcancelInvOutput) Converter.string2Object(U.toJson(jsonData), HDDTcancelInvOutput.class);

			if (curResponse.getCancelInvResult().toUpperCase().startsWith("ERR")) {
				if (curResponse.getCancelInvResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage("Tài khoản đăng nhập sai hoặc không có quyền");
				} else if (curResponse.getCancelInvResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage("Không tồn tại hóa đơn cần hủy");
				} else if (curResponse.getCancelInvResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage("Sequence contains more than one element");
				} else if (curResponse.getCancelInvResult().toUpperCase().startsWith(ConstantString.ERR8)) {
					modelResponse.setMessage("Hóa đơn đã được thay thế rồi, hủy rồi");
				} else if (curResponse.getCancelInvResult().toUpperCase().startsWith(ConstantString.ERR9)) {
					modelResponse.setMessage("Trạng thái hóa đơn ko được hủy");
				} else {
					modelResponse.setMessage(ConstantString.HDDTFAIL);
				}
			}

			if (!curResponse.getCancelInvResult().toUpperCase().startsWith("ERR")) {
				// OK:
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getCancelInvResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject getSerialByPattern(HDDTGetSerialByPatternInput hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn, String uuid) {

		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/getSerialByPattern");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<getSerialByPattern xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("<pattern>" + hoaDonDienTuThongTin.getPattern() + "</pattern>");
		xmlBody.append("</getSerialByPattern>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.getSerialByPatternResponse");

			jsonData.remove("-xmlns");

			HDDTGetSerialByPatternOutPut curResponse = (HDDTGetSerialByPatternOutPut) Converter.string2Object(U.toJson(jsonData), HDDTGetSerialByPatternOutPut.class);

			if (curResponse.getGetSerialByPatternResult().toUpperCase().startsWith("ERR")) {

				if (curResponse.getGetSerialByPatternResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getGetSerialByPatternResult().toUpperCase().startsWith(ConstantString.ERR20)) {
					modelResponse.setMessage("Không có danh sách ký hiệu hóa đơn");
				} else
					modelResponse.setMessage(ConstantString.HDDTFAIL);

			}
			if (!curResponse.getGetSerialByPatternResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				Object value = null;
				try {
					value = xmlMapper.readValue(curResponse.getGetSerialByPatternResult(), Object.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject deliverInvFkey(HDDTDeliverInvFkeyInput hoaDonDienTuThongTin, ThongtinServiceHDDTModel ttcnn, String uuid) {

		ResponseObject modelResponse = new ResponseObject();

		String url = ttcnn.getUrl() + ttcnn.getBizservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/deliverInvFkey");
		StringBuffer xmlBody = new StringBuffer();

		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<deliverInvFkey xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<lstFkey>" + hoaDonDienTuThongTin.getLstFkey() + "</lstFkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");

		xmlBody.append("</deliverInvFkey>");
		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.deliverInvFkeyResponse");

			jsonData.remove("-xmlns");

			HDDTDeliverInvFkeyOutput curResponse = (HDDTDeliverInvFkeyOutput) Converter.string2Object(U.toJson(jsonData), HDDTDeliverInvFkeyOutput.class);

			if (curResponse.getDeliverInvFkeyResult().toUpperCase().startsWith("ERR")) {
				Integer ErrorCode = Integer.parseInt(curResponse.getDeliverInvFkeyResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;
				case 2:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_TIMTHAY_HOADON);
					break;
				default:
					modelResponse.setMessage(ConstantString.HDDTFAIL);
					break;
				}
			}
			if (!curResponse.getDeliverInvFkeyResult().toUpperCase().startsWith("ERR")) {
				// OK:
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getDeliverInvFkeyResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}

			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject listInvByCusFkey(HDDTListInvByCusFkeyInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/listInvByCusFkey");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<listInvByCusFkey xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<key>" + input.getKey() + "</key>");
		xmlBody.append("<fromDate>" + input.getFromDate() + "</fromDate>");
		xmlBody.append("<toDate>" + input.getToDate() + "</toDate>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</listInvByCusFkey>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.listInvByCusFkeyResponse");
			jsonData.remove("-xmlns");


			HDDTListInvByCusFkeyOutput curResponse = (HDDTListInvByCusFkeyOutput) Converter.string2Object(U.toJson(jsonData), HDDTListInvByCusFkeyOutput.class);
			// String xmlStr
			// ="<Data><Item><index>11</index><cusCode>HPG-LD/00734746</cusCode><invToken>01GTKT0/001;AA/21E;21</invToken><name>Hóa
			// đơn giá trị gia tăng</name><publishDate>11/4/2021 3:16:13
			// PM</publishDate><signStatus>0</signStatus><pattern>01GTKT0/001</pattern><serial>AA/21E</serial><invNum>0000021</invNum><amount>2119350</amount><status>1</status><cusname><![CDATA[Doanh
			// NghiÖp T­ Nh©n Th­¬ng M¹i Lª B¶o
			// Ch©u]]></cusname><payment>0</payment><fkey>200001GTKT0001AA21E21</fkey><type>0</type><kindOfService></kindOfService></Item><Item><index>12</index><cusCode>HPG-LD/00734746</cusCode><invToken>01GTKT0/001;AA/21E;21</invToken><name>Hóa
			// đơn giá trị gia tăng</name><publishDate>11/4/2021 3:16:13
			// PM</publishDate><signStatus>0</signStatus><pattern>01GTKT0/001</pattern><serial>AA/21E</serial><invNum>0000021</invNum><amount>2119350</amount><status>1</status><cusname><![CDATA[Doanh
			// NghiÖp T­ Nh©n Th­¬ng M¹i Lª B¶o
			// Ch©u]]></cusname><payment>0</payment><fkey>200001GTKT0001AA21E21</fkey><type>0</type><kindOfService></kindOfService></Item></Data>";
			if (curResponse.getListInvByCusFkeyResult().toUpperCase().startsWith("ERR")) {

				Integer ErrorCode = Integer.parseInt(curResponse.getListInvByCusFkeyResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;

				case 3:
					modelResponse.setMessage("Không tồn tài khách hàng tương ứng với cusCode");
					break;
				case 4:
					modelResponse.setMessage("Công ty chưa được đăng kí mẫu hóa đơn nào");
					break;

				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
			}

			if (!curResponse.getListInvByCusFkeyResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				ArrayList value = null;
				try {
					value = xmlMapper.readValue(curResponse.getListInvByCusFkeyResult(), ArrayList.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject getInvViewNoPay(HDDTGetInvViewNoPayInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/getInvViewNoPay");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<getInvViewNoPay xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<invToken>" + input.getInvToken() + "</invToken>");

		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</getInvViewNoPay>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.getInvViewNoPayResponse");
			jsonData.remove("-xmlns");


			HDDTGetInvViewNoPayOutput curResponse = (HDDTGetInvViewNoPayOutput) Converter.string2Object(U.toJson(jsonData), HDDTGetInvViewNoPayOutput.class);
			// String xmlStr
			// ="<Data><Item><index>11</index><cusCode>HPG-LD/00734746</cusCode><invToken>01GTKT0/001;AA/21E;21</invToken><name>Hóa
			// đơn giá trị gia tăng</name><publishDate>11/4/2021 3:16:13
			// PM</publishDate><signStatus>0</signStatus><pattern>01GTKT0/001</pattern><serial>AA/21E</serial><invNum>0000021</invNum><amount>2119350</amount><status>1</status><cusname><![CDATA[Doanh
			// NghiÖp T­ Nh©n Th­¬ng M¹i Lª B¶o
			// Ch©u]]></cusname><payment>0</payment><fkey>200001GTKT0001AA21E21</fkey><type>0</type><kindOfService></kindOfService></Item><Item><index>12</index><cusCode>HPG-LD/00734746</cusCode><invToken>01GTKT0/001;AA/21E;21</invToken><name>Hóa
			// đơn giá trị gia tăng</name><publishDate>11/4/2021 3:16:13
			// PM</publishDate><signStatus>0</signStatus><pattern>01GTKT0/001</pattern><serial>AA/21E</serial><invNum>0000021</invNum><amount>2119350</amount><status>1</status><cusname><![CDATA[Doanh
			// NghiÖp T­ Nh©n Th­¬ng M¹i Lª B¶o
			// Ch©u]]></cusname><payment>0</payment><fkey>200001GTKT0001AA21E21</fkey><type>0</type><kindOfService></kindOfService></Item></Data>";
			if (curResponse.getGetInvViewNoPayResult().toUpperCase().startsWith("ERR")) {

				Integer ErrorCode = Integer.parseInt(curResponse.getGetInvViewNoPayResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;

				case 2:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 11:
					modelResponse.setMessage("Hóa đơn chưa thanh toán nên chưa xem được");
					break;

				default:
					modelResponse.setMessage(ConstantString.MESSAGE_KHONG_XACDINH_LOI_HDDT);
					break;
				}
			}

			if (!curResponse.getGetInvViewNoPayResult().toUpperCase().startsWith("ERR")) {
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getGetInvViewNoPayResult());

				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject getInvViewFkeyNoPay(HDDTGetInvViewFkeyNoPayInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/getInvViewFkeyNoPay");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<getInvViewFkeyNoPay xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<fkey>" + input.getFkey() + "</cusCode>");

		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</getInvViewFkeyNoPay>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.getInvViewFkeyNoPayResponse");
			jsonData.remove("-xmlns");


			HDDTGetInvViewFkeyNoPayModel curResponse = (HDDTGetInvViewFkeyNoPayModel) Converter.string2Object(U.toJson(jsonData), HDDTGetInvViewFkeyNoPayModel.class);
			if (curResponse.getGetInvViewFkeyNoPayResult().toUpperCase().startsWith("ERR")) {

				Integer ErrorCode = Integer.parseInt(curResponse.getGetInvViewFkeyNoPayResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;

				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;
				case 7:
					modelResponse.setMessage(" Công ty không tồn tại");
					break;

				default:
					modelResponse.setMessage(ConstantString.HDDTFAIL);
					break;
				}
			}

			if (!curResponse.getGetInvViewFkeyNoPayResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				Object value = null;
				try {
					value = xmlMapper.readValue(curResponse.getGetInvViewFkeyNoPayResult(), Object.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject downloadInv(HDDTDownloadInvInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInv");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInv xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<invToken>" + input.getInvToken() + "</invToken>");

		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</downloadInv>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvResponse");
			jsonData.remove("-xmlns");


			HDDTDownloadInvOutput curResponse = (HDDTDownloadInvOutput) Converter.string2Object(U.toJson(jsonData), HDDTDownloadInvOutput.class);
			if (curResponse.getDownloadInvResult().toUpperCase().startsWith("ERR")) {

				Integer ErrorCode = Integer.parseInt(curResponse.getDownloadInvResult().replace("ERR:", ""));

				switch (ErrorCode) {
				case 1:
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
					break;

				case 6:
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
					break;

				default:
					modelResponse.setMessage(ConstantString.HDDTFAIL);
					break;
				}
			}

			if (!curResponse.getDownloadInvResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				Object value = null;
				try {
					value = xmlMapper.readValue(curResponse.getDownloadInvResult(), Object.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject downloadInvPDF(HDDTDownloadInvPDFInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvPDF");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInvPDF xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<token>" + input.getToken() + "</token>");

		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</downloadInvPDF>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvPDFResponse");
			jsonData.remove("-xmlns");


			HDDTDownloadInvPDFOutput curResponse = (HDDTDownloadInvPDFOutput) Converter.string2Object(U.toJson(jsonData), HDDTDownloadInvPDFOutput.class);
			if (curResponse.getDownloadInvPDFResult().toUpperCase().startsWith("ERR")) {
				if (curResponse.getDownloadInvPDFResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getDownloadInvPDFResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
				}

			}

			if (!curResponse.getDownloadInvPDFResult().toUpperCase().startsWith("ERR")) {

				XmlMapper xmlMapper = new XmlMapper();
				Object value = null;
				try {
					value = xmlMapper.readValue(curResponse.getDownloadInvPDFResult(), Object.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject convertForVerify(HDDTConvertForVerifyInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/convertForVerify");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<convertForVerify xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<invToken>" + input.getInvToken() + "</token>");

		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</convertForVerify>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.convertForVerifyResponse");
			jsonData.remove("-xmlns");


			HDDTConvertForVerifyOutput curResponse = (HDDTConvertForVerifyOutput) Converter.string2Object(U.toJson(jsonData), HDDTConvertForVerifyOutput.class);
			if (curResponse.getConvertForVerifyResult().toUpperCase().startsWith("ERR")) {

				if (curResponse.getConvertForVerifyResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getConvertForVerifyResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
				} else if (curResponse.getConvertForVerifyResult().toUpperCase().startsWith(ConstantString.ERR8)) {
					modelResponse.setMessage("Hóa đơn đã được chuyển đổi rồi");
				}

			}

			if (!curResponse.getConvertForVerifyResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				Object value = null;
				try {
					value = xmlMapper.readValue(curResponse.getConvertForVerifyResult(), Object.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid.toString());
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject convertForStore(HDDTConvertForVerifyInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/convertForStore");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<convertForStore xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<invToken>" + input.getInvToken() + "</token>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</convertForStore>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.convertForStoreResponse");
			jsonData.remove("-xmlns");


			HDDTConvertForStoreOutput curResponse = (HDDTConvertForStoreOutput) Converter.string2Object(U.toJson(jsonData), HDDTConvertForStoreOutput.class);
			if (curResponse.getConvertForStoreResult().startsWith("ERR")) {

				if (curResponse.getConvertForStoreResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getConvertForStoreResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
				}

			}

			if (!curResponse.getConvertForStoreResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				Object value = null;
				try {
					value = xmlMapper.readValue(curResponse.getConvertForStoreResult(), Object.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid);
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject convertForStoreFkey(HDDTConvertForStoreFkeyInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/convertForStoreFkey");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<convertForStoreFkey xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<fkey>" + input.getFkey() + "</fkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("</convertForStoreFkey>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.convertForStoreFkeyResponse");
			jsonData.remove("-xmlns");


			HDDTConvertForStoreFkeyOutput curResponse = (HDDTConvertForStoreFkeyOutput) Converter.string2Object(U.toJson(jsonData), HDDTConvertForStoreFkeyOutput.class);
			if (curResponse.getConvertForStoreFkeyResult().startsWith("ERR")) {

				if (curResponse.getConvertForStoreFkeyResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getConvertForStoreFkeyResult().toUpperCase().startsWith(ConstantString.ERR2)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC);
				}

			}

			if (!curResponse.getConvertForStoreFkeyResult().toUpperCase().startsWith("ERR")) {
				String value;
				try {
					value = curResponse.getConvertForStoreFkeyResult();
				} catch (Exception e) {
					value = null;
					// TODO: handle exception
				}
				// XmlMapper xmlMapper = new XmlMapper();
				// Object value = null;
				// try {
				// value = xmlMapper.readValue(curResponse.getConvertForStoreFkeyResult(),
				// Object.class);
				// } catch (JsonMappingException e) {
				//
				// e.printStackTrace();
				// } catch (JsonProcessingException e) {
				//
				// e.printStackTrace();
				// }
				if (value == null) {
					modelResponse = Utils.notFound(uuid);
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject downloadInvFkeyNoPay(HDDTConvertForStoreFkeyInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvFkeyNoPay");
		StringBuffer xmlBody = new StringBuffer();
		//xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append(XMLDEVELOP);
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInvFkeyNoPay xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("<fkey>" + input.getFkey() + "</fkey>");
		xmlBody.append("</downloadInvFkeyNoPay>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvFkeyNoPayResponse");
			jsonData.remove("-xmlns");


			HDDTDownloadInvFkeyNoPayOutput curResponse = (HDDTDownloadInvFkeyNoPayOutput) Converter.string2Object(U.toJson(jsonData), HDDTDownloadInvFkeyNoPayOutput.class);
			if (curResponse.getDownloadInvFkeyNoPayResult().startsWith("ERR")) {

				if (curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage(ConstantString.MESSAGE_FKEY_KHONG_CHINHXAC);
				} else if (curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
				}

			}

			if (!curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith("ERR")) {
				XmlMapper xmlMapper = new XmlMapper();
				Object value = null;
				try {
					value = xmlMapper.readValue(curResponse.getDownloadInvFkeyNoPayResult(), Object.class);
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				if (value == null) {
					modelResponse = Utils.notFound(uuid);
				} else {
					modelResponse.setError("200");
					modelResponse.setError_code(ErrorCode.BSS_00000000);
					modelResponse.setData(value);
					modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
					modelResponse.setRequest_id(uuid);
				}
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	// lynqm gọi tạm thời hàm downloadInvFkeyNoPay của HP và trả về xml
	public static ResponseObject downloadInvFkeyNoPayXml(HDDTConvertForStoreFkeyInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvFkeyNoPay");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInvFkeyNoPay xmlns=\"http://tempuri.org/\">");

		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");
		xmlBody.append("<fkey>" + input.getFkey() + "</fkey>");
		xmlBody.append("</downloadInvFkeyNoPay>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvFkeyNoPayResponse");
			jsonData.remove("-xmlns");


			HDDTDownloadInvFkeyNoPayOutput curResponse = (HDDTDownloadInvFkeyNoPayOutput) Converter.string2Object(U.toJson(jsonData), HDDTDownloadInvFkeyNoPayOutput.class);
			if (curResponse.getDownloadInvFkeyNoPayResult().startsWith("ERR")) {

				if (curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage(ConstantString.MESSAGE_FKEY_KHONG_CHINHXAC);
				} else if (curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
				}

			}

			if (!curResponse.getDownloadInvFkeyNoPayResult().toUpperCase().startsWith("ERR")) {
				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getDownloadInvFkeyNoPayResult());

				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);
			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	public static ResponseObject downloadInvFkeyNoPayXmlHCM(HDDTConvertForStoreFkeyInput input,
															ThongtinServiceHDDTModel ttcnn, String uuid, int xmlCheck) {
		//ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = buildSoapRequest(input, ttcnn);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			HDDTDownloadInvFkeyNoPayHCMOutput curResponse = parseSoapResponse(response.getBody());

			if (curResponse == null || curResponse.getDownloadInvFkeyActionResult() == null) {
				return Utils.notFound(uuid);
			}

			String result = curResponse.getDownloadInvFkeyActionResult();
			if (result.toUpperCase().startsWith("ERR")) {
				return buildErrorResponse(result, uuid);
			}

			return buildSuccessResponse(result, uuid, xmlCheck);

		} catch (HttpStatusCodeException e) {
			ResponseObject errorResponse = Utils.badRequest(uuid);
			errorResponse.setMessage(ConstantString.HDDTFAIL);
			return errorResponse;
		}
	}

	private static HttpEntity<String> buildSoapRequest(HDDTConvertForStoreFkeyInput input, ThongtinServiceHDDTModel ttcnn) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvFkeyAction");

		StringBuilder xmlBody = new StringBuilder();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ")
				.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ")
				.append("xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">")
				.append("<soap:Body>")
				.append("<downloadInvFkeyAction xmlns=\"http://tempuri.org/\">")
				.append("<fkey>").append(input.getFkey()).append("</fkey>")
				.append("<userName>").append(ttcnn.getUser()).append("</userName>")
				.append("<userPass>").append(ttcnn.getPasswword()).append("</userPass>")
				.append("</downloadInvFkeyAction>")
				.append("</soap:Body>")
				.append("</soap:Envelope>");

		return new HttpEntity<>(xmlBody.toString(), headers);
	}

	private static HDDTDownloadInvFkeyNoPayHCMOutput parseSoapResponse(String responseBody) {
		Map<String, Object> jsonData = U.get(
				U.fromXmlWithoutNamespaces(responseBody),
				"Envelope.Body.downloadInvFkeyActionResponse"
		);
		if (jsonData != null) {
			jsonData.remove("-xmlns");
			return Converter.string2Object(U.toJson(jsonData), HDDTDownloadInvFkeyNoPayHCMOutput.class);
		}
		return null;
	}

	private static ResponseObject buildErrorResponse(String result, String uuid) {
		ResponseObject response = new ResponseObject();
		response.setError("400");
		response.setError_code(ErrorCode.BSS_00004008);
		response.setRequest_id(uuid);

		if (result.toUpperCase().startsWith(ConstantString.ERR1)) {
			response.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
		} else if (result.toUpperCase().startsWith(ConstantString.ERR6)) {
			response.setMessage(ConstantString.MESSAGE_FKEY_KHONG_CHINHXAC);
		} else if (result.toUpperCase().startsWith(ConstantString.ERR7)) {
			response.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
		}

		return response;
	}

	private static ResponseObject buildSuccessResponse(String result, String uuid, int xmlCheck) {
		ResponseObject response = new ResponseObject();
		response.setError("200");
		response.setError_code(ErrorCode.BSS_00000000);
		response.setRequest_id(uuid);
		response.setMessage(ConstantString.MESSAGE_THANHCONG);

		if (xmlCheck == 1) {
			Map<String, String> value = new HashMap<>();
			value.put(ConstantString.LOG_RESPONE, result);
			response.setData(value);
		} else {
			try {
				XmlMapper xmlMapper = new XmlMapper();
				Object value = xmlMapper.readValue(result, Object.class);
				response.setData(value);
			} catch (JsonProcessingException e) {
				return Utils.notFound(uuid);
			}
		}
		return response;
	}

	public static ResponseObject downloadInvPDFFkeyNoPay(HDDTConvertForStoreFkeyInput input, ThongtinServiceHDDTModel ttcnn, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		String url = ttcnn.getUrl() + ttcnn.getPortalservice();
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.set(ConstantString.CONTENT_TYPE, ConstantString.CONTENT_TYPE_VALUE);
		headers.set("SOAPAction", "http://tempuri.org/downloadInvPDFFkeyNoPay");
		StringBuffer xmlBody = new StringBuffer();
		xmlBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		xmlBody.append("<soap:Body>");
		xmlBody.append("<downloadInvPDFFkeyNoPay xmlns=\"http://tempuri.org/\">");
		xmlBody.append("<fkey>" + input.getFkey() + "</fkey>");
		xmlBody.append("<userName>" + ttcnn.getUser() + "</userName>");
		xmlBody.append("<userPass>" + ttcnn.getPasswword() + "</userPass>");

		xmlBody.append("</downloadInvPDFFkeyNoPay>");

		xmlBody.append("</soap:Body>");
		xmlBody.append("</soap:Envelope>");

		HttpEntity<String> request = new HttpEntity<>(xmlBody.toString(), headers);
		// make an HTTP GET request with headers
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class, 1);
			Map<String, Object> jsonData = U.<Map<String, Object>>get(U.<Map<String, Object>>fromXmlWithoutNamespaces(response.getBody()), "Envelope.Body.downloadInvPDFFkeyNoPayResponse");
			jsonData.remove("-xmlns");


			HDDTDownloadInvPDFFkeyNoPayOutput curResponse = (HDDTDownloadInvPDFFkeyNoPayOutput) Converter.string2Object(U.toJson(jsonData), HDDTDownloadInvPDFFkeyNoPayOutput.class);
			if (curResponse.getDownloadInvPDFFkeyNoPayResult().startsWith("ERR")) {
				if (curResponse.getDownloadInvPDFFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR1)) {
					modelResponse.setMessage(ConstantString.MESSAGE_TK_DANGNHAP_SAI);
				} else if (curResponse.getDownloadInvPDFFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR6)) {
					modelResponse.setMessage(ConstantString.MESSAGE_FKEY_KHONG_CHINHXAC);
				} else if (curResponse.getDownloadInvPDFFkeyNoPayResult().toUpperCase().startsWith(ConstantString.ERR7)) {
					modelResponse.setMessage(ConstantString.MESSAGE_CTY_KHONG_TONTAI);
				}
			}

			if (!curResponse.getDownloadInvPDFFkeyNoPayResult().toUpperCase().startsWith("ERR")) {

				Map<String, String> value = new HashMap<>();
				value.put(ConstantString.LOG_RESPONE, curResponse.getDownloadInvPDFFkeyNoPayResult());
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				modelResponse.setData(value);
				modelResponse.setMessage(ConstantString.MESSAGE_THANHCONG);
				modelResponse.setRequest_id(uuid);

			} else {
				modelResponse.setError("400");
				modelResponse.setError_code(ErrorCode.BSS_00004008);
				modelResponse.setRequest_id(uuid);
			}
			return modelResponse;
		} catch (HttpStatusCodeException e) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(ConstantString.HDDTFAIL);
			return modelResponse;
		}
	}

	@SuppressWarnings("unchecked")
	private static List<Map<String, Object>> convertData2(Object data) {
		if (data == null) {
			return new ArrayList<>();
		}
		if (data instanceof List) {
			return (List<Map<String, Object>>) data;
		}
		if (data instanceof Map) {
			List<Map<String, Object>> resultList = new ArrayList<>();
			resultList.add((Map<String, Object>) data);
			return resultList;
		}
		return new ArrayList<>();
	}
	// public static <T extends List<?>> T cast(Object obj) {
	// return (T) obj;
	// }
}
