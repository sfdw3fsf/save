export default {
  getTTKhachHang: (axios, params) => axios.post('/web-hopdong/ghepmakhachhang/sp_lay_tt_khachhang_by_ma_kh', params), // {"ma_kh": "HPG-001106076","type":0}
  getDSThueBao: (axios, params) => axios.post('/web-hopdong/ghepmakhachhang/sp_lay_ds_thuebao_by_tt_id', params), // {"thanhtoan_id": 765203}
  getLSGMKH: (axios, params) => axios.post('/web-hopdong/ghepmakhachhang/sp_lay_ds_lichsu_ghep_ma_khachhang', params), // {"ma_kh": "HPG-002001412","nguoi_cn": "test_weberp.hpg'","from_date": "05/10/2021","to_date": "05/10/2021"}
  submitGMKH: (axios, params) => axios.post('/web-hopdong/ghepmakhachhang/sp_ghilai_ghep_ma_khachhang', params)
}
