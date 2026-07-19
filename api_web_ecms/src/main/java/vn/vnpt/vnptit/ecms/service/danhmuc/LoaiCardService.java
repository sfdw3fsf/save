package vn.vnpt.vnptit.ecms.service.danhmuc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.CapNhatLoaiCardDtnIn;
import vn.vnpt.vnptit.ecms.dto.ThemLoaiCardDtnIn;
import vn.vnpt.vnptit.ecms.dto.ThemNhomCardTbiDtnIn;

@Service
public class LoaiCardService {
    @Autowired
    DbContext dbContext;
    public  ArrayList<Map<String, Object>> lay_ds_loaicard()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAICARD.SP_LOAICARD_SEL", parameters);
    }
    public Map<String, Object> lay_ds_loaicard_paging(Integer pageIndex, Integer pageSize)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_page_index", pageIndex, ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_page_size", pageSize, ParameterDirection.INPUT, Types.NUMERIC));
        
        SqlParameter parameterCursor = new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        parameters.add(parameterCursor);
        SqlParameter parameterTotalRow = new SqlParameter("p_total_row", null, ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        parameters.add(parameterTotalRow);
        dbContext.executeStoredProcedure("ECMS.PKG_LOAICARD.SP_LOAICARD_PAGING_SELECT", parameters);
        
        Map<String, Object> result = new HashMap<>();
        result.put("pageItems", parameterCursor.getOutValue());
        result.put("allItems", parameterTotalRow.getOutValue());
        return result;
    }
    public Map<String, Object> lay_loaicard_theo_loaicard_id(Long loaicardId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaicard_id", loaicardId != null ? loaicardId.toString() : "",ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAICARD.SP_LOAICARD_SEL_BY_LOAIDCARD_ID", parameters);
        return rs !=null && rs.size() > 0 ? rs.get(0) : null;
    }

    public Map<String, Object> tao_loaicard(ThemLoaiCardDtnIn loaiCard) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaicard", loaiCard.getLoaicard() != null ? loaiCard.getLoaicard() : "", ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_soport", loaiCard.getSoPort() != null ? loaiCard.getSoPort().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_portbd", loaiCard.getPortBd() != null ? loaiCard.getPortBd().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cardtbi_id", loaiCard.getCardTbiId() != null ? loaiCard.getCardTbiId().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hangsx_id", loaiCard.getHangSxId() != null ? loaiCard.getHangSxId().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_kieumodule", loaiCard.getKieuModule() != null ? loaiCard.getKieuModule().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAICARD.SP_LOAICARD_INS", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }
    public Map<String, Object> tao_nhom_card_tbi(ThemNhomCardTbiDtnIn nhomCardTbiDtnIn) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nhomcard_id", nhomCardTbiDtnIn.getNhomcardId() != null ? nhomCardTbiDtnIn.getNhomcardId() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaitbi_id", nhomCardTbiDtnIn.getLoaitbiId() != null ? nhomCardTbiDtnIn.getLoaitbiId().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAICARD.SP_NHOMCARD_TBI_INS", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }

    public Map<String, Object> capnhat_loaicard(CapNhatLoaiCardDtnIn loaiCard) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaicard_id", loaiCard.getLoaicardId() != null ? loaiCard.getLoaicardId().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaicard", loaiCard.getLoaicard() != null ? loaiCard.getLoaicard() : "", ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_soport", loaiCard.getSoPort() != null ? loaiCard.getSoPort().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_portbd", loaiCard.getPortBd() != null ? loaiCard.getPortBd().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cardtbi_id", loaiCard.getCardTbiId() != null ? loaiCard.getCardTbiId().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hangsx_id", loaiCard.getHangSxId() != null ? loaiCard.getHangSxId().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_kieumodule", loaiCard.getKieuModule() != null ? loaiCard.getKieuModule().toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAICARD.SP_LOAICARD_UPD", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }

    public Boolean xoa_loaicard(Long loaicardId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaicard_id", loaicardId != null ? loaicardId.toString() : "", ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAICARD.SP_LOAICARD_DEL", parameters);
        if (rs != null && rs.size() > 0){
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> lay_danhsach_nhomcard() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAICARD.SP_NHOMCARD_SEL", parameters);
    }
}
