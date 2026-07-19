package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.MayAoDTO;
import vn.vnpt.vnptit.ecms.dto.danhmuc.MayAoIPDTO;
import vn.vnpt.vnptit.ecms.service.danhmuc.CapMayAoService;


@RestController
@RequestMapping("cap-may-ao")
public class CapMayAoController {
    @Autowired
    private CapMayAoService services;

    @Operation(summary = "Thêm mới, sửa  máy ảo")
    @RequestMapping(value = "/cap-nhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> insertUpdate(@RequestBody MayAoDTO mayAoDTO) {
        ApiResult rs = new ApiResult();
        try {
            services.capNhatMayAo(mayAoDTO);
            rs.setData("OK");
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm mới, sửa  máy ảo")
    @RequestMapping(value = "/xac-nhan/{pID}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xacNhanCapMayAo(@PathVariable(value = "pID") Long pID) {
        ApiResult rs = new ApiResult();
        try {
            services.xacNhanCapMayAo(pID);
            rs.setData("OK");
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "List danh sách máy ảo")
    @RequestMapping(value = "/danh-sach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> danhSach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.getDanhSach());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "List danh sách đơn vị cấp 2")
    @RequestMapping(value = "/danhsach-donvi-cap2/{idCha}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> danhSachDonViCap2(@PathVariable(value = "idCha") Long idCha) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.getDanhSachDonViCap2(idCha));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "List danh sách nhân viên đơn vị")
    @RequestMapping(value = "/danhsach-nhanvien-donvi/{idDonVi}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDanhSachNhanVienDonVi(@PathVariable(value = "idDonVi") Long idDonVi) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.getDanhSachNhanVienDonVi(idDonVi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "List danh sách ha tầng idg")
    @RequestMapping(value = "/danhmuc-hatang-aohoa-idg/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getHaTangAoHoaIDG(@PathVariable(value = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.getHaTangAoHoaIDG(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới, sửa ip  máy ảo")
    @RequestMapping(value = "/cap-nhat-ip", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatIPMayAo(@RequestBody MayAoIPDTO data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(services.capNhatIPMayAo(data.getId(), data.getIp_address_id_list()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
