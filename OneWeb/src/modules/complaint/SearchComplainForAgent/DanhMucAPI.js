import axios from "axios";
export default {
    getDSDichVu: async (axios) => axios.post('/web-hopdong/TraCuuPhieuGiaoDen/lay_ds_dichvu_vt'),
    getLoaiHinhTB: async (axios, id) => axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId='+id),
    getDSNhanVienQLDL: async(axios, data) =>axios.post('web-gqkn/TraCuuKhieuNaiChung/sp_lay_ds_nhanvien_qldl',data),
    getThongTinThueBao: async(axios, data) =>axios.post('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_thongtin_tt_theo_matb',data),
    getDSKhieuNai: async(axios, data) =>axios.post('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_ds_khieunai',data)
   
}