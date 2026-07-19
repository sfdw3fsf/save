let root = {}
let login = {}
export default {
  setupRoot: function (_root) {
    root = _root
  },
  setLogin: function (_login) {
    Object.assign(login, _login)
  },
  ts_hoanthien_hd_cn_kh: async function (psma_tuyen, psdangkytv, pshoct_id, psuserid, pstaikhoan,
                                         pskh_lon, psghichu, pshuongtt_id1, psma_qhns, psnoicapgt1,
                                         pstennv, psnganhang_id, pskyten, psquantt_id, psdangkydb,
                                         psloaikh, psdonviql0, psonhatt_id, psnganhnghe, pssogt1,
                                         pshott_id, psuutien, pslydo_anh, psngaycapgt, psdiachitt,
                                         psquanct_id, tentat_ccbs, psgioitinh, psma_kh0, psmsthue,
                                         pssogiayto, psngaycapgt1, psdiachict, pssodaidien, psdienthoailh,
                                         psmacq, pstentt, psdonviql, psnguoidaidien, pshuongtt_id, psonhact_id,
                                         psemail, psmabc, psnoicapgt, psdiadiemtt, psten_tuyen, psma_kh,
                                         psloaigtid, psnguoi_gt, psloaigtid1, psngaysinh, pskhrr,
                                         pscoquan, pschuyenkhoan, psmanv) {
    try {
      return await root.context.post(
        "/ccbs/executor/ts_hoanthien_hd_cn_kh",
        {
          ma_tuyen: psma_tuyen,
          dangky_tv_0_1: psdangkytv,
          phoct_id: pshoct_id,
          userid: psuserid,
          tai_khoan: pstaikhoan,
          kh_lon: pskh_lon,
          ghi_chu: psghichu,
          phuongct_id: pshuongtt_id1,
          ma_qhns: psma_qhns,
          noi_cap1: psnoicapgt1,
          ten_nv: pstennv,
          ngan_hang_id: psnganhang_id,
          ky_ten: pskyten,
          quantt_id: psquantt_id,
          dangky_db_0_1: psdangkydb,
          loai_kh: psloaikh,
          donv_ql_tb: psdonviql0,
          sonha_tt: psonhatt_id,
          ip: "",
          nganhnghe: psnganhnghe,
          so_giay_to1: pssogt1,
          phott_id: pshott_id,
          uutien: psuutien,
          ly_do_anh: pslydo_anh,
          ngay_cap_giay_to: psngaycapgt,
          dia_chi_tt: psdiachitt,
          quanct_id: psquanct_id,
          ma_tinh: tentat_ccbs,
          gender: psgioitinh,
          ma_kh: psma_kh0,
          ms_thue: psmsthue,
          so_giay_to: pssogiayto,
          ngay_cap1: psngaycapgt1,
          dia_chi_ct: psdiachict,
          sodaidien: pssodaidien,
          dienthoai_lh: psdienthoailh,
          ma_cq: psmacq,
          ten_tt: pstentt,
          donv_ql_id: psdonviql,
          nguoidaidien: psnguoidaidien,
          phuongtt_id: pshuongtt_id,
          sonhact_id: psonhact_id,
          email: psemail,
          ma_bc: psmabc,
          noi_cap_giay_to: psnoicapgt,
          diadiem_tt_id: psdiadiemtt,
          ten_tuyen: psten_tuyen,
          ma_kh_cu: psma_kh,
          loaigt_id: psloaigtid,
          nguoi_gioi_thieu: psnguoi_gt,
          loaigt_id1: psloaigtid1,
          ngay_sinh: psngaysinh,
          kh_rr: pskhrr,
          coquan: pscoquan,
          chuyen_khoan_id: pschuyenkhoan,
          ma_nv: psmanv,
        }
      );
    } catch (error) {
      console.log(error)
      return null
    } finally {
    }
  },
}
