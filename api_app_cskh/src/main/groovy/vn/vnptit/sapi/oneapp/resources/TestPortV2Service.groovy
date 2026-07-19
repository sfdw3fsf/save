package vn.vnptit.sapi.oneapp.resources

import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonBuilder
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import io.swagger.model.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.input.DoKiemInput
import vn.vnptit.sapi.oneapp.model.output.LayPortTheoTBIDOutput
import vn.vnptit.sapi.oneapp.services.BNMServicev
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.DataUtilsv

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("TestPortV2")
class TestPortV2Service {
	@Autowired
	LoggingManaged loggingManaged

	@Autowired
	OracleManaged oracleManaged

	@Autowired
	HttpServletRequest request

	@Autowired
	GenericApi genericApi

	@Autowired
	BNMServicev bnmService

	@Autowired
	DataUtilsv dataUtils

	@PostMapping(value = "/input_dokiem")
	ResponseEntity<BaseResponse<String>> input_dokiem(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody DoKiemInput requestBody) {
		UUID uuid = UUID.randomUUID()
		String requestId = uuid.toString()
		try {
			def ret = [:]
			Map<String, Object> session = genericApi.getSessionData(request)
			long phanvung_id = ((requestBody.phanvung_id != null && requestBody.phanvung_id != 0) ? requestBody.phanvung_id : session.get(GenericApi.ID_TINHTHANH)?.toString()?.toLong())
			String ma_tinh = session.get(GenericApi.MA_TINH)
			dataUtils.init(phanvung_id)
			Object resObj

			GroovyRowResult dtuser
			String sqlString = "SELECT * FROM css.tinh where tentat = ?"
			Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
				dtuser = sql.firstRow(sqlString, [ma_tinh?.toUpperCase()])
			}
			String vuser_alt = "", vpass_alt = "", vuser_xtest = "", vpass_xtest = ""
			if (dtuser != null) {
				vuser_alt = dtuser?.getProperty("user_alt")?.toString()
				vpass_alt = dtuser?.getProperty("pass_alt")?.toString()
				vuser_xtest = dtuser?.getProperty("user_xtest")?.toString()
				vpass_xtest = dtuser?.getProperty("pass_xtest")?.toString()
			}

			if (vuser_alt == "" || vuser_alt == "-1") {
				vuser_alt = ""
			}
			if (vpass_alt == "" || vpass_alt == "-1") {
				vpass_alt = ""
			}
			if (vuser_xtest == "" || vuser_xtest == "-1") {
				vuser_xtest = ""
			}
			if (vpass_xtest == "" || vpass_xtest == "-1") {
				vpass_xtest = ""
			}
			if (requestBody.show_server_dk == 1) {
				iServer_DK = requestBody.server_id
			} else {
				GroovyRowResult iServer_DK_rowResult
				Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
					iServer_DK_rowResult = sql.firstRow("select macdinh from admin.thamso_md where ma_ts = 'SERVER_DK' and phanvung_id = ?", [phanvung_id])
				}
				if (iServer_DK_rowResult == null) {
					iServer_DK = -1
				}
				else {
					iServer_DK = iServer_DK_rowResult.get("macdinh")?.toString().toInteger()
				}
			}
			if (iServer_DK == 1) {
				if (vuser_alt == "" || vuser_alt == "-1" || vpass_alt == "" || vpass_alt == "-1") {
					return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin user kết nối", HttpStatus.INTERNAL_SERVER_ERROR))
				}
			} else {
				if (vpass_xtest == "" || vuser_xtest == "-1" || vuser_xtest == "" || vuser_xtest == "-1") {
					return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin user kết nối", HttpStatus.INTERNAL_SERVER_ERROR))
				}
			}
			List<LayPortTheoTBIDOutput> dtPort
			if (requestBody?.vthuebao_id > 0) {
				dtPort = dataUtils.LAY_PORT_THEO_TBID(-1, requestBody?.vthuebao_id)
			} else if (requestBody.vhdtb_id > 0) {
				dtPort = dataUtils.LAY_PORT_THEO_TBID(requestBody?.vhdtb_id, -1)
			} else {
				dtPort = dataUtils.LAY_PORT_THEOMATB_V2(phanvung_id, requestBody?.vma_tb, 2)
			}
			if (dtPort != null && dtPort?.size() > 0) {
				vip = dtPort?.get(0)?.ip
				vport_adsl = dtPort?.get(0)?.port_adsl
				vport_visa = dtPort?.get(0)?.port_visa
				vonu_id = dtPort?.get(0)?.onu_id
				vInput = vip + ":" + vport_visa
				vloaitbi_id = dtPort?.get(0)?.loaitbi_id
				thamso = dtPort?.get(0)?.thamso
				vIP_xtest = dtPort?.get(0)?.portinfo
				port_info_9ts = ""
				if (dtPort?.get(0)?.port_9ts != null && !dtPort?.get(0)?.port_9ts?.isEmpty() && ma_tinh?.trim()?.toUpperCase() == "HCM") {
					port_info_9ts = dtPort?.get(0)?.port_9ts
				}
				vdoitac_id = "" 
				if (dtPort?.get(0)?.doitac_id != null && !dtPort?.get(0)?.doitac_id?.isEmpty()) {
					vdoitac_id = dtPort?.get(0)?.doitac_id
				}
				if (vonu_id != "") {
					vInput += "/" + vonu_id
				}
				if (iServer_DK == 3 && dtPort?.get(0)?.loai_gpon != null && !dtPort?.get(0)?.loai_gpon?.isEmpty()) {
					vloai_gpon = dtPort?.get(0)?.loai_gpon
					vport_bnm = dtPort?.get(0)?.port_bnm
					vInput = vip + ":" + vport_bnm + ":" + vloai_gpon + ":" + vloaitbi_id + ":" + vdoitac_id
					IP_bras = dtPort?.get(0)?.ip_bras
					vslid = dtPort?.get(0)?.vslid
				}
				if (vip == null || vip?.isEmpty()) {
					return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thuê bao chưa được cấp port", HttpStatus.INTERNAL_SERVER_ERROR))
				}
			} else {
				return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thuê bao chưa được cấp port", HttpStatus.INTERNAL_SERVER_ERROR))
			}
			if (vInput == null || vInput?.isEmpty()) {
				return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có account hoặc port để thực hiện", HttpStatus.INTERNAL_SERVER_ERROR))
			}

