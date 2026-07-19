package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.TraCuuDoDanCapTBTheoSoMay;
import vn.vnpt.vnptit.ecms.service.tracuu.DanDoCapTheoThueBaoService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("tracuu")
public class DanDoCapTheoThueBaoController {

    @Autowired
    private DanDoCapTheoThueBaoService danDoCapTheoThueBaoService;

    @Operation(summary = "Tra cứu dẫn độ cáp thuê bao theo số máy ảo")
    @PostMapping("/dodancaptheosomayao")
    public ResponseEntity<ApiResult> traCuuDanDoCap(@Valid @RequestBody TraCuuDoDanCapTBTheoSoMay obj){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danDoCapTheoThueBaoService.traCuuDoDanCap(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Tra cứu dẫn độ cáp thuê bao theo số máy ảo")
    @PostMapping("/lichsucap/{id}")
    public ResponseEntity<ApiResult> traCuuLichSuCap(@PathVariable("id") int thuebao_id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danDoCapTheoThueBaoService.traCuuLichSuCap(thuebao_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin cáp")
    @PostMapping("/thongtincap")
    public ResponseEntity<ApiResult> thongTinCap(@RequestBody TraCuuDoDanCapTBTheoSoMay obj){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danDoCapTheoThueBaoService.thongTinCap(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin cáp")
    @PostMapping("/capnhatghichu")
    public ResponseEntity<ApiResult> capNhatGhiChuCap(@RequestBody TraCuuDoDanCapTBTheoSoMay obj){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danDoCapTheoThueBaoService.capNhatGhiChuCap(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }
}
