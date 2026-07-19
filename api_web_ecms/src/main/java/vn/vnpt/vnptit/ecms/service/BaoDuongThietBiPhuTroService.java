package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_CapNhatVanHanhKMInput;

@Service
public class BaoDuongThietBiPhuTroService {
	private static final Logger logger = LoggerFactory.getLogger(BaoDuongThietBiPhuTroService.class);
    private final DbContext dbContext;

    public BaoDuongThietBiPhuTroService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object lay_ds_vattu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.SP_VATTU_SEL", params);
    }
    public Object phutro_lay_ds_vanhanh_km(int vanhanh_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("vvanhanh_id", vanhanh_id, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.phutro_lay_ds_vanhanh_km", params);
    }
    public Object phutro_lay_ds_khmuc(int kieu) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("vkieu", kieu, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.phutro_lay_ds_khmuc", params);
    }
    public Object phutro_capnhap_vanhanh_km(QLTBPT_CapNhatVanHanhKMInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("vkieu", input.getKieu(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("vvanhanh_id", input.getVanhanh_id(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("vkhoanmuc_id", input.getKhoanmuc_id(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("vkhoanmuc_id_2", input.getKhoanmuc_id_2(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("vsoluong", input.getSoluong(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("vthanhtien", input.getThanhtien(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("vghichu", input.getGhichu(), Types.VARCHAR));
        try {
            params.add(new SqlParameter("vtugio",new SimpleDateFormat("DD/MM/yyyy HH:mm:ss").parse(input.getTugio()) , Types.DATE));
        } catch (ParseException e) {
        	logger.error("NoSuchFieldException occurred while processing field error.", e);
        }
        try {
            params.add(new SqlParameter("vdengio", new SimpleDateFormat("DD/MM/yyyy HH:mm:ss").parse(input.getDengio()) , Types.DATE));
        } catch (ParseException e) {
        	logger.error("NoSuchFieldException occurred while processing field error.", e);
        }
        return dbContext.executeSpWithCursorToMap("ECMS.phutro_capnhap_vanhanh_km", params);
    }
}