			switch (requestBody?.vkieu) {
				case 0:
					if (ma_tinh?.trim()?.toUpperCase() == "HCM") {
						vInput += "'" + port_info_9ts
						vIP_xtest += "'" + port_info_9ts
					}
					String _thamso_ma_tb = ""
					if (ma_tinh?.trim()?.toUpperCase() == "BDG") {
						_thamso_ma_tb = "'" + requestBody.vma_tb
					}

					ret.linetestUser = (iServer_DK == 1 ? vuser_alt : vuser_xtest) + "'" + iServer_DK + "'" + vloaitbi_id + "'" + thamso + _thamso_ma_tb
					ret.linetestPassword = iServer_DK == 1 ? vpass_alt : vpass_xtest
					ret.input = "1,3".contains(iServer_DK.toString()) ? vInput : vIP_xtest
					ret.mac = requestBody.isGetMac ? "1" : "0"
					break
				default:
					break
			}
			return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
		} catch (Exception ex) {
			return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
		}
	}
		

	@PostMapping(value = "/dokiem")
	@Produces([
		MediaType.APPLICATION_JSON,
		MediaType.TEXT_PLAIN
	])
	@Operation(summary = "Đo kiểm, UR2.8.018", description = "dev: quannv", tags = ["UR2.8.018"])
	ResponseEntity<BaseResponse<String>> lay_ds_thuoctinh_bang(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody DoKiemInput requestBody) {
		UUID uuid = UUID.randomUUID()
		String requestId = uuid.toString()
		try {
//			loggingManaged.info(new ImmutableMap.Builder<String, Object>()
//					.put("request_id", requestId)
//					.put("action", "[TestPortV2Service] - [dokiem] - Request")
//					.put("requestBody", new JsonBuilder(requestBody))
//					.build())
			Map<String, Object> session = genericApi.getSessionData(request)
			// String dataSrc = session.get(GenericApi.CSS_SCH) 
			long phanvung_id = ((requestBody.phanvung_id != null && requestBody.phanvung_id != 0) ? requestBody.phanvung_id : session.get(GenericApi.ID_TINHTHANH)?.toString()?.toLong())
			String ma_tinh = (requestBody.ma_tinh && requestBody.ma_tinh != null && requestBody.ma_tinh != "" ? requestBody.ma_tinh : session.get(GenericApi.MA_TINH))
			String nguoi_cn = session.get(GenericApi.USERNAME)
			String may_cn = session.get(GenericApi.DEVICE_ID) != null ? session.get(GenericApi.DEVICE_ID) : "VNPT Employee"
			String token = request.getHeader("Authorization")
			bnmService.init(token)
			dataUtils.init(phanvung_id)
			String response = ""
			Object resObj

			// Lấy thông tin server đo kiểm
			GroovyRowResult dtuser
			//String sqlString = "SELECT * FROM css.tinh where tentat = '${ma_tinh?.toUpperCase()}'"
			String sqlString = "SELECT * FROM css.tinh where tentat = ?"
			Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
				dtuser = sql.firstRow(sqlString, [ma_tinh?.toUpperCase()])
			}
			String vuser_alt = "", vpass_alt = "", vuser_xtest = "", vpass_xtest = ""
			Float att_gpon = 0
			if (dtuser != null) {
				vuser_alt = dtuser?.getProperty("user_alt")?.toString()
				vpass_alt = dtuser?.getProperty("pass_alt")?.toString()
				vuser_xtest = dtuser?.getProperty("user_xtest")?.toString()
				vpass_xtest = dtuser?.getProperty("pass_xtest")?.toString()
				att_gpon = dtuser?.getProperty("ATT_GPON")?.toString()?.toFloat()
			}

			if (vuser_alt == "" || vuser_alt == "-1") {
				vuser_alt = ""
			}
			if (vpass_alt == "" || vpass_alt == "-1") {
				vpass_alt = ""
			}
			if (vuser_xtest == "" || vuser_xtest == "-1") {
				vuser_xtest = ""
			}
			if (vpass_xtest == "" || vpass_xtest == "-1") {
				vpass_xtest = ""
			}
			if (requestBody.show_server_dk == 1) {
				iServer_DK = requestBody.server_id
			} else {
				//iServer_DK = dataUtils.MAP_ID("macdinh", "admin.thamso_md", "where ma_ts = 'SERVER_DK' and phanvung_id = $phanvung_id")?.toInteger()
				GroovyRowResult iServer_DK_rowResult
				Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
					iServer_DK_rowResult = sql.firstRow("select macdinh from admin.thamso_md where ma_ts = 'SERVER_DK' and phanvung_id = ?", [phanvung_id])
				}
				if (iServer_DK_rowResult == null) {
					iServer_DK = -1
				}
				else {
					iServer_DK = iServer_DK_rowResult.get("macdinh")?.toString().toInteger()
				}
			}
			if (iServer_DK == 1) {
				if (vuser_alt == "" || vuser_alt == "-1" || vpass_alt == "" || vpass_alt == "-1") {
					return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin user kết nối", HttpStatus.INTERNAL_SERVER_ERROR))
				}
			} else {
				if (vpass_xtest == "" || vuser_xtest == "-1" || vuser_xtest == "" || vuser_xtest == "-1") {
					return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin user kết nối", HttpStatus.INTERNAL_SERVER_ERROR))
				}
			}
			List<LayPortTheoTBIDOutput> dtPort
			if (requestBody?.vthuebao_id > 0) {
				dtPort = dataUtils.LAY_PORT_THEO_TBID(-1, requestBody?.vthuebao_id)
//				loggingManaged.info(new ImmutableMap.Builder<String, Object>()
//						.put("request_id", requestId)
//						.put("action", "[TestPortV2Service] - [dokiem] - Request")
//						.put("func", "LAY_PORT_THEO_TBID").put("phanvung_id", phanvung_id).put("vthuebao_id", requestBody?.vthuebao_id)
//						.put("dtPort", new JsonBuilder(dtPort))
//						.build())
			} else if (requestBody.vhdtb_id > 0) {
				dtPort = dataUtils.LAY_PORT_THEO_TBID(requestBody?.vhdtb_id, -1)
//				loggingManaged.info(new ImmutableMap.Builder<String, Object>()
//						.put("request_id", requestId)
//						.put("action", "[TestPortV2Service] - [dokiem] - Request")
//						.put("func", "LAY_PORT_THEO_TBID").put("phanvung_id", phanvung_id).put("vhdtb_id", requestBody?.vhdtb_id)
//						.put("dtPort", new JsonBuilder(dtPort))
//						.build())
			} else {
				dtPort = dataUtils.LAY_PORT_THEOMATB_V2(phanvung_id, requestBody?.vma_tb, 2)
//				loggingManaged.info(new ImmutableMap.Builder<String, Object>()
//						.put("request_id", requestId)
//						.put("action", "[TestPortV2Service] - [dokiem] - Request")
//						.put("func", "LAY_PORT_THEOMATB").put("phanvung_id", phanvung_id).put("vma_tb", requestBody?.vma_tb)
//						.put("dtPort", new JsonBuilder(dtPort))
//						.build())
			}
			String vInput = ""
			String vip = ""
			String vport_adsl = ""
			String vport_visa = ""
			String vonu_id = ""
			String vloaitbi_id = ""
			String thamso = ""
			String vIP_xtest = ""
			String port_info_9ts = ""
			String vdoitac_id = ""
			String vloai_gpon = ""
			String vport_bnm = ""
			String vslid = ""
			String IP_bras = ""
			if (dtPort != null && dtPort?.size() > 0) {
				vip = dtPort?.get(0)?.ip
				vport_adsl = dtPort?.get(0)?.port_adsl
				vport_visa = dtPort?.get(0)?.port_visa
				vonu_id = dtPort?.get(0)?.onu_id
				vInput = vip + ":" + vport_visa
				vloaitbi_id = dtPort?.get(0)?.loaitbi_id
				thamso = dtPort?.get(0)?.thamso
				vIP_xtest = dtPort?.get(0)?.portinfo
				if (dtPort?.get(0)?.port_9ts != null && !dtPort?.get(0)?.port_9ts?.isEmpty() && ma_tinh?.trim()?.toUpperCase() == "HCM") {
					port_info_9ts = dtPort?.get(0)?.port_9ts
				}
				if (dtPort?.get(0)?.doitac_id != null && !dtPort?.get(0)?.doitac_id?.isEmpty()) {
					vdoitac_id = dtPort?.get(0)?.doitac_id
				}
				if (vonu_id != "") {
					vInput += "/" + vonu_id
				}

				IP_bras = dtPort?.get(0)?.ip_bras
				if (iServer_DK == 3 && dtPort?.get(0)?.loai_gpon != null && !dtPort?.get(0)?.loai_gpon?.isEmpty()) {
					vloai_gpon = dtPort?.get(0)?.loai_gpon
					vport_bnm = dtPort?.get(0)?.port_bnm
					vInput = vip + ":" + vport_bnm + ":" + vloai_gpon + ":" + vloaitbi_id + ":" + vdoitac_id
					IP_bras = dtPort?.get(0)?.ip_bras
					vslid = dtPort?.get(0)?.vslid
				}
				if (vip == null || vip?.isEmpty()) {
					return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thuê bao chưa được cấp port!", HttpStatus.INTERNAL_SERVER_ERROR))
				}
			} else {
				return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thuê bao chưa được cấp port", HttpStatus.INTERNAL_SERVER_ERROR))
			}
			if (vInput == null || vInput?.isEmpty()) {
				return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có account hoặc port để thực hiện", HttpStatus.INTERNAL_SERVER_ERROR))
			}

			//int _SET_PASS_ONT_USING_MA_TB = dataUtils.MAP_ID("macdinh", "admin.thamso_md", "where ma_ts = 'SET_PASS_ONT_USING_MA_TB' and phanvung_id = $phanvung_id")?.toInteger()
			int _SET_PASS_ONT_USING_MA_TB
			GroovyRowResult rowResult
			Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
				rowResult = sql.firstRow("select macdinh from admin.thamso_md where ma_ts = 'SET_PASS_ONT_USING_MA_TB' and phanvung_id = ?", [phanvung_id])
			}
			if (rowResult == null) {
				_SET_PASS_ONT_USING_MA_TB = -1
			}
			else {
				_SET_PASS_ONT_USING_MA_TB = rowResult.get("macdinh")?.toString().toInteger()
			}

			switch (requestBody?.vkieu) {
				case 0:
					dataUtils.capnhat_log_action_bnm("TestPort", requestBody.vma_tb, vInput, "Log from PTTB-PC", nguoi_cn, may_cn)
					if (ma_tinh?.trim()?.toUpperCase() == "HCM") {
						vInput += "'" + port_info_9ts
						vIP_xtest += "'" + port_info_9ts
					}
					String _thamso_ma_tb = ""
					if (ma_tinh?.trim()?.toUpperCase() == "BDG") {
						_thamso_ma_tb = "'" + requestBody.vma_tb
					}

					resObj = bnmService.testPortV2((iServer_DK == 1 ? vuser_alt : vuser_xtest) + "'" + iServer_DK + "'" + vloaitbi_id + "'" + thamso + _thamso_ma_tb,
							iServer_DK == 1 ? vpass_alt : vpass_xtest, "1,3".contains(iServer_DK.toString()) ? vInput : vIP_xtest, requestBody.isGetMac ? "1" : "0")
//					loggingManaged.info(new ImmutableMap.Builder<String, Object>()
//							.put("request_id", requestId)
//							.put("action", "[TestPortV2Service] - [dokiem] - Request")
//							.put("resObj", new JsonBuilder(resObj))
//							.build())
					if (resObj != null && resObj["errorCode"] == 0) {
						String kq = "";
						if (att_gpon > 0 && resObj["data"]["CHATLUONG"] != null) {
							float chatluong
							try {
								chatluong = resObj["data"]["CHATLUONG"]?.toString()?.toFloat()
							} catch(Exception e) {
								chatluong = 0
							}
							if (chatluong > att_gpon) {
								kq = "\n" + "Vượt ngưỡng"
							}
						}
						response = resObj["data"]["KETQUA"] + kq
					} else {
						response = resObj["faultString"]
					}
					break
				case 1:
					dataUtils.capnhat_log_action_bnm("Tool_ResetOnePort", requestBody.vma_tb, vInput, "Log from PTTB-PC", nguoi_cn, may_cn)
					resObj = bnmService.toolResetOnePort((iServer_DK == 1 ? vuser_alt : vuser_xtest) + "'" + iServer_DK + "'" + vloaitbi_id + "'" + thamso, iServer_DK == 1 ? vpass_alt : vpass_xtest, vInput)
					if (resObj != null && resObj["errorCode"] == 0) {
						response = resObj["data"]
					} else {
						response = "Reset port thất bại: " + resObj["faultString"]
					}
					break
				case 2:
					dataUtils.capnhat_log_action_bnm("Tool_SetOnePortProfile", requestBody.vma_tb, vInput, "Log from PTTB-PC", nguoi_cn, may_cn)
					resObj = bnmService.toolSetOnePortProfile(vuser_alt, vpass_alt, vInput, requestBody?.vProfile)
					if (resObj != null && resObj["errorCode"] == 0) {
						response = "Reset port thành công!"
					} else {
						response = "Reset port thất bại: " + resObj["faultString"]
					}
					break
				case 3:
					dataUtils.capnhat_log_action_bnm("CheckAccount_Online", requestBody.vma_tb + "|" + IP_bras, vInput, "Log from PTTB-PC", nguoi_cn, may_cn)
					resObj = bnmService.checkAccountOnline(IP_bras, requestBody.vma_tb)
					if (resObj != null && resObj["errorCode"] == 0) {
						response = resObj["data"]?.toString()
					} else {
						response = resObj["faultString"]
					}
					break
				case 4:
					dataUtils.capnhat_log_action_bnm("ResetPassOnt", requestBody.vma_tb, vInput, "Log from PTTB-PC", nguoi_cn, may_cn)
					if (_SET_PASS_ONT_USING_MA_TB == 1) {
						String _thamso_matb = ""
						if (ma_tinh?.trim()?.toUpperCase() == "BDG") {
							_thamso_matb = "'" + requestBody.vma_tb
						}
						if (vIP_xtest == null || vIP_xtest?.isEmpty()) {
							GetPortInfo(requestBody.vma_tb, ma_tinh)
						}
						String _paras = "${("1,3".contains(iServer_DK.toString()) ? vInput : vIP_xtest)}|${((iServer_DK == 1 ? vuser_alt : vuser_xtest) + "'" + iServer_DK + "'" + vloaitbi_id + "'" + thamso + _thamso_matb)}|${(iServer_DK == 1 ? vpass_alt : vpass_xtest)}"
						resObj = bnmService.resetPassOnt(requestBody?.user_ont, requestBody?.pass_ont, _paras)
						if (resObj != null && resObj["errorCode"] == 0) {
							response = resObj["data"]?.toString()
						} else {
							response = resObj["faultString"]
						}
					} else {
						resObj = bnmService.resetPassOnt(requestBody?.user_ont, requestBody?.pass_ont, vInput)
						if (resObj != null && resObj["errorCode"] == 0) {
							response = resObj["data"]?.toString()
						} else {
							response = resObj["faultString"]
						}
					}
					break
				case 5:
					dataUtils.capnhat_log_action_bnm("Reset_Pass_Ont_Igate040H", requestBody.vma_tb, vInput, "Log from PTTB-PC", nguoi_cn, may_cn)
					if (_SET_PASS_ONT_USING_MA_TB == 1) {
						String _thamso_matb = ""
						if (ma_tinh?.trim()?.toUpperCase() == "BDG") {
							_thamso_matb = "'" + requestBody.vma_tb
						}
						if (vIP_xtest == null || vIP_xtest?.isEmpty()) {
							GetPortInfo(requestBody.vma_tb, ma_tinh)
						}
						String _paras = "${("1,3".contains(iServer_DK?.toString()) ? vInput : vIP_xtest)}|${((iServer_DK == 1 ? vuser_alt : vuser_xtest) + "'" + iServer_DK + "'" + vloaitbi_id + "'" + thamso + _thamso_matb)}|${(iServer_DK == 1 ? vpass_alt : vpass_xtest)}"
						resObj = bnmService.resetPassOntIgate040H(requestBody?.user_ont, requestBody?.pass_ont, _paras)
						if (resObj != null && resObj["errorCode"] == 0) {
							response = resObj["data"]?.toString()
						} else {
							response = resObj["faultString"]
						}
					} else {
						resObj = bnmService.resetPassOntIgate040H(requestBody?.user_ont, requestBody?.pass_ont, vInput)
						if (resObj != null && resObj["errorCode"] == 0) {
							response = resObj["data"]?.toString()
						} else {
							response = resObj["faultString"]
						}
					}
					break
			}

