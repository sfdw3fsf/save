package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.TuanDdDTO;
import vn.vnpt.vnptit.ecms.dto.TuanDdSearchDTO;
import vn.vnpt.vnptit.ecms.service.TuanDdService;

import javax.validation.Valid;



@RestController
@RequestMapping("/tuandd-democrud")
public class TuanDdControllers {
    private  TuanDdService tuanDdService;
    public  TuanDdControllers(TuanDdService tuanDdService){
        this.tuanDdService=tuanDdService;
    }
    @Operation(summary = "Lấy danh sách users")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListUser() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.getAllUser());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Tạo user mới")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createNewUser(@Valid @RequestBody TuanDdDTO usernew) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.createNewUser(usernew));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy User By Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getUserById(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.getUserById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Update User")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createNewUser(@Valid @RequestBody TuanDdDTO user,@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.updateUser(user,id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xóa User")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteUser(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.deleteUser(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Get all province")
    @RequestMapping(value = "/province", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllProvince() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.getAllProvince());
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy tất cả huyện theo TINH_ID")
    @RequestMapping(value = "/district/province/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllDistrictByProvince(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.getAllDistrictByProvinceId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "lấy tất cả xã theo huyện id")
    @RequestMapping(value = "/ward/district/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllWardByDistrict(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.getAllWardByDistrictId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy quận/huyện,tỉnh theo id xã")
    @RequestMapping(value = "/province/district/ward/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getProvinceAndDistrictByWard(@PathVariable(value = "id") int id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.getProvinceAndDistrictByWardId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Filter user")
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> filterUsers(@Valid @RequestBody TuanDdSearchDTO filter) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tuanDdService.filterUsers(filter));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

}
