package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.DeleteRequestDto;
import vn.vnpt.vnptit.ecms.service.hatang.LoaiVaiTroService;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@RestController
@RequestMapping("hatang/loai-vaitro")
public class LoaiVaiTroController {
    private final LoaiVaiTroService service;

    public LoaiVaiTroController(LoaiVaiTroService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy tất cả danh mục băng thông cổng")
    @RequestMapping(value = "/get-items", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getItems() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getItems());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm danh mục loại vai trò thiết bị")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    public ResponseEntity<ApiResult> insert(@RequestParam("p_file") MultipartFile file, @RequestParam("p_ten") String ten,
                                            @RequestParam("p_doi_tuong") Number doiTuong, @RequestParam("p_ghi_chu") String ghiChu,
                                            @RequestParam("p_hieu_luc") Integer hieuLuc)
            throws IOException{
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.insert(file, ten, doiTuong, ghiChu, hieuLuc)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm danh mục loại vai trò thiết bị")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    public ResponseEntity<ApiResult> update(@RequestParam("p_file") MultipartFile file, @RequestParam("p_id") Number id,
                                            @RequestParam("p_ten") String ten, @RequestParam("p_doi_tuong") Number doiTuong,
                                            @RequestParam("p_ghi_chu") String ghiChu, @RequestParam("p_hieu_luc") Integer hieuLuc)
            throws IOException{
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.update(file, id, ten, doiTuong, ghiChu, hieuLuc)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục loại vai trò khóa chính")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@Valid @RequestBody DeleteRequestDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.delete(item.getIds())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tất cả danh mục đối tượng")
    @RequestMapping(value = "/get-doituong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDoiTuongs() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDoiTuongs());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ảnh đại diện của loại vai trò")
    @RequestMapping(value = "/get-anh-dai-dien", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAnhDaiDien(@Valid @RequestBody DataKeyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getAnhDaiDien(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tất cả danh mục băng thông cổng")
    @RequestMapping(value = "/v2/get-items", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getItemsV2(
            @RequestParam(value="validity", required = false) Integer validity,
            @RequestParam(value="doi_tuong", required = false) Integer doi_tuong
    ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getItemsV2(validity, doi_tuong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
