export default {
    //UR2.6.042: 13,14,15,16 tuỳ thuộc tag truyền vào
    sp_ht_dichvuvt_combobox: async (axios, dsdichvuvt_id) => axios.post('/web-thicong/thanhly-thuebao/sp_ht_dichvuvt_combobox',{
        dichvu_vt_id: dsdichvuvt_id
    }),
    
    // lay_ds_tocdo_adsl:async (axios) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_dulieu_bang_theo_dieukien',{
    //     schema: "CSS",
    //     table_name: "TOCDO_ADSL",
    //     list_of_cols: "TOCDO_ID,THUONGHIEU",
    //     where: "",
    //     order: "TOCDO_ID"
    // }),
    //Hàm này không dùng nữa, thay thể bởi hàm lay_ds_tocdo_adsl_vs_muccuoc
    lay_ds_tocdo_adsl:async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_tocdo_adsl',{
        p_param: "",
        p_type:3
    }),

    lay_ds_nhanvien_thuchien_theo_dv: async (axios, donvi_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_ds_nhanvien_thuchien_theo_dv',{
        donvi_id: donvi_id
    }),

    // lay_ds_bras: async (axios) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md',{
    //     schema: "CSS",
    //     table_name: "bras",
    //     list_of_cols: "bras_id, tenbras",
    //     where: "",
    //     order: "caidat_md desc"
    // }),
    lay_ds_bras: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_bras',{
        p_param: "",
        p_type:1
    }),
    
    // lay_ds_muccuoc: async (axios) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien',{
    //     schema: "CSS",
    //     table_name: "muccuoc",
    //     list_of_cols: "muccuoc_id,tenmuc",
    //     where: "",
    //     order: "muccuoc_id"
    // }),
    lay_ds_muccuoc: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_muccuoc',{
        p_param: "",
        p_type:1
    }),

    lay_ds_quytrinh_v2: async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2',data),

    lay_huonggiao_theo_quytrinh: async (axios, quytrinh_id, tthd_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_huonggiao_theo_quytrinh',{
        quytrinh_id: quytrinh_id,
        tthd_id: tthd_id
    }),

    sp_ht_dslam_cbo: async (axios, bras_id, kieu_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_ht_dslam_cbo',{
        kieu_id: kieu_id,
        bras_id: bras_id 
    }),
    //làm sau
    lay_thaotac: async (axios, luong_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_thaotac',{
        luong_id:luong_id
    }),
    // lay_all_thaotac_control: async (axios) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md',{
    //     schema: "css",
    //     table_name: "thaotac_control",
    //     list_of_cols: "*",
    //     where: "",
    //     order: ""
    // }),
    //
    lay_all_thaotac_control: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_thaotac_control',{
        p_param: "",
        p_type:1
    }),

    lay_ds_hopdong_huonggiao: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/lay_ds_hopdong_huonggiao',data),

    lay_tongtien_theo_hdkh_id: async (axios, hdkh_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/lay_tongtien_theo_hdkh_id',{
        vhdkh_id:hdkh_id
    }),

    ht_phieu_tra: async (axios, phieu_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_ht_phieu_tra',{
        phieu_id:phieu_id
    }),

    //truyền tramtb_id -->donvi_id
    // lay_tram_tb: async (axios, donvi_id) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md',{
    //     schema: "admin",
    //     table_name: "donvi",
    //     list_of_cols: "ten_dv",
    //     where: "donvi_id="+donvi_id,
    //     order: ""
    // }),
    lay_tram_tb: async (axios, donvi_id) => axios.post('/web-hopdong/laydulieu/fn_tt_donvi',{
        p_param: donvi_id,
        p_type:2
    }),

    lay_ds_hopdong_thuebao_adsl: async (axios, hdtbId) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId='+hdtbId),
    lay_ds_kieu_ld: async (axios, loaihd_id, loaitb_id) => axios.get('/web-hopdong/danhmuc/ds_kieu_ld/'+loaihd_id+'/'+loaitb_id),

    lay_ds_nhanvien_theo_phieu: async (axios, phieu_id, nhanvien_id, kieu_id) => axios.post('/web-ccdv/tiepnhanhopdong/ds_nhanvien_theo_phieu',{
        kieu_id: kieu_id,
        nhanvien_id: nhanvien_id,
        phieu_id: phieu_id
    }),

    // lay_tocdo_adsl_by_id: async (axios, tocdo_id) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien',{
    //     schema: "CSS",
    //     table_name: "TOCDO_ADSL",
    //     list_of_cols: "*",
    //     where: "tocdo_id="+tocdo_id,
    //     order: ""
    // }),
    lay_tocdo_adsl_by_id: async (axios, tocdo_id) => axios.post('/web-hopdong/laydulieu/sp_tt_tocdo_adsl',{
        p_param: tocdo_id,
        p_type:1
    }),

    // lay_thamso_cabman_v2: async (axios) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md',{
    //     list_of_cols: "*",
    //     order: "",
    //     schema: "admin",
    //     table_name: "thamso_md",
    //     where: "ma_ts='USING_CABMAN_2.0'"
    // }),
    lay_thamso_cabman_v2: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_thamso_md',{
        p_param: "USING_CABMAN_2.0",
        p_type:2
    }),
    
    lay_ds_thongtin_kythuat_hdtb: async (axios, hdtb_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/lay_ds_thongtin_kythuat_hdtb',{
        vhdtb_id:hdtb_id
    }),

    xoa_phieu: async (axios, phieu_id) => axios.get('/web-ccdv/tiepnhanhopdong/xoa_phieu/'+phieu_id),

    insert_giaophieu_nv: async (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/insert_giaophieu_nv', {
        json_giaophieu_nv:data
    }),

    sp_update_tt_giaoviec: async (axios, data) => axios.post('/web-ccdv/hths-thaydoi-tocdo/sp_update_tt_giaoviec', data),

    //  id_neo: "muccuoc_id_vdc",
    //     table: "CSS.MUCCUOC",
    //     dk:"where muccuoc_id = 1"
    //fn_map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),

    fn_tt_hd_thuebao: async (axios, hdtb_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao',{
        param: hdtb_id,
        type: 1 
    }),

    fn_tt_diachi_tb: async (axios, thuebao_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_tb',{
        param: thuebao_id,
        type: 1 
    }),

    fn_tt_profile_id_tocdo_adsl_theo_tocdo_id: async (axios, tocdo_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl',{
        param: tocdo_id,
        type: 5
    }),

    



    

    

    // 
    update_ip_adsl: async (axios, hdtb_id, ip) => axios.post('/web-thicong/hths-thaydoi-tocdo/update_ip_adsl', {
        hdtb_id: hdtb_id,
        ip: ip
    }),

    capnhat_ngaykh_ngay_ht_v2: async (axios, hdtb_id) => axios.post('/web-hopdong/CapNhatTraTruoc/capnhat_ngaykh_ngay_ht_v2', {
        hdtb_id: hdtb_id
    }),

    capnhat_nangtocdo: async (axios, thuebao_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/capnhat_nangtocdo', {
        vthuebao_id: thuebao_id
    }),

    // Gom api nút Kích hoạt
    kichhoat_thaydoi_tocdo: async (axios, data) => axios.post('/web-ccdv/hths-thaydoi-tocdo/kichhoat_thaydoi_tocdo', data),
    
    kiemtra_trangthai_jira_hths_v2: async (axios, hdtb_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/kiemtra_trangthai_jira_hths_v2', {
        vhdtb_id: hdtb_id
    }),

    sp_lay_donvi_giaophieu: async (axios, hdtb_id, huonggiao_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_donvi_giaophieu', {
        hdtb_id: hdtb_id,
        huonggiao_id: huonggiao_id
    }),

    fn_kiemtra_phieu_dagiao: async (axios, hdtb_id, huonggiao_id) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_kiemtra_phieu_dagiao', {
        vhdtb_id: hdtb_id,
        vhuonggiao_id: huonggiao_id
    }),

    fn_capnhat_ngayht: async (axios, hdtb_id, ngay_ht) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_capnhat_ngayht', {
        hdtb_id: hdtb_id,
        ngay_ht: ngay_ht
    }),
    sp_capnhat_ngaykh: async (axios, hdtb_id, ngay_kh) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_capnhat_ngaykh', {
        hdtb_id: hdtb_id,
        ngay_kh: ngay_kh
    }),

    lay_ip_tinh: async (axios, brasId, serviceId) => axios.post('/tichhop/visa-report/getIps', {
        brasId:brasId,
        serviceId:serviceId
    }),


    // Giao phiếu
    lay_huonggiao_theo_luong_id: async (axios, luong_id) => axios.post('/web-hopdong/dichvu-ca/lay_huonggiao_theo_luong_id', {
         vluong_id: luong_id
    }),

    // Cập nhật nội dung thực hiện
    capnhat_noidung_thuchien: async (axios, phieuId, noiDung) => axios.post('/web-thicong/thuebao/capnhat_noidung_thuchien?phieuId='+phieuId+'&&noiDung='+noiDung, {}),
    
    fn_hths_thaydoi_tocdo: async (axios, data) => axios.post('/web-ccdv/hths-thaydoi-tocdo/fn_hths_thaydoi_tocdo', data),
    fn_hoancong_hths_thaydoi_tocdo: async (axios, data) => axios.post('/web-ccdv/hths-thaydoi-tocdo/fn_hoancong_hths_thaydoi_tocdo', data),

    sp_lay_ds_phieuvt_hdbh: async (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_lay_ds_phieuvt_hdbh', data),
    
    sp_chonport: async (axios, hdtb_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/sp_chonport', {
        p_hdtb_id: hdtb_id
    }),

    lay_ds_hdtb_adsl_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/lay_ds_hdtb_adsl_theo_hdtb_id', {
        in_hdtb_id: hdtb_id
    }),

    sp_ktra_dk_port: async (axios, data) => axios.post('/web-ccdv/khaibaoims/sp_ktra_dk_port', data),

    fn_capnhat_trangthai_port: async (axios, data) => axios.post('/web-ccdv/khaibaoims/fn_capnhat_trangthai_port', data),
    fn_capnhat_trangthai_portid: async (axios, data) => axios.post('/web-ccdv/khaibaoims/fn_capnhat_trangthai_portid', data),
    fn_capnhat_ds_matb: async (axios, data) => axios.post('/web-ccdv/khaibaoims/fn_capnhat_ds_matb', data),
    Capnhat_port_vatly: async (axios, data) => axios.post('/web-ccdv/dongbo-visa-vasc/Capnhat_port_vatly', data),
    
    lay_dslam_theo_port_id: async (axios, portId) => axios.get('/web-ccdv/khaibaoims/lay_dslam_theo_port_id?portId='+portId),

    capnhat_thongtin_port_int_second: async (axios,data) => axios.post('/web-ccdv/hths-thaydoi-tocdo/capnhat_thongtin_port_int_second', data),
    

    //test thử coi sao
    lay_ds_tocdo_adsl_vs_muccuoc: async (axios, nguoidung_id) => axios.post('/web-quantri/muccuoc/fn_muccuoctb_load',{
        p_ds_para: JSON.stringify({nguoidung_id: nguoidung_id})
    }),

    lay_ds_quytrinh_newlife: async (axios, data) => axios.post('/web-thicong/new-life/lay_ds_quytrinh_newlife', data),
    lay_ds_hopdong_huonggiao_chuquan: async (axios, data) => axios.post('/web-thicong/new-life/lay_ds_hopdong_huonggiao_chuquan', data),
}