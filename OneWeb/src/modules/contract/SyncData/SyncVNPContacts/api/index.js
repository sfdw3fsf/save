export default {
    
    /**
     * Lấy danh sách cho dropdown các kiểu biến động thuê bao
     */
    get_ds_kieubd: async (axios) => axios.get('/web-ccdv/dongbo_danhba_vnp/ht_combo_kieubd_dongbo_vina'),

    /**
     * Lấy danh sách các lọai dữ liệu cho dropdown
     */
    get_ds_loai_dulieu: async (axios) => axios.get('/web-ccdv/dongbo_danhba_vnp/ht_combo_kieudl_dongbo_vina'),

    /**
     * Lấy danh sách đơn vị quản lý cộng tác viên cho dropdown
     */
    get_ds_donvi_QL: async (axios) => axios.get('/web-ccdv/dongbo_danhba_vnp/lay_ds_donvi_ctv'),

    /**
     * Lấy danh sách Nhóm nhân viên
     * @param {*} axios 
     * @param {*} body {"nhomlnv_id": 0}
     * @returns 
     */
    post_ds_nhom_nv: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/ht_ds_nhomnv_combobox', body),

    /**
     * Lấy danh sách nhân viên
     * @param {*} body {"donvi_id": 1, // Đơn vị quản lý "loainv_id": 0 // Nhóm CTV}
     */
    post_ds_nhanvien: async (axios, body) => axios.post('/web-thicong/hoso_datcoc_theofile/ht_ds_nhanvien_ctv_combobox', body),

    post_ds_user_neo: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/lay_ds_user_neo', body),

    /**
     * Hiển thị danh sách dữ liệu thuê bao đã đồng bộ.
     */
    post_ds_da_dong_bo: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/hienthi_danhsach_da_dongbo', body),

    /**
     * Lấy danh sách thuê bao biến động (đưa vào grid)
     * @param {*} body {"nguoi_cn ": "","loaidl": 1 (lấy từ dropdow), "tungay": "13/04/2022","denngay": "13/04/2022","kieu": 0 (lấy từ group radio }
     */
    post_ds_tb_biendong: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/lay_ds_su_lieu_dongbo', body),
    
    // Danh sách các API thực hiện tác vụ đồng bộ (có 7 APIs)
    // Hàm 1: body: {"vloai": 0,"vid": 0}
    post_kiemtra_diachi_vnp: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/kiemtra_diachi_vnp', body),

    // Hàm 2: body {"vid": 0,"vloai_dl": 0,"vquan_id": 0,"vphuong_id": 0,"vpho_id": 0}
    post_dongbo_danhba_vnp_capnhat_dc: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/dongbo_danhba_vnp_capnhat_dc', body),
    
    // Hàm 3: body: {"vloai": 0,"vid": 0}
    post_lay_diachi_vnp: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/lay_diachi_vnp', body),
    
    // Hàm 4 chưa thực hiện. Note hàm 4: view khachhangs_vina không có, chưa có dblink

    // Hàm 5: body: {"somay": "abc"}
    post_fn_thanhtoan: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/fn_thanhtoan', body),

    // Hàm 6: body: {"ma_kh_neo": "abc"}
    post_fn_thanhtoan_ktchuyenquyen: async (axios, body) => axios.post('/web-ccdv/dongbo_danhba_vnp/fn_thanhtoan_ktchuyenquyen', body)

}