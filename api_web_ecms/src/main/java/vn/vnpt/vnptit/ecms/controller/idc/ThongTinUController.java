package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.AddDiskRequestDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.MayAoDto;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.vnptit.ecms.service.hatang.MayAoService;
import vn.vnpt.vnptit.ecms.service.idc.ThongTinUService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "ha-tang/thongtin-u")
public class ThongTinUController {

  private final ThongTinUService service;

  public ThongTinUController(ThongTinUService service) {
    this.service = service;
  }

  @Operation(summary = "Get list U Infor")
  @GetMapping("")
  public ResponseEntity<ApiResult> getListVirtualMachine() {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getListRackUnitByRackId(0));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @PostMapping("/{id}")
  public ResponseEntity<String> updateRackUnit(
          @PathVariable("id") Integer id,
          @RequestBody RackUnitDTO dto
  ) {
    try {
      // set id từ URL path vào DTO
      dto.setRackUnitId(id);

      service.updateRackUnit(dto);

      return ResponseEntity.ok("Cập nhật RackUnit thành công");
    } catch (Exception ex) {
      // log lỗi nếu cần
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Lỗi khi cập nhật RackUnit: " + ex.getMessage());
    }
  }

  @Operation(summary = "Get list idc Infor")
  @GetMapping("/idcList/{donvi_id}")
  public ResponseEntity<ApiResult> getListIdc(
          @PathVariable("donvi_id") int donvi_id
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.idcList(donvi_id));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get list idc Infor")
  @GetMapping("/thietbi-from-u/{u_id}")

  public ResponseEntity<ApiResult> getThietbiFromU(
          @PathVariable("u_id") int uId
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getThietbiFromU(uId));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get list TB Infor")
  @GetMapping("/thietbi/{phieutc_id}")
  public ResponseEntity<ApiResult> getThietbi(
          @PathVariable("phieutc_id") int phieutc_id
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getThietbi(phieutc_id));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get list TB Port Infor")
  @GetMapping("/thietbi-port/{thietbi_id}")
  public ResponseEntity<ApiResult> getThietbiPort(
          @PathVariable("thietbi_id") int thietbi_id
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getThietbiPort(thietbi_id));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get list mat san")
  @GetMapping("/mat-san")
  public ResponseEntity<ApiResult> getMatSan() {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getMatSan());
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get list IDC Infor")
  @GetMapping("/idc")
  public ResponseEntity<ApiResult> getIdc() {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getIdc());
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get Phieu tc")
  @GetMapping("/phieutc/{phieutc_id}")
  public ResponseEntity<ApiResult> getPhieutc(
          @PathVariable("phieutc_id") int phieutc_id
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getPhieutc(phieutc_id));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get Phieu tc Kenh ban")
  @GetMapping("/phieutc-kenhban/{phieutc_id}")
  public ResponseEntity<ApiResult> getPhieutcKenhban(
          @PathVariable("phieutc_id") int phieutc_id
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getPhieutcKenhban(phieutc_id));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get DS Thi cong bang thong")
  @GetMapping("/thicong-bangthong/dsKetqua")
  public ResponseEntity<ApiResult> getDsThicongBangthong(
          @RequestParam("loaiKq") String loaiKq
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getDsKetquaThicongBangthong(loaiKq));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get DS Thi cong bang thong")
  @GetMapping("/thicong-bangthong/{trangthai_id}")
  public ResponseEntity<ApiResult> getDsThicongBangthong(
          @PathVariable("trangthai_id") int trangthai_id,
          @RequestParam("phieutcId") int phieutcId
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getDsThicongBangthong(trangthai_id, phieutcId));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }



  @Operation(summary = "Get DS Thi cong bang thong")
  @GetMapping("/thicong-bangthong-thuhoi/{trangthai_id}")
  public ResponseEntity<ApiResult> getDsThicongBangthongThuhoi(
          @PathVariable("trangthai_id") int trangthai_id
          ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getDsThicongBangthongThuhoi(trangthai_id));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Thêm mới danh sách ThiCongBangThong")
  @PostMapping("/thicong-bangthong")
  public ResponseEntity<ApiResult> createThiCongBangThong(
          @RequestParam("loaiAction") String loaiAction,
          @RequestParam("phieutc") int phieutc,
          @RequestBody List<ThiCongBangThongDto> dtoList
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.createThiCongBangThong(dtoList, loaiAction, phieutc)); // gọi service nhận List
    } catch (Exception e) {
      if (e instanceof AppSqlException) {
        rs.setException((AppSqlException) e);
      } else {
        // Bọc lỗi khác thành AppSqlException để giữ chuẩn
//        rs.setException(new AppSqlException("Lỗi hệ thống", e));
      }
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Cập nhật người xử lý cho phiếu TC")
  @PostMapping("/phieutc/{phieutc_id}/nguoi-xuly")
  public ResponseEntity<ApiResult> updateNguoiXuLy(
          @PathVariable("phieutc_id") int phieutcId,
          @RequestBody Map<String, Object> body
  ) {
    ApiResult rs = new ApiResult();
    try {
      String nguoiXl = (String) body.get("nguoiXl");
      String nguoiCn = (String) body.get("nguoiCn");

      rs.setData(service.updateNguoiXuLy(phieutcId, nguoiXl, nguoiCn));
    } catch (Exception e) {
      rs.setException(new AppSqlException(e));
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Get DS nhan vien cua don vi")
  @GetMapping("/danhsach-nhanvien/{donvi_id}")
  public ResponseEntity<ApiResult> getDsNhanvienDonvi(
          @PathVariable("donvi_id") int donvi_id
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.getDsNhanvienDonvi(donvi_id));
    } catch (Exception e) {
      rs.setException((AppSqlException) e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Cập nhật trạng thái thi công băng thông")
  @PostMapping("/thicong-bangthong/capnhat-trangthai")
  public ResponseEntity<ApiResult> capNhatTrangThai(
          @RequestBody List<CapNhatTrangThaiDto> request
  ) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.capNhatTrangThaiThiCongBt(request));
    } catch (Exception e) {
      if (e instanceof AppSqlException) {
        rs.setException((AppSqlException) e);
      } else {
        rs.setException(new AppSqlException(e));
      }
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Cập nhật trạng thái phiếu thi công")
  @PostMapping("/phieuthicong/{phieutc_id}/capnhat-trangthai")
  public ResponseEntity<ApiResult> capNhatTrangThaiPhieuTc(
          @PathVariable("phieutc_id") int phieutcId,
          @RequestParam("trangThaiId") int trangThaiId,
          @RequestParam("nguoiCn") String nguoiCn) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.capNhatTrangThaiPhieuTc(phieutcId, trangThaiId, nguoiCn));
    } catch (Exception e) {
      if (e instanceof AppSqlException) {
        rs.setException((AppSqlException) e);
      } else {
        rs.setException(new AppSqlException(e));
      }
    }
    return rs.getResponseEntity();
  }


  @PostMapping("/search")
  public ResponseEntity<ApiResult> searchCongViec(@RequestBody DanhSachCongViecDto request) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.timKiemCongViec(request));
    } catch (Exception e) {
      if (e instanceof AppSqlException) {
        rs.setException((AppSqlException) e);
      } else {
        rs.setException(new AppSqlException(e));
      }
    }
    return rs.getResponseEntity();
  }
}
