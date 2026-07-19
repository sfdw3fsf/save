package vn.vnpt.vnptit.ecms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CatChuyenDsLamDtoIn;
import vn.vnpt.vnptit.ecms.service.CatChuyenDsLamService;

@RestController
@RequestMapping("catchuyendslam")
public class CatChuyenDslamController {
    private CatChuyenDsLamService catChuyenDsLamService;
    public CatChuyenDslamController(CatChuyenDsLamService catChuyenDsLamService){
        this.catChuyenDsLamService = catChuyenDsLamService;
    }

    @Operation(summary = "Thêm cat chuyen Dslam", description = "Thêm cat chuyen Dslam")
    @RequestMapping(value = "/them", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them(@RequestBody CatChuyenDsLamDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.themmoi(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cap nhật cat chuyen Dslam", description = "Cap nhật chuyen Dslam")
    @RequestMapping(value = "/capnhat", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat(@RequestBody CatChuyenDsLamDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(catChuyenDsLamService.capnhat(data)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xoa cat chuyen Dslam", description = "xoa cat chuyen Dslam")
    @RequestMapping(value = "/xoa", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@RequestParam("dslam_id") Long dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(catChuyenDsLamService.xoacatchuyen(dslam_id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "kiem tra cat chuyen Dslam", description = "kiem tra cat chuyen Dslam")
    @RequestMapping(value = "/kiemtracatchuyen", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemtracatchuyen(@RequestParam("dslam_id") int dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.kiemtraCatChuyen(dslam_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "ds chuyen Dslam theo dslam_id", description = "ds chuyen Dslam theo dslam_id")
    @RequestMapping(value = "/laythongtincatchuyen", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laythongtincatchuyen(@RequestParam("dslam_id") int dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.danhsachcatchuyen_id(dslam_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Danh sach cat chuyen Dslam", description = "Danh sach cat chuyen Dslam")
    @RequestMapping(value = "/danhsachcatchuyen", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> danhsachcatchuyen(@RequestParam("trangthai") int trangthai) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.danhsachcatchuyen(trangthai));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sach thue bao theo Dslam", description = "Danh sach thue bao theo Dslam")
    @RequestMapping(value = "/danhsachthuebao", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> danhsachthuebao(@RequestParam("dslam_id") int dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.danhsachthuebao(dslam_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay du lieu excel theo dslam id", description = "Lay du lieu excel theo dslam id")
    @RequestMapping(value = "/laydulieuexceltheodslamid", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> laydulieuexceltheodslamid(@RequestBody CatChuyenDsLamDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.tao_dulieu_excel_theo_dslamid(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoan thien dslam", description = "Hoan thien dslam")
    @RequestMapping(value = "/hoanthiendslam", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> hoanthiendslam(@RequestBody CatChuyenDsLamDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(catChuyenDsLamService.hoanthiencatchuyen(data)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách bras")
    @RequestMapping(value = "/lietkeDsBras", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietkeDsBras(Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.lietkeDsBras());
        } catch (AppSqlException e) {
            e.printStackTrace();
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách bras")
    @RequestMapping(value = "/lietkeDsDslamTheoBras", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietkeDsDslamTheoBras(@RequestParam("bras_id") Long bras_id) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(catChuyenDsLamService.lietkeDsDslamTheoBras(bras_id));
        } catch (AppSqlException e) {
            e.printStackTrace();
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Dslam VDC Cu theo Dslam")
    @RequestMapping(value = "/lietkeDsDslamVDCCuTheoDslam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietkeDsDslamVDCCuTheoDslam(@RequestParam("dslam_id") Long dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.lietkeDsDslamVDCCuTheoDslam(dslam_id));
        } catch (AppSqlException e) {
            e.printStackTrace();
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Dslam VDC moi theo Bras moi")
    @RequestMapping(value = "/lietkeDsDslamVDCMoiTheoBrasMoi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietkeDsDslamVDCMoiTheoBrasMoi(@RequestParam("bras_id") Long bras_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.lietkeDsDslamVDCMoiTheoBrasMoi(bras_id));
        } catch (AppSqlException e) {
            e.printStackTrace();
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Slot Bras theo Bras moi")
    @RequestMapping(value = "/lietkeDsSlotBrasTheoBrasMoi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietkeDsSlotBrasTheoBrasMoi(@RequestParam("bras_id") Long bras_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.lietkeDsSlotBrasTheoBrasMoi(bras_id));
        } catch (AppSqlException e) {
            e.printStackTrace();
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Port Bras theo Slot Bras")
    @RequestMapping(value = "/lietkeDsPortBrasTheoSlotBras", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietkeDsPortBrasTheoSlotBras(@RequestParam("slot_id") Long bras_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.lietkeDsPortBrasTheoSlotBras(bras_id));
        } catch (AppSqlException e) {
            e.printStackTrace();
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "lay thong tin bras theo dslam_id", description = "lay thong tin bras theo dslam_id")
    @RequestMapping(value = "/laythongtinbras", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laythongtinbras(@RequestParam("dslam_id") int dslam_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(catChuyenDsLamService.laythongtinbras(dslam_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
