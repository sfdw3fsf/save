package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.service.NghiemThuSuCoService;

import javax.validation.Valid;

@RestController
@RequestMapping("qlsc/nghiem-thu")
public class NghiemThuSuCoController {

    private final NghiemThuSuCoService nghiemThuSuCoService;

    @org.springframework.beans.factory.annotation.Value("${internal.esb.url}")
    private String esbUrl;

    @org.springframework.beans.factory.annotation.Value("${internal.esb.token-key}")
    private String esbTokenKey;

    @org.springframework.beans.factory.annotation.Value("${internal.esb.token-id}")
    private String esbTokenId;

    @org.springframework.beans.factory.annotation.Value("${internal.tichhop.url}")
    private String tichhopUrl;

    public NghiemThuSuCoController(NghiemThuSuCoService nghiemThuSuCoService) {
        this.nghiemThuSuCoService = nghiemThuSuCoService;
    }

    @Operation(summary = "Nạp danh sách nguồn sự cố")
    @RequestMapping(value = "/napDsNguonSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsNguon() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.napDsNguonSC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nạp danh sách mức độ sự cố")
    @RequestMapping(value = "/napDsMucDoSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsMucDo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.napDsMucDoSC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nạp danh sách tình trạng sự cố")
    @RequestMapping(value = "/napDsTinhTrangSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsTinhTrang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.napDsTinhTrangSC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nạp danh sách nhóm sự cố")
    @RequestMapping(value = "/napDsNhomSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsNhom() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.napDsNhomSc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách phieu dieu nt")
    @RequestMapping(value = "/layDsPhieuDieuNt", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsPhieuDieuNt(@RequestParam("suCoId") int suCoId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.layDsPhieuDieuNt(suCoId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ảnh chụp phiếu nghiệm thu sự cố")
    @RequestMapping(value = "/layAnhChupPhieuNghiemThuSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layAnhChupPhieuNghiemThuSc(@RequestParam("phieuId") int phieuId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.layAnhChupPhieuNghiemThuSc(phieuId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách phiếu nghiệm thu sự cố")
    @RequestMapping(value = "/layDsPhieuNghiemThuSc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsPhieuNghiemThuSc(@RequestBody DanhSachPhieuNghiemThu danhSachPhieuNghiemThu) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.layDsPhieuNghiemThuSc(danhSachPhieuNghiemThu));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nghiệm thu xử lý sự cố")
    @RequestMapping(value = "/nghiemThuSc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nghiemThuSuCotd(@RequestBody NghiemThuSuCo nghiemThuSuCotd) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.nghiemThuSuCotd(nghiemThuSuCotd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Trả phiếu nghiệm thu")
    @RequestMapping(value = "/traPhieuNghiemThu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> traPhieuNghiemThu(@RequestBody @Valid TraPhieuSuCoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(nghiemThuSuCoService.traPhieuNghiemThu(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm danh sách thiết bị")
    @RequestMapping(value = "/timKiemDsThietBi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> timKiemDsThietBi(@RequestBody TimKiemDsThietBiDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.timKiemDsThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

//    @Operation(summary = "Khóa phiếu")
//    @RequestMapping(value = "/khoa-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> khoaPhieu(@Valid @RequestBody KhoaPhieuDto input) {
        ApiResult rs = new ApiResult();
        try {
            boolean khoaPhieuResult = nghiemThuSuCoService.khoaPhieu(input);

            String externalResult = null;
            // Call external API after nghiemThuSuCoService.khoaPhieu
            try {
                // Get ma_tinh and ma_nhanvien_xuly from context
                vn.vnpt.context.AppRequestContext context = vn.vnpt.context.AppRequestContext.getCurrent();
                String maTinh = context.getMaTinh() != null ? context.getMaTinh() : "";

                // Get extra data from stored procedure
                java.util.Map<String, Object> spData = nghiemThuSuCoService.layDataChoSaveCSSAPI(input.getSuco_id(),
                        maTinh);

                String maSc = "";
                String phieuId = "";
                String noiDungXuLy = "";
                String mucDoId = "";
                String maQt = "";
                String maBuoc = "";
                String donViGiaoId = "";
                String donViNhanId = "";

                if (spData != null) {
                    maSc = spData.get("MA_SC") != null ? String.valueOf(spData.get("MA_SC")) : "";
                    phieuId = spData.get("PHIEU_ID") != null ? String.valueOf(spData.get("PHIEU_ID")) : "";
                    noiDungXuLy = spData.get("NOI_DUNG_XU_LY") != null ? String.valueOf(spData.get("NOI_DUNG_XU_LY"))
                            : "";
                    mucDoId = spData.get("MUCDO_ID") != null ? String.valueOf(spData.get("MUCDO_ID")) : "";
                    maQt = spData.get("MA_QT") != null ? String.valueOf(spData.get("MA_QT")) : "";
                    maBuoc = spData.get("MA_BUOC") != null ? String.valueOf(spData.get("MA_BUOC")) : "";
                    donViGiaoId = spData.get("DONVI_GIAO_ID") != null ? String.valueOf(spData.get("DONVI_GIAO_ID"))
                            : "";
                    donViNhanId = spData.get("DONVI_NHAN_ID") != null ? String.valueOf(spData.get("DONVI_NHAN_ID"))
                            : "";
                }

                if ("QT1.6.1".equals(maQt) || "QTXLSC_MANE".equals(maQt) || "QTXLSC_ACCESS".equals(maQt)) {
                    org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
                    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
    
                    java.util.Map<String, Object> payload = new java.util.HashMap<>();
                    payload.put("id_tts", "0");
                    payload.put("id_css", null);
                    payload.put("loaiphieu_id", "10");
                    payload.put("parent_id", maSc);
                    payload.put("ma_tinh", maTinh);
    
                    payload.put("mo_ta", noiDungXuLy);
    
                    // tg_batdau: current date time (format "dd/MM/yyyy HH:mm:ss")
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    payload.put("tg_batdau", sdf.format(new java.util.Date()));
    
                    payload.put("tg_dukien_xyly", "");
                    payload.put("tg_ketthuc", "");
                    payload.put("anhhuong_dvu", "0");
                    payload.put("chitiet_anhhuong", "");
                    payload.put("donvi_chuquan", "");
                    payload.put("dai_vt", "");
                    payload.put("mucdo_id", mucDoId);
                    payload.put("loai_suco", "0");
                    payload.put("hethong", "0");
                    payload.put("thietbi_id", "0");
                    payload.put("ma_thietbi", "");
                    payload.put("nguon_sc", "");
                    payload.put("thongtin_chitiet", noiDungXuLy);
                    payload.put("nguyen_nhan", "");
                    payload.put("giai_phap", "");
                    payload.put("bienphap_phongngua", "");
                    payload.put("thamso_thaydoi", "");
                    payload.put("trangthai_xly", "1");
                    payload.put("ma_quytrinh", maQt);
                    payload.put("ma_buoc", maBuoc);
                    payload.put("ma_nhanvien_xuly", "");
                    payload.put("donvi_nhan_id", donViNhanId);
                    payload.put("ma_donvi_nhan_id", "");
                    payload.put("donvi_giao_id", donViGiaoId);
                    payload.put("css_parent_id", "0");
                    payload.put("css_id", phieuId);
                    payload.put("donvi_xuly_id", "0");
                    payload.put("loaidoituong", "");
                    payload.put("phuongan_dexuat", java.util.Collections.emptyList());
    
                    org.springframework.http.HttpEntity<java.util.Map<String, Object>> request = new org.springframework.http.HttpEntity<>(
                            payload, headers);
    
                    // Logging Request
                    System.out.println("[SaveCSSAPI Request] URL: https://tts.vnpt.vn/ws/api/APIDHSXKD/SaveCSSAPI");
                    System.out.println("[SaveCSSAPI Request] Payload: " + payload);
    
                    org.springframework.http.ResponseEntity<String> apiResponse = restTemplate
                            .postForEntity("https://tts.vnpt.vn/ws/api/APIDHSXKD/SaveCSSAPI", request, String.class);
    
                    externalResult = apiResponse.getBody();
                    // Logging Response
                    System.out.println("[SaveCSSAPI Response] Status: " + apiResponse.getStatusCode());
                    System.out.println("[SaveCSSAPI Response] Body: " + externalResult);
                } else if ("C3_CLM_03".equals(maQt)) {
                    org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
                    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                    
                    vn.vnpt.context.AppRequestContext current = vn.vnpt.context.AppRequestContext.getCurrent();
                    if (current.getToken() != null && current.getToken().getValue() != null) {
                        headers.add("Authorization", current.getToken().getValue());
                    }
    
                    java.util.Map<String, Object> payload = new java.util.HashMap<>();
                    payload.put("loaiphieu_id", "10");
                    payload.put("tts_parent_id", maSc);

                    java.util.Map<String, Object> gwOssData = nghiemThuSuCoService.layThongTinDongPhieuGwOss(maSc, maTinh);
                    if (gwOssData != null) {
                        payload.put("id_tts", gwOssData.get("ID_TTS") != null ? String.valueOf(gwOssData.get("ID_TTS")) : "");
                        payload.put("noi_dung_xu_ly", gwOssData.get("NOI_DUNG_XU_LY") != null ? String.valueOf(gwOssData.get("NOI_DUNG_XU_LY")) : "");
                        payload.put("ma_nhanvien_xuly", gwOssData.get("MA_NHANVIEN_XULY") != null ? String.valueOf(gwOssData.get("MA_NHANVIEN_XULY")) : "");
                        payload.put("ma_donvi_xuly_hrm", gwOssData.get("MA_DONVI_XULY_HRM") != null ? String.valueOf(gwOssData.get("MA_DONVI_XULY_HRM")) : "");
                    } else {
                        payload.put("id_tts", "");
                        payload.put("noi_dung_xu_ly", "");
                        payload.put("ma_nhanvien_xuly", "");
                        payload.put("ma_donvi_xuly_hrm", "");
                    }
    
                    org.springframework.http.HttpEntity<java.util.Map<String, Object>> request = new org.springframework.http.HttpEntity<>(
                            payload, headers);
    
                    String apiUrl = tichhopUrl + "/fms_alarms/sync_via_gw_oss";
                    System.out.println("[sync_via_gw_oss Request] URL: " + apiUrl);
                    System.out.println("[sync_via_gw_oss Request] Payload: " + payload);
    
                    org.springframework.http.ResponseEntity<String> apiResponse = restTemplate
                            .postForEntity(apiUrl, request, String.class);
    
                    externalResult = apiResponse.getBody();
                    System.out.println("[sync_via_gw_oss Response] Status: " + apiResponse.getStatusCode());
                    System.out.println("[sync_via_gw_oss Response] Body: " + externalResult);
                }

            } catch (Exception ex) {
                // Ignore external API failure so it doesn't fail the main transaction
                System.err.println("[SaveCSSAPI Error] " + ex.getMessage());
                ex.printStackTrace();
            }

            java.util.Map<String, Object> responseData = new java.util.HashMap<>();
            responseData.put("result", khoaPhieuResult);
            if (externalResult != null && !externalResult.trim().isEmpty()) {
                try {
                    responseData.put("externalResult", new com.fasterxml.jackson.databind.ObjectMapper().readValue(externalResult, Object.class));
                } catch (Exception e) {
                    responseData.put("externalResult", externalResult);
                }
            } else {
                responseData.put("externalResult", null);
            }
            rs.setData(responseData);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Khóa phiếu")
    @RequestMapping(value = "/khoa-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> khoaPhieuV2(@Valid @RequestBody KhoaPhieuDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(nghiemThuSuCoService.khoaPhieuV2(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy số điện thoại nhắc việc")
    @RequestMapping(value = "/lay-so-dt-nhac-viec", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laySoDtNhacViec(@RequestParam("phieu_id") Long phieuId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.laySoDtNhacViec(phieuId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nhắc việc qua SMS")
    @RequestMapping(value = "/xuLyGiaoViec", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xuLyGiaoViec(@Valid @RequestBody XuLyGiaoViecInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoService.xuLyGiaoViec(input.getPhieu_id(), input.getNoiDung()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
