package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyGenericInput;
import vn.vnpt.vnptit.ecms.dto.hatang.ThongTinThietBi_DTO;
import vn.vnpt.vnptit.ecms.service.hatang.ThongTinTB_Service;

@RestController
@RequestMapping("thong-tin-thiet-bi")
public class ThongTinTB_Controller {
    private final ThongTinTB_Service service;

    public ThongTinTB_Controller(ThongTinTB_Service service) {
        this.service = service;
    }

    @GetMapping(value = "")
    public ResponseEntity<ApiResult> getAllTB() {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getAllTB());
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/search")
    public ResponseEntity<ApiResult> searchTB(@Valid @RequestBody JsonNode dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.searchTB(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResult> getAllTB(@PathVariable("id") Number id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getTBById(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/thiet-bi-dau-noi")
    public ResponseEntity<ApiResult> getTBDauNoi(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getTBDauNoi(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/thiet-bi-ha-tang")
    public ResponseEntity<ApiResult> getTBHaTang(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getTBHaTang(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/thiet-bi-lich-su")
    public ResponseEntity<ApiResult> getTBLichSu(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getTBLichSu(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/port-khai-bao")
    public ResponseEntity<ApiResult> getPortKhaiBao(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getTBPortKhaiBao(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/thiet-bi-colocation")
    public ResponseEntity<ApiResult> getTBColocation(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getTBColocation(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/thiet-bi-ip")
    public ResponseEntity<ApiResult> getTBIP(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getTBIP(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

//    @Operation(summary = "Tạo mới thiết bị port khai bao")
//    @PostMapping(value = "/port-khai-bao")
//    public ResponseEntity<ApiResult>createUpdatePortKhaiBao(@Valid @RequestBody ThietBiPortKhaiBao_DTO dto) {
//        ApiResult rs = new ApiResult();
//        try {
//            rs.setData(this.service.CreateUpdateTBPortKhaiBao(dto));
//        } catch (AppSqlException e) {
//            rs.setException(e);
//        }
//        return rs.getResponseEntity();
//    }

    @Operation(summary = "Xóa thiết bị port khai bao")
    @PostMapping(value = "/port-khai-bao/delete")
    public ResponseEntity<ApiResult>deletePortKhaiBao(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
            String msg = "";
            Number[] ids = item.getId();
            if (ids.length == 0) {
                msg = "Danh sách id không được để trống";
            } else {
                for (Number id: ids) {
                    if (id == null || id.intValue() < 1) {
                        msg = "Danh sách id có thông tin không phù hợp";
                        break;
                    }
                }
            }

            if (msg.equals("")) rs.setData(this.service.deleteTBPortByIds(ids));
            else rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo mới và update thông tin thiết bị")
    @PostMapping(value = "")
    public ResponseEntity<ApiResult>createTb(@Valid @RequestBody ThongTinThietBi_DTO dto) {
        ApiResult rs = new ApiResult();
        try {
            Object result = service.createAndUpdateTBWithChangeIP(dto);
            rs.setData(result);
        } catch (AppSqlException e) { 
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa thông tin thiết bị")
    @PostMapping(value = "/delete-items")
    public ResponseEntity<ApiResult>deleteTB(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
            String msg = "";
            Number[] ids = item.getId();
            if (ids.length == 0) {
                msg = "Danh sách id không được để trống";
            } else {
                for (Number id: ids) {
                    if (id == null || id.intValue() < 1) {
                        msg = "Danh sách id có thông tin không phù hợp";
                        break;
                    }
                }
            }

            if (msg.equals("")) rs.setData(this.service.deleteTBByIds(ids));
            else rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/{id}/vm")
    public ResponseEntity<ApiResult> getVM(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getVM(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/nfs")
    public ResponseEntity<ApiResult> getNFS(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getNFS(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/{id}/thiet-bi-port")
    public ResponseEntity<ApiResult> getThietBiPort(@PathVariable("id") Integer id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getThietBiPort(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}
