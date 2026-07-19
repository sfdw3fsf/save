package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyGenericInput;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiMayAoDTO;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLoaiMayAoService;

import javax.validation.Valid;


@RestController
@RequestMapping("danhmuc-loai-mayao")
public class DanhMucLoaiMayAoController {
    private final DanhMucLoaiMayAoService danhMucLoaiMayAoService;
    DanhMucLoaiMayAoController(DanhMucLoaiMayAoService danhMucLoaiMayAoService){
        this.danhMucLoaiMayAoService = danhMucLoaiMayAoService;
    }
    @Operation(summary = "Lấy danh sách loại máy áo")
    @RequestMapping(value = "lay-dsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhsach(){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(danhMucLoaiMayAoService.getDanhsach());
        }catch (AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật loại máy ảo")
    @RequestMapping(value = "upsert", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> upsert(@Valid @RequestBody LoaiMayAoDTO input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(new ResultOutput(danhMucLoaiMayAoService.upsert(input)));
        }catch (AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa loại máy ảo")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@RequestBody DataKeyInput input){
        ApiResult rs = new ApiResult();
        try{
            rs.setData(new ResultOutput(danhMucLoaiMayAoService.delete(input.getId())));
        }catch (AppSqlException e){
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều loại máy ảo")
    @RequestMapping(value = "delete-items", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteItems(@RequestBody DataKeyGenericInput<Number[]> input){
        ApiResult rs = new ApiResult();
        try{
            boolean flag = true;
            Number[] ids = input.getId();
            if(ids == null || ids.length == 0){
                flag = false;
            }else{
                for(Number id: ids){
                    if(id == null || id.intValue() < 1){
                        flag = false;
                        break;
                    }
                }
            }
            if(flag){
                rs.setData(new ResultOutput(danhMucLoaiMayAoService.deleteItems(ids)));
            }
        }catch (AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

