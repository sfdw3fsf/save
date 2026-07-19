package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.DauNoiHTAHvsHTIDGDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DauNoiHTCLOUDvsHTIDGDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DauNoiHTMvsHTAHDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DauNoiHTMvsHTCLOUDDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DsHTIDGDChuaDauNoiDto;
import vn.vnpt.vnptit.ecms.dto.hatang.GoDNDsHTvsIDGDto;
import vn.vnpt.vnptit.ecms.dto.hatang.LayThongTinHaTangDTO;
import vn.vnpt.vnptit.ecms.service.hatang.SoDoLogicService;

@RestController
@RequestMapping("hatang/sodo-logic")
public class SoDoLogicController {
	private final SoDoLogicService service;

	public SoDoLogicController(SoDoLogicService service) {
		this.service = service;
	}
	
    @Operation(summary = "Lấy thông tin IDC theo khóa chính")
    @RequestMapping(value = "/get-thongtin-idc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinIDC(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinIDC(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy thông tin hạ tầng ảo hóa theo khóa chính")
    @RequestMapping(value = "/get-thongtin-htah", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinHTAH(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinHTAH(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy thông tin hạ tầng cloud theo khóa chính")
    @RequestMapping(value = "/get-thongtin-htcloud", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinHTCLOUD(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinHTCLOUD(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy thông tin hạ tầng IDG theo khóa chính")
    @RequestMapping(value = "/get-thongtin-htidg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinHTIDG(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinHTIDG(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy thông tin hạ tầng mạng theo khóa chính")
    @RequestMapping(value = "/get-thongtin-htm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinHTM(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinHTM(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin hạ tầng mạng theo IDC")
    @RequestMapping(value = "/get-thongtin-hatang", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinHaTang(@Valid @RequestBody LayThongTinHaTangDTO item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinHaTang(item.getKieu(), item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị theo hạ tầng mạng")
    @RequestMapping(value = "/get-danhsach-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachThietBi(@Valid @RequestBody LayThongTinHaTangDTO item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachThietBi(item.getKieu(), item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị hạ tầng mạng")
    @RequestMapping(value = "/get-ds-thietbi-htm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachThietBiHTM(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachThietBiHTM(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị hạ tầng IDG")
    @RequestMapping(value = "/get-ds-thietbi-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachThietBiIDG(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachThietBiIDG(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị hạ tầng ảo hóa")
    @RequestMapping(value = "/get-ds-thietbi-ah", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachThietBiAH(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachThietBiAH(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin sơ đồ")
    @RequestMapping(value = "/get-thongtin-sodo", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinSoDo(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinSoDo(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách IDC")
    @RequestMapping(value = "/get-danhsach-idc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachIDC() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachIDC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng ảo hóa")
    @RequestMapping(value = "/get-ds-htah", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTAH() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTAH());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng cloud")
    @RequestMapping(value = "/get-ds-htcloud", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTCLOUD() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTCLOUD());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng IDG")
    @RequestMapping(value = "/get-ds-htidg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTIDG() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTIDG());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng ảo hóa chưa đấu nối")
    @RequestMapping(value = "/get-ds-htah-chuadn", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTAHChuaDN() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTAHChuaDN());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng ảo hóa chưa đấu nối theo hạ tầng mạng")
    @RequestMapping(value = "/get-ds-htah-chuadn-by-htm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTAHChuaDNby(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTAHChuaDNbyHTM(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng cloud chưa đấu nối")
    @RequestMapping(value = "/get-ds-htcloud-chuadn", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTCLOUDChuaDN() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTCLOUDChuaDN());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng cloud chưa đấu nối theo hạ tầng mạng")
    @RequestMapping(value = "/get-ds-htcloud-chuadn-by-htm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTCLOUDChuaDNbyHTM(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTCLOUDChuaDNbyHTM(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng IDG chưa đấu nối")
    @RequestMapping(value = "/get-ds-htidg-chuadn", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHTIDGChuaDN(@Valid @RequestBody DsHTIDGDChuaDauNoiDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHTIDGChuaDN(item.getId(), item.getDauNoiHTAH()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dịch vụ hạ tầng mạng")
    @RequestMapping(value = "/get-ds-dichvu-htm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachDichVuHTM(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachDichVuHTM(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dịch vụ hạ tầng ảo hóa")
    @RequestMapping(value = "/get-ds-dichvu-ah", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachDichVuHTAH(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachDichVuHTAH(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Lấy danh sách dịch vụ hạ tầng cloud")
    @RequestMapping(value = "/get-ds-dichvu-cloud", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachDichVuHTCLOUD(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachDichVuHTCLOUD(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Lấy danh sách dịch vụ hạ tầng IDG")
    @RequestMapping(value = "/get-ds-dichvu-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachDichVuHTIDG(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachDichVuHTIDG(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách khách hàng hạ tầng ảo hóa")
    @RequestMapping(value = "/get-ds-khachhang-htah", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachKhachHangHTAH(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachKhachHangHTAH(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Lấy danh sách khách hàng hạ tầng mạng")
    @RequestMapping(value = "/get-ds-khachhang-htm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachKhachHangHTM(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachKhachHangHTM(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Đấu nối hạ tầng mạng và hạ tầng ảo hóa")
    @RequestMapping(value = "/daunoi-htm-htah", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dauNoiHTMvsHTAH(@Valid @RequestBody DauNoiHTMvsHTAHDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.dauNoiHTMvsHTAH(item.getHatangmang_id(), item.getHatangaohoa_id())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Đấu nối hạ tầng mạng và hạ tầng cloud")
    @RequestMapping(value = "/daunoi-htm-htcloud", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dauNoiHTMvsHTCLOUD(@Valid @RequestBody DauNoiHTMvsHTCLOUDDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.dauNoiHTMvsHTCLOUD(item.getHatangmang_id(), item.gethatangmartcloud_id())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Đấu nối hạ tầng ảo hóa và hạ tầng IDG")
    @RequestMapping(value = "/daunoi-htah-htidg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dauNoiHTAHvsHTIDG(@Valid @RequestBody DauNoiHTAHvsHTIDGDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.dauNoiHTAHvsHTIDG(item.getHatangaohoa_id(), item.getHatangidg_id())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Đấu nối hạ tầng ảo hóa và hạ tầng Cloud")
    @RequestMapping(value = "/daunoi-htcloud-htidg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dauNoiHTCLOUDvsHTIDG(@Valid @RequestBody DauNoiHTCLOUDvsHTIDGDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.dauNoiHTCLOUDvsHTIDG(item.getHatangsmartcloud_id(), item.getHatangidg_id())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Gỡ đấu nối hạ tầng mạng và hạ tầng ảo hóa")
    @RequestMapping(value = "/go-daunoi-htm-htah", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> goDauNoiHTMvsHTAH(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.goDauNoiHTMvsHTAH(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Gỡ đấu nối hạ tầng mạng và hạ tầng cloud")
    @RequestMapping(value = "/go-daunoi-htm-htcloud", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> goDauNoiHTMvsHTCLOUD(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.goDauNoiHTMvsHTCLOUD(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Gỡ đấu nối hạ tầng idg")
    @RequestMapping(value = "/go-daunoi-htidg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> goDauNoiHTIDG(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.goDauNoiHTIDG(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Gỡ đấu nối hạ tầng idg")
    @RequestMapping(value = "/go-daunoi-htidg-htah-htcloud", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> goDauNoiDsHTvsIDG(@Valid @RequestBody GoDNDsHTvsIDGDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.goDauNoiDsHTvsIDG(item.getHaTangIDGID(), item.getDsHT())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng theo IDC")
    @RequestMapping(value = "/get-all-hatang-by-idc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllHaTangByIDC(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getAllHaTangByIDC(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách SPDV theo thiết bị")
    @RequestMapping(value = "/get-all-spdv-by-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllSPDVByThietBi(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getSPDVByThietBi(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách khách hàng theo thiết bị")
    @RequestMapping(value = "/get-all-kh-by-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllKHByThietBi(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getKHByHietBi(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng ảo hóa và cloud đã đấu nối với IDG")
    @RequestMapping(value = "/get-ds-hatang-dadn-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsHaTangDNvsIDG(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDsHaTangDNvsIDG(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thiêt bị cha/con theo thiết bị id")
    @RequestMapping(value = "/get-thietbi-cha-con", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThietBiChaCon(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThietBiChaCon(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin cây thiết bị")
    @RequestMapping(value = "/get-cay-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getCayThietBi(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getCayThietBi(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
