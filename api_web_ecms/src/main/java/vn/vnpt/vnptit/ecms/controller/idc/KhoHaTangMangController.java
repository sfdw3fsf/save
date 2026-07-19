package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.vnptit.ecms.service.idc.KhoHaTangMangService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("quanly-kho-ha-tang-mang")
public class KhoHaTangMangController {

    private final KhoHaTangMangService service;

    public KhoHaTangMangController(KhoHaTangMangService service) {
        this.service = service;
    }

    // ===========================================
    // GET DETAIL
    // ===========================================
    @GetMapping("/{id}/detail")
    @ApiOperation(value = "Lấy chi tiết hạ tầng mạng")
    public ResponseEntity<ApiResult> getDetail(@PathVariable Long id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getDetail(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // GET LIST
    // ===========================================
    @PostMapping("/get-all")
    @ApiOperation(value = "Lấy danh sách hạ tầng mạng")
    public ResponseEntity<ApiResult> getAll(@RequestBody SearchHaTangMangDto dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getAll(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // INSERT / UPDATE
    // ===========================================
    @PostMapping("/insert-update")
    @ApiOperation(value = "Thêm hoặc cập nhật hạ tầng mạng")
    public ResponseEntity<ApiResult> insertOrUpdate(@RequestBody @Valid UpsertHaTangMangDto dto) {
        ApiResult res = new ApiResult();
        try {
            res = service.insertOrUpdate(dto);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // DELETE SINGLE
    // ===========================================
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Xoá hạ tầng mạng")
    public ResponseEntity<ApiResult> delete(@PathVariable Long id) {
        ApiResult res = new ApiResult();
        try {
            res = service.delete(id);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // DELETE MULTI
    // ===========================================
    @PostMapping("/delete-multi")
    @ApiOperation(value = "Xoá nhiều hạ tầng mạng")
    public ResponseEntity<ApiResult> deleteMulti(@RequestBody Map<String, Object> payload) {
        ApiResult res;
        try {
            List<?> ids = (List<?>) payload.get("ids");
            String listId = ids.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            res = service.deleteMulti(listId);
        } catch (AppSqlException e) {
            ApiResult err = new ApiResult();
            err.setException(e);
            return err.getResponseEntity();
        }
        return res.getResponseEntity();
    }
    //check tên hạ tầng mạng đã tồn tại
    @PostMapping("/check-ten")
    @ApiOperation(value = "Kiểm tra tên hạ tầng mạng đã tồn tại")
    public ResponseEntity<ApiResult> checkTenHaTang(@RequestBody CheckTenHaTangDto dto) {

        ApiResult res = new ApiResult();
        try {
            res = service.checkTenHaTangTonTai(dto.getId(), dto.getTen());
        } catch (AppSqlException e) {
            res.setException(e);
        }

        return res.getResponseEntity();
    }
    //check mã hạ tầng mạng đã tồn tại
    @PostMapping("/check-ma")
    @ApiOperation(value = "Kiểm tra mã hạ tầng mạng đã tồn tại")
    public ResponseEntity<ApiResult> checkMaHaTang(@RequestBody CheckMaHaTangDto dto) {

        ApiResult res = new ApiResult();
        try {
            res = service.checkMaHaTangTonTai(dto.getId(), dto.getMaHaTang());
        } catch (AppSqlException e) {
            res.setException(e);
        }

        return res.getResponseEntity();
    }
    // ===========================================
    // GET SUBNET BY HTM
    // ===========================================
    @GetMapping("/{htmId}/subnets")
    @ApiOperation(value = "Lấy danh sách subnet theo hạ tầng mạng")
    public ResponseEntity<ApiResult> getSubnetsByHtm(
            @PathVariable("htmId") Long htmId) {

        ApiResult res = new ApiResult();
        try {
            res.setData(service.getSubnetByHaTangMangId(htmId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // GET NHÂN VIÊN THEO HẠ TẦNG MẠNG
    // ===========================================
    @GetMapping("/{htmId}/nhanvien")
    @ApiOperation(value = "Lấy danh sách nhân viên theo hạ tầng mạng")
    public ResponseEntity<ApiResult> getNhanVienHtm(@PathVariable("htmId") Long htmId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getNhanVienByHtmId(htmId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // LƯU NHÂN VIÊN THEO HẠ TẦNG MẠNG
    // ===========================================
    @PostMapping("/{htmId}/nhanvien")
    @ApiOperation(value = "Lưu danh sách nhân viên quản lý hạ tầng mạng")
    public ResponseEntity<ApiResult> saveNhanVienHtm(
            @PathVariable("htmId") Long htmId,
            @RequestBody List<NhanVienHTMItem> employees) {

        ApiResult res = new ApiResult();
        try {
            res = service.saveNhanVienForHtm(htmId, employees);
        } catch (AppSqlException e) {
            res.setException(e);
        }

        return res.getResponseEntity();
    }
    //get ipaddress theo hạ tầng mạng
    @GetMapping("/{htmId}/ipaddress")
    @ApiOperation(value = "Lấy danh sách địa chỉ IP theo hạ tầng mạng")
    public ResponseEntity<ApiResult> getIpAddressByHtm(@PathVariable("htmId") Long htmId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getIpAddressByHtmId(htmId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    //get thiet bi co the dat vao ha tang mang
    @GetMapping("/{htmId}/thietbi-available")
    @ApiOperation(value = "Lấy danh sách thiết bị có thể đặt vào hạ tầng mạng")
    public ResponseEntity<ApiResult> getThietBiAvailableByHtm(@PathVariable("htmId") Long htmId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getThietBiMangByIdcHtm(htmId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    //get thiet bi da dat vao ha tang mang
    @GetMapping("{htmId}/thietbi")
    @ApiOperation(value = "Lấy danh sách thiết bị đã đặt vào hạ tầng mạng")
    public ResponseEntity<ApiResult> getThietBiByHtm(@PathVariable("htmId") Long htmId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getThietBiMangByHaTangMang(htmId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    //update thiet bi ha tang mang
    @PostMapping("/thietbi/update")
    @ApiOperation(value = "Cập nhật thiết bị hạ tầng mạng")
    public ResponseEntity<ApiResult> updateThietBiHaTangMang(@RequestBody @Valid UpdateThietBiHaTangMangDto dto) {
        ApiResult res = new ApiResult();
        try {
            res = service.updateThietBiHaTangMang(dto);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    @GetMapping("/{htmId}/lichsu-thietbi")
    @ApiOperation(value = "Lấy lịch sử thay đổi thiết bị hạ tầng mạng")
    public ResponseEntity<ApiResult> getLichSuThietBiHaTangMang(@PathVariable("htmId") Long htmId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getLichSuThietBiHaTangMang(htmId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

}