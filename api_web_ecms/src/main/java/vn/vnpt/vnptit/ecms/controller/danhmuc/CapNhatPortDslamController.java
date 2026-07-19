package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.CapNhatPortDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ChangePortMegaVNNDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.CapNhatPortService;

import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/cap-nhat-port")
public class CapNhatPortDslamController {
    @Autowired
    private CapNhatPortService services;

    @Operation(summary = "Liệt kê danh sách don vi quan lyi")
    @RequestMapping(value = "/lietke-donvi-qli", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKe_donvi_qli(@Valid @RequestParam Long p_donvi_id, @Valid @RequestParam  Long p_loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lietKe_donvi_qli(p_donvi_id,p_loaidv_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách don vi quan lyi")
    @RequestMapping(value = "/lietke-tramtbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKe_tramtbi(@Valid @RequestParam Long p_donvi_id, @Valid @RequestParam Long p_loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lietKe_tramtbi(p_donvi_id,p_loaidv_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách dslam tinh")
    @RequestMapping(value = "/lietke-dslam-tinh", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKe_dslam_tinh(@Valid @RequestParam Long p_donvi_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lietKe_dslam_tinh(p_donvi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách card theo dslam")
    @RequestMapping(value = "/lietke-dscard-theo-dslam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKe_dscard_theo_dslam(@Valid @RequestParam Long p_dslam_id, @Valid @RequestParam Long p_loaitbi_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lietKe_dscard_theo_dslam(p_dslam_id,p_loaitbi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách bras")
    @RequestMapping(value = "/lietke-bras", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKe_bras() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lietKe_bras());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách dslam")
    @RequestMapping(value = "/lietke-dslam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietke_dslam(@Valid @RequestParam Long p_bras_id, @Valid @RequestParam Long p_kieu,
                                                  @RequestParam Long p_thamso ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lietKe_dslam(p_bras_id, p_kieu, p_thamso));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách port logic v3")
    @RequestMapping(value = "/lay-ds-port-logic-v3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_port_logic_v3(@Valid @RequestParam Long p_donvi_id, @RequestParam String p_tt_port_id,
                                                          @Valid @RequestParam Long p_dslam_id, @Valid @RequestParam Long p_card_id) {
            ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lay_ds_port_logic_v3(p_donvi_id, p_tt_port_id, p_dslam_id,p_card_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách thue bao theo port")
    @RequestMapping(value = "/lay-ds-thuebao-theo-port", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_thuebao_theo_port(@Valid @RequestParam Long p_dslam_id,@RequestParam String p_ds_port_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.lay_ds_thuebao_theo_port(p_dslam_id,p_ds_port_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "chen log vao he thong")
    @RequestMapping(value = "/log-ins", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> log_ins(@Valid @RequestBody CapNhatPortDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.log_ins(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cap nhat port theo danh sach")
    @RequestMapping(value = "/cap-nhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> port_upd(@RequestParam Long p_dslam_vdc_id, @Valid @RequestParam Long p_port_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.port_upd(p_dslam_vdc_id,p_port_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin bras")
    @RequestMapping(value = "/get-bras-infor", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> get_bras(@RequestParam Long bras_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.get_bras_vdc(bras_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin dslam")
    @RequestMapping(value = "/get-dslam-infor", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> get_dslam(@RequestParam Long dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.get_dslam_vdc(dslam_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin bras")
    @RequestMapping(value = "/map-bras-vdc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> map_bras(@RequestParam Long bras_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.map_bras_vdc(bras_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin dslam")
    @RequestMapping(value = "/map-dslam-vdc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> map_dslam(@RequestParam Long dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.map_dslam_vdc(dslam_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin dslam")
    @RequestMapping(value = "/change-port-megavnn", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> changePortMegaVNN(@RequestBody ChangePortMegaVNNDtoIn serviceObj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.changePortMegaVNN(serviceObj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
