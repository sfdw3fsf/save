package vn.vnpt.vnptit.ecms.controller.hatang;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyGenericInput;
import vn.vnpt.vnptit.ecms.dto.hatang.CapPhatTaiNguyenDto;
import vn.vnpt.vnptit.ecms.dto.hatang.ThongTinYeuCauDto;
import vn.vnpt.vnptit.ecms.dto.hatang.YeuCauCapPhatDTO;
import vn.vnpt.vnptit.ecms.service.hatang.PhieuCapPhatService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("phieu-cap-phat")
public class PhieuCapPhatController {

    @Autowired
    private PhieuCapPhatService service;

    /**
     * 1. GET /phieu-cap-phat/danh-sach
     * => Gọi SP_PHIEU_DANHSACH
     */
    @Operation(summary = "Lấy danh sách phiếu cấp phát (có filter)")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> getDanhSachPhieu(@RequestParam(name = "trang_thai", required = false) Long trangThai, @RequestParam(name = "donvi_id", required = false) Long donviId, @RequestParam(name = "spdv_id", required = false) Long spdvId) {
        ApiResult rs = new ApiResult();
        try {
            List<Map<String, Object>> data = service.spPhieuDanhSach(trangThai, donviId, spdvId);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * 2. POST /phieu-cap-phat/tao-moi
     * => Gọi SP_PHIEU_TAOMOI
     */
    @Operation(summary = "Tạo mới phiếu cấp phát")
    @PostMapping("/tao-moi")
    public ResponseEntity<ApiResult> taoMoiPhieu(@RequestParam(name = "spdv_id", required = true) Long spdvId, @RequestParam(name = "ten_spdv_tu_nhap", required = false) String tenSpdvTuNhap, @RequestParam(name = "hatangmang_id", required = true) Long hatangmangId, @RequestParam(name = "mucdich_sudung", required = false) String mucDich, @RequestParam(name = "ghi_chu", required = false) String ghiChu) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> data = service.spPhieuTaoMoi(spdvId, tenSpdvTuNhap, hatangmangId, mucDich, ghiChu);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * 3. GET /phieu-cap-phat/{phieuId}/chi-tiet
     * => Gọi 2 SP (nếu tách sẵn) spPhieuHeader + spPhieuChiTiet
     */
    @Operation(summary = "Lấy chi tiết 1 phiếu, gồm header + chi tiết")
    @GetMapping("/{phieuId}/chi-tiet")
    public ResponseEntity<ApiResult> getPhieuChiTiet(@PathVariable("phieuId") Long phieuId) {
        ApiResult rs = new ApiResult();
        try {
            // Demo: Gọi 2 SP => gộp kết quả
            // Hoặc, nếu code PKG_PHIEU_CAPPHAT trả 2 cursor, ta parse 2 cursors
            // Tùy logic
            List<Map<String, Object>> header = service.spPhieuHeader(phieuId);
            List<Map<String, Object>> detail = service.spPhieuChiTiet(phieuId);

            Map<String, Object> data = new HashMap<>();
            data.put("header", header);
            data.put("detail", detail);

            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * 4. PUT /phieu-cap-phat/{phieuId}/cap-nhat => SP_PHIEU_CAPNHAT
     */
    @Operation(summary = "Cập nhật phiếu (chỉ khi đang Lưu nháp)")
    @PutMapping("/{phieuId}/cap-nhat")
    public ResponseEntity<ApiResult> capNhatPhieu(@PathVariable("phieuId") Long phieuId, @RequestParam(name = "spdv_id") Long spdvId, @RequestParam(name = "ten_spdv_tu_nhap") String tenSpdvTuNhap, @RequestParam(name = "hatangmang_id") Long hatangmangId, @RequestParam(name = "mucdich_sudung") String mucDich, @RequestParam(name = "ghi_chu") String ghiChu) {
        ApiResult rs = new ApiResult();
        try {
            Long result = service.spPhieuCapNhat(phieuId, spdvId, tenSpdvTuNhap, hatangmangId, mucDich, ghiChu);
            Map<String, Object> data = new HashMap<>();
            data.put("result", result);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * 5. POST /phieu-cap-phat/{phieuId}/gui-yeu-cau => SP_PHIEU_GUIYEUCAU
     */
    @Operation(summary = "Gửi yêu cầu (chuyển sang Chờ cấp phát)")
    @PostMapping("/{phieuId}/gui-yeu-cau")
    public ResponseEntity<ApiResult> guiYeuCau(@PathVariable("phieuId") Long phieuId) {
        ApiResult rs = new ApiResult();
        try {
            Long result = service.spPhieuGuiYeuCau(phieuId);
            Map<String, Object> data = new HashMap<>();
            data.put("result", result);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * 6. POST /phieu-cap-phat/{phieuId}/hoan-thanh => SP_PHIEU_HOANTHANH
     */
    @Operation(summary = "Hoàn thành phiếu (chỉ khi đang Chờ cấp phát)")
    @PostMapping("/{phieuId}/hoan-thanh")
    public ResponseEntity<ApiResult> hoanThanhPhieu(@PathVariable("phieuId") Long phieuId) {
        ApiResult rs = new ApiResult();
        try {
            Long result = service.spPhieuHoanThanh(phieuId);
            Map<String, Object> data = new HashMap<>();
            data.put("result", result);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * 7. GET /phieu-cap-phat/{phieuId}/ds-cap-phat => SP_PHIEU_DANHSACH_CAPPHAT
     */
    @Operation(summary = "Lấy danh sách tài nguyên đã cấp cho phiếu này")
    @GetMapping("/{phieuId}/ds-cap-phat")
    public ResponseEntity<ApiResult> dsCapPhat(@PathVariable("phieuId") Long phieuId) {
        ApiResult rs = new ApiResult();
        try {
            List<Map<String, Object>> data = service.spPhieuDsCapPhat(phieuId);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /// //////////////////// Yeu cau cap phat //////////////////////
    /// Author: Nguyen Viet Cuong
    @Operation(summary = "Lấy danh sách phiếu yêu cầu")
    @GetMapping("")
    public ResponseEntity<ApiResult> getAllCapPhat(@RequestParam(value = "type", required = false) Number type) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsYeuCauCapPhat(type);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhu cầu ảo hóa")
    @GetMapping("/{yeucau_id}/nhu-cau-ao-hoa")
    public ResponseEntity<ApiResult> getAllNhuCauAoHoa(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsNhuCauAoHoa(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhu cầu k8s")
    @GetMapping("/{yeucau_id}/nhu-cau-k8s")
    public ResponseEntity<ApiResult> getAllNhuCauK8S(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsNhuCauK8S(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhu cầu IDG")
    @GetMapping("/{yeucau_id}/nhu-cau-idg")
    public ResponseEntity<ApiResult> getAllNhuCauIDG(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsNhuCauIDG(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhu cầu backup")
    @GetMapping("/{yeucau_id}/nhu-cau-backup")
    public ResponseEntity<ApiResult> getAllNhuCauBackup(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsNhuCauBackup(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thông tin cấp phát ảo hóa")
    @GetMapping("/{yeucau_id}/thong-tin-cap-phat-ao-hoa")
    public ResponseEntity<ApiResult> getThongTinCapPhatAoHoa(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsThongTinCapPhatAoHoa(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thông tin cấp phát k8s")
    @GetMapping("/{yeucau_id}/thong-tin-cap-phat-k8s")
    public ResponseEntity<ApiResult> getThongTinCapPhatK8s(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsThongTinCapPhatK8S(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thông tin cấp phát IDG")
    @GetMapping("/{yeucau_id}/thong-tin-cap-phat-idg")
    public ResponseEntity<ApiResult> getThongTinCapPhatIDG(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsThongTinCapPhatIDG(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thông tin cấp phát backup")
    @GetMapping("/{yeucau_id}/thong-tin-cap-phat-backup")
    public ResponseEntity<ApiResult> getThongTinCapPhatBackup(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsThongTinCapPhatBackup(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/{yeucau_id}/file-yeu-cau")
    public ResponseEntity<ApiResult> getFileYeuCau(@PathVariable("yeucau_id") Integer ycID) {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.getFileYeuCau(ycID);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo mới/cập nhật yêu cầu cấp phát")
    @PostMapping("")
    public ResponseEntity<ApiResult> createUpdateYeuCau(@Valid @RequestBody YeuCauCapPhatDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.createUpdateCapPhat(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhu cầu ảo hóa")
    @PostMapping("/nhu-cau-ao-hoa/{id}")
    public ResponseEntity<ApiResult> deleteNhuCauAoHoa(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteNCAoHoa(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhu cầu k8s")
    @PostMapping("/nhu-cau-k8s/{id}")
    public ResponseEntity<ApiResult> deleteNhuCauK8s(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteNCK8s(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhu cầu IDG")
    @PostMapping("/nhu-cau-idg/{id}")
    public ResponseEntity<ApiResult> deleteNhuCauIDG(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteNCIDG(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhu cầu backup")
    @PostMapping("/nhu-cau-backup/{id}")
    public ResponseEntity<ApiResult> deleteNhuCauBackup(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteNCBackup(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhu cầu backup")
    @PostMapping("/delete-items")
    public ResponseEntity<ApiResult> deleteYeuCauCapPhat(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
            String msg = "";
            Number[] ids = item.getId();
            if (ids.length == 0) {
                msg = "Danh sách id không được để trống";
            } else {
                for (Number id: ids) {
                    if (id == null || id.intValue() < 1) {
                        msg = "Danh sách id có thông tin không phù hợp";
                        break;
                    }
                }
            }
            if (msg.equals("")) rs.setData(this.service.deleteYeuCauCapPhat(ids));
            else rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tao moi phieu cap phat theo phieu yeu cau")
    @PostMapping(value = "/cap-phat")
    public ResponseEntity<ApiResult> createPhieuCapPhat(@RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            Number yc_id = (Number) body.get("yc_id");
            rs.setData(this.service.createPhieuCapPhat(yc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //    @Operation(summary = "Tạo mới/cập nhật yêu cầu cấp phát")
    //    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //    public ResponseEntity<ApiResult> createUpdateYeuCau(
    //            @RequestParam("info") String infoJson,
    //            @RequestParam(value = "file_yc", required = false)MultipartFile file
    //    ) {
    //        ApiResult rs = new ApiResult();
    //        try {
    //            // Chuyen JSON thanh DTO
    //            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
    //                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //            YeuCauCapPhatDTO input = objectMapper.readValue(infoJson, YeuCauCapPhatDTO.class);
    //
    //            // Chuyen file thanh byte[] neu co
    //            if (file != null && !file.isEmpty()) {
    //                input.setFile_yc(file.getBytes());
    //            }
    //
    //            rs.setData(this.service.createAndUpdateYeuCau(input));
    //        } catch (Exception e) {
    //            rs.setException(new AppSqlException("Lỗi không xác định: " + e.getMessage()));
    //        }
    //        return rs.getResponseEntity();
    //    }

    @Operation(summary = "Từ chối cấp phát tài nguyên")
    @RequestMapping(value = "/tu-choi-cap-phat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tuChoiCapPhatTaiNguyen(@RequestBody CapPhatTaiNguyenDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.tuChoiCapPhatTaiNguyen(dto.getP_capphat_id(), dto.getP_lydotuchoi()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xác nhận cấp phát tài nguyên")
    @RequestMapping(value = "/xac-nhan-cap-phat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xacNhanCapPhatTaiNguyen(@RequestBody CapPhatTaiNguyenDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.xacNhanCapPhatTaiNguyen(dto.getP_capphat_id()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thực hiện cấp phát tài nguyên")
    @RequestMapping(value = "/thuc-hien-cap-phat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> thucHienCapPhatTaiNguyen(@RequestBody CapPhatTaiNguyenDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.thucHienCapPhatTaiNguyen(dto.getP_capphat_id()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
