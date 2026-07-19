package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatHuongKetNoiDTO;
import vn.vnpt.vnptit.ecms.dto.ThongTinKetNoiIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.ThongTinKetNoiService;

import javax.validation.Valid;
/* Thông tin kết nối tới dslam*/
@RestController
@RequestMapping("thongtin-ketnoi")
public class ThongTinKetNoiController {
    private final ThongTinKetNoiService thongTinKetNoiService;

    public ThongTinKetNoiController(ThongTinKetNoiService thongTinKetNoiService) {
        this.thongTinKetNoiService = thongTinKetNoiService;
    }
    @Operation(summary = "Kết nối tới Dslam")
    @RequestMapping(value = "/ketnoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ketnoiDslam(@Valid  @RequestBody ThongTinKetNoiIn.KetNoi item) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.ketnoiDslam(phanvung_id,item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kết nối tới Dslam v2")
    @RequestMapping(value = "/ketnoi-v2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ketnoiDslam_v2(@Valid  @RequestBody ThongTinKetNoiIn.KetNoi item) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.ketnoiDslam_v2(phanvung_id,item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật hướng kết nối của thiết bị đích")
    @RequestMapping(value = "/capnhat-huong-kn", method = RequestMethod.POST)
    public  ResponseEntity<ApiResult> capNhatHuongKN(@Valid @RequestBody CapNhatHuongKetNoiDTO item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thongTinKetNoiService.capNhatHuongKN(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Gỡ kết nối tới Dslam")
    @RequestMapping(value = "/goketnoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> goKetNoiDsLam(@Valid @RequestBody ThongTinKetNoiIn.GoKetNoi item) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.goketnoi(phanvung_id,item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Load thông tin kết nối")
    @RequestMapping(value = "/load-tt-ketnoi/{madslam_port}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> loadthongtinketnoi(@Valid @PathVariable Integer madslam_port) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.loadthongtinketnoi(phanvung_id,madslam_port));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Load thông tin kết nối v2")
    @RequestMapping(value = "/load-tt-ketnoi-v2/{madslam_port}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> loadthongtinketnoi_v2(@Valid @PathVariable Integer madslam_port) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.loadthongtinketnoi_v2(phanvung_id,madslam_port));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy mã kết nối tới Dslam")
    @RequestMapping(value = "/laymakn", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layMaKetNoi(@Valid @RequestBody ThongTinKetNoiIn.MaKetNoi item) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.layMaKetNoi(phanvung_id,item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds hướng kết nối")
    @RequestMapping(value = "/huong-ket-noi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsHuongKetNoi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinKetNoiService.dsHuongKetNoi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds Thiết bị")
    @RequestMapping(value = "/dsThietBi/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsThietBi(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsThietBi(phanvung_id,id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds card dslam")
    @RequestMapping(value = "/dsCardDsl/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCardDsl(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsCardDsl(phanvung_id,id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds card gpon")
    @RequestMapping(value = "/dsCardGpon/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCardGpon(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsCardGpon(phanvung_id,id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds card Mane")
    @RequestMapping(value = "/dsCardMane/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCardMane(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsCardMane(phanvung_id,id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds port và module của cardDslam")
    @RequestMapping(value = "/ds-port-module-carddsl", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsPort_Module_CardDsl(@Valid @RequestBody ThongTinKetNoiIn.InputItem input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsPort_Module_CardDsl(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds port của card gpon")
    @RequestMapping(value = "/dsPort_CardGpon/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsPort_CardGpon(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsPort_CardGpon(phanvung_id,id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds port- module của card Mane")
    @RequestMapping(value = "/dsPortModule_CardMane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsPortModule_CardMane(@Valid @RequestBody ThongTinKetNoiIn.InputItem input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsPortModule_CardMane(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds port switch")
    @RequestMapping(value = "/dsPortSwitch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsPortSwitch(@Valid @RequestBody ThongTinKetNoiIn.InputItem input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsPortSwitch(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds port của moduleDslam")
    @RequestMapping(value = "/dsPort_ModuleDsLam", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsPortMouleDsl(@Valid @RequestBody ThongTinKetNoiIn.InputItem input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsPortMouleDsl(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds port của moduleMane")
    @RequestMapping(value = "/dsPort_ModuleMane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsPortMouleMane(@Valid @RequestBody ThongTinKetNoiIn.InputItem input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsPortMouleMane(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds tủ rack của các thiết bị")
    @RequestMapping(value = "/dsTuRack_theo_tbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsTuRack_theo_tbi(@Valid @RequestBody ThongTinKetNoiIn.LayTuRack input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongTinKetNoiService.dsTuRack_theo_tbi(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
