package vn.vnpt.vnptit.ecms.service.idc;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.SoDoKetNoiDto;

@Service
public class SoDoKetNoiService {
        private final DbContext dbContext;

        public SoDoKetNoiService(DbContext dbContext) {
                this.dbContext = dbContext;
        }

        public ArrayList<Map<String, Object>> luuBoCucSoDo(SoDoKetNoiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_doituong_id", dto.getDoiTuongId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_doituong", dto.getDoiTuong(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(new SqlParameter("p_du_lieu", dto.getDuLieu(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.CLOB));
                parameters.add(new SqlParameter("p_mo_ta", dto.getMoTa(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI.SP_LUU_BOCUC_SODO",
                                parameters);
        }

        public ArrayList<Map<String, Object>> layBoCucSoDoKetNoi(SoDoKetNoiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_doituong_id", dto.getDoiTuongId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_doituong", dto.getDoiTuong(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI.SP_LAY_BC_SODO_KETNOI",
                                parameters);
        }
}
