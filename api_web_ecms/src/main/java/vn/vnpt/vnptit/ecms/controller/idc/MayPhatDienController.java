package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.common.errorcode.ErrorCode;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ThongTinPortDto;
import vn.vnpt.vnptit.ecms.dto.idc.ViTriThietBiRequest;
import vn.vnpt.vnptit.ecms.service.idc.MayPhatDienService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("idc/thiet-bi-vat-ly")
public class MayPhatDienController {

    @Autowired
    private MayPhatDienService mayPhatDienService;

    @Operation(summary = "Thêm mới hoặc sửa port thiết bị")
    @PostMapping("/add-or-update-thiet-bi-port")
    public ResponseEntity<ApiResult> addThietBiPort(@RequestBody ThongTinPortDto dto) {
        ApiResult rs = new ApiResult();
        try {
            List<Map<String, Object>> map = mayPhatDienService.getThietBiPort(dto.getPortId());
            // validate đã kết nối thì không cho phép sửa xóa port nữa
            if( map != null && !map.isEmpty() && map.get(0).get("KETNOI") != null
                    && Integer.parseInt(map.get(0).get("KETNOI").toString()) == 1) {
                Map<String, Object> mapMessage = new HashMap<>();
                mapMessage.put("message","Port đã kết nối, không thể sửa hoặc xóa");
                rs.setMessage("fail");
                rs.setData(mapMessage);
                return rs.getResponseEntity();
            }
            rs.setData(mayPhatDienService.addOrUpdateThietBiPort(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @PostMapping("/get-port-theo-loai-cong-id")
    @Operation(summary = "Lấy danh sách Port theo thiết bị và loại cổng")
    public ResponseEntity<ApiResult> getThietBiPortByLoaiCong(@RequestBody ThongTinPortDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayPhatDienService.getThietBiPortByLoaiCong(dto.getThietBiId(), dto.getLoaiCongId()));
        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa port thiết bị")
    @PostMapping("/delete-thiet-bi-port")
    public ResponseEntity<ApiResult> deleteThietBiPort(@RequestBody ThongTinPortDto dto) {
        ApiResult rs = new ApiResult();
        try {
            List<Map<String, Object>> map = mayPhatDienService.getThietBiPort(dto.getPortId());
            if(map == null || map.isEmpty()){
                Map<String, Object> mapMessage = new HashMap<>();
                mapMessage.put("message","Không tìm thấy dữ liệu cần xóa.");
                rs.setMessage("fail");
                rs.setData(mapMessage);
                return rs.getResponseEntity();
            }
            // validate đã kết nối thì không cho phép sửa xóa port nữa
            if( map != null && !map.isEmpty() && map.get(0).get("KETNOI") != null
                    && Integer.parseInt(map.get(0).get("KETNOI").toString()) == 1) {
                Map<String, Object> mapMessage = new HashMap<>();
                mapMessage.put("message","Port đã kết nối, không thể sửa hoặc xóa.");
                rs.setMessage("fail");
                rs.setData(mapMessage);
                return rs.getResponseEntity();
            }
            rs.setData(mayPhatDienService.deleteThietBiPort(dto.getPortId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @GetMapping("/get-rack-theo-phong-id")
    @Operation(summary = "Lấy danh sách rack theo phòng")
    public ResponseEntity<ApiResult> getDanhMucRack( @RequestParam(value = "phongId", required = false) Long phongId) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayPhatDienService.findIdcRack(phongId));
        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/update-vi-tri-thiet-bi")
    @Operation(summary = "Thêm mới vị trí thiết bị")
    public ResponseEntity<?> insertViTriThietBi(@RequestBody ViTriThietBiRequest viTriDto) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, String> error = mayPhatDienService.validate(viTriDto);
            if(!error.isEmpty()){
                rs.setData(error);
                Map<String, Object> mapMessage = new HashMap<>();
                mapMessage.put("message","Dữ liệu đầu vào không đúng.");
                rs.setMessage("fail");
                rs.setData(mapMessage);
                return rs.getResponseEntity();
            }
            Map<String, Object> tb = mayPhatDienService.getThietBiById(viTriDto.getThietBiId());
            if(tb == null){
                Map<String, Object> mapMessage = new HashMap<>();
                mapMessage.put("message","không tìm thấy thiết bị.");
                rs.setMessage("fail");
                rs.setData(mapMessage);
                return rs.getResponseEntity();
            }
            BigDecimal fullDepth = (BigDecimal) tb.get("FULL_DEPTH");
            BigDecimal kieuLapDat = (BigDecimal) tb.get("KIEU_LD");
            if (fullDepth != null && fullDepth.compareTo(BigDecimal.ZERO) == 0
                    && kieuLapDat.compareTo(BigDecimal.valueOf(2)) == 0) {
                viTriDto.setMatRackUnit("0");
                viTriDto.setCanhRackUnit("0");
            } else {
                if ("0".equals(viTriDto.getMatRackUnit()) || "0".equals(viTriDto.getCanhRackUnit())) {
                    Map<String, Object> mapMessage = new HashMap<>();
                    mapMessage.put("message","Vị trí mặt rack không hợp lệ.");
                    rs.setMessage("fail");
                    rs.setData(mapMessage);
                    return rs.getResponseEntity();
                }
            }
            ArrayList<Map<String, Object>> data =  mayPhatDienService.insertViTriThietBi(viTriDto, false);
            if(data == null ){
                rs.setData(data);
            }else {
                rs.setData(data);
                rs.setMessage("fail");
                return rs.getResponseEntity();
            }

        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/delete-vi-tri-thiet-bi")
    @Operation(summary = "Thêm mới vị trí thiết bị")
    public ResponseEntity<?> deleteViTriThietBi(@RequestBody ViTriThietBiRequest viTriDto) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> tb = mayPhatDienService.getThietBiById(viTriDto.getThietBiId());
            if(tb == null){
                Map<String, Object> mapMessage = new HashMap<>();
                mapMessage.put("message","không tìm thấy thiết bị.");
                rs.setMessage("fail");
                rs.setData(mapMessage);
                return rs.getResponseEntity();
            }
            viTriDto.setIdcId(null);
            viTriDto.setDonViQLyId(null);
            viTriDto.setMatSanId(null);
            viTriDto.setToaNhaId(null);
            viTriDto.setPhongId(null);
            viTriDto.setIdcRackId(null);
            viTriDto.setRackUnitId(null);
            viTriDto.setKieuLapDat(null);
            viTriDto.setUpUnitId(null);
            viTriDto.setDownUnitId(null);
            viTriDto.setMatRackUnit(null);
            viTriDto.setCanhRackUnit(null);
            mayPhatDienService.resetRackunitByThietBi(viTriDto);
            rs.setData(mayPhatDienService.insertViTriThietBi(viTriDto, true));

        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/get-vi-tri-thiet-bi")
    @Operation(summary = "Thêm mới vị trí thiết bị")
    public ResponseEntity<?> gettViTriThietBi(@RequestBody ViTriThietBiRequest viTriDto) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> tb = mayPhatDienService.getThietBiById(viTriDto.getThietBiId());
            if(tb == null){
                Map<String, Object> mapMessage = new HashMap<>();
                mapMessage.put("message","không tìm thấy thiết bị để lấy ra vị trí của thiết bị.");
                rs.setMessage("fail");
                rs.setData(mapMessage);
                return rs.getResponseEntity();
            }

            rs.setData(tb);
        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/lich-su-tac-dong/page")
    public ResponseEntity<?> getLichSuTacDongPage(
            @RequestParam(required = false) BigDecimal thietBiId,
            @RequestParam(required = false) String tenbang,
            @RequestParam(required = false) String khoaDuLieuList,
            @RequestParam(required = false) String tenCotList,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        ApiResult rs = new ApiResult();
        try {
            // Gọi service
            Map<String, Object> data = mayPhatDienService.getLichSuTacDongPage(
                    thietBiId, tenbang, khoaDuLieuList, tenCotList, pageNumber,  pageSize );
            rs.setData(data);

        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/get-nghiep-vu-idc")
    public ResponseEntity<?> getNghiepVuIDC(
            @RequestParam(required = false) Long thietBiId
    ) {
        ApiResult rs = new ApiResult();
        try {
            List<Map<String, Object>>  data = mayPhatDienService.getPhieuTcByThietBiId(thietBiId);
            rs.setData(data);
        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/get-danh-muc-dv-idc")
    public ResponseEntity<?> getDonViIDC() {
        ApiResult rs = new ApiResult();
        try {
            List<Map<String, Object>>  data = mayPhatDienService.getDonViIDC();
            rs.setData(data);
        } catch (AppSqlException e) {
            e.printStackTrace();
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
