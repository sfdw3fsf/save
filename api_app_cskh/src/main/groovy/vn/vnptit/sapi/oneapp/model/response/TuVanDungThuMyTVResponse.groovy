package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString
import io.swagger.model.dto.FileApp
import vn.vnptit.sapi.oneapp.util.Constant

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Tư vấn dùng thử MyTV
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class TuVanDungThuMyTVResponse {
    Long phieutn_id
    Long khachhang_id
    Long hdkh_id
    String ten_kh
    String diachi_kh
    String so_dt
    String ma_kh
    String nguon
    String nguon_tt
    Long ttlh_id
    Float kinhdo
    Float vido
    String ghichu
    String mota
    String ngaygiao
    String loai_nc
    Long loainc_id
    String nhiemvu
    String ma_tb
    String ghichu_tn
    String trangthai_tn
    List<FileApp> listImage

    TuVanDungThuMyTVResponse convertFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            phieutn_id = rs.getLong("phieutn_id")
            khachhang_id = rs.getLong("khachhang_id")
            hdkh_id = rs.getLong("hdkh_id")
            ten_kh = rs.getString("ten_kh")
            diachi_kh = rs.getString("diachi_kh")
            so_dt = rs.getString("so_dt")
            ma_kh = rs.getString("ma_kh")
            nguon = rs.getString("nguon")
            nguon_tt = rs.getString("nguon_tt")
            ttlh_id = rs.getLong("ttlh_id")
            kinhdo = rs.getFloat("kinhdo")
            vido = rs.getFloat("vido")
            ghichu = rs.getString("ghichu")
            mota = rs.getString("mota")
            ngaygiao = rs.getDate("ngaygiao")?.format(Constant.FORMAT_DATETIME)
            loai_nc = rs.getString("loai_nc")
            loainc_id = rs.getLong("loainc_id")
            nhiemvu = rs.getString("nhiemvu")
            ma_tb = rs.getString("ma_tb")
            ghichu_tn = rs.getString("ghichu_tn")
            trangthai_tn = rs.getString("trangthai_tn")
            return this
        } else {
            return null
        }
    }
}
