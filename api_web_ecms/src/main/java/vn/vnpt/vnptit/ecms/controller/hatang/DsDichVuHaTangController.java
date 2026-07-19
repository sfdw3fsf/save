package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchDvhtDTO;
import vn.vnpt.vnptit.ecms.dto.idc.DongBoDichvuHtDto;
import vn.vnpt.vnptit.ecms.service.hatang.DsDichVuHaTangService;
import vn.vnpt.vnptit.ecms.service.idc.DichvuHtService;

import javax.validation.Valid;

@RestController
@RequestMapping("/ds-dichvu-hatang")
public class DsDichVuHaTangController {
    private final DsDichVuHaTangService dsDichVuHaTangService;
    DsDichVuHaTangController(DsDichVuHaTangService dsDichVuHaTangService){
        this.dsDichVuHaTangService = dsDichVuHaTangService;
    }

    @Operation(summary = "Lấy danh sách dịch vụ hạ tầng")
    @RequestMapping(value = "get-dsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSach(@Valid @RequestBody SearchDvhtDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsDichVuHaTangService.getDsach(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa dịch vụ hạ tầng")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@RequestBody DataKeyInput input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsDichVuHaTangService.delete(input.getId()));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ dịch vụ hạ tầng hạ tầng theo từng tham số nội bộ")
    @RequestMapping(value = "sync", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> sync(@RequestBody DongBoDichvuHtDto input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsDichVuHaTangService.sync(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
