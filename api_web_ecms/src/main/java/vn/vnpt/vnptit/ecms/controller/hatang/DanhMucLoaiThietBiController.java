package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyGenericInput;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucLoaiThietBiDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DanhMucLoaiThietBiService;

import javax.validation.Valid;

@RestController
@RequestMapping("danh-muc-loai-thiet-bi")
public class DanhMucLoaiThietBiController {
    private final DanhMucLoaiThietBiService service;

    //    @Autowired
    public DanhMucLoaiThietBiController(DanhMucLoaiThietBiService service) {
        this.service = service;
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<ApiResult> getAll(@RequestParam(value = "validity", required = false) Integer validity) {
        ApiResult res = new ApiResult();
        try {
//            res.setData(service.getAll());
            res.setData(this.service.getAll(validity));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResult> create(@Valid @RequestBody DanhMucLoaiThietBiDTO dto) {
        ApiResult rest = new ApiResult();
        try {
//            rest.setData(service.create(dto));
            rest.setData(this.service.create(dto.getTen(), dto.getPhanLoai(), dto.getMa(), dto.getGhiChu(), dto.getHieuLuc()));
        } catch (AppSqlException e) {
            rest.setException(e);
        }

        return rest.getResponseEntity();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@Valid @RequestBody DanhMucLoaiThietBiDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.update(dto.getId(), dto.getTen(), dto.getPhanLoai(), dto.getMa(), dto.getGhiChu(), dto.getHieuLuc()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteById(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/delete-items", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteItems(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
            String msg = "";
            Number[] ids = item.getId();
            if (ids.length == 0) {
                msg = "Danh sách loại thiết bị không được để trống";
            } else {
                for (Number id : ids) {
                    if (id == null || id.intValue() < 1) {
                        msg = "Danh sách loại thiết bị có thông tin không hợp lệ";
                        break;
                    }
                }
            }
            if (msg.equals("")) rs.setData(this.service.deleteByIds(ids));
            else rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
