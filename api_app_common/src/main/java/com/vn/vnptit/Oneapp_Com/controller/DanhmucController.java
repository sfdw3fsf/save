package com.vn.vnptit.Oneapp_Com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.common.NumberToWord;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuCustomer;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuProduct;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuThongTin;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.DanhMucParam;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.DauSoCDParam;
import com.vn.vnptit.Oneapp_Com.model.DonHangChiTietModel;
import com.vn.vnptit.Oneapp_Com.model.DonViQuanLyThanhToanParam;
import com.vn.vnptit.Oneapp_Com.model.DsCTV_input;
import com.vn.vnptit.Oneapp_Com.model.KhuVucParam;
import com.vn.vnptit.Oneapp_Com.model.LoaiNhaParam;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.TramThiCongParam;
import com.vn.vnptit.Oneapp_Com.model.TuyenThuParam;
import com.vn.vnptit.Oneapp_Com.model.VeTinhParam;
import com.vn.vnptit.Oneapp_Com.service.DanhMucService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author danhd
 *
 */
@RestController
@Configuration
@RequestMapping("danhmuc")
public class DanhmucController {

	@Autowired
	DataSourceProperties	dataSourceProperties;

	@Autowired
	DanhMucService			danhmucService;

	
	// lynqm 04/03/2021 hàm lấy tất cả danh mục
	@Operation(summary = "Danh mục all dùng chung")
	@PostMapping("/get_danhmuc")
	public ResponseEntity<Object> getDanhMuc(HttpServletRequest request,
			@RequestBody DanhMucParam dm) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.getDanhMuc(dm, token, uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());					
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());				
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 30/03/2021 lấy ds tuyến thu
	@Operation(summary = "Lấy danh sách tuyến thu")
	@PostMapping("/ds_tuyenthu")
	public ResponseEntity<Object> dsTuyenThu(HttpServletRequest request,
			@RequestBody TuyenThuParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsTuyenThu(tt, token, uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	
	@Operation(summary = "DS cộng tác viên")
	@PostMapping("/getDSCongtacvien")
	public ResponseEntity<Object> getDSCongtacvien(HttpServletRequest request,
			@RequestBody DsCTV_input data) {

		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;
		UUID uuid = UUID.randomUUID();
		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.getDsCongtacvien(data, token,
						uuid.toString());
			}
			modelResponse = Utils.badRequestAppSecret(uuid.toString());			
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	
	
	@Operation(summary = "DS nhân viên khảo sát")
	@PostMapping("/DSNhanVienKS")
	public ResponseEntity<Object> dsNhanVienKS(HttpServletRequest request,
			@RequestBody DsCTV_input data) {

		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;
		UUID uuid = UUID.randomUUID();
		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsNhanVienKS(data, token,
						uuid.toString());
			}
			modelResponse = Utils.badRequestAppSecret(uuid.toString());			
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}



	// lynqm 08/04/2021 lấy ds tuyến thu
	@Operation(summary = "Lấy danh sách khu vực thuê bao")
	@PostMapping("/ds_khuvuc_thuebao")
	public ResponseEntity<Object> dsKhuVuc(HttpServletRequest request,
			@RequestBody KhuVucParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsKhuVuc(tt, token, uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 08/04/2021 lấy ds đầu số cố định
	@Operation(summary = "Lấy danh sách đầu số cố định")
	@PostMapping("/ds_dauso_codinh")
	public ResponseEntity<Object> dsDauSoCD(HttpServletRequest request,
			@RequestBody DauSoCDParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsDauSoCD(tt, token, uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	
	@Operation(summary = "Danh mục mặc định màn hình vina trả sau")
	@PostMapping("/dm_macdinh_manhinh_vina")
	public ResponseEntity<Object> danhmucMacdinhVinaTrasau(HttpServletRequest request,
			@RequestBody DonHangChiTietModel tt) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.danhmucMacdinhVinaTrasau(tt, token, uuid.toString(), appSecret);
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 08/04/2021 lấy ds vệ tinh
	@Operation(summary = "Lấy danh sách vệ tinh")
	@PostMapping("/ds_vetinh")
	public ResponseEntity<Object> dsVeTinh(HttpServletRequest request,
			@RequestBody VeTinhParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsVeTinh(tt, token, uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 09/04/2021 lấy ds trạm thi công
	@Operation(summary = "Lấy danh sách trạm thi công")
	@PostMapping("/ds_tram_thicong")
	public ResponseEntity<Object> dsTramThiCong(HttpServletRequest request,
			@RequestBody TramThiCongParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsTramThiCong(tt, token,
						uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 09/04/2021 lấy ds tổ thi công
	@Operation(summary = "Lấy danh sách tổ thi công")
	@PostMapping("/ds_to_thicong")
	public ResponseEntity<Object> dsToThiCong(HttpServletRequest request,
			@RequestBody TramThiCongParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsToThiCong(tt, token, uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 09/04/2021 lấy ds đơn vị quản lý + tuyến thu của thanh toán
	@Operation(summary = "Lấy danh sách đơn vị quản lý và tuyến thu")
	@PostMapping("/ds_donvi_quanly_thanhtoan")
	public ResponseEntity<Object> dsDonViQuanLyThanhToan(
			HttpServletRequest request,
			@RequestBody DonViQuanLyThanhToanParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsDonViQuanLyThanhToan(tt, token,
						uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 23/04/2021 lấy ds đơn vị quản lý lắp đặt
	@Operation(summary = "Lấy danh sách đơn vị quản lý lắp đặt")
	@PostMapping("/ds_donvi_quanly_lapdat")
	public ResponseEntity<Object> dsDonViQuanLyLapDat(
			HttpServletRequest request,
			@RequestBody DonViQuanLyThanhToanParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsDonViQuanLyLapDat(tt, token,
						uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}
	
	@Operation(summary = "Lấy danh sách Loại nhà")
	@PostMapping("/ds_loai_nha")
	public ResponseEntity<Object> dsLoaiNha(
			HttpServletRequest request,
			@RequestBody LoaiNhaParam tt) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.dsLoaiNha(tt, token,
						uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());				
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());			
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	@Operation(summary = "Danh mục mặc định màn hình vina trả sau dùng cho đơn hàng sim kit")
	@PostMapping("/dm_macdinh_manhinh_vina_v2")
	public ResponseEntity<Object> danhmucMacdinhVinaTrasauV2(HttpServletRequest request,
														   @RequestBody DonHangChiTietModel tt) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader(ConstantString.AUTHORIZATION);
		String appSecret = request.getHeader(ConstantString.APPSECRET);
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return danhmucService.danhmucMacdinhVinaTrasauV2(tt, token, uuid.toString(), appSecret);
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}
	
}
