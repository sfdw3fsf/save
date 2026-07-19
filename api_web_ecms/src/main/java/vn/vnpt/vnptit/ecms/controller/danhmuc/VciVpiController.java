package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.VcivpiDto;
import vn.vnpt.vnptit.ecms.service.danhmuc.VcivpiService;

import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/vcivpi")
public class VciVpiController {
    private final VcivpiService vcivpiService;
    private int phanvung;
    public VciVpiController(VcivpiService vcivpiService)
    {
        this.vcivpiService = vcivpiService;
    }
    @Operation(summary = "Danh sách đon vị quản lý")
    @RequestMapping(value = "/dsdonviql/{loaidv_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laydsDonViQL(@PathVariable int loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.laydsDonViQL(phanvung,loaidv_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dstramthicong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> laydsTramThiCong(@Valid @RequestBody VcivpiDto.TramThiCong input) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.laydsTramThiCong(phanvung,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dstramtbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> laydsTramTBi(@Valid @RequestBody VcivpiDto.TramThiCong input) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.laydsTramTBi(phanvung,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dslam/{dvcha_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laydsDsLam(@PathVariable int dvcha_id) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.laydsDsLam(phanvung,dvcha_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dsvci_vpi/{dslamid}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laydsVciVpi(@PathVariable int dslamid) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.laydsVciVpi(phanvung,dslamid));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themVciVpi(@Valid @RequestBody VcivpiDto.VciVpiIns input) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.themVciVpi(phanvung,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "sua", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatVciVpi(@Valid @RequestBody VcivpiDto.VciVpiUpd input) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.capNhatVciVpi(phanvung,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "chitiet/{vcivpi}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layVciVpiById(@PathVariable int vcivpi) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.layVciVpiById(phanvung,vcivpi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "xoa/{vcivpi}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaVciVpiBy(@PathVariable int vcivpi) {
        ApiResult rs = new ApiResult();
        try {
            phanvung =AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(vcivpiService.xoaVciVpiBy(phanvung,vcivpi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
