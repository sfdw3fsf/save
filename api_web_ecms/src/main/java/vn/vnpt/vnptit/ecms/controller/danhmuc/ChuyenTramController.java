package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.ChuyenTramDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.ChuyenTramService;

import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/chuyentram")
public class ChuyenTramController {
    private ChuyenTramService _chuyenTramService;

    public  ChuyenTramController(ChuyenTramService chuyenTramService){
        this._chuyenTramService = chuyenTramService;
    }

    @Operation(summary = "Chuyen tram cho Dslam, Gpon, Switch", description = "Chuyen tram cho Dslam, Gpon, Switch")
    @RequestMapping(value = "/chuyentramdslam", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> chuyentramdslam(@Valid @RequestBody ChuyenTramDtoIn chuyenTramDtoIn)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_chuyenTramService.chuyenTramThietbi(chuyenTramDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
