package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.HoangntUserCreateDTO;
import vn.vnpt.vnptit.ecms.dto.HoangntFilterUserDTO;
import vn.vnpt.vnptit.ecms.service.HoangntUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/hoangnt-testing")
public class HoangntUserController {
       private HoangntUserService hoangntUserService;

    public HoangntUserController(HoangntUserService hoangntUserService) {
        this.hoangntUserService = hoangntUserService;
    }

    @Operation(summary = "Get a list of all users")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListUser() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.getAllUser());
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get user by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getUserById(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.getUserById(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Create new user")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createUser(@Valid @RequestBody HoangntUserCreateDTO user) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.createUser(user));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update user")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateUser(@Valid @RequestBody HoangntUserCreateDTO user, @PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.updateUser(user, id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Delete user")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteUser(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.deleteUser(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all province")
    @RequestMapping(value = "/province", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllProvince() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.getAllProvince());
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all district from province")
    @RequestMapping(value = "/district/province/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllDistrictByProvince(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.getAllDistrictByProvinceId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all ward from district")
    @RequestMapping(value = "/ward/district/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllWardByDistrict(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.getAllWardByDistrictId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get province and district by ward id")
    @RequestMapping(value = "/province/district/ward/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getProvinceAndDistrictByWard(@PathVariable(value = "id") int id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.getProvinceAndDistrictByWardId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Filter User")
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> filterUser(@Valid @RequestBody HoangntFilterUserDTO filter) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hoangntUserService.filterUser(filter));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
}
