// WinUI.WinUIQuanLyDanhBa.frmChuyenThueBao_KV,PTTB.exe
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=537265640&range=A833
export default {
  getDmKhuVuc: (axios) => axios.get('/web-quantri/diaban/ds-khuvuc-bts'),
  getDSThueBaoTheoBTS: (axios, data) => axios.post('/web-quantri/diaban/ds-thuebao-theo-bts', data),
  chuyenThueBaoTheoKhuVucBTS: (axios, data) => axios.post('/web-quantri/diaban/chuyen-thuebao-theo-khuvuc-bts', data)

}
