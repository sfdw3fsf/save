package vn.vnpt.vnptit.qlsc.controller.cntt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.cntt.KhoaPhieuCnttDto;
import vn.vnpt.vnptit.qlsc.service.NghiemThuSuCoService;

import javax.validation.Valid;

@RestController
@RequestMapping("qlsc")
public class NghiemThuSuCoCNTT2Controller {

    private final NghiemThuSuCoService nghiemThuSuCoService;

    public NghiemThuSuCoCNTT2Controller(NghiemThuSuCoService nghiemThuSuCoService) {
        this.nghiemThuSuCoService = nghiemThuSuCoService;
    }

    @RequestMapping(value = "/nghiem-thu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nghiemThu(@Valid @RequestBody KhoaPhieuCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(nghiemThuSuCoService.nghiemThu(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
