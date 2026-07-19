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
import vn.vnpt.vnptit.ecms.dto.hatang.SearchCHTAoHoaDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DanhSachCumBackupService;

@RestController
@RequestMapping("ds-cum-backup")
public class DanhSachCumBackupController {
    private final DanhSachCumBackupService danhSachCumBackupService;
    DanhSachCumBackupController(DanhSachCumBackupService danhSachCumBackupService){
        this.danhSachCumBackupService = danhSachCumBackupService;
    }
    @Operation(summary = "Lấy danh sách cụm hạ tầng Backup")
    @RequestMapping(value = "get-dsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSach(@RequestBody SearchCHTAoHoaDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(danhSachCumBackupService.getDsach(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa cụm hạ tầng Backup")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@RequestBody DataKeyInput input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(danhSachCumBackupService.delete(input.getId()));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
