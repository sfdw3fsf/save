package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.TraCuuDsSplitterTheoPortOltDtoIn;
import vn.vnpt.vnptit.ecms.service.tracuu.ThongTinSplitterTheoPortOltService;

@RestController
public class ThongTinSplitterTheoPortOltController {

    private final ThongTinSplitterTheoPortOltService thongTinSplitterTheoPortOltService;

    public ThongTinSplitterTheoPortOltController(ThongTinSplitterTheoPortOltService thongTinSplitterTheoPortOltService) {
        this.thongTinSplitterTheoPortOltService = thongTinSplitterTheoPortOltService;
    }

    @Operation(summary = "Tra cứu danh sách splitter theo port OLT", description = "Tra cứu danh sách splitter theo port OLT")
    @RequestMapping(value = "/tracuu/ds_splitter_theo_port_olt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> traCuuDsSplitterTheoPortOlt(@RequestBody TraCuuDsSplitterTheoPortOltDtoIn param) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinSplitterTheoPortOltService.traCuuDsSplitterTheoPortOlt(param));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
