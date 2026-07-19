export default {
  layThongTinKetNoiMegawan: (axios, body) => axios.post('web-quantri/ketnoi-megawan/lay_thongtin_ketnoi_megawan', body),
  getThongTinPe: (axios, body) => axios.post('web-hopdong/laydulieu/sp_tt_pe', body),
  getThongTinLoaiCap: (axios, body) => axios.post('web-hopdong/laydulieu/sp_tt_loai_cap', body)
}
