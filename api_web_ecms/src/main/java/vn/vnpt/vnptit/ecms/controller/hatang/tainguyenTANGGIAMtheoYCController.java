package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;

import vn.vnpt.vnptit.ecms.dto.hatang.tainguyenTANGGIAMtheoYCDTO;
import vn.vnpt.vnptit.ecms.service.hatang.tainguyenTANGGIAMtheoYCService;

@RestController
@RequestMapping("/tai-nguyen-tang-giam-theo-yc")

public class tainguyenTANGGIAMtheoYCController {

    private final tainguyenTANGGIAMtheoYCService service;

    public tainguyenTANGGIAMtheoYCController(tainguyenTANGGIAMtheoYCService service) {
        this.service = service;
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/chitiet-vpc")
    public ResponseEntity<ApiResult> chitietVPC(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.chitietVPC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/chitiet-mayao")
    public ResponseEntity<ApiResult> chitietMaYao(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.chitietMaYao(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/insert-vpc")
    public ResponseEntity<ApiResult> insertVPC(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.insertVPC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/insert-phieuyc-dv")
    public ResponseEntity<ApiResult> insertPhieuYCDV(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.insertPhieuYCDV(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    //@CrossOrigin(origins = "*")
    @PostMapping("/insert-phieuyc-mayao")
    public ResponseEntity<ApiResult> insertPhieuYcMayAo(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.insertPhieuYcMayAo(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/lst-ttphieu")
    public ResponseEntity<ApiResult> lstTTPhieu() {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lstTTPhieu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/tt-donviQL")
    public ResponseEntity<ApiResult> ttDonViQL(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ttDonViQL(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/tt-donviYC")
    public ResponseEntity<ApiResult> ttDonViYC(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ttDonViYC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/tt-nhanvienYC")
    public ResponseEntity<ApiResult> ttNhanVienYC(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ttNhanVienYC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/tt-phieuYC")
    public ResponseEntity<ApiResult> ttPhieuYC(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ttPhieuYC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/ds-donvi")
    public ResponseEntity<ApiResult> dsDonVi(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsDonVi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //@CrossOrigin(origins = "*")
    @PostMapping("/ds-nhanvien")
    public ResponseEntity<ApiResult> dsNhanVien(@RequestBody tainguyenTANGGIAMtheoYCDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsNhanVien(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}


