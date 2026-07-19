export default{
    getDmCBB: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_tatca_danhmuc_chuyenquyen', data),
    getDmCBB_KHNQ: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_tatca_danhmuc_chuyenquyen', data),
    getDmLoaiHinh: async (axios, data) => axios.get(`web-hopdong/danhmuc/ds_loaitb/${data.dichvuvt_id}`, data),
    getDmKieuYC: async (axios, data) => axios.get(`web-hopdong/danhmuc/ds_kieu_ld/${data.loaihd_id}/${data.loaitb_id}`, data),
    getDSHopDong_MaTB: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_matb', data),
    getDSHopDong_MaGD: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data),
    getDsThanhToan: async (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_ma_thanhtoan?${data.hdkhId}`, data),
    getTTDanhBa_MaTB:async (axios, data) => axios.get(`web-hopdong/hopdong/lay_ds_thuebao_matb?maTb=${data.maTb}&dvvtId=${data.dvvtId}`, data),
    getTTDanhBa_ThueBaoID:async (axios, data) => axios.get(`web-hopdong/hopdong/lay_thongtin_thuebao_thuebaoid?thuebaoId=${data.thuebaoId}`, data),
    getDSDichVu_Them:async (axios, data) => axios.post('web-hopdong/hopdong/lay_ds_thuebao_dichvu', data),
    getDSDichVuKhac_CQ:async (axios, data) => axios.get(`web-hopdong/hopdong/lay_ds_sudung_dvk?dbId=${data.thuebaoId}&loaiId=${data.loaiId}`, data),
    getDsKhachHang_NhanQuyen: async (axios, data) => axios.get(`/web-hopdong/hopdong/lay_thongtin_khachhang_nhanquyen?maKh=${data.maKh}`, data),
    getTTDSThanhToanHD: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_dbtt_theo_matt_khid', data),
    getDSMaDaiDien_TTID: async (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_ma_thuebao_daidien?thanhtoanId=${data.thanhtoanId}`, data),
    getTTThueBao: async (axios, data) => axios.post('/web-baohong/TiepNhanBaoHong/lay_ds_thongtin_kythuat', data),

    getDmTienKM_LD: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld', data),
    // ghi lai
    postChuyenQuyen: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/sp_lap_hopdong_chuyenquyen', data),
    postChuyenQuyen_v2: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/sp_lap_hopdong_chuyenquyen_v2', data),
    
    postThemTB: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/sp_chuyenquyen_them_tb', data),
    // xoa hd
    postXoaHDKH: async (axios, data) => axios.post('/web-hopdong/hopdong/khoa_mo_thuebao_no_cuoc/xoa_hopdong', data),
    postXoaHDTB: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdtb', data),

    post_lay_ds_tieunganh : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_ds_tieunganh',data),
    get_lay_ds_nhanvienql : async (axios, data) => axios.get(`/web-hopdong//hopdong/lay_nhanvien_quanly?kieu=1&pId=${data.nhanvienId}`,data),
    post_lay_ds_donviql: async (axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_donviql',data),
    post_lay_ds_nvtc: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/ht_nvtc_theodc',data),

    get_ds_hopdongthuebao: async (axios, data) => axios.get(`/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId=${data.hdkhId}`, data),
    get_ds_hopdongthanhtoan: async (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh?hdKhId=${data.hdkhId}`, data),


    // thong tin email
    getDsThongTinEmail: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_email', data),
    getDsMucDich_HDEmailId: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_email_md', data),
    getDsMucDich_EmailId: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_email_md_db', data),
    capnhat_email: async (axios, data) => axios.post('/web-ccdv/tienhopdong/capnhat_email', data),


    getDsKH_TT_TB: async (axios, data) => axios.post('/web-ccdv/tienhopdong/lay_ds_hopdong_email', data),

    // cong tac vien
    getDsNhom: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_LOAI_NV', data),
    getDsDonViQL: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/lay_ds_donvi_ctv', data),
    getDsNhanVien: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/sp_lay_ds_nhanvien_gt', data),

    sp_lay_ds_nhanvien_gt_v2: async (axios, data) => axios.post('/web-tracuu/tracuu/sp_lay_ds_nhanvien_gt_v2', data),
    // lien he
    getThongTinLienHe: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_thongtin_lh', data),  
    getThongTinKHTTTB: async (axios, data) => axios.post('/web-ccdv/tienhopdong/lay_ds_hopdong_lienhe', data), 
    getThongTinLHMD: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_thongtin_lh_md', data),
    getThongTinLHMD_DB: async (axios, data) => axios.post('/web-thicong/thicong/lay_ds_thongtin_lh_md_db', data),
    getThongTin_LHMXH: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_lienhe_mxh', data),   
    getKey: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys', data),   
    postLienHe: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_capnhat_lienhe', data),
    DeleteLienHe: async (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_xoa_hd_lienhe', data),
}