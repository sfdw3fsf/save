package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.DanhMucPhanLoaiHaTangDtoIn;
import vn.vnpt.vnptit.ecms.dto.IPStoreDtoIn;
import vn.vnpt.vnptit.ecms.dto.KhayDiaTrongTuDia;
import vn.vnpt.vnptit.ecms.service.DanhMucPhanLoaiHaTangService;

@RestController
@RequestMapping("danhmuc-phanloai-hatang")
public class DanhMucPhanLoaiHaTang {
    @Autowired
    private DanhMucPhanLoaiHaTangService danhMucPhanLoaiHaTangService;

    @Operation(summary = "Thêm danh mục phân loại hạ tầng")
    @PostMapping("insert-phanloai-hatang")
    public ResponseEntity<ApiResult> insertPhanLoaiHaTang(@Valid @RequestBody DanhMucPhanLoaiHaTangDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty()) {
            rs.setException(new AppSqlException("Tên phân loại hạ tầng không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucPhanLoaiHaTangService.insertPhanLoaiHaTang(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật danh mục phân loại hạ tầng")
    @PostMapping("update-phanloai-hatang")
    public ResponseEntity<ApiResult> updatePhanLoaiHaTang(@Valid @RequestBody DanhMucPhanLoaiHaTangDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty()) {
            rs.setException(new AppSqlException("Tên phân loại hạ tầng không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucPhanLoaiHaTangService.updatePhanLoaiHaTang(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục phân loại hạ tầng")
    @PostMapping("delete-phanloai-hatang")
    public ResponseEntity<ApiResult> deletePhanLoaiHaTang(@Valid @RequestBody DanhMucPhanLoaiHaTangDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.deletePhanLoaiHaTang(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục phân loại hạ tầng")
    @PostMapping("get-phanloai-hatang")
    public ResponseEntity<ApiResult> getPhanLoaiHaTang(@Valid @RequestBody DanhMucPhanLoaiHaTangDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.getPhanLoaiHaTang(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tất cả danh mục phân loại hạ tầng")
    @PostMapping("get-all-phanloai-hatang")
    public ResponseEntity<ApiResult> getAllPhanLoaiHaTang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.getAllPhanLoaiHaTang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách IP Store")
    @PostMapping("lay-ds-ipstore")
    public ResponseEntity<ApiResult> layDsIPStore() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.layDsIPStore());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách IP Store theo ID")
    @PostMapping("lay-ds-ipstore-theo-id")
    public ResponseEntity<ApiResult> layDsIPStoreTheoId(@Valid @RequestBody IPStoreDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.layDsIPStoreTheoId(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới IP Store")
    @PostMapping("them-moi-ipstore")
    public ResponseEntity<ApiResult> themMoiIPStore(@Valid @RequestBody IPStoreDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.themMoiIPStore(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật IP Store")
    @PostMapping("cap-nhat-ipstore")
    public ResponseEntity<ApiResult> capNhatIPStore(@Valid @RequestBody IPStoreDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.capNhatIPStore(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa IP Store")
    @PostMapping("xoa-ipstore")
    public ResponseEntity<ApiResult> xoaIPStore(@Valid @RequestBody IPStoreDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.xoaIPStore(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy lịch sử cấp phát theo IP Store")
    @PostMapping("lay-lich-su-cap-phat-theo-ipstore")
    public ResponseEntity<ApiResult> layLichSuCapPhatTheoIPStore(@Valid @RequestBody IPStoreDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.getLichSuCapPhatByIPStore(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Khay dia trong tu dia")
    @PostMapping("lay-ds-khay-dia-trong-tu-dia")
    public ResponseEntity<ApiResult> layDsKhayDiaTrongTuDia() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.layDsKhayDiaTrongTuDia());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Khay dia trong tu dia theo ID")
    @PostMapping("lay-ds-khay-dia-trong-tu-dia-theo-id")
    public ResponseEntity<ApiResult> layDsKhayDiaTrongTuDiaTheoId(@Valid @RequestBody KhayDiaTrongTuDia obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.layDsKhayDiaTrongTuDiaTheoId(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới Khay dia trong tu dia")
    @PostMapping("them-moi-khay-dia-trong-tu-dia")
    public ResponseEntity<ApiResult> themMoiKhayDiaTrongTuDia(@Valid @RequestBody KhayDiaTrongTuDia obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.themMoiKhayDiaTrongTuDia(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Khay dia trong tu dia")
    @PostMapping("cap-nhat-khay-dia-trong-tu-dia")
    public ResponseEntity<ApiResult> capNhatKhayDiaTrongTuDia(@Valid @RequestBody KhayDiaTrongTuDia obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.capNhatKhayDiaTrongTuDia(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Khay dia trong tu dia")
    @PostMapping("xoa-khay-dia-trong-tu-dia")
    public ResponseEntity<ApiResult> xoaKhayDiaTrongTuDia(@Valid @RequestBody KhayDiaTrongTuDia obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucPhanLoaiHaTangService.xoaKhayDiaTrongTuDia(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
