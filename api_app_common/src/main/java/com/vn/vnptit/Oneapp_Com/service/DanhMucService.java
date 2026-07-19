
package com.vn.vnptit.Oneapp_Com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vn.vnptit.Oneapp_Com.common.*;
import com.vn.vnptit.Oneapp_Com.managed.RedisManaged;
import com.vn.vnptit.Oneapp_Com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.util.StringUtils;

/**
 * @author dongnv
 */
@Service
public class DanhMucService {

	@Autowired
	DataSourceProperties dataSourceProperties;

	@Autowired
	private RedisManaged redisManaged;
	

	/**
	 * Find danh muc by id and nguondulieu
	 *
	 * @return list danh muc
	 */
	public ResponseEntity<Object> getDanhMuc(DanhMucParam dm, String token,
			String uuid) {
		ResponseObject modelResponse = new ResponseObject();

		// Redis Cache Logic
		String cacheKey = getDanhMucCacheKey(dm, token);
		Object cachedData = redisManaged.getFromCache("get_danhmuc", cacheKey);
		if (cachedData != null) {
			modelResponse.setData(cachedData);
			modelResponse.setRequest_id(uuid);
			modelResponse.setMessage("Success (from cache)");
			return ResponseEntity.ok().body(modelResponse);
		}

		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_get_danhmuc";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_danhmucs", ConstantString.VARCHAR2 ,
				dm.getId_danhmucs(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_nguondulieu", ConstantString.VARCHAR2,
				dm.getNguon_dulieu(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_param_customize", ConstantString.VARCHAR2,
				dm.getParam_customize(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				false);
				        
		modelResponse.setData(result.getData());

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {
				// Save to cache
				redisManaged.saveToCache("get_danhmuc", cacheKey, result.getData(), 5);

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	private String getDanhMucCacheKey(DanhMucParam dm, String token) {
		String phanVung = "UNKNOWN";
		try {
			if (token != null && token.contains(" ")) {
				phanVung = JwtService.getPhanVungFromToken(token.split(" ")[1]);
			}
		} catch (Exception e) {
			// fallback to UNKNOWN
		}
		return "|" + phanVung + "|" + (dm.getId_danhmucs() != null ? dm.getId_danhmucs() : "") 
				+ "|" + (dm.getParam_customize() != null ? dm.getParam_customize() : "");
	}

	public ResponseEntity<Object> getDsCongtacvien(
			DsCTV_input pageInfoModel, String token, String uuid) {

		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.get_ds_nhan_vien_ctv";
		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_loai_nv", ConstantString.NUMBER,
				"" + pageInfoModel.getP_id_loai_nv(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_donvi_ql", ConstantString.NUMBER,
				"" + pageInfoModel.getP_id_donvi_ql(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_tieu_chi", ConstantString.NUMBER,
				"" + pageInfoModel.getP_id_tieu_chi(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_noi_dung", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_noi_dung(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_page_num", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_page_num(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_page_rec", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_page_rec(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_type", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_type(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rsout", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		parameters.add(new OracleParam("p_page_info", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));

		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
				
		if (result.getError() != null) {
			Utils utils = new Utils();
			modelResponse = utils.classifyError(result, uuid);
		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);
			} else {
				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				modelResponse.setPage_info(result.getPage_info());
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	public ResponseEntity<Object> dsNhanVienKS(DsCTV_input pageInfoModel,
			String token, String uuid) {

		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.get_ds_nhan_vien_khaosat";
		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_loai_nv", ConstantString.NUMBER,
				"" + pageInfoModel.getP_id_loai_nv(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_donvi_ql", ConstantString.NUMBER,
				"" + pageInfoModel.getP_id_donvi_ql(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_tieu_chi", ConstantString.NUMBER,
				"" + pageInfoModel.getP_id_tieu_chi(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_noi_dung", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_noi_dung(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_page_num", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_page_num(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_page_rec", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_page_rec(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_type", ConstantString.VARCHAR2,
				"" + pageInfoModel.getP_type(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rsout", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		parameters.add(new OracleParam("p_page_info", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));

		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
				
		if (result.getError() != null) {
			Utils utils = new Utils();
			modelResponse = utils.classifyError(result, uuid);
		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);
			} else {
				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				modelResponse.setPage_info(result.getPage_info());
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);

	}

	// lynqm 30/03/2021 lấy ds tuyến thu
	public ResponseEntity<Object> dsTuyenThu(TuyenThuParam tt, String token,
			String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_ds_tuyenthu";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHUONG_ID, ConstantString.NUMBER,
				tt.getP_phuong_id() == null ? null
						: tt.getP_phuong_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHO_ID, ConstantString.NUMBER,
				tt.getP_pho_id() == null ? null : tt.getP_pho_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.AP_ID, ConstantString.NUMBER,
				tt.getP_ap_id() == null ? null : tt.getP_ap_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.KHU_ID, ConstantString.NUMBER,
				tt.getP_khu_id() == null ? null : tt.getP_khu_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_dacdiem_id", ConstantString.NUMBER,
				tt.getP_dacdiem_id() == null ? null
						: tt.getP_dacdiem_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_kieu_id", ConstantString.NUMBER,
				tt.getP_kieu_id() == null ? null
						: tt.getP_kieu_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
				
		modelResponse.setData(result.getData());

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	// lynqm 08/04/2021 lấy ds khu vực
	public ResponseEntity<Object> dsKhuVuc(KhuVucParam kv, String token,
			String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_thuebao_khuvuc";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_quanid", ConstantString.NUMBER,
				kv.getP_quan_id() == null ? null
						: kv.getP_quan_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_phuong", ConstantString.NUMBER,
				kv.getP_phuong_id() == null ? null
						: kv.getP_phuong_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_khuid", ConstantString.NUMBER,
				kv.getP_khu_id() == null ? null : kv.getP_khu_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_apid", ConstantString.NUMBER,
				kv.getP_ap_id() == null ? null : kv.getP_ap_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_phoid", ConstantString.NUMBER,
				kv.getP_pho_id() == null ? null : kv.getP_pho_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				false);
		
		modelResponse.setData(result.getData());

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	// lynqm 08/04/2021 lấy ds đầu số cố định
	public ResponseEntity<Object> dsDauSoCD(DauSoCDParam pr, String token,
			String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_ds_dauso_cd";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_quan_id", ConstantString.NUMBER,
				pr.getP_quan_id() == null ? null
						: pr.getP_quan_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHUONG_ID, ConstantString.NUMBER,
				pr.getP_phuong_id() == null ? null
						: pr.getP_phuong_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_loaitb_id", ConstantString.NUMBER,
				pr.getP_loaitb_id() == null ? null
						: pr.getP_loaitb_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_dichvuvt_id", ConstantString.NUMBER,
				pr.getP_dichvuvt_id() == null ? null
						: pr.getP_dichvuvt_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_nhomso_id", ConstantString.NUMBER,
				pr.getP_nhomso_id() == null ? null
						: pr.getP_nhomso_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_vetinh_id", ConstantString.NUMBER,
				pr.getP_vetinh_id() == null ? null
						: pr.getP_vetinh_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				false);
		
		modelResponse.setData(result.getData());

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	//mặc định màn hình
	public ResponseEntity<Object> danhmucMacdinhVinaTrasau(
			DonHangChiTietModel pr, String token, String uuid,
			String appsecret) throws JsonProcessingException {

		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_manhinh_macdinh_vina_ts";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_donhang_chiet", ConstantString.NUMBER,
				pr.getP_id_donhang_ctiet() == null ? null
						: pr.getP_id_donhang_ctiet().toString(),
				OracleParam.OracleDirect.INPUT));

		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);

		List<Object> dataUser = result.getData();
		Object loaiKH = null;

		for (Object item : dataUser) {
			@SuppressWarnings("unchecked")
			Map<String, Object> data = (Map<String, Object>) item;

			if (data.get("name").equals("loai_kh")) {
				loaiKH =   data.get("id");
				break;
			}
		}
		ResponseObject response;
//		if (loaiKH != null) {
//			// Lấy loại thuê bao
//			String url = "http://ccbs/executor/app_ds_loai_doituong";
////			String url = "https://api-onebss.vnpt.vn/ccbs/executor/app_ds_loai_doituong";
//			Map<String, String> header = new HashMap<>();
//			header.put("Content-Type", "application/json");
//			header.put("App-secret", appsecret);
//			header.put("Authorization", token);
//
//			Map<String, String> body = new HashMap<>();
//			body.put("loaikh_id", loaiKH.toString());
//			response = CallHttp.httpPostBody(header, body, url);
//
//			Map<String, Object> ccbs = (Map<String, Object>) response
//					.getData();
//			List<Object> ccbs1 = (List<Object>) ccbs.get("data");
//			Object item = ccbs1.get(0);
//			Map<String, Object> doituong = (Map<String, Object>) item;
//
//			Map<String, Object> doituongTB = new HashMap<>();
//			doituongTB.put("id_danhmuc", 1107);
//			doituongTB.put("id", doituong.get("DOITUONG_ID").toString());
//			doituongTB.put("name", doituong.get("TEN_DT").toString());
//			dataUser.add(doituongTB);
//
//		}
		if (loaiKH != null) {
			// Lấy loại thuê bao
			String url = "http://ccbs/executor/app_ds_loai_doituong";
//			String url = "https://api-dev-onebss.vnpt.vn/ccbs/executor/app_ds_loai_doituong";
//			String url = "https://api-onebss.vnpt.vn/ccbs/executor/app_ds_loai_doituong";

			Map<String, String> header = new HashMap<>();
			header.put("App-secret", appsecret);
			header.put("Authorization", token);
			Map<String, String> body = new HashMap<>();
			body.put("loaikh_id", loaiKH.toString());
			response = CallHttp.httpPostBody_v2(header, body, url);

			if (StringUtils.isEmpty(response.getError())) {
				Object dataString = response.getData();
				XmlMapper xmlMapper = new XmlMapper();
				BaseResponse responseDT = xmlMapper.readValue(dataString.toString(), BaseResponse.class);

				if (responseDT.getData() != null && responseDT.getData().size() > 0) {
					Map<String, Object> doituongTB = new HashMap<>();
					doituongTB.put("id_danhmuc", 1107);
					doituongTB.put("id", responseDT.getData().get(0).getDoituongId());
					doituongTB.put("name", responseDT.getData().get(0).getTenDt());
					dataUser.add(doituongTB);
				}
			}
		}

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(dataUser);
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	// lynqm 08/04/2021 lấy ds vệ tinh
	public ResponseEntity<Object> dsVeTinh(VeTinhParam pr, String token,
			String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_dm_vetinh";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_quan_id", ConstantString.NUMBER,
				pr.getP_quan_id() == null ? null
						: pr.getP_quan_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHUONG_ID, ConstantString.NUMBER,
				pr.getP_phuong_id() == null ? null
						: pr.getP_phuong_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHO_ID, ConstantString.NUMBER,
				pr.getP_pho_id() == null ? null : pr.getP_pho_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.AP_ID, ConstantString.NUMBER,
				pr.getP_ap_id() == null ? null : pr.getP_ap_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.KHU_ID, ConstantString.NUMBER,
				pr.getP_khu_id() == null ? null : pr.getP_khu_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_loaitb_id", ConstantString.NUMBER,
				pr.getP_loaitb_id() == null ? null
						: pr.getP_loaitb_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_loaikhoso_id", ConstantString.NUMBER,
				pr.getP_loaikhoso_id() == null ? null
						: pr.getP_loaikhoso_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				false);
		modelResponse.setData(result.getData());
		
		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	// lynqm 09/04/2021 lấy ds trạm thi công
	public ResponseEntity<Object> dsTramThiCong(TramThiCongParam pr,
			String token, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_ds_tram_thicong";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_phuongld_id", ConstantString.NUMBER,
				pr.getP_phuongld_id() == null ? null
						: pr.getP_phuongld_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_khuld_id", ConstantString.NUMBER,
				pr.getP_khuld_id() == null ? null
						: pr.getP_khuld_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_apld_id", ConstantString.NUMBER,
				pr.getP_apld_id() == null ? null
						: pr.getP_apld_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_phold_id", ConstantString.NUMBER,
				pr.getP_phold_id() == null ? null
						: pr.getP_phold_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
		modelResponse.setData(result.getData());		

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	// lynqm 09/04/2021 lấy ds tổ thi công
	public ResponseEntity<Object> dsToThiCong(TramThiCongParam pr,
			String token, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_ds_to_thicong";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_phuongld_id", ConstantString.NUMBER,
				pr.getP_phuongld_id() == null ? null
						: pr.getP_phuongld_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_khuld_id", ConstantString.NUMBER,
				pr.getP_khuld_id() == null ? null
						: pr.getP_khuld_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_apld_id", ConstantString.NUMBER,
				pr.getP_apld_id() == null ? null
						: pr.getP_apld_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_phold_id", ConstantString.NUMBER,
				pr.getP_phold_id() == null ? null
						: pr.getP_phold_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
		modelResponse.setData(result.getData());
		
		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	// lynqm 09/04/2021 lấy ds đơn vị quản lý + tuyến thu của thanh toán
	public ResponseEntity<Object> dsDonViQuanLyThanhToan(
			DonViQuanLyThanhToanParam pr, String token, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_donvi_quanly_thanhtoan";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHUONG_ID, ConstantString.NUMBER,
				pr.getP_phuong_id() == null ? null
						: pr.getP_phuong_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.KHU_ID, ConstantString.NUMBER,
				pr.getP_khu_id() == null ? null : pr.getP_khu_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.AP_ID, ConstantString.NUMBER,
				pr.getP_ap_id() == null ? null : pr.getP_ap_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHO_ID, ConstantString.NUMBER,
				pr.getP_pho_id() == null ? null : pr.getP_pho_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_loaikh_id", ConstantString.NUMBER,
				pr.getP_loaikh_id() == null ? null
						: pr.getP_loaikh_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
		modelResponse.setData(result.getData());
		
		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	// lynqm 23/04/2021 lấy ds đơn vị quản lý lắp đặt
	public ResponseEntity<Object> dsDonViQuanLyLapDat(
			DonViQuanLyThanhToanParam pr, String token, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_donvi_quanly_lapdat";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHUONG_ID, ConstantString.NUMBER,
				pr.getP_phuong_id() == null ? null
						: pr.getP_phuong_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.KHU_ID, ConstantString.NUMBER,
				pr.getP_khu_id() == null ? null : pr.getP_khu_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.AP_ID, ConstantString.NUMBER,
				pr.getP_ap_id() == null ? null : pr.getP_ap_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam(ConstantString.PHO_ID, ConstantString.NUMBER,
				pr.getP_pho_id() == null ? null : pr.getP_pho_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
		modelResponse.setData(result.getData());
		

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}	
	
	
	// Lấy danh sách Loại nhà
	public ResponseEntity<Object> dsLoaiNha(
			LoaiNhaParam pr, String token, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_lay_ds_loai_nha";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_nhomln_id", ConstantString.NUMBER,
				pr.getP_nhomln_id() == null ? null
						: pr.getP_nhomln_id().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_so_tang", ConstantString.NUMBER,
				pr.getP_so_tang() == null ? null : pr.getP_so_tang().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_dien_tich", ConstantString.NUMBER,
				pr.getP_dien_tich() == null ? null : pr.getP_dien_tich().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_so_phong", ConstantString.NUMBER,
				pr.getP_so_phong() == null ? null : pr.getP_so_phong().toString(),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
		modelResponse.setData(result.getData());
		

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}

	//mặc định màn hình cho đơn hàng sim kit
	public ResponseEntity<Object> danhmucMacdinhVinaTrasauV2(
			DonHangChiTietModel pr, String token, String uuid,
			String appsecret) throws JsonProcessingException {

		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.danh_muc.sp_manhinh_macdinh_vina_ts_v2";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		parameters.add(new OracleParam(ConstantString.P_IDENTIFY_CODE, ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_donhang", ConstantString.NUMBER,
				pr.getP_id_donhang() == null ? null
						: pr.getP_id_donhang().toString(),
				OracleParam.OracleDirect.INPUT));

		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);

		List<Object> dataUser = result.getData();
		Object loaiKH = null;

		for (Object item : dataUser) {
			@SuppressWarnings("unchecked")
			Map<String, Object> data = (Map<String, Object>) item;

			if (data.get("name").equals("loai_kh")) {
				loaiKH =   data.get("id");
				break;
			}
		}
		ResponseObject response;
//		if (loaiKH != null) {
//			// Lấy loại thuê bao
//			String url = "http://ccbs/executor/app_ds_loai_doituong";
////			String url = "https://api-dev-onebss.vnpt.vn/ccbs/executor/app_ds_loai_doituong";
//			Map<String, String> header = new HashMap<>();
//			header.put("Content-Type", "application/json");
//			header.put("App-secret", appsecret);
//			header.put("Authorization", token);
//
//			Map<String, String> body = new HashMap<>();
//			body.put("loaikh_id", loaiKH.toString());
//			response = CallHttp.httpPostBody(header, body, url);
//
//			Map<String, Object> ccbs = (Map<String, Object>) response
//					.getData();
//			List<Object> ccbs1 = (List<Object>) ccbs.get("data");
//			Object item = ccbs1.get(0);
//			Map<String, Object> doituong = (Map<String, Object>) item;
//
//			Map<String, Object> doituongTB = new HashMap<>();
//			doituongTB.put("id_danhmuc", 1107);
//			doituongTB.put("id", doituong.get("DOITUONG_ID").toString());
//			doituongTB.put("name", doituong.get("TEN_DT").toString());
//			dataUser.add(doituongTB);
//
//		}
		if (loaiKH != null) {
			// Lấy loại thuê bao
			String url = "http://ccbs/executor/app_ds_loai_doituong";
//			String url = "https://api-dev-onebss.vnpt.vn/ccbs/executor/app_ds_loai_doituong";
			Map<String, String> header = new HashMap<>();
			header.put("App-secret", appsecret);
			header.put("Authorization", token);
			Map<String, String> body = new HashMap<>();
			body.put("loaikh_id", loaiKH.toString());
			response = CallHttp.httpPostBody_v2(header, body, url);

			if (StringUtils.isEmpty(response.getError())) {
				Object dataString = response.getData();
				XmlMapper xmlMapper = new XmlMapper();
				BaseResponse responseDT = xmlMapper.readValue(dataString.toString(), BaseResponse.class);

				if (responseDT.getData() != null && responseDT.getData().size() > 0) {
					Map<String, Object> doituongTB = new HashMap<>();
					doituongTB.put("id_danhmuc", 1107);
					doituongTB.put("id", responseDT.getData().get(0).getDoituongId());
					doituongTB.put("name", responseDT.getData().get(0).getTenDt());
					dataUser.add(doituongTB);
				}
			}
		}

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(dataUser);
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}
		
}
