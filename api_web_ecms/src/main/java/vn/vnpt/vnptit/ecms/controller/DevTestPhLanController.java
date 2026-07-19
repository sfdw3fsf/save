package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.DevTestPhLanDto;
import vn.vnpt.vnptit.ecms.dto.DevTestPhLanFilterDto;
import vn.vnpt.vnptit.ecms.service.DevTestPhLanService;

import javax.validation.Valid;
import java.sql.Date;

@RestController
@RequestMapping("/api/users")
public class DevTestPhLanController {
    private DevTestPhLanService devTestPhLanService;

    public DevTestPhLanController(DevTestPhLanService devTestPhLanService) {
        this.devTestPhLanService = devTestPhLanService;
    }
    @Operation(summary = "Get list user with filter and pagination")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListUserWithFilterAndPagination() {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(devTestPhLanService.getListUserWithFilterAndPagination());
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get user details by ID")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getUserById(@PathVariable(value = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.getUserById(id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Create a new user")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createUser(@Valid @RequestBody DevTestPhLanDto devTestPhLanDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.createUser(devTestPhLanDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Delete the user")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteUser(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.deleteUser(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update the user")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateUser(@PathVariable(value = "id") int id,
                                                @Valid @RequestBody DevTestPhLanDto devTestPhLanDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.updateUser(id, devTestPhLanDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all province ")
    @RequestMapping(value = "/province", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllProvince() {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(devTestPhLanService.getAllProvince());
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all district by province")
    @RequestMapping(value = "/district/province/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllDistricByProvince(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.getAllDistricByProvinceId(id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get all ward by district")
    @RequestMapping(value = "ward/district/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAllWardByDistrict(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.getAllWardByDistrictId(id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get province and district by ward id")
    @RequestMapping(value = "province/district/ward/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getProvinceAndDistrictByWard(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.getProvinceAndDistrictByWardId(id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Filter users")
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> filterUsers(@Valid @RequestBody DevTestPhLanFilterDto devTestPhLanFilterDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(devTestPhLanService.filterUsers(devTestPhLanFilterDto));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}

