package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.TraCuuDsSplitterTheoPortOltDtoIn;
import vn.vnpt.vnptit.ecms.dto.TtThueBaoIn;
import vn.vnpt.vnptit.ecms.service.tracuu.ThongTinSplitterTheoPortOltService;
import vn.vnpt.vnptit.ecms.service.tracuu.TraCuuTBSplitterService;

@RestController
public class TraCuuTBSplitterController {

    private final TraCuuTBSplitterService traCuuTBSplitterService;

    public TraCuuTBSplitterController(TraCuuTBSplitterService traCuuTBSplitterService) {
        this.traCuuTBSplitterService = traCuuTBSplitterService;
    }

    @Operation(summary = "LAY_DL_FILE_MAU", description = "Lấy dữ liệu file mẫu")
    @RequestMapping(value = "/tra-cuu/tb-splitter/lay-dl-file-mau", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDlFileMau() {
        ApiResult rs = new ApiResult();
        try {
            long phanVungId = AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(traCuuTBSplitterService.layDlFileMau(phanVungId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "LAY_TT_THUEBAO", description = "Tra cứu thuê bao theo danh sách Splitter")
    @RequestMapping(value = "/tra-cuu/tb-splitter/lay-tt-thue-bao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layTtThueBao(@RequestBody TtThueBaoIn ttThueBaoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(traCuuTBSplitterService.layTtThueBao(ttThueBaoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
