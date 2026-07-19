package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.vnptit.ecms.service.idc.QuanLyKetNoiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/dien/")
public class QuanLyKetNoiController {
    @Autowired
    private QuanLyKetNoiService ketNoiService;

    @PostMapping(value = "/chitiet-congviec")
    public ResponseEntity<ApiResult> getPhieuTCById(@RequestParam Integer idPhieuTc) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.getPhieuTCById(idPhieuTc));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/chitiet-ketnoi")
    public ResponseEntity<ApiResult> getKetNoiById(@RequestParam Integer idKetNoi, @RequestParam Integer idPhieuTc) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.getKetNoiById(idKetNoi, idPhieuTc));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    //
    // @PostMapping(value = "/chitiet-ketnoi-by-phieutc")
    // public ResponseEntity<ApiResult> getKetNoiByPhieuTc(@RequestParam Integer
    // idPhieuTc) {
    // ApiResult apiResult = new ApiResult();
    // try {
    // apiResult.setData(ketNoiService.getKetNoiByPhieuTc(idPhieuTc));
    // } catch (AppSqlException e) {
    // apiResult.setException(e);
    // }
    // return apiResult.getResponseEntity();
    // }

    @PostMapping(value = "/danhsach-ketnoi")
    public ResponseEntity<ApiResult> getDanhSachKetNoi(@RequestBody DanhSachKetNoiRequest request) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.getDanhSachKetNoi(request.getIdLoaiCv()));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/ketnoi/save")
    public ResponseEntity<ApiResult> saveKetNoi(@RequestBody KetNoiDto ketNoiDto) {
        ApiResult apiResult = new ApiResult();
        ketNoiDto.isValid();
        try {
            ketNoiDto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
            apiResult.setData(ketNoiService.saveKetNoiIdc(ketNoiDto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/ketnoi/delete")
    public ResponseEntity<ApiResult> deleteKetNoi(@RequestBody DeleteKetNoiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.deleteKetNoiIdc(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/ketnoi/update-trang-thai")
    public ResponseEntity<ApiResult> updateTrangThaiKetNoi(@RequestBody TrangThaiKetNoiDto dto) throws AppSqlException {
        ApiResult apiResult = new ApiResult();
        apiResult.setData(ketNoiService.updateTrangThaiKetNoi(dto));

        return apiResult.getResponseEntity();
    }

    @PostMapping("/ketnoi/kenhban")
    public ResponseEntity<ApiResult> getKetNoiKenhBan(@RequestBody KetNoiKenhBanDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.getKetNoiKenhBan(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/ketnoi/search")
    public ResponseEntity<ApiResult> searchKetNoi(@RequestBody SearchKetNoiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.searchKetNoi(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/phieutc-ketqua/search")
    public ResponseEntity<ApiResult> searchKQPhieuTc(@RequestBody SearchKetNoiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.searchKQPhieuTc(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/thicong-ketnoi/create-or-update")
    public ResponseEntity<ApiResult> saveThiCongKetNoi(@RequestBody ThiCongKetNoiDto thiCongKetNoiDto) {
        ApiResult apiResult = new ApiResult();
        try {
            thiCongKetNoiDto.isValid();
            apiResult.setData(ketNoiService.saveThiCongKetNoi(thiCongKetNoiDto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/thicong-ketnoi-by-phieutc")
    public ResponseEntity<ApiResult> getThiCongKetNoiByPhieuTc(@RequestBody ThiCongKetNoiDtoByPhieu dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(ketNoiService.saveThiCongKetNoiByPhieu(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

}
