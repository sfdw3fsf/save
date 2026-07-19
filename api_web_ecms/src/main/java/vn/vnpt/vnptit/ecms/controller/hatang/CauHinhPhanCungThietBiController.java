package vn.vnpt.vnptit.ecms.controller.hatang;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.CauHinhPhanCungThietBiDtoIn;
import vn.vnpt.vnptit.ecms.service.hatang.CauHinhPhanCungThietBiService;

@RestController
@RequestMapping("/hatang/cauhinh-phancung-thietbi")
public class CauHinhPhanCungThietBiController {
    private final CauHinhPhanCungThietBiService cauHinhPhanCungThietBiService;

    public CauHinhPhanCungThietBiController(CauHinhPhanCungThietBiService cauHinhPhanCungThietBiService) {
        this.cauHinhPhanCungThietBiService = cauHinhPhanCungThietBiService;
    }

    @Operation(summary = "Thông tin thiết bị")
    @PostMapping("/thongtin")
    public ResponseEntity<ApiResult> thongTinThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.thongTinThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-ram")
    public ResponseEntity<ApiResult> danhSachRamThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachRamThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-cpu")
    public ResponseEntity<ApiResult> danhSachCpuThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachCpuThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-gpu")
    public ResponseEntity<ApiResult> danhSachGpuThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachGpuThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-blade")
    public ResponseEntity<ApiResult> danhSachBladeThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachBladeThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-ocung")
    public ResponseEntity<ApiResult> danhSachOCungThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachOCungThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-ocung-khd")
    public ResponseEntity<ApiResult> danhSachOCungKhayDia(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachOCungKhayDia(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-phancung")
    public ResponseEntity<ApiResult> danhSachPhanCungThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachPhanCungThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-khaydia")
    public ResponseEntity<ApiResult> danhSachKhayDiaTuDia(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachKhayDiaTuDia(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-bodkh")
    public ResponseEntity<ApiResult> danhSachBoDKTuDia(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachBoDKTuDia(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-khaydia-idc")
    public ResponseEntity<ApiResult> danhSachKhayDiaTuDiaIDC(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachKhayDiaTuDiaIDC(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-blade-idc")
    public ResponseEntity<ApiResult> danhSachBladeIDC(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachBladeIDC(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-bodkh-idc")
    public ResponseEntity<ApiResult> danhSachBoDKTuDiaIDC(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.danhSachBoDKTuDiaIDC(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/xoa-ram")
    public ResponseEntity<ApiResult> xoaRamThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.xoaRamThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/xoa-cpu")
    public ResponseEntity<ApiResult> xoaCpuThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.xoaCpuThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/xoa-gpu")
    public ResponseEntity<ApiResult> xoaGpuThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.xoaGpuThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/xoa-blade")
    public ResponseEntity<ApiResult> xoaBladeThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.xoaBladeThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/xoa-ocung")
    public ResponseEntity<ApiResult> xoaOCungThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.xoaOCungThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/xoa-tuadia-pc")
    public ResponseEntity<ApiResult> xoaTuDiaPCThietBi(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.xoaTuDiaPCThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/capnhat-cauhinh")
    public ResponseEntity<ApiResult> capNhatCauHinhPhanCung(@RequestBody CauHinhPhanCungThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cauHinhPhanCungThietBiService.capNhatCauHinhPhanCung(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
