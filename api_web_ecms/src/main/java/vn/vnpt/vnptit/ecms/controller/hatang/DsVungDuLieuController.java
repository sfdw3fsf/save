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
import vn.vnpt.vnptit.ecms.dto.hatang.DongBoInputDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchVungDLDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DsVungDLService;

@RestController
@RequestMapping("ds-vung-dl")
public class DsVungDuLieuController {
    private final DsVungDLService dsVungDLService;
    DsVungDuLieuController(DsVungDLService dsVungDLService){ this.dsVungDLService = dsVungDLService;}

    @Operation(summary = "Lấy danh sách vùng dữ liệu")
    @RequestMapping(value = "get-dsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsach(@RequestBody SearchVungDLDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsVungDLService.getDsach(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa vùng dữ liệu")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@RequestBody DataKeyInput input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsVungDLService.delete(input.getId()));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ vùng dữ liệu")
    @RequestMapping(value = "sync", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> syncVungDl(@RequestBody DongBoInputDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsVungDLService.syncVungDl(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
