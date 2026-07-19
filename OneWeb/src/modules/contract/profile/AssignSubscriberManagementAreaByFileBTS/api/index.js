export default {
  InsertThongTin: (axios, data) => axios.post('/web-danhba/khuvuc_qltb_theofile/sp_insert_kiemtra_ma_tt_tmp_ghichu', data),
  Update_KV_File: (axios, data) => axios.post('/web-quantri/diaban/thaydoi-khuvuc-didong-theo-file', data)
}
