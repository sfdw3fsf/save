package vn.vnpt.vnptit.ecms.controller.idc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.idc.DoiTuongKhongGianDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDoiTuongDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDto;
import vn.vnpt.vnptit.ecms.service.idc.DoiTuongKhongGianService;

@RestController
@RequestMapping("tainguyen-idc/khonggian")
public class DoiTuongKhongGianController {

    @Autowired
    DoiTuongKhongGianService service;

    @Operation(summary = "Upsert đối tượng không gian")
    @RequestMapping(value = "/upsert-doituong-khonggian", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> upsertDoiTuongKhongGian(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.upsertDoiTuongKhongGian(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Delete đối tượng không gian")
    @RequestMapping(value = "/delete-doituong-khonggian", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteDoiTuongKhongGian(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.deleteDoiTuongKhongGian(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách đối tượng không gian")
    @RequestMapping(value = "/get-ds-doituong-khonggian", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsDoiTuongKhongGian(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.getDsDoiTuongKhongGian(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách đối tượng chưa lắp đặt")
    @RequestMapping(value = "/get-ds-doituong-chualapdat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsDoiTuongChuaLapDat(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.getDsDoiTuongChuaLapDat(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách đối tượng đã lắp đặt")
    @RequestMapping(value = "/get-ds-doituong-dalapdat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsDoiTuongDaLapDat(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.getDsDoiTuongDaLapDat(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update vị trí đối tượng không gian")
    @RequestMapping(value = "/update-vitri-doituong-khonggian", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateVitriDoiTuongKhongGian(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.updateVitriDoiTuongKhongGian(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách vị trí tọa độ trong tập điểm của đối tượng ")
    @PostMapping("/get-toado-doituong")
    public ResponseEntity<ApiResult> getToaDoDoiTuong(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getToaDoDoiTuong(item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm thông tin vị trí tọa độ trong tập điểm của đối tượng ")
    @PostMapping("/insert-toado-doituong")
    public ResponseEntity<ApiResult> insertToaDoDoiTuong(@Valid @RequestBody ToaDoDoiTuongDto item) {
        ApiResult rs = new ApiResult();
        try {
            String msg = "";
            ToaDoDto[] ds = item.getDsToaDo();
            if (ds.length == 0)
                msg = "Danh sách tọa độ không được để trống";
            else {
                for (ToaDoDto toaDo : ds) {
                    if (toaDo == null) {
                        msg = "Danh sách tọa độ có thông tin không hợp lệ";
                        break;
                    }
                }
            }
            if (msg.equals(""))
                rs.setData(new ResultOutput(this.service.insertToaDoDoiTuong(item)));
            else
                rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật thông tin vị trí tọa độ trong tập điểm của đối tượng ")
    @PostMapping("/update-toado-doituong")
    public ResponseEntity<ApiResult> updateToaDoDoiTuong(@Valid @RequestBody ToaDoDoiTuongDto item) {
        ApiResult rs = new ApiResult();
        try {
            String msg = "";
            ToaDoDto[] ds = item.getDsToaDo();
            if (ds.length == 0)
                msg = "Danh sách tọa độ không được để trống";
            else {
                for (ToaDoDto toaDo : ds) {
                    if (toaDo == null) {
                        msg = "Danh sách tọa độ có thông tin không hợp lệ";
                        break;
                    }
                }
            }
            if (msg.equals(""))
                rs.setData(new ResultOutput(this.service.updateToaDoDoiTuong(item)));
            else
                rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa thông tin vị trí tọa độ trong tập điểm của đối tượng ")
    @PostMapping("/delete-toado-doituong")
    public ResponseEntity<ApiResult> deleteToaDoDoiTuong(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(
                    new ResultOutput(this.service.deleteToaDoDoiTuong(item.getLoaiDoiTuong(), item.getDoiTuongId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị CNTT và rPDU chưa lắp đặt trong IDC")
    @PostMapping("/get-ds-thietbi-chualapdat")
    public ResponseEntity<ApiResult> getDsThietBiChuaLapDat(@Valid @RequestBody DoiTuongKhongGianDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.getDsThietBiChuaLapDat(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
