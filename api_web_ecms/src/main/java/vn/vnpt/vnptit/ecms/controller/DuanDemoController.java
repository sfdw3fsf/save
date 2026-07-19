package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.DuanDemoDto;
import vn.vnpt.vnptit.ecms.dto.DuanDemoSearchDto;
import vn.vnpt.vnptit.ecms.service.DuanDemoService;

@RestController
@RequestMapping("/duan-test")
public class DuanDemoController {
    private DuanDemoService duanDemoService;
    public DuanDemoController(DuanDemoService duanDemoService) {
        this.duanDemoService = duanDemoService;
    }

    //search
    @Operation(summary = "Search for users")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> searchUsers(@Valid @RequestBody DuanDemoSearchDto filter) {
        ApiResult result = new ApiResult();
        try {
            result.setData(duanDemoService.searchUsers(filter));
        } catch (AppSqlException exception) {
            result.setException(exception);
        }
        return result.getResponseEntity();
    }
    //Lấy Quận Huyện theo ID Tỉnh
    @Operation(summary = "Get districts by province")
    @RequestMapping(value = "/district/province/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDistrictsByProvinceId(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.getDistrictsByProvinceId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    //Lấy Xã Phường theo ID Quận Huyện
    @Operation(summary = "Get wards by district")
    @RequestMapping(value = "/ward/district/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getWardsByDistrictId(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.getWardsByDistrictId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    //Lấy Thông Tin Tỉnh, Quận Huyện, Xã Phường bằng ID Xã Phường
    @Operation(summary = "Get Location Info by ward id")
    @RequestMapping(value = "/province/district/ward/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getLocationInfoByWardId(@PathVariable(value = "id") int id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.getLocationInfoByWardId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    //Lấy danh sách tất cả các tỉnh
    @Operation(summary = "Get all provinces")
    @RequestMapping(value = "/province", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllProvinces() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.getAllProvinces());
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    //Lấy ds user
    @Operation(summary = "Get all user")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListUser() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.getAllUser());
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    //Lấy user theo id
    @Operation(summary = "Get user details by ID")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getUserById(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.getUserById(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    //Thêm mới
    @Operation(summary = "Create new user")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createUser(@Valid @RequestBody DuanDemoDto user) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.createUser(user));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
    //cập nhật
    @Operation(summary = "Update user")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateUser( @Valid @RequestBody DuanDemoDto duanDemoDto,@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.updateUser(duanDemoDto, id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    //xóa
    @Operation(summary = "Delete user")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteUser(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(duanDemoService.deleteUser(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
