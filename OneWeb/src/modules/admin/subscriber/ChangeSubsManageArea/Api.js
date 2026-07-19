// WinUI.WinUIQuanLyDanhBa.frmChuyenThueBao_KV,PTTB.exe
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=537265640&range=A833
export default {
  getDmKhuVuc: (axios, data) => axios.post('/web-quantri/diaban/ds-khuvuc-khoan', data),
  getDSThueBaoTheoKhuVuc: (axios, data) => axios.post('/web-quantri/diaban/ds-thuebao-theo-khuvuc', data),
  chuyenThueBaoTheoKhuVuc: (axios, data) => axios.post('/web-quantri/diaban/chuyen-khuvuc-thuebao', data),
  getLsTraoDoiBienDong: (axios,data) => axios.post('/web-thicong/tientrinh_thicong/sp_log_khuvuc_quanly_thuebao', data)
}
