package vn.vnpt.vnptit.ecms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.*;
import vn.vnpt.vnptit.ecms.service.ThongSoPortService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/thongsoport")
public class ThongSoPortController {
    private final ThongSoPortService thongSoPortService;

    public ThongSoPortController(ThongSoPortService thongSoPortService) {
        this.thongSoPortService = thongSoPortService;
    }


    @Operation(summary = "Lấy danh sách đơn vị quản lý", description = "Lấy danh sách đơn vị quản lý")
    @RequestMapping(value = "/dsdonviquanly", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsDonViQuanLy(@Valid  @RequestParam("donviId") Long  donviId,@Valid  @RequestParam("loaidvId") Long loaidvId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsDonViQuanLy(donviId,loaidvId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách trạng thái port", description = "Lấy danh sách trạng thái port")
    @RequestMapping(value = "/dslaytrangthaiport", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayTrangThaiPort() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayTrangThaiPort());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy rack shelf", description = "Lấy Lấy rack shelf")
    @RequestMapping(value = "/dslayrackshelf", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayRackShelf() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayRackShelf());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy bras adsl", description = "Lấy bras adsl")
    @RequestMapping(value = "/dslaybrasadsl", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayBrasAdsl() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayBrasAdsl());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tram tc ", description = "Lấy trạm tc ")
    @RequestMapping(value = "/dsLayTramTc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayTramTc(@Valid  @RequestParam("donviId") Long  donviId,@Valid  @RequestParam("loaidvId") Long loaidvId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayTramTc(donviId,loaidvId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy trạm tbi", description = "Lấy trạm tbi")
    @RequestMapping(value = "/dsLayTramTb", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayTramTb(@Valid  @RequestParam("donviId") Long  donviId,@Valid  @RequestParam("loaidvId") Long loaidvId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayTramTb(donviId,loaidvId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Dslam", description = "Lấy Dslam")
    @RequestMapping(value = "/dsLayDslam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayDslam(@Valid  @RequestParam("donviId") Long  donviId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayDslam(donviId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy VCI VPI chưa gán", description = "Lấy VCI VPI chưa gán")
    @RequestMapping(value = "/dsLayVciVpiChuaGan", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayVciVpiChuaGan(@Valid  @RequestParam("dslamId") Long  dslamId,@Valid  @RequestParam("portId") Long portId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayVciVpiChuaGan(dslamId,portId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy VCI VPI đã gán", description = "Lấy VCI VPI đã gán")
    @RequestMapping(value = "/dsLayVciVpiDaGan", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayVciVpiDaGan(@Valid  @RequestParam("dslamId") Long  dslamId,@Valid  @RequestParam("portId") Long portId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayVciVpiDaGan(dslamId,portId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds card", description = "Lấy ds card")
    @RequestMapping(value = "/dsLayDsCard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayDsCard(@Valid  @RequestParam("dslamId") Long  dslamId,@Valid  @RequestParam("loaiTbId") Long loaiTbId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayDsCard(dslamId,loaiTbId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy DsPort", description = "Lấy DsPort")
    @RequestMapping(value = "/dsLayDsPort", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayDsPort(@Valid  @RequestParam("donviId") Long  donviId, @RequestParam(name = "portId",required = false) Long portId,@Valid  @RequestParam("dslamId") Long dslamId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayDsPort(donviId, portId, dslamId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy loai hinh đã gán", description = "Lấy loại hình đã gán" )
    @RequestMapping(value = "/dsLayLoaiHinhDaGan", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayLoaiHinhDaGan(@Valid  @RequestParam("vrpId") Long  vrpId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayLoaiHinhDaGan(vrpId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy dslam vdc", description = "Lấy dslam vdc" )
    @RequestMapping(value = "/dsLayDsLamVdc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayDsLamVdc(@Valid  @RequestParam("brasId") Long  brasId,@Valid  @RequestParam("kieu") Long kieu) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayDsLamVdc(brasId,kieu));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm port", description = "Thêm port" )
    @RequestMapping(value = "/themPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themPort(@Valid @RequestBody PortDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.themPort(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Cập nhật port", description = "Cập nhật port" )
    @RequestMapping(value = "/capnhatPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatPort(@Valid @RequestBody PortDtoInput.PortDtoUpdInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thongSoPortService.capNhatPort(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá Vci Vpi của port", description = "Xoá Vci Vpi của port" )
    @RequestMapping(value = "/xoaVciVpiChoPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsLayDsLamVdc(@Valid  @RequestParam("portIds") String  portIds) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.xoaVciPort(portIds));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm Vci Vpi Cho Port", description = "Thêm Vci Vpi Cho Port" )
    @RequestMapping(value = "/themVciVpiChoPort", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themVciVpiChoPort(@Valid @RequestBody List<PortVciVpiUpdateDto> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.themVciVpiChoPort(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        catch (JsonProcessingException ignored){}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật port mdf", description = "Cập nhật port mdf" )
    @RequestMapping(value = "/capNhatPortMdf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatPortMdf(@Valid  @RequestParam("rpId") Long rpId, @Valid  @RequestParam("portMdf") Long portMdf) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.capNhatPortMdf(rpId,portMdf))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra thông tin danh bạ", description = "Kiểm tra thông tin danh bạ" )
    @RequestMapping(value = "/kiemTraThongTinDanhBa", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemTraThongTinDanhBa(@Valid @RequestParam("portId") Long portId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.kiemTraThongTinDanhBa(portId))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra thông tin hợp đồng", description = "Kiểm tra thông tin hợp đồng" )
    @RequestMapping(value = "/kiemTraThongTinHopDong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemTraThongTinHopDong(@Valid @RequestParam("portId") Long portId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.kiemTraThongTinHopDong(portId))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Cập nhật trạng thái port", description = "Cập nhật trạng thái port" )
    @RequestMapping(value = "/capnhattrangthaiport", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatTrangThaiPort(@Valid @RequestParam("portId") Long portId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.capNhatTrangThaiPort(portId))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Vdc", description = "Cập nhật Vdc" )
    @RequestMapping(value = "/capNhatVDC", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatVDC(@Valid @RequestParam("dslamVdcId") Long dslamVdcId,@Valid @RequestParam("dsPortId") String dsPortId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.capNhatVDC(dslamVdcId,dsPortId))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "lấy Dslam Theo Trạm Tbi ID", description = "lấy Dslam Theo Trạm Tbi ID" )
    @RequestMapping(value = "/dsLayDsLamTheoTramTbiID", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsLayDsLamTheoTramTbiID(@Valid @RequestParam("donViId")  Long donViId, @RequestParam("congNgheId") long congNgheId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayDsLamTheoTramTbiID(donViId,congNgheId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    //Dslam Tỉnh
    @Operation(summary = "lấy Dslam Tỉnh Theo Trạm Tbi ID", description = "lấy Dslam Tỉnh Theo Trạm Tbi ID" )
    @RequestMapping(value = "/dsLayDsLamTinhTheoTramTbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsLayDsLamTinhTheoTramTbi(@Valid @RequestParam("donViId")  Long donViId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayDsLamTinhTheoTramTbi(donViId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xoá port", description = "Xoá port" )
    @RequestMapping(value = "/xoaPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaPort(@Valid @RequestParam("portId")  String portId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thongSoPortService.xoaPort(portId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá port", description = "Xoá port" )
    @RequestMapping(value = "/xoaPortV2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaPortV2(@Valid @RequestBody XoaDsPortInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thongSoPortService.xoaPortV2(input.getIds())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    //Ds port 2
    @Operation(summary = "lấy ds port v2", description = "lấy ds port v2" )
    @RequestMapping(value = "/layDsPortV2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsPortV2(@Valid @RequestBody  DsPortLogicV2Dot dsPortLogicV2Dot) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.layDsPortV2(dsPortLogicV2Dot));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }
    
  //Ds port 3
    @Operation(summary = "lấy ds port v3 - lấy theo DsLamID và ds PortID", description = "lấy ds port v3" )
    @RequestMapping(value = "/layDsPortV3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsPortV3(@Valid @RequestBody  DsPortLogicV2Dot dsPortLogicV2Dot) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.layDsPortV3(dsPortLogicV2Dot));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }

    @Operation(summary = "lấy dich vu", description = "lấy dich vu" )
    @RequestMapping(value = "/layDichVu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDichVu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.layDichVu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "lấy spliter", description = "lấy spliter" )
    @RequestMapping(value = "/laySpliterTheoTram", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laySpliterTheoTram(@Valid @RequestParam("tramtbi")  Long tramtbi) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.laySpliterTheoTram(tramtbi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy loai hinh chưa gán", description = "Lấy loại hình chưa gán" )
    @RequestMapping(value = "/dsLayLoaiHinhChuaGan", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayLoaiHinhChuaGan(@Valid  @RequestParam("vrpId") Long  vrpId,@Valid  @RequestParam("dichVuId") Long  dichVuId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayLoaiHinhChuaGan(vrpId,dichVuId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật trạng thái port v2 " , description = "Cập nhật trạng thái port v2" )
    @RequestMapping(value = "/capNhatTrangThaiPortV2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatTrangThaiPortV2(@Valid @RequestBody CapNhatPort capNhatPort) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.capNhatTrangThaiPortV2(capNhatPort))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gán splitter theo port " , description = "Gán splitter theo port" )
    @RequestMapping(value = "/ganSplitterTheoPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ganSplitterTheoPort(@Valid @RequestBody CapNhatSplit capNhatSplit) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.ganSplitterTheoPort(capNhatSplit))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gán LH port " , description = "Gán LH port" )
    @RequestMapping(value = "/ganLhChoPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ganLhChoPort(@Valid @RequestBody LoaiHinhDaGanInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(new ResultOutput(thongSoPortService.ganLhChoPort(item.getVrpId(), item.getLoaiTbId()))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }



    @Operation(summary = "Lấy loai hinh chưa gán v2", description = "Lấy loại hình chưa gán v2" )
    @RequestMapping(value = "/dsLayLoaiHinhChuaGanV2", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayLoaiHinhChuaGanV2(@Valid  @RequestParam("dichVuId") Long  dichVuId,@Valid  @RequestParam("dsloaitbId") String  dsloaitbId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayLoaiHinhChuaGanV2(dichVuId,dsloaitbId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy trạng thái dslam", description = "Lấy trạng thái dslam" )
    @RequestMapping(value = "/dsLayTTDslam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayTTDslam(@Valid  @RequestParam("dslamId") Long  dslamId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayTTDslam(dslamId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }



    @Operation(summary = "Lấy dịch vụ V2", description = "Lấy dịch vụ V2" )
    @RequestMapping(value = "/dsLayDichVuV2", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLayDichVuV2() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.dsLayDichVuV2());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy ds Dai port", description = "Lấy ds dai port" )
    @RequestMapping(value = "/layDsDaiPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsDaiPort(@Valid @RequestBody DaiPortDto daiPortDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.layDsDaiPort(daiPortDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Tạo ds Dai port", description = "Lấy ds dai port" )
    @RequestMapping(value = "/TaoDsDaiPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> TaoDsDaiPort(@Valid @RequestBody TaoDaiPortDto daiPortDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.TaoDsDaiPort(daiPortDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoa ds Dai port", description = "Xoa ds dai port" )
    @RequestMapping(value = "/XoaDaiPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaDaiPort(@Valid @RequestBody XoaDaiPortDto daiPortDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.XoaDaiPort(daiPortDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Sinh pass onu cho port", description = "Sinh pass onu cho port" )
    @RequestMapping(value = "/sinhPassOnuPortId", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> sinhPassOnuPortId(@Valid @RequestParam("portId") Long  portId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.sinhPassOnuPortId(portId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cap nhat port mdf da gan", description = "Cap nhat port mdf da gan" )
    @RequestMapping(value = "/updatePortMdf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updatePortMdf(@Valid @RequestParam("vrpDaGan") Long  vrpDaGan ,@Valid @RequestParam("portMdf") Long  portMdf) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.updatePortMdf(vrpDaGan,portMdf));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiem tra thong tin danh ba", description = "Kiem tra thong tin danh ba" )
    @RequestMapping(value = "/kiemTraTTDanhBa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemTraTTDanhBa(@Valid @RequestBody PortArrayDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.kiemTraTTDanhBa(item.getDsPort()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiem tra thong tin hop dong", description = "Kiem tra thong tin hop dong" )
    @RequestMapping(value = "/kiemTraTTHopDong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemTraTTHopDong(@Valid @RequestBody PortArrayDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.kiemTraTTHopDong(item.getDsPort()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update port form", description = "Update port form" )
    @RequestMapping(value = "/updateTTportForm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateTTportFormChinh(@Valid @RequestBody PortArrayDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.updateTTportFormChinh(item.getDsPort()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "UpdateVdc Port", description = "UpdateVdc Port" )
    @RequestMapping(value = "/updateVcdIdPort", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateVcdIdPort(@Valid @RequestBody PortVdcIdDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.updateVcdIdPort(item.getDslamVcdId(),item.getDsPort()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
   

    @Operation(summary = "Lấy max port mdf hcm", description = "Lấy max port mdf hcm" )
    @RequestMapping(value = "/dsLayMaxPortMdfHcm", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layMaxPortMdfHcm(@Valid  @RequestParam("dslamId") Long  dslamId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongSoPortService.layMaxPortMdfHcm(dslamId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Cập nhật trạng thái port v3" , description = "Cập nhật trạng thái port v3" )
    @RequestMapping(value = "/capNhatTrangThaiPortV3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatTrangThaiPortV3(@Valid @RequestBody CapNhatTTDsPortInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thongSoPortService.capNhatTrangThaiPortV3(input.getIds(), input.getTrangThaiID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật port V2", description = "Cập nhật port" )
    @RequestMapping(value = "/capnhatPortV2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatPortV2(@Valid @RequestBody PortDtoInput.PortDtoUpdInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thongSoPortService.capNhatPortV2(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
