package vn.vnpt.vnptit.qlsc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.service.CauTrucMangBangRongService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("mang_bangrong")
public class CauTrucMangBangRongController {
   private final CauTrucMangBangRongService cauTrucMangBangRongService;
   public CauTrucMangBangRongController(CauTrucMangBangRongService cauTrucMangBangRongService){
      this.cauTrucMangBangRongService = cauTrucMangBangRongService;
   }
   @Operation(summary = "Nạp giản đồ")
   @RequestMapping(value = "/napgiando", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> NapGianDo(@Valid  @RequestBody CauTrucMangBangRongDtoIn.GianDo input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.NapGianDo(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "tao doi tuong")
   @RequestMapping(value = "/taodoituong", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> TaoDoiTuong(@Valid  @RequestBody CauTrucMangBangRongDtoIn.DoiTuongIns input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.TaoDoiTuong(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "capnhatdoituong")
   @RequestMapping(value = "/capnhatdoituong", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> CapNhatDoiTuong(@Valid  @RequestBody CauTrucMangBangRongDtoIn.DoiTuongUpd input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.CapNhatDoiTuong(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "xóa đối tượng")
   @RequestMapping(value = "/xoadoituong", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> XoaDoiTuong(@Valid  @RequestBody CauTrucMangBangRongDtoIn.DoiTuongUpd input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.XoaDoiTuong(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "tạo liên kết")
   @RequestMapping(value = "/taolienket", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> TaoLienKet(@Valid  @RequestBody CauTrucMangBangRongDtoIn.LienketIns input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.TaoLienKet(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "cap nhat lien ket")
   @RequestMapping(value = "/capnhatlienket", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> CapNhatLienKet(@Valid  @RequestBody CauTrucMangBangRongDtoIn.LienketUpd input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.CapNhatLienKet(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "xoa lien ket")
   @RequestMapping(value = "/xoalienket", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> XoaLienKet(@Valid  @RequestBody CauTrucMangBangRongDtoIn.LienketUpd input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.XoaLienKet(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "tao vong")
   @RequestMapping(value = "/taovong", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> TaoVong(@Valid  @RequestBody CauTrucMangBangRongDtoIn.VongIns input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.TaoVong(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "cap nhat vong")
   @RequestMapping(value = "/capnhatvong", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> CapNhatVong(@Valid  @RequestBody CauTrucMangBangRongDtoIn.VongUpd input) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.CapNhatVong(phanvungId,input));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "xóa vòng")
   @RequestMapping(value = "/xoavong/{id}", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> XoaVong(@PathVariable int id) {
      ApiResult rs = new ApiResult();
      try {
         int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
         rs.setData(cauTrucMangBangRongService.XoaVong(phanvungId,id));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "tỷ lệ in")
   @RequestMapping(value = "/tyle_in", method = RequestMethod.GET)
   public ResponseEntity<ApiResult> TyLeIn() {
      ApiResult rs = new ApiResult();
      try { ;
         rs.setData(cauTrucMangBangRongService.TyLeIn());
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "Lấy danh sách tuyến tryền dẫn")
   @RequestMapping(value = "/ds-tuyen-truyendan", method = RequestMethod.GET)
   public ResponseEntity<ApiResult> dsTuyenTruyenDan() {
      ApiResult rs = new ApiResult();
      try { ;
         rs.setData(cauTrucMangBangRongService.dsTuyenTruyenDan());
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "Lấy danh sách loại tuyến tryền dẫn - form Quản lý tuyến truyền dẫn")
   @RequestMapping(value = "/ds-loaituyen-td", method = RequestMethod.GET)
   public ResponseEntity<ApiResult> dsLoaiTuyenTruyenDan() {
      ApiResult rs = new ApiResult();
      try { ;
         rs.setData(cauTrucMangBangRongService.dsLoaiTuyenTruyenDan());
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "Thêm tuyến tryền dẫn")
   @RequestMapping(value = "/them-tuyen-truyendan", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> themTuyenTruyenDan(@Valid @RequestBody ThemTuyenTruyenDan tuyenTruyenDan ) {
      ApiResult rs = new ApiResult();
      try { ;
         rs.setData(cauTrucMangBangRongService.themTuyenTruyenDan(tuyenTruyenDan));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "Cập nhật tuyến tryền dẫn")
   @RequestMapping(value = "/capnhat-tuyen-truyendan", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> capnhatTuyenTruyenDan(@Valid @RequestBody CapNhatTuyenTruyenDan tuyenTruyenDan ) {
      ApiResult rs = new ApiResult();
      try { ;
         rs.setData(cauTrucMangBangRongService.capnhatTuyenTruyenDan(tuyenTruyenDan));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }
   @Operation(summary = "Xóa tuyến tryền dẫn")
   @RequestMapping(value = "/xoa-tuyen-truyendan", method = RequestMethod.POST)
   public ResponseEntity<ApiResult> xoaTuyenTruyenDan(@Valid @RequestBody XoaTuyenTruyenDan tuyenTruyenDan) {
      ApiResult rs = new ApiResult();
      try {
         rs.setData(new ResultOutput(cauTrucMangBangRongService.xoaTuyenTruyenDan(tuyenTruyenDan)));
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }

   @Operation(summary = "lấy loại liên kết")
   @RequestMapping(value = "/lay-loai-lk", method = RequestMethod.GET)
   public ResponseEntity<ApiResult> layLoaiLk() {
      ApiResult rs = new ApiResult();
      try {
         rs.setData(cauTrucMangBangRongService.layLoaiLk());
      } catch (AppSqlException e) {
         rs.setException(e);
      }
      return rs.getResponseEntity();
   }

   @Operation(summary = "CapNhatListDoiTuong")
   @RequestMapping(value = "/capNhatListDoiTuong" ,produces = { "application/json" }, method = RequestMethod.POST)
   public ResponseEntity<ApiResult> CapNhatListDoiTuong( @Valid @RequestBody List<DoiTuongUpdate> input) {
      ApiResult rs = new ApiResult();
      try {
         rs.setData(cauTrucMangBangRongService.CapNhatListDoiTuong(input));
      } catch (AppSqlException e) {
         rs.setException(e);
      } catch (JsonProcessingException ignored){}

      return rs.getResponseEntity();
   }
}
