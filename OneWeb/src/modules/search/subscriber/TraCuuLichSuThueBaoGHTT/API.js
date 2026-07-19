import axios from 'axios'
export default {
    sp_lay_tt_kinhdo_vido: async (axios, data, header) => axios.post('/web-tracuu/tracuu_th/sp_lay_tt_kinhdo_vido', data, header),
    getDSDoiTuong: (axios, data) => axios.post(`/web-tracuu/tracuulichsutb/sp_tc_doituong_tb`),
	getDSDichVu: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`),
    getDSLoaiHinhTB: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId=${data}`),
    getDSTrangThai: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_TRANGTHAI_TB`),

}