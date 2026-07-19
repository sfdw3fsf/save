package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.BaseMap;
import vn.vnpt.vnptit.ecms.dto.mau.*;
import vn.vnpt.vnptit.ecms.service.idc.MauCardService;
import vn.vnpt.vnptit.ecms.service.idc.MauDieuKhienService;
import vn.vnpt.vnptit.ecms.service.idc.MauKhayDiaService;
import vn.vnpt.vnptit.ecms.service.idc.MauMayBienApService;
import vn.vnpt.vnptit.ecms.service.idc.MauPortService;
import vn.vnpt.vnptit.ecms.service.idc.MauServerBladeService;
import vn.vnpt.vnptit.ecms.service.idc.MauThietBiMangService;
import vn.vnpt.vnptit.ecms.service.idc.MauThietBiService;

import java.util.Map;

@RestController
@RequestMapping("/mau-thietbi")
public class MauThietBiController {

    @Autowired
    private MauThietBiService mauThietBiService;

    @Autowired
    private MauPortService mauPortService;

    @Autowired
    private MauThietBiMangService mauThietBiMangService;

    @Autowired
    private MauDieuKhienService mauDieuKhienService;

    @Autowired
    private MauServerBladeService mauServerBladeService;

    @Autowired
    private MauMayBienApService mauMayBienApService;

    @Autowired
    private MauCardService mauCardService;

    @Autowired
    private MauKhayDiaService mauKhayDiaService;
    
    // ===========================================
    // MAU_TBI (Mẫu thiết bị) - CRUD
    // ===========================================

