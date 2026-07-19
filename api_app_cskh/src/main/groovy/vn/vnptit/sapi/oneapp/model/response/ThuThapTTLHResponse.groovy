package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString
import io.swagger.model.dto.FileApp
import vn.vnptit.sapi.oneapp.util.Constant

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Thu thập thông tin liên hệ
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThuThapTTLHResponse {
    Long phieutn_id
    Long khachhang_id
    Long hdkh_id
    String ten_kh
    String diachi_kh
    String so_dt
    String ngaygiao
    String ma_kh
    String ma_tb
    Long ttlh_id
    Float kinhdo
    Float vido
    String ghichu
    String mota
    String nguon
    String ngay_cn
    String nguon_tt
    String loai_nc
    Long loainc_id
    String nhiemvu
    List<FileApp> listImage

    ThuThapTTLHResponse convertFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            phieutn_id = rs.getLong("phieutn_id")
            khachhang_id = rs.getLong("khachhang_id")
            hdkh_id = rs.getLong("hdkh_id")
            ten_kh = rs.getString("ten_kh")
            diachi_kh = rs.getString("diachi_kh")
            so_dt = rs.getString("so_dt")
            ngaygiao = rs.getDate("ngaygiao")?.format(Constant.FORMAT_DATETIME)
            ma_kh = rs.getString("ma_kh")
            ma_tb = rs.getString("ma_tb")
            ttlh_id = rs.getLong("ttlh_id")
            kinhdo = rs.getFloat("kinhdo")
            vido = rs.getFloat("vido")
            ghichu = rs.getString("ghichu")
            mota = rs.getString("mota")
            nguon = rs.getString("nguon")
            ngay_cn = rs.getDate("ngay_cn")?.format(Constant.FORMAT_DATETIME)
            nguon_tt = rs.getString("nguon_tt")
            loai_nc = rs.getString("loai_nc")
            loainc_id = rs.getLong("loainc_id")
            nhiemvu = rs.getString("nhiemvu")
            return this
        } else {
            return null
        }
    }
}
