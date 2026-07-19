export default {
    //Lấy dánh sách phiếu yêu cầu
    getDsPhieuYeucau: (axios, data) => axios.post('/web-hopdong/dichvu-ca/lay_ds_hdtb_thaydoi_sosim_tt', data),
    //Lấy danh sách loại giấy tờ
    getDsLoaiGiayto: (axios, data) => axios.post('/web-quantri/danhmuc-chung/CSS_LOAI_GT', data),
   //Lây danh sách HĐ theo mã giao dịch
   getDsHDByMaGD: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),
   //Sinh mã giao dịch
   getMaGD: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', data),
   //Xóa hợp đồng khách hàng
   XoaHDKhachhang: (axios, data) => axios.post('/web-danhba/thutienphatsinh/xoa_hdkh', data),
   //Cập nhật hợp đồng
   Capnhathopdong: (axios, data) => axios.post('/web-hopdong/dichvu-ca/sp_upd_doisosimtratruoc', data),
   //Thêm mới hợp đồng
   Themmoihopdong: (axios, data) => axios.post('/web-hopdong/dichvu-ca/sp_ins_hopdong_doisimtratruoc', data),
   //Lấy tiền khoản mục lắp đặt
   LayTienKhoanMuc: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld', data),
   //Sinh key
   getKeys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
   //Kiểm tra sim
   KiemTraSoSimMoi: (axios, data) => axios.post('/ccbs/multisim/multisim_doisim_ktsimmoi', data),
   //Kiểm tra sim cũ 
   multisim_doisim_ttsimcu: (axios, data) => axios.post('/ccbs/multisim/multisim_doisim_ttsimcu', data),
   //Kiểm tra sim cũ 
   multisim_tt_thuebao: (axios, data) => axios.post('/ccbs/multisim/multisim_tt_thuebao', data),
   //Kiểm tra sim cũ 
   ts_tracuu_laytt_tb: (axios, data) => axios.post('ccbs/executor/ts_tracuu_laytt_tb', data),
   ts_tracuu_laytt_tb_ts: (axios, data) => axios.post('ccbs/executor/ts_tracuu_laytt_tb_ts', data),
   
   
   
    
}
  