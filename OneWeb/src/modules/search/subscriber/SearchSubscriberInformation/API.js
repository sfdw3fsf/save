export default {
	getDSDoiTuong: (axios, data) => axios.post(`/web-tracuu/tracuulichsutb/sp_tc_doituong_tb`),
	getDSDichVu: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`),
	getDSTrangThai: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_TRANGTHAI_TB`),
	getDSLoaiHinhTB: (axios, data) => axios.get(`/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId=${data}`),
	getDanhBaMaTB: (axios, data) => axios.post(`/web-hopdong/tracuulichsutb/sp_lay_danhba_theo_matb`, data),
	getLichSuDungThu: (axios, data) => axios.post(`/web-tracuu/tracuulichsutb/sp_lay_tt_dungthu_theo_matb`, data),
	getBienDongCuoc: (axios, data) => axios.post(`/web-tracuu/tracuulichsutb/sp_lay_ds_biendongthongtin_tinhcuoc`, data),
	getLichSuThueBao: (axios, data) => axios.post(`/web-tracuu/tracuulichsutb/sp_tra_cuu_ls_thuebao`, data),
	getLichSuThongtinTB: (axios, data) => axios.post(`/web-tracuu/tracuulichsutb/sp_lay_lichsu_thongtin_tb`, data),
	sp_lay_ds_tratruoc_trasau: (axios, data) => axios.post(`/web-tracuu/tracuu/sp_lay_ds_tratruoc_trasau`, data),
}
