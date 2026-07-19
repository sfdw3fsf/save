package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.idc.DanhSachPhieuTCDTO;
import vn.vnpt.vnptit.ecms.dto.idc.IdcCongViecDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhieuThiCongDTO;
import vn.vnpt.vnptit.ecms.service.idc.TiepNhanThiCongService;

@RestController
@RequestMapping("/tainguyen-idc/tiepnhan-thicong")
public class TiepNhanThiCongController {

    @Autowired
    private TiepNhanThiCongService service;

    @PostMapping("/ds-phieu-cv")
    public ResponseEntity<ApiResult> getDsPhieuNoiBo(@RequestBody DanhSachPhieuTCDTO input) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object result = this.service.dsPhieuThiCong(input);
        rs.setData(result);
        return rs.getResponseEntity();
    }

    @PostMapping("/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdatePhieuThiCongNoiBo(@RequestBody PhieuThiCongDTO dto)
            throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.createOrUpdatePhieuTC(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/ds-cv-kenhban")
    public ResponseEntity<ApiResult> getDsCvKenhBan(@RequestBody DanhSachPhieuTCDTO dto) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.dsCongViecKenhBan(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/ds-cv-kenhban-v2")
    public ResponseEntity<ApiResult> getDsCvKenhBanV2(@RequestBody DanhSachPhieuTCDTO dto) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.dsCongViecKenhBanV2(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/ct-phieu-cv")
    public ResponseEntity<ApiResult> getDsCvKenhBanV2(@RequestBody PhieuThiCongDTO dto) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.ctPhieuCv(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/chuyen-thuchien-phieu-cv")
    public ResponseEntity<ApiResult> chuyenThucHienPhieuCv(@RequestBody PhieuThiCongDTO dto) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.chuyenThucHienPhieuCv(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/ds-cv-kenhban-v3")
    public ResponseEntity<ApiResult> getDsCvKenhBanV3(@RequestBody DanhSachPhieuTCDTO dto) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.dsCongViecKenhBanV3(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/ds-cv-con")
    public ResponseEntity<ApiResult> getDsCvCon(@RequestBody DanhSachPhieuTCDTO dto) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.dsCongViecCon(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/danhsach-congviec-kenhban")
    public ResponseEntity<ApiResult> getDanhSachCongViecKenhBan(@RequestBody DanhSachPhieuTCDTO dto)
            throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.danhSachCongViecKenhBan(dto);
        return rs.getResponseEntity(res);
    }

    @PostMapping("/insert-congviec-kenhban")
    public ResponseEntity<ApiResult> insertCongViecKenhBan(@RequestBody IdcCongViecDto dto) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Object res = this.service.insertCongViecKenhBan(dto);
        return rs.getResponseEntity(new ResultOutput(res));
    }

    @PostMapping("/delete-congviec")
    public ResponseEntity<ApiResult> deleteCongViec(@RequestBody IdcCongViecDto dto) throws AppSqlException {
        ApiResult rs = new ApiResult();

        Object res = this.service.deleteCongViec(dto);
        return rs.getResponseEntity(new ResultOutput(res));
    }
}
