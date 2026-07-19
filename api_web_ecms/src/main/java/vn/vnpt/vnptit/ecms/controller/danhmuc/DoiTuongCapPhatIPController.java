package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DoiTuongCapPhatIPDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.DoiTuongCapPhatIPService;

import javax.validation.Valid;

@RestController
@RequestMapping("/doituong-capphat-ip")
public class DoiTuongCapPhatIPController {

    @Autowired
    DoiTuongCapPhatIPService doiTuongCapPhatIPService;

    @Operation(summary = "Lấy danh sách đối tượng cấp phát IP")
    @RequestMapping(value = "/danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiTuongCapPhatIPService.layDs());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update đối tượng cấp phát IP")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao(@Valid @RequestBody DoiTuongCapPhatIPDtoIn input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(doiTuongCapPhatIPService.updateInsert(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa đối tượng cấp phát ip")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@Valid @RequestBody DataKeyInput item){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(doiTuongCapPhatIPService.xoa(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
