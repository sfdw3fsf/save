export default{
  getDsKhachHang: (axios, data) => axios.get(`/web-tracuu/pttb/tracuu_khachhang_sudung_oa_zalo?sdt=${data.ma_thuebao}`)
}
