package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.DanhMucLoaiBackupDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDanhMuc;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDanhMuc;
import vn.vnpt.vnptit.ecms.dto.idc.HaTangCnttThongTinChungDTO;
import vn.vnpt.vnptit.ecms.service.idc.CapPhatCongSuatDienService;
import vn.vnpt.vnptit.ecms.service.idc.HaTangCNTTService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("idc/hatang-cntt")
public class HaTangCNTTController {

    @Autowired
    HaTangCNTTService _service;

    @Operation(summary = "Kiểm tra user có phải tổ trưởng không")
    @RequestMapping(value = "check-is-to-truong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> checkIsToTruong( ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.checkIsToTruong());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách mảng thiết bị")
    @RequestMapping(value = "danhmuc/mang-thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachMangThietBi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachMangThietBi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách hệ thống theo  mảng thiết bị")
    @RequestMapping(value = "danhmuc/he-thong-thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachHeThongThietBi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachHeThongThietBi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Liệt kê danh sách loai thiết bị")
    @RequestMapping(value = "danhmuc/loai-thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachLoaiThietBi(@RequestParam("mang_thietbi_id") long thietbiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachLoaiThietBi(thietbiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Liệt kê danh sách phân loai thiết bị theo loại thiết bị")
    @RequestMapping(value = "danhmuc/phan-loai-thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachPhanLoaiThietBi(@RequestParam("loai_thietbi_id") long loaiThietbiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachPhanLoaiThietBi(loaiThietbiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Liệt kê danh sách chủng loai thiết bị")
    @RequestMapping(value = "danhmuc/chung-loai-thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachChungLoaiThietBi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachChungLoaiThietBi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Liệt kê danh sách chủ quản sở hữu thiết bị")
    @RequestMapping(value = "danhmuc/chu-quan-so-huu-thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachChuQuanThietBi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachChuQuanThietBi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách loại khách hàng")
    @RequestMapping(value = "danhmuc/loai-khach-hang", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachLoaiKhachHang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachLoaiKhachHang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách trạng thái vận hành")
    @RequestMapping(value = "danhmuc/trang-thai-van-hanh", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachTrangThaiVanHanh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachTrangThaiVanHanh());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Liệt kê danh sách hãng sản xuất")
    @RequestMapping(value = "danhmuc/hang-san-xuat", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachHangSanXuat() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachHangSanXuat());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách nhà cung cấp")
    @RequestMapping(value = "danhmuc/nha-cung-cap", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachNhaCungCap() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachNhaCungCap());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Liệt kê danh sách hệ điều hành")
    @RequestMapping(value = "danhmuc/he-dieu-hanh", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachHeDieuHanh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachHeDieuHanh());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }




    @Operation(summary = "Tìm kiếm thiết bị")
    @RequestMapping(value = "thiet-bi/tim-kiem", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> TimKiemThietBi( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        try {
            //rs.setData(obj);
            rs.setData(_service.TimKiemThietBi(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }



    @Operation(summary = "Thêm mới thông tin chung thiết bị")
    @RequestMapping(value = "thiet-bi/them-moi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemMoiThietBi( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        Long thietBiId = 0L;
        try {

            List<Map<String, Object>> list_rs = _service.ThemMoiThietBi(obj);
            rs.setData(list_rs);
            if (list_rs != null && !list_rs.isEmpty()) {
                Object idObj = list_rs.get(0).get("ID");
                thietBiId = Long.valueOf(idObj.toString());
            }
            if(obj.getLoaiTbiId() == 9197){

                // nếu là loại rpdu thì thêm danh sách ổ cắm
                List<HaTangCnttThongTinChungDTO.ThongTinOCamDTO> list  = obj.getDanhSachOCam();
                for(HaTangCnttThongTinChungDTO.ThongTinOCamDTO tt : list){

                    _service.CapNhatThemMoiOCam(thietBiId,tt.getLoaiOCamId(),tt.getTen(),tt.getTrangThaiOCam(),tt.getTrangThaiKetNoi(),tt.getThietBiKetNoiId());
                }

            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Sửa thông tin chung thiết bị")
    @RequestMapping(value = "thiet-bi/sua", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> SuaThietBi( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        try {

            rs.setData(_service.CapNhatThietBi(obj));
            if(obj.getLoaiTbiId() == 9197){
                // nếu là loại rpdu thì thêm danh sách ổ cắm
                List<HaTangCnttThongTinChungDTO.ThongTinOCamDTO> list  = obj.getDanhSachOCam();
                for(HaTangCnttThongTinChungDTO.ThongTinOCamDTO tt : list){
                    _service.CapNhatThemMoiOCam(obj.getId(),tt.getLoaiOCamId(),tt.getTen(),tt.getTrangThaiOCam(),tt.getTrangThaiKetNoi(),tt.getThietBiKetNoiId());
                }
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xóa thông tin chung thiết bị")
    @RequestMapping(value = "thiet-bi/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaThietBi( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.XoaThietBi(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "danh sách khách hàng")
    @RequestMapping(value = "thiet-bi/danh-sach-khach-hang", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DANHSACH_KHACHHANG( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DANHSACH_KHACHHANG(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "thêm/sửa tài sản")
    @RequestMapping(value = "thiet-bi/cap-nhat-tai-san", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatTaiSan( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.CapNhatTaiSan(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "chi tiết tài sản")
    @RequestMapping(value = "thiet-bi/chi-tiet-tai-san", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ChiTietTaiSan( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ChiTietTaiSan(obj.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "chi tiết thiết bị - thông tin chung")
    @RequestMapping(value = "thiet-bi/chi-tiet-thiet-bi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ChiTietThietBi( @RequestBody HaTangCnttThongTinChungDTO obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ChiTietThietBi(obj.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "danh mục loại ổ cắm")
    @RequestMapping(value = "danhmuc/loai-o-cam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachLoaiOCam( ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachLoaiOCam());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "danh mục thiết bị kết nối")
    @RequestMapping(value = "danhmuc/thiet-bi-ket-noi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachThietBiKetNoi( ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhSachThietBiKetNoi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "danh mục tab tài sản idc")
    @RequestMapping(value = "danhmuc/idc-common", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhMucIDC( @RequestParam("loaiDanhMuc") String loaiDanhMuc, @RequestParam("thamSo1") Long thamSo1) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DanhMucIDC(loaiDanhMuc,thamSo1));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
