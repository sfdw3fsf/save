export default {    
    ///thietlap-thoigian-sua-phieuks-v2/lay_danhmuc_giamthinh
    lay_danhmuc_giamthinh: (axios, data) => axios.post('/web-hopdong/thietlap-thoigian-sua-phieuks-v2/lay_danhmuc_giamthinh', data),

    ///thietlap-thoigian-sua-phieuks-v2/ds_tgks_nhanvien
    ds_tgks_nhanvien: (axios, params) => axios.get('/web-hopdong/thietlap-thoigian-sua-phieuks-v2/ds_tgks_nhanvien', {params}),

   //thietlap-thoigian-sua-phieuks-v2/capnhat_tgks_nhanvien
   capnhat_tgks_nhanvien: (axios, data) => axios.post('/web-hopdong/thietlap-thoigian-sua-phieuks-v2/capnhat_tgks_nhanvien', data),

  ///thietlap-thoigian-sua-phieuks-v2/delete_tgks_nhanvien
   delete_tgks_nhanvien: (axios, data) => axios.post('/web-hopdong/thietlap-thoigian-sua-phieuks-v2/delete_tgks_nhanvien', data),

}
  