package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatLoaiVatLieuKNMDTO;
import vn.vnpt.vnptit.ecms.dto.danhmuc.TinhTrangIPDTO;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiVatLieuKetNoiMangService;
import vn.vnpt.vnptit.ecms.service.danhmuc.TinhTrangIPService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("danhmuc/loaivatlieu-ketnoimang")
public class LoaiVatLieuKetNoiMangController {
    @Autowired
    LoaiVatLieuKetNoiMangService service;

    @Operation(summary = "Lấy danh sách loại vật liệu kết nối mạng")
    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhsach(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getList());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update loại vật liệu kết nối mạng")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.update(input.get("p_ds"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa loại vật liệu kết nối mạng")
    @RequestMapping(value = "/delete/{Id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.delete(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
