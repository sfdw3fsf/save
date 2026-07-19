package vn.vnpt.vnptit.qlsc.controller.cntt;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.dto.cntt.*;
import vn.vnpt.vnptit.qlsc.service.cntt.CapNhatKQXuLySuCoCNTTService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/capnhat-kqxl-cntt")
public class CapNhatKQXuLySuCoCNTTController {

    @Autowired
    CapNhatKQXuLySuCoCNTTService service;

    @Operation(summary = "Lấy danh mục")
    @RequestMapping(value = "/get-danhmuc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhmuc(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDSDanhMuc(input.get("ds")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục khác")
    @RequestMapping(value = "/get-danhmuc-khac", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhmuc_khac(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDSDanhMucKhac(input.get("table"), input.get("conds")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách phiếu", description = "Danh sách phiếu")
    @RequestMapping(value = "/danhsach-phieu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ds_phieu_dieuto(@RequestBody @Valid PhieuDieuCnttToInputDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ds_phieu_dieuto(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách phần tử mạng", description = "Danh sách phần tử mạng")
    @RequestMapping(value = "/danhsach-phantu-mang-cntt", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_phantu_mang(@RequestParam("phanvung_id") int phanvung_id, @RequestParam("phieu_id") int phieu_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ds_phantu_mang(phanvung_id, phieu_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách giao việc nhân viên", description = "Danh sách giao việc nhân viên")
    @RequestMapping(value = "/danhsach-giaoviec-nv-cntt", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_giaoviec_nv(@RequestParam("phanvung_id") int phanvung_id, @RequestParam("phieu_id") int phieu_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ds_giaoviec(phanvung_id, phieu_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật ghi chú phiếu xử lý", description = "Cập nhật ghi chú phiếu xử lý")
    @RequestMapping(value = "/capnhat-ghichu-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_ghichu(@RequestBody @Valid GhiChuPXLCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.capnhat_ghichu(input.getphanvung_id(), input.getphieu_id(),input.getghi_chu())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Trả phiếu sự cố")
    @RequestMapping(value = "/traphieu-suco-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> traphieu_suco(@RequestBody @Valid TraPhieuSuCoCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.traphieu_suco(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Giao việc nhân viên")
    @RequestMapping(value = "/giaoviec-nv-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaoviec_nv(@RequestBody GiaoViecNvCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.xuLySMSGiaoViecNV(input)));
            //rs.setData(service.giaoviec_nv(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /*
        COMMENTS
    * */

    @Operation(summary = "Lấy danh sách comments")
    @RequestMapping(value = "/ds-comment-cntt", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_comment(@RequestParam("phanvung_id_sc") int phanvung_id_sc, @RequestParam("suco_id") int suco_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ds_comments(phanvung_id_sc, suco_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Add comment")
    @RequestMapping(value = "/add-comment-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> add_comment(@RequestBody CommentCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.add_comment(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update comment")
    @RequestMapping(value = "/update-comment-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update_comment(@RequestBody CommentCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.update_comment(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Delete comment")
    @RequestMapping(value = "/delete-comment-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete_comment(@RequestBody CommentCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.delete_comment(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /*
        Dịch vụ cần xử lý (giống PTM)
    * */
    @Operation(summary = "Cập nhật ghi chú dịch vụ cần xử ly")
    @RequestMapping(value = "/capnhat-ghichu-dichvu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_ghichu_dichvu(@RequestBody @Valid GhiChuXuLyDichVuInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.capnhat_ghichu_dichvu(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Cập nhật báo tồn")
    @RequestMapping(value = "/capnhat-baoton-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_baoton(@RequestBody @Valid CapNhatBaoTonCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.capnhat_baoton(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hủy báo tồn")
    @RequestMapping(value = "/huy-baoton-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> huy_baoton(@RequestBody @Valid HuyBaoTonCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.huy_baoton(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoàn công phiếu xử lý", description = "Hoàn công phiếu xử lý")
    @RequestMapping(value = "/hoan-cong", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> hoan_cong(@RequestBody @Valid HoanCongPXLCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.hoan_cong(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy danh sách tình trạng MVT")
    @RequestMapping(value = "/ds-tinhtrang-dv-cntt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTinhTrangDVCNTT(@Valid @RequestBody TinhTrangDichVuCNTTInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsTinhTrangDichVuCNTT(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhân viên xử lý", description = "Danh sách nhân viên xử lý")
    @RequestMapping(value = "/ds-nhanvien-xuly", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_nhanvien_xl(@RequestParam("phanvung_id") int phanvung_id,@RequestParam("phieu_id") int phieu_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ds_nhanvien_xuly(phanvung_id,phieu_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Hủy giao việc nhân viên")
    @RequestMapping(value = "/huy-giaoviec-nv", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> huy_giaoviec_nv(@RequestParam("giaoviec_id") Long giaoViecId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.xuLySMSHuyGiaoViec(giaoViecId));
            //rs.setData(service.huy_giaoviec_nv(giaoViecId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật ảnh chụp")
    @RequestMapping(value = "/capnhat-anhchup-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_anhchup_cntt(@RequestBody CapNhatAnhChupCNTTDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.capnhat_anhchup_cntt(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

@Operation(summary = "Delete file đính kèm comment")
    @RequestMapping(value = "/delete-file-comment-cntt", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete_file_comment(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.delete_file_comment(Long.parseLong(input.get("phanvung_id")), Long.parseLong(input.get("file_id"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
