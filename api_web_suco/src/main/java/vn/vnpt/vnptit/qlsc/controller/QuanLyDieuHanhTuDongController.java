package vn.vnpt.vnptit.qlsc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.CapNhatChuKyNhanTinDtoInp;
import vn.vnpt.vnptit.qlsc.dto.NhanVienNhanSMSDtoInp;
import vn.vnpt.vnptit.qlsc.dto.QuanLyDieuHanhTuDongInputDto;
import vn.vnpt.vnptit.qlsc.service.QuanLyDieuHanhTuDongService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("danhmuc/dieuhanhtudong")
public class QuanLyDieuHanhTuDongController {
    @Autowired
    private QuanLyDieuHanhTuDongService quanLyDieuHanhTuDongService;

    @Operation(summary = "Lấy danh sách loại tinh nhắn")
    @GetMapping("ds-loai-tn")
    public ResponseEntity<ApiResult> Get_DS_Loai_TinNhan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_Loai_TinNhan());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách cấp tinh nhắn")
    @PostMapping("ds-cap-tn")
    public ResponseEntity<ApiResult> Get_DS_Cap_TinNhan(@RequestBody QuanLyDieuHanhTuDongInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_Cap_TinNhan(input.getLoaitn_id()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị")
    @GetMapping("ds-donvi")
    public ResponseEntity<ApiResult> Get_DS_Cap_TinNhan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_Donvi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhân viên đã gán có phân trang")
    @PostMapping("ds-nv-dagan-paging")
    public ResponseEntity<ApiResult> Get_DS_NV_DaGan_Paging(@Valid @RequestBody NhanVienNhanSMSDtoInp input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_NV_DaGan_Paging(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhân viên chưa gán có phân trang")
    @PostMapping("ds-nv-chuagan-paging")
    public ResponseEntity<ApiResult> Get_DS_NV_ChuaGan_Paging(@Valid @RequestBody NhanVienNhanSMSDtoInp input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_NV_ChuaGan_Paging(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật nhân viên nhận sms")
    @PostMapping("capnhat-nv-sms")
    public ResponseEntity<ApiResult> Update_NV_SMS(@RequestBody QuanLyDieuHanhTuDongInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Update_NV_SMS(input.getTinnhan_id(), input.getDsnv_id()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật đơn vị nhận sms")
    @PostMapping("capnhat-donvi-sms")
    public ResponseEntity<ApiResult> Update_DonVi_SMS(@RequestBody QuanLyDieuHanhTuDongInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Update_DonVi_SMS(input.getTinnhan_id(), input.getNhanvien_id(), input.getDonvi_id()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách mức độ sự cố")
    @GetMapping("ds-mucdo-sc")
    public ResponseEntity<ApiResult> Get_DS_MucDoSC() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_MucDoSC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy mức độ sự cố")
    @GetMapping("mucdo-sc/{id}")
    public ResponseEntity<ApiResult> Get_DS_MucDoSC_ById(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_MucDoSC_ById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mức độ sự cố")
    @PostMapping("them-mucdo-sc")
    public ResponseEntity<ApiResult> Insert_MucDo_SC(@RequestBody QuanLyDieuHanhTuDongInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Insert_MucDo_SC(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật mức độ sự cố")
    @PostMapping("capnhat-mucdo-sc/{id}")
    public ResponseEntity<ApiResult> Update_MucDo_SC(@RequestBody QuanLyDieuHanhTuDongInputDto input, @PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            input.setMucdo_id(id);
            rs.setData(quanLyDieuHanhTuDongService.Update_MucDo_SC(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa mức độ sự cố")
    @PostMapping("xoa-mucdo-sc/{id}")
    public ResponseEntity<ApiResult> Delete_MucDo_SC(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Delete_MucDo_SC(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách chu kỳ nhắn tin")
    @GetMapping("chuky-nt")
    public ResponseEntity<ApiResult> Get_DS_CHUKY() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_CHUKY());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm chu kỳ nhắn tin")
    @PostMapping("them-chuky-nt")
    public ResponseEntity<ApiResult> Insert_DS_CHUKY(@RequestBody QuanLyDieuHanhTuDongInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Insert_DS_CHUKY(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật chu kỳ nhắn tin")
    @PostMapping("capnhat-chuky-nt")
    public ResponseEntity<ApiResult> Update_DS_CHUKY(@Valid @RequestBody List<CapNhatChuKyNhanTinDtoInp> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(quanLyDieuHanhTuDongService.Update_DS_CHUKY(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật chu kỳ nhắn tin")
    @PostMapping("xoa-chuky-nt/{id}")
    public ResponseEntity<ApiResult> Delete_DS_CHUKY(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Delete_DS_CHUKY(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhân viên đã gán")
    @PostMapping("ds-nv-dagan")
    public ResponseEntity<ApiResult> Get_DS_NV_DaGan(@RequestBody QuanLyDieuHanhTuDongInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_NV_DaGan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhân viên chưa gán")
    @PostMapping("ds-nv-chuagan")
    public ResponseEntity<ApiResult> Get_DS_NV_ChuaGan(@RequestBody QuanLyDieuHanhTuDongInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDieuHanhTuDongService.Get_DS_NV_ChuaGan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
