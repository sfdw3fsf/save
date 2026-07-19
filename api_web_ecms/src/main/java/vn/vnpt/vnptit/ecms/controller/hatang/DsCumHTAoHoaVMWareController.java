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
import vn.vnpt.vnptit.ecms.dto.hatang.SearchCHTAoHoaDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DsCumHTAoHoaVMWareService;

@RestController
@RequestMapping("ds-cumht-aohoa-vmware")
public class DsCumHTAoHoaVMWareController {
    private final DsCumHTAoHoaVMWareService dsCumHTAoHoaVMWareService;
    DsCumHTAoHoaVMWareController(DsCumHTAoHoaVMWareService dsCumHTAoHoaVMWareService){
        this.dsCumHTAoHoaVMWareService = dsCumHTAoHoaVMWareService;
    }

    @Operation(summary = "Lấy danh sách cụm hạ tầng ảo hóa VMWare")
    @RequestMapping(value = "get-dsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSach(@RequestBody SearchCHTAoHoaDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsCumHTAoHoaVMWareService.getDsach(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa cụm hạ tầng ảo hóa VMWare")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@RequestBody DataKeyInput input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsCumHTAoHoaVMWareService.delete(input.getId()));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ cụm hạ tầng ảo hóa VMWare")
    @RequestMapping(value = "sync", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> syncCHT(@RequestBody DongBoInputDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsCumHTAoHoaVMWareService.sync(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
