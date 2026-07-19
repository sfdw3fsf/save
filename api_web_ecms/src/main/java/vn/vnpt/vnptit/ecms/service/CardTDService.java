package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CardTDDtoInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CardTDService {
    private final DbContext dbContext;

    public CardTDService(DbContext _dbcontext) {
        this.dbContext = _dbcontext;
    }

    public ArrayList<Map<String, Object>> layThongTinFrameCard(long cardtd_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CARDTD_ID", cardtd_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_THONGTINFRAME_CARD_SEL", params);
    }

    public ArrayList<Map<String, Object>> layThongTinPortSuDung(long cardtd_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CARDTD_ID", cardtd_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_PORTTDCARDSUDUNG_SEL", params);
    }

    public ArrayList<Map<String, Object>> layDsPortTheoCardTD(long cardtd_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CARDTD_ID", cardtd_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_PORT_THEO_CARDTD_SEL", params);
    }

    public ArrayList<Map<String, Object>> layThongTinLoaiCard(long loaicard_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAICARD_ID", loaicard_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_CURS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_LAYTHONGTIN_LOAI_CARD", params);
    }

    public ArrayList<Map<String, Object>> themCardTD(CardTDDtoInput input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAICARD_ID", input.getLoaicard_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_FRAMETD_ID", input.getFrametd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", input.getVitri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_BD", input.getPortbd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", input.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_PARTNUMBER", input.getPartNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_CARD_TD_INS", params);
    }

    public ArrayList<Map<String, Object>> taoPortCardTD(long cardtd_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("VCARDTD_ID", cardtd_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_CURS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.TAO_PORT_CARD_TRUYENDAN", params);
    }

    public ArrayList<Map<String, Object>> capNhatCardTD(CardTDDtoInput input, long cardtd_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CARDTD_ID", cardtd_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAICARD_ID", input.getLoaicard_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_FRAMETD_ID", input.getFrametd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", input.getVitri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_BD", input.getPortbd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", input.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_PARTNUMBER", input.getPartNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_CARD_TD_UPD", params);
    }

    public ArrayList<Map<String, Object>> xoaCardTD(long cardtd_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CARD_ID", cardtd_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_CURS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_BANGRONG_TRUYENDAN.SP_CARD_TD_DEL", params);
    }
}
