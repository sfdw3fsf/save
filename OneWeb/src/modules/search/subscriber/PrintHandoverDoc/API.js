  

    export default{
        /////////////////PrintHandoverDoc////////
        get_ds_loai_hd: async (axios, data) => axios.get('/web-tracuu/bienban_nt/ds_loai_hd', null),
        get_ds_loai_dichvu_vt: async (axios, data) => axios.get('/web-tracuu/bienban_nt/ds_dichvu', null),
        get_nhanviens: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi/'+data+'/nhanviens', null),
        get_ds_phieu_yc: async (axios, data) => axios.post('/web-tracuu/bienban_nt/tim_kiem', data),
        map_id: async (axios, data) => axios.post('/web-quantri/thamso/map_id', data)

      }
    