//			loggingManaged.info(new ImmutableMap.Builder<String, Object>()
//					.put("request_id", requestId)
//					.put("action", "[TestPortV2Service] - [dokiem] - Response")
//					.put("result", response != null ? response : "")
//					.build())
			return ResponseEntity.ok().body(BaseResponseBuilder.of(response, requestId))
		} catch (Exception ex) {
//			loggingManaged.info(new ImmutableMap.Builder<String, Object>()
//					.put("request_id", requestId)
//					.put("action", "[TestPortV2Service] - [dokiem] - Error")
//					.put("result", ex.message)
//					.build())
			return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
		}
	}
	int iServer_DK
	String vInput, vIP_xtest
	String vip = "", vport_adsl = "", vport_visa = "", vonu_id = "", vloaitbi_id = "", thamso = "", port_info_9ts = "", vdoitac_id = "",
	vloai_gpon = "", vport_bnm = "", IP_bras = "", vslid = ""
	private void GetPortInfo(String vma_tb, String ma_tinh) {
		List<LayPortTheoTBIDOutput> dtPort
		dtPort = dataUtils.LAY_PORT_THEOMATB(vma_tb, 2)
		if (dtPort != null && dtPort?.size() > 0) {
			vip = dtPort?.get(0)?.ip
			vport_adsl = dtPort?.get(0)?.port_adsl
			vport_visa = dtPort?.get(0)?.port_visa
			vonu_id = dtPort?.get(0)?.onu_id
			vInput = vip + ":" + vport_visa
			vloaitbi_id = dtPort?.get(0)?.loaitbi_id
			thamso = dtPort?.get(0)?.thamso
			vIP_xtest = dtPort?.get(0)?.portinfo
			if (dtPort?.get(0)?.port_9ts != null && !dtPort?.get(0)?.port_9ts?.isEmpty() && ma_tinh?.trim()?.toUpperCase() == "HCM") {
				port_info_9ts = dtPort?.get(0)?.port_9ts
			}
			if (dtPort?.get(0)?.doitac_id != null && !dtPort?.get(0)?.doitac_id?.isEmpty()) {
				vdoitac_id = dtPort?.get(0)?.doitac_id
			}
			if (vonu_id != "") {
				vInput += "/" + vonu_id
			}
			if (iServer_DK == 3 && dtPort?.get(0)?.loai_gpon != null && !dtPort?.get(0)?.loai_gpon?.isEmpty()) {
				vloai_gpon = dtPort?.get(0)?.loai_gpon
				vport_bnm = dtPort?.get(0)?.port_bnm
				vInput = vip + ":" + vport_bnm + ":" + vloai_gpon + ":" + vloaitbi_id + ":" + vdoitac_id
				IP_bras = dtPort?.get(0)?.ip_bras
				vslid = dtPort?.get(0)?.vslid
			}
		}
	}
}
