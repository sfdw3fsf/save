package vn.vnpt.vnptit.ecms.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.HieunphUserCreateDTO;
import vn.vnpt.vnptit.ecms.dto.HieunphUserFilterDTO;
import vn.vnpt.vnptit.ecms.service.HieunphUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/hieunph-test-user")
public class HieunphUserController {
    private HieunphUserService hieunphUserService;

    public HieunphUserController(HieunphUserService hieunphUserService) {
        this.hieunphUserService = hieunphUserService;
    }

    @Operation(summary = "Get all user")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListUser() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hieunphUserService.getAllUser());
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
            rs.setData(hieunphUserService.getUserById(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Create new user")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createUser(@Valid @RequestBody HieunphUserCreateDTO user) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hieunphUserService.createUser(user));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update current user")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateUser(@Valid @RequestBody HieunphUserCreateDTO user, @PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hieunphUserService.updateUser(user, id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Delete current user")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteUser(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hieunphUserService.deleteUser(id));
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
            rs.setData(hieunphUserService.getAllProvince());
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all district by province")
    @RequestMapping(value = "/district/province/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllDistrictByProvince(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hieunphUserService.getAllDistrictByProvinceId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all ward by district")
    @RequestMapping(value = "/ward/district/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllWardByDistrict(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hieunphUserService.getAllWardByDistrictId(id));
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
            rs.setData(hieunphUserService.getProvinceAndDistrictByWardId(id));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Filter user")
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> filterUser(@Valid @RequestBody HieunphUserFilterDTO filter) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hieunphUserService.filterUser(filter));
        } catch (AppSqlException exception) {
            rs.setException(exception);
        }
        return rs.getResponseEntity();
    }
}
