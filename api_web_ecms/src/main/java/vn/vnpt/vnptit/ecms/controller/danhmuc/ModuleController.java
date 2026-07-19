package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatModuleDtoInput;
import vn.vnpt.vnptit.ecms.dto.ModuleDtoInput;
import vn.vnpt.vnptit.ecms.service.ModuleService;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("danhmuc/module")
public class ModuleController {
        private final ModuleService moduleService;

        public ModuleController(ModuleService moduleService) {
            this.moduleService = moduleService;
        }

    @Operation(summary = "Liệt kê danh sách module theo phân vùng id", description = "Liệt kê danh sách module theo phân vùng id")
    @RequestMapping(value = "/danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDanhSachModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(moduleService.layDanhSachModule());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách module theo id", description = "Liệt kê danh sách module theo id")
    @RequestMapping(value = "/danhsachtheoid", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDanhSachModuleTheoKey(@Valid @RequestParam("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(moduleService.layModuleTheoId(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới module", description = "Thêm mới module")
    @RequestMapping(value = "/them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoModule(@Valid @RequestBody ModuleDtoInput moduleDtoInput) {
        ApiResult rs = new ApiResult();
        try {
            if(moduleDtoInput.getLoai_module().getBytes(StandardCharsets.UTF_8).length>100){
                throw new AppSqlException("LoaiModule max length.");
            }
            rs.setData(moduleService.taoModule(moduleDtoInput));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật module", description = "Cập nhật module")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatModule(@Valid @RequestBody CapNhatModuleDtoInput capNhatModuleDtoInput) {
        ApiResult rs = new ApiResult();
        try {
            if(capNhatModuleDtoInput.getLoai_module().getBytes(StandardCharsets.UTF_8).length>100){
                throw new AppSqlException("LoaiModule max length.");
            }
            rs.setData(new ResultOutput(moduleService.capNhatModule(capNhatModuleDtoInput)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa module", description = "Xóa module")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaModule(@Valid  @RequestParam("module_id") int moduleId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(moduleService.xoaModule(moduleId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
