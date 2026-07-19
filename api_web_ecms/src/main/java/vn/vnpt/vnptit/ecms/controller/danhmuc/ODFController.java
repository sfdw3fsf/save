package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatODFDtoIn;
import vn.vnpt.vnptit.ecms.dto.DsOdfDtoInp;
import vn.vnpt.vnptit.ecms.dto.ThemODFDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.ODFService;

import javax.validation.Valid;

@RestController
@RequestMapping("/danhmuc/odf")
public class ODFController {
    @Autowired
    ODFService odfService;
    @Operation(summary = "LAY_ODF_THEO_ODF_ID")
    @RequestMapping(value = "/lay_odf_theo_odf_id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_odf_theo_odf_id(Long odfId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(odfService.lay_odf_theo_odf_id(odfId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "TAO_ODF")
    @RequestMapping(value = "/tao_odf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao_odf(@Valid @RequestBody ThemODFDtoIn odf){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(odfService.tao_odf(odf));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "CAPNHAT_ODF")
    @RequestMapping(value = "/capnhat_odf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_odf(@Valid @RequestBody CapNhatODFDtoIn odf){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(odfService.capnhat_odf(odf));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "XOA_ODF")
    @RequestMapping(value = "/xoa_odf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa_odf(Long odfId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(odfService.xoa_odf(odfId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "LAY_DS_PORT_ODF_THEO_ODF_ID")
    @RequestMapping(value = "/lay_ds_port_odf_theo_odf_id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_port_odf_theo_odf_id(Long odfId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(odfService.lay_ds_port_odf_theo_odf_id(odfId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra trùng tên odf")
    @RequestMapping(value = "/kiemtra-trung-tenodf", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemTraTrungTenOdf(@RequestParam("tenOdf") String ten){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(odfService.kiemTraTrungTenOdf(ten)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách ODF theo tủ rack và trạm thiết bị")
    @RequestMapping(value = "/ds-odf-theo-turack-va-tramtb", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsOdfTheoTuRackVaTramTb(@RequestBody DsOdfDtoInp obj){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(odfService.layDsOdfTheoTuRackVaTramTb(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo port odf")
    @RequestMapping(value = "/tao-port-odf", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> taoPortOdf(@RequestParam("odfId") Long odfId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(odfService.taoPortOdf(odfId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
