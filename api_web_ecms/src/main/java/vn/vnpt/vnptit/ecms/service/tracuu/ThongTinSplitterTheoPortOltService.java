package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.TraCuuDsSplitterTheoPortOltDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ThongTinSplitterTheoPortOltService {

    private final DbContext dbContext;

    public ThongTinSplitterTheoPortOltService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> traCuuDsSplitterTheoPortOlt(TraCuuDsSplitterTheoPortOltDtoIn paramDto)
            throws AppSqlException
    {
    	String ttvtID = paramDto.getTtvtId();
    	String toktID = paramDto.getToktId();
    	String tramtbID = paramDto.getTramtbId();
    	String oltID = paramDto.getOltId();
    	String cardOltID = paramDto.getCardOltId();
    	String lstPortVlID = paramDto.getLstPortVlId();
    	String kyHieu = paramDto.getKyHieu();
    	String diaChi = paramDto.getDiaChi();
    	if(!this.checkNullOfString(lstPortVlID) || !this.checkNullOfString(kyHieu) || !this.checkNullOfString(diaChi)
    			 || !this.checkNullOfNumber(ttvtID) || !this.checkNullOfNumber(toktID) || !this.checkNullOfNumber(tramtbID)
    			 || !this.checkNullOfNumber(oltID) || !this.checkNullOfNumber(cardOltID) || !this.checkNullOfNumber(lstPortVlID))
    	{
            ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ttvt_id", ttvtID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_tokt_id", toktID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_tramtb_id", tramtbID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_olt_id", oltID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_cardolt_id", cardOltID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_lst_port_vl_id", lstPortVlID, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ky_hieu", kyHieu, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_dia_chi", diaChi, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ds_splitter", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
            return dbContext.executeSpWithCursorToListMap("ecms.pkg_tracuumnv.sp_ds_splitter_theo_port_olt", params);
    	}
    	return null;
    }
    
    private boolean checkNullOfString(String value) {
    	return value == null || value == "";
    }
    
    private boolean checkNullOfNumber(String value) {
    	return value == null || value == "" || value.toString().equals("-1");
    }
}
