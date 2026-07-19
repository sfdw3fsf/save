package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.DongBoInputDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchCumHTIDGDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DsCumHTIDGService;

@RestController
@RequestMapping("ds-cumht-idg")
public class DsCumHTIDGController {
    private final DsCumHTIDGService dsCumHTIDGService;
    DsCumHTIDGController(DsCumHTIDGService dsCumHTIDGService){ this.dsCumHTIDGService = dsCumHTIDGService;}

    @Operation(summary = "Lấy danh sách cụm hạ tầng IDG")
    @RequestMapping(value = "get-dsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSach(@RequestBody SearchCumHTIDGDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsCumHTIDGService.getDsach(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa cụm hạ tầng IDG")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@RequestBody DataKeyInput input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsCumHTIDGService.delete(input.getId()));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ cụm hạ tầng IDG")
    @RequestMapping(value = "sync", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> syncCumIDG(@RequestBody DongBoInputDTO input) {
        ApiResult rs = new ApiResult();
        try{
            rs.setData(dsCumHTIDGService.syncCumIDG(input.getRegion_id()));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
