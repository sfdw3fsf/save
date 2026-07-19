package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatPort;
import vn.vnpt.vnptit.ecms.dto.CapNhatSplit;
import vn.vnpt.vnptit.ecms.dto.DaiPortDto;
import vn.vnpt.vnptit.ecms.dto.DsPortLogicV2Dot;
import vn.vnpt.vnptit.ecms.dto.PortDtoInput;
import vn.vnpt.vnptit.ecms.dto.PortVciVpiUpdateDto;
import vn.vnpt.vnptit.ecms.dto.TaoDaiPortDto;
import vn.vnpt.vnptit.ecms.dto.XoaDaiPortDto;

@Service
public class ThongSoPortService {

    private final DbContext dbContext;

    public ThongSoPortService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> dsDonViQuanLy(Long donviId, Long loaidvId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", donviId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidv_id", loaidvId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_DONVI_QUANLY", params);
    }

    public ArrayList<Map<String, Object>> dsLayTrangThaiPort() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_TRANG_THAI_PORT", params);
    }

    public ArrayList<Map<String, Object>> dsLayRackShelf() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_RACK_SHELF", params);
    }

    public ArrayList<Map<String, Object>> dsLayBrasAdsl() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_BRAS_ADSL", params);
    }

    public ArrayList<Map<String, Object>> dsLayTramTc(Long donviId, Long loaidvId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", donviId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidv_id", loaidvId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_TRAM_TC", params);
    }

    public ArrayList<Map<String, Object>> dsLayTramTb(Long donviId, Long loaidvId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", donviId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidv_id", loaidvId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_TRAM_TB", params);
    }

    //Check have dslam
    public ArrayList<Map<String, Object>> dsLayDslam(Long donviId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", donviId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_DSLAM", params);
    }

    public ArrayList<Map<String, Object>> dsLayVciVpiChuaGan(Long dslamId,Long portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_DanhSachVCI_VPIChuaGan", params);
    }

    public ArrayList<Map<String, Object>> dsLayVciVpiDaGan(Long dslamId,Long portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_DanhSachVCI_VPIDaGan", params);
    }

    public ArrayList<Map<String, Object>> dsLayDsCard(Long dslamId,Long loaiTbId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitbi_id", loaiTbId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_DANH_SACH_CARD", params);
    }

    public ArrayList<Map<String, Object>> dsLayDsPort(Long donviId,Long portId,Long dslamId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdonvi_id", donviId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vtt_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vdslam_id", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_DANH_SACH_PORT", params);
    }

    public ArrayList<Map<String, Object>> dsLayLoaiHinhDaGan(Long vrpId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vrp_id", vrpId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_LOAIHINH_TB_DaGan", params);
    }

    public ArrayList<Map<String, Object>> dsLayDsLamVdc(Long brasId,Long kieu) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_bras_id", brasId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_kieu", kieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_DSLAM_VDC", params);
    }

    public ArrayList<Map<String, Object>> themPort(PortDtoInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_slot", input.getSlot(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_port", input.getPort(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rack_id", input.getRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tt_port_id", input.getTtPortId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_selfid01", input.getSelfid01(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid02", input.getSelfid02(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid03", input.getSelfid03(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid04", input.getSelfid04(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_cn",  new Date(), Types.DATE));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dslam_vdc_id", input.getDslamVdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_int", input.getVlanInt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dslam_id", input.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_PORT_INS", params);
    }

    public boolean capNhatPort(PortDtoInput.PortDtoUpdInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", input.getPortId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_slot", input.getSlot(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_port", input.getPort(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rack_id", input.getRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tt_port_id", input.getTtPortId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_selfid01", input.getSelfid01(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid02", input.getSelfid02(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid03", input.getSelfid03(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid04", input.getSelfid04(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_cn",  new Date(), Types.DATE));
        params.add(new SqlParameter("p_may_cn",AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",  AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dslam_vdc_id", input.getDslamVdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_int", input.getVlanInt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dslam_id", input.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_PORT_UPD", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());
    }

    public ArrayList<Map<String, Object>> xoaVciPort(String portIds) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ds_rp_id_xoa",  StringUtils.join( portIds, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_PORT_VCI_VPI_DEL", params);
    }

    public boolean themVciVpiChoPort(List<PortVciVpiUpdateDto> input) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LIST", input != null ? objectMapper.writeValueAsString(input) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_VCI_VPI_FOR_PORT_INS", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());
    }


    public boolean capNhatPortMdf(Long rpId, Long portMdf) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rp_id", rpId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_mdf",portMdf, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_PORT_MDF_UPD", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }



    public boolean kiemTraThongTinDanhBa(Long portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_KIEMTRA_THONGTIN_DANHBA", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }

    public boolean kiemTraThongTinHopDong(Long portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_KIEMTRA_THONGTIN_HOPDONG", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }


    public boolean capNhatTrangThaiPort(Long portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_TRANGTHAI_PORT", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }


    public boolean capNhatVDC(Long dslamVdcId , String dsPortId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_vdc_id", dslamVdcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ds_port_id", dsPortId, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_CAPNHAT_VDC", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }

    public ArrayList<Map<String, Object>> dsLayDsLamTheoTramTbiID(Long donViId,Long congNgheId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dovi_id", donViId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_congnghe_id", congNgheId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_DSLAM_THEO_TRAMTB_ID", params);
    }
    public ArrayList<Map<String, Object>> dsLayDsLamTinhTheoTramTbi(Long donViId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", donViId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_DSLAM_THEO_TRAMTB", params);
    }

    public Boolean xoaPort(String portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("CSS.XOA_PORT", params);
        return  "OK".equals(list.get(0).get("KETQUA").toString());

    }

    public Boolean xoaPortV2(Number[] portIds) throws AppSqlException {
    	String ids = Arrays.toString(portIds);
    	ids = ids.substring(1, ids.length() - 1);
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", ids, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("CSS.XOA_PORT", params);
        return  "OK".equals(list.get(0).get("KETQUA").toString());

    }

    public Map<String, Object>  layDsPortV2(DsPortLogicV2Dot dsPortLogicV2Dot) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", dsPortLogicV2Dot.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_index", dsPortLogicV2Dot.getPageIndex(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_size", dsPortLogicV2Dot.getPageSize(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        SqlParameter parameterTotalRow = new SqlParameter("p_total_row", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        params.add(parameterTotalRow);
        SqlParameter parameterCursor = new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        params.add(parameterCursor);
        dbContext.executeStoredProcedure("CSS.PKG_THONG_SO_PORT.lay_ds_port_logic_v2", params);

        Map<String, Object> result = new HashMap<>();
        result.put("pageItems", parameterCursor.getOutValue());
        result.put("allItems", parameterTotalRow.getOutValue());
        return result;
    }
    
    public Map<String, Object>  layDsPortV3(DsPortLogicV2Dot dsPortLogicV2Dot) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", dsPortLogicV2Dot.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_ids", dsPortLogicV2Dot.getPortIds(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_page_index", dsPortLogicV2Dot.getPageIndex(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_size", dsPortLogicV2Dot.getPageSize(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        SqlParameter parameterTotalRow = new SqlParameter("p_total_row", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        params.add(parameterTotalRow);
        SqlParameter parameterCursor = new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        params.add(parameterCursor);
        dbContext.executeStoredProcedure("CSS.PKG_THONG_SO_PORT.lay_ds_port_logic_v3", params);
       
        Map<String, Object> result = new HashMap<>();
        result.put("pageItems", parameterCursor.getOutValue());
        result.put("allItems", parameterTotalRow.getOutValue());
        return result;
    }

    public ArrayList<Map<String, Object>> layDichVu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_GET_DICHVU_PORT", params);
    }

    public ArrayList<Map<String, Object>> laySpliterTheoTram(Long tramtbi) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tramtbi_id", tramtbi, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_GET_SPLITTER_THEO_TRAM", params);
    }


    public ArrayList<Map<String, Object>> dsLayLoaiHinhChuaGan(Long vrpId,Long dichVuId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vrp_id", vrpId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvu_id", dichVuId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LOAIHINH_CHUAGAN", params);
    }


    public boolean capNhatTrangThaiPortV2(CapNhatPort  capNhatPort) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vrp_id",  StringUtils.join(capNhatPort.getVrpId(), ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai", capNhatPort.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_UPDATE_TT_PORT_V2", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }

    public boolean ganSplitterTheoPort(CapNhatSplit capNhatSplit) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vrp_id",  StringUtils.join(capNhatSplit.getVrpId(), ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vketcuoi_id", capNhatSplit.getKetCuoiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.Gan_spliter_vao_port", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }
    public boolean ganLhChoPort(Integer[] vrpId, Integer[] loaiTbId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rp_id",  StringUtils.join(vrpId, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loaitb_id",  StringUtils.join(loaiTbId, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_UPDATE_PORT_LHTB", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());

    }



    public ArrayList<Map<String, Object>> dsLayLoaiHinhChuaGanV2(Long dichVuId, String dsloaitbId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdichvuvt_id", dichVuId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdsloaitb_id", dsloaitbId, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_CHUA_GAN_V2", params);
    }

    public ArrayList<Map<String, Object>> dsLayTTDslam(Long dslamId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdichvuvt_id", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_TT_DSLAM", params);
    }

    public ArrayList<Map<String, Object>> dsLayDichVuV2() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_DICHVU_V2", params);
    }

    public  Map<String, Object> layDsDaiPort(DaiPortDto daiPortDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdslam_id", daiPortDto.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vrack_id", daiPortDto.getRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vslot_bd", daiPortDto.getSlotBd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vslot_kt", daiPortDto.getSlotKt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_index", daiPortDto.getPageIndex(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_size", daiPortDto.getPageSize(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        SqlParameter parameterTotalRow = new SqlParameter("p_total_row", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        params.add(parameterTotalRow);
        SqlParameter parameterCursor = new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        params.add(parameterCursor);
        dbContext.executeStoredProcedure("CSS.PKG_THONG_SO_PORT.LAY_DS_DAIPORT", params);

        Map<String, Object> result = new HashMap<>();
        result.put("pageItems", parameterCursor.getOutValue());
        result.put("allItems", parameterTotalRow.getOutValue());
        return result;
    }


    public ArrayList<Map<String, Object>> TaoDsDaiPort(TaoDaiPortDto daiPortDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vslot_bd", daiPortDto.getSlot_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vslot_kt", daiPortDto.getSlot_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vport_bd", daiPortDto.getPort_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vport_kt", daiPortDto.getPort_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvci_bd", daiPortDto.getVci_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvci_kt", daiPortDto.getVci_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvpi_bd", daiPortDto.getVpi_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvpi_kt", daiPortDto.getVpi_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdslam_id", daiPortDto.getDslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vnhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vport_mdf", daiPortDto.getPort_mdf(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vnguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vmay_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vcard_id", daiPortDto.getCard_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid01", daiPortDto.getSelfid01(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vselfid02", daiPortDto.getSelfid02(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vselfid03_bd", daiPortDto.getSelfid03_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid03_kt", daiPortDto.getSelfid03_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid04_bd", daiPortDto.getSelfid04_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid04_kt", daiPortDto.getSelfid04_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdsloaitb_id",  StringUtils.join(daiPortDto.getDsloaitb_id(), ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vrack_id", daiPortDto.getRack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdslam_vdc_id", daiPortDto.getDslam_vdc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
       // params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
//        || AppRequestContext.getCurrent().getPhanVungID() == 15
        if( AppRequestContext.getCurrent().getPhanVungID() == 21 ){
            return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.TAO_DAI_PORT_VL_V2_HNI", params);
        }else if (AppRequestContext.getCurrent().getPhanVungID() == 28) {
            return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.TAO_DAI_PORT_VL_V2_HCM", params);
        } else{
            return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.TAO_DAI_PORT_VL_V2", params);
        }
    }


    public ArrayList<Map<String, Object>> XoaDaiPort(XoaDaiPortDto daiPortDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vslot_bd", daiPortDto.getSlot_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vslot_kt", daiPortDto.getSlot_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vport_bd", daiPortDto.getPort_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vport_kt", daiPortDto.getPort_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvci_bd", daiPortDto.getVci_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvci_kt", daiPortDto.getVci_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvpi_bd", daiPortDto.getVpi_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvpi_kt", daiPortDto.getVpi_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vsystem", daiPortDto.getSystem(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vrack_id", daiPortDto.getRack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vthamso", daiPortDto.getThamso(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vdslam_id", daiPortDto.getDslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid01", daiPortDto.getSelfid01(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid02", daiPortDto.getSelfid02(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid03_bd", daiPortDto.getSelfid03_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid03_kt", daiPortDto.getSelfid03_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid04_bd", daiPortDto.getSelfid04_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vselfid04_kt", daiPortDto.getSelfid04_kt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vnhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vnguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("vmay_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
//        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.XOA_DAI_PORT", params);

    }



    public ArrayList<Map<String, Object>> sinhPassOnuPortId(Long portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", portId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SINH_PASS_ONU_PORT_ID", params);

    }

    public ArrayList<Map<String, Object>> updatePortMdf(Long vrpDaGan, Long portMdf) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vrp_id_dagan", vrpDaGan, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_mdf", portMdf, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_CAPNHAT_PORT_MDF_DAGAN", params);

    }


    public ArrayList<Map<String, Object>> kiemTraTTDanhBa(Integer[] p_port) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port",  StringUtils.join(p_port, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_KIEMTRA_THONGTIN_DANHBA", params);

    }

    public ArrayList<Map<String, Object>> kiemTraTTHopDong(Integer[] p_port) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port",  StringUtils.join(p_port, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_KIEMTRA_THONGTIN_HOPDONG", params);

    }

    public ArrayList<Map<String, Object>> updateTTportFormChinh(Integer[] p_port) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port",  StringUtils.join(p_port, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_UPDATE_TT_PORT_FORM", params);

    }


    public ArrayList<Map<String, Object>> updateVcdIdPort(Long dslamVdcId ,Integer[] dsPort) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_vdc_id", dslamVdcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ds_port_id",  StringUtils.join(dsPort, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_UPDATE_VCD_ID", params);

    }

    public ArrayList<Map<String, Object>> layMaxPortMdfHcm(Long dslamId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("DSLAMID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_LAY_MAX_PORT_MDF_HCM", params);
    }


    public Boolean capNhatTrangThaiPortV3(Number[] portIds, int trangThaiID) throws AppSqlException {
    	String ids = Arrays.toString(portIds);
    	ids = ids.substring(1, ids.length() - 1);
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_ids", ids, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tt_port_id", trangThaiID, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_CAPNHAT_TT_PORT_V3", params);
        return "1".equals(list.get(0).get("KQ").toString());

    }

    public boolean capNhatPortV2(PortDtoInput.PortDtoUpdInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", input.getPortId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_slot", input.getSlot(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_port", input.getPort(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rack_id", input.getRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tt_port_id", input.getTtPortId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_selfid01", input.getSelfid01(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid02", input.getSelfid02(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid03", input.getSelfid03(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_selfid04", input.getSelfid04(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_cn",  new Date(), Types.DATE));
        params.add(new SqlParameter("p_may_cn",AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",  AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dslam_vdc_id", input.getDslamVdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_int", input.getVlanInt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dslam_id", input.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_card_id", input.getCardId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("CSS.PKG_THONG_SO_PORT.SP_PORT_UPD_V2", params);
        return  "1".equals(list.get(0).get("KETQUA").toString());
    }

}
