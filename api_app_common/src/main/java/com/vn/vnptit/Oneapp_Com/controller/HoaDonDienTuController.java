package com.vn.vnptit.Oneapp_Com.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConfirmPaymentInputModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForStoreFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForVerifyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDeliverInvFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvPDFInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyNoPayInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewNoPayInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetSerialByPatternInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTcancelInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuAdjustInvActionInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuCustomer;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuInvNoPayModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuThongTin;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTureReplaceInvoiceActionInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTureplaceInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.UnConfirmPaymentFkeyInputModel;
import com.vn.vnptit.Oneapp_Com.service.HoaDonDienTuSerrvice;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author DanhNQ
 *
 */
@RestController
@Configuration
@RequestMapping("hoadon_dientu")
public class HoaDonDienTuController {
	@Autowired
	DataSourceProperties	dataSourceProperties;

	@Autowired
	HoaDonDienTuSerrvice	hoaDonDienTuSerrvice;

	public HoaDonDienTuController(DataSourceProperties dataSourceProperties,
			HoaDonDienTuSerrvice hoaDonDienTuSerrvice) {
		super();
		this.dataSourceProperties = dataSourceProperties;
		this.hoaDonDienTuSerrvice = hoaDonDienTuSerrvice;
	}

