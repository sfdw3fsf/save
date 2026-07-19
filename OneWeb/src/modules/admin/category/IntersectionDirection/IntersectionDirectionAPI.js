export default{
    lay_danhsach_nhom_quytrinh: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NHOM_QT'),

    lay_danhsach_loai_hd: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),

    lay_danhsach_dichvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

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

    sp_lay_ds_quytrinh_combobox: async function (axios, nhom_qt_id, loaihd_id, dichvuvt_id, kt_load){
        let path='/web-quantri/huonggiao/sp_lay_ds_quytrinh_combobox'
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

    sp_del_huonggiao_by_id: async (axios, data) => axios.post('/web-quantri/huonggiao/sp_del_huonggiao_by_id', data),
   
    sp_ghilai_huonggiao: async (axios, data) => axios.post('/web-quantri/huonggiao/sp_ghilai_huonggiao', data),

    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
        keyname:keyname
    }),

    fn_quytrinh_get_command_by_huonggiao_id: async (axios, huonggiao_id) => axios.get('/web-quantri/huonggiao/fn_quytrinh_get_command_by_huonggiao_id?huonggiao_id='+huonggiao_id),
    
    fn_huonggiao_get_command: async (axios, huonggiao_id) => axios.get('/web-quantri/huonggiao/fn_huonggiao_get_command?huonggiao_id='+huonggiao_id),

    sp_upd_huonggiao_command: async (axios, data) => axios.post('/web-quantri/huonggiao/sp_upd_huonggiao_command', data),

    sp_giaosim_get_treeview: async (axios, donvicha_id) => axios.post('/web-quantri/donvi/SP_GIAOSIM_GET_TREEVIEW_NEW', {
        p_donvicha_id: donvicha_id
    }),


    //https://cntt.vnpt.vn/browse/BSS-21943
    //Fix ATBM theo api mới link trên
    lay_nhanvien_id_theo_mand: async (axios, ma_nd) => axios.post('/web-quantri/huonggiao/lay_nhanvien_id_theo_mand', {
        ma_nd: ma_nd
    }),
    lay_nguoidung_id_theo_mand: async (axios, ma_nd) => axios.post('/web-quantri/huonggiao/lay_nguoidung_id_theo_mand', {
        ma_nd: ma_nd
    }),

    lay_donvi_id_theo_nhanvien_id: async (axios, nhanvien_id) => axios.post('/web-quantri/huonggiao/lay_donvi_id_theo_nhanvien_id', {
       nhanvien_id: nhanvien_id
    }),
    sp_huonggiao_runsql: async (axios, data) => axios.post('/web-quantri/huonggiao/sp_huonggiao_runsql', data),


}
