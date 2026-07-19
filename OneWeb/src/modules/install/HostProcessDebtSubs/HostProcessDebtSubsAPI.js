import axios from "axios"
import moment from 'moment'

export default {
  Lay_ds_nhanvien_thuchien_theo_dv: donvi =>
    axios
      .get(`/web-quantri/danhmuc/ds_nhanvien_donvi/${donvi}`)
      .then(rs => rs.data.data),
  dichvu_vienthong: () =>
    axios
      .post(`/web-hopdong/danhmuc/dichvu_vienthong`, {})
      .then(rs => rs.data.data),
  getDataFromSQL: () =>
    axios
      .post(`/web-quantri/khuyenmai/lay_ds_thamso_md`, {
        list_of_cols: "*",
        order: "",
        schema: "admin",
        table_name: "THAMSO_MD",
        where: "ma_ts = 'NGAY_THUHOI'"
      })
      .then(rs => rs.data.data),
  LAY_DS_THUEBAO_HATOCDO: () =>
    axios
      .post(`/web-hopdong/danhmuc/dichvu_vienthong`, {
        list_of_cols: "thuebao_id",
        order: "",
        schema: "qltn",
        table_name: "hatocdo",
        where: ""
      })
      .then(rs => rs.data.data),
  LAY_DS_THAMSO_MD: () =>
    axios
      .post(`/web-quantri/khuyenmai/lay_ds_thamso_md`, {
        list_of_cols: "*",
        order: "",
        schema: "admin",
        table_name: "THAMSO_MD",
        where: ""
      })
      .then(rs => rs.data.data),
  LAY_DS_THAMSO_MD_MATS: ma_ts =>
    axios
      .post(`/web-thicong/hoancongmegawan/lay_ds_thamso_md_mats`, {
        ma_ts: ma_ts
      })
      .then(rs => rs.data.data),
  Lay_DS_QuyTrinh: (dsloaihd_id, dsdichvuvt_id, dstthd_id, kieu) =>
    axios
      .post(`/web-quantri/khoamodichvu/sp_lay_ds_quytrinh_theo_loaihd_dichvu`, {
        dsloaihd_id: dsloaihd_id,
        dsdichvuvt_id: dsdichvuvt_id,
        dstthd_id: dstthd_id,
        kieu: kieu
      })
      .then(rs => rs.data.data),
  HT_LoaiHinh_TB_QT_Combobox: quytrinh_id =>
    axios
      .get(
        `/web-hopdong/danhmuc/lay_ds_loaihinh_tb_theo_quytrinh/${quytrinh_id}`
      )
      .then(rs => rs.data.data),
  LAY_DS_HDTB_KHOAMOMAY_HC: (
    ma_gd,
    nhanvien_id,
    dichvuvt_id,
    loaitb_id,
    tthd_id,
    huongiao_id,
    kieu_yc,
    loai_id
  ) =>
    axios
      .post(`/web-quantri/khoamodichvu/lay_ds_hdtb_khoamomay_hc`, {
        ma_gd: ma_gd,
        nhanvien_id: nhanvien_id,
        dichvuvt_id: dichvuvt_id,
        loaitb_id: loaitb_id,
        tthd_id: tthd_id,
        huongiao_id: huongiao_id,
        kieu_yc: kieu_yc,
        loai_id: loai_id
      })
      .then(rs => rs.data.data),
  LAY_DS_HDTB_THANHLY_HC: (
    ma_gd,
    dichvuvt_id,
    loaitb_id,
    tthd_id,
    huongiao_id,
    kieu_yc,
    loai_id
  ) =>
    axios
      .post(`/web-hopdong/hopdong/lay_ds_hdtb_thanhly_hc`, {
        ma_gd: ma_gd,
        dichvuvt_id: dichvuvt_id,
        loaitb_id: loaitb_id,
        tthd_id: tthd_id,
        huongiao_id: huongiao_id,
        kieu_yc: kieu_yc,
        loai_id: loai_id
      })
      .then(rs => rs.data.data),
  Laythongtin_table: (quytrinh_id, tthd_id) =>
    axios
      .get(`/web-hopdong/danhmuc/ds_huonggiao/${quytrinh_id}/${tthd_id}`)
      .then(rs => rs.data.data),
  AddGIAOPHIEU_NVRow: data =>
    axios
      .post(
        `/web-ccdv/tiepnhanhopdong/insert_giaophieu_nv`,
        JSON.stringify(data)
      )
      .then(rs => rs.data.data),
  lay_luong_thaotac: luong_id =>
    axios
      .post(`/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac`, {
        luong_id: luong_id
      })
      .then(rs => rs.data.data),
  // web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control
  lay_ct_thaotac_control: thaotac_id =>
    axios
      .post(`/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control`, {
        thaotac_id: thaotac_id
      })
      .then(rs => rs.data.data),
  LAY_DS_NHANVIEN_THEO_PHIEU_ID: (phieu_id, nhanvien_id, kieu_id) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id`, {
    "phieu_id": phieu_id,
    "nhanvien_id": nhanvien_id,
    "kieu_id": kieu_id
  }).then(rs => rs.data.data),
  Update_ND_THUCHIEN: (phieu_id, noidung) => axios.post(`/web-ccdv/thicong_ghone/sp_upd_noidung_thuchien`, {
    "phieu_id": phieu_id.toString(),
    "noidung": noidung
  }).then(rs => rs.data.data),
  Lay_DS_LyDoHuy_HDTB: (hdtb_id) => axios.post(`/web-thicong/thanhly-thuebao/sp_lay_ds_lydohuy_hdtb`, {
    "hdtb_id": hdtb_id
  }).then(rs => rs.data.data),
  XOA_HDKH_V2: (data) => axios.post(`/web-hopdong/hopdong/khoa_mo_thuebao_no_cuoc/xoa_hopdong`, { "list": data }).then(rs => rs.data),
  capnhat: (lstChon, ngayGiao, dsNhanVien, nhanVienId, nguoiGiaoViec, tuDongGiaoViec) => {
    const data = {
      tudonggiaoviec: tuDongGiaoViec ? 1 : 0,
      nhanvien_id: nhanVienId,
      nhanvien_giao_id: nguoiGiaoViec,
      ngay_giao: ngayGiao,
      js_ds_chon: lstChon,
      js_giaophieu_nv: dsNhanVien
    }

    return axios.post(`/web-ccdv/hths_khoamo_may/capnhat`, {
      "ds_param": JSON.stringify(data)
    }).then(rs => rs.data)
  },
  kichhoat: (dichVuId, luongId, tthd, chkLoiKH, kieuYC, nhanTinNV, loaiTBID, dsHDTB) => {
    return axios.post(`/web-ccdv/hths_khoamo_may/kichhoat`, {
      "dichvuvt_id": dichVuId,
      "luong_id": luongId,
      "kt_tthd_kichhoat": tthd,
      "chk_loikh": chkLoiKH,
      "kieu_yc": kieuYC,
      "nhantin_nhanvien_diaban": nhanTinNV,
      "loaitb_id": loaiTBID,
      "ds_hdtb": dsHDTB
    }).then(rs => rs.data)
  },
  map_id: (col, table, condition) => axios.post(`web-hopdong/thanhly/thanh_ly_map_id`, {'id_neo': col, 'in_table': table, 'in_dk': ` ${condition}`}).then(rs => rs.data.data),
  fn_tt_hd_thuebao:
  (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, { param: param, type: type })
      .then(rs => rs.data.data),
  fn_tt_nguoidung:
  (param, type) =>
    axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_nguoidung`, { param: param, type: type })
      .then(rs => rs.data.data),
  capnhat_ngayht: (hdtb_id, ngay_ht) => axios.post(`/web-hopdong/hopdong/fn_capnhat_ngayht`, {
    hdtb_id: hdtb_id,
    ngay_ht: moment(ngay_ht).format('DD/MM/YYYY HH:mm:ss')
  }).then(rs => rs.data.data),
  sp_capnhat_ngaykh: async (hdtb_id, ngay_kh) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_capnhat_ngaykh', {
    hdtb_id: hdtb_id,
    ngay_kh: ngay_kh ? moment(ngay_kh).format('DD/MM/YYYY HH:mm:ss') : ''
  }).then(rs => rs.data.data),
  capnhat_nangtocdo: async (thuebao_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/capnhat_nangtocdo', {
    vthuebao_id: thuebao_id
  }),
  kt_taodb_thang: (kyhoadon) => axios.post(`/web-hopdong/thaydoiloaihinhtb/kt_taodb_thang`, { kyhoadon: kyhoadon }).then(rs => rs.data.data),
  // HOANTHANH_HOANCONG: (luong_id, dichvuvt_id, loaitb_id, kieu_yc, huonggiao_id, ngay_bg, chk_loihoancong, kt_thamso_thuhoi, rdogiao_theoqd, rdogiaongay, xacminh_theoyeucau, ds_hdtb) => axios.post(`/web-ccdv/hths_khoamo_may/hoancong`, {
  //   "luong_id": luong_id,
  //   "dichvuvt_id": dichvuvt_id,
  //   "loaitb_id": loaitb_id,
  //   "kieu_yc": kieu_yc,
  //   "huonggiao_id": huonggiao_id,
  //   "ngay_bg": ngay_bg,
  //   "chk_loihoancong": chk_loihoancong,
  //   "kt_thamso_thuhoi": kt_thamso_thuhoi,
  //   "rdogiao_theoqd": rdogiao_theoqd,
  //   "rdogiaongay": rdogiaongay,
  //   "xacminh_theoyeucau": xacminh_theoyeucau,
  //   "ds_hdtb": ds_hdtb
  // }),
  kiemtra_trangthai_jira_hths_v2: async (hdtb_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/kiemtra_trangthai_jira_hths_v2', {
    vhdtb_id: hdtb_id
  }).then(rs => rs.data.data),
  Lay_DonVi_GiaoPhieu: async (hdtb_id, huonggiao_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_donvi_giaophieu', {
    hdtb_id: hdtb_id,
    huonggiao_id: huonggiao_id
  }).then(rs => rs.data.data),
  CAPNHAT_TT_PHIEUGIAO: (phieu_id, ttph_id, ngay_th) => axios.post(`/web-ccdv/khaibaotsl/capnhat_tt_phieugiao`, {
    phieu_id: phieu_id,
    ttph_id: ttph_id,
    ngay_th: ngay_th + " 00:00:00"
  }),
  fn_giaophieu_chamdutsd: (hdtb_id, hdkh_id, loaitb_id, luong_id) => axios.post(`web-hopdong/thanhly/fn_giaophieu_chamdutsd`, {
    'hdtb_id': hdtb_id,
    'hdkh_id': hdkh_id,
    'loaitb_id': loaitb_id,
    'luong_id': luong_id
  }).then(rs => rs.data.data),
  // ts_tracuu_laytt_tb: (ma_tinh, so_tb, userid, userip) => axios.post(`web-hopdong/qltn/ts_tracuu_laytt_tb`, {
  //   "ma_tinh": ma_tinh,
  //   "so_tb": so_tb,
  //   "userid": userid,
  //   "userip": userip
  // }).then(rs => rs.data.data),
  lay_ds_khoamay_theo_hdkh_id: (hdkh_id) => axios.get(`web-hopdong/hopdong/lay_ds_khoamay_theo_hdkh_id/${hdkh_id}`).then(rs => rs.data.data),
  them_dulieu_khoamay_theo_yc: (may_cn, nguoi_cn, thuebao_id) => axios.post(`web-quantri/khoamodichvu/them_dulieu_khoamay_theo_yc`, {
    "may_cn": may_cn,
    "nguoi_cn": nguoi_cn,
    "thuebao_id": thuebao_id
  }).then(rs => rs.data.data),
  giaophieu_td_khi_hc: (hdtb_id, huonggiao_id, may_cn, phieu_id) => axios.post(`web-hopdong/thaydoithongtinkhuyenmai/giaophieu_td_khi_hc`, {
    "hdtb_id": hdtb_id,
    "huonggiao_id": huonggiao_id,
    "may_cn": may_cn,
    "phieu_id": phieu_id
  }).then(rs => rs.data.data),
  HOANTHANH_HOANCONG: (luong_id, dichvuvt_id, loaitb_id, kieu_yc, huonggiao_id, ngay_bg, chk_loihoancong, kt_thamso_thuhoi, rdogiao_theoqd, rdogiaongay, xacminh_theoyeucau, ds_hdtb) => axios.post(`web-ccdv/hths_khoamo_may/hoancong`, {
    "luong_id": luong_id,
    "dichvuvt_id": dichvuvt_id,
    "loaitb_id": loaitb_id,
    "kieu_yc": kieu_yc,
    "huonggiao_id": huonggiao_id,
    "ngay_bg": ngay_bg,
    "chk_loihoancong": chk_loihoancong,
    "kt_thamso_thuhoi": kt_thamso_thuhoi,
    "rdogiao_theoqd": rdogiao_theoqd,
    "rdogiaongay": rdogiaongay,
    "xacminh_theoyeucau": xacminh_theoyeucau,
    "ds_hdtb": ds_hdtb
  }).then(rs => rs.data),
  Kiemtra_huygoi_mytv_tddv: (hdtb_id) => axios.get(`web-ccdv/dongbo-visa-vasc/Kiemtra_huygoi_mytv_tddv/${hdtb_id}`).then(rs => rs.data.data),
  GetKey: (keyname) => axios.get(`web-hopdong/thaydoithongtinkhuyenmai/getkey?keyname=${keyname}`).then(rs => rs.data.data),
  ts_tracuu_laytt_tb: (soTB) => axios.post(`ccbs/tracuu/ts_tracuu_laytt_tb`, {
    "so_tb": soTB
  }).then(rs => rs.data.data),
  lay_ds_thamso_md: async (data) => axios.post(`web-hopdong/laydulieu/sp_tt_thamso_md`, data).then(rs => rs.data.data),
}