	@Operation(summary = "Hóa đơn điện tử - updateCus")
	@PostMapping("/updateCus")
	public ResponseEntity<Object> updateCus(HttpServletRequest request,
			@RequestBody HoaDonDienTuCustomer hoaDonDienTuCustomer) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.UpdateCus(hoaDonDienTuCustomer);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử - updateCus")
	@PostMapping("/updateCusV2")
	public ResponseEntity<Object> updateCusV2(HttpServletRequest request,
											@RequestBody HoaDonDienTuCustomer hoaDonDienTuCustomer) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.updateCusV2(hoaDonDienTuCustomer, token, uuid.toString());

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

//	@Operation(summary = "Hóa đơn điện tử - downloadInvNoPay")
//	@PostMapping("/downloadInvNoPay")
//	public ResponseEntity<Object> downloadInvNoPay(HttpServletRequest request,
//			@RequestBody HoaDonDienTuInvNoPayModel invNoPayModel) throws JsonProcessingException {
//		UUID uuid = UUID.randomUUID();
//		String token = request.getHeader(ConstantString.AUTHORIZATION);
//
//		ResponseObject modelResponse;
//
//		if (JwtService.validateAuthToken(token)) {
//
//			return hoaDonDienTuSerrvice.downloadInvNoPay(invNoPayModel);
//
//		} else {
//			modelResponse = Utils.accessDenied(uuid.toString());
//		}
//		return ResponseEntity
//				.status(Integer.parseInt(modelResponse.getError()))
//				.body(modelResponse);
//
//	}
	@PostMapping("/downloadInvNoPay1")
	public ResponseEntity<Object> downloadInvNoPay1(HttpServletRequest request,
												   @RequestBody HoaDonDienTuInvNoPayModel invNoPayModel) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.downloadInvNoPay1(invNoPayModel);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -importAndPublishInv")
	@PostMapping("/importAndPublishInv")
	public ResponseEntity<Object> importAndPublishInv(
			HttpServletRequest request,
			@RequestBody HoaDonDienTuThongTin hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice
					.importAndPublishInv(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@PostMapping("/importAndPublishInvV2")
	public ResponseEntity<Object> importAndPublishInvV2(
			HttpServletRequest request,
			@RequestBody HoaDonDienTuThongTin hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice
					.importAndPublishInvV2(hoaDonDienTuThongTin, token, uuid.toString());

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -confirmPaymentFkey ")
	@PostMapping("/confirmPaymentFkey")
	public ResponseEntity<Object> confirmPaymentFkey(
			HttpServletRequest request,
			@RequestBody HoaDonDienTuThongTin hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice
					.confirmPaymentFkey(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -confirmPaymentFkey ")
	@PostMapping("/confirmPaymentFkeyV2")
	public ResponseEntity<Object> confirmPaymentFkeyV2(
			HttpServletRequest request,
			@RequestBody HoaDonDienTuThongTin hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice
					.confirmPaymentFkeyV2(hoaDonDienTuThongTin, token, uuid.toString());

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -adjustInvAction ")
	@PostMapping("/adjustInvAction")
	public ResponseEntity<Object> adjustInvAction(HttpServletRequest request,
			@RequestBody HoaDonDienTuAdjustInvActionInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.adjustInvAction(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -replaceInv ")
	@PostMapping("/replaceInv")
	public ResponseEntity<Object> replaceInv(HttpServletRequest request,
			@RequestBody HoaDonDienTureplaceInvInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.replaceInv(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@PostMapping("/ReplaceInvoiceActionMoi")
	public ResponseEntity<Object> replaceInvoiceActionMoi(HttpServletRequest request,
													   @RequestBody HoaDonDienTureReplaceInvoiceActionInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.replaceInvoiceActionMoi(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -ReplaceInvoiceAction ")
  @PostMapping("/ReplaceInvoiceAction")
  public ResponseEntity<Object> replaceInvoiceAction(HttpServletRequest request,
      @RequestBody HoaDonDienTureReplaceInvoiceActionInput hoaDonDienTuThongTin) {
    UUID uuid = UUID.randomUUID();
    String token = request.getHeader(ConstantString.AUTHORIZATION);

    ResponseObject modelResponse;

    if (JwtService.validateAuthToken(token)) {

      return hoaDonDienTuSerrvice.replaceInvoiceAction(hoaDonDienTuThongTin);

    } else {
      modelResponse = Utils.accessDenied(uuid.toString());
    }
    return ResponseEntity
        .status(Integer.parseInt(modelResponse.getError()))
        .body(modelResponse);

  }

	@Operation(summary = "Hóa đơn điện tử -listInvByCus ")
	@PostMapping("/listInvByCus")
	public ResponseEntity<Object> listInvByCus(HttpServletRequest request,
			@RequestBody HDDTListInvByCusModel hoaDonDienTuThongTin) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.listInvByCus(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -getInvViewFkey ")
	@PostMapping("/getInvViewFkey")
	public ResponseEntity<Object> getInvViewFkey(HttpServletRequest request,
			@RequestBody HDDTGetInvViewFkeyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.getInvViewFkey(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	@Operation(summary = "Hóa đơn điện tử -confirmPayment ")
	@PostMapping("/confirmPayment")
	public ResponseEntity<Object> confirmPayment(HttpServletRequest request,
			@RequestBody HDDTConfirmPaymentInputModel hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.confirmPayment(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -unConfirmPayment ")
	@PostMapping("/unConfirmPayment")
	public ResponseEntity<Object> unConfirmPayment(HttpServletRequest request,
			@RequestBody HDDTConfirmPaymentInputModel hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice
					.unConfirmPayment(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -UnconfirmPaymentFkey ")
	@PostMapping("/UnconfirmPaymentFkey")
	public ResponseEntity<Object> unConfirmPaymentFkey(
			HttpServletRequest request,
			@RequestBody UnConfirmPaymentFkeyInputModel hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice
					.unConfirmPaymentFkey(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -confirmPaymentDetailFkey ")
	@PostMapping("/confirmPaymentDetailFkey")
	public ResponseEntity<Object> confirmPaymentDetailFkey(
			HttpServletRequest request,
			@RequestBody UnConfirmPaymentFkeyInputModel hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice
					.confirmPaymentDetailFkey(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -cancelInv ")
	@PostMapping("/cancelInv")
	public ResponseEntity<Object> cancelInv(HttpServletRequest request,
			@RequestBody HDDTcancelInvInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return hoaDonDienTuSerrvice.cancelInv(hoaDonDienTuThongTin);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -getSerialByPattern ")
	@PostMapping("/getSerialByPattern")
	public ResponseEntity<Object> getSerialByPattern(
			HttpServletRequest request,
			@RequestBody HDDTGetSerialByPatternInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice
					.getSerialByPattern(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -deliverInvFkey ")
	@PostMapping("/deliverInvFkey")
	public ResponseEntity<Object> deliverInvFkey(HttpServletRequest request,
			@RequestBody HDDTDeliverInvFkeyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.deliverInvFkey(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -listInvByCusFkey ")
	@PostMapping("/listInvByCusFkey")
	public ResponseEntity<Object> listInvByCusFkey(HttpServletRequest request,
			@RequestBody HDDTListInvByCusFkeyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice
					.listInvByCusFkey(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -getInvViewNoPay ")
	@PostMapping("/getInvViewNoPay")
	public ResponseEntity<Object> getInvViewNoPay(HttpServletRequest request,
			@RequestBody HDDTGetInvViewNoPayInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.getInvViewNoPay(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -getInvViewFkeyNoPay ")
	@PostMapping("/getInvViewFkeyNoPay")
	public ResponseEntity<Object> getInvViewFkeyNoPay(
			HttpServletRequest request,
			@RequestBody HDDTGetInvViewFkeyNoPayInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice
					.getInvViewFkeyNoPay(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -downloadInv ")
	@PostMapping("/downloadInv")
	public ResponseEntity<Object> downloadInv(HttpServletRequest request,
			@RequestBody HDDTDownloadInvInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.downloadInv(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	@Operation(summary = "Hóa đơn điện tử -downloadInvPDF ")
	@PostMapping("/downloadInvPDF")
	public ResponseEntity<Object> downloadInvPDF(HttpServletRequest request,
			@RequestBody HDDTDownloadInvPDFInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.downloadInvPDF(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	@Operation(summary = "Hóa đơn điện tử -convertForVerify ")
	@PostMapping("/convertForVerify")
	public ResponseEntity<Object> convertForVerify(HttpServletRequest request,
			@RequestBody HDDTConvertForVerifyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.convertForVerify(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	@Operation(summary = "Hóa đơn điện tử -convertForStore ")
	@PostMapping("/convertForStore")
	public ResponseEntity<Object> convertForStore(HttpServletRequest request,
			@RequestBody HDDTConvertForVerifyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.convertForStore(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	@Operation(summary = "Hóa đơn điện tử -convertForStoreFkey ")
	@PostMapping("/convertForStoreFkey")
	public ResponseEntity<Object> convertForStoreFkey(HttpServletRequest request,
			@RequestBody HDDTConvertForStoreFkeyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.convertForStoreFkey(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	@Operation(summary = "Hóa đơn điện tử -downloadInvFkeyNoPay ")
	@PostMapping("/downloadInvFkeyNoPay")
	public ResponseEntity<Object> downloadInvFkeyNoPay(HttpServletRequest request,
			@RequestBody HDDTConvertForStoreFkeyInput hoaDonDienTuThongTin) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.downloadInvFkeyNoPay(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -downloadInvFkeyNoPayXml")
	@PostMapping("/downloadInvFkeyNoPayXml")
	public ResponseEntity<Object> downloadInvFkeyNoPayXml(HttpServletRequest request,
			@RequestBody HDDTConvertForStoreFkeyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.downloadInvFkeyNoPayXml(hoaDonDienTuThongTin);
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	
//	@Operation(summary = "Hóa đơn điện tử -downloadInvPDFFkeyNoPay ")
//	@PostMapping("/downloadInvPDFFkeyNoPay")
//	public ResponseEntity<Object> downloadInvPDFFkeyNoPay(HttpServletRequest request,
//			@RequestBody HDDTConvertForStoreFkeyInput hoaDonDienTuThongTin) {
//		UUID uuid = UUID.randomUUID();
//		String token = request.getHeader(ConstantString.AUTHORIZATION);
//		ResponseObject modelResponse;
//		if (JwtService.validateAuthToken(token)) {
//			return hoaDonDienTuSerrvice.downloadInvPDFFkeyNoPay(hoaDonDienTuThongTin);
//		} else {
//			modelResponse = Utils.accessDenied(uuid.toString());
//		}
//		return ResponseEntity
//				.status(Integer.parseInt(modelResponse.getError()))
//				.body(modelResponse);
//
//	}

	@Operation(summary = "Hóa đơn điện tử -downloadInvPDFFkeyNoPay ")
	@PostMapping("/downloadInvPDFFkeyNoPay")
	public ResponseEntity<Object> downloadInvPDFFkeyNoPayV2(HttpServletRequest request,
														  @RequestBody HDDTConvertForStoreFkeyInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;
		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.downloadInvPDFFkeyNoPayV2(hoaDonDienTuThongTin, token, uuid.toString());
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	@PostMapping("/downloadInvNoPay")
	public ResponseEntity<Object> downloadInvNoPayV2(HttpServletRequest request,
													@RequestBody HoaDonDienTuInvNoPayModel invNoPayModel) throws IOException {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.downloadInvNoPayV2(invNoPayModel, token, uuid.toString()) ;
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	@Operation(summary = "Hóa đơn điện tử -adjustInvAction ")
	@PostMapping("/adjustInvAction_v2")
	public ResponseEntity<Object> adjustInvActionV2(HttpServletRequest request,
												  @RequestBody HoaDonDienTuAdjustInvActionInput hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			return hoaDonDienTuSerrvice.adjustInvActionV2(hoaDonDienTuThongTin, token, uuid.toString());
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	
}
