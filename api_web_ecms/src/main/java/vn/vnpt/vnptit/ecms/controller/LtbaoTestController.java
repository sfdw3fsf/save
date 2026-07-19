package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.LtbaoTestDto;
import vn.vnpt.vnptit.ecms.service.LtbaoTestService;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ltbao")
public class LtbaoTestController {

    private LtbaoTestService ltbaoTestService;

    public LtbaoTestController(LtbaoTestService ltbaoTestService) {this.ltbaoTestService = ltbaoTestService;}

    @Operation(summary = "Get list dev test")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListDevTest() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ltbaoTestService.getListDevTest());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get details by ID")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDetailsById(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ltbaoTestService.getDetailsById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Create a new dev test")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createDevTest(@Valid @RequestBody LtbaoTestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ltbaoTestService.createDevTest(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update dev test")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResult> updateDevTestById(@PathVariable(value = "id") int id,
                                                @Valid @RequestBody LtbaoTestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ltbaoTestService.updateDevTestById(id, dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Delete dev test by id")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResult> deleteDevTestById(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ltbaoTestService.deleteDevTestById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
