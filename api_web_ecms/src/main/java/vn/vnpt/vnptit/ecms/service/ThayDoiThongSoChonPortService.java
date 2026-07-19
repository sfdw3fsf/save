package vn.vnpt.vnptit.ecms.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.PortQuyHoachDto;

@Service
public class ThayDoiThongSoChonPortService {
    private static final Logger logger = LoggerFactory.getLogger(ThayDoiThongSoChonPortService.class);
    private final DbContext dbContext;

    public ThayDoiThongSoChonPortService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object lay_ds_port_quy_hoach(PortQuyHoachDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_dslam_id", input.getP_dslam_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getP_trangthai_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_page_size", input.getP_page_size(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_page_index", input.getP_page_index(), java.sql.Types.NUMERIC));
        params.add(
                new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_GET_DS_PORT_QUYHOACH", params);
    }

    public Object lay_ds_port_quy_hoachV2(PortQuyHoachDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_dslam_id", input.getP_dslam_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getP_trangthai_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_slot", input.getP_slot(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_port", input.getP_port(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ont", input.getP_ont(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_page_size", input.getP_page_size(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_page_index", input.getP_page_index(), java.sql.Types.NUMERIC));
        params.add(
                new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_GET_DS_PORT_QUYHOACH_V2", params);
    }

    public Object lay_count_port_quy_hoach(PortQuyHoachDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_dslam_id", input.getP_dslam_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getP_trangthai_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.INTEGER));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_COUNT_DS_PORT_QUYHOACH", params);
    }

    public Object lay_count_port_quy_hoach_v2(PortQuyHoachDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_dslam_id", input.getP_dslam_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getP_trangthai_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_slot", input.getP_slot(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_port", input.getP_port(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ont", input.getP_ont(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.INTEGER));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_COUNT_DS_PORT_QUYHOACH_V2", params);
    }

    public Object lay_count_port_quy_hoach_v3(PortQuyHoachDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_dslam_id", input.getP_dslam_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_splitter_id", input.getP_splitter_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getP_trangthai_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_slot", input.getP_slot(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_port", input.getP_port(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ont", input.getP_ont(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.INTEGER));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_COUNT_DS_PORT_QUYHOACH_V3", params);
    }

    public Object lay_ds_port_quy_hoachV3(PortQuyHoachDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_dslam_id", input.getP_dslam_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_splitter_id", input.getP_splitter_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getP_trangthai_id(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_slot", input.getP_slot(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_port", input.getP_port(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ont", input.getP_ont(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_page_size", input.getP_page_size(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_page_index", input.getP_page_index(), java.sql.Types.NUMERIC));
        params.add(
                new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_GET_DS_PORT_QUYHOACH_V3", params);
    }
}
