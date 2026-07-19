package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.sql.OutParameter
import groovy.sql.Sql
import groovy.util.slurpersupport.GPathResult
import io.swagger.api.DVITCare2Api
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.GetDataFileITCareRequest
import vn.vnptit.sapi.oneapp.model.request.GuiFileKhachHangRequest
import vn.vnptit.sapi.oneapp.model.request.HuyGanHoSoITCareRequest
import vn.vnptit.sapi.oneapp.model.request.INSERT_HOSO_ITCARE_SMPRequest
import vn.vnptit.sapi.oneapp.model.request.KiemTraUploadFileHSRequest
import vn.vnptit.sapi.oneapp.model.request.LayDSFileHoSoITCareRequest
import vn.vnptit.sapi.oneapp.model.request.hinhthuc
import vn.vnptit.sapi.oneapp.model.response.LayThongTinBaoTriResponse
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource;


import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class DVITCare2Controller implements DVITCare2Api {

    private final HttpServletRequest request;
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private final ObjectMapper objectMapper;

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    GenericApi genericApi
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    HttpManaged httpManaged

    @Autowired
    DVITCare2Controller(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    ResponseEntity<BaseResponse> lay_ds_file_hs_itcare_smp(LayDSFileHoSoITCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DS_FILE_HS_ITCARE_SMP(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vdb_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_file_hs_itcare_smp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> insert_hoso_itcare_smp(INSERT_HOSO_ITCARE_SMPRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String vnguoi_cn = session_.get(USERNAME)
            String vmay_cn = session_.get(DEVICE_ID)
            String vip_cn = session_.get(DEVICE_IP)
            String sqlString = "{ call CSS.INSERT_HOSO_ITCARE_SMP(?,?,?,?,?,?,?,?,?,?,?) }"
            String data = null
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaifile_id,
                                     requestBody.vdb_id,
                                     requestBody.vloaidb_id,
                                     requestBody.vfile_name,
                                     requestBody.vfile_url,
                                     requestBody.vghichu,
                                     vmay_cn,
                                     vnguoi_cn,
                                     vip_cn,
                                     Sql.VARCHAR]) { String verr ->
                    data = verr
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [insert_hoso_itcare_smp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> huygan_hoso_itcare_smp(HuyGanHoSoITCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String vnguoi_cn = session_.get(USERNAME)
            String vmay_cn = session_.get(DEVICE_ID)
            String vip_cn = session_.get(DEVICE_IP)
            String sqlString = "{ call CSS.HUYGAN_HOSO_ITCARE_SMP(?,?,?,?,?,?,?,?,?) }"
            String data = null
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vdb_id,
                                     requestBody.vhoso_id,
                                     requestBody.vfile_id,
                                     vmay_cn,
                                     vnguoi_cn,
                                     vip_cn,
                                     Sql.VARCHAR]) { String verr ->
                    data = verr
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [huygan_hoso_itcare_smp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> kiemtra_upload_hs_itcaresmp(KiemTraUploadFileHSRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ ? = call css.kiemtra_upload_hs_itcaresmp(?,?,?) }"
            String data = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR,
                                     Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vdb_id
                ]) { String rs ->
                    data = rs
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [kiemtra_upload_hs_itcaresmp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<?> get_datafile_itcare(GetDataFileITCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)

            LayThongTinBaoTriResponse data = new LayThongTinBaoTriResponse();
            List dtThongTin = []
            List dtDanhSachTB = []
            String sqlString = "{ call css.lay_thongtin_baotri(?,?,?,?,?,?,?) }"
            String xml = "";
            List dtBieuMau = []

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vgr_id,
                                     requestBody.vkieu_id,
                                     requestBody.vloai_id,
                                     requestBody.vbieumau_id,
                                     CURSOR_PARAMETER,
                                     CURSOR_PARAMETER]) { ResultSet returnd_hd, ResultSet returnds_dt ->
                    data.setDs_hd(resultSetToObject.getDynamicData(returnd_hd))
                    data.setDs_dt(resultSetToObject.getDynamicData(returnds_dt))
                }
            }

            dtThongTin = data.getDs_hd();
            dtDanhSachTB = data.getDs_dt();

            if (dtThongTin.size() == 0){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
            }

            if (dtDanhSachTB.size() == 0){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin chi tiết thuê bao!", HttpStatus.INTERNAL_SERVER_ERROR))
            }

            StringBuilder xmlData = new StringBuilder()
            xmlData.append("<PhieuITCare>")

            xmlData.append("<ThongTin>")
            for (int i = 0; i < dtThongTin.size(); i++) {
                xmlData.append("<MA_KH>" + dtThongTin[i]["ma_kh"].toString() + "</MA_KH>")
                xmlData.append("<TEN_KH>" + dtThongTin[i]["ten_kh"].toString() + "</TEN_KH>")
                xmlData.append("<DIACHI_KH>" + dtThongTin[i]["diachi_kh"].toString() + "</DIACHI_KH>")
                xmlData.append("<SO_DT>" + dtThongTin[i]["so_dt"].toString() + "</SO_DT>")
                xmlData.append("<NHANVIEN_TH>" + dtThongTin[i]["nhanvien_th"].toString() + "</NHANVIEN_TH>")
                xmlData.append("<DONVI>" + dtThongTin[i]["donvi"].toString() + "</DONVI>")
                xmlData.append("<NGAY_GIO>" + dtThongTin[i]["ngay_gio"].toString() + "</NGAY_GIO>")
            }
            xmlData.append("</ThongTin>")
            xmlData.append("<DanhSachTB>")

            for (int i = 0; i < dtDanhSachTB.size(); i++) {
                if(requestBody.vbieumau_id == 3){
                    xmlData.append("<LOAI_ID>" + dtDanhSachTB[i]["loai_id"].toString() + "</LOAI_ID>")
                    xmlData.append("<NHANVIEN_TH>" + dtDanhSachTB[i]["nhanvien_th"].toString() + "</NHANVIEN_TH>")
                    xmlData.append("<DONVI>" + dtDanhSachTB[i]["donvi"].toString() + "</DONVI>")
                    xmlData.append("<DIENTHOAI_NV>" + dtDanhSachTB[i]["dienthoai_nv"].toString() + "</DIENTHOAI_NV>")
                    xmlData.append("<TEN_KH>" + dtDanhSachTB[i]["ten_kh"].toString() + "</TEN_KH>")
                    xmlData.append("<NGUOI_DAI_DIEN>" + dtDanhSachTB[i]["nguoi_dai_dien"].toString() + "</NGUOI_DAI_DIEN>")
                    xmlData.append("<CHUC_VU>" + dtDanhSachTB[i]["chuc_vu"].toString() + "</CHUC_VU>")
                    xmlData.append("<DIENTHOAI_KH>" + dtDanhSachTB[i]["dienthoai_kh"].toString() + "</DIENTHOAI_KH>")
                    xmlData.append("<GOI_DV>" + dtDanhSachTB[i]["goi_dv"].toString() + "</GOI_DV>")
                    xmlData.append("<SL_TBI>" + dtDanhSachTB[i]["sl_tbi"].toString() + "</SL_TBI>")
                    xmlData.append("<TINH_TRANG>" + dtDanhSachTB[i]["tinh_trang"].toString() + "</TINH_TRANG>")
                    xmlData.append("<NGUYEN_NHAN>" + dtDanhSachTB[i]["nguyen_nhan"].toString() + "</NGUYEN_NHAN>")
                    xmlData.append("<KHAC_PHUC>" + dtDanhSachTB[i]["khac_phuc"].toString() + "</KHAC_PHUC>")
                    xmlData.append("<KET_QUA>" + dtDanhSachTB[i]["ket_qua"].toString() + "</KET_QUA>")
                } else if (requestBody.vbieumau_id == 2){
                    xmlData.append("<MA_TB>" + dtDanhSachTB[i]["ma_tb"].toString() + "</MA_TB>")
                    xmlData.append("<NGUOI_SD>" + dtDanhSachTB[i]["nguoi_sd"].toString() + "</NGUOI_SD>")
                    xmlData.append("<ANTI_VIRUS>" + dtDanhSachTB[i]["anti_virus"].toString() + "</ANTI_VIRUS>")
                    xmlData.append("<CLEAN_COMPUTER>" + dtDanhSachTB[i]["clean_computer"].toString() + "</CLEAN_COMPUTER>")
                    xmlData.append("<UPDATE_OS>" + dtDanhSachTB[i]["update_os"].toString() + "</UPDATE_OS>")
                    xmlData.append("<RECYCLE>" + dtDanhSachTB[i]["recycle"].toString() + "</RECYCLE>")
                    xmlData.append("<CHECK_HDD>" + dtDanhSachTB[i]["check_hdd"].toString() + "</CHECK_HDD>")
                    xmlData.append("<CHECK_NETWORK>" + dtDanhSachTB[i]["check_network"].toString() + "</CHECK_NETWORK>")
                }else{
                    xmlData.append("<MA_TB>" + dtDanhSachTB[i]["ma_tb"].toString() + "</MA_TB>")
                    xmlData.append("<SERIAL_SUB>" + dtDanhSachTB[i]["serial_sub"].toString() + "</SERIAL_SUB>")
                    xmlData.append("<RAM_SUB>" + dtDanhSachTB[i]["ram_sub"].toString() + "</RAM_SUB>")
                    xmlData.append("<CPU_SUB>" + dtDanhSachTB[i]["cpu_sub"].toString() + "</CPU_SUB>")
                    xmlData.append("<HDD_SUB>" + dtDanhSachTB[i]["hdd_sub"].toString() + "</HDD_SUB>")
                    xmlData.append("<SCREEN_SUB>" + dtDanhSachTB[i]["screen_sub"].toString() + "</SCREEN_SUB>")
                    xmlData.append("<OS_SUB>" + dtDanhSachTB[i]["os_sub"].toString() + "</OS_SUB>")
                    xmlData.append("<NGUOI_SD_SUB>" + dtDanhSachTB[i]["nguoi_sd_sub"].toString() + "</NGUOI_SD_SUB>")
                }

            }
            xmlData.append("</DanhSachTB>")
            xmlData.append("</PhieuITCare>")


            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call css.danhmuc_tracuu_kh_donhang_smp(?,?,?,?) }", [Long.parseLong(phanVungId),
                                                                                 48,
                                                                                 requestBody.vbieumau_id,
                                                                                 CURSOR_PARAMETER]) { ResultSet rs ->
                    dtBieuMau = resultSetToObject.getDynamicData(rs)
                }
            }

            if (dtBieuMau.size() == 0){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin biểu mẫu gửi khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
            }

            String xslPath = dtBieuMau[0]["bieumau"].toString();

            StringWriter out = new StringWriter();

            def transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new StringReader(xslPath)))
            transformer.transform(new StreamSource(new StringReader(xmlData.toString())), new StreamResult(out))

            def ale = out.toString();

