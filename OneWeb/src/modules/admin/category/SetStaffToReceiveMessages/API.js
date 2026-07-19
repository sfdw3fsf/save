export default {
    lay_danhsach_nhom_quytrinh: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NHOM_QT'),
    lay_danhsach_loai_hd: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),
    lay_danhsach_dichvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    sp_lay_ds_quytrinh_timkiem_quytrinh: async function (axios, nhom_qt_id, loaihd_id, dichvuvt_id, kt_load){
        let path='/web-quantri/huonggiao/sp_lay_ds_quytrinh_timkiem_quytrinh'
        path=path.concat('?')
        path=path.concat('nhom_qt_id=')
        path=path.concat(nhom_qt_id)
        path=path.concat('&')
        path=path.concat('loaihd_id=')
        path=path.concat(loaihd_id)
        path=path.concat('&')
        path=path.concat('dichvuvt_id=')
        path=path.concat(dichvuvt_id)
        path=path.concat('&')
        path=path.concat('kt_load=')
        path=path.concat(kt_load)
        return axios.get(path)
    },
    lay_danhsach_huonggiao: async function (axios, nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id){
        let path='/web-quantri/huonggiao/sp_lay_ds_huonggiao_by_id'
        path=path.concat('?')
        path=path.concat('nhom_qt_id=')
        path=path.concat(nhom_qt_id)
        path=path.concat('&')
        path=path.concat('quytrinh_id=')
        path=path.concat(quytrinh_id)
        path=path.concat('&')
        path=path.concat('loaihd_id=')
        path=path.concat(loaihd_id)
        path=path.concat('&')
        path=path.concat('dichvuvt_id=')
        path=path.concat(dichvuvt_id)
        return axios.get(path)
    },
    sp_load_treeview: async (axios) => axios.get('/web-quantri/donvi/sp_load_treeview'),

    fn_tt_huonggiao_ldv: async (axios, huonggiao_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_huonggiao_ldv',{
        type: type, 
        param: huonggiao_id
    }),
    sp_ht_tatca_vetinh_combobox_v2: async (axios, huonggiao_id, loaidv_dich_id) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_ht_tatca_vetinh_combobox_v2',{
        huonggiao_id: huonggiao_id,
        loaidv_dich_id: loaidv_dich_id
    }),

    lay_ds_kieucb: async (axios) => axios.post('/web-quantri/quanlytinnhanv2/sp_ql_tinnhan_kieucb',{}),

    lay_ds_nhanvien_nhantin: async (axios, data) => axios.post('/web-quantri/nhantin_nv/lay_ds_nhanvien_nhantin',data),

    capnhat_nhantin_nv: async (axios, data) => axios.post('/web-quantri/nhantin_nv/capnhat_nhantin_nv', data),
    
    
    
    
}