export default {
   getcboDichVuVT: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
   getcboPhongBH: async (axios, data) => axios.get('/web-tratruoc/ttkdhcmservice/dsPhongPhangiao'),
   getcboDonViTo: async (axios, data) => axios.get('/web-tratruoc/ttkdhcmservice/dsToPhanGiao'),
   getcboNhanVien: async (axios, donviId) => axios.get('/web-tratruoc/ttkdhcmservice/dsNhanvienTo?donviId='+donviId),
   getcboHeSoQuyDoi: async (axios, donviId) => axios.get('/web-tratruoc/ttkdhcmservice/dmHesoGhtt'),
   getcboNhanVienDinhmuc: async (axios, donviId,thangKt,cTrinh) => axios.get('/web-tratruoc/ttkdhcmservice/dsNvDinhMuc?donvi_id='+donviId+'&thang_kt='+thangKt+'&chuongtrinh='+cTrinh),
   getcboDinhMuc: async (axios, donviId,thangKt,cTrinh) => axios.get('/web-tratruoc/ttkdhcmservice/ttDinhMucGHTT?donvi_id='+donviId+'&thang_kt='+thangKt+'&chuongtrinh='+cTrinh),
   postCapnhatCauhinhDinhmuc: async (axios, request) => axios.post('/web-tratruoc/ttkdhcmservice/CapNhatCauHinhDinhMuc', request),
   //ds NV tab 1
   getdsNvPhanGiaoDuKien: async (axios, donviId,thangKt,cTrinh) => axios.get('/web-tratruoc/ttkdhcmservice/dsNvPhanGiaoDuKien?donvi_id='+donviId+'&thang_kt='+thangKt+'&chuongtrinh='+cTrinh),
   postDsthuchienPhangiao: async (axios, request) => axios.post('/web-tratruoc/ttkdhcmservice/thuchienPhangiao', request),
   //nut Ghilai cua tab1
   postCapnhatCapNhatDsNvGiaoDuKien: async (axios, request) => axios.post('/web-tratruoc/ttkdhcmservice/CapNhatDsNvGiaoDuKien', request),
   getChiSoPhanGiaoDuKien: async (axios, donviId,thangKt,cTrinh) => axios.get('/web-tratruoc/ttkdhcmservice/ChiSoPhanGiaoDuKien?donvi_id='+donviId+'&thang_kt='+thangKt+'&chuongtrinh='+cTrinh),
   getThongTinDuLieuGiaoNV: async (axios, donviId,thangKt,cTrinh) => axios.get('/web-tratruoc/ttkdhcmservice/ThongTinDuLieuGiaoNV?donvi_id='+donviId+'&thang_kt='+thangKt+'&chuongtrinh='+cTrinh),
   getcboThongTinThuebaoPhanGiaoNV: async (axios, cTrinh,thangKt,donviId) => axios.get('/web-tratruoc/ttkdhcmservice/ThongTinThuebaoPhanGiaoNV?chuongtrinh='+cTrinh+'&thang_kt='+thangKt+'&donvi_id='+donviId),
   getcboDsTo: async (axios, donviId) => axios.get('/web-tratruoc/ttkdhcmservice/dsToThuocPhong?donviId='+donviId),
   // api tab4
   getcboDsNhanvienChuyen: async (axios, cTrinh,thangGiao,donviIdto ) => axios.get('/web-tratruoc/ttkdhcmservice/getdsNhanvienChuyen?chuongtrinh='+cTrinh+'&thanggiao='+thangGiao+'&donvi_id_to='+donviIdto),
   getcboDsTbNvCanDieuChinh: async (axios, cTrinh,thangKt,nhanvienId ) => axios.get('/web-tratruoc/ttkdhcmservice/DsTbNvCanDieuChinh?chuongtrinh='+cTrinh+'&thang_kt='+thangKt+'&nhanvien_nhan_id='+nhanvienId),
   postluu_dsThuebao_chuyen_thucong: async (axios, request) => axios.post('/web-tratruoc/ttkdhcmservice/luu_dsThuebao_chuyen_thucong', request),

   // api tab5 
   getcboDsNhanvienYeucauChuyen: async (axios, thanggiaoId) => axios.get('/web-tratruoc/ttkdhcmservice/getdsNhanvienYeuCauChuyen?thang_kt='+thanggiaoId),
   getcboDsTbNvTiepNhan: async (axios, nhanvienIdChuyen,thangKt) => axios.get('/web-tratruoc/ttkdhcmservice/DsTbNvTiepNhan?nhanvien_chuyen_id='+nhanvienIdChuyen+'&thang_kt='+thangKt),
   postXacNhanDieuChinh: async (axios, request) => axios.post('/web-tratruoc/ttkdhcmservice/XacNhanDieuChinh', request),
   // api ds phân giao import (Phải)
   getDSphangiaoFile: async (axios, data) => axios.post('/web-tratruoc/ttkdhcmservice/up_dsThuebao_chuyen_diaban', data),
   ghiDSphangiaoFile: async (axios, thang_giao) => axios.post('/web-tratruoc/ttkdhcmservice/insert_dsThuebaochuyen', thang_giao),

   //getcboDinhMuc: async (axios, donviId,thangKt,cTrinh) => axios.get('/web-tratruoc/ttkdhcmservice/ttDinhMucGHTT?donvi_id='+donviId+'&thang_kt='+thangKt+'&chuongtrinh='+cTrinh),
   //getcboDichVuKhac: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_KHAC'),
   //getThongtinDoiTac: async (axios, data) => axios.get('/web-thicong/quanlydonhang/lay_thongtin_doitac'),
   //getThongTinThueBao: async (axios, p_ma_tb) => axios.post('/web-thicong/quanlydonhang/lay_thongtin_hdbs_vnpti', {p_ma_tb}),
   //saveThongTinThueBao: async (axios, {p_thuebao_id, p_diachi_ld_sub, p_diachi_pop, p_dvkhac_id, p_dichvuvt_id, p_doitac_id}) => axios.post('/web-thicong/quanlydonhang/capnhat_thongtin_hdbs_vnpti', {p_thuebao_id, p_diachi_ld_sub, p_diachi_pop, p_dvkhac_id, p_dichvuvt_id, p_doitac_id}),
   //chitiet_loaihinh_tb: async (axios) => axios.get('/web-thicong/quanlydonhang/capnhat_thongtin_hdbs_vnpti', {p_thuebao_id, p_diachi_ld_sub, p_diachi_pop, p_dvkhac_id, p_dichvuvt_id, p_doitac_id})
   //chitiet_loaihinh_tb: async (axios) => axios.get('/web-tratruoc/chitiet-loaihinh-tb', {p_thuebao_id, p_diachi_ld_sub,p_diachi_pop,  p_dvkhac_id, p_dichvuvt_id, p_doitac_id}),

}
