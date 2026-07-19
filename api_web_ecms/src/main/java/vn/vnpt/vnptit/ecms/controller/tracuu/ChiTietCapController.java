package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.common.converter.ExcelHelper;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.PaginationInput;
import vn.vnpt.security.AppToken;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDslamModule;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDslamModule;
import vn.vnpt.vnptit.ecms.service.danhmuc.DslamModuleService;
import vn.vnpt.vnptit.ecms.service.tracuu.ChiTietCapService;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tracuu")
public class ChiTietCapController {
    private final ChiTietCapService _service;
    private static final Logger logger = LoggerFactory.getLogger(ChiTietCapController.class);
    public ChiTietCapController(ChiTietCapService service) {
        this._service = service;
    }

    @Operation(summary = "Chi tiết cáp")
    @RequestMapping(value = "/ChiTietCapTheoToQuanLy", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ChiTietCapTheoToQuanLy(@RequestParam String dsToQuanLyId)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ChiTietCapTheoToQuanLy(AppRequestContext.getCurrent().getPhanVungID(), dsToQuanLyId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Chi tiết cáp - paging")
    @RequestMapping(value = "/ChiTietCapTheoToQuanLyPaging", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ChiTietCapTheoToQuanLyPaging(@RequestParam String dsToQuanLyId, @Valid @RequestBody PaginationInput item)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ChiTietCapTheoToQuanLyPaging(AppRequestContext.getCurrent().getPhanVungID(), dsToQuanLyId, item.getPageIndex(), item.getPageSize()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    /***
     * Export data to excel file
     */
    @GetMapping("/chitiet-cap/excelExport")
    public HttpEntity<? extends Object> exportToExcel(@RequestParam String dsToQuanLyId, @RequestParam String filename, @RequestParam String sheetname){
        List<String> headers = new ArrayList<>();
        ArrayList<Map<String, Object>> list;
        ApiResult rs = new ApiResult();
        try {
            try {
                //read data from DB
                int phanvungId = AppRequestContext.getCurrent().getPhanVungID();
                list = _service.ChiTietCapTheoToQuanLy(phanvungId, dsToQuanLyId);
                if (list != null && list.size() > 0){
                    headers = new ArrayList<>(list.get(0).keySet());
                }
            }catch (AppSqlException e){
                throw e;
            }
            ByteArrayInputStream in = ExcelHelper.buildExcelDocument(sheetname,headers,list);
            InputStreamResource file = new InputStreamResource(in);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);
        }catch (Exception e){
        	logger.error("NoSuchFieldException occurred while processing field error.", e);
            rs.setError(e.toString());
            rs.setMessage(Message.Codes.BSS_00000500, e.toString());
            return rs.getResponseEntity();
        }
    }
}