    @Operation(summary = "Tìm kiếm hoặc lấy danh sách mẫu thiết bị")
    @PostMapping("/mau-tbi/search")
    public ResponseEntity<ApiResult> searchMauThietBi(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiService.search(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }


    @Operation(summary = "Lấy chi tiết mẫu thiết bị theo ID")
    @GetMapping("/mau-tbi/{id}")
    public ResponseEntity<ApiResult> getMauThietBiById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    
    @Operation(summary = "Lấy chi tiết mẫu thiết bị dùng để merge data theo ID")
    @GetMapping("/mau-tbi/merge/{id}")
    public ResponseEntity<ApiResult> getMauThietBiMergeById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiService.getMauThietBiMergeById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật mẫu thiết bị")
    @PostMapping("/mau-tbi/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdateMauThietBi(@RequestBody MauThietBiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiService.save(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa mẫu thiết bị")
    @PostMapping("/mau-tbi/delete")
    public ResponseEntity<ApiResult> deleteMauThietBi(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ===========================================
    // MAU_PORT (Port mẫu) - CRUD
    // ===========================================

    @Operation(summary = "Lấy danh sách port của mẫu thiết bị")
    @PostMapping("/mau-port/danh-sach")
    public ResponseEntity<ApiResult> getMauPortByMauThietBi(@RequestBody Map<String, Object> params) {
        BaseMap map = new BaseMap(params);
        map.validNumber("mautbiId", "param mautbiId là bắt buộc")
                .validNumber("phanLoai", "param phanLoai là bắt buộc")
                .validComplate();
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mauPortService.gePorttByMauThietBi(
                    map.get("mautbiId", Long.class),
                    map.get("phanLoai", Integer.class),
                    map.get("kheCam", Integer.class, 0)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật port mẫu")
    @PostMapping("/mau-port/them-cong")
    public ResponseEntity<ApiResult> createOrUpdateMauPort(@RequestBody MauPortDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauPortService.insertPort(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ===========================================
    // MAU_TBIMANG (Thiết bị mạng mẫu) - CRUD
    // ===========================================

    @Operation(summary = "Tìm kiếm hoặc lấy danh sách thiết bị mạng mẫu")
    @PostMapping("/mau-tbimang/search")
    public ResponseEntity<ApiResult> searchMauThietBiMang(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiMangService.search(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết thiết bị mạng mẫu theo ID")
    @GetMapping("/mau-tbimang/{id}")
    public ResponseEntity<ApiResult> getMauThietBiMangById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiMangService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy thiết bị mạng mẫu theo mẫu thiết bị ID")
    @GetMapping("/mau-tbimang/by-mautbi/{mautbiId}")
    public ResponseEntity<ApiResult> getMauThietBiMangByMauThietBiId(@PathVariable Long mautbiId) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiMangService.getByMauThietBiId(mautbiId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật thiết bị mạng mẫu")
    @PostMapping("/mau-tbimang/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdateMauThietBiMang(@RequestBody MauThietBiMangDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiMangService.save(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa thiết bị mạng mẫu")
    @PostMapping("/mau-tbimang/delete")
    public ResponseEntity<ApiResult> deleteMauThietBiMang(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauThietBiMangService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ===========================================
    // MAU_DKH (Thiết bị điều khiển mẫu) - CRUD
    // ===========================================

    @Operation(summary = "Tìm kiếm hoặc lấy danh sách thiết bị điều khiển mẫu")
    @PostMapping("/mau-dkh/search")
    public ResponseEntity<ApiResult> searchMauDieuKhien(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauDieuKhienService.search(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết thiết bị điều khiển mẫu theo ID")
    @GetMapping("/mau-dkh/{id}")
    public ResponseEntity<ApiResult> getMauDieuKhienById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauDieuKhienService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy thiết bị điều khiển mẫu theo mẫu thiết bị ID")
    @GetMapping("/mau-dkh/by-mautbi/{mautbiId}")
    public ResponseEntity<ApiResult> getMauDieuKhienByMauThietBiId(@PathVariable Long mautbiId) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauDieuKhienService.getByMauThietBiId(mautbiId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật thiết bị điều khiển mẫu")
    @PostMapping("/mau-dkh/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdateMauDieuKhien(@RequestBody MauDieuKhienDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauDieuKhienService.save(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa thiết bị điều khiển mẫu")
    @PostMapping("/mau-dkh/delete")
    public ResponseEntity<ApiResult> deleteMauDieuKhien(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauDieuKhienService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ===========================================
    // MAU_SB (Server/Blade mẫu) - CRUD
    // ===========================================

    @Operation(summary = "Tìm kiếm hoặc lấy danh sách server/blade mẫu")
    @PostMapping("/mau-sb/search")
    public ResponseEntity<ApiResult> searchMauServerBlade(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauServerBladeService.search(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết server/blade mẫu theo ID")
    @GetMapping("/mau-sb/{id}")
    public ResponseEntity<ApiResult> getMauServerBladeById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauServerBladeService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy server/blade mẫu theo mẫu thiết bị ID")
    @GetMapping("/mau-sb/by-mautbi/{mautbiId}")
    public ResponseEntity<ApiResult> getMauServerBladeByMauThietBiId(@PathVariable Long mautbiId) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauServerBladeService.getByMauThietBiId(mautbiId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật server/blade mẫu")
    @PostMapping("/mau-sb/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdateMauServerBlade(@RequestBody MauServerBladeDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauServerBladeService.save(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa server/blade mẫu")
    @PostMapping("/mau-sb/delete")
    public ResponseEntity<ApiResult> deleteMauServerBlade(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauServerBladeService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ===========================================
    // MAU_MBA (Máy biến áp mẫu) - CRUD
    // ===========================================

    @Operation(summary = "Tìm kiếm hoặc lấy danh sách máy biến áp mẫu")
    @PostMapping("/mau-mba/search")
    public ResponseEntity<ApiResult> searchMauMayBienAp(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauMayBienApService.search(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết máy biến áp mẫu theo ID")
    @GetMapping("/mau-mba/{id}")
    public ResponseEntity<ApiResult> getMauMayBienApById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauMayBienApService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy máy biến áp mẫu theo mẫu thiết bị ID")
    @GetMapping("/mau-mba/by-mautbi/{mautbiId}")
    public ResponseEntity<ApiResult> getMauMayBienApByMauThietBiId(@PathVariable Long mautbiId) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauMayBienApService.getByMauThietBiId(mautbiId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật máy biến áp mẫu")
    @PostMapping("/mau-mba/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdateMauMayBienAp(@RequestBody MauMayBienApDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauMayBienApService.save(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa máy biến áp mẫu")
    @PostMapping("/mau-mba/delete")
    public ResponseEntity<ApiResult> deleteMauMayBienAp(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauMayBienApService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ===========================================
    // MAU_CARD (Card mẫu) - CRUD
    // ===========================================

    @Operation(summary = "Tìm kiếm hoặc lấy danh sách card mẫu")
    @PostMapping("/mau-card/search")
    public ResponseEntity<ApiResult> searchMauCard(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauCardService.search(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết card mẫu theo ID")
    @GetMapping("/mau-card/{id}")
    public ResponseEntity<ApiResult> getMauCardById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauCardService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy card mẫu theo mẫu thiết bị ID")
    @GetMapping("/mau-card/by-mautbi/{mautbiId}")
    public ResponseEntity<ApiResult> getMauCardByMauThietBiId(@PathVariable Long mautbiId) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauCardService.getByMauThietBiId(mautbiId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật card mẫu")
    @PostMapping("/mau-card/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdateMauCard(@RequestBody MauCardDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauCardService.save(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa card mẫu")
    @PostMapping("/mau-card/delete")
    public ResponseEntity<ApiResult> deleteMauCard(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauCardService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ===========================================
    // MAU_KHD (Khay đĩa mẫu) - CRUD
    // ===========================================

    @Operation(summary = "Tìm kiếm hoặc lấy danh sách khay đĩa mẫu")
    @PostMapping("/mau-khd/search")
    public ResponseEntity<ApiResult> searchMauKhayDia(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauKhayDiaService.search(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết khay đĩa mẫu theo ID")
    @GetMapping("/mau-khd/{id}")
    public ResponseEntity<ApiResult> getMauKhayDiaById(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauKhayDiaService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy khay đĩa mẫu theo mẫu thiết bị ID")
    @GetMapping("/mau-khd/by-mautbi/{mautbiId}")
    public ResponseEntity<ApiResult> getMauKhayDiaByMauThietBiId(@PathVariable Long mautbiId) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauKhayDiaService.getByMauThietBiId(mautbiId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật khay đĩa mẫu")
    @PostMapping("/mau-khd/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdateMauKhayDia(@RequestBody MauKhayDiaDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauKhayDiaService.save(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa khay đĩa mẫu")
    @PostMapping("/mau-khd/delete")
    public ResponseEntity<ApiResult> deleteMauKhayDia(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(mauKhayDiaService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}

