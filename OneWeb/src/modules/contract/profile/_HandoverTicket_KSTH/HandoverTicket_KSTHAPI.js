export default{
    get_ds_quyen_nguoidung: async (axios, data) => axios.get('/web-quantri/quyen/ds_quyen_nguoidung', data),
    get_kieu_giao_hoso: async (axios, data) => axios.get('/web-tracuu/danhmuc/kieu_giao_hoso', data),
    get_donvi_giao_hoso: async (axios, data) => axios.get('/web-tracuu/danhmuc/donvi_giao_hoso', data),
    get_trangthai_ph: async (axios, data) => axios.get('/web-tracuu/danhmuc/trangthai_ph', data),
    post_layds_bohoso_chuagiao_theo_makh_magd: async (axios, data) => axios.post('/web-quantri/hoso/layds_bohoso_chuagiao_theo_makh_magd', data),
    /*
    {
      "denngay": null,
      "huonggiao_id": 0, //fix
      "kieugiao": 0,
      "ma_gd": "string",
      "phieu_id": 0, //fix
      "quytrinh_id": 550, //fix
      "timngay": 0,
      "tths_id": 1, //fix
      "ttph_id": 0,
      "tungay": null
    }
    */
    post_layds_file_trong_hoso: async (axios, data) => axios.post('/web-quantri/hoso/layds_file_trong_hoso', data),
    // { "hoso_id": 1}
    get_nhom_file: async (axios, data) => axios.get('/web-quantri/danhmuc/hoso/nhom_file', data),
    get_loai_file: async (axios, data) => axios.get('/web-quantri/danhmuc/hoso/loai_file', data),
    post_lay_ds_hd_tb_theo_hdkh_id: async (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_hd_tb_theo_hdkh_id', data),
    /*
    {vhdkh_id:0}

    "data": [
      {
        "chk": "string",
        "diachi_ld": "string",
        "diachi_tb": "string",
        "dichvuvt_id": "string",
        "donvi_id": "string",
        "ghichu": "string",
        "hdtb_id": "string",
        "loai_hd": "string",
        "loaihd_id": "string",
        "loaihinh_tb": "string",
        "loaitb_id": "string",
        "ma_tb": "string",
        "ngay_ht": "string",
        "ten_dvvt": "string",
        "ten_tb": "string",
        "thanhtoan_id": "string",
        "thuebao_id": "string",
        "trangthai_hd": "string",
        "trangthai_tb": "string"
      }
    ],*/
    post_danhsach_doituong_hoso_chung: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_doituong_hoso_chung', data),
    /*
      {
        "vtungay": "01/07/2021",
        "vdenngay": "02/07/2021",
      }
    */
    post_ds_bohoso_theo_nhanvien: async (axios, data) => axios.post('/web-quantri/hoso/ds_bohoso_theo_nhanvien', data),
    /*
      {
        "tungay": "17/07/2021",
        "denngay": "18/07/2021"
      }
    */
    post_giaophieu_donvi_tieptheo: async (axios, data) => axios.post('/web-quantri/hoso/giaophieu_donvi_tieptheo', data),
    /*
    {
      "danhsach": [
        {
          "bhs_id": "string",
          "dvgiao_id": "string",
          "dvnext_id": "string",
          "dvtt_id": "string",
          "lhs_id": "string",
          "malhs": "string",
          "nv_nhan": "string",
          "phieu_id": "string",
          "tthu_id": "string"
        }
      ],
      "hoso_id": 0,
      "huonggiao_id": 0,
      "kieu": 0,
      "quytrinh_id": 0
    }
    */
    post_traphieu_bohoso: async (axios, data) => axios.post('/web-quantri/hoso/traphieu_bohoso', data),
    /*
    {
      "danhsach": [
        {
          "bhs_id": "string",
          "dvgiao_id": "string",
          "dvnext_id": "string",
          "dvtt_id": "string",
          "lhs_id": "string",
          "malhs": "string",
          "nv_nhan": "string",
          "phieu_id": "string",
          "phieu_id_cha": "string",
          "tthu_id": "string"
        }
      ],
      "huonggiao_id": 0,
      "kieu": 0,
      "noidung": "string",
      "xoalohs": 0
    }
    */
    post_xacnhan_bohoso: async (axios, data) => axios.post('/web-quantri/hoso/xacnhan_bohoso', data),
    /*
    {
      "danhsach": [
        {
          "bhs_id": "string",
          "dvgiao_id": "string",
          "dvnext_id": "string",
          "dvtt_id": "string",
          "lhs_id": "string",
          "malhs": "string",
          "nv_nhan": "string",
          "phieu_id": "string",
          "phieu_id_cha": "string",
          "tthu_id": "string"
        }
      ],
      "huonggiao_id": 0,
      "kieu": 0,
      "noidung": "string",
      "xn_gv": 0
    }
    */
    post_lay_hd_thuebao_theo_hd_khachhang: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_hd_thuebao_theo_hd_khachhang', data),
    // { "hdkh_id": 5161 }
    //post_upload_file_hoso: async (axios, data) => axios.post('/web-hopdong/hopdong/upload_file_hoso', data),
    /*
    files: <file1>
    files: <file2>

    {
      "error": "BSS-00000000",
      "error_code": "BSS-00000000",
      "message": "Success",
      "message_detail": null,
      "request_id": null,
      "data": {
        "success_files": ["DHSXKD/HPG/HOSO/center of earth.jpg"],
        "failure_files": ["file2.jpg]
      }
    }
    */
    post_capnhat_file_hoso: async (axios, data) => axios.post('/web-hopdong/hopdong/capnhat_file_hoso', data),
    /*
    {
      "bohoso_id": 0, //truyen vao
      "file_id": 0,
      "file_url": "DHSXKD/HPG/HOSO/center of earth.jpg",
      "files": [],
      "ghichu": "string", //truyen vao
      "hoso_id": 0, //truyen vao
      "kieu": 6, //fix
      "loaifile_id": 0 //truyen vao
    }
    */
    upload_file_hoso: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    xoa_file_bsh_theo_fileid: async (axios, data) => axios.post('/web-hopdong/hopdong/xoa_file_bsh_theo_fileid', data),
  }