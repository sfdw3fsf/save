export default {
    //Hiển thị danh sách
    getDanhsach: (axios, data) => axios.get(`/web-quantri/thaotac/lay_ds_thaotac?thaotacId=${data.thaotac_id}`),
    //Xóa thao tác
    xoa_thaotac: (axios, data) => axios.post('/web-quantri/thaotac/xoa_thaotac', data),

    danhsach_control: (axios, data) => axios.post('/web-quantri/thaotac/sp_lay_ds_thaotac_control', data),
    getKeys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
    capnhat_thaotac: (axios, data) => axios.post('/web-quantri/thaotac/capnhat_thaotac', data),
    
   
    
    
}
  