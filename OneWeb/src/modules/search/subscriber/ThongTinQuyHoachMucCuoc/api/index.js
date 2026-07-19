import axios from 'axios'
export default{        
    sp_lay_tt_thuebao_tuvan_khuyennghi: (data) => axios.post('/web-tratruoc/ob_ghtt/sp_lay_tt_thuebao_tuvan_khuyennghi', data),
    /*
        Lấy các danh sách theo mã thuê bao
        - input
        {"vma_tb":this.ma_tb}
        - output
          + v_dstb_sudung: thông tin chi tiết thuê bao
          + v_dstb_thamgiagoi: danh sách thuê bao tham gia gói
          + v_dstb_kh: danh sách thuê bao thuộc khách hàng

    */
    sp_tuvan_thuebao_le: (data) => axios.post('/web-tratruoc/ob_ghtt/sp_tuvan_thuebao_le', data),
    /*
        Tra cứu tại lưới Thông tin tốc độ mới, mức cước, gói đa dv, trả trước, lấy theo điều kiện từ radio:
        * kieu: 0: Tốc độ (MB), 1: Tiền mức cước TB, 2: Tiền gói đa dv
        - input
            {
                "vthuebao_id":this.thuebao_id,
                "vkieu":this.radio_kieu,
                "vnum_bd":this.removeComma(this.txt_tracuu_tu),
                "vnum_kt":this.removeComma(this.txt_tracuu_den)
            }
        - output
          + data

    */   
    sp_ds_km_theo_goi_id_tuvan: (data) => axios.post('/web-tratruoc/ob_ghtt/sp_ds_km_theo_goi_id_tuvan', data),
    /*
        Load "Danh sách trả trước: Tên khuyến mại", theo sự kiện chọn dòng từ danh sách "Tư vấn thuê bao chỉ tham gia gói đa dịch vụ"
        - input
            {"vgoi_id":item.data.goi_id}
        - output
          + data

    */
    sp_ds_thuebao_chithamgia_goidadv_tuvan: (data) => axios.post('/web-tratruoc/ob_ghtt/sp_ds_thuebao_chithamgia_goidadv_tuvan', data),
    /*
        Load "Tư vấn thuê bao chỉ tham gia gói đa dịch vụ", sau sự kiện tìm kiếm thuê bao
        - input
            {"vthuebao_id":this.thuebao_id}
        - output
          + data

    */
    sp_ds_tuvan_muathem_mytv: (data) => axios.post('/web-tracuu/tracuu_tb_mytv/sp_ds_tuvan_muathem_mytv', data),

    /*
        
        - input
            { thuebao_id, ds_rg_id }
        - output
          + data

    */
    hoan_thien_sau_tu_van: (data) => axios.post('/web-hopdong/goi-da-dichvu/hoan_thien_sau_tu_van', data),
    post_lay_khoanmuc_tt_theo_loaihd : (data) => axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd',data),
    post_get_keys: (data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',data),
    fn_lap_hd_tocdo_quyhoach: (data) => axios.post('/web-hopdong/hopdong/fn_lap_hd_tocdo_quyhoach', data),
    xoa_hdkh_v2: ( data) => axios.post('/web-hopdong/hopdong/xoa_hdkh_v2',data),
    lay_ds_hopdong_theo_ma_gd: (data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd',data),
    lay_ds_hopdong_thuebao_theo_hdkh: (hdkhid) => axios.get('web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId='+hdkhid),
    sp_thaydoi_tocdo_internet_hienthi: (data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthi', data),
    hths_td_tocdo_goi_quyhoach: (data) => axios.post('/web-hopdong/dongbo-visa-vasc/hths_td_tocdo_goi_quyhoach', data),
    fn_lay_thongtin_nhanvien: (data) => axios.post('/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien',data),
    lay_ds_hd_theo_ma_tb: (data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),
    lay_ds_hd_tb_theo_hdkh_id_v3: (data) => axios.post('/web-hopdong/tracuu_chitiet_thicong/lay_ds_hd_tb_theo_hdkh_id_v3', data),
    getLichSuThongtinTB: (data) => axios.post(`/web-tracuu/tracuu/sp_lay_lichsu_thongtin_tb_quyhoach`, data),
    sp_lay_tien_hd_theo_hdkh_id: (data) => axios.post(`/web-tracuu/tracuu_chitiet_thicong/sp_lay_tien_hd_theo_hdkh_id`, data),
    lay_tt_km_tratruoc_hd_khachhang: (data) => axios.post(`/web-tracuu/tracuu/lay_tt_km_tratruoc_hd_khachhang`, data),
}