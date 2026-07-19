package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString
import io.swagger.model.dto.FileApp
import vn.vnptit.sapi.oneapp.util.Constant

import java.sql.ResultSet
import java.sql.SQLException

/**
 * Chăm sóc khách hàng 36 tháng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class CSKH36Response {
    Long phieutn_id
    Long diachi_id_tt
    String diachi_tt
    Long tinh_id_tt
    Long quan_id_tt
    Long phuong_id_tt
    Long pho_id_tt
    Long ap_id_tt
    Long khu_id_tt
    String sonha_tt
    Long dacdiem_id_tt
    Float kinhdo_tt
    Float vido_tt
    String ten_tinh_tt
    String ten_quan_tt
    String ten_phuong_tt
    String ten_pho_tt
    String ten_ap_tt
    String ten_khu_tt
    String dacdiem_tt
    Long diachi_id_ld
    String diachi_ld
    Long tinh_id_ld
    Long quan_id_ld
    Long phuong_id_ld
    Long pho_id_ld
    Long ap_id_ld
    Long khu_id_ld
    String sonha_ld
    Long dacdiem_id_ld
    Float kinhdo_ld
    Float vido_ld
    String ten_tinh_ld
    String ten_quan_ld
    String ten_phuong_ld
    String ten_pho_ld
    String ten_ap_ld
    String ten_khu_ld
    String dacdiem_ld
    Long khachhang_id
    Long hdkh_id
    String ten_kh
    Long thuebao_id
    Long thanhtoan_id
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
    Integer is_khdn
    List<FileApp> listImage

    CSKH36Response convertFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            phieutn_id = rs.getLong("phieutn_id")
            diachi_id_tt = rs.getLong("diachi_id_tt")
            diachi_tt = rs.getString("diachi_tt")
            tinh_id_tt = rs.getLong("tinh_id_tt")
            quan_id_tt = rs.getLong("quan_id_tt")
            phuong_id_tt = rs.getLong("phuong_id_tt")
            pho_id_tt = rs.getLong("pho_id_tt")
            ap_id_tt = rs.getLong("ap_id_tt")
            khu_id_tt = rs.getLong("khu_id_tt")
            sonha_tt = rs.getString("sonha_tt")
            dacdiem_id_tt = rs.getLong("dacdiem_id_tt")
            kinhdo_tt = rs.getFloat("kinhdo_tt")
            vido_tt = rs.getFloat("vido_tt")
            ten_tinh_tt = rs.getString("ten_tinh_tt")
            ten_quan_tt = rs.getString("ten_quan_tt")
            ten_phuong_tt = rs.getString("ten_phuong_tt")
            ten_pho_tt = rs.getString("ten_pho_tt")
            ten_ap_tt = rs.getString("ten_ap_tt")
            ten_khu_tt = rs.getString("ten_khu_tt")
            dacdiem_tt = rs.getString("dacdiem_tt")
            diachi_id_ld = rs.getLong("diachi_id_ld")
            diachi_ld = rs.getString("diachi_ld")
            tinh_id_ld = rs.getLong("tinh_id_ld")
            quan_id_ld = rs.getLong("quan_id_ld")
            phuong_id_ld = rs.getLong("phuong_id_ld")
            pho_id_ld = rs.getLong("pho_id_ld")
            ap_id_ld = rs.getLong("ap_id_ld")
            khu_id_ld = rs.getLong("khu_id_ld")
            sonha_ld = rs.getString("sonha_ld")
            dacdiem_id_ld = rs.getLong("dacdiem_id_ld")
            kinhdo_ld = rs.getFloat("kinhdo_ld")
            vido_ld = rs.getFloat("vido_ld")
            ten_tinh_ld = rs.getString("ten_tinh_ld")
            ten_quan_ld = rs.getString("ten_quan_ld")
            ten_phuong_ld = rs.getString("ten_phuong_ld")
            ten_pho_ld = rs.getString("ten_pho_ld")
            ten_ap_ld = rs.getString("ten_ap_ld")
            ten_khu_ld = rs.getString("ten_khu_ld")
            dacdiem_ld = rs.getString("dacdiem_ld")
            khachhang_id = rs.getLong("khachhang_id")
            hdkh_id = rs.getLong("hdkh_id")
            ten_kh = rs.getString("ten_kh")
            thuebao_id = rs.getLong("thuebao_id")
            thanhtoan_id = rs.getLong("thanhtoan_id")
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
            is_khdn = rs.getInt("is_khdn")
            return this
        } else {
            return null
        }
    }

}
