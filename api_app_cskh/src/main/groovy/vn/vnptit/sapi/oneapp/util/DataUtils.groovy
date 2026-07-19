package vn.vnptit.sapi.oneapp.util

import groovy.sql.OutParameter
import groovy.sql.Sql
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.converter.ResultSetToObjectv
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.output.LayPortTheoTBIDOutput

import java.sql.ResultSet

@Component
class DataUtilsv {
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    ResultSetToObjectv resultSetToObject

    Long phanvung_id

    def init(long phanvung_id) {
        this.phanvung_id = phanvung_id
    }

    List<LayPortTheoTBIDOutput> LAY_PORT_THEO_TBID(long vhdtb_id, long vthuebao_id) {
        List<LayPortTheoTBIDOutput> response = []
        String sqlString = "{ call CSS.LAY_PORT_THEO_TBID(?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
            sql.call(sqlString, [phanvung_id, vhdtb_id, vthuebao_id, CURSOR_PARAMETER]) { ResultSet rs ->
                if (rs != null) {
                    response = resultSetToObject.getDanhMuc(rs, LayPortTheoTBIDOutput.class)
                }
            }
        }
        return response
    }

    List<LayPortTheoTBIDOutput> LAY_PORT_THEOMATB(String vma_tb, int vkieu) {
        List<LayPortTheoTBIDOutput> response = []
        String sqlString = "{ call CSS.LAY_PORT_THEOMATB(?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
            sql.call(sqlString, [phanvung_id, vma_tb, vkieu, CURSOR_PARAMETER]) { ResultSet rs ->
                if (rs != null) {
                    response = resultSetToObject.getDanhMuc(rs, LayPortTheoTBIDOutput.class)
                }
            }
        }
        return response
    }

    List<LayPortTheoTBIDOutput> LAY_PORT_THEOMATB_V2(long phanvung_id, String vma_tb, int vkieu) {
        List<LayPortTheoTBIDOutput> response = []
        String sqlString = "{ call CSS.LAY_PORT_THEOMATB(?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
            sql.call(sqlString, [phanvung_id, vma_tb, vkieu, CURSOR_PARAMETER]) { ResultSet rs ->
                if (rs != null) {
                    response = resultSetToObject.getDanhMuc(rs, LayPortTheoTBIDOutput.class)
                }
            }
        }
        return response
    }

    void capnhat_log_action_bnm(String vaction_type, String vma_tb, String vvalue, String vnote, String vnguoi_cn, String vmay_cn) {
        String sqlString = "{ call css.capnhat_log_action_bnm(?,?,?,?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
            sql.call(sqlString, [phanvung_id, vaction_type, vma_tb,
                                 vvalue, vnote, vnguoi_cn, vmay_cn])
        }
    }

}