//            //String originalInput = Base64.getEncoder().encodeToString(ale.getBytes());
//
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(ale))

            byte[] exp =  exportPdf(ale);
            String kq = base64EncodeToString(exp);

            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq))


//            def env = ""
//            String kq = "";
//            String vxml = Base64.getEncoder().encodeToString(xmlData.toString().getBytes())
//            String vxsl = Base64.getEncoder().encodeToString(xslPath.getBytes())
//            String cskh_service_url = "http://10.35.60.141:8086/wsMobileApp.asmx"
//            env = post(cskh_service_url, null,
//                    """<?xml version="1.0" encoding="utf-8"?>
//                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
//                              <soap:Body>
//                                <HienThiFileITCare xmlns="http://tempuri.org/">
//                                  <vxml>${vxml}</vxml>
//                                  <vxsl>${vxsl}</vxsl>
//                                </HienThiFileITCare>
//                              </soap:Body>
//                            </soap:Envelope>""")
//
//           if(env.toString().startsWith("ERR:")){
//               kq = "Có lỗi khi thực hiện lấy thông tin phiếu!";
//           } else {
//               kq = env;
//           }
//
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq))

        } catch (Exception e) {
            //loggingManaged.error("[DVITCareController] - [kiemtra_upload_hs_itcaresmp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> gui_file_khachhang(GuiFileKhachHangRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String vnguoi_cn = session_.get(USERNAME)
            String vmay_cn = session_.get(DEVICE_ID)
            String vip_cn = session_.get(DEVICE_IP)
            String vnhanvien_id = session_.get(ID_NHANVIEN_DHSX)
            String vdonvi_id = session_.get(ID_DONVI)
            String vmatinh = session_.get(MA_TINH)

            LayThongTinBaoTriResponse data = new LayThongTinBaoTriResponse();
            List dtThongTin = []
            List dtDanhSachTB = []
            List dtBieuMau = []
            List dtNhanVien = []
            String vsdtnv = ""
            List dtToken = []
            String vtoken_gui = ""
            String vuid = ""
            List dtBieuMau_Email = []
            String vmaufile_email = ""
            String vten_kh = ""
            String vma_pl = ""
            String vngay_ky_hd = ""
            String vten_nv = ""
            String vso_dt = ""
            List lst_xslPath = [];
            List lst_xmlPath = [];

            String kq = "";

            // Lay thong tin nhan vien và các thông tin khách hàng

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call css.lay_thongtin_baotri_email(?,?,?,?) }", [Long.parseLong(phanVungId),
                                                                             requestBody.vgr_id,
                                                                             requestBody.vkieu,
                                                                             CURSOR_PARAMETER]) { ResultSet rs ->
                    dtNhanVien = resultSetToObject.getDynamicData(rs)
                }
            }

            if(dtNhanVien.size() == 0){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không lấy được thông tin khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
            }

            vsdtnv = dtNhanVien[0]["so_dt"].toString();
            vten_kh  = dtNhanVien[0]["ten_kh"].toString();
            vma_pl = dtNhanVien[0]["ma_pl"].toString();
            vngay_ky_hd = dtNhanVien[0]["ngay_ky_hd"].toString();
            vten_nv = dtNhanVien[0]["ten_nv"].toString();
            vso_dt = dtNhanVien[0]["so_dt"].toString();

            for (int k = 0; k < requestBody.hinhthuc.size(); k++) {
                // Lấy thông tin mẫu file xml và xsl

                if(requestBody.bieumau_id > 0){
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call css.lay_thongtin_baotri(?,?,?,?,?,?,?) }", [Long.parseLong(phanVungId),
                                                                                     requestBody.vgr_id,
                                                                                     requestBody.vkieu,
                                                                                     requestBody.hinhthuc[k]["id"],
                                                                                     requestBody.bieumau_id,
                                                                                     CURSOR_PARAMETER,
                                                                                     CURSOR_PARAMETER]) { ResultSet returnd_hd, ResultSet returnds_dt ->
                            data.setDs_hd(resultSetToObject.getDynamicData(returnd_hd))
                            data.setDs_dt(resultSetToObject.getDynamicData(returnds_dt))
                        }
                    }

                    dtThongTin = data.getDs_hd();
                    dtDanhSachTB = data.getDs_dt();

                    if (dtThongTin.size() == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
                    }

                    if (dtDanhSachTB.size() == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin chi tiết thuê bao!", HttpStatus.INTERNAL_SERVER_ERROR))
                    }


                    StringBuilder xmlData = new StringBuilder()
                    xmlData.append("<PhieuITCare>")

                    xmlData.append("<ThongTin>")
                    for (int i = 0; i < dtThongTin.size(); i++) {
                        xmlData.append("<MA_KH>" + dtThongTin[i]["ma_kh"].toString() + "</MA_KH>")
                        xmlData.append("<TEN_KH>" + dtThongTin[i]["ten_kh"].toString() + "</TEN_KH>")
                        xmlData.append("<DIACHI_KH>" + dtThongTin[i]["diachi_kh"].toString() + "</DIACHI_KH>")
                        xmlData.append("<SO_DT>" + dtThongTin[i]["so_dt"].toString() + "</SO_DT>")
                        xmlData.append("<NHANVIEN_TH>" + dtThongTin[i]["nhanvien_th"].toString() + "</NHANVIEN_TH>")
                        xmlData.append("<DONVI>" + dtThongTin[i]["donvi"].toString() + "</DONVI>")
                        xmlData.append("<NGAY_GIO>" + dtThongTin[i]["ngay_gio"].toString() + "</NGAY_GIO>")
                    }
                    xmlData.append("</ThongTin>")
                    xmlData.append("<DanhSachTB>")
                    for (int i = 0; i < dtDanhSachTB.size(); i++) {
                        if(requestBody.bieumau_id == 3){
                            xmlData.append("<LOAI_ID>" + dtDanhSachTB[i]["loai_id"].toString() + "</LOAI_ID>")
                            xmlData.append("<NHANVIEN_TH>" + dtDanhSachTB[i]["nhanvien_th"].toString() + "</NHANVIEN_TH>")
                            xmlData.append("<DONVI>" + dtDanhSachTB[i]["donvi"].toString() + "</DONVI>")
                            xmlData.append("<DIENTHOAI_NV>" + dtDanhSachTB[i]["dienthoai_nv"].toString() + "</DIENTHOAI_NV>")
                            xmlData.append("<TEN_KH>" + dtDanhSachTB[i]["ten_kh"].toString() + "</TEN_KH>")
                            xmlData.append("<NGUOI_DAI_DIEN>" + dtDanhSachTB[i]["nguoi_dai_dien"].toString() + "</NGUOI_DAI_DIEN>")
                            xmlData.append("<CHUC_VU>" + dtDanhSachTB[i]["chuc_vu"].toString() + "</CHUC_VU>")
                            xmlData.append("<DIENTHOAI_KH>" + dtDanhSachTB[i]["dienthoai_kh"].toString() + "</DIENTHOAI_KH>")
                            xmlData.append("<GOI_DV>" + dtDanhSachTB[i]["goi_dv"].toString() + "</GOI_DV>")
                            xmlData.append("<SL_TBI>" + dtDanhSachTB[i]["sl_tbi"].toString() + "</SL_TBI>")
                            xmlData.append("<TINH_TRANG>" + dtDanhSachTB[i]["tinh_trang"].toString() + "</TINH_TRANG>")
                            xmlData.append("<NGUYEN_NHAN>" + dtDanhSachTB[i]["nguyen_nhan"].toString() + "</NGUYEN_NHAN>")
                            xmlData.append("<KHAC_PHUC>" + dtDanhSachTB[i]["khac_phuc"].toString() + "</KHAC_PHUC>")
                            xmlData.append("<KET_QUA>" + dtDanhSachTB[i]["ket_qua"].toString() + "</KET_QUA>")
                        } else if (requestBody.bieumau_id == 2){
                            xmlData.append("<MA_TB>" + dtDanhSachTB[i]["ma_tb"].toString() + "</MA_TB>")
                            xmlData.append("<NGUOI_SD>" + dtDanhSachTB[i]["nguoi_sd"].toString() + "</NGUOI_SD>")
                            xmlData.append("<ANTI_VIRUS>" + dtDanhSachTB[i]["anti_virus"].toString() + "</ANTI_VIRUS>")
                            xmlData.append("<CLEAN_COMPUTER>" + dtDanhSachTB[i]["clean_computer"].toString() + "</CLEAN_COMPUTER>")
                            xmlData.append("<UPDATE_OS>" + dtDanhSachTB[i]["update_os"].toString() + "</UPDATE_OS>")
                            xmlData.append("<RECYCLE>" + dtDanhSachTB[i]["recycle"].toString() + "</RECYCLE>")
                            xmlData.append("<CHECK_HDD>" + dtDanhSachTB[i]["check_hdd"].toString() + "</CHECK_HDD>")
                            xmlData.append("<CHECK_NETWORK>" + dtDanhSachTB[i]["check_network"].toString() + "</CHECK_NETWORK>")
                        }else{
                            xmlData.append("<MA_TB>" + dtDanhSachTB[i]["ma_tb"].toString() + "</MA_TB>")
                            xmlData.append("<SERIAL_SUB>" + dtDanhSachTB[i]["serial_sub"].toString() + "</SERIAL_SUB>")
                            xmlData.append("<RAM_SUB>" + dtDanhSachTB[i]["ram_sub"].toString() + "</RAM_SUB>")
                            xmlData.append("<CPU_SUB>" + dtDanhSachTB[i]["cpu_sub"].toString() + "</CPU_SUB>")
                            xmlData.append("<HDD_SUB>" + dtDanhSachTB[i]["hdd_sub"].toString() + "</HDD_SUB>")
                            xmlData.append("<SCREEN_SUB>" + dtDanhSachTB[i]["screen_sub"].toString() + "</SCREEN_SUB>")
                            xmlData.append("<OS_SUB>" + dtDanhSachTB[i]["os_sub"].toString() + "</OS_SUB>")
                            xmlData.append("<NGUOI_SD_SUB>" + dtDanhSachTB[i]["nguoi_sd_sub"].toString() + "</NGUOI_SD_SUB>")
                        }
                    }
                    xmlData.append("</DanhSachTB>")
                    xmlData.append("</PhieuITCare>")

                    Object idx = new hinhthuc();
                    idx.id = requestBody.bieumau_id;
                    idx.value = xmlData.toString();

                    lst_xmlPath.push(idx);


                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call css.danhmuc_tracuu_kh_donhang_smp(?,?,?,?) }", [Long.parseLong(phanVungId),
                                                                                         58,
                                                                                         requestBody.bieumau_id,
                                                                                         CURSOR_PARAMETER]) { ResultSet rs ->
                            dtBieuMau = resultSetToObject.getDynamicData(rs)
                        }
                    }

                    String xslPath = dtBieuMau[0]["bieumau"].toString();

                    Object idk = new hinhthuc();
                    idk.id = requestBody.bieumau_id;
                    idk.value = xslPath.toString();
                    lst_xslPath.push(idk);
                } else {
                    for (int j = 1; j < 4; j++){
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call("{ call css.lay_thongtin_baotri(?,?,?,?,?,?,?) }", [Long.parseLong(phanVungId),
                                                                                         requestBody.vgr_id,
                                                                                         requestBody.vkieu,
                                                                                         requestBody.hinhthuc[k]["id"],
                                                                                         j,
                                                                                         CURSOR_PARAMETER,
                                                                                         CURSOR_PARAMETER]) { ResultSet returnd_hd, ResultSet returnds_dt ->
                                data.setDs_hd(resultSetToObject.getDynamicData(returnd_hd))
                                data.setDs_dt(resultSetToObject.getDynamicData(returnds_dt))
                            }
                        }

                        dtThongTin = data.getDs_hd();
                        dtDanhSachTB = data.getDs_dt();

                        if (dtThongTin.size() == 0){
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
                        }

                        if (dtDanhSachTB.size() == 0){
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin chi tiết thuê bao!", HttpStatus.INTERNAL_SERVER_ERROR))
                        }

                        StringBuilder xmlData = new StringBuilder()
                        xmlData.append("<PhieuITCare>")

                        xmlData.append("<ThongTin>")
                        for (int i = 0; i < dtThongTin.size(); i++) {
                            xmlData.append("<MA_KH>" + dtThongTin[i]["ma_kh"].toString() + "</MA_KH>")
                            xmlData.append("<TEN_KH>" + dtThongTin[i]["ten_kh"].toString() + "</TEN_KH>")
                            xmlData.append("<DIACHI_KH>" + dtThongTin[i]["diachi_kh"].toString() + "</DIACHI_KH>")
                            xmlData.append("<SO_DT>" + dtThongTin[i]["so_dt"].toString() + "</SO_DT>")
                            xmlData.append("<NHANVIEN_TH>" + dtThongTin[i]["nhanvien_th"].toString() + "</NHANVIEN_TH>")
                            xmlData.append("<DONVI>" + dtThongTin[i]["donvi"].toString() + "</DONVI>")
                            xmlData.append("<NGAY_GIO>" + dtThongTin[i]["ngay_gio"].toString() + "</NGAY_GIO>")
                        }
                        xmlData.append("</ThongTin>")
                        xmlData.append("<DanhSachTB>")
                        for (int i = 0; i < dtDanhSachTB.size(); i++) {
                            if(j == 3){
                                xmlData.append("<LOAI_ID>" + dtDanhSachTB[i]["loai_id"].toString() + "</LOAI_ID>")
                                xmlData.append("<NHANVIEN_TH>" + dtDanhSachTB[i]["nhanvien_th"].toString() + "</NHANVIEN_TH>")
                                xmlData.append("<DONVI>" + dtDanhSachTB[i]["donvi"].toString() + "</DONVI>")
                                xmlData.append("<DIENTHOAI_NV>" + dtDanhSachTB[i]["dienthoai_nv"].toString() + "</DIENTHOAI_NV>")
                                xmlData.append("<TEN_KH>" + dtDanhSachTB[i]["ten_kh"].toString() + "</TEN_KH>")
                                xmlData.append("<NGUOI_DAI_DIEN>" + dtDanhSachTB[i]["nguoi_dai_dien"].toString() + "</NGUOI_DAI_DIEN>")
                                xmlData.append("<CHUC_VU>" + dtDanhSachTB[i]["chuc_vu"].toString() + "</CHUC_VU>")
                                xmlData.append("<DIENTHOAI_KH>" + dtDanhSachTB[i]["dienthoai_kh"].toString() + "</DIENTHOAI_KH>")
                                xmlData.append("<GOI_DV>" + dtDanhSachTB[i]["goi_dv"].toString() + "</GOI_DV>")
                                xmlData.append("<SL_TBI>" + dtDanhSachTB[i]["sl_tbi"].toString() + "</SL_TBI>")
                                xmlData.append("<TINH_TRANG>" + dtDanhSachTB[i]["tinh_trang"].toString() + "</TINH_TRANG>")
                                xmlData.append("<NGUYEN_NHAN>" + dtDanhSachTB[i]["nguyen_nhan"].toString() + "</NGUYEN_NHAN>")
                                xmlData.append("<KHAC_PHUC>" + dtDanhSachTB[i]["khac_phuc"].toString() + "</KHAC_PHUC>")
                                xmlData.append("<KET_QUA>" + dtDanhSachTB[i]["ket_qua"].toString() + "</KET_QUA>")
                            } else if (j == 2){
                                xmlData.append("<MA_TB>" + dtDanhSachTB[i]["ma_tb"].toString() + "</MA_TB>")
                                xmlData.append("<NGUOI_SD>" + dtDanhSachTB[i]["nguoi_sd"].toString() + "</NGUOI_SD>")
                                xmlData.append("<ANTI_VIRUS>" + dtDanhSachTB[i]["anti_virus"].toString() + "</ANTI_VIRUS>")
                                xmlData.append("<CLEAN_COMPUTER>" + dtDanhSachTB[i]["clean_computer"].toString() + "</CLEAN_COMPUTER>")
                                xmlData.append("<UPDATE_OS>" + dtDanhSachTB[i]["update_os"].toString() + "</UPDATE_OS>")
                                xmlData.append("<RECYCLE>" + dtDanhSachTB[i]["recycle"].toString() + "</RECYCLE>")
                                xmlData.append("<CHECK_HDD>" + dtDanhSachTB[i]["check_hdd"].toString() + "</CHECK_HDD>")
                                xmlData.append("<CHECK_NETWORK>" + dtDanhSachTB[i]["check_network"].toString() + "</CHECK_NETWORK>")
                            }else{
                                xmlData.append("<MA_TB>" + dtDanhSachTB[i]["ma_tb"].toString() + "</MA_TB>")
                                xmlData.append("<SERIAL_SUB>" + dtDanhSachTB[i]["serial_sub"].toString() + "</SERIAL_SUB>")
                                xmlData.append("<RAM_SUB>" + dtDanhSachTB[i]["ram_sub"].toString() + "</RAM_SUB>")
                                xmlData.append("<CPU_SUB>" + dtDanhSachTB[i]["cpu_sub"].toString() + "</CPU_SUB>")
                                xmlData.append("<HDD_SUB>" + dtDanhSachTB[i]["hdd_sub"].toString() + "</HDD_SUB>")
                                xmlData.append("<SCREEN_SUB>" + dtDanhSachTB[i]["screen_sub"].toString() + "</SCREEN_SUB>")
                                xmlData.append("<OS_SUB>" + dtDanhSachTB[i]["os_sub"].toString() + "</OS_SUB>")
                                xmlData.append("<NGUOI_SD_SUB>" + dtDanhSachTB[i]["nguoi_sd_sub"].toString() + "</NGUOI_SD_SUB>")
                            }
                        }
                        xmlData.append("</DanhSachTB>")
                        xmlData.append("</PhieuITCare>")

                        //lst_xmlPath.push(xmlData.toString());
                        Object idx = new hinhthuc();
                        idx.id = j;
                        idx.value = xmlData.toString();

                        lst_xmlPath.push(idx);


                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call("{ call css.danhmuc_tracuu_kh_donhang_smp(?,?,?,?) }", [Long.parseLong(phanVungId),
                                                                                             58,
                                                                                             j,
                                                                                             CURSOR_PARAMETER]) { ResultSet rs ->
                                dtBieuMau = resultSetToObject.getDynamicData(rs)
                            }
                        }

                        if (dtBieuMau.size() == 0){
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin biểu mẫu gửi khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
                        }

                        String xslPath = dtBieuMau[0]["bieumau"].toString();
                        //lst_xslPath.push(xslPath);
                        Object idk = new hinhthuc();
                        idk.id = j;
                        idk.value = xslPath.toString();
                        lst_xslPath.push(idk);
                    }
                }


                if(requestBody.hinhthuc[k]["id"].toString() == "1") //Zalo
                {
                    //Lấy token gửi zalo

                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call css.lay_token_sendzalo(?,?) }", [Long.parseLong(phanVungId),
                                                                          CURSOR_PARAMETER]) { ResultSet rs ->
                            dtToken = resultSetToObject.getDynamicData(rs)
                        }
                    }

                    if (dtToken.size() == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không lấy được thông tin token zalo!", HttpStatus.INTERNAL_SERVER_ERROR))
                    }

                    vtoken_gui = dtToken[0]["lenh"].toString();

                    //Lấy uid

                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ ? = call css.getidzalo(?,?) }", [Sql.VARCHAR,
                                                                     Long.parseLong(phanVungId),
                                                                     requestBody.hinhthuc[k]["value"]]) { String rs ->
                            vuid = rs

                        }
                    }

                    if (vuid.length() == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không lấy được thông tin zalo khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
                    }

                    if(vuid == "-3" || vuid == "-2"){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Khách hàng chưa quan tâm Zalo OA, không thể gửi thông báo tới khách hàng!", HttpStatus.INTERNAL_SERVER_ERROR))
                    }

                    ////////////////////////////////////////
                }


                if(requestBody.hinhthuc[k]["id"].toString() == "2"){
                    //nếu là loại gửi email thì lấy mẫu email: vmaufile_email
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call css.lay_thongtin_bieumau(?,?,?,?) }", [Long.parseLong(phanVungId),
                                                                                2,
                                                                                requestBody.bieumau_id,
                                                                                CURSOR_PARAMETER]) { ResultSet rs ->
                            dtBieuMau_Email = resultSetToObject.getDynamicData(rs)
                        }
                    }

                    if (dtBieuMau_Email.size() == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không lấy được thông tin biểu mẫu email!", HttpStatus.INTERNAL_SERVER_ERROR))
                    }

                    vmaufile_email = Base64.getEncoder().encodeToString(dtBieuMau_Email[0]["noidung"].toString().getBytes());
                }


                //Gọi ws gui file cho khach hang
                def env = "";
                String vdoituong_gui = requestBody.hinhthuc[k]["value"].toString()
                String vkieugui_id = requestBody.hinhthuc[k]["id"].toString()
                String cskh_service_url = "http://10.35.60.141:8086/wsMobileApp.asmx"

                StringBuilder inp_xml = new StringBuilder()
                StringBuilder inp_xsl = new StringBuilder()

                //inp_xml.append("<vxslPath>")
                for(int t = 0; t<lst_xslPath.size(); t++ ){
                    inp_xml.append("<lstBieuMau_OBJ>")
                    inp_xml.append("<id>" + lst_xslPath[t]["id"].toString() + "</id>")
                    inp_xml.append("<value>" + Base64.getEncoder().encodeToString(lst_xslPath[t]["value"].toString().getBytes()) + "</value>")
                    inp_xml.append("</lstBieuMau_OBJ>")
                }
                //inp_xml.append("<vxslPath>")

                //inp_xsl.append("<vxmlPath>")
                for(int v = 0; v<lst_xmlPath.size(); v++ ){
                    inp_xsl.append("<lstBieuMau_OBJ>")
                    inp_xsl.append("<id>" + lst_xmlPath[v]["id"].toString() + "</id>")
                    inp_xsl.append("<value>" + Base64.getEncoder().encodeToString(lst_xmlPath[v]["value"].toString().getBytes()) + "</value>")
                    inp_xsl.append("</lstBieuMau_OBJ>")
                }
                //inp_xsl.append("<vxmlPath>")

                env = post(cskh_service_url, null,
                        """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <GUI_FILE_KHACHHANG2 xmlns="http://tempuri.org/">
                                  <obj>
                                    <vgr_id>${requestBody.vgr_id}</vgr_id>
                                    <vkieu>${requestBody.vkieu}</vkieu>
                                    <vkieugui_id>${vkieugui_id}</vkieugui_id>
                                    <doituong_gui>${vdoituong_gui}</doituong_gui>
                                    <bieumau_id>${requestBody.bieumau_id}</bieumau_id>
                                    <nhanvien_gui_id>${vnhanvien_id}</nhanvien_gui_id>
                                    <donvi_gui_id>${vdonvi_id}</donvi_gui_id>
                                    <hinhthuc>
                                      <HINHTHUC_OBJ>
                                        <id>${vkieugui_id}</id>
                                        <value>${vdoituong_gui}</value>
                                      </HINHTHUC_OBJ>
                                    </hinhthuc>
                                    <vsdtnv>${vsdtnv}</vsdtnv>
                                    <vtoken_gui>${vtoken_gui}</vtoken_gui>
                                    <vuid>${vuid}</vuid>
                                    <vxslPath>${inp_xsl}</vxslPath>
                                    <vxmlPath>${inp_xml}</vxmlPath>
                                    <vmaufile_email>${vmaufile_email}</vmaufile_email>
                                    <vten_kh>${vten_kh}</vten_kh>
                                    <vma_pl>${vma_pl}</vma_pl>
                                    <vngay_ky_hd>${vngay_ky_hd}</vngay_ky_hd>
                                    <vten_nv>${vten_nv}</vten_nv>
                                    <vso_dt>${vso_dt}</vso_dt>
                                    <may_cn>${vmay_cn}</may_cn>
                                    <nguoi_cn>${vnguoi_cn}</nguoi_cn>
                                    <matinh>${vmatinh}</matinh>
                                  </obj>
                                </GUI_FILE_KHACHHANG2>
                              </soap:Body>
                            </soap:Envelope>""")

                //---------------------------//

                //nếu kết quả gửi thành công = "OK", thì cập nhật log gửi
                if(env == "OK"){
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call css.capnhat_log_guitt_itcare_kh(?,?,?,?,?,?,?,?,?,?) }",
                                [Long.parseLong(phanVungId),
                                 requestBody.vgr_id,
                                 requestBody.hinhthuc[k]["id"],
                                 requestBody.hinhthuc[k]["value"],
                                 requestBody.bieumau_id,
                                 vnhanvien_id,
                                 vdonvi_id,
                                 vmay_cn,
                                 vnguoi_cn,
                                 Sql.VARCHAR]) { String verr ->
                            kq = verr
                        }
                    }
                } else {
                    kq = env;
                }

            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [gui_file_khachhang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    GPathResult post(String url, Map<String, String> headers, String body) {
        //headers.put("Content-Type", "text/xml;charset=UTF-8")
        Map<String, String> headers2 = new HashMap<>();
        headers2.put("Content-Type", "text/xml;charset=UTF-8");

        String resp = httpManaged.post(["url": url], headers2, body)
        return new XmlSlurper().parseText(resp)
    }

    ConverterProperties ConverterProperties = getDefaultConverterProperties();
    ConverterProperties getDefaultConverterProperties() {
        ConverterProperties properties = new ConverterProperties();
        //properties.setBaseUri(Paths.get("").toUri() + BaseUri);//dòng này chỉ cần nếu sử dụng file style.css
        properties.setFontProvider(new DefaultFontProvider(true, true, true));
        return properties;
    }

    byte[] exportPdf(String input) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(input, outputStream, ConverterProperties);
        return outputStream.toByteArray();
    }
    String base64EncodeToString(byte[] input) throws IOException {
        return Base64.getEncoder().encodeToString(input);
    }

//    byte[] convertHtmlToPdfBytes( String htmlString ) {
//        Document document = new Document();
//
//        ByteArrayOutputStream outp = new ByteArrayOutputStream();
//
//        PdfWriter writer = PdfWriter.getInstance(document, outp);
//        document.open();
//
//        InputStream inp = IOUtils.toInputStream(htmlString);
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inp, Charset.forName("UTF-8"));
//
//        document.close();
//
//        return outp.toByteArray();
//    }

}
