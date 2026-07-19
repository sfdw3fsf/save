package vn.vnpt.vnptit.ecms.controller.danhmuc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.LoaiEWSDtoIn;
import vn.vnpt.vnptit.ecms.dto.LoaiEWSUpdDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiEWSDService;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("danhmuc/loaiewsd")
public class LoaiEWSDController {
    private final LoaiEWSDService loaiEWSDService;

    public  LoaiEWSDController(LoaiEWSDService loaiEWSDService){
        this.loaiEWSDService = loaiEWSDService;
    }

    @Operation(summary = "Liệt kê danh sách loại EWSD")
    @RequestMapping(value = "/dsloaiewsd", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDs() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiEWSDService.DsLoaiEWSD());
        } catch (AppSqlException e) {
            // TODO Auto-generated catch block
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách loại EWSD theo ID")
    @RequestMapping(value = "/dsloaiewsdtheoid", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsTheoID(@RequestParam("loai_ewsd_id") Long loai_dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiEWSDService.DsLoaiEWSDTheoID(loai_dslam_id));
        } catch (AppSqlException e) {
            // TODO Auto-generated catch block
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới loại EWSD")
    @RequestMapping(value = "/them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> add(@Valid @RequestBody LoaiEWSDtoIn loaiEWSDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiEWSDService.themLoaiEWSD(loaiEWSDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật loại EWSD")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat(@Valid @RequestBody LoaiEWSUpdDtoIn loaiEWSDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiEWSDService.capnhatLoaiEWSD(loaiEWSDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa loại EWSD")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@RequestParam("loai_ewsd_id") String loai_ewsd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(loaiEWSDService.xoa_loai_ewsd(loai_ewsd_id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


}